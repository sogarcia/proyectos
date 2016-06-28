package co.edu.usbcali.demo.vista;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.demo.delegado.IDelegadoDeNegocio;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@ManagedBean
@ViewScoped
public class UsuarioVista {

	private static final Logger log = LoggerFactory.getLogger(UsuarioVista.class);
	
	@ManagedProperty(value="#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private List<Usuarios> losUsuarios;
	
	private List<SelectItem> losTiposUsuariosItems;
	
	private InputText txtCodigo;
	private InputText txtNombre;
	private InputText txtLogin;
	private Password  pswClave;
	
	
	private SelectOneMenu somTipoUsuario;
	
	private CommandButton btnGuardar;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;
	
	
	public void getTxtCedulaListener(){
		Usuarios usuario = null;
		try {
			long cedula = Long.parseLong(txtCodigo.getValue().toString().trim());
			usuario = delegadoDeNegocio.consultarUsuariosPorId(cedula);						
		} catch (Exception e) {
		}
		
		if(usuario == null){
			txtNombre.resetValue();
			txtLogin.resetValue();
			pswClave.resetValue();			
			somTipoUsuario.setValue("-1");
			
			btnBorrar.setDisabled(true);
			btnGuardar.setDisabled(false);
			btnModificar.setDisabled(true);
		}else{
			log.info("Clave encontrada: " + usuario.getUsuClave().toString());
			txtNombre.setValue(usuario.getUsuNombre());
			txtLogin.setValue(usuario.getUsuLogin());
			String pass = usuario.getUsuClave().toString();
			pswClave.setValue(pass);
			//pswClave.setDisabled(true);
			
			
			somTipoUsuario.setValue(usuario.getTiposUsuarios().getTusuCodigo());
			
			btnBorrar.setDisabled(false);
			btnGuardar.setDisabled(true);
			btnModificar.setDisabled(false);			
		}
	}
	
	public String crearAction(){
		
		log.info("Ingreso al crear usuario");		
		try {
			Usuarios usuario = new Usuarios();	
			String usuCedula = txtCodigo.getValue().toString().trim();
			if(!delegadoDeNegocio.isNumerico(txtCodigo.getValue().toString().trim())){
				throw new Exception("El numero de cedula debe ser numerico");
			}	
			usuario.setUsuCedula(Long.parseLong((usuCedula)));
			usuario.setUsuNombre(txtNombre.getValue().toString());
			usuario.setUsuLogin(txtLogin.getValue().toString());
			usuario.setUsuClave(pswClave.getValue().toString());
			log.info("Inicia consulta de tipo usuarios [ " + somTipoUsuario.getValue().toString()+ " ]");
			TiposUsuarios tipoUsuarios = delegadoDeNegocio.consultarTipoUsuarioPorId((Long.parseLong(somTipoUsuario.getValue().toString())));
			usuario.setTiposUsuarios(tipoUsuarios);	
			delegadoDeNegocio.grabarUsuario(usuario);			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"El usuario fue registrado con exito", ""));
			losUsuarios = null;
			getLosUsuarios();		
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
		}		
		
		return "";
	}
	
	public String modificarAction(){
		
		log.info("Ingreso al modificar usuario");
		try {
			Usuarios usuario = new Usuarios();			
			usuario.setUsuCedula((Long.parseLong((txtCodigo.getValue().toString().trim()))));
			usuario.setUsuNombre((txtNombre.getValue().toString()));
			usuario.setUsuClave((pswClave.getValue().toString()));
			usuario.setUsuLogin((txtLogin.getValue().toString()));
			log.info("Inicia consulta de tipo usuario [ " + somTipoUsuario.getValue().toString()+ " ]");
			TiposUsuarios tipoUsuario = delegadoDeNegocio.consultarTipoUsuarioPorId((Long.parseLong(somTipoUsuario.getValue().toString())));
			usuario.setTiposUsuarios(tipoUsuario);		
			delegadoDeNegocio.modificarUsuario(usuario);
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"El usuario fue modificado con exito", ""));
			losUsuarios = null;
			getLosUsuarios();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
		}
		return "";
	}
	
	public String borrarAction(){
		log.info("Ingreso al borrar usuario");
		try {
			Usuarios usuario = new Usuarios();			
			usuario.setUsuCedula(Long.parseLong((txtCodigo.getValue().toString().trim())));
			log.info("Inicia consulta de tipo usuarios [ " + somTipoUsuario.getValue().toString()+ " ]");			
			delegadoDeNegocio.borrarUsuario(usuario);			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"El usuario fue eliminado con exito", ""));
			losUsuarios = null;
			getLosUsuarios();
			limpiarAction();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
		}
		return "";
	}
	
	public String limpiarAction(){
		log.info("Ingreso al limpiar usuario");
		
		try {
			txtCodigo.resetValue();
			txtNombre.resetValue();
			txtLogin.resetValue();
			pswClave.resetValue();
			somTipoUsuario.setValue("-1");			
			btnBorrar.setDisabled(true);
			btnGuardar.setDisabled(true);
			btnModificar.setDisabled(true);		
		} catch (Exception e) {
			//FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
		}		
		return "";
	}

	public List<Usuarios> getLosUsuarios() {		
		if(losUsuarios == null){		
			try {
				losUsuarios = delegadoDeNegocio.consultarTodosUsuarios();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}		
		return losUsuarios;
	}

	public void setLosUsuarios(List<Usuarios> losUsuarios) {
		this.losUsuarios = losUsuarios;
	}
	
	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public List<SelectItem> getLosTiposUsuariosItems() {
		
		if(losTiposUsuariosItems == null){
			
			List<TiposUsuarios> listTiposUsuarios;
			try {
				losTiposUsuariosItems = new ArrayList<>();
				listTiposUsuarios = delegadoDeNegocio.consultarTodosTipoUsuario();
				for (TiposUsuarios tiposUsuarios : listTiposUsuarios) {
					losTiposUsuariosItems.add(new SelectItem(tiposUsuarios.getTusuCodigo(), tiposUsuarios.getTusuNombre()));
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		return losTiposUsuariosItems;
	}

	public void setLosTiposUsuariosItems(List<SelectItem> losTiposUsuariosItems) {
		this.losTiposUsuariosItems = losTiposUsuariosItems;
	}

	public InputText getTxtCodigo() {
		return txtCodigo;
	}

	public void setTxtCodigo(InputText txtCodigo) {
		this.txtCodigo = txtCodigo;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public CommandButton getBtnGuardar() {
		return btnGuardar;
	}

	public void setBtnGuardar(CommandButton btnGuardar) {
		this.btnGuardar = btnGuardar;
	}

	public CommandButton getBtnModificar() {
		return btnModificar;
	}

	public void setBtnModificar(CommandButton btnModificar) {
		this.btnModificar = btnModificar;
	}

	public CommandButton getBtnBorrar() {
		return btnBorrar;
	}

	public void setBtnBorrar(CommandButton btnBorrar) {
		this.btnBorrar = btnBorrar;
	}

	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}

	public InputText getTxtLogin() {
		return txtLogin;
	}

	public void setTxtLogin(InputText txtLogin) {
		this.txtLogin = txtLogin;
	}

	public Password getPswClave() {
		return pswClave;
	}

	public void setPswClave(Password pswClave) {
		this.pswClave = pswClave;
	}

	public SelectOneMenu getSomTipoUsuario() {
		return somTipoUsuario;
	}

	public void setSomTipoUsuario(SelectOneMenu somTipoUsuario) {
		this.somTipoUsuario = somTipoUsuario;
	}
	
	
	
}

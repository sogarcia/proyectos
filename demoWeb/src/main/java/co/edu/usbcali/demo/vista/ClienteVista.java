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
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.demo.delegado.IDelegadoDeNegocio;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@ManagedBean
@ViewScoped
public class ClienteVista {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteVista.class);
	
	@ManagedProperty(value="#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;

	private List<Clientes> losClientes;
	
	private List<SelectItem> losTiposDocumentosItems;
	
	private InputText txtIdentificacion;
	private InputText txtNombre;
	private InputText txtTelefono;
	private InputText txtDireccion;
	private InputText txtMail;
	
	private SelectOneMenu somTipoDocumentos;
	
	private CommandButton btnGuardar;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;
	
	
	public void getTxtIdentificacionListener(){
		Clientes cliente = null;
		if(!txtIdentificacion.getValue().toString().trim().isEmpty()){
			try {
				long clieId = Long.parseLong(txtIdentificacion.getValue().toString().trim());
				cliente = delegadoDeNegocio.consultarClinetePorId(clieId);						
			} catch (Exception e) {
			}		
		
			if(cliente == null){				
				txtNombre.resetValue();
				txtDireccion.resetValue();
				txtTelefono.resetValue();
				txtMail.resetValue();			
				somTipoDocumentos.setValue("-1");
				
				btnBorrar.setDisabled(true);
				btnGuardar.setDisabled(false);
				btnModificar.setDisabled(true);
			}else{
				
				txtNombre.setValue(cliente.getCliNombre());
				txtDireccion.setValue(cliente.getCliDireccion());
				txtTelefono.setValue(cliente.getCliTelefono());
				txtMail.setValue(cliente.getCliMail());
				
				somTipoDocumentos.setValue(cliente.getTiposDocumentos().getTdocCodigo());
				
				btnBorrar.setDisabled(false);
				btnGuardar.setDisabled(true);
				btnModificar.setDisabled(false);			
			}
		}else{
			
			txtNombre.resetValue();
			txtDireccion.resetValue();
			txtTelefono.resetValue();
			txtMail.resetValue();			
			somTipoDocumentos.setValue("-1");
			
			btnBorrar.setDisabled(true);
			btnGuardar.setDisabled(true);
			btnModificar.setDisabled(true);			
		}
	}
	
	public String crearAction(){
		
		log.info("Ingreso al crear cliente");		
		try {
			Clientes cliente = new Clientes();	
			String usuCedula = txtIdentificacion.getValue().toString().trim();
			if (!delegadoDeNegocio.isNumerico(usuCedula)){
				throw new Exception("La identificacion debe ser numerico");
			}
			cliente.setCliId(Long.parseLong((usuCedula)));
			cliente.setCliNombre(txtNombre.getValue().toString());
			cliente.setCliDireccion(txtDireccion.getValue().toString());
			cliente.setCliTelefono(txtTelefono.getValue().toString());
			cliente.setCliMail(txtMail.getValue().toString());
			log.info("Inicia consulta de tipo documento [ " + somTipoDocumentos.getValue().toString()+ " ]");
			TiposDocumentos tipoDocumento = delegadoDeNegocio.consultarTipoDocumentoId(Long.parseLong(somTipoDocumentos.getValue().toString()));
			cliente.setTiposDocumentos(tipoDocumento);			
			delegadoDeNegocio.grabarClientes(cliente);			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"El cliente fue registrado con exito", ""));
			losClientes = null;
			getLosClientes();			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
		}		
		
		return "";
	}
	
	public String modificarAction(){
		
		log.info("Ingreso al modificar cliente");
		try {
			Clientes cliente = new Clientes();			
			cliente.setCliId(Long.parseLong((txtIdentificacion.getValue().toString().trim())));
			cliente.setCliNombre(txtNombre.getValue().toString());
			cliente.setCliDireccion(txtDireccion.getValue().toString());
			cliente.setCliTelefono(txtTelefono.getValue().toString());
			cliente.setCliMail(txtMail.getValue().toString());
			log.info("Inicia consulta de tipo documento [ " + somTipoDocumentos.getValue().toString()+ " ]");
			TiposDocumentos tipoDocumento = delegadoDeNegocio.consultarTipoDocumentoId(Long.parseLong(somTipoDocumentos.getValue().toString()));
			cliente.setTiposDocumentos(tipoDocumento);			
			delegadoDeNegocio.modificarClientes(cliente);			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"El cliente fue modificado con exito", ""));
			losClientes = null;
			getLosClientes();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
		}
		return "";
	}
	
	public String borrarAction(){
		log.info("Ingreso al borrar cliente");
		try {
			Clientes cliente = new Clientes();			
			cliente.setCliId(Long.parseLong((txtIdentificacion.getValue().toString().trim())));
			log.info("Inicia consulta de tipo documento [ " + somTipoDocumentos.getValue().toString()+ " ]");			
			delegadoDeNegocio.borrarClientes(cliente);			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"El cliente fue eliminado con exito", ""));
			losClientes = null;
			getLosClientes();
			limpiarAction();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
		}
		return "";
	}
	
	public String limpiarAction(){
		log.info("Ingreso al limpiar cliente");
		
		try {
			txtIdentificacion.resetValue();
			txtNombre.resetValue();
			txtDireccion.resetValue();
			txtTelefono.resetValue();
			txtMail.resetValue();
			somTipoDocumentos.setValue("-1");			
			btnBorrar.setDisabled(true);
			btnGuardar.setDisabled(true);
			btnModificar.setDisabled(true);		
		} catch (Exception e) {
			//FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
		}		
		return "";
	}

	public List<Clientes> getLosClientes() {		
		if(losClientes == null){			
			try {
				losClientes = delegadoDeNegocio.consultarTodosClientes();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return losClientes;
	}

	public void setLosClientes(List<Clientes> losClientes) {
		this.losClientes = losClientes;
	}
	
	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public List<SelectItem> getLosTiposDocumentosItems() {
		
		if(losTiposDocumentosItems == null){
			
			List<TiposDocumentos> listTiposDocumentos;
			try {
				losTiposDocumentosItems = new ArrayList<>();
				listTiposDocumentos = delegadoDeNegocio.consultarTodosTiposDocumentos();
				for (TiposDocumentos tiposDocumentos : listTiposDocumentos) {
					losTiposDocumentosItems.add(new SelectItem(tiposDocumentos.getTdocCodigo(), tiposDocumentos.getTdocNombre()));
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		return losTiposDocumentosItems;
	}

	public void setLosTiposDocumentosItems(List<SelectItem> losTiposDocumentosItems) {
		this.losTiposDocumentosItems = losTiposDocumentosItems;
	}

	public InputText getTxtIdentificacion() {
		return txtIdentificacion;
	}

	public void setTxtIdentificacion(InputText txtIdentificacion) {
		this.txtIdentificacion = txtIdentificacion;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputText getTxtTelefono() {
		return txtTelefono;
	}

	public void setTxtTelefono(InputText txtTelefono) {
		this.txtTelefono = txtTelefono;
	}

	public InputText getTxtDireccion() {
		return txtDireccion;
	}

	public void setTxtDireccion(InputText txtDireccion) {
		this.txtDireccion = txtDireccion;
	}

	public InputText getTxtMail() {
		return txtMail;
	}

	public void setTxtMail(InputText txtMail) {
		this.txtMail = txtMail;
	}

	public SelectOneMenu getSomTipoDocumentos() {
		return somTipoDocumentos;
	}

	public void setSomTipoDocumentos(SelectOneMenu somTipoDocumentos) {
		this.somTipoDocumentos = somTipoDocumentos;
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
	
	

}

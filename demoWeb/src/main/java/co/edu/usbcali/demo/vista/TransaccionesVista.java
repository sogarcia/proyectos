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
import co.edu.usbcali.demo.dto.TransaccionDTO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@ManagedBean
@ViewScoped
public class TransaccionesVista {
	
	private static final Logger log = LoggerFactory.getLogger(TransaccionesVista.class);
	
	@ManagedProperty(value="#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;
	
	private InputText txtNumeroCuenta;
	private InputText txtSaldoCuenta;
	private InputText txtIdCliente;
	private InputText txtNombreCliente;
	private InputText txtEmailCliente;
	private InputText txtTelefonoCliente;
	private InputText txtValorTransaccion;
	
	private SelectOneMenu somUsuarios;
	private List<SelectItem> losUsuariosItems;
	
	private CommandButton btnConsignar;
	private CommandButton btnRetirar;
	private CommandButton btnLimpiar;
	
	private List<TransaccionDTO> lastransacciones;
	
	
	public void getTxtCuentaListener(){
		log.info("Ingresa a consultar cuenta");
		Cuentas cuenta = null;
		try {
			String cueNumero = txtNumeroCuenta.getValue().toString().trim();
			cuenta = delegadoDeNegocio.consultarCuentaPorId(cueNumero);						
		} catch (Exception e) {
		}
		
		if(cuenta == null){				
			txtSaldoCuenta.resetValue();
			txtIdCliente.resetValue();
			txtNombreCliente.resetValue();
			txtEmailCliente.resetValue();			
			txtTelefonoCliente.resetValue();
			
			txtValorTransaccion.resetValue();
			somUsuarios.setValue("-1");
			
			btnConsignar.setDisabled(true);
			btnRetirar.setDisabled(true);
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"El numero de cuenta ingresado no existe", ""));
			
		}else{			
			txtSaldoCuenta.setValue(cuenta.getCueSaldo());
			
			Clientes cliente = new Clientes();
			try {
				cliente = delegadoDeNegocio.consultarClinetePorId(cuenta.getClientes().getCliId());
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
			}
			txtIdCliente.setValue(cliente.getCliId());
			txtNombreCliente.setValue(cliente.getCliNombre());
			txtEmailCliente.setValue(cliente.getCliMail());
			txtTelefonoCliente.setValue(cliente.getCliTelefono());
			
			btnConsignar.setDisabled(false);
			btnRetirar.setDisabled(false);
			getTransaccionCuenta();
					
		}
	}
	
	public void getPosConsignacionCuenta(){		
		Cuentas cuenta = null;
		try {
			String cueNumero = txtNumeroCuenta.getValue().toString().trim();
			cuenta = delegadoDeNegocio.consultarCuentaPorId(cueNumero);						
		} catch (Exception e) {
		}
		
		if(cuenta == null){		
			
			txtSaldoCuenta.resetValue();
			txtIdCliente.resetValue();
			txtNombreCliente.resetValue();
			txtEmailCliente.resetValue();			
			txtTelefonoCliente.resetValue();			
			txtValorTransaccion.resetValue();
			somUsuarios.setValue("-1");			
			btnConsignar.setDisabled(true);
			btnRetirar.setDisabled(true);
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"El numero de cuenta ingresado no existe", ""));
			
		}else{
			
			txtSaldoCuenta.setValue(cuenta.getCueSaldo());
			Clientes cliente = new Clientes();
			try {
				cliente = delegadoDeNegocio.consultarClinetePorId(cuenta.getClientes().getCliId());
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
			}
			txtIdCliente.setValue(cliente.getCliId());
			txtNombreCliente.setValue(cliente.getCliNombre());
			txtEmailCliente.setValue(cliente.getCliMail());
			txtTelefonoCliente.setValue(cliente.getCliTelefono());			
			txtValorTransaccion.resetValue();
			somUsuarios.setValue("-1");			
			btnConsignar.setDisabled(false);
			btnRetirar.setDisabled(false);
			getTransaccionCuenta();
					
		}
	}
	
	public String consignarAction(){
		
		log.info("Ingresa a consignar action");
		
		try {
			log.info("Inicializando bloque try catch" + txtIdCliente.getValue().toString());
						
			long idCliente = Long.parseLong(txtIdCliente.getValue().toString().trim());
			log.info("Encuentra cliente");
			String cueNumero = txtNumeroCuenta.getValue().toString().trim();
			if(cueNumero == null || cueNumero.isEmpty()){
				throw new Exception("El numero de cuenta no es valido");
			}			
			long usuCedula = Long.parseLong(somUsuarios.getValue().toString().trim());
			
			if(txtValorTransaccion.getValue().toString().trim().isEmpty()){
				throw new Exception("Es obligatorio diligenciar el valor de la consignacion");
			}			
			if(!delegadoDeNegocio.isNumerico(txtValorTransaccion.getValue().toString().trim())){
				throw new Exception("El valor de la consignacion debe ser numerico");
			}
			
			double valor = Double.parseDouble(txtValorTransaccion.getValue().toString().trim());
			
			delegadoDeNegocio.consignar(idCliente, cueNumero, usuCedula, valor);
			
			getTxtCuentaListener();			
						
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"Consignacion realizada con exito", ""));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
		}
		
		return "";
	}
	
	public String retirarAction(){
		
		log.info("Ingresa a retirar action");
		
		try {
			log.info("Inicializando bloque try catch" + txtIdCliente.getValue().toString());
			//long cliId, String cueNumero, long usuCedula, double valor
			long idCliente = Long.parseLong(txtIdCliente.getValue().toString().trim());
			log.info("Encuentra cuenta");
			String cueNumero = txtNumeroCuenta.getValue().toString().trim();
			if(cueNumero == null || cueNumero.isEmpty()){
				throw new Exception("El numero de cuenta no es valido");
			}			
			long usuCedula = Long.parseLong(somUsuarios.getValue().toString().trim());
			
			if(txtValorTransaccion.getValue().toString().trim().isEmpty()){
				throw new Exception("Es obligatorio diligenciar el valor a retirar");
			}
			
			if(!delegadoDeNegocio.isNumerico(txtValorTransaccion.getValue().toString().trim())){
				throw new Exception("El valor del retiro debe ser numerico");
			}
			
			double valor = Double.parseDouble(txtValorTransaccion.getValue().toString().trim());
			
			delegadoDeNegocio.retirar(idCliente, cueNumero, usuCedula, valor);
			
			getTxtCuentaListener();			
						
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,"Retiro realizado con exito", ""));
			
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
		}
		
		return "";
	}
	
	public String limpiarAction() {
		log.info("Ingreso al limpiar cliente");

		try {
			txtNumeroCuenta.resetValue();
			txtSaldoCuenta.resetValue();
			txtIdCliente.resetValue();
			txtNombreCliente.resetValue();
			txtValorTransaccion.resetValue();
			txtEmailCliente.resetValue();
			txtTelefonoCliente.resetValue();
			somUsuarios.setValue("-1");
			btnConsignar.setDisabled(true);
			btnRetirar.setDisabled(true);
			btnLimpiar.setDisabled(false);
		} catch (Exception e) {
			// FacesContext.getCurrentInstance().addMessage("", new
			// FacesMessage(FacesMessage.SEVERITY_ERROR,e.getMessage(), ""));
		}
		return "";
	}
	
	public String getTransaccionCuenta(){
		
		try {			
			String cueNumero = txtNumeroCuenta.getValue().toString().trim();
			log.info("Cuenta encontrada: " + cueNumero);
			if(cueNumero != null || !cueNumero.isEmpty()){
				log.info("Valida el numero de la cuenta");
				//if(lastransacciones == null){
					log.info("Las transacciones se encuentran nulas.");
					lastransacciones = new ArrayList<>();
					lastransacciones = delegadoDeNegocio.consultarTransaccionPorCuenta(cueNumero);
				//}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}


	public InputText getTxtNumeroCuenta() {
		return txtNumeroCuenta;
	}


	public void setTxtNumeroCuenta(InputText txtNumeroCuenta) {
		this.txtNumeroCuenta = txtNumeroCuenta;
	}


	public InputText getTxtSaldoCuenta() {
		return txtSaldoCuenta;
	}


	public void setTxtSaldoCuenta(InputText txtSaldoCuenta) {
		this.txtSaldoCuenta = txtSaldoCuenta;
	}


	public InputText getTxtIdCliente() {
		return txtIdCliente;
	}


	public void setTxtIdCliente(InputText txtIdCliente) {
		this.txtIdCliente = txtIdCliente;
	}


	public InputText getTxtNombreCliente() {
		return txtNombreCliente;
	}


	public void setTxtNombreCliente(InputText txtNombreCliente) {
		this.txtNombreCliente = txtNombreCliente;
	}


	public InputText getTxtEmailCliente() {
		return txtEmailCliente;
	}


	public void setTxtEmailCliente(InputText txtEmailCliente) {
		this.txtEmailCliente = txtEmailCliente;
	}


	public InputText getTxtTelefonoCliente() {
		return txtTelefonoCliente;
	}


	public void setTxtTelefonoCliente(InputText txtTelefonoCliente) {
		this.txtTelefonoCliente = txtTelefonoCliente;
	}


	public InputText getTxtValorTransaccion() {
		return txtValorTransaccion;
	}


	public void setTxtValorTransaccion(InputText txtValorTransaccion) {
		this.txtValorTransaccion = txtValorTransaccion;
	}


	public SelectOneMenu getSomUsuarios() {
		return somUsuarios;
	}


	public void setSomUsuarios(SelectOneMenu somUsuarios) {
		this.somUsuarios = somUsuarios;
	}


	public List<SelectItem> getLosUsuarioItems() {
		
		if(losUsuariosItems == null){
			
			List<Usuarios> listUsuarios;
			try {
				losUsuariosItems = new ArrayList<>();
				listUsuarios = delegadoDeNegocio.consultarTodosUsuarios();
				for (Usuarios usuarios : listUsuarios) {
					losUsuariosItems.add(new SelectItem(usuarios.getUsuCedula(),usuarios.getUsuCedula() + " - "+ usuarios.getUsuNombre()));
				}				
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
		
		return losUsuariosItems;
	}


	public void setLosUsuarioItems(List<SelectItem> losUsuariosItems) {
		this.losUsuariosItems = losUsuariosItems;
	}


	public CommandButton getBtnConsignar() {
		return btnConsignar;
	}


	public void setBtnConsignar(CommandButton btnConsignar) {
		this.btnConsignar = btnConsignar;
	}


	public CommandButton getBtnRetirar() {
		return btnRetirar;
	}


	public void setBtnRetirar(CommandButton btnRetirar) {
		this.btnRetirar = btnRetirar;
	}


	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}


	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}


	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}


	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public List<TransaccionDTO> getLastransacciones() {		
		return lastransacciones;
	}

	public void setLastransacciones(List<TransaccionDTO> lastransacciones) {
		this.lastransacciones = lastransacciones;
	}
	
	
	
	

}

package co.edu.usbcali.demo.delegado;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import co.edu.usbcali.demo.dto.TransaccionDTO;
import co.edu.usbcali.demo.logica.IClienteLogica;
import co.edu.usbcali.demo.logica.ICuentasLogica;
import co.edu.usbcali.demo.logica.ITipoDocumentoLogica;
import co.edu.usbcali.demo.logica.ITiposUsuariosLogica;
import co.edu.usbcali.demo.logica.ITransaccionesLogica;
import co.edu.usbcali.demo.logica.IUsuariosLogica;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;
import co.edu.usbcali.demo.utilidades.IUtilidades;

@Scope("singleton")
@Component("delegadoDeNegocio")
public class DelegadoDeNegocio implements IDelegadoDeNegocio {
	
	@Autowired
	private IUtilidades<DelegadoDeNegocio> utilidades;
	
	@Autowired
	private IClienteLogica clienteLogica;
	
	@Autowired
	private ITipoDocumentoLogica tipoDocumentoLogica;
	
	@Autowired
	private ICuentasLogica cuentaLogica;
	
	@Autowired
	private ITiposUsuariosLogica tipoUsuarios;
	
	@Autowired
	private ITransaccionesLogica transaccionesLogica;
	
	@Autowired
	private IUsuariosLogica usuariosLogica;

	@Override
	public void grabarClientes(Clientes clientes) throws Exception {
		clienteLogica.grabar(clientes);
	}

	@Override
	public void modificarClientes(Clientes clientes) throws Exception {
		clienteLogica.modificar(clientes);
	}

	@Override
	public void borrarClientes(Clientes clientes) throws Exception {
		clienteLogica.borrar(clientes);
	}

	@Override
	public Clientes consultarClinetePorId(long cliId) throws Exception {
		return clienteLogica.consultarClinetePorId(cliId);
	}

	@Override
	public List<Clientes> consultarTodosClientes() throws Exception {
		return clienteLogica.consultarTodos();
	}

	@Override
	public void grabarTiposDocumentos(TiposDocumentos entity) throws Exception {
		tipoDocumentoLogica.grabar(entity);

	}

	@Override
	public void modificarTiposDocumentos(TiposDocumentos entity) throws Exception {
		tipoDocumentoLogica.modificar(entity);
	}

	@Override
	public void borrarTiposDocumentos(TiposDocumentos entity) throws Exception {
		tipoDocumentoLogica.borrar(entity);

	}

	@Override
	public TiposDocumentos consultarTipoDocumentoId(long tdocCodigo) throws Exception {
		return tipoDocumentoLogica.consultarTipoDocumentoPorId(tdocCodigo);
	}

	@Override
	public List<TiposDocumentos> consultarTodosTiposDocumentos() throws Exception {		
		return tipoDocumentoLogica.consultarTodos();
	}

	@Override
	public Cuentas consultarCuentaPorId(String cueNumero) throws Exception {
		return cuentaLogica.consultarCuentaPorId(cueNumero);
	}

	@Override
	public void consignar(long cliId, String cueNumero, long usuCedula, double valor) throws Exception {
		transaccionesLogica.consignar(cliId, cueNumero, usuCedula, valor);		
	}

	@Override
	public void retirar(long cliId, String cueNumero, long usuCedula, double valor) throws Exception {
		transaccionesLogica.retirar(cliId, cueNumero, usuCedula, valor);	
		
	}

	@Override
	public void grabarUsuario(Usuarios usuarios) throws Exception {
		usuariosLogica.grabar(usuarios);		
	}

	@Override
	public void modificarUsuario(Usuarios usuarios) throws Exception {
		usuariosLogica.modificar(usuarios);		
	}

	@Override
	public void borrarUsuario(Usuarios usuarios) throws Exception {
		usuariosLogica.borrar(usuarios);		
	}

	@Override
	public Usuarios consultarUsuariosPorId(long usuCedula) throws Exception {
		return usuariosLogica.consultarUsuariosPorId(usuCedula);
	}

	@Override
	public List<Usuarios> consultarTodosUsuarios() throws Exception {
		return usuariosLogica.consultarTodos();
	}

	@Override
	public void grabarTipoUsuario(TiposUsuarios tiposUsuarios) throws Exception {
		tipoUsuarios.grabar(tiposUsuarios);
	}

	@Override
	public void modificarTipoUsuario(TiposUsuarios tiposUsuarios) throws Exception {
		tipoUsuarios.modificar(tiposUsuarios);
	}

	@Override
	public void borrarTipoUsuario(TiposUsuarios tiposUsuarios) throws Exception {
		tipoUsuarios.borrar(tiposUsuarios);
	}

	@Override
	public TiposUsuarios consultarTipoUsuarioPorId(long tusuCodigo) throws Exception {
		return tipoUsuarios.consultarTipoUsuarioPorId(tusuCodigo);
	}

	@Override
	public List<TiposUsuarios> consultarTodosTipoUsuario() throws Exception {
		return tipoUsuarios.consultarTodos();
	}

	@Override
	public boolean isNumerico(String variable) throws Exception {
		return utilidades.isNumerico(variable);
	}

	@Override
	public List<TransaccionDTO> consultarTransaccionPorCuenta(String cueNumero) throws Exception {
		return transaccionesLogica.consultarTransaccionPorCuenta(cueNumero);
	}

}

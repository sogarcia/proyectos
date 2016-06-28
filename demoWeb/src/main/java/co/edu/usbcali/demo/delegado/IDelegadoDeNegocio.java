package co.edu.usbcali.demo.delegado;

import java.util.List;

import co.edu.usbcali.demo.dto.TransaccionDTO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

public interface IDelegadoDeNegocio {
	
	public void grabarClientes(Clientes clientes)throws Exception;
	public void modificarClientes(Clientes clientes)throws Exception;
	public void borrarClientes(Clientes clientes)throws Exception;
	public Clientes consultarClinetePorId(long cliId)throws Exception;
	public List<Clientes> consultarTodosClientes()throws Exception;
	
	public void grabarTiposDocumentos(TiposDocumentos entity)throws  Exception;
	public void modificarTiposDocumentos(TiposDocumentos entity)throws  Exception;
	public void borrarTiposDocumentos(TiposDocumentos entity)throws  Exception;
	public TiposDocumentos consultarTipoDocumentoId(long tdocCodigo)throws  Exception;
	public List<TiposDocumentos> consultarTodosTiposDocumentos()throws Exception;
	
	public void grabarUsuario(Usuarios usuarios) throws Exception;
	public void modificarUsuario(Usuarios usuarios) throws Exception;
	public void borrarUsuario(Usuarios usuarios) throws Exception;
	public Usuarios consultarUsuariosPorId(long usuCedula) throws Exception;
	public List<Usuarios> consultarTodosUsuarios() throws Exception;
	
	public void grabarTipoUsuario(TiposUsuarios tiposUsuarios) throws Exception;	
	public void modificarTipoUsuario(TiposUsuarios tiposUsuarios) throws Exception;	
	public void borrarTipoUsuario(TiposUsuarios tiposUsuarios) throws Exception;
	public TiposUsuarios consultarTipoUsuarioPorId(long tusuCodigo) throws Exception;	
	public List<TiposUsuarios> consultarTodosTipoUsuario() throws Exception;
	
	public Cuentas consultarCuentaPorId(String cueNumero) throws Exception;	
	public void consignar(long cliId, String cueNumero, long usuCedula, double valor)throws Exception;	
	public void retirar(long cliId, String cueNumero, long usuCedula, double valor)throws Exception;
	public List<TransaccionDTO> consultarTransaccionPorCuenta(String cueNumero) throws Exception;
	
	public boolean isNumerico(String variable) throws Exception;
	
	


}

package co.edu.usbcali.demo.logica;

import java.util.List;

import co.edu.usbcali.demo.modelo.Cuentas;

public interface ICuentasLogica {
	
	public void grabar(Cuentas cuenta) throws Exception;
	public void modificar(Cuentas cuenta) throws Exception;
	public void borrar(Cuentas cuenta) throws Exception;
	public Cuentas consultarCuentaPorId(String cueNumero) throws Exception;
	public List<Cuentas> consultarTodos() throws Exception;

}

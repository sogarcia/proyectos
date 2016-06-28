package co.edu.usbcali.demo.logica;

import java.util.List;

import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;


public interface IRetirosLogica {
	
	
	public void grabar(Retiros retiro) throws Exception;
	public void modificar(Retiros retiro) throws Exception;
	public void borrar(Retiros retiro) throws Exception;
	public Retiros consultarRetirosPorId(RetirosId id) throws Exception;
	public List<Retiros> consultarTodos() throws Exception;
	public List<Retiros> consultarRetirosporCuenta(String cueNumero) throws Exception;

}

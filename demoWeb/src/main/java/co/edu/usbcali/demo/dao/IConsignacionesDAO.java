package co.edu.usbcali.demo.dao;

import java.util.List;

import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;

public interface IConsignacionesDAO {
	
	public void grabar(Consignaciones consignaciones);
	public void modificar(Consignaciones consignaciones);
	public void borrar(Consignaciones consignaciones);
	public Consignaciones consultarConsignacionesPorId(ConsignacionesId id);
	public List<Consignaciones> consultarTodos();
	public List<Consignaciones> consultarConsignacionPorCuenta(String cueNumero);

}

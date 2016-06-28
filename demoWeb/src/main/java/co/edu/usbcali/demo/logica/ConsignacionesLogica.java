package co.edu.usbcali.demo.logica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.IConsignacionesDAO;
import co.edu.usbcali.demo.dao.ICuentaDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;
import co.edu.usbcali.demo.utilidades.IUtilidades;

@Service
@Scope("singleton")
public class ConsignacionesLogica implements IConsignacionesLogica {
	
	
	@Autowired
	private IUtilidades<Consignaciones> utilidad;
	
	@Autowired
	private IUsuariosDAO usuarioDAO;
	
	@Autowired
	private ICuentaDAO cuentasDAO;
	
	@Autowired
	private IConsignacionesDAO consignacionDAO;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Consignaciones consignaciones) throws Exception {
		
		utilidad.validaModeloGenerico(consignaciones);
		
		if(consignaciones == null){
			throw new Exception("No es posible realizar la operacion consignacion es nula");
		}
		
		if(consignaciones.getCuentas() instanceof Cuentas){
			Cuentas entityCuenta = cuentasDAO.consultarCuentaPorId(consignaciones.getCuentas().getCueNumero());
			if (entityCuenta == null){
				throw new Exception("La cuenta n° " +consignaciones.getCuentas().getCueNumero()+"  asociada a la consignacion no existe");
			}
			//Double saldoFinal = Double.parseDouble(entityCuenta.getCueSaldo().toString()) - Double.parseDouble(consignaciones.getConValor().toString());
			//if(saldoFinal < 0){
				//throw new Exception("La cuenta no tiene saldo disponible para realizar el retiro");
			//}
			consignaciones.setCuentas(entityCuenta);
		}else{
			throw new Exception("No existe cuenta para realizar el retiro");
		}
		if(consignaciones.getUsuarios() instanceof Usuarios){
			Usuarios entityUsuario = usuarioDAO.consultarUsuarioPorId(consignaciones.getUsuarios().getUsuCedula());
			if(entityUsuario == null){
				throw new Exception("El usuario no existe");
			}
			consignaciones.setUsuarios(entityUsuario);
		}		
		
		consignacionDAO.grabar(consignaciones);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Consignaciones consignaciones) throws Exception {
		
		utilidad.validaModeloGenerico(consignaciones);
		
		if(consignaciones == null){
			throw new Exception("No es posible realizar la operacion consignacion es nula");
		}
		
		if(consignaciones.getCuentas() instanceof Cuentas){
			Cuentas entityCuenta = cuentasDAO.consultarCuentaPorId(consignaciones.getCuentas().getCueNumero());
			if (entityCuenta == null){
				throw new Exception("La cuenta n° " +consignaciones.getCuentas().getCueNumero()+"  asociada a la consignacion no existe");
			}
			//Double saldoFinal = Double.parseDouble(entityCuenta.getCueSaldo().toString()) - Double.parseDouble(consignaciones.getConValor().toString());
			//if(saldoFinal < 0){
				//throw new Exception("La cuenta no tiene saldo disponible para realizar el retiro");
			//}
			consignaciones.setCuentas(entityCuenta);
		}else{
			throw new Exception("No existe cuenta para realizar la consignacion");
		}
		if(consignaciones.getUsuarios() instanceof Usuarios){
			Usuarios entityUsuario = usuarioDAO.consultarUsuarioPorId(consignaciones.getUsuarios().getUsuCedula());
			if(entityUsuario == null){
				throw new Exception("El usuario no existe");
			}
			consignaciones.setUsuarios(entityUsuario);
		}		
		
		consignacionDAO.modificar(consignaciones);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Consignaciones consignaciones) throws Exception {
		if (consignaciones == null) {
			throw new Exception("No es posible realizar la operacion consignacion es nula");
		}
		
		consignacionDAO.borrar(consignaciones);

	}

	@Override
	@Transactional(readOnly=true)
	public Consignaciones consultarConsignacionesPorId(ConsignacionesId id) throws Exception {
		return consignacionDAO.consultarConsignacionesPorId(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Consignaciones> consultarTodos() throws Exception {
		return consignacionDAO.consultarTodos();
	}

	@Override
	public List<Consignaciones> consultarConsignacionPorCuenta(String cueNumero) throws Exception {
		return consignacionDAO.consultarConsignacionPorCuenta(cueNumero);
	}

}

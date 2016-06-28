package co.edu.usbcali.demo.logica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.ICuentaDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.utilidades.IUtilidades;

@Scope("singleton")
@Service
public class CuentasLogica implements ICuentasLogica {
	
	@Autowired
	private IUtilidades<Cuentas> utilidad;
	
	@Autowired
	private ICuentaDAO cuentaDAO;
	
	@Autowired
	private IClienteDAO clienteDAO;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Cuentas cuenta) throws Exception {
		
		utilidad.validaModeloGenerico(cuenta);
		
		if (cuenta == null){
			throw new Exception("La cuenta es nula");
		}
		
		Clientes entity = clienteDAO.consultarClinetePorId(cuenta.getClientes().getCliId());
		
		if (entity == null){
			throw new Exception("El cliente asociado a la cuenta no existe ");
		}		
		cuenta.setClientes(entity);
		cuentaDAO.grabar(cuenta);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Cuentas cuenta) throws Exception {
		utilidad.validaModeloGenerico(cuenta);
		
		if (cuenta == null){
			throw new Exception("La cuenta es nula");
		}
		
		Clientes entity = clienteDAO.consultarClinetePorId(cuenta.getClientes().getCliId());
		
		if (entity == null){
			throw new Exception("El cliente asociado a la cuenta no existe ");
		}		
		cuenta.setClientes(entity);
		cuentaDAO.modificar(cuenta);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Cuentas cuenta) throws Exception {
		
		if (cuenta == null){
			throw new Exception("La cuenta es nula");
		}
		
		Clientes entity = clienteDAO.consultarClinetePorId(cuenta.getClientes().getCliId());
		
		if (entity == null){
			throw new Exception("El cliente asociado a la cuenta no existe ");
		}		
		cuenta.setClientes(entity);
		cuentaDAO.borrar(cuenta);

	}

	@Override
	@Transactional(readOnly=true)
	public Cuentas consultarCuentaPorId(String cueNumero) throws Exception {
		return cuentaDAO.consultarCuentaPorId(cueNumero);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Cuentas> consultarTodos() throws Exception {
		return cuentaDAO.consultarTodos();
	}

}

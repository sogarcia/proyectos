package co.edu.usbcali.demo.logica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.ICuentaDAO;
import co.edu.usbcali.demo.dao.IRetirosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;
import co.edu.usbcali.demo.utilidades.IUtilidades;

@Service
@Scope("singleton")
public class RetirosLogica implements IRetirosLogica {
	
	@Autowired
	private IUtilidades<Retiros> utilidad;
	
	@Autowired
	private IUsuariosDAO usuarioDAO;
	
	@Autowired
	private ICuentaDAO cuentasDAO;
	
	@Autowired
	private IRetirosDAO retirosDAO;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Retiros retiro) throws Exception {
		
		utilidad.validaModeloGenerico(retiro);
		
		if(retiro == null){
			throw new Exception("No es posible realizar la operacion el retiro se encuentra nulo");
		}
		
		if(retiro.getCuentas() instanceof Cuentas){
			Cuentas entityCuenta = cuentasDAO.consultarCuentaPorId(retiro.getCuentas().getCueNumero());
			if (entityCuenta == null){
				throw new Exception("La cuenta n° " +retiro.getCuentas().getCueNumero()+"  asociada al retiro no existe");
			}
			Double saldoFinal = Double.parseDouble(entityCuenta.getCueSaldo().toString()) - Double.parseDouble(retiro.getRetValor().toString());
			if(saldoFinal < 0){
				throw new Exception("La cuenta no tiene saldo disponible para realizar el retiro");
			}
			retiro.setCuentas(entityCuenta);
		}else{
			throw new Exception("No existe cuenta para realizar el retiro");
		}
		if(retiro.getUsuarios() instanceof Usuarios){
			Usuarios entityUsuario = usuarioDAO.consultarUsuarioPorId(retiro.getUsuarios().getUsuCedula());
			if(entityUsuario == null){
				throw new Exception("El usuario no existe");
			}
			retiro.setUsuarios(entityUsuario);
		}		
		
		retirosDAO.grabar(retiro);
		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Retiros retiro) throws Exception {
		
		utilidad.validaModeloGenerico(retiro);
		
		if(retiro == null){
			throw new Exception("No es posible realizar la operacion el retiro se encuentra nulo");
		}
		
		if(retiro.getCuentas() instanceof Cuentas){
			Cuentas entityCuenta = cuentasDAO.consultarCuentaPorId(retiro.getCuentas().getCueNumero());
			if (entityCuenta == null){
				throw new Exception("La cuenta n° " +retiro.getCuentas().getCueNumero()+"  asociada al retiro no existe");
			}
			Double saldoFinal = Double.parseDouble(entityCuenta.getCueSaldo().toString()) - Double.parseDouble(retiro.getRetValor().toString());
			if(saldoFinal < 0){
				throw new Exception("La cuenta no tiene saldo disponible para realizar el retiro");
			}
			retiro.setCuentas(entityCuenta);
		}else{
			throw new Exception("No existe cuenta para realizar el retiro");
		}
		if(retiro.getUsuarios() instanceof Usuarios){
			Usuarios entityUsuario = usuarioDAO.consultarUsuarioPorId(retiro.getUsuarios().getUsuCedula());
			if(entityUsuario == null){
				throw new Exception("El usuario no existe");
			}
			retiro.setUsuarios(entityUsuario);
		}
		
		retirosDAO.modificar(retiro);
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Retiros retiro) throws Exception {
		
		if (retiro == null) {
			throw new Exception("No es posible realizar la operacion el retiro se encuentra nulo");
		}
		
		retirosDAO.borrar(retiro);

	}

	@Override
	@Transactional(readOnly=true)
	public Retiros consultarRetirosPorId(RetirosId id) throws Exception {
		return retirosDAO.consultarRetirosPorId(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Retiros> consultarTodos() throws Exception {
		return retirosDAO.consultarTodos();
	}

	@Override
	public List<Retiros> consultarRetirosporCuenta(String cueNumero) throws Exception {
		return retirosDAO.consultarRetirosporCuenta(cueNumero);
	}

}

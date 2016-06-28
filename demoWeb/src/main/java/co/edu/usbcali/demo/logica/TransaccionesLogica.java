package co.edu.usbcali.demo.logica;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dto.TransaccionDTO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;

@Service
@Scope("singleton")
public class TransaccionesLogica implements ITransaccionesLogica {
	
	private static final Logger log = LoggerFactory.getLogger(TransaccionesLogica.class);
	
	@Autowired
	private IClienteLogica clienteLogica;
	
	@Autowired
	private IUsuariosLogica usuarioLogica;
	
	@Autowired
	private ICuentasLogica cuentaLogica;
	
	@Autowired
	private IConsignacionesLogica consignacionLogica;
	
	@Autowired
	private IRetirosLogica retiroslogica;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void consignar(long cliId, String cueNumero, long usuCedula, double valor) throws Exception {
		
		double saldoCuenta = 0;
		
		Clientes cliente = new Clientes();		
		cliente = clienteLogica.consultarClinetePorId(cliId);
		
		Cuentas cuenta = new Cuentas();
		cuenta = cuentaLogica.consultarCuentaPorId(cueNumero);
		
		Usuarios usuario = new Usuarios();		
		usuario = usuarioLogica.consultarUsuariosPorId(usuCedula);
				
		if (cuenta == null){
			throw new Exception("La cuenta no existe");
		}
		if(!cuenta.getCueActiva().trim().equals("S")){
			throw new Exception("La cuenta se encuentra inactiva");
		}
		
		if (cliente == null){
			throw new Exception("El cliente no existe");
		}
		if (usuario == null){
			throw new Exception("El usuario no existe");
		}
		
		if (valor <= 0){
			throw new Exception("No se puede consignar valores menores o iguales a cero");
		}	
		saldoCuenta = cuenta.getCueSaldo().doubleValue() + valor;
		BigDecimal bd = new BigDecimal(saldoCuenta);
		
		log.info("Precison obtenido de BigDecimal: " + bd.precision() + " valor plainString de bd " + bd.toPlainString() );
		
		if(bd.precision() > 8){
			throw new Exception("La consignacion excede saldo maximo permitido para la cuenta");
		}		
		cuenta.setCueSaldo(new BigDecimal(saldoCuenta));
		
		//Buscar una mejor opcion para manejar los codigos de las consignaciones
		long conCodigo = consignacionLogica.consultarTodos().size() + 1;
		
		ConsignacionesId consiId = new ConsignacionesId(conCodigo, cueNumero);
		
		Consignaciones consignacion = new Consignaciones();
		consignacion.setConDescripcion("Consignacion");
		consignacion.setConFecha(new Date());
		consignacion.setConValor(new BigDecimal(valor));
		consignacion.setCuentas(cuenta);
		consignacion.setId(consiId);
		consignacion.setUsuarios(usuario);		
		
		consignacionLogica.grabar(consignacion);
		cuentaLogica.modificar(cuenta);			
		
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void retirar(long cliId, String cueNumero, long usuCedula, double valor) throws Exception {
		double saldoCuenta = 0;
		
		Clientes cliente = new Clientes();		
		cliente = clienteLogica.consultarClinetePorId(cliId);
		
		Cuentas cuenta = new Cuentas();
		cuenta = cuentaLogica.consultarCuentaPorId(cueNumero);
		
		Usuarios usuario = new Usuarios();		
		usuario = usuarioLogica.consultarUsuariosPorId(usuCedula);
				
		if (cuenta == null){
			throw new Exception("La cuenta no existe");
		}
		if(!cuenta.getCueActiva().trim().equals("S")){
			throw new Exception("La cuenta se encuentra inactiva");
		}
		
		if (cliente == null){
			throw new Exception("El cliente no existe");
		}
		if (usuario == null){
			throw new Exception("El usuario no existe");
		}
		
		if (valor <= 0){
			throw new Exception("No se puede retirar valores menores o iguales a cero");
		}	
		saldoCuenta = cuenta.getCueSaldo().doubleValue() - valor;
		
		if(saldoCuenta < 0){
			throw new Exception("No tiene fondos suficientes para realizar la transaccion");
		}
		
		BigDecimal bd = new BigDecimal(saldoCuenta);
		
		log.info("Precison obtenido de BigDecimal: " + bd.precision() + " valor plainString de bd " + bd.toPlainString() );
		
		if(bd.precision() > 8){
			throw new Exception("La consignacion excede saldo maximo permitido para la cuenta");
		}		
		cuenta.setCueSaldo(new BigDecimal(saldoCuenta));
		
		//Buscar una mejor opcion para manejar los codigos de las consignaciones
		long retCodigo = retiroslogica.consultarTodos().size() + 1;
		
		RetirosId retId = new RetirosId(retCodigo, cueNumero);
		
		Retiros retiros = new Retiros();
		retiros.setRetDescripcion("Retiros");
		retiros.setRetFecha(new Date());
		retiros.setRetValor(new BigDecimal(valor));
		retiros.setCuentas(cuenta);
		retiros.setId(retId);
		retiros.setUsuarios(usuario);		
		
		retiroslogica.grabar(retiros);
		cuentaLogica.modificar(cuenta);		

	}

	@Override
	@Transactional(readOnly=true)
	public List<TransaccionDTO> consultarTransaccionPorCuenta(String cueNumero) throws Exception {
		
		List<Consignaciones> lasConsignaciones = new ArrayList<>();
		List<Retiros> losRetiros  = new ArrayList<>();
		List<TransaccionDTO> lasTransacciones = null;
		
		try {
			TransaccionDTO transaccionDTO;
			lasConsignaciones = consignacionLogica.consultarConsignacionPorCuenta(cueNumero);
			losRetiros = retiroslogica.consultarRetirosporCuenta(cueNumero);
			
			if(lasConsignaciones != null && lasConsignaciones.size() > 0){
				lasTransacciones = new ArrayList<>();
				//lasConsignaciones = new ArrayList<>();
				for (Consignaciones entity : lasConsignaciones) {
					transaccionDTO = new TransaccionDTO();
					transaccionDTO.setDescripcionTransaccion(entity.getConDescripcion());
					transaccionDTO.setTipoTransaccion("Consignacion");
					transaccionDTO.setFechaTransaccion(entity.getConFecha().toString());
					transaccionDTO.setValorTransaccion(entity.getConValor().toString());
					
					lasTransacciones.add(transaccionDTO);
				}				
			}
			if(lasTransacciones == null){
				lasTransacciones = new ArrayList<>();
			}
			if(losRetiros != null && losRetiros.size() > 0){				
				//losRetiros = new ArrayList<>();
				for (Retiros entity : losRetiros) {
					transaccionDTO = new TransaccionDTO();
					transaccionDTO.setDescripcionTransaccion(entity.getRetDescripcion());
					transaccionDTO.setTipoTransaccion("Retiro");
					transaccionDTO.setFechaTransaccion(entity.getRetFecha().toString());
					transaccionDTO.setValorTransaccion(entity.getRetValor().toString());
					
					lasTransacciones.add(transaccionDTO);
				}				
			}
			
			
		} catch (Exception e) {
			log.info("No se encontro transacciones para mostrar, verificar consulta transacciones DTO");
			e.printStackTrace();
		}
		
		
		return lasTransacciones;
	}

}

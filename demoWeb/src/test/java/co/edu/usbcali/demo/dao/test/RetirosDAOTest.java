package co.edu.usbcali.demo.dao.test;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.ICuentaDAO;
import co.edu.usbcali.demo.dao.IRetirosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;


//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class RetirosDAOTest {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteDAOTest.class);
	
	@Autowired
	private IRetirosDAO retirosDAO;
	
	@Autowired 
	private IUsuariosDAO usuarioDAO;
	
	@Autowired 
	private ICuentaDAO cuentaDAO;
	
	private RetirosId retId;
	
	private Long retCodigo = 22l;
	
	private String cueNumero = "4008-5305-0010";

	/*@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {		
		Retiros retiro = new Retiros();
		retiro.setRetDescripcion("Retiro bancario efectivo");
		retiro.setRetFecha(new Date());
		retiro.setRetValor(new BigDecimal(56000));
		
		List<Cuentas> lCuentas = cuentaDAO.consultarTodos();
		Cuentas cuenta = null;
		if (lCuentas != null && lCuentas.size() > 0){
			cuenta = lCuentas.get(0);
		}
		List<Usuarios> lUsuario = usuarioDAO.consultarTodos();
		Usuarios usuario = null;
		if (lUsuario != null && lUsuario.size() > 0){
			usuario = lUsuario.get(0);
		}
		retiro.setCuentas(cuenta);
		retiro.setUsuarios(usuario);
		
		RetirosId retiroId = new RetirosId(16l,cuenta.getCueNumero());
		
		retiro.setId(retiroId);
		retirosDAO.grabar(retiro);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		retId = new RetirosId(retCodigo, cueNumero);
		Retiros retiros = retirosDAO.consultarRetirosPorId(retId);
		assertNotNull("Retiros se encuentra nula",retiros);
		retiros.setRetFecha(new Date());
		retiros.setRetValor(new BigDecimal(20000));
		log.info("Modifca el # de retiro: " + retiros.getId());
		retirosDAO.modificar(retiros);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		retId = new RetirosId(retCodigo, cueNumero);
		Retiros retiros = retirosDAO.consultarRetirosPorId(retId);
		assertNotNull("Transaccion de retiro no existe",retiros);		
		retirosDAO.borrar(retiros);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Retiros> losRetiros = retirosDAO.consultarTodos();
		for (Retiros retiros : losRetiros) {
			log.info("Retiro de: " + retiros.getRetDescripcion() + " Valor: "+retiros.getRetValor());
		}
	}*/

}

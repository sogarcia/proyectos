package co.edu.usbcali.demo.dao.test;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
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

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.ICuentaDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CuentaDAOTest {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteDAOTest.class);
		
	@Autowired 
	private ICuentaDAO cuentaDAO;
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	private String cueNumero = "4008-5305-0090";
	
	private Long cliId = 101234L;
	
	/*@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		Cuentas cuenta = new Cuentas();
		cuenta.setCueActiva("S");
		cuenta.setCueClave("1234");
		cuenta.setCueNumero(cueNumero);
		cuenta.setCueSaldo(new BigDecimal(120000));		
		Clientes cliente = clienteDAO.consultarClinetePorId(cliId);
		assertNotNull("Cliente no existe", cliente);
		cuenta.setClientes(cliente);
		
		cuentaDAO.grabar(cuenta);
	}	
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		Cuentas cuentas = cuentaDAO.consultarCuentaPorId(cueNumero);
		assertNotNull("Cuenta No Existe", cuentas);
		log.info(cuentas.getCueNumero());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		Cuentas cuentas = cuentaDAO.consultarCuentaPorId(cueNumero);
		assertNotNull("Cuenta No Existe", cuentas);
		cuentas.setCueClave("12345");
		log.info("Modifca la cuenta # " + cuentas.getCueNumero());
		cuentaDAO.modificar(cuentas);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Cuentas> lasCuentas = cuentaDAO.consultarTodos();
		for (Cuentas cuentas : lasCuentas) {
			log.info("Cuenta: " + cuentas.getCueNumero() + " Saldo: "+cuentas.getCueSaldo());
		}
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		Cuentas cuentas = cuentaDAO.consultarCuentaPorId(cueNumero);
		//assertNotNull("El cliente no Existe",cuentas);		
		cuentaDAO.borrar(cuentas);
	}*/

}

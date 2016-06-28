package co.edu.usbcali.demo.logica.test;

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

import co.edu.usbcali.demo.dao.test.ClienteDAOTest;
import co.edu.usbcali.demo.logica.IClienteLogica;
import co.edu.usbcali.demo.logica.ICuentasLogica;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CuentaLogicaTest {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteDAOTest.class);
	
	@Autowired 
	private ICuentasLogica cuentaLogica;
	
	@Autowired
	private IClienteLogica clienteLogica;
	
	private String cueNumero = "4008-5305-0034";
	
	private Long cliId = 351234L;
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception{
		
		Cuentas cuenta = new Cuentas();
		cuenta.setCueActiva("S");
		cuenta.setCueClave("1234");
		cuenta.setCueNumero(cueNumero);
		cuenta.setCueSaldo(new BigDecimal(120000));	
		Clientes cliente = clienteLogica.consultarClinetePorId(cliId);
		assertNotNull("Cliente no existe", cliente);
		cuenta.setClientes(cliente);
		
		cuentaLogica.grabar(cuenta);
	}	
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception{
		Cuentas cuentas = cuentaLogica.consultarCuentaPorId(cueNumero);
		assertNotNull("Cuenta No Existe", cuentas);
		log.info(cuentas.getCueNumero());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception{
		Cuentas cuentas = cuentaLogica.consultarCuentaPorId(cueNumero);
		assertNotNull("Cuenta No Existe", cuentas);
		cuentas.setCueClave("12345");
		log.info("Modifca la cuenta # " + cuentas.getCueNumero());
		cuentaLogica.modificar(cuentas);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception{
		List<Cuentas> lasCuentas = cuentaLogica.consultarTodos();
		for (Cuentas cuentas : lasCuentas) {
			log.info("Cuenta: " + cuentas.getCueNumero() + " Saldo: "+cuentas.getCueSaldo());
		}
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception{
		Cuentas cuentas = cuentaLogica.consultarCuentaPorId(cueNumero);
		assertNotNull("El cliente no Existe",cuentas);		
		cuentaLogica.borrar(cuentas);
	}

}

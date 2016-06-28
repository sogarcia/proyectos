package co.edu.usbcali.demo.logica.test;

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


import co.edu.usbcali.demo.logica.ICuentasLogica;
import co.edu.usbcali.demo.logica.IRetirosLogica;
import co.edu.usbcali.demo.logica.IUsuariosLogica;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class RetirosLogicaTest {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteLogicaTest.class);
	
	@Autowired
	private IRetirosLogica retirosLogica;
	
	@Autowired 
	private IUsuariosLogica usuariosLogica;
	
	@Autowired
	private ICuentasLogica cuentaLogica;
	
	private RetirosId retirosId;
	
	private Long usuCedula = 25l;
	
	private Long retCodigo = 17l;
	
	private String cueNumero = "4008-5305-0010";

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception{
		
		Retiros retiros = new Retiros();		
		retiros.setRetDescripcion("Retiro millonario");
		retiros.setRetValor(new BigDecimal(67000));
		retiros.setRetFecha(new Date());
		
		retirosId = new RetirosId(retCodigo, cueNumero);		
		retiros.setId(retirosId);
		
		Usuarios usuario = usuariosLogica.consultarUsuariosPorId(usuCedula);
		Cuentas cuenta =  cuentaLogica.consultarCuentaPorId(cueNumero);

		retiros.setCuentas(cuenta);
		retiros.setUsuarios(usuario);
		
		retirosLogica.grabar(retiros);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception{
		retirosId = new RetirosId(retCodigo, cueNumero);
		Retiros retiro = retirosLogica.consultarRetirosPorId(retirosId);
		assertNotNull("Retiro No Existe", retiro);
		log.info(retiro.getRetDescripcion());
		}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception{
		retirosId = new RetirosId(retCodigo, cueNumero);
		Retiros retiros = retirosLogica.consultarRetirosPorId(retirosId);
		assertNotNull("Retiros No Existe", retiros);
		retiros.setRetDescripcion("Retiro rechazado");
		
		retirosLogica.modificar(retiros);
		}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception{
		retirosId = new RetirosId(retCodigo, cueNumero);
		Retiros retiros = retirosLogica.consultarRetirosPorId(retirosId);
		assertNotNull("El retiros no Existe",retiros);
		
		retirosLogica.borrar(retiros);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception{
		List<Retiros> losRetiros = retirosLogica.consultarTodos();
		for (Retiros retiros : losRetiros) {
			log.info(retiros.getRetDescripcion());
		}
	}

}

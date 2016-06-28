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

import co.edu.usbcali.demo.dao.test.ClienteDAOTest;
import co.edu.usbcali.demo.logica.IConsignacionesLogica;
import co.edu.usbcali.demo.logica.ICuentasLogica;
import co.edu.usbcali.demo.logica.IUsuariosLogica;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ConsignacionesLogicaTest {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteDAOTest.class);
	
	@Autowired
	private IConsignacionesLogica consignacionesLogica;
	
	@Autowired 
	private IUsuariosLogica usuarioLogica;
	
	@Autowired 
	private ICuentasLogica cuentaLogica;	
	
	private Long conCodigo = 19l;
	
	private String cueNumero = "4008-5305-0065"; 
	
	private ConsignacionesId consId;

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception{		
		Consignaciones consignaciones = new Consignaciones();
		consignaciones.setConDescripcion("Consignacion bancaria en efectivo");
		consignaciones.setConFecha(new Date());
		consignaciones.setConValor(new BigDecimal(516000));
		
		List<Cuentas> lCuentas = cuentaLogica.consultarTodos();
		Cuentas cuenta = null;
		if (lCuentas != null && lCuentas.size() > 0){
			cuenta = lCuentas.get(0);
		}
		List<Usuarios> lUsuario = usuarioLogica.consultarTodos();
		Usuarios usuario = null;
		if (lUsuario != null && lUsuario.size() > 0){
			usuario = lUsuario.get(0);
		}
		consignaciones.setCuentas(cuenta);
		consignaciones.setUsuarios(usuario);
		
		ConsignacionesId consignacionId = new ConsignacionesId(conCodigo,cueNumero);
		
		consignaciones.setId(consignacionId);
		consignacionesLogica.grabar(consignaciones);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception{
		consId = new ConsignacionesId(conCodigo, cueNumero);
		Consignaciones consignaciones = consignacionesLogica.consultarConsignacionesPorId(consId);
		assertNotNull("Consignacion No Existe", consignaciones);
		log.info(consignaciones.getConDescripcion());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception{
		consId = new ConsignacionesId(conCodigo, cueNumero);
		Consignaciones consignaciones = consignacionesLogica.consultarConsignacionesPorId(consId);
		assertNotNull("La consignacion se encuentra nula",consignaciones);
		consignaciones.setConFecha(new Date());
		consignaciones.setConValor(new BigDecimal("20000"));
		log.info("Modifca la consignacion # " + consignaciones.getConDescripcion());
		consignacionesLogica.modificar(consignaciones);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception{
		consId = new ConsignacionesId(conCodigo, cueNumero);
		Consignaciones consignacion = consignacionesLogica.consultarConsignacionesPorId(consId);
		assertNotNull("La consignacion no Existe",consignacion);		
		consignacionesLogica.borrar(consignacion);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception{
		List<Consignaciones> lasConsignaciones = consignacionesLogica.consultarTodos();
		for (Consignaciones consignacion : lasConsignaciones) {
			log.info("Consignacion: " + consignacion.getConDescripcion() + " Valor: "+consignacion.getConValor());
		}
	}
	
	//@Test
	//@Transactional(readOnly=true)
	public void ftest() throws Exception{
		List<Consignaciones> lasConsignaciones = consignacionesLogica.consultarConsignacionPorCuenta(cueNumero);
		for (Consignaciones consignacion : lasConsignaciones) {
			log.info("Consignacion: " + consignacion.getConDescripcion() + " Valor: "+consignacion.getConValor());
		}
	}

}

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

import co.edu.usbcali.demo.dao.IConsignacionesDAO;
import co.edu.usbcali.demo.dao.ICuentaDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ConsignacionesDAOTest {

	private static final Logger log = LoggerFactory.getLogger(ClienteDAOTest.class);
	
	@Autowired
	private IConsignacionesDAO consignacionesDAO;
	
	@Autowired 
	private IUsuariosDAO usuarioDAO;
	
	@Autowired 
	private ICuentaDAO cuentaDAO;	
	
	private Long conCodigo = 1l;
	
	private String cueNumero = "4008-5305-0010"; 
	
	private ConsignacionesId consId;

	/*@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {		
		Consignaciones consignaciones = new Consignaciones();
		consignaciones.setConDescripcion("Consignacion bancaria en efectivo");
		consignaciones.setConFecha(new Date());
		consignaciones.setConValor(new BigDecimal(516000));
		
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
		consignaciones.setCuentas(cuenta);
		consignaciones.setUsuarios(usuario);
		
		ConsignacionesId consignacionId = new ConsignacionesId(conCodigo,cuenta.getCueNumero());
		
		consignaciones.setId(consignacionId);
		//consignacionesDAO.grabar(consignaciones);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		consId = new ConsignacionesId(conCodigo, cueNumero);
		Consignaciones consignaciones = consignacionesDAO.consultarConsignacionesPorId(consId);
		assertNotNull("Cuenta No Existe", consignaciones);
		log.info(consignaciones.getConDescripcion());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		consId = new ConsignacionesId(conCodigo, cueNumero);
		Consignaciones consignaciones = consignacionesDAO.consultarConsignacionesPorId(consId);
		assertNotNull("La consignacion se encuentra nula",consignaciones);
		consignaciones.setConFecha(new Date());
		consignaciones.setConValor(new BigDecimal("20000"));
		log.info("Modifca la consignacion # " + consignaciones.getConDescripcion());
		consignacionesDAO.modificar(consignaciones);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		consId = new ConsignacionesId(conCodigo, cueNumero);
		Consignaciones consignacion = consignacionesDAO.consultarConsignacionesPorId(consId);
		assertNotNull("La consignacion no Existe",consignacion);		
		consignacionesDAO.borrar(consignacion);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Consignaciones> lasConsignaciones = consignacionesDAO.consultarTodos();
		for (Consignaciones consignacion : lasConsignaciones) {
			log.info("Consignacion: " + consignacion.getConDescripcion() + " Valor: "+consignacion.getConValor());
		}
	}
*/
	
}

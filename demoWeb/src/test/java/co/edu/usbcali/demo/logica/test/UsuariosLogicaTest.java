package co.edu.usbcali.demo.logica.test;

import static org.junit.Assert.assertNotNull;

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
import co.edu.usbcali.demo.logica.ITiposUsuariosLogica;
import co.edu.usbcali.demo.logica.IUsuariosLogica;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UsuariosLogicaTest {
	
private static final Logger log = LoggerFactory.getLogger(ClienteDAOTest.class);
	
	@Autowired
	private IUsuariosLogica usuariosLogica;
	
	@Autowired
	private ITiposUsuariosLogica tipoUsuaLogica;
	
	private long usuCedula = 911110L;
	
	private long tusuCodigo = 10l;
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception{
		Usuarios usuario = new Usuarios();
		usuario.setUsuCedula(usuCedula);
		usuario.setUsuLogin("GUUNNERS");
		usuario.setUsuNombre("Mr Guunners");
		usuario.setUsuClave("1234");
		
		TiposUsuarios tiposUsuarios = tipoUsuaLogica.consultarTipoUsuarioPorId(tusuCodigo);
		
		usuario.setTiposUsuarios(tiposUsuarios);
		
		usuariosLogica.grabar(usuario);
	}

	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception{
		Usuarios usuario = usuariosLogica.consultarUsuariosPorId(usuCedula);
		assertNotNull("Usuario No Existe", usuario);
		log.info(usuario.getUsuNombre());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception{
		Usuarios usuario = usuariosLogica.consultarUsuariosPorId(usuCedula);
		assertNotNull("Usuario No Existe", usuario);
		usuario.setUsuNombre("Garcia Moreno");		
		usuariosLogica.modificar(usuario);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception{
		Usuarios usuarios = usuariosLogica.consultarUsuariosPorId(usuCedula);
		assertNotNull("El cliente no Existe",usuarios);
		
		usuariosLogica.borrar(usuarios);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception{
		List<Usuarios> losUsuarios = usuariosLogica.consultarTodos();
		for (Usuarios usuarios : losUsuarios) {
			log.info(usuarios.getUsuNombre());
		}
	}

}

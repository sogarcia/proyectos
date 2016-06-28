package co.edu.usbcali.demo.dao.test;

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

import co.edu.usbcali.demo.dao.ITiposUsuariosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class UsuariosDAOTest {
	
	private static final Logger log = LoggerFactory.getLogger(ClienteDAOTest.class);
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private ITiposUsuariosDAO tipoUsuaDAO;
	
	private Long usuCedula = 911110L;
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		Usuarios usuario = new Usuarios();
		usuario.setUsuCedula(usuCedula);
		usuario.setUsuLogin("GUUNNERS");
		usuario.setUsuNombre("Mr Guunners");
		usuario.setUsuClave("1234");
		
		TiposUsuarios tiposUsuarios = tipoUsuaDAO.consultarTipoUsuarioId(10L);
		
		usuario.setTiposUsuarios(tiposUsuarios);
		
		usuariosDAO.grabar(usuario);
	}

	@Test
	@Transactional(readOnly=true)
	public void btest() {
		Usuarios usuario = usuariosDAO.consultarUsuarioPorId(usuCedula);
		assertNotNull("Usuario No Existe", usuario);
		log.info(usuario.getUsuNombre());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		Usuarios usuario = usuariosDAO.consultarUsuarioPorId(usuCedula);
		assertNotNull("Usuario No Existe", usuario);
		usuario.setUsuNombre("Garcia Moreno");		
		usuariosDAO.modificar(usuario);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		Usuarios usuarios = usuariosDAO.consultarUsuarioPorId(usuCedula);
		assertNotNull("El cliente no Existe",usuarios);
		
		usuariosDAO.borrar(usuarios);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Usuarios> losUsuarios = usuariosDAO.consultarTodos();
		for (Usuarios usuarios : losUsuarios) {
			log.info(usuarios.getUsuNombre());
		}
	}


}

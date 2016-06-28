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
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TipoUsuariosDAOTest {
	
	private static final Logger log = LoggerFactory.getLogger(TipoUsuariosDAOTest.class);
	
	@Autowired
	private ITiposUsuariosDAO tipoUsuarioDAO;
	
	private Long tUsuCodigo = 30L;
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		TiposUsuarios tipoUsuario = new TiposUsuarios();
		tipoUsuario.setTusuCodigo(tUsuCodigo);
		tipoUsuario.setTusuNombre("ADMINISTRADOR");
		
		tipoUsuarioDAO.grabar(tipoUsuario);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		TiposUsuarios tipoUsuario = tipoUsuarioDAO.consultarTipoUsuarioId(tUsuCodigo);
		assertNotNull("Tipo Usuario No Existe", tipoUsuario);
		log.info(tipoUsuario.getTusuNombre());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		TiposUsuarios tipoUsuario = tipoUsuarioDAO.consultarTipoUsuarioId(tUsuCodigo);
		assertNotNull("Tipo Usuario No Existe", tipoUsuario);
		tipoUsuario.setTusuNombre("ADMINISTRADOR PRINCIPAL");
		
		tipoUsuarioDAO.modificar(tipoUsuario);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		TiposUsuarios tipoUsuario = tipoUsuarioDAO.consultarTipoUsuarioId(tUsuCodigo);
		assertNotNull("El tipoUsuario no Existe",tipoUsuario);
		
		tipoUsuarioDAO.borrar(tipoUsuario);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<TiposUsuarios> losTipoUsua = tipoUsuarioDAO.consultarTodos();
		for (TiposUsuarios tipoUsuario : losTipoUsua) {
			log.info(tipoUsuario.getTusuNombre());
		}
	}	

}

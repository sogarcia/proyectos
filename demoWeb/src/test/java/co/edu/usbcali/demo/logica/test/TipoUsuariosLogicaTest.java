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

import co.edu.usbcali.demo.dao.test.TipoUsuariosDAOTest;
import co.edu.usbcali.demo.logica.ITiposUsuariosLogica;
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TipoUsuariosLogicaTest {
	
private static final Logger log = LoggerFactory.getLogger(TipoUsuariosDAOTest.class);
	
	@Autowired
	private ITiposUsuariosLogica tipoUsuarioLogica;
	
	private long tUsuCodigo = 30l;
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception{
		TiposUsuarios tipoUsuario = new TiposUsuarios();
		tipoUsuario.setTusuCodigo(tUsuCodigo);
		tipoUsuario.setTusuNombre("ADMINISTRADOR");
		
		tipoUsuarioLogica.grabar(tipoUsuario);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception{
		TiposUsuarios tipoUsuario = tipoUsuarioLogica.consultarTipoUsuarioPorId(tUsuCodigo);
		assertNotNull("Tipo Usuario No Existe", tipoUsuario);
		log.info(tipoUsuario.getTusuNombre());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception{
		TiposUsuarios tipoUsuario = tipoUsuarioLogica.consultarTipoUsuarioPorId(tUsuCodigo);
		assertNotNull("Tipo Usuario No Existe", tipoUsuario);
		tipoUsuario.setTusuNombre("ADMINISTRADOR PRINCIPAL");
		
		tipoUsuarioLogica.modificar(tipoUsuario);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception{
		TiposUsuarios tipoUsuario = tipoUsuarioLogica.consultarTipoUsuarioPorId(tUsuCodigo);
		assertNotNull("El tipoUsuario no Existe",tipoUsuario);
		
		tipoUsuarioLogica.borrar(tipoUsuario);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception{
		List<TiposUsuarios> losTipoUsua = tipoUsuarioLogica.consultarTodos();
		for (TiposUsuarios tipoUsuario : losTipoUsua) {
			log.info(tipoUsuario.getTusuNombre());
		}
	}

}

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

import co.edu.usbcali.demo.dao.ITipoDocumentoDAO;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TiposDocumentosDAOTest {
	
	private static final Logger log = LoggerFactory.getLogger(TipoUsuariosDAOTest.class);
	
	@Autowired
	private ITipoDocumentoDAO tipoDocumentoDAO;
	
	private Long tdocCodigo = 40L;
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		TiposDocumentos tipoDocumento = new TiposDocumentos();
		tipoDocumento.setTdocCodigo(tdocCodigo);
		tipoDocumento.setTdocNombre("PASAPORTE");
		
		tipoDocumentoDAO.grabar(tipoDocumento);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarTipoDocumentoId(tdocCodigo);
		assertNotNull("Tipo Documento No Existe", tiposDocumentos);
		log.info(tiposDocumentos.getTdocNombre());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarTipoDocumentoId(tdocCodigo);
		assertNotNull("Tipo Usuario No Existe", tiposDocumentos);
		tiposDocumentos.setTdocNombre("CEDULA DE CIUDADANIA");
		
		tipoDocumentoDAO.modificar(tiposDocumentos);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarTipoDocumentoId(tdocCodigo);
		assertNotNull("El Tipo Documento no Existe",tiposDocumentos);
		
		tipoDocumentoDAO.borrar(tiposDocumentos);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<TiposDocumentos> losTipoDocumento = tipoDocumentoDAO.consultarTodos();
		for (TiposDocumentos tipoDocumento : losTipoDocumento) {
			log.info(tipoDocumento.getTdocNombre());
		}
	}	

}

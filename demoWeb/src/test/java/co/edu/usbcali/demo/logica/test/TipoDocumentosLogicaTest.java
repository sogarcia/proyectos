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
import co.edu.usbcali.demo.logica.ITipoDocumentoLogica;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TipoDocumentosLogicaTest {

	
private static final Logger log = LoggerFactory.getLogger(TipoUsuariosDAOTest.class);
	
	@Autowired
	private ITipoDocumentoLogica tipoDocumentoLogica;
	
	private long tdocCodigo = 10L;
	
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() throws Exception {
		TiposDocumentos tipoDocumento = new TiposDocumentos();
		tipoDocumento.setTdocCodigo(tdocCodigo);
		tipoDocumento.setTdocNombre("PASAPORTE");
		
		tipoDocumentoLogica.grabar(tipoDocumento);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception{
		TiposDocumentos tiposDocumentos = tipoDocumentoLogica.consultarTipoDocumentoPorId(tdocCodigo);
		assertNotNull("Tipo Documento No Existe", tiposDocumentos);
		log.info(tiposDocumentos.getTdocNombre());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception{
		TiposDocumentos tiposDocumentos = tipoDocumentoLogica.consultarTipoDocumentoPorId(tdocCodigo);
		assertNotNull("Tipo Usuario No Existe", tiposDocumentos);
		tiposDocumentos.setTdocNombre("CEDULA DE CIUDADANIA");
		
		tipoDocumentoLogica.modificar(tiposDocumentos);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception{
		TiposDocumentos tiposDocumentos = tipoDocumentoLogica.consultarTipoDocumentoPorId(tdocCodigo);
		assertNotNull("El Tipo Documento no Existe",tiposDocumentos);
		
		tipoDocumentoLogica.borrar(tiposDocumentos);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest() throws Exception{
		List<TiposDocumentos> losTipoDocumento = tipoDocumentoLogica.consultarTodos();
		for (TiposDocumentos tipoDocumento : losTipoDocumento) {
			log.info(tipoDocumento.getTdocNombre());
		}
	}	
	
}

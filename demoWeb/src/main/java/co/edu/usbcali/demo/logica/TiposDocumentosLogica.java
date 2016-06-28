package co.edu.usbcali.demo.logica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.ITipoDocumentoDAO;
import co.edu.usbcali.demo.modelo.TiposDocumentos;


@Service
@Scope("singleton")
public class TiposDocumentosLogica implements ITipoDocumentoLogica {

	@Autowired
	private ITipoDocumentoDAO tiposDocumentosDAO;

	@Override
	public void grabar(TiposDocumentos entity) throws Exception {
	

	}

	@Override
	public void modificar(TiposDocumentos entity) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void borrar(TiposDocumentos entity) throws Exception {
	

	}

	@Override
	@Transactional(readOnly=true)
	public TiposDocumentos consultarTipoDocumentoPorId(long tdocCodigo) throws Exception {
		if(tdocCodigo==0){
			throw new Exception("El cero no es un numero de tipo documento valida");
		}
		return tiposDocumentosDAO.consultarTipoDocumentoId(tdocCodigo);
	}

	@Override
	@Transactional(readOnly=true)
	public List<TiposDocumentos> consultarTodos() throws Exception {
		return tiposDocumentosDAO.consultarTodos();
	}

}

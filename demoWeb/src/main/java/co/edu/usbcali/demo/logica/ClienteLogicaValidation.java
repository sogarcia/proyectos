package co.edu.usbcali.demo.logica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.ITipoDocumentoDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.utilidades.IUtilidades;

@Service
@Scope("singleton")
public class ClienteLogicaValidation implements IClienteLogica {
	
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	@Autowired
	private ITipoDocumentoDAO tipoDocumentoDAO;	
	
	@Autowired
	private IUtilidades<Clientes> utilidad;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Clientes clientes) throws Exception{		
		
		utilidad.validaModeloGenerico(clientes);
		
		Clientes entity = null;
		
		entity = clienteDAO.consultarClinetePorId(clientes.getCliId());
		
		
		if(entity != null){
			throw new Exception("Cliente con Identificacion "+ entity.getCliId() + " ya existe ");
		}
		
		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarTipoDocumentoId(clientes.getTiposDocumentos().getTdocCodigo());
		if(tiposDocumentos == null){
			throw new Exception("El tipo de documento no existe");
		}
		
		clientes.setTiposDocumentos(tiposDocumentos);
		
		clienteDAO.grabar(clientes);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Clientes clientes) throws Exception{
		
		utilidad.validaModeloGenerico(clientes);
		
		TiposDocumentos tiposDocumentos=tipoDocumentoDAO.consultarTipoDocumentoId(clientes.getTiposDocumentos().getTdocCodigo());
		if(tiposDocumentos==null){
			throw new Exception("El tipo de documento no existe");
		}
		clientes.setTiposDocumentos(tiposDocumentos);
		
		clienteDAO.modificar(clientes);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Clientes clientes) throws Exception {
		if(clientes == null){
			throw new Exception("El clientes es null");
		}
		Clientes entity=clienteDAO.consultarClinetePorId(clientes.getCliId());
		if(entity==null){
			throw new Exception("El cliente que desea eliminar no existe");
		}
		
		clienteDAO.borrar(entity);

	}

	@Override
	@Transactional(readOnly=true)
	public Clientes consultarClinetePorId(long cliId) throws Exception {
		return clienteDAO.consultarClinetePorId(cliId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Clientes> consultarTodos() throws Exception {		
		return clienteDAO.consultarTodos();
	}

}

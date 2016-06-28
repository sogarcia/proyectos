package co.edu.usbcali.demo.logica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.ITiposUsuariosDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.utilidades.IUtilidades;

@Service
@Scope("singleton")
public class TiposUsuariosLogica implements ITiposUsuariosLogica {
	
	@Autowired
	private IUtilidades<TiposUsuarios> utilidades;
	
	@Autowired
	private ITiposUsuariosDAO tipoUsuariosDAO;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(TiposUsuarios tiposUsuarios) throws Exception {		
		utilidades.validaModeloGenerico(tiposUsuarios);
		tipoUsuariosDAO.grabar(tiposUsuarios);
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(TiposUsuarios tiposUsuarios) throws Exception {
		utilidades.validaModeloGenerico(tiposUsuarios);
		tipoUsuariosDAO.modificar(tiposUsuarios);
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(TiposUsuarios tiposUsuarios) throws Exception {
		
		if(tiposUsuarios == null){
			throw new Exception("El tipo de usuario se encuentra null");
		}
		TiposUsuarios entity = tipoUsuariosDAO.consultarTipoUsuarioId(tiposUsuarios.getTusuCodigo());
		if(entity == null){
			throw new Exception("El tipo de documento no existe");
		}
		
		tipoUsuariosDAO.borrar(tiposUsuarios);

	}

	@Override
	@Transactional(readOnly=true)
	public TiposUsuarios consultarTipoUsuarioPorId(long tusuCodigo) throws Exception {
		return tipoUsuariosDAO.consultarTipoUsuarioId(tusuCodigo);
	}

	@Override
	@Transactional(readOnly=true)
	public List<TiposUsuarios> consultarTodos() throws Exception {
		return tipoUsuariosDAO.consultarTodos();
	}

}

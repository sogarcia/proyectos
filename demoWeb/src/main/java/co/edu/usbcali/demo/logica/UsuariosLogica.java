package co.edu.usbcali.demo.logica;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.ITiposUsuariosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;
import co.edu.usbcali.demo.utilidades.IUtilidades;

@Service
@Scope("singleton")
public class UsuariosLogica implements IUsuariosLogica {
	
	
	@Autowired
	private ITiposUsuariosDAO tipoUsuarioDAO;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private IUtilidades<Usuarios> utilidad;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Usuarios usuarios) throws Exception {
		
		utilidad.validaModeloGenerico(usuarios);
		
		Usuarios entity = null;
		
		entity = usuariosDAO.consultarUsuarioPorId(usuarios.getUsuCedula());
		
		
		if(entity != null){
			throw new Exception("Usuario con Cedula "+ entity.getUsuCedula() + " ya existe ");
		}
		
		TiposUsuarios tiposUsuarios = tipoUsuarioDAO.consultarTipoUsuarioId(usuarios.getTiposUsuarios().getTusuCodigo());
		if(tiposUsuarios == null){
			throw new Exception("El tipo de usuario no existe");
		}
		
		usuarios.setTiposUsuarios(tiposUsuarios);
		
		usuariosDAO.grabar(usuarios);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Usuarios usuarios) throws Exception {
		utilidad.validaModeloGenerico(usuarios);
		
		TiposUsuarios tiposUsuarios = tipoUsuarioDAO.consultarTipoUsuarioId(usuarios.getTiposUsuarios().getTusuCodigo());
		if(tiposUsuarios == null){
			throw new Exception("El tipo de usuario no existe");
		}
		
		usuarios.setTiposUsuarios(tiposUsuarios);
		
		usuariosDAO.modificar(usuarios);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Usuarios usuarios) throws Exception {
		if (usuarios == null){
			throw new Exception("El usuario es null");
		}
		
		Usuarios entity = usuariosDAO.consultarUsuarioPorId(usuarios.getUsuCedula());
		if(entity==null){
			throw new Exception("El usuario que desea eliminar no existe");
		}
		
		usuariosDAO.borrar(entity);
	}

	@Override
	@Transactional(readOnly=true)
	public Usuarios consultarUsuariosPorId(long usuCedula) throws Exception {
		return usuariosDAO.consultarUsuarioPorId(usuCedula);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Usuarios> consultarTodos() throws Exception {
		return usuariosDAO.consultarTodos();
	}

}

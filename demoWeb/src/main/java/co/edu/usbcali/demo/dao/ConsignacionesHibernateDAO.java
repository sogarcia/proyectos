package co.edu.usbcali.demo.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;

@Repository
@Scope("singleton")
public class ConsignacionesHibernateDAO implements IConsignacionesDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public void grabar(Consignaciones consignaciones) {
		sessionFactory.getCurrentSession().save(consignaciones);
	}

	@Override
	public void modificar(Consignaciones consignaciones) {
		sessionFactory.getCurrentSession().update(consignaciones);
	}

	@Override
	public void borrar(Consignaciones consignaciones) {
		sessionFactory.getCurrentSession().delete(consignaciones);
	}

	@Override
	public Consignaciones consultarConsignacionesPorId(ConsignacionesId id) {
		return sessionFactory.getCurrentSession().get(Consignaciones.class, id);
	}

	@Override
	public List<Consignaciones> consultarTodos() {
		return sessionFactory.getCurrentSession().createCriteria(Consignaciones.class).list();
	}

	@Override
	public List<Consignaciones> consultarConsignacionPorCuenta(String cueNumero) {
		//String query = "select con_codigo, cue_numero, usu_cedula, con_valor, con_fecha,con_descripcion from consignaciones where cue_numero = '"+cueNumero+"'";
		String query = "select * from consignaciones where cue_numero = '"+cueNumero+"'";
		//return sessionFactory.getCurrentSession().cr
		return sessionFactory.getCurrentSession().createSQLQuery(query).list();
	}

}

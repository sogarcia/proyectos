package co.edu.usbcali.demo.utilidades;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope
public class Utilidades<T> implements IUtilidades<T>{
	
	private Logger log = LoggerFactory.getLogger(Utilidades.class);
	
	@Autowired
	private Validator validator;
	
	
	public void validaModeloGenerico(T entidad)throws Exception {
		
		StringBuilder stringBuilder = new StringBuilder();
		Set<ConstraintViolation<T>> constraintViolations = validator.validate(entidad);
		
		if(constraintViolations.size()>0){
			for (ConstraintViolation<T> constraintViolation : constraintViolations) {
				//Registra en el log el mensaje de error y la propiedad que se esta evaluando
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				//Se agrega cada propiedad y mensaje error encontrado en la cadena de string que se lanzara como mensaje de error.
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}	
	}


	@Override
	public boolean isNumerico(String variable) throws Exception {		
		try {			
			if (variable.isEmpty()) {
				return false;
			}			
			Double.parseDouble(variable);			
		} catch (Exception e) {
			return false;
		}		
		return true;
	}

}

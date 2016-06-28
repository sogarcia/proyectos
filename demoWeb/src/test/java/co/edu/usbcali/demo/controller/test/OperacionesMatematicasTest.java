package co.edu.usbcali.demo.controller.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import co.edu.usbcali.demo.dto.Resultado;

public class OperacionesMatematicasTest {
	
	private static final Logger log=LoggerFactory.getLogger(OperacionesMatematicasTest.class);

	//@Test
	public void test() {
		RestTemplate restTemplate = new RestTemplate();
		Resultado resultado=restTemplate.getForObject("http://localhost:8080/demoWeb/controller/operacionesMatematicas/sumar/4/100", Resultado.class);
		assertNotNull("El resultado es null", resultado);
		log.info(""+resultado.getValor());
	}
	
	//@Test
	public void multiplicarTest() {
		RestTemplate restTemplate = new RestTemplate();
		Resultado resultado=restTemplate.getForObject("http://localhost:8080/demoWeb/controller/operacionesMatematicas/multiplicar/4/100", Resultado.class);
		assertNotNull("El resultado es null", resultado);
		log.info(""+resultado.getValor());
	}

	//@Test
	public void restarTest() {
		RestTemplate restTemplate = new RestTemplate();
		Resultado resultado=restTemplate.getForObject("http://localhost:8080/demoWeb/controller/operacionesMatematicas/restar/4/100", Resultado.class);
		assertNotNull("El resultado es null", resultado);
		log.info(""+resultado.getValor());
	}

	//@Test
	public void dividirTest() {
		RestTemplate restTemplate = new RestTemplate();
		Resultado resultado=restTemplate.getForObject("http://localhost:8080/demoWeb/controller/operacionesMatematicas/dividir/4/100", Resultado.class);
		assertNotNull("El resultado es null", resultado);
		log.info(""+resultado.getValor());
	}


}

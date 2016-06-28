package co.edu.usbcali.demo.logica;

import java.util.List;

import co.edu.usbcali.demo.dto.TransaccionDTO;

public interface ITransaccionesLogica {
	
	public void consignar(long cliId, String cueNumero, long usuCedula, double valor)throws Exception;
	
	public void retirar(long cliId, String cueNumero, long usuCedula, double valor)throws Exception;
	
	public List<TransaccionDTO> consultarTransaccionPorCuenta(String cueNumero) throws Exception;

}

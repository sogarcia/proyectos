package co.edu.usbcali.demo.utilidades;

public interface IUtilidades<T> {
	
	public void validaModeloGenerico(T entidad)throws Exception;
	
	public boolean isNumerico(String variable) throws Exception;

}

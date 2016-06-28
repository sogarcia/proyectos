package co.edu.usbcali.demo.modelo;
// Generated 22/04/2016 07:50:35 PM by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;

/**
 * Cuentas generated by hbm2java
 */
public class Cuentas implements java.io.Serializable {

	private String cueNumero;
	private Clientes clientes;
	@Digits(integer=10, fraction=2)
	private BigDecimal cueSaldo;
	private String cueActiva;
	private String cueClave;
	private Set<Consignaciones> consignacioneses = new HashSet<Consignaciones>(0);
	private Set<Retiros> retiroses = new HashSet<Retiros>(0);

	public Cuentas() {
	}

	public Cuentas(String cueNumero, Clientes clientes, BigDecimal cueSaldo, String cueActiva, String cueClave) {
		this.cueNumero = cueNumero;
		this.clientes = clientes;
		this.cueSaldo = cueSaldo;
		this.cueActiva = cueActiva;
		this.cueClave = cueClave;
	}

	public Cuentas(String cueNumero, Clientes clientes, BigDecimal cueSaldo, String cueActiva, String cueClave,
			Set<Consignaciones> consignacioneses, Set<Retiros> retiroses) {
		this.cueNumero = cueNumero;
		this.clientes = clientes;
		this.cueSaldo = cueSaldo;
		this.cueActiva = cueActiva;
		this.cueClave = cueClave;
		this.consignacioneses = consignacioneses;
		this.retiroses = retiroses;
	}

	public String getCueNumero() {
		return this.cueNumero;
	}

	public void setCueNumero(String cueNumero) {
		this.cueNumero = cueNumero;
	}

	public Clientes getClientes() {
		return this.clientes;
	}

	public void setClientes(Clientes clientes) {
		this.clientes = clientes;
	}

	public BigDecimal getCueSaldo() {
		return this.cueSaldo;
	}

	public void setCueSaldo(BigDecimal cueSaldo) {
		this.cueSaldo = cueSaldo;
	}

	public String getCueActiva() {
		return this.cueActiva;
	}

	public void setCueActiva(String cueActiva) {
		this.cueActiva = cueActiva;
	}

	public String getCueClave() {
		return this.cueClave;
	}

	public void setCueClave(String cueClave) {
		this.cueClave = cueClave;
	}

	public Set<Consignaciones> getConsignacioneses() {
		return this.consignacioneses;
	}

	public void setConsignacioneses(Set<Consignaciones> consignacioneses) {
		this.consignacioneses = consignacioneses;
	}

	public Set<Retiros> getRetiroses() {
		return this.retiroses;
	}

	public void setRetiroses(Set<Retiros> retiroses) {
		this.retiroses = retiroses;
	}

}

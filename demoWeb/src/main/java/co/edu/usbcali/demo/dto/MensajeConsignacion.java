package co.edu.usbcali.demo.dto;

import java.math.BigDecimal;
import java.util.Date;

public class MensajeConsignacion {
	private long numeroConsignacion;
	private String numeroCuenta;
	private BigDecimal valorConsignacion;
	private String fechaConsignacion;
	private String descripcionConsignacion;
	public long getNumeroConsignacion() {
		return numeroConsignacion;
	}
	public void setNumeroConsignacion(long numeroConsignacion) {
		this.numeroConsignacion = numeroConsignacion;
	}
	public String getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(String numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public BigDecimal getValorConsignacion() {
		return valorConsignacion;
	}
	public void setValorConsignacion(BigDecimal valorConsignacion) {
		this.valorConsignacion = valorConsignacion;
	}
	
	public String getDescripcionConsignacion() {
		return descripcionConsignacion;
	}
	public void setDescripcionConsignacion(String descripcionConsignacion) {
		this.descripcionConsignacion = descripcionConsignacion;
	}
	public String getFechaConsignacion() {
		return fechaConsignacion;
	}
	public void setFechaConsignacion(String fechaConsignacion) {
		this.fechaConsignacion = fechaConsignacion;
	}
	
	

}

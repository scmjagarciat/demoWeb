package co.edu.usbcali.demo.modelo;
// Generated 22/04/2016 07:50:35 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

/**
 * TiposDocumentos generated by hbm2java
 */
public class TiposDocumentos implements java.io.Serializable {
	@NotNull
	@Max(value=1999999999)
	private long tdocCodigo;
	@NotNull
	@Length(min=3,max=50)
	private String tdocNombre;
	private Set<Clientes> clienteses = new HashSet<Clientes>(0);

	public TiposDocumentos() {
	}

	public TiposDocumentos(long tdocCodigo, String tdocNombre) {
		this.tdocCodigo = tdocCodigo;
		this.tdocNombre = tdocNombre;
	}

	public TiposDocumentos(long tdocCodigo, String tdocNombre, Set<Clientes> clienteses) {
		this.tdocCodigo = tdocCodigo;
		this.tdocNombre = tdocNombre;
		this.clienteses = clienteses;
	}

	public long getTdocCodigo() {
		return this.tdocCodigo;
	}

	public void setTdocCodigo(long tdocCodigo) {
		this.tdocCodigo = tdocCodigo;
	}

	public String getTdocNombre() {
		return this.tdocNombre;
	}

	public void setTdocNombre(String tdocNombre) {
		this.tdocNombre = tdocNombre;
	}

	public Set<Clientes> getClienteses() {
		return this.clienteses;
	}

	public void setClienteses(Set<Clientes> clienteses) {
		this.clienteses = clienteses;
	}

}

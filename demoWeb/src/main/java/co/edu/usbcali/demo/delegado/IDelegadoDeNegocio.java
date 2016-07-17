package co.edu.usbcali.demo.delegado;

import java.math.BigDecimal;
import java.util.List;

import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

public interface IDelegadoDeNegocio {

	public void grabarClientes(Clientes clientes) throws Exception;

	public void modificarClientes(Clientes clientes) throws Exception;

	public void borrarClientes(Clientes clientes) throws Exception;

	public Clientes consultarClinetePorId(long cliId) throws Exception;

	public List<Clientes> consultarTodosClientes() throws Exception;

	public void grabarTiposDocumentos(TiposDocumentos entity) throws Exception;

	public void modificarTiposDocumentos(TiposDocumentos entity) throws Exception;

	public void borrarTiposDocumentos(TiposDocumentos entity) throws Exception;

	public TiposDocumentos consultarTipoDocumentoId(long tdocCodigo) throws Exception;

	public List<TiposDocumentos> consultarTodosTiposDocumentos() throws Exception;

	public void grabarUsuarios(Usuarios usuarios) throws Exception;

	public void modificarUsuarios(Usuarios usuarios) throws Exception;

	public void borrarUsuarios(Usuarios usuarios) throws Exception;

	public Usuarios consultarUsuarioId(long usuCedula) throws Exception;

	public List<Usuarios> consultarTodosUsuarios() throws Exception;

	public void grabarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception;

	public void modificarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception;

	public void borrarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception;

	public TiposUsuarios consultarTipoUsuarioId(long tusuCodigo) throws Exception;

	public List<TiposUsuarios> consultarTodosTiposUsuarios() throws Exception;

	public void retirar(long cliId, long usuCedula, String cueNumero, BigDecimal conValor, String conDescripcion)
			throws Exception;

	public void consignar(long cliId, long usuCedula, String cueNumero, BigDecimal conValor, String conDescripcion)
			throws Exception;
	
	public Cuentas consultarCuentasPorId(String cueNumero) throws Exception;

}

package co.edu.usbcali.demo.delegado;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import co.edu.usbcali.demo.logica.IClienteLogica;
import co.edu.usbcali.demo.logica.ICuentaLogica;
import co.edu.usbcali.demo.logica.ITipoUsuarioLogica;
import co.edu.usbcali.demo.logica.ITiposDocumentosLogica;
import co.edu.usbcali.demo.logica.ITransaccionesLogica;
import co.edu.usbcali.demo.logica.IUsuarioLogica;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@Scope("singleton")
@Component("delegadoDeNegocio")
public class DelegadoDeNegocio implements IDelegadoDeNegocio {

	@Autowired
	private IClienteLogica clienteLogica;

	@Autowired
	private ITiposDocumentosLogica tiposDocumentosLogica;

	@Autowired
	private IUsuarioLogica usuarioLogica;

	@Autowired
	private ITipoUsuarioLogica tipoUsuarioLogica;

	@Autowired
	private ITransaccionesLogica transaccionLogica;
	
	@Autowired
	private ICuentaLogica cuentaLogica;

	@Override
	public void grabarClientes(Clientes clientes) throws Exception {
		clienteLogica.grabar(clientes);
	}

	@Override
	public void modificarClientes(Clientes clientes) throws Exception {
		clienteLogica.modificar(clientes);

	}

	@Override
	public void borrarClientes(Clientes clientes) throws Exception {
		clienteLogica.borrar(clientes);

	}

	@Override
	public Clientes consultarClinetePorId(long cliId) throws Exception {
		return clienteLogica.consultarClinetePorId(cliId);
	}

	@Override
	public List<Clientes> consultarTodosClientes() throws Exception {
		return clienteLogica.consultarTodos();
	}

	@Override
	public void grabarTiposDocumentos(TiposDocumentos entity) throws Exception {
		tiposDocumentosLogica.grabar(entity);
	}

	@Override
	public void modificarTiposDocumentos(TiposDocumentos entity) throws Exception {
		tiposDocumentosLogica.modificar(entity);

	}

	@Override
	public void borrarTiposDocumentos(TiposDocumentos entity) throws Exception {
		tiposDocumentosLogica.borrar(entity);

	}

	@Override
	public TiposDocumentos consultarTipoDocumentoId(long tdocCodigo) throws Exception {
		return tiposDocumentosLogica.consultarTipoDocumentoId(tdocCodigo);
	}

	@Override
	public List<TiposDocumentos> consultarTodosTiposDocumentos() throws Exception {
		return tiposDocumentosLogica.consultarTodos();
	}

	@Override
	public void grabarUsuarios(Usuarios usuarios) throws Exception {
		usuarioLogica.grabar(usuarios);
	}

	@Override
	public void modificarUsuarios(Usuarios usuarios) throws Exception {
		usuarioLogica.modificar(usuarios);
	}

	@Override
	public void borrarUsuarios(Usuarios usuarios) throws Exception {
		usuarioLogica.borrar(usuarios);
	}

	@Override
	public Usuarios consultarUsuarioId(long usuCedula) throws Exception {
		return usuarioLogica.consultarUsuarioId(usuCedula);
	}

	@Override
	public List<Usuarios> consultarTodosUsuarios() throws Exception {
		return usuarioLogica.consultarTodos();
	}

	@Override
	public void grabarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception {

		tipoUsuarioLogica.grabar(tiposUsuarios);
	}

	@Override
	public void modificarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception {
		tipoUsuarioLogica.modificar(tiposUsuarios);
	}

	@Override
	public void borrarTiposUsuarios(TiposUsuarios tiposUsuarios) throws Exception {
		tipoUsuarioLogica.borrar(tiposUsuarios);
	}

	@Override
	public TiposUsuarios consultarTipoUsuarioId(long tusuCodigo) throws Exception {
		return tipoUsuarioLogica.consultarTipoUsuarioId(tusuCodigo);
	}

	@Override
	public List<TiposUsuarios> consultarTodosTiposUsuarios() throws Exception {
		return tipoUsuarioLogica.consultarTodos();
	}

	@Override
	public void retirar(long cliId, long usuCedula, String cueNumero, BigDecimal conValor, String conDescripcion)
			throws Exception {
		transaccionLogica.retirar(cliId, usuCedula, cueNumero, conValor, conDescripcion);
	}

	@Override
	public void consignar(long cliId, long usuCedula, String cueNumero, BigDecimal conValor, String conDescripcion)
			throws Exception {
		transaccionLogica.consignar(cliId, usuCedula, cueNumero, conValor, conDescripcion);
	}

	public Cuentas consultarCuentasPorId(String cueNumero) throws Exception{
		return cuentaLogica.consultarCuentasPorId(cueNumero);
		
	}
}

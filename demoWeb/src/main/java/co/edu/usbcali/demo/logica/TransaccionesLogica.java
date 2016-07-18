package co.edu.usbcali.demo.logica;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;
import co.edu.usbcali.demo.sqs.IConsignacionSQS;
import co.edu.usbcali.demo.sqs.IRetiroSQS;

@Service
@Scope("singleton")

public class TransaccionesLogica implements ITransaccionesLogica {

	private static final Logger log = LoggerFactory.getLogger(TransaccionesLogica.class);

	@Autowired
	ICuentaLogica cuentaLogica;

	@Autowired
	IClienteLogica clienteLogica;

	@Autowired
	IConsignacionLogica consignacionLogica;

	@Autowired
	IRetiroLogica retiroLogica;

	@Autowired
	IUsuarioLogica usuarioLogica;
	/* Colas SQS AWS */
	@Autowired
	IRetiroSQS retiroSQS;
	@Autowired
	IConsignacionSQS consignacionSQS;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void retirar(long cliId, long usuCedula, String cueNumero, BigDecimal conValor, String conDescripcion)
			throws Exception {
		StringBuilder stringBuilder = new StringBuilder();


		if (conValor.toString().isEmpty()) {
			stringBuilder.append("Valor es nulo");
			throw new Exception(stringBuilder.toString());
		}

		if (Objects.isNull(usuCedula)) {
			stringBuilder.append("Usuario no seleccionado");
			throw new Exception(stringBuilder.toString());
		}	
		
		Cuentas cuenta = cuentaLogica.consultarCuentasPorId(cueNumero);
		Usuarios usuario = usuarioLogica.consultarUsuarioId(usuCedula);
		BigDecimal saldoNuevo;
	
		if (usuario == null) {
			stringBuilder.append("Usuario No Existe");
			throw new Exception(stringBuilder.toString());
		}

		if (cuenta == null) {
			stringBuilder.append("Cuenta No Existe");
			throw new Exception(stringBuilder.toString());
		}

		Clientes cliente = clienteLogica.consultarClinetePorId(cliId);
		if (cliente == null) {
			stringBuilder.append("Cliente No Existe");
			throw new Exception(stringBuilder.toString());
		}

		if (cliente.getCliId() != cuenta.getClientes().getCliId()) {
			stringBuilder.append("Cliente No corresponde al enviado " + cliente.getCliId() + " - "
					+ cuenta.getClientes().getCliId());
			throw new Exception(stringBuilder.toString());
		}
		if (conValor.compareTo(new BigDecimal("0")) <= 0) {
			stringBuilder.append("Valor a retirar es menor o igual a cero");
			throw new Exception(stringBuilder.toString());

		}

		if (cuenta.getCueSaldo().compareTo(conValor) < 0) {
			stringBuilder.append("Valor a retirar es mayor al saldo disponible de la cuenta");
			throw new Exception(stringBuilder.toString());
		}
		saldoNuevo = cuenta.getCueSaldo().add(conValor.multiply(new BigDecimal("-1")));
		if (cuenta.getCueActiva().toString().trim().compareTo("I") == 0) {
			stringBuilder.append(
					"Cuenta Inactiva para realizar retiros ");
			throw new Exception(stringBuilder.toString());
		}

		if (cuenta.getCueActiva().toString().trim().compareTo("N") == 0) {
			log.debug("cuenta con estado que no permite ninguna transacción");
			stringBuilder.append("cuenta con estado que no permite ninguna transacción");
			throw new Exception(stringBuilder.toString());
		}
		/* Crear transacción de Retiro */
		Retiros retiros = new Retiros();
		retiros.setId(new RetirosId((long) (Math.random() * 999999999 + 1), cuenta.getCueNumero()));
		retiros.setUsuarios(usuario);
		retiros.setCuentas(cuenta);
		retiros.setRetFecha(new Date());
		retiros.setRetValor(conValor);
		retiros.setRetDescripcion(conDescripcion);
		retiroLogica.grabar(retiros);
		log.debug(retiros.getRetDescripcion() + " - " + retiros.getRetValor());

		/* Actualizar saldo */
		log.debug(cuenta.getCueNumero() + " - " + cuenta.getCueSaldo());
		cuenta.setCueSaldo(saldoNuevo);
		cuentaLogica.modificar(cuenta);
		log.debug(cuenta.getCueNumero() + " - " + cuenta.getCueSaldo());
		
		/* Enviar Mensaje SQS */
		retiroSQS.grabarMensajeRetiro(retiros);
		log.debug("Se envio mensaje retiro a AWS SQS");
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void consignar(long cliId, long usuCedula, String cueNumero, BigDecimal conValor, String conDescripcion)
			throws Exception {
		StringBuilder stringBuilder = new StringBuilder();
		if (conValor.toString().isEmpty()) {
			stringBuilder.append("Valor es nulo");
			throw new Exception(stringBuilder.toString());
		}

		if (Objects.isNull(usuCedula)) {
			stringBuilder.append("Usuario es nulo");
			throw new Exception(stringBuilder.toString());
		}	

		Cuentas cuenta = cuentaLogica.consultarCuentasPorId(cueNumero);
		Usuarios usuario = usuarioLogica.consultarUsuarioId(usuCedula);
		BigDecimal saldoNuevo;

		if (usuario == null) {
			stringBuilder.append("Usuario No Existe");
			throw new Exception(stringBuilder.toString());
		}

		if (cuenta == null) {
			stringBuilder.append("Cuenta No Existe");
			throw new Exception(stringBuilder.toString());
		}

		Clientes cliente = clienteLogica.consultarClinetePorId(cliId);
		if (cliente == null) {
			stringBuilder.append("Cliente No Existe");
			throw new Exception(stringBuilder.toString());
		}

		if (cliente.getCliId() != cuenta.getClientes().getCliId()) {
			stringBuilder.append("Cliente No corresponde al enviado " + cliente.getCliId() + " - "
					+ cuenta.getClientes().getCliId());
			throw new Exception(stringBuilder.toString());
		}
		if (conValor.compareTo(new BigDecimal("0")) <= 0) {
			stringBuilder.append("Valor a consignar es menor o igual a cero");
			throw new Exception(stringBuilder.toString());

		}

		saldoNuevo = cuenta.getCueSaldo().add(conValor);
		if (cuenta.getCueActiva().toString().trim().compareTo("I") == 0) {
			log.debug("cuenta con estado inactivo");
			cuenta.setCueActiva("S");
		}
		if (cuenta.getCueActiva().toString().trim().compareTo("N") == 0) {
			log.debug("cuenta con estado que no permite ninguna transacción");
			stringBuilder.append("cuenta con estado que no permite ninguna transacción");
			throw new Exception(stringBuilder.toString());
		}
		/* Crear transacción de consignación */
		Consignaciones consignaciones = new Consignaciones();
		consignaciones.setId(new ConsignacionesId((long) (Math.random() * 999999999 + 1), cuenta.getCueNumero()));
		consignaciones.setUsuarios(usuario);
		consignaciones.setCuentas(cuenta);
		consignaciones.setConFecha(new Date());
		consignaciones.setConValor(conValor);
		consignaciones.setConDescripcion(conDescripcion);
		consignacionLogica.grabar(consignaciones);
		log.debug(consignaciones.getConDescripcion() + " - " + consignaciones.getConValor());

		/* Actualizar saldo */
		log.debug(cuenta.getCueNumero() + " - " + cuenta.getCueSaldo());
		cuenta.setCueSaldo(saldoNuevo);
		cuentaLogica.modificar(cuenta);
		log.debug(cuenta.getCueNumero() + " - " + cuenta.getCueSaldo());

		/* Enviar Mensaje SQS */
		consignacionSQS.grabarMensajeConsignacion(consignaciones);
		log.debug("Se envio mensaje consignacion a AWS SQS");
	}

}

package co.edu.usbcali.demo.logica;

import java.math.BigDecimal;

public interface ITransaccionesLogica {
	public void retirar(long cliId, long usuCedula, String cueNumero, BigDecimal conValor, String conDescripcion) throws Exception;
	public void consignar(long cliId, long usuCedula, String cueNumero, BigDecimal conValor, String conDescripcion) throws Exception;
	
}

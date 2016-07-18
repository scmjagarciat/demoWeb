package co.edu.usbcali.demo.sqs;

import co.edu.usbcali.demo.modelo.Consignaciones;


public interface IConsignacionSQS {
	public void grabarMensajeConsignacion(Consignaciones consignaciones)throws Exception;

}

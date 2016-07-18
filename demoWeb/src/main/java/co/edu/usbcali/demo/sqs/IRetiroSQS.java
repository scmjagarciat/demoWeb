package co.edu.usbcali.demo.sqs;

import co.edu.usbcali.demo.modelo.Retiros;

public interface IRetiroSQS {

	public void grabarMensajeRetiro(Retiros retiros)throws Exception;
}

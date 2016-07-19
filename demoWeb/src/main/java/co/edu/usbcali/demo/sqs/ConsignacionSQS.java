package co.edu.usbcali.demo.sqs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.usbcali.demo.dto.MensajeConsignacion;
import co.edu.usbcali.demo.modelo.Consignaciones;

@Service
@Scope("singleton")
public class ConsignacionSQS implements IConsignacionSQS {

	private static final Logger log = LoggerFactory.getLogger(ConsignacionSQS.class);

	@Autowired
	String queueConsignacion; 
	@Override
	public void grabarMensajeConsignacion(Consignaciones consignaciones) throws Exception {
		String qurl = queueConsignacion.toString();
		MensajeConsignacion mensajeConsignacion = new MensajeConsignacion();
		String mensajeConsignacionJsonString = null;
		AWSCredentials credentials = null;
		try {
			credentials = new ProfileCredentialsProvider("default").getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (/.aws/credentials), and is in valid format.", e);
		}

		AmazonSQS sqs = new AmazonSQSClient(credentials);

		log.debug("Getting Started with Amazon SQS");

		/* Construyendo mensaje */
		DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mensajeConsignacion.setNumeroConsignacion(consignaciones.getId().getConCodigo());
		mensajeConsignacion.setNumeroCuenta(consignaciones.getId().getCueNumero());
		mensajeConsignacion.setFechaConsignacion(fecha.format(consignaciones.getConFecha()));
		mensajeConsignacion.setValorConsignacion(consignaciones.getConValor());
		mensajeConsignacion.setDescripcionConsignacion(consignaciones.getConDescripcion());

		/* Convertir Objeto a JSON */
		try {
			ObjectMapper mapper = new ObjectMapper();
			mensajeConsignacionJsonString = mapper.writeValueAsString(mensajeConsignacion);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		}
		try {
			/* Enviando mensaje */
			log.debug(qurl + ": " + mensajeConsignacionJsonString);
			sqs.sendMessage(new SendMessageRequest(qurl, mensajeConsignacionJsonString));
			sqs.shutdown();
		} catch (AmazonServiceException ase) {
			log.debug("Caught an AmazonServiceException, which means your request made it "
					+ "to Amazon SQS, but was rejected with an error response for some reason.");
			log.debug("Error Message:    " + ase.getMessage());
			log.debug("HTTP Status Code: " + ase.getStatusCode());
			log.debug("AWlog.debugS Error Code:   " + ase.getErrorCode());
			log.debug("Error Type:       " + ase.getErrorType());
			log.debug("Request ID:       " + ase.getRequestId());
		} catch (AmazonClientException ace) {
			log.debug("Caught an AmazonClientException, which means the client encountered "
					+ "a serious internal problem while trying to communicate with SQS, such as not "
					+ "being able to access the network.");
			log.debug("Error Message: " + ace.getMessage());
		}

	}

}

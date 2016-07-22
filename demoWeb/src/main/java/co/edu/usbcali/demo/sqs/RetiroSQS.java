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
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.edu.usbcali.demo.dto.MensajeRetiro;
import co.edu.usbcali.demo.modelo.Retiros;

@Service
@Scope("singleton")
public class RetiroSQS implements IRetiroSQS {

	private static final Logger log = LoggerFactory.getLogger(RetiroSQS.class);

	@Autowired
	String queueRetiro; 
	
	@Override
	public void grabarMensajeRetiro(Retiros retiros) throws Exception {

		String qurl =queueRetiro.toString();
		MensajeRetiro mensajeRetiro = new MensajeRetiro();
		String mensajeRetiroJsonString = null;
		BasicAWSCredentials credentials = null;
		try {
			credentials = new BasicAWSCredentials("AKIAJTHZM2W27YAZBMRA", "LsG/SGBblThgkCsMcCIp1AdZsBqIiwbjAw2ueYI0");
} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (/.aws/credentials), and is in valid format.", e);
		}

		AmazonSQS sqs = new AmazonSQSClient(credentials);

		log.debug("Getting Started with Amazon SQS");

		/* Construyendo mensaje */
		DateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mensajeRetiro.setNumeroRetiro(retiros.getId().getRetCodigo());
		mensajeRetiro.setNumeroCuenta(retiros.getId().getCueNumero());
		mensajeRetiro.setFechaRetiro(fecha.format(retiros.getRetFecha()));
		mensajeRetiro.setValorRetiro(retiros.getRetValor());
		mensajeRetiro.setDescripcionRetiro(retiros.getRetDescripcion());

		/* Convertir Objeto a JSON */
		try {
			ObjectMapper mapper = new ObjectMapper();
			mensajeRetiroJsonString = mapper.writeValueAsString(mensajeRetiro);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		}
		try {
			/* Enviando mensaje */
			log.debug(qurl + ": " + mensajeRetiroJsonString);
			sqs.sendMessage(new SendMessageRequest(qurl, mensajeRetiroJsonString));
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

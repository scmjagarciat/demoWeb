package co.edu.usbcali.demo.sqs.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.logica.IConsignacionLogica;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;
import co.edu.usbcali.demo.sqs.IConsignacionSQS;
import co.edu.usbcali.demo.sqs.IRetiroSQS;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ConsignacionSQSTest {
	private static final Logger log=LoggerFactory.getLogger(ConsignacionSQSTest.class);
	
	@Autowired
	private IConsignacionSQS consignacionSQS;
	//@Autowired
	//private IConsignacionLogica consignacionesLogica;
	@Autowired
	private IUsuariosDAO usuariosDAO;

	@Autowired
	private ICuentasDAO cuentasDAO;

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)	
	public  void atest() throws Exception{
		Consignaciones consignaciones = new Consignaciones();
		consignaciones.setId(new ConsignacionesId(100, "4008-5305-0010"));
		Usuarios usuarios= usuariosDAO.consultarUsuarioId(25L);
		consignaciones.setUsuarios(usuarios);
		Cuentas cuentas = cuentasDAO.consultarCuentasPorId("4008-5305-0010");
		consignaciones.setCuentas(cuentas);
		consignaciones.setConFecha(new Date());
		consignaciones.setConValor(new BigDecimal("100900.99"));
		consignaciones.setConDescripcion("CONSIGNACIONES");
		//consignacionesLogica.grabar(consignaciones);
		log.debug(consignaciones.getConDescripcion()+" - "+consignaciones.getConValor());
		consignacionSQS.grabarMensajeConsignacion(consignaciones);

	}
	

}

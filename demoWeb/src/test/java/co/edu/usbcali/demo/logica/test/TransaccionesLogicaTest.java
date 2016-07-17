package co.edu.usbcali.demo.logica.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

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

import co.edu.usbcali.demo.logica.ITransaccionesLogica;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class TransaccionesLogicaTest {
	private static final Logger log=LoggerFactory.getLogger(TransaccionesLogicaTest.class);
	
	@Autowired
	private ITransaccionesLogica transaccionesLogica;


	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		
		try {
			transaccionesLogica.retirar(101234L, 25L, "4008-5305-0010", new BigDecimal("1000.10"), "RETIRO TEST");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void btest() {
		
		try {
			transaccionesLogica.consignar(101234L, 25L, "4008-5305-0010", new BigDecimal("100000.10"), "CONSIGNACION TEST");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

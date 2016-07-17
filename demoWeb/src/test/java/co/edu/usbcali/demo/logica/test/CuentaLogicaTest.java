package co.edu.usbcali.demo.logica.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.List;

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

import co.edu.usbcali.demo.dao.IClienteDAO;

import co.edu.usbcali.demo.logica.ICuentaLogica;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class CuentaLogicaTest {
	private static final Logger log=LoggerFactory.getLogger(CuentaLogicaTest.class);
	@Autowired
	private ICuentaLogica cuentaLogica;
	@Autowired
	private IClienteDAO clienteDAO;

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)	
	public  void atest() throws Exception{
		Clientes clientes = clienteDAO.consultarClinetePorId(401234L);
		Cuentas cuenta= new Cuentas();
		cuenta.setClientes(clientes);
		cuenta.setCueNumero("4008-5305-0090");
		cuenta.setCueSaldo(new BigDecimal("0.00"));
		cuenta.setCueActiva("S");
		cuenta.setCueClave("12345");
		cuentaLogica.grabar(cuenta);
		log.info(cuenta.getCueNumero()+" "+cuenta.getClientes().getCliNombre()+" "+cuenta.getCueSaldo().toString());
		
			
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception{
		Cuentas cuenta = cuentaLogica.consultarCuentasPorId("4008-5305-0090");
		assertNotNull(" La cuenta no existe", cuenta);
		log.info(" El saldo de la  "+cuenta.getCueActiva()+" es: "+ cuenta.getCueSaldo().toString());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception{
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId("4008-5305-0090");
		assertNotNull(" LA CUENTA NO EXISTE ", cuentas );
		cuentas.setCueSaldo(new BigDecimal("500000.58"));
		cuentaLogica.modificar(cuentas);	
		log.info(" El saldo de la  "+cuentas.getCueActiva()+" es: "+ cuentas.getCueSaldo().toString());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception{
		Cuentas cuentas = cuentaLogica.consultarCuentasPorId("4008-5305-0090");
		assertNotNull(" LA CUENTA NO EXISTE ", cuentas.getCueActiva() );
		cuentaLogica.borrar(cuentas);		
	}
	@Test
	@Transactional(readOnly=true)
	@Rollback(false)
	public void ftest() throws Exception {
		List<Cuentas> lasCuentas=cuentaLogica.consultarTodos();
		for (Cuentas cuentas : lasCuentas) {
			log.info("La cuenta es: "+cuentas.getCueNumero()+" Se encuentra activa:? "+cuentas.getCueActiva()+" Su saldo es: "+cuentas.getCueSaldo().toString());
		}

	}
}

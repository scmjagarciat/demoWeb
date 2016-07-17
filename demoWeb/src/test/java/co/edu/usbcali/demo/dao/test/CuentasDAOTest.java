package co.edu.usbcali.demo.dao.test;

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
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class CuentasDAOTest {

	private static final Logger log=LoggerFactory.getLogger(CuentasDAOTest.class);
	
	@Autowired
	private IClienteDAO clienteDAO;
	@Autowired
	private ICuentasDAO cuentasDAO;
	
	private Long cliId=401234L;
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	
	public  void atest(){
		Clientes clientes = clienteDAO.consultarClinetePorId(cliId);
		Cuentas cuenta= new Cuentas();
		cuenta.setClientes(clientes);
		cuenta.setCueNumero("4008-5305-0085");
		cuenta.setCueSaldo(new BigDecimal("200000.00"));
		cuenta.setCueActiva("S");
		cuenta.setCueClave("123456");
		cuentasDAO.grabar(cuenta);
		cuenta = cuentasDAO.consultarCuentasPorId("4008-5305-0085");
		log.info(cuenta.getCueNumero()+" "+cuenta.getClientes().getCliNombre()+" "+cuenta.getCueSaldo().toString());
		
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void btest(){
		Cuentas cuenta = cuentasDAO.consultarCuentasPorId("4008-5305-0080");
		assertNotNull(" La cuenta no existe", cuenta);
		log.info(cuenta.getClientes().getCliNombre());
		log.info(cuenta.getCueNumero());
		log.info(cuenta.getCueActiva());

	}
	
	
	
	@Test
	@Transactional(readOnly=true)
	@Rollback(false)
	public void ctest() {
		List<Cuentas> lasCuentas=cuentasDAO.consultarTodos();
		for (Cuentas cuenta : lasCuentas) {
			log.info(cuenta.getCueNumero()+" "+cuenta.getClientes().getCliNombre()+" "+cuenta.getCueActiva()+" "+cuenta.getCueSaldo().toString());
		}
	}
		
		@Test
		@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
		@Rollback(false)
		public void dtest() {
			Cuentas cuenta = cuentasDAO.consultarCuentasPorId("4008-5305-0085");
			cuenta.setCueActiva("N");
			cuentasDAO.modificar(cuenta);
			cuenta= cuentasDAO.consultarCuentasPorId("4008-5305-0085");
			log.info(cuenta.getCueNumero());
			log.info(cuenta.getCueActiva());
			
		}


		@Test
		@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
		@Rollback(false)
		public void etest() {
			Cuentas cuenta = cuentasDAO.consultarCuentasPorId("4008-5305-0085");
			cuentasDAO.borrar(cuenta);
			List<Cuentas> lasCuentas=cuentasDAO.consultarTodos();
			for (Cuentas cuentaI : lasCuentas) {
				log.info(cuentaI.getCueNumero()+" "+cuentaI.getClientes().getCliNombre()+" "+cuentaI.getCueActiva()+" "+cuentaI.getCueSaldo().toString());
			}		
		
		}
	
}

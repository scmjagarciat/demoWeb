package co.edu.usbcali.demo.logica.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;
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

import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.dao.test.ClienteDAOTest;
import co.edu.usbcali.demo.logica.IConsignacionLogica;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class ConsignacionesLogicaTest {
	private static final Logger log=LoggerFactory.getLogger(ConsignacionesLogicaTest.class);
	
	@Autowired
	private IConsignacionLogica consignacionesLogica;
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
		consignacionesLogica.grabar(consignaciones);
		log.debug(consignaciones.getConDescripcion()+" - "+consignaciones.getConValor());
		
	}
	

	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception{
		Consignaciones consignaciones = consignacionesLogica.consultarConsignacionesPorId(new ConsignacionesId(100, "4008-5305-0010"));
		assertNotNull(" la consignacion no existe", consignaciones);
		log.info(consignaciones.getConDescripcion()+" - "+consignaciones.getConValor());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception{
		Consignaciones consignaciones = consignacionesLogica.consultarConsignacionesPorId(new ConsignacionesId(100, "4008-5305-0010"));
		assertNotNull(" la consignacion no existe", consignaciones );
		consignaciones.setConDescripcion("CONSIGNACION NL");
		consignacionesLogica.modificar(consignaciones);	
		log.info(consignaciones.getConDescripcion()+" - "+consignaciones.getConValor());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception{
		Consignaciones consignaciones = consignacionesLogica.consultarConsignacionesPorId(new ConsignacionesId(100, "4008-5305-0010"));
		assertNotNull(" la consignacion no existe", consignaciones );
		consignacionesLogica.borrar(consignaciones);		
	}
	@Test
	@Transactional(readOnly=true)
	@Rollback(false)
	public void ftest() throws Exception {
		List<Consignaciones> lasConsignaciones=consignacionesLogica.consultarTodos();
		for (Consignaciones consignaciones : lasConsignaciones) {
			log.info(consignaciones.getConDescripcion()+" - "+consignaciones.getConValor());
		}

	}


}

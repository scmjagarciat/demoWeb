package co.edu.usbcali.demo.dao.test;

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

import co.edu.usbcali.demo.dao.IConsignacionesDAO;
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class ConsignacionesDAOTest {
	private static final Logger log=LoggerFactory.getLogger(ConsignacionesDAOTest.class);
	
	@Autowired
	private IConsignacionesDAO consignacionesDAO;
	@Autowired
	private IUsuariosDAO usuariosDAO;
	private long usuCedula = 25L;
	@Autowired
	private ICuentasDAO cuentasDAO;
	
	private String cueNumero = "4008-5305-0010";
	private ConsignacionesId consignacionesId = new ConsignacionesId(1, "4008-5305-0010");
	private ConsignacionesId consignacionesIdNew = new ConsignacionesId(100, "4008-5305-0010");
	
	private Date conFecha = new Date();
	private BigDecimal conValor= new BigDecimal("1115000.00");

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		Consignaciones consignaciones = new Consignaciones();
		consignaciones.setId(consignacionesIdNew);
		Usuarios usuarios= usuariosDAO.consultarUsuarioId(usuCedula);
		consignaciones.setUsuarios(usuarios);
		Cuentas cuentas = cuentasDAO.consultarCuentasPorId(cueNumero);
		consignaciones.setCuentas(cuentas);
		consignaciones.setConFecha(conFecha);
		consignaciones.setConValor(conValor);
		consignaciones.setConDescripcion("consignacion");
		consignacionesDAO.grabar(consignaciones);
		consignaciones = consignacionesDAO.consultarConsignacionesPorId(consignacionesIdNew);
		log.info(consignaciones.getConDescripcion());
		log.info(consignaciones.getConValor().toString());
		log.info(consignaciones.getCuentas().getCueNumero());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void btest(){
		Consignaciones consignacion = consignacionesDAO.consultarConsignacionesPorId(consignacionesId);
		assertNotNull(" La consignación no existe ", consignacion);
		log.info(consignacion.getConDescripcion());
		log.info(consignacion.getId().getCueNumero());
		log.info(consignacion.getConValor().toString());
	}
	
	@Test
	@Transactional(readOnly=true)
	@Rollback(false)
	public void ctest() {
		List<Consignaciones> lasConsignaciones=consignacionesDAO.consultarTodos();
		for (Consignaciones consignacion : lasConsignaciones) {
			log.info(consignacion.getId().getCueNumero()+" "+consignacion.getConDescripcion()+" "+consignacion.getConFecha().toString());
		}
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		Consignaciones consignacion = consignacionesDAO.consultarConsignacionesPorId(consignacionesId);
		consignacion.setConDescripcion("APERTURA PRUEBAS");
		consignacionesDAO.modificar(consignacion);
		consignacion = consignacionesDAO.consultarConsignacionesPorId(consignacionesId);
		log.info(consignacion.getConDescripcion());
	
	}


	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void etest() {
		Consignaciones consignacion = consignacionesDAO.consultarConsignacionesPorId(consignacionesIdNew);
		consignacionesDAO.borrar(consignacion);
		List<Consignaciones> lasConsignaciones=consignacionesDAO.consultarTodos();
		for (Consignaciones laConsignacion : lasConsignaciones) {
			log.info(laConsignacion.getId().getCueNumero()+" "+laConsignacion.getConValor().toString()+" "+laConsignacion.getConDescripcion()+" "+laConsignacion.getConFecha().toString());
		}
	}

	
	
	}

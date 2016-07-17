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

import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IRetirosDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class RetirosDAOTest {

	private static final Logger log=LoggerFactory.getLogger(RetirosDAOTest.class);
	
	@Autowired
	private IRetirosDAO retirosDAO;
	@Autowired
	private IUsuariosDAO usuariosDAO;
	private long usuCedula = 25L;
	@Autowired
	private ICuentasDAO cuentasDAO;
	
	private String cueNumero = "4008-5305-0010";
	private RetirosId retirosId = new RetirosId(1, "4008-5305-0010");
	private RetirosId retirosIdNew = new RetirosId(16, "4008-5305-0010");
	
	private Date conFecha = new Date();
	private BigDecimal conValor= new BigDecimal("22245000.59");

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		Retiros retiros = new Retiros();
		retiros.setId(retirosIdNew);
		Usuarios usuarios= usuariosDAO.consultarUsuarioId(usuCedula);
		retiros.setUsuarios(usuarios);
		Cuentas cuentas = cuentasDAO.consultarCuentasPorId(cueNumero);
		retiros.setCuentas(cuentas);
		retiros.setRetFecha(conFecha);
		retiros.setRetValor(conValor);
		retiros.setRetDescripcion("retiro cajero");
		retirosDAO.grabar(retiros);
		retiros = retirosDAO.consultarRetirosPorId(retirosIdNew);
		log.info(retiros.getRetDescripcion());
		log.info(retiros.getRetValor().toString());
		log.info(retiros.getCuentas().getCueNumero());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void btest(){
		Retiros retiro = retirosDAO.consultarRetirosPorId(retirosId);
		assertNotNull(" El retiro no existe ", retiro);
		log.info(retiro.getRetDescripcion());
		log.info(retiro.getId().getCueNumero());
		log.info(retiro.getRetValor().toString());
	}
	
	@Test
	@Transactional(readOnly=true)
	@Rollback(false)
	public void ctest() {
		List<Retiros> losRetiros=retirosDAO.consultarTodos();
		for (Retiros retiro : losRetiros) {
			log.info(retiro.getRetValor().toString()+" "+retiro.getRetDescripcion()+" "+retiro.getRetFecha().toString());
		}
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		Retiros retiro = retirosDAO.consultarRetirosPorId(retirosId);
		retiro.setRetDescripcion("RETIRO PRUEBAS MOD");
		retirosDAO.modificar(retiro);
		retiro = retirosDAO.consultarRetirosPorId(retirosId);
		log.info(retiro.getRetDescripcion());
	
	}


	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void etest() {
		Retiros retiro = retirosDAO.consultarRetirosPorId(retirosIdNew);
		retirosDAO.borrar(retiro);
		List<Retiros> losRetiros=retirosDAO.consultarTodos();
		for (Retiros elRetiro : losRetiros) {
			log.info(elRetiro.getId().getCueNumero()+" "+elRetiro.getRetValor().toString()+" "+elRetiro.getRetDescripcion()+" "+elRetiro.getRetFecha().toString());
		}
	}


}

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
import co.edu.usbcali.demo.logica.IRetiroLogica;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class RetirosLogicaTest {
	private static final Logger log=LoggerFactory.getLogger(RetirosLogicaTest.class);
	
	@Autowired
	private IRetiroLogica RetirosLogica;
	@Autowired
	private IUsuariosDAO usuariosDAO;

	@Autowired
	private ICuentasDAO cuentasDAO;

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)	
	public  void atest() throws Exception{
		Retiros retiros = new Retiros();
		retiros.setId(new RetirosId(100, "4008-5305-0010"));
		Usuarios usuarios= usuariosDAO.consultarUsuarioId(25L);
		retiros.setUsuarios(usuarios);
		Cuentas cuentas = cuentasDAO.consultarCuentasPorId("4008-5305-0010");
		retiros.setCuentas(cuentas);
		retiros.setRetFecha(new Date());
		retiros.setRetValor(new BigDecimal("100900.99"));
		retiros.setRetDescripcion("RETIRO NACIONAL");
		RetirosLogica.grabar(retiros);
		log.debug(retiros.getRetDescripcion()+" - "+retiros.getRetValor());
		
	}
	

	@Test
	@Transactional(readOnly=true)
	public void btest() throws Exception{
		Retiros retiros = RetirosLogica.consultarRetirosPorId(new RetirosId(100, "4008-5305-0010"));
		assertNotNull(" el retiro no existe", retiros);
		log.info(retiros.getRetDescripcion()+" - "+retiros.getRetValor());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() throws Exception{
		Retiros retiros = RetirosLogica.consultarRetirosPorId(new RetirosId(100, "4008-5305-0010"));
		assertNotNull(" el RETIRO no existe", retiros );
		retiros.setRetDescripcion("RETIRO LOCAL");
		RetirosLogica.modificar(retiros);	
		log.info(retiros.getRetDescripcion()+" - "+retiros.getRetValor());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() throws Exception{
		Retiros retiros = RetirosLogica.consultarRetirosPorId(new RetirosId(100, "4008-5305-0010"));
		assertNotNull(" el retiro no existe", retiros );
		RetirosLogica.borrar(retiros);		
	}
	@Test
	@Transactional(readOnly=true)
	@Rollback(false)
	public void ftest() throws Exception {
		List<Retiros> lasRetiros=RetirosLogica.consultarTodos();
		for (Retiros retiros : lasRetiros) {
			log.info(retiros.getRetDescripcion()+" - "+retiros.getRetValor());
		}

	}


}

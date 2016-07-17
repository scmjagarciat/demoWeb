package co.edu.usbcali.demo.dao.test;

import static org.junit.Assert.*;

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

import co.edu.usbcali.demo.dao.ITiposUsuariosDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")


public class TiposUsuariosDAOTest {
	private static final Logger log=LoggerFactory.getLogger(TiposUsuariosDAOTest.class);
	
	@Autowired
	private ITiposUsuariosDAO tipoUsuarioDAO;

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		TiposUsuarios tiposUsuarios = new TiposUsuarios();
		tiposUsuarios.setTusuCodigo(30L);
		tiposUsuarios.setTusuNombre("CLIENTE");
		tipoUsuarioDAO.grabar(tiposUsuarios);
		tiposUsuarios = tipoUsuarioDAO.consultarTipoUsuarioId(30L);
		log.info(tiposUsuarios.getTusuNombre());
		log.info(tiposUsuarios.getTusuCodigo()+"");
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void btest() {
		TiposUsuarios tiposUsuarios = tipoUsuarioDAO.consultarTipoUsuarioId(10L);
		assertNotNull(" El tipo de usuario no existe", tiposUsuarios);		
		log.info(tiposUsuarios.getTusuNombre());
		log.info(tiposUsuarios.getTusuCodigo()+"");
	}

	
	@Test
	@Transactional(readOnly=true)
	@Rollback(false)
	public void ctest() {
		List<TiposUsuarios> losTiposUsuarios=tipoUsuarioDAO.consultarTodos();
		for (TiposUsuarios tiposUsuarios : losTiposUsuarios) {
			log.info(tiposUsuarios.getTusuCodigo()+" - "+tiposUsuarios.getTusuNombre());
		}
	}
		
		@Test
		@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
		@Rollback(false)
public void dtest() {
		TiposUsuarios tiposUsuarios = tipoUsuarioDAO.consultarTipoUsuarioId(20L);
		assertNotNull(" El tipo de usuario no existe", tiposUsuarios);		
		tiposUsuarios.setTusuNombre("Asesor Ccial");
		tipoUsuarioDAO.modificar(tiposUsuarios);
		tiposUsuarios = tipoUsuarioDAO.consultarTipoUsuarioId(20L);
		log.info(tiposUsuarios.getTusuNombre());
		log.info(tiposUsuarios.getTusuCodigo()+"");
	}
		@Test
		@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
		@Rollback(false)
		public void etest() {
			TiposUsuarios tiposUsuarios = tipoUsuarioDAO.consultarTipoUsuarioId(30L);
			assertNotNull(" El tipo de usuario no existe", tiposUsuarios);		
			tipoUsuarioDAO.borrar(tiposUsuarios);
			List<TiposUsuarios> losTiposUsuarios=tipoUsuarioDAO.consultarTodos();
			for (TiposUsuarios tipoUsuario : losTiposUsuarios) {
				log.info(tipoUsuario.getTusuCodigo()+" - "+tipoUsuario.getTusuNombre());
			}
			}

		

}

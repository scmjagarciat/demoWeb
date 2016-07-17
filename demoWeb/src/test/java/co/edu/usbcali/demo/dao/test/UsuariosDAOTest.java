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
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class UsuariosDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(UsuariosDAOTest.class);
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private ITiposUsuariosDAO tipoUsuarioDAO;
	
	private Long usuCedula=16376245L;

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		Usuarios usuarios = new Usuarios();
		usuarios.setUsuCedula(usuCedula);
		usuarios.setUsuClave("123456789");
		usuarios.setUsuLogin("jhoalejandro");
		usuarios.setUsuNombre("JHOAN ALEJANDRO GARCIA");
		
		TiposUsuarios tiposUsuarios = tipoUsuarioDAO.consultarTipoUsuarioId(20L);
		usuarios.setTiposUsuarios(tiposUsuarios);
		usuariosDAO.grabar(usuarios);
		usuarios = usuariosDAO.consultarUsuarioId(usuCedula);
		log.info(usuarios.getUsuLogin());
		log.info(usuarios.getUsuNombre());
		
	}
	
	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void btest(){
		Usuarios usuarios = usuariosDAO.consultarUsuarioId(usuCedula);
		assertNotNull(" El usuario no existe", usuarios);
		log.info(usuarios.getUsuNombre());
		log.info(usuarios.getTiposUsuarios().getTusuNombre());
	}
	
	
	
	@Test
	@Transactional(readOnly=true)
	@Rollback(false)
	public void ctest() {
		List<Usuarios> losUsuarios=usuariosDAO.consultarTodos();
		for (Usuarios usuarios : losUsuarios) {
			log.info(usuarios.getUsuNombre());
		}
	}
		
		@Test
		@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
		@Rollback(false)
		public void dtest() {
			Usuarios usuarios = usuariosDAO.consultarUsuarioId(usuCedula);
			usuarios.setUsuNombre("Jhoan Alejandro García Trujillo");
			usuariosDAO.modificar(usuarios);
			usuarios = usuariosDAO.consultarUsuarioId(usuCedula);
			log.info(usuarios.getUsuLogin());
			log.info(usuarios.getUsuNombre());
			
		}


		@Test
		@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
		@Rollback(false)
		public void etest() {
			Usuarios usuarios = usuariosDAO.consultarUsuarioId(usuCedula);
			usuariosDAO.borrar(usuarios);
			List<Usuarios> losUsuarios=usuariosDAO.consultarTodos();
			for (Usuarios usuario : losUsuarios) {
				log.info(usuario.getUsuNombre());
			}
		}

}

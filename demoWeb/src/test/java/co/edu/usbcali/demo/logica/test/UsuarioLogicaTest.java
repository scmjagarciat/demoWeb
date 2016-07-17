package co.edu.usbcali.demo.logica.test;

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
import co.edu.usbcali.demo.logica.IUsuarioLogica;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class UsuarioLogicaTest {
	private static final Logger log=LoggerFactory.getLogger(UsuarioLogicaTest.class);
	
	@Autowired
	private IUsuarioLogica usuarioLogica;
	
	@Autowired
	private ITiposUsuariosDAO tipoUsuarioDAO;
	
	private Long usuCedula=16376245L;
	

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest()throws Exception {
		Usuarios usuarios=new Usuarios();
		usuarios.setUsuCedula(usuCedula);
		usuarios.setUsuClave("1223456");
		usuarios.setUsuLogin("jgarcia8");
		usuarios.setUsuNombre("Jhoan Alejandro García");
			
		TiposUsuarios tiposUsuarios = tipoUsuarioDAO.consultarTipoUsuarioId(20L);
		
		usuarios.setTiposUsuarios(tiposUsuarios);
		
		usuarioLogica.grabar(usuarios);
		
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest()throws Exception {
		Usuarios usuarios=usuarioLogica.consultarUsuarioId(usuCedula);
		assertNotNull("El usuario no existe", usuarios);
		log.info("Consulta "+usuarios.getUsuNombre()+" - "+usuarios.getUsuClave());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest()throws Exception {
		Usuarios usuarios=usuarioLogica.consultarUsuarioId(usuCedula);
		assertNotNull("El usuario no existe", usuarios);
		usuarios.setUsuNombre("Juan Perez");
		usuarioLogica.modificar(usuarios);
		log.info("Modificar "+usuarios.getUsuNombre()+" - "+usuarios.getUsuClave());
		
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest()throws Exception {
		Usuarios usuarios=usuarioLogica.consultarUsuarioId(usuCedula);
		assertNotNull("El usuario no existe", usuarios);
		usuarioLogica.borrar(usuarios);
		log.info("Borrar "+usuarios.getUsuNombre()+" - "+usuarios.getUsuClave());
	}

	@Test
	@Transactional(readOnly=true)
	public void etest()throws Exception {
		List<Usuarios> losUsuarios=usuarioLogica.consultarTodos();
		for (Usuarios usuarios : losUsuarios) {
			log.info("Consulta Todos "+usuarios.getUsuNombre()+" - "+usuarios.getUsuClave());
		}
	}
	
}

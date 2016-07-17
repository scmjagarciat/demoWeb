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

import co.edu.usbcali.demo.logica.ITipoUsuarioLogica;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class TipoUsuarioTest {
private static final Logger log=LoggerFactory.getLogger(TipoUsuarioTest.class);
	
	@Autowired
	private ITipoUsuarioLogica tipoUsuarioLogica;
	
	private int tusuCodigo= 30;
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest()throws Exception {
		TiposUsuarios tipoUsuario=new TiposUsuarios();
		tipoUsuario.setTusuCodigo(tusuCodigo);
		tipoUsuario.setTusuNombre("ASESOR BV");
		tipoUsuarioLogica.grabar(tipoUsuario);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest()throws Exception {
		TiposUsuarios tipoUsuario =tipoUsuarioLogica.consultarTipoUsuarioId(tusuCodigo);
		assertNotNull("El tipo de usuario no exsiste",tipoUsuario);
		log.info("CONSULTAR: "+tipoUsuario.getTusuCodigo()+" - "+tipoUsuario.getTusuNombre());
	}

	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest()throws Exception {

		TiposUsuarios tipoUsuario =tipoUsuarioLogica.consultarTipoUsuarioId(tusuCodigo);
		assertNotNull("El tipo de usuario no exsiste",tipoUsuario);
		tipoUsuario.setTusuNombre("ASESOR BV NUEVO");
		tipoUsuarioLogica.modificar(tipoUsuario);
		log.info("MODIFICAR: "+tipoUsuario.getTusuCodigo()+" - "+tipoUsuario.getTusuNombre());
	}


	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest()throws Exception {

		TiposUsuarios tipoUsuario =tipoUsuarioLogica.consultarTipoUsuarioId(tusuCodigo);
		assertNotNull("El tipo de usuario no exsiste",tipoUsuario);
		tipoUsuarioLogica.borrar(tipoUsuario);
		log.info("BORRAR: "+tipoUsuario.getTusuCodigo()+" - "+tipoUsuario.getTusuNombre());
	}
	
	@Test
	@Transactional(readOnly=true)
	public void etest()throws Exception {
		List<TiposUsuarios> losTiposUsuarios=tipoUsuarioLogica.consultarTodos();
		for (TiposUsuarios tipoUsuario : losTiposUsuarios) {
			log.info("CONSULTA MASIVA: "+tipoUsuario.getTusuCodigo()+" - "+tipoUsuario.getTusuNombre());
		}
	}


}

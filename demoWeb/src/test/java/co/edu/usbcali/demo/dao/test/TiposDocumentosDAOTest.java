package co.edu.usbcali.demo.dao.test;

import static org.junit.Assert.assertNotNull;

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

import co.edu.usbcali.demo.dao.ITipoDocumentoDAO;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")

public class TiposDocumentosDAOTest {
	private static final Logger log=LoggerFactory.getLogger(TiposDocumentosDAOTest.class);
	
	@Autowired
	private ITipoDocumentoDAO tipoDocumentoDAO;

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		TiposDocumentos tiposDocumentos = new TiposDocumentos();
		tiposDocumentos.setTdocCodigo(40L);
		tiposDocumentos.setTdocNombre("PASAPORTE");
		tipoDocumentoDAO.grabar(tiposDocumentos);
		tiposDocumentos = tipoDocumentoDAO.consultarTipoDocumentoId(40L);
		log.info(tiposDocumentos.getTdocNombre());
		log.info(tiposDocumentos.getTdocCodigo()+"");
	}

	@Test
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void btest() {
		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarTipoDocumentoId(10L);
		assertNotNull(" El tipo de documento no existe", tiposDocumentos);		
		log.info(tiposDocumentos.getTdocNombre());
		log.info(tiposDocumentos.getTdocCodigo()+"");
	}

	
	@Test
	@Transactional(readOnly=true)
	@Rollback(false)
	public void ctest() {
		List<TiposDocumentos> losTiposDocumentos=tipoDocumentoDAO.consultarTodos();
		for (TiposDocumentos tiposDocumentos : losTiposDocumentos) {
			log.info(tiposDocumentos.getTdocCodigo()+" - "+tiposDocumentos.getTdocNombre());
		}
	}
		
		@Test
		@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
		@Rollback(false)
public void dtest() {
		TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarTipoDocumentoId(20L);
		assertNotNull(" El tipo de documento no existe", tiposDocumentos);		
		tiposDocumentos.setTdocNombre("TARJETA DE ID");
		tipoDocumentoDAO.modificar(tiposDocumentos);
		tiposDocumentos = tipoDocumentoDAO.consultarTipoDocumentoId(20L);
		log.info(tiposDocumentos.getTdocNombre());
		log.info(tiposDocumentos.getTdocCodigo()+"");
	}
		@Test
		@Transactional(readOnly=false, propagation=Propagation.REQUIRED)
		@Rollback(false)
		public void etest() {
			TiposDocumentos tiposDocumentos = tipoDocumentoDAO.consultarTipoDocumentoId(40L);
			assertNotNull(" El tipo de documento no existe", tiposDocumentos);		
			tipoDocumentoDAO.borrar(tiposDocumentos);
			List<TiposDocumentos> losTiposDocumentos=tipoDocumentoDAO.consultarTodos();
			for (TiposDocumentos tipoDocumento : losTiposDocumentos) {
				log.info(tipoDocumento.getTdocCodigo()+" - "+tipoDocumento.getTdocNombre());
			}
			}

		

}

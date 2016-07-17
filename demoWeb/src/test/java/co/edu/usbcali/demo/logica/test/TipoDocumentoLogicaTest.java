package co.edu.usbcali.demo.logica.test;

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

import co.edu.usbcali.demo.logica.ITiposDocumentosLogica;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TipoDocumentoLogicaTest {

private static final Logger log=LoggerFactory.getLogger(TipoDocumentoLogicaTest.class);
	
	@Autowired
	private ITiposDocumentosLogica tipodocumentoLogica;
	
	private int tipoDocId= 40;
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest()throws Exception {
		TiposDocumentos tipodocumento=new TiposDocumentos();
		tipodocumento.setTdocCodigo(tipoDocId);
		tipodocumento.setTdocNombre("PASAPORTE");
		tipodocumentoLogica.grabar(tipodocumento);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest()throws Exception {
		TiposDocumentos tipodocumento =tipodocumentoLogica.consultarTipoDocumentoId(tipoDocId);
		assertNotNull("El tipo de documento no exsiste",tipodocumento);
		log.info(tipodocumento.getTdocNombre());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest()throws Exception {
		TiposDocumentos tipodocumento =tipodocumentoLogica.consultarTipoDocumentoId(tipoDocId);
		assertNotNull("El tipo de documento no exsiste",tipodocumento);
		tipodocumento.setTdocNombre("NUEVO DOCUMENTO");
		
		tipodocumentoLogica.modificar(tipodocumento);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest()throws Exception {
		TiposDocumentos tipodocumento =tipodocumentoLogica.consultarTipoDocumentoId(tipoDocId);
		assertNotNull("El tipo de documento no exsiste",tipodocumento);

		
		tipodocumentoLogica.borrar(tipodocumento);
	}

	@Test
	@Transactional(readOnly=true)
	public void etest()throws Exception {
		List<TiposDocumentos> losTiposDocumentos=tipodocumentoLogica.consultarTodos();
		for (TiposDocumentos tiposDocumentos : losTiposDocumentos) {
			log.info(tiposDocumentos.getTdocNombre());
		}
	}
}


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

import antlr.collections.ASTEnumeration;
import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.ITipoDocumentoDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class ClienteDAOTest {
	
	private static final Logger log=LoggerFactory.getLogger(ClienteDAOTest.class);
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	@Autowired
	private ITipoDocumentoDAO tipoDocumentoDAO;
	
	
	private Long cliId=142020L;
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void atest() {
		Clientes clientes=new Clientes();
		clientes.setCliId(cliId);
		clientes.setCliDireccion("Avenida Siempre Viva 123");
		clientes.setCliMail("hj@gmail.com");
		clientes.setCliNombre("Homero J Simpson");
		clientes.setCliTelefono("5555555");
		
		TiposDocumentos tiposDocumentos=tipoDocumentoDAO.consultarTipoDocumentoId(10L);
		
		clientes.setTiposDocumentos(tiposDocumentos);
		
		clienteDAO.grabar(clientes);
	}
	
	@Test
	@Transactional(readOnly=true)
	public void btest() {
		Clientes clientes=clienteDAO.consultarClinetePorId(cliId);
		assertNotNull("El cliente no exsiste",clientes);
		log.info(clientes.getCliNombre());
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void ctest() {
		Clientes clientes=clienteDAO.consultarClinetePorId(cliId);
		assertNotNull("El cliente no exsiste",clientes);
		clientes.setCliNombre("Juan Perez");
		
		clienteDAO.modificar(clientes);
	}
	
	@Test
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED)
	@Rollback(false)
	public void dtest() {
		Clientes clientes=clienteDAO.consultarClinetePorId(cliId);
		assertNotNull("El cliente no exsiste",clientes);
		
		clienteDAO.borrar(clientes);
	}

	@Test
	@Transactional(readOnly=true)
	public void etest() {
		List<Clientes> losClientes=clienteDAO.consultarTodos();
		for (Clientes clientes : losClientes) {
			log.info(clientes.getCliNombre());
		}
	}
	
	

}

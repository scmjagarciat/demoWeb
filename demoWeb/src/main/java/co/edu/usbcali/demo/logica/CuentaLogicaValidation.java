package co.edu.usbcali.demo.logica;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import co.edu.usbcali.demo.dao.IClienteDAO;
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.ITipoDocumentoDAO;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.TiposDocumentos;

@Service
@Scope("singleton")
public class CuentaLogicaValidation implements ICuentaLogica {
	
	private Logger log=LoggerFactory.getLogger(CuentaLogicaValidation.class);
	
	@Autowired
	private IClienteDAO clienteDAO;
	
	@Autowired
	private ICuentasDAO cuentasDAO;;
	
	@Autowired
	private Validator validator;
	

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Cuentas cuentas) throws Exception {
StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<Cuentas>> constraintViolations=validator.validate(cuentas);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Cuentas> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		Clientes clientes=clienteDAO.consultarClinetePorId(cuentas.getClientes().getCliId());
		if(clientes==null){
			throw new Exception("El  cliente no existe");
		}
		cuentas.setClientes(clientes);
		
		cuentasDAO.grabar(cuentas);	

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Cuentas cuentas) throws Exception {
StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<Cuentas>> constraintViolations=validator.validate(cuentas);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Cuentas> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		Clientes clientes=clienteDAO.consultarClinetePorId(cuentas.getClientes().getCliId());
		if(clientes==null){
			throw new Exception("El  cliente no existe");
		}
		cuentas.setClientes(clientes);
		
		cuentasDAO.modificar(cuentas);	
	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Cuentas cuentas) throws Exception {
StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<Cuentas>> constraintViolations=validator.validate(cuentas);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Cuentas> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		Clientes clientes=clienteDAO.consultarClinetePorId(cuentas.getClientes().getCliId());
		if(clientes==null){
			throw new Exception("El  cliente no existe");
		}
		cuentas.setClientes(clientes);
		
		cuentasDAO.borrar(cuentas);	

	}

	@Override
	@Transactional(readOnly=true)
	public Cuentas consultarCuentasPorId(String cueNumero) throws Exception {

		return cuentasDAO.consultarCuentasPorId(cueNumero);
	}


	@Override
	@Transactional(readOnly=true)
	public List<Cuentas> consultarTodos() throws Exception {

		return cuentasDAO.consultarTodos();
	}

}

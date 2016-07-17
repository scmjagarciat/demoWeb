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

import co.edu.usbcali.demo.dao.IRetirosDAO;
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.dao.test.ClienteDAOTest;
import co.edu.usbcali.demo.modelo.Retiros;
import co.edu.usbcali.demo.modelo.RetirosId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.Usuarios;

@Service
@Scope("singleton")
public class RetiroLogicaValidation implements IRetiroLogica {
	
	private static final Logger log=LoggerFactory.getLogger(RetiroLogicaValidation.class);
	
	@Autowired
	private IRetirosDAO retirosDAO;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private ICuentasDAO cuentasDAO;

	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Retiros retiros) throws Exception {
		StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<Retiros>> constraintViolations=validator.validate(retiros);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Retiros> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		Cuentas cuenta=cuentasDAO.consultarCuentasPorId(retiros.getId().getCueNumero());
		if(cuenta==null){
			throw new Exception("La cuenta no existe");
		}
		retiros.setCuentas(cuenta);
		
		Usuarios usuario=usuariosDAO.consultarUsuarioId(retiros.getUsuarios().getUsuCedula());
		if(usuario==null){
			throw new Exception("El usuario no existe");
		}
		retirosDAO.grabar(retiros);
		

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Retiros retiros) throws Exception {
StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<Retiros>> constraintViolations=validator.validate(retiros);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Retiros> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		Cuentas cuenta=cuentasDAO.consultarCuentasPorId(retiros.getId().getCueNumero());
		if(cuenta==null){
			throw new Exception("La cuenta no existe");
		}
		retiros.setCuentas(cuenta);
		
		Usuarios usuario=usuariosDAO.consultarUsuarioId(retiros.getUsuarios().getUsuCedula());
		if(usuario==null){
			throw new Exception("El usuario no existe");
		}
		retirosDAO.modificar(retiros);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Retiros retiros) throws Exception {
		StringBuilder stringBuilder=new StringBuilder();
		Set<ConstraintViolation<Retiros>> constraintViolations=validator.validate(retiros);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Retiros> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		Cuentas cuenta=cuentasDAO.consultarCuentasPorId(retiros.getId().getCueNumero());
		if(cuenta==null){
			throw new Exception("La cuenta no existe");
		}
		retiros.setCuentas(cuenta);
		
		Usuarios usuario=usuariosDAO.consultarUsuarioId(retiros.getUsuarios().getUsuCedula());
		if(usuario==null){
			throw new Exception("El usuario no existe");
		}
		retirosDAO.borrar(retiros);

	}

	

	@Override
	@Transactional(readOnly=true)
	public Retiros consultarRetirosPorId(RetirosId id) throws Exception {
		if(id==null){
			throw new Exception("No existe la referencia al retiro");
		}
		return retirosDAO.consultarRetirosPorId(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Retiros> consultarTodos() throws Exception {
		return retirosDAO.consultarTodos();
	}
	
}

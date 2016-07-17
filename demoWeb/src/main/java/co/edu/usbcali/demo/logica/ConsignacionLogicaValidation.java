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

import co.edu.usbcali.demo.dao.IConsignacionesDAO;
import co.edu.usbcali.demo.dao.ICuentasDAO;
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.dao.test.ClienteDAOTest;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Consignaciones;
import co.edu.usbcali.demo.modelo.ConsignacionesId;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.Usuarios;

@Service
@Scope("singleton")
public class ConsignacionLogicaValidation implements IConsignacionLogica {
	
	private static final Logger log=LoggerFactory.getLogger(ConsignacionLogicaValidation.class);
	
	@Autowired
	private IConsignacionesDAO consignacionesDAO;
	
	@Autowired
	private IUsuariosDAO usuariosDAO;
	
	@Autowired
	private ICuentasDAO cuentasDAO;

	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void grabar(Consignaciones consignaciones) throws Exception {
		StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<Consignaciones>> constraintViolations=validator.validate(consignaciones);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Consignaciones> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		Cuentas cuenta=cuentasDAO.consultarCuentasPorId(consignaciones.getId().getCueNumero());
		if(cuenta==null){
			throw new Exception("La cuenta no existe");
		}
		consignaciones.setCuentas(cuenta);
		
		Usuarios usuario=usuariosDAO.consultarUsuarioId(consignaciones.getUsuarios().getUsuCedula());
		if(usuario==null){
			throw new Exception("El usuario no existe");
		}
		consignacionesDAO.grabar(consignaciones);
		

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void modificar(Consignaciones consignaciones) throws Exception {
StringBuilder stringBuilder=new StringBuilder();
		
		Set<ConstraintViolation<Consignaciones>> constraintViolations=validator.validate(consignaciones);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Consignaciones> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		Cuentas cuenta=cuentasDAO.consultarCuentasPorId(consignaciones.getId().getCueNumero());
		if(cuenta==null){
			throw new Exception("La cuenta no existe");
		}
		consignaciones.setCuentas(cuenta);
		
		Usuarios usuario=usuariosDAO.consultarUsuarioId(consignaciones.getUsuarios().getUsuCedula());
		if(usuario==null){
			throw new Exception("El usuario no existe");
		}
		consignacionesDAO.modificar(consignaciones);

	}
	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Consignaciones consignaciones) throws Exception {
		StringBuilder stringBuilder=new StringBuilder();
		Set<ConstraintViolation<Consignaciones>> constraintViolations=validator.validate(consignaciones);
		if(constraintViolations.size()>0){
			for (ConstraintViolation<Consignaciones> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		
		Cuentas cuenta=cuentasDAO.consultarCuentasPorId(consignaciones.getId().getCueNumero());
		if(cuenta==null){
			throw new Exception("La cuenta no existe");
		}
		consignaciones.setCuentas(cuenta);
		
		Usuarios usuario=usuariosDAO.consultarUsuarioId(consignaciones.getUsuarios().getUsuCedula());
		if(usuario==null){
			throw new Exception("El usuario no existe");
		}
		consignacionesDAO.borrar(consignaciones);

	}

	

	@Override
	@Transactional(readOnly=true)
	public Consignaciones consultarConsignacionesPorId(ConsignacionesId id) throws Exception {
		if(id==null){
			throw new Exception("No existe la referencia de la consignación");
		}
		return consignacionesDAO.consultarConsignacionesPorId(id);
	}

	@Override
	@Transactional(readOnly=true)
	public List<Consignaciones> consultarTodos() throws Exception {
		return consignacionesDAO.consultarTodos();
	}
	
}

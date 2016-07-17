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

import co.edu.usbcali.demo.dao.ITiposUsuariosDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;

@Service
@Scope("singleton")
public class TipoUsuarioLogicaValidation implements ITipoUsuarioLogica {

	private static final Logger log = LoggerFactory.getLogger(TipoUsuarioLogicaValidation.class);

	@Autowired
	private ITiposUsuariosDAO tipoUsuarioDAO;

	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void grabar(TiposUsuarios tusuario) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();

		Set<ConstraintViolation<TiposUsuarios>> constraintViolations = validator.validate(tusuario);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<TiposUsuarios> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		tipoUsuarioDAO.grabar(tusuario);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void modificar(TiposUsuarios tusuario) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();

		Set<ConstraintViolation<TiposUsuarios>> constraintViolations = validator.validate(tusuario);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<TiposUsuarios> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		tipoUsuarioDAO.modificar(tusuario);
	}

	@Override
	public void borrar(TiposUsuarios tusuario) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();

		Set<ConstraintViolation<TiposUsuarios>> constraintViolations = validator.validate(tusuario);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<TiposUsuarios> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}
		tipoUsuarioDAO.borrar(tusuario);

	}

	@Override
	@Transactional(readOnly=true)
	public TiposUsuarios consultarTipoUsuarioId(long usuarioId) throws Exception {
		StringBuilder stringBuilder = new StringBuilder();

		return tipoUsuarioDAO.consultarTipoUsuarioId(usuarioId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<TiposUsuarios> consultarTodos() throws Exception {
		return tipoUsuarioDAO.consultarTodos();
	}

}

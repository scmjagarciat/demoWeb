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
import co.edu.usbcali.demo.dao.IUsuariosDAO;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@Service
@Scope("singleton")
public class UsuarioLogicaValidation implements IUsuarioLogica {
	private Logger log = LoggerFactory.getLogger(UsuarioLogicaValidation.class);

	@Autowired
	private IUsuariosDAO usuarioDAO;

	@Autowired
	private ITiposUsuariosDAO tipoUsuarioDAO;

	@Autowired
	private Validator validator;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void grabar(Usuarios usuarios) throws Exception {

		StringBuilder stringBuilder = new StringBuilder();

		Set<ConstraintViolation<Usuarios>> constraintViolations = validator.validate(usuarios);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Usuarios> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}

		TiposUsuarios tiposUsuarios = tipoUsuarioDAO
				.consultarTipoUsuarioId(usuarios.getTiposUsuarios().getTusuCodigo());
		if (tiposUsuarios == null) {
			throw new Exception("El tipo de usuario no existe");
		}
		usuarios.setTiposUsuarios(tiposUsuarios);

		usuarioDAO.grabar(usuarios);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void modificar(Usuarios usuarios) throws Exception {

		StringBuilder stringBuilder = new StringBuilder();

		Set<ConstraintViolation<Usuarios>> constraintViolations = validator.validate(usuarios);
		if (constraintViolations.size() > 0) {
			for (ConstraintViolation<Usuarios> constraintViolation : constraintViolations) {
				log.error(constraintViolation.getPropertyPath().toString());
				log.error(constraintViolation.getMessage());
				stringBuilder.append(constraintViolation.getPropertyPath().toString());
				stringBuilder.append("-");
				stringBuilder.append(constraintViolation.getMessage());
				stringBuilder.append(",");
			}
			throw new Exception(stringBuilder.toString());
		}

		TiposUsuarios tiposUsuarios = tipoUsuarioDAO
				.consultarTipoUsuarioId(usuarios.getTiposUsuarios().getTusuCodigo());
		if (tiposUsuarios == null) {
			throw new Exception("El tipo de usuario no existe");
		}
		usuarios.setTiposUsuarios(tiposUsuarios);

		usuarioDAO.modificar(usuarios);

	}

	@Override
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED,rollbackFor=Exception.class)
	public void borrar(Usuarios usuarios) throws Exception {

		Usuarios entity = usuarioDAO.consultarUsuarioId(usuarios.getUsuCedula());
		if (entity == null) {
			throw new Exception("El usuario que desea eliminar no existe");
		}

		usuarioDAO.borrar(entity);



	}


	@Override
	@Transactional(readOnly=true)
	public Usuarios consultarUsuarioId(long usuCedula) throws Exception {
		return usuarioDAO.consultarUsuarioId(usuCedula);
	}


	@Override
	@Transactional(readOnly=true)
	public List<Usuarios> consultarTodos() throws Exception {
		return usuarioDAO.consultarTodos();
	}

}

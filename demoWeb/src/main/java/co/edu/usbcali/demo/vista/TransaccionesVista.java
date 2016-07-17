package co.edu.usbcali.demo.vista;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.edu.usbcali.demo.delegado.IDelegadoDeNegocio;
import co.edu.usbcali.demo.modelo.Clientes;
import co.edu.usbcali.demo.modelo.Cuentas;
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@ViewScoped
@ManagedBean

public class TransaccionesVista {
	private final static Logger log = LoggerFactory.getLogger(TransaccionesVista.class);

	@ManagedProperty(value = "#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;
	private List<SelectItem> losUsuariosItems;
	private InputText txtCuenta;
	private InputText txtNombre;
	private InputText txtValor;

	private SelectOneMenu somUsuario;

	private CommandButton btnConsignar;
	private CommandButton btnRetirar;
	private CommandButton btnLimpiar;

	public CommandButton getBtnRetirar() {
		return btnRetirar;
	}

	public void setBtnRetirar(CommandButton btnRetirar) {
		this.btnRetirar = btnRetirar;
	}

	public void txtCuentaListener() throws Exception {
		Cuentas entity = null;
		Clientes cliente = null;
		log.debug("Consultando cliente");
		try {

			entity = delegadoDeNegocio.consultarCuentasPorId(txtCuenta.getValue().toString().trim());
			Long id = entity.getClientes().getCliId();
			cliente = delegadoDeNegocio.consultarClinetePorId(id);

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "cuenta no existe", ""));

		}
		if (entity == null) {
			btnRetirar.setDisabled(true);
			btnConsignar.setDisabled(true);
			btnLimpiar.setDisabled(false);

		} else {
			txtNombre.setValue(cliente.getCliNombre());
			btnRetirar.setDisabled(false);
			btnConsignar.setDisabled(false);
			btnLimpiar.setDisabled(false);

		}
	}

	public String consignarAction() {
		log.info("ingreso a consignar");
		Cuentas entity = null;
		Clientes cliente = null;
		try {
			entity = delegadoDeNegocio.consultarCuentasPorId(txtCuenta.getValue().toString().trim());
			Long id = entity.getClientes().getCliId();
			cliente = delegadoDeNegocio.consultarClinetePorId(id);
			log.debug("Consignando...");
			if (somUsuario.getValue().toString().trim().compareTo("-1") == 0) {

				throw new Exception("Debe seleccionar un usuario");

			}
			if (txtValor.getValue().toString().trim() == "") {

				throw new Exception("El Valor  no puede ser nulo");

			}
			Long usuCedula = delegadoDeNegocio.consultarUsuarioId(Long.parseLong(somUsuario.getValue().toString()))
					.getUsuCedula();

			delegadoDeNegocio.consignar(id, usuCedula, entity.getCueNumero(),
					new BigDecimal(txtValor.getValue().toString()), "CONSIGNACION OFICINA");

			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Se consigno con exito " + txtValor.getValue().toString(), ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public String retirarAction() {
		log.info("ingreso a retirar");
		Cuentas entity = null;
		Clientes cliente = null;
		try {
			entity = delegadoDeNegocio.consultarCuentasPorId(txtCuenta.getValue().toString().trim());
			Long id = entity.getClientes().getCliId();
			cliente = delegadoDeNegocio.consultarClinetePorId(id);
			log.debug("Retirando...");
			if (somUsuario.getValue().toString().trim().compareTo("-1") == 0) {

				throw new Exception("Debe seleccionar un usuario");

			}
			if (txtValor.getValue().toString().trim() == "") {

				throw new Exception("El Valor  no puede ser nulo");

			}

			Long usuCedula = delegadoDeNegocio.consultarUsuarioId(Long.parseLong(somUsuario.getValue().toString()))
					.getUsuCedula();
			delegadoDeNegocio.retirar(id, usuCedula, entity.getCueNumero(),
					new BigDecimal(txtValor.getValue().toString()), "RETIRO EN OFICINA");

			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Se retiro con exito " + txtValor.getValue().toString(), ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}

	public String limpiarAction() {
		log.info("ingreso a limpiar");
		txtNombre.resetValue();
		txtCuenta.resetValue();
		txtValor.resetValue();
		somUsuario.setValue("-1");
		btnRetirar.setDisabled(true);
		btnConsignar.setDisabled(true);
		btnLimpiar.setDisabled(false);
		return "";
	}

	public CommandButton getBtnConsignar() {
		return btnConsignar;
	}

	public void setBtnConsignar(CommandButton btnConsignar) {
		this.btnConsignar = btnConsignar;
	}

	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}

	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}

	public SelectOneMenu getSomUsuario() {
		return somUsuario;
	}

	public void setSomUsuario(SelectOneMenu somUsuario) {
		this.somUsuario = somUsuario;
	}

	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}

	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}

	public List<SelectItem> getLosUsuariosItems() {
		try {
			if (losUsuariosItems == null) {

				losUsuariosItems = new ArrayList<SelectItem>();
				List<Usuarios> losEntity = delegadoDeNegocio.consultarTodosUsuarios();
				for (Usuarios usuarios : losEntity) {
					losUsuariosItems.add(new SelectItem(usuarios.getUsuCedula(), usuarios.getUsuNombre()));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return losUsuariosItems;
	}

	public void setLosUsuariosItems(List<SelectItem> losUsuariosItems) {
		this.losUsuariosItems = losUsuariosItems;
	}

	public InputText getTxtCuenta() {
		return txtCuenta;
	}

	public void setTxtCuenta(InputText txtCuenta) {
		this.txtCuenta = txtCuenta;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputText getTxtValor() {
		return txtValor;
	}

	public void setTxtValor(InputText txtValor) {
		this.txtValor = txtValor;
	}

}

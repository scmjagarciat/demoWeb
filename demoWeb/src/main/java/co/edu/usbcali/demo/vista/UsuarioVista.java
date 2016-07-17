package co.edu.usbcali.demo.vista;

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
import co.edu.usbcali.demo.modelo.TiposDocumentos;
import co.edu.usbcali.demo.modelo.TiposUsuarios;
import co.edu.usbcali.demo.modelo.Usuarios;

@ViewScoped
@ManagedBean

public class UsuarioVista {
	private final static Logger log=LoggerFactory.getLogger(UsuarioVista.class);
	
	@ManagedProperty(value="#{delegadoDeNegocio}")
	private IDelegadoDeNegocio delegadoDeNegocio;
	private List<Usuarios> losUsuarios;
	private List<SelectItem> losTiposUsuariosItems;
	private InputText txtCedula;
	private InputText txtNombre;
	private InputText txtLogin;
	private InputText txtClave;
	
	private SelectOneMenu somTipoUsuario;
	
	private CommandButton btnCrear;
	private CommandButton btnModificar;
	private CommandButton btnBorrar;
	private CommandButton btnLimpiar;
	
	public void txtCedulaListener(){
		Usuarios entity=null;
		
		try {
		
			Long id=Long.parseLong(txtCedula.getValue().toString().trim());
			entity=delegadoDeNegocio.consultarUsuarioId(id);
			
		} catch (Exception e) {
			
		}
		if(entity==null){
			txtNombre.resetValue();
			txtLogin.resetValue();
			txtClave.resetValue();
			somTipoUsuario.setValue("-1");
			
			btnBorrar.setDisabled(true);
			btnCrear.setDisabled(false);
			btnModificar.setDisabled(true);
			
		}else{
			txtNombre.setValue(entity.getUsuNombre());
			txtLogin.setValue(entity.getUsuLogin());
			txtClave.setValue(entity.getUsuClave());
			somTipoUsuario.setValue(entity.getTiposUsuarios().getTusuCodigo());
			
			btnBorrar.setDisabled(false);
			btnCrear.setDisabled(true);
			btnModificar.setDisabled(false);
			
		}
		
		
	}
	
	public String crearAction(){
		log.info("ingreso a crear");
		try {
			Usuarios usuarios=new Usuarios();

			usuarios.setUsuCedula(Long.parseLong(txtCedula.getValue().toString().trim()));
			usuarios.setUsuNombre(txtNombre.getValue().toString().trim());
			usuarios.setUsuLogin(txtLogin.getValue().toString().trim());
			usuarios.setUsuClave(txtClave.getValue().toString().trim());
			TiposUsuarios tiposUsuarios=delegadoDeNegocio.consultarTipoUsuarioId(Long.parseLong(somTipoUsuario.getValue().toString()));
			usuarios.setTiposUsuarios(tiposUsuarios);			
			delegadoDeNegocio.grabarUsuarios(usuarios);			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se creo con exito", ""));
			this.limpiarAction();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}
	public String modificarAction(){
		log.info("ingreso a modificar");
		try {
			Usuarios usuarios=new Usuarios();

			usuarios.setUsuCedula(Long.parseLong(txtCedula.getValue().toString().trim()));
			usuarios.setUsuNombre(txtNombre.getValue().toString().trim());
			usuarios.setUsuLogin(txtLogin.getValue().toString().trim());
			usuarios.setUsuClave(txtClave.getValue().toString().trim());
			TiposUsuarios tiposUsuarios=delegadoDeNegocio.consultarTipoUsuarioId(Long.parseLong(somTipoUsuario.getValue().toString()));
			usuarios.setTiposUsuarios(tiposUsuarios);	
			
			delegadoDeNegocio.modificarUsuarios(usuarios);
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se modifico con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}
	public String borrarAction(){
		log.info("ingreso a borrar");
		try {
			Usuarios usuarios=new Usuarios();
			
			usuarios.setUsuCedula(Long.parseLong(txtCedula.getValue().toString().trim()));
			
			delegadoDeNegocio.borrarUsuarios(usuarios);
			
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "El usuario se elimino con exito", ""));
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), ""));
		}
		return "";
	}
	public String limpiarAction(){
		log.info("ingreso a limpiar");
		
		txtNombre.resetValue();
		txtCedula.resetValue();
		txtLogin.resetValue();
		txtClave.resetValue();
		somTipoUsuario.setValue("-1");
			
		btnBorrar.setDisabled(true);
		btnCrear.setDisabled(false);
		btnModificar.setDisabled(true);
		
		
		return "";
	}
	
	public CommandButton getBtnCrear() {
		return btnCrear;
	}
	public void setBtnCrear(CommandButton btnCrear) {
		this.btnCrear = btnCrear;
	}
	public CommandButton getBtnModificar() {
		return btnModificar;
	}
	public void setBtnModificar(CommandButton btnModificar) {
		this.btnModificar = btnModificar;
	}
	public CommandButton getBtnBorrar() {
		return btnBorrar;
	}
	public void setBtnBorrar(CommandButton btnBorrar) {
		this.btnBorrar = btnBorrar;
	}
	public CommandButton getBtnLimpiar() {
		return btnLimpiar;
	}
	public void setBtnLimpiar(CommandButton btnLimpiar) {
		this.btnLimpiar = btnLimpiar;
	}
	public InputText getTxtCedula() {
		return txtCedula;
	}
	public void setTxtCedula(InputText txtCedula) {
		this.txtCedula = txtCedula;
	}
	public InputText getTxtNombre() {
		return txtNombre;
	}
	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}
	public InputText getTxtLogin() {
		return txtLogin;
	}
	public void setTxtLogin(InputText txtLogin) {
		this.txtLogin = txtLogin;
	}
	public InputText getTxtClave() {
		return txtClave;
	}
	public void setTxtClave(InputText txtClave) {
		this.txtClave = txtClave;
	}
	public SelectOneMenu getSomTipoUsuario() {
		return somTipoUsuario;
	}
	public void setSomTipoUsuario(SelectOneMenu somTipoUsuario) {
		this.somTipoUsuario = somTipoUsuario;
	}
	public List<Usuarios> getLosUsuarios() {
		log.info("ingreso a listar");
		if(losUsuarios==null)
		{
			try {
				losUsuarios=delegadoDeNegocio.consultarTodosUsuarios();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return losUsuarios;
	}
	public IDelegadoDeNegocio getDelegadoDeNegocio() {
		return delegadoDeNegocio;
	}
	public void setDelegadoDeNegocio(IDelegadoDeNegocio delegadoDeNegocio) {
		this.delegadoDeNegocio = delegadoDeNegocio;
	}
	public void setLosUsuarios(List<Usuarios> losUsuarios) {
		this.losUsuarios = losUsuarios;
	}
	public List<SelectItem> getLosTiposUsuariosItems() {
		try {
			if(losTiposUsuariosItems==null){
				
				losTiposUsuariosItems=new ArrayList<SelectItem>();
				List<TiposUsuarios> losEntity = delegadoDeNegocio.consultarTodosTiposUsuarios();
				for(TiposUsuarios tiposUsuarios : losEntity){
					losTiposUsuariosItems.add(new SelectItem(tiposUsuarios.getTusuCodigo(), tiposUsuarios.getTusuNombre()));
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return losTiposUsuariosItems;
	}
	public void setLosTiposUsuariosItems(List<SelectItem> losTiposUsuariosItems) {
		this.losTiposUsuariosItems = losTiposUsuariosItems;
	}
	
	

}

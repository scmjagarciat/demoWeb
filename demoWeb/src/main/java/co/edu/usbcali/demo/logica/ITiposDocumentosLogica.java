package co.edu.usbcali.demo.logica;

import java.util.List;

import co.edu.usbcali.demo.modelo.TiposDocumentos;

public interface ITiposDocumentosLogica {
	
	public void grabar(TiposDocumentos entity)throws  Exception;
	public void modificar(TiposDocumentos entity)throws  Exception;
	public void borrar(TiposDocumentos entity)throws  Exception;
	public TiposDocumentos consultarTipoDocumentoId(long tdocCodigo)throws  Exception;
	public List<TiposDocumentos> consultarTodos()throws Exception;

}

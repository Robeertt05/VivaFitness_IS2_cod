/**
 * Contexto que se pasa entre la Vista y el Controlador.
 * Contiene el evento (int) que disparo la accion y el objeto de datos asociado.
 * Patron: Context Object - encapsula estado compartido entre capas.
 */
package Controlador;

import Presentacion.FactoriaPresentacion.Evento;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class Context {

	private Evento evento;
	private Object data;
	private boolean success;
	private String message;

	public Context() {
	}

	public Context(Evento evento, Object data) {
		this.evento = evento;
		this.data = data;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getObjeto() {
		return data;
	}

	public void setObjeto(Object objeto) {
		this.data = objeto;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

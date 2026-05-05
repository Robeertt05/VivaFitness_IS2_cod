 
package Controlador;

import Presentacion.FactoriaPresentacion.Evento;

 
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

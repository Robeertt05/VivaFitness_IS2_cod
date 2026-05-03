/**
 * 
 */
package Controlador;

import Presentacion.FactoriaPresentacion.Evento;

/** 
 * Context object passed between Controller, Commands and Views
 * @author azuri
 */
public class Context {

private Evento evento;
private Object data;
private boolean success;
private String message;

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

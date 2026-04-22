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

	/** Objeto de datos que acompana al evento (TEntrenador, TCliente, etc.) */
	private Object objeto;

	/** Evento que disparo la accion en la vista (constante de Evento) */
	private int evento;

	/** Constructor por defecto */
	public Context() {}

	/**
	 * Constructor con evento y datos.
	 * @param evento constante int de la clase Evento
	 * @param objeto datos asociados al evento
	 */
	public Context(int evento, Object objeto) {
		this.evento = evento;
		this.objeto = objeto;
	}

	/**
	 * Devuelve el evento del contexto.
	 * @return evento (constante int de Evento)
	 */
	public int getEvento() {
		return this.evento;
	}

	/**
	 * Establece el evento del contexto.
	 * @param evento constante int de la clase Evento
	 */
	public void setEvento(int evento) {
		this.evento = evento;
	}

	/**
	 * Devuelve el objeto de datos del contexto.
	 * @return objeto (TEntrenador, Integer, etc.)
	 */
	public Object getObjeto() {
		return this.objeto;
	}

	/**
	 * Establece el objeto de datos del contexto.
	 * @param objeto datos a transportar
	 */
	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}
}

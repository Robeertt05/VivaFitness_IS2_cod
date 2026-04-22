/**
 * Controlador principal de la aplicacion.
 * Patron: Front Controller + Singleton.
 * Recibe Context de la Presentacion, delega en CommandFactory para
 * obtener el Command correcto y lo ejecuta.
 */
package Controlador;

import Presentacion.FactoriaPresentacion.Evento;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public abstract class Controller {

	/** Unica instancia del controlador (Singleton). */
	private static Controller instance;

	/**
	 * Devuelve la unica instancia del controlador (Singleton).
	 * @return instancia de Controller
	 */
	public static Controller getInstance() {
		if (instance == null) {
			instance = new ControllerImp();
		}
		return instance;
	}

	/**
	 * Punto de entrada unico para todas las acciones del sistema.
	 * Obtiene el Command correspondiente al evento del Context y lo ejecuta.
	 * @param context contexto con el evento (int) y los datos de la peticion
	 * @return Context con el resultado para que la vista se actualice
	 */
	public Context action(Context context) {
		if (context == null) return context;
		int evento = context.getEvento();
		Command cmd = CommandFactory.getInstance().getCommand(evento);
		if (cmd != null) {
			return cmd.execute(context.getObjeto());
		}
		return context;
	}
}

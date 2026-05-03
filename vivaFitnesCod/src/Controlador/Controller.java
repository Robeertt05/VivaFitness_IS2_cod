/**
 * Controlador principal de la aplicacion.
 * Patron: Front Controller + Singleton.
 * Recibe Context de la Presentacion, delega en CommandFactory para
 * obtener el Command correcto y lo ejecuta.
 */
package Controlador;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 */
public class Controller {

	/** Unica instancia del controlador (Singleton). */
	private static Controller instance;

	/** Constructor protegido para Singleton */
	protected Controller() {}

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
	 * @param context contexto con el evento y los datos de la peticion
	 * @return Context con el resultado para que la vista se actualice
	 */
	public Context action(Context context) {
		if (context == null || context.getEvento() == null) {
			return context;
		}
		
		Command command = CommandFactory.getInstance().getCommand(context.getEvento());
		
		if (command == null) {
			context.setSuccess(false);
			context.setMessage("No command found for event: " + context.getEvento());
			return context;
		}
		
		return command.execute(context.getData());
	}
}
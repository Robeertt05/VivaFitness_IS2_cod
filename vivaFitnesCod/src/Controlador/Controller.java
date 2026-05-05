 
package Controlador;

 
public class Controller {
 
	private static Controller instance;
 
	protected Controller() {}

	public static Controller getInstance() {
		if (instance == null) {
			instance = new ControllerImp();
		}
		return instance;
	}

	public Context action(Context context) {
		if (context == null || context.getEvento() == null) {
			return context;
		}
		
		Command command = CommandFactory.getInstance().getCommand(context.getEvento());
		
		if (command == null) {
			context.setSuccess(false);
			context.setMessage("No hay comando para el evento: " + context.getEvento());
			return context;
		}
		
		return command.execute(context.getData());
	}
}
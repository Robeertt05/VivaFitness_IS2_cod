/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.SASesion;
import Negocio.entrenador.TEntrenador;

/**
 * Command to display the trainer assigned to a session
 * CASO 5: Mostrar entrenador por sesiÃÂ³n (SRS)
 * Get the specific trainer assigned to a particular session
 * @author azuri
 */
public class CommandMostrarEntrenadorSesion implements Command {
	
	private SASesion saSesion;
	
	public CommandMostrarEntrenadorSesion(SASesion saSesion) {
		this.saSesion = saSesion;
	}

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		if (!(datos instanceof Integer)) {
			ctx.setSuccess(false);
			ctx.setMessage("Session ID must be an integer");
			return ctx;
		}
		
		int idSesion = (Integer) datos;
		TEntrenador entrenador = saSesion.mostrar_entrenador_sesion(idSesion);
		
		if (entrenador != null) {
			ctx.setSuccess(true);
			ctx.setMessage("Trainer found for session");
			ctx.setData(entrenador);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Session not found or no trainer assigned");
		}
		
		return ctx;
	}
}

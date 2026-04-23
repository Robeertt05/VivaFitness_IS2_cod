/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.SASesion;

/**
 * Command to delete a session
 * CASO 1: Baja sesión (SRS)
 * Precondition: Session must not have registered clients
 * @author azuri
 */
public class CommandEliminarSesion implements Command {
	
	private SASesion saSesion;
	
	public CommandEliminarSesion(SASesion saSesion) {
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
		int resultado = saSesion.baja_sesion(idSesion);
		
		if (resultado > 0) {
			ctx.setSuccess(true);
			ctx.setMessage("Session deleted successfully");
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Failed to delete session (may have registered clients)");
		}
		
		return ctx;
	}
}

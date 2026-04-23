/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.SASesion;

/**
 * Command to check available spaces in a session
 * @author azuri
 */
public class CommandEspaciosSesion implements Command {
	
	private SASesion saSesion;
	
	public CommandEspaciosSesion(SASesion saSesion) {
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
		int espacios = saSesion.espacios_disponibles(idSesion);
		
		if (espacios >= 0) {
			ctx.setSuccess(true);
			ctx.setMessage("Available spaces: " + espacios);
			ctx.setData(espacios);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Session not found");
		}
		
		return ctx;
	}
}

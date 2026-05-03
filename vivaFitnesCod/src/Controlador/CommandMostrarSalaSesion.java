/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.SASesion;
import Integracion.FactoriaIntegracion.TSala;

/**
 * Command to display the room assigned to a session
 * CASO 4: Mostrar sala por sesiÃÂ³n (SRS)
 * Get the specific room assigned to a particular session
 * @author azuri
 */
public class CommandMostrarSalaSesion implements Command {
	
	private SASesion saSesion;
	
	public CommandMostrarSalaSesion(SASesion saSesion) {
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
		TSala sala = saSesion.mostrar_sala_sesion(idSesion);
		
		if (sala != null) {
			ctx.setSuccess(true);
			ctx.setMessage("Room found for session");
			ctx.setData(sala);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Session not found or no room assigned");
		}
		
		return ctx;
	}
}

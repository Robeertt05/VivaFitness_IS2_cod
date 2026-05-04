/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.SASala;

/**
 * Command to delete a room
 * CASO 2: Baja sala (SRS)
 * Precondition: Room must not have active sessions
 * @author azuri
 */
public class CommandBajaSala implements Command {
	
	private SASala saSala;
	
	public CommandBajaSala(SASala saSala) {
		this.saSala = saSala;
	}

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		if (!(datos instanceof Integer)) {
			ctx.setSuccess(false);
			ctx.setMessage("Room ID must be an integer");
			return ctx;
		}
		
		int idSala = (Integer) datos;
		int resultado = saSala.baja_sala(idSala);
		
		if (resultado > 0) {
			ctx.setSuccess(true);
			ctx.setMessage("Room deleted successfully");
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Failed to delete room (may have active sessions)");
		}
		
		return ctx;
	}
}

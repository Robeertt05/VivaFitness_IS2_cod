/**
 * 
 */
package Controlador;

import Negocio.Sala.SASala;

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
			ctx.setMessage("El ID de la sala debe ser un número entero");
			return ctx;
		}
		
		int idSala = (Integer) datos;
		int resultado = saSala.baja_sala(idSala);
		
		if (resultado > 0) {
			ctx.setSuccess(true);
			ctx.setMessage("Sala eliminada correctamente");
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Error al eliminar la sala. La sala no existe, tiene sesiones activas o ya está inactiva");
		}
		
		return ctx;
	}
}

/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.SASala;
import Integracion.Sesion.TSesion;
import java.util.Set;

/**
 * Command to get all sessions for a specific room
 * CASO 6: Obtener sesiones de una sala (Relación 1-N)
 * @author azuri
 */
public class CommandObtenerSesionesSala implements Command {
	
	private SASala saSala;
	
	public CommandObtenerSesionesSala(SASala saSala) {
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
		Set<TSesion> sesiones = saSala.obtener_sesiones_sala(idSala);
		
		if (sesiones != null && !sesiones.isEmpty()) {
			ctx.setSuccess(true);
			ctx.setMessage("Found " + sesiones.size() + " sessions in this room");
			ctx.setData(sesiones);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("No sessions found for this room");
		}
		
		return ctx;
	}
}

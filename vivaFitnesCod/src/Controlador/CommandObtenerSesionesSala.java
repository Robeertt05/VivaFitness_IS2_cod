/**
 * 
 */
package Controlador;

import Integracion.Sesion.TSesion;
import Negocio.Sala.SASala;
import java.util.Set;

/**
 * Command to get all sessions for a specific room
	 * CASO 6: Obtener sesiones de una sala (Relacion 1-N)
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
			ctx.setMessage("El ID de la sala debe ser un numero entero");
			return ctx;
		}
		
		int idSala = (Integer) datos;
		Set<TSesion> sesiones = saSala.obtener_sesiones_sala(idSala);
		
		if (sesiones != null && !sesiones.isEmpty()) {
			ctx.setSuccess(true);
			ctx.setMessage("Se encontraron " + sesiones.size() + " sesiones en esta sala");
			ctx.setData(sesiones);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("No se encontraron sesiones para esta sala");
		}
		
		return ctx;
	}
}

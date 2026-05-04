/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.SASala;
import Integracion.FactoriaIntegracion.TSala;

/**
 * Command to display a specific room
 * CASO 4: Mostrar sala (SRS)
 * @author azuri
 */
public class CommandMostrarSala implements Command {
	
	private SASala saSala;
	
	public CommandMostrarSala(SASala saSala) {
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
		TSala sala = saSala.mostrar_sala(idSala);
		
		if (sala != null) {
			ctx.setSuccess(true);
			ctx.setMessage("Room found");
			ctx.setData(sala);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Room not found");
		}
		
		return ctx;
	}
}

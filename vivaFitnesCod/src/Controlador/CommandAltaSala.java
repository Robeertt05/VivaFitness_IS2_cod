/**
 * 
 */
package Controlador;

import Negocio.Sala.SASala;
import Integracion.Sala.TSala;

/**
 * Command to create a new room
 * CASO 1: Alta sala (SRS)
 * @author azuri
 */
public class CommandAltaSala implements Command {
	
	private SASala saSala;
	
	public CommandAltaSala(SASala saSala) {
		this.saSala = saSala;
	}

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		if (!(datos instanceof TSala)) {
			ctx.setSuccess(false);
			ctx.setMessage("Invalid room data");
			return ctx;
		}
		
		TSala sala = (TSala) datos;
		int resultado = saSala.alta_sala(sala);
		
		if (resultado > 0) {
			ctx.setSuccess(true);
			ctx.setMessage("Room created successfully with ID: " + resultado);
			ctx.setData(resultado);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Failed to create room");
		}
		
		return ctx;
	}
}

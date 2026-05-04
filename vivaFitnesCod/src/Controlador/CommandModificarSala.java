/**
 * 
 */
package Controlador;

import Negocio.Sala.SASala;
import Integracion.Sala.TSala;

/**
 * Command to update room details
 * CASO 3: Modificar sala (SRS)
 * @author azuri
 */
public class CommandModificarSala implements Command {
	
	private SASala saSala;
	
	public CommandModificarSala(SASala saSala) {
		this.saSala = saSala;
	}

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		if (!(datos instanceof Object[])) {
			ctx.setSuccess(false);
			ctx.setMessage("Invalid data format. Expected [idSala, TSala]");
			return ctx;
		}
		
		Object[] arr = (Object[]) datos;
		if (arr.length != 2 || !(arr[0] instanceof Integer) || !(arr[1] instanceof TSala)) {
			ctx.setSuccess(false);
			ctx.setMessage("Invalid data format. Expected [idSala(int), TSala(object)]");
			return ctx;
		}
		
		int idSala = (Integer) arr[0];
		TSala sala = (TSala) arr[1];
		int resultado = saSala.modificar_sala(idSala, sala);
		
		if (resultado > 0) {
			ctx.setSuccess(true);
			ctx.setMessage("Room updated successfully");
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Failed to update room");
		}
		
		return ctx;
	}
}

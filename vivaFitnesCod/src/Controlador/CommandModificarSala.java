/**
 * 
 */
package Controlador;



import Integracion.Sala.TSala;
import Negocio.Sala.SASala;

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
		
		if (!(datos instanceof TSala)) {
			ctx.setSuccess(false);
			ctx.setMessage("Invalid data format. Expected TSala");
			return ctx;
		}
		
		TSala sala = (TSala) datos;
		if (sala.getIdSala() <= 0) {
			ctx.setSuccess(false);
			ctx.setMessage("Invalid room id");
			return ctx;
		}
		
		int idSala = sala.getIdSala();
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

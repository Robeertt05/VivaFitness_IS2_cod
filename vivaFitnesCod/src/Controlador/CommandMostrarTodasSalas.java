/**
 * 
 */
package Controlador;

import Negocio.Sala.SASala;
import Integracion.Sala.TSala;
import java.util.Set;

/**
 * Command to list all rooms
 * CASO 5: Mostrar todas salas (SRS)
 * @author azuri
 */
public class CommandMostrarTodasSalas implements Command {
	
	private SASala saSala;
	
	public CommandMostrarTodasSalas(SASala saSala) {
		this.saSala = saSala;
	}

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		Set<TSala> salas = saSala.mostrar_todas_salas();
		
		if (salas != null && !salas.isEmpty()) {
			ctx.setSuccess(true);
			ctx.setMessage("Found " + salas.size() + " rooms");
			ctx.setData(salas);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("No rooms found");
		}
		
		return ctx;
	}
}

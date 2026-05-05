/**
 * 
 */
package Controlador;

import Integracion.Sala.TSala;
import Negocio.Sala.SASala;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import java.util.Set;

/**
 * Command to list all rooms
 * CASO 5: Mostrar todas salas (SRS)
 * @author azuri
 */
public class CommandMostrarTodasSalas implements Command {

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		SASala saSala = FactoriaServicioAplicacion.getInstance().generaSASala();
		Set<TSala> salas = saSala.mostrar_todas_salas();
		
		if (salas != null && !salas.isEmpty()) {
			ctx.setSuccess(true);
			ctx.setMessage("Se encontraron " + salas.size() + " salas");
			ctx.setData(salas);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("No se encontraron salas");
		}
		
		return ctx;
	}
}

/**
 * 
 */
package Controlador;

import Negocio.Entrenador.TEntrenador;
import Negocio.FactoriaNegocio.SASesion;

/**
 * Command to display the trainer assigned to a session
 * CASO 5: Mostrar entrenador por sesin (SRS)
 * Get the specific trainer assigned to a particular session
 * @author azuri
 */
public class CommandMostrarEntrenadorSesion implements Command {
	
	private SASesion saSesion;
	
	public CommandMostrarEntrenadorSesion(SASesion saSesion) {
		this.saSesion = saSesion;
	}

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		if (!(datos instanceof Integer)) {
			ctx.setSuccess(false);
			ctx.setMessage("El ID de sesion debe ser un numero entero");
			return ctx;
		}
		
		int idSesion = (Integer) datos;
		TEntrenador entrenador = saSesion.mostrar_entrenador_sesion(idSesion);
		
		if (entrenador != null) {
			ctx.setSuccess(true);
			ctx.setMessage("Entrenador encontrado para la sesion");
			ctx.setData(entrenador);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Sesion no encontrada o sin entrenador asignado");
		}
		
		return ctx;
	}
}

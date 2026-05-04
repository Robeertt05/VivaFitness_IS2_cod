/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Sesion.SASesion;
import Integracion.Sala.TSala;

/**
 * Command to display the room assigned to a session
 * CASO 4: Mostrar sala por sesin (SRS)
 * Get the specific room assigned to a particular session
 * @author azuri
 */
public class CommandMostrarSalaSesion implements Command {

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		if (!(datos instanceof Integer)) {
			ctx.setSuccess(false);
			ctx.setMessage("El ID de sesion debe ser un numero entero");
			return ctx;
		}
		
		int idSesion = (Integer) datos;
		SASesion saSesion = FactoriaServicioAplicacion.getInstance().crearSASesion();
		TSala sala = saSesion.mostrar_sala_sesion(idSesion);
		
		if (sala != null) {
			ctx.setSuccess(true);
			ctx.setMessage("Sala encontrada para la sesion");
			ctx.setData(sala);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Sesion no encontrada o sin sala asignada");
		}
		
		return ctx;
	}
}

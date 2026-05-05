/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Sesion.SASesion;
import Integracion.Sesion.TSesion;
import java.util.Set;

/**
 * Command to list all sessions
 * @author azuri
 */
public class CommandMostrarTodasSesiones implements Command {

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		SASesion saSesion = FactoriaServicioAplicacion.getInstance().crearSASesion();
		Set<TSesion> sesiones = saSesion.mostrar_todas_sesiones();
		
		if (sesiones != null && !sesiones.isEmpty()) {
			ctx.setSuccess(true);
			ctx.setMessage("Se encontraron " + sesiones.size() + " sesiones");
			ctx.setData(sesiones);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("No se encontraron sesiones");
		}
		
		return ctx;
	}
}

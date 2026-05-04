/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Sesion.SASesion;

/**
 * Command to delete a session
 * CASO 1: Baja sesin (SRS)
 * Precondition: Session must not have registered clients
 * @author azuri
 */
public class CommandEliminarSesion implements Command {

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		if (!(datos instanceof Integer)) {
			ctx.setSuccess(false);
			ctx.setMessage("El ID de sesion debe ser un numero entero");
			return ctx;
		}
		
		int idSesion = (Integer) datos;
		try {
			SASesion saSesion = FactoriaServicioAplicacion.getInstance().crearSASesion();
			int resultado = saSesion.baja_sesion(idSesion);
			ctx.setSuccess(true);
			ctx.setMessage("Sesion eliminada correctamente");
			ctx.setData(resultado);
		} catch (Exception e) {
			ctx.setSuccess(false);
			ctx.setMessage(e.getMessage());
		}
		
		return ctx;
	}
}

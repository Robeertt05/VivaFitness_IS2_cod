/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.SASesion;
import Integracion.FactoriaIntegracion.TSesion;

/**
 * Command to display a specific session
 * CASO 3: Mostrar sesin (SRS)
 * @author azuri
 */
public class CommandMostrarSesion implements Command {
	
	private SASesion saSesion;
	
	public CommandMostrarSesion(SASesion saSesion) {
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
		TSesion sesion = saSesion.mostrar_sesion(idSesion);
		
		if (sesion != null) {
			ctx.setSuccess(true);
			ctx.setMessage("Sesion encontrada");
			ctx.setData(sesion);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Sesion no encontrada");
		}
		
		return ctx;
	}
}

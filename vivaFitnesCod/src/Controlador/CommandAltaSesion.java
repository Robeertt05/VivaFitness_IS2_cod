/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.SASesion;
import Integracion.Sesion.TSesion;

/**
 * Command to create a new session
 * @author azuri
 */
public class CommandAltaSesion implements Command {
	
	private SASesion saSesion;
	
	public CommandAltaSesion(SASesion saSesion) {
		this.saSesion = saSesion;
	}

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		if (!(datos instanceof TSesion)) {
			ctx.setSuccess(false);
			ctx.setMessage("Datos de sesion no validos");
			return ctx;
		}
		
		TSesion sesion = (TSesion) datos;
		try {
			int resultado = saSesion.alta_sesion(sesion);
			ctx.setSuccess(true);
			ctx.setMessage("Sesion creada correctamente con ID: " + resultado);
			ctx.setData(resultado);
		} catch (Exception e) {
			ctx.setSuccess(false);
			ctx.setMessage(e.getMessage());
		}
		
		return ctx;
	}
}

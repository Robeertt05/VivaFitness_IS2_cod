/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.SASesion;
import Integracion.FactoriaIntegracion.TSesion;

/**
 * Command to create a new session
 * @author azuri
 */
public class CommandCrearSesion implements Command {
	
	private SASesion saSesion;
	
	public CommandCrearSesion(SASesion saSesion) {
		this.saSesion = saSesion;
	}

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		if (!(datos instanceof TSesion)) {
			ctx.setSuccess(false);
			ctx.setMessage("Invalid session data");
			return ctx;
		}
		
		TSesion sesion = (TSesion) datos;
		int resultado = saSesion.alta_sesion(sesion);
		
		if (resultado > 0) {
			ctx.setSuccess(true);
			ctx.setMessage("Session created successfully with ID: " + resultado);
			ctx.setData(resultado);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Failed to create session");
		}
		
		return ctx;
	}
}

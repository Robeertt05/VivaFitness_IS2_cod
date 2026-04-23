/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.SASesion;
import Integracion.FactoriaIntegracion.TSesion;

/**
 * Command to modify an existing session
 * CASO 2: Modificar sesión (SRS)
 * @author azuri
 */
public class CommandModificarSesion implements Command {
	
	private SASesion saSesion;
	
	public CommandModificarSesion(SASesion saSesion) {
		this.saSesion = saSesion;
	}

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		if (!(datos instanceof Object[])) {
			ctx.setSuccess(false);
			ctx.setMessage("Invalid parameters");
			return ctx;
		}
		
		Object[] params = (Object[]) datos;
		if (params.length < 2 || !(params[0] instanceof Integer) || !(params[1] instanceof TSesion)) {
			ctx.setSuccess(false);
			ctx.setMessage("Expected (Integer sessionId, TSesion sesion)");
			return ctx;
		}
		
		int idSesion = (Integer) params[0];
		TSesion sesion = (TSesion) params[1];
		
		int resultado = saSesion.modificar_sesion(idSesion, sesion);
		
		if (resultado > 0) {
			ctx.setSuccess(true);
			ctx.setMessage("Session modified successfully");
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Failed to modify session");
		}
		
		return ctx;
	}
}

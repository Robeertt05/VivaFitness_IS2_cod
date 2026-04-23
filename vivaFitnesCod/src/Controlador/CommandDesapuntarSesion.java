/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.SASesion;

/**
 * Command to unregister a client from a session
 * @author azuri
 */
public class CommandDesapuntarSesion implements Command {
	
	private SASesion saSesion;
	
	public CommandDesapuntarSesion(SASesion saSesion) {
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
		if (params.length < 2 || !(params[0] instanceof Integer) || !(params[1] instanceof Integer)) {
			ctx.setSuccess(false);
			ctx.setMessage("Expected (Integer sessionId, Integer clientId)");
			return ctx;
		}
		
		int idSesion = (Integer) params[0];
		int idCliente = (Integer) params[1];
		
		int resultado = saSesion.desapuntar_cliente_sesion(idSesion, idCliente);
		
		if (resultado > 0) {
			ctx.setSuccess(true);
			ctx.setMessage("Client unregistered from session successfully");
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Failed to unregister client from session");
		}
		
		return ctx;
	}
}

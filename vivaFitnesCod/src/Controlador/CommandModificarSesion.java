/**
 * 
 */
package Controlador;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.FactoriaNegocio.SASesion;
import Integracion.Sesion.TSesion;

/**
 * Command to modify an existing session
 * CASO 2: Modificar sesin (SRS)
 * @author azuri
 */
public class CommandModificarSesion implements Command {
	
	private SASesion saSesion;

	public CommandModificarSesion() {
		this(FactoriaServicioAplicacion.getInstance().crearSASesion());
	}
	
	public CommandModificarSesion(SASesion saSesion) {
		this.saSesion = saSesion;
	}

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();
		
		if (!(datos instanceof Object[])) {
			ctx.setSuccess(false);
			ctx.setMessage("Parametros no validos");
			return ctx;
		}
		
		Object[] params = (Object[]) datos;
		if (params.length < 2 || !(params[0] instanceof Integer) || !(params[1] instanceof TSesion)) {
			ctx.setSuccess(false);
			ctx.setMessage("Se esperaba un ID de sesion y los datos de la sesion");
			return ctx;
		}
		
		int idSesion = (Integer) params[0];
		TSesion sesion = (TSesion) params[1];
		
		try {
			int resultado = saSesion.modificar_sesion(idSesion, sesion);
			ctx.setSuccess(true);
			ctx.setMessage("Sesion modificada correctamente");
			ctx.setData(resultado);
		} catch (Exception e) {
			ctx.setSuccess(false);
			ctx.setMessage(e.getMessage());
		}
		
		return ctx;
	}
}

 
package Controlador;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Sesion.SASesion;
import Integracion.Sesion.TSesion;

 
public class CommandMostrarSesion implements Command {

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

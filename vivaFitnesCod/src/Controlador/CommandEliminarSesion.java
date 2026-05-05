
package Controlador;

import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Sesion.SASesion;


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
			if (resultado == -2) {
				ctx.setSuccess(false);
				ctx.setMessage("No se puede dar de baja la sesion porque tiene clientes inscritos activos.\nDesapunte a los clientes antes de eliminar la sesion.");
			} else {
				ctx.setSuccess(true);
				ctx.setMessage("Sesion eliminada correctamente. Las relaciones con sala y entrenador han sido eliminadas.");
				ctx.setData(resultado);
			}
		} catch (Exception e) {
			ctx.setSuccess(false);
			ctx.setMessage(e.getMessage());
		}
		
		return ctx;
	}
}

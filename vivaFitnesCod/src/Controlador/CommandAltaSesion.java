
package Controlador;

import Integracion.Sesion.TSesion;
import Negocio.Entrenador.SAEntrenador;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;

public class CommandAltaSesion implements Command {

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
			SAEntrenador saEntrenador = FactoriaServicioAplicacion.getInstance().crearSAEntrenador();
			int resultado = saEntrenador.crear_sesion(sesion);
			ctx.setSuccess(true);
			ctx.setMessage("Sesion creada correctamente con ID: " + resultado);
			ctx.setData(resultado);
		} catch (IllegalArgumentException e) {
			ctx.setSuccess(false);
			ctx.setMessage("Datos invalidos: " + e.getMessage());
		} catch (RuntimeException e) {
			ctx.setSuccess(false);
			ctx.setMessage("Error de base de datos: " + e.getMessage());
		} catch (Exception e) {
			ctx.setSuccess(false);
			ctx.setMessage("Error al crear sesion: " + e.getMessage());
		}
		
		return ctx;
	}
}

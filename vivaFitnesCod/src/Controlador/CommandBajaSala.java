
package Controlador;

import Negocio.Sala.SASala;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;


public class CommandBajaSala implements Command {

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();

		if (!(datos instanceof Integer)) {
			ctx.setSuccess(false);
			ctx.setMessage("El ID de la sala debe ser un numero entero");
			return ctx;
		}
		int idSala = (Integer) datos;
		SASala saSala = FactoriaServicioAplicacion.getInstance().generaSASala();
		int resultado = saSala.baja_sala(idSala);
		
		if (resultado > 0) {
			ctx.setSuccess(true);
			ctx.setMessage("Sala eliminada correctamente");
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Error al eliminar la sala. La sala no existe, tiene sesiones activas o ya esta inactiva");
		}
		
		return ctx;
	}
}

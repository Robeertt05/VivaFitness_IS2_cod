 
package Controlador;

import Integracion.Sesion.TSesion;
import Negocio.Sala.SASala;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import java.util.Set;

 
public class CommandObtenerSesionesSala implements Command {

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
		Set<TSesion> sesiones = saSala.obtener_sesiones_sala(idSala);
		
		if (sesiones != null && !sesiones.isEmpty()) {
			ctx.setSuccess(true);
			ctx.setMessage("Se encontraron " + sesiones.size() + " sesiones en esta sala");
			ctx.setData(sesiones);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("No se encontraron sesiones para esta sala");
		}
		
		return ctx;
	}
}

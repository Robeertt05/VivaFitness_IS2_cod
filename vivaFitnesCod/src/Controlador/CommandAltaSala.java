package Controlador;

import Integracion.Sala.TSala;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.Sala.SASala;

/**
 * Command to create a new room
 * CASO 1: Alta sala (SRS)
 * @author azuri
 */
public class CommandAltaSala implements Command {

	@Override
	public Context execute(Object datos) {
		Context ctx = new Context();

		if (!(datos instanceof TSala)) {
			ctx.setSuccess(false);
			ctx.setMessage("Datos de sala invalidos");
			return ctx;
		}

		TSala sala = (TSala) datos;
		SASala saSala = FactoriaServicioAplicacion.getInstance().generaSASala();
		int resultado = saSala.alta_sala(sala);
		if (resultado > 0) {
			ctx.setSuccess(true);
			ctx.setMessage("Sala creada correctamente con ID: " + resultado);
			ctx.setData(resultado);
		} else {
			ctx.setSuccess(false);
			ctx.setMessage("Error al crear la sala");
		}
		return ctx;
	}
}

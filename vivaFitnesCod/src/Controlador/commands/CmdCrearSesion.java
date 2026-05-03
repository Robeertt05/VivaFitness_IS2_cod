/**
 * Command: Crear Sesion de entrenamiento.
 * Patron: Command.
 */
package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.entrenador.SAEntrenador;
import Negocio.entrenador.TEntrenador;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdCrearSesion implements Command {

	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			TEntrenador t = (TEntrenador) datos;
			SAEntrenador sa = FactoriaServicioAplicacion.getInstance().crearSAEntrenador();
			int res = sa.crear_sesion(t.get_id(), t);
			if (res >= 0) {
				resultado.setEvento(Evento.RES_CREAR_SESION_OK);
				resultado.setObjeto(res);
			} else {
				resultado.setEvento(Evento.RES_CREAR_SESION_KO);
				resultado.setObjeto(null);
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_CREAR_SESION_KO);
			resultado.setObjeto(null);
		}
		return resultado;
	}
}

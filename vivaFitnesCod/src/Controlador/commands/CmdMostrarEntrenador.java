/**
 * Command: Mostrar datos de un Entrenador.
 * Patron: Command.
 */
package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.Entrenador.SAEntrenador;
import Negocio.Entrenador.TEntrenador;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdMostrarEntrenador implements Command {

	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			int id = (Integer) datos;
			SAEntrenador sa = FactoriaServicioAplicacion.getInstance().crearSAEntrenador();
			TEntrenador entrenador = sa.mostrar_entrenador(id);
			if (entrenador != null) {
				resultado.setEvento(Evento.RES_MOSTRAR_ENTRENADOR_OK);
				resultado.setObjeto(entrenador);
			} else {
				resultado.setEvento(Evento.RES_MOSTRAR_ENTRENADOR_KO);
				resultado.setObjeto(null);
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_MOSTRAR_ENTRENADOR_KO);
			resultado.setObjeto(null);
		}
		return resultado;
	}
}

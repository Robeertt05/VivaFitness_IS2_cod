/**
 * Command: Mostrar todos los Entrenadores.
 * Patron: Command.
 */
package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.Entrenador.SAEntrenador;
import Negocio.Entrenador.TEntrenador;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;
import java.util.Set;

public class CmdMostrarEntrenadores implements Command {

	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			SAEntrenador sa = FactoriaServicioAplicacion.getInstance().crearSAEntrenador();
			Set<TEntrenador> lista = sa.mostrar_entrenadores();
			if (lista != null) {
				resultado.setEvento(Evento.RES_MOSTRAR_ENTRENADORES_OK);
				resultado.setObjeto(lista);
			} else {
				resultado.setEvento(Evento.RES_MOSTRAR_ENTRENADORES_KO);
				resultado.setObjeto(null);
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_MOSTRAR_ENTRENADORES_KO);
			resultado.setObjeto(null);
		}
		return resultado;
	}
}

/**
 * Command: Modificar datos de Entrenador.
 * Patron: Command.
 */
package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.entrenador.SAentrrenador;
import Negocio.entrenador.TEntrenador;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdModificarEntrenador implements Command {

	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			TEntrenador t = (TEntrenador) datos;
			SAentrrenador sa = FactoriaServicioAplicacion.getInstance().crearSAEntrenador();
			int res = sa.modificar_entrenador(t.get_id(), t);
			if (res >= 0) {
				resultado.setEvento(Evento.RES_MODIFICAR_ENTRENADOR_OK);
				resultado.setObjeto(t);
			} else {
				resultado.setEvento(Evento.RES_MODIFICAR_ENTRENADOR_KO);
				resultado.setObjeto(null);
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_MODIFICAR_ENTRENADOR_KO);
			resultado.setObjeto(null);
		}
		return resultado;
	}
}

/**
 * Command: Alta de Entrenador.
 * Patron: Command - encapsula la logica de invocacion del SA.
 */
package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.entrenador.SAEntrenador;
import Negocio.entrenador.TEntrenador;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdAltaEntrenador implements Command {

	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			TEntrenador t = (TEntrenador) datos;
			SAEntrenador sa = FactoriaServicioAplicacion.getInstance().crearSAEntrenador();
			int id = sa.alta_entrenador(t);
			if (id >= 0) {
				resultado.setEvento(Evento.RES_ALTA_ENTRENADOR_OK);
				resultado.setObjeto(id);
			} else {
				resultado.setEvento(Evento.RES_ALTA_ENTRENADOR_KO);
				resultado.setObjeto(null);
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_ALTA_ENTRENADOR_KO);
			resultado.setObjeto(null);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}
}

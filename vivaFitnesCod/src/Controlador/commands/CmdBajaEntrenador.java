/**
 * Command: Baja logica de Entrenador.
 * Patron: Command.
 */
package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.entrenador.SAEntrenador;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdBajaEntrenador implements Command {

	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			int id = (Integer) datos;
			SAEntrenador sa = FactoriaServicioAplicacion.getInstance().crearSAEntrenador();
			int res = sa.baja_entrenador(id);
			if (res >= 0) {
				resultado.setEvento(Evento.RES_BAJA_ENTRENADOR_OK);
				resultado.setObjeto(id);
			} else {
				resultado.setEvento(Evento.RES_BAJA_ENTRENADOR_KO);
				resultado.setObjeto(null);
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_BAJA_ENTRENADOR_KO);
			resultado.setObjeto(null);
		}
		return resultado;
	}
}

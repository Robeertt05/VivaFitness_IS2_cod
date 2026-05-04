package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.Cliente.SACliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdDesapuntarseSesion implements Command {
	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			Object[] params = (Object[]) datos;
			int idCliente = (Integer) params[0];
			int idSesion = (Integer) params[1];
			SACliente sa = FactoriaServicioAplicacion.getInstance().crearSACliente();
			int res = sa.desapuntar_sesion(idCliente, idSesion);
			resultado.setObjeto(res);
			resultado.setSuccess(res > 0);
			resultado.setEvento(res > 0 ? Evento.RES_DESAPUNTARSE_SESION_OK : Evento.RES_DESAPUNTARSE_SESION_KO);
			resultado.setMessage(res > 0 ? "Cliente desapuntado correctamente." : "No habia inscripcion para eliminar.");
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_DESAPUNTARSE_SESION_KO);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}
}

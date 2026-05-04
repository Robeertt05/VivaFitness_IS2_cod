package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.Cliente.SACliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdMostrarSesionesCliente implements Command {
	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			Integer idCliente = (Integer) datos;
			SACliente sa = FactoriaServicioAplicacion.getInstance().crearSACliente();
			sa.mostrar_sesiones(idCliente);
			resultado.setEvento(Evento.RES_MOSTRAR_SESIONES_CLIENTE_OK);
			resultado.setObjeto(idCliente);
			resultado.setMessage("Sesiones del cliente mostradas");
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_MOSTRAR_SESIONES_CLIENTE_KO);
			resultado.setObjeto(null);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}
}

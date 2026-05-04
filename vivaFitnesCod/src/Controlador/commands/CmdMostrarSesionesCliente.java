package Controlador.commands;

import java.util.Set;

import Controlador.Command;
import Controlador.Context;
import Integracion.Sesion.TSesion;
import Negocio.Cliente.SACliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdMostrarSesionesCliente implements Command {
	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			int idCliente = (Integer) datos;
			SACliente sa = FactoriaServicioAplicacion.getInstance().crearSACliente();
			Set<TSesion> sesiones = sa.mostrar_sesiones_cliente(idCliente);
			resultado.setObjeto(sesiones);
			resultado.setSuccess(sesiones != null);
			resultado.setEvento(sesiones != null ? Evento.RES_MOSTRAR_SESIONES_CLIENTE_OK : Evento.RES_MOSTRAR_SESIONES_CLIENTE_KO);
			resultado.setMessage(sesiones != null ? "Sesiones del cliente cargadas." : "No se pudieron cargar las sesiones.");
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_MOSTRAR_SESIONES_CLIENTE_KO);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}
}

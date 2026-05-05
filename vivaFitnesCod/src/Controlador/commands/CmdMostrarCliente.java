package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Integracion.Cliente.TCliente;
import Negocio.Cliente.SACliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdMostrarCliente implements Command {

	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			int id = (Integer) datos;
			SACliente saCliente = FactoriaServicioAplicacion.getInstance().crearSACliente();
			TCliente cliente = saCliente.mostrar_cliente(id);
			resultado.setObjeto(cliente);
			resultado.setSuccess(cliente != null);
			resultado.setEvento(cliente != null ? Evento.RES_MOSTRAR_CLIENTE_OK : Evento.RES_MOSTRAR_CLIENTE_KO);
			resultado.setMessage(cliente != null ? "Cliente encontrado." : "Cliente no encontrado.");
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_MOSTRAR_CLIENTE_KO);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}
}

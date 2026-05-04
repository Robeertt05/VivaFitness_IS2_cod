package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.Cliente.SACliente;
import Integracion.Cliente.TCliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdMostrarCliente implements Command {
	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			Integer id = (Integer) datos;
			SACliente sa = FactoriaServicioAplicacion.getInstance().crearSACliente();
			TCliente cliente = sa.mostrar_cliente(id);
			if (cliente != null) {
				resultado.setEvento(Evento.RES_MOSTRAR_CLIENTE_OK);
				resultado.setObjeto(cliente);
			} else {
				resultado.setEvento(Evento.RES_MOSTRAR_CLIENTE_KO);
				resultado.setObjeto(null);
				resultado.setMessage("Cliente no encontrado");
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_MOSTRAR_CLIENTE_KO);
			resultado.setObjeto(null);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}
}

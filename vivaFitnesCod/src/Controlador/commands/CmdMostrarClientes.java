package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.Cliente.SACliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;
import java.util.Set;

public class CmdMostrarClientes implements Command {
	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			SACliente sa = FactoriaServicioAplicacion.getInstance().crearSACliente();
			Set<?> clientes = sa.mostrar_todos_clientes();
			if (clientes != null && !clientes.isEmpty()) {
				resultado.setEvento(Evento.RES_MOSTRAR_CLIENTES_OK);
				resultado.setObjeto(clientes);
			} else {
				resultado.setEvento(Evento.RES_MOSTRAR_CLIENTES_KO);
				resultado.setObjeto(null);
				resultado.setMessage("No hay clientes disponibles");
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_MOSTRAR_CLIENTES_KO);
			resultado.setObjeto(null);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}
}

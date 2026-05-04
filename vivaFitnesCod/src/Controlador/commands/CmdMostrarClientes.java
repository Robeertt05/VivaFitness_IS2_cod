package Controlador.commands;

import java.util.Set;

import Controlador.Command;
import Controlador.Context;
import Integracion.Cliente.TCliente;
import Negocio.Cliente.SACliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdMostrarClientes implements Command {
	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			SACliente sa = FactoriaServicioAplicacion.getInstance().crearSACliente();
			Set<TCliente> clientes = sa.mostrar_todos_clientes();
			resultado.setObjeto(clientes);
			resultado.setSuccess(clientes != null);
			resultado.setEvento(clientes != null ? Evento.RES_MOSTRAR_CLIENTES_OK : Evento.RES_MOSTRAR_CLIENTES_KO);
			resultado.setMessage(clientes != null ? "Clientes cargados." : "No se pudieron cargar los clientes.");
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_MOSTRAR_CLIENTES_KO);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}
}

package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.Cliente.SACliente;
import Integracion.Cliente.TCliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdModificarCliente implements Command {
	private final SACliente saCliente;

	public CmdModificarCliente() {
		this(FactoriaServicioAplicacion.getInstance().crearSACliente());
	}

	public CmdModificarCliente(SACliente saCliente) {
		this.saCliente = saCliente;
	}

	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			TCliente t = (TCliente) datos;
			int res = saCliente.modificar_cliente(t.getId(), t);
			if (res > 0) {
				resultado.setEvento(Evento.RES_MODIFICAR_CLIENTE_OK);
				resultado.setObjeto(t);
				resultado.setSuccess(true);
				resultado.setMessage("Cliente modificado correctamente.");
			} else {
				resultado.setEvento(Evento.RES_MODIFICAR_CLIENTE_KO);
				resultado.setObjeto(null);
				resultado.setMessage("No se pudo modificar el cliente.");
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_MODIFICAR_CLIENTE_KO);
			resultado.setObjeto(null);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}
}

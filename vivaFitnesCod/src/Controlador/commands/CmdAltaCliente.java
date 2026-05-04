package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.Cliente.SACliente;
import Integracion.Cliente.TCliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdAltaCliente implements Command {
	private final SACliente saCliente;

	public CmdAltaCliente() {
		this(FactoriaServicioAplicacion.getInstance().crearSACliente());
	}

	public CmdAltaCliente(SACliente saCliente) {
		this.saCliente = saCliente;
	}

	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			TCliente t = (TCliente) datos;
			int id = saCliente.alta_cliente(t);
			if (id > 0) {
				resultado.setEvento(Evento.RES_ALTA_CLIENTE_OK);
				resultado.setObjeto(id);
				resultado.setSuccess(true);
				resultado.setMessage("Cliente creado correctamente con ID: " + id);
			} else {
				resultado.setEvento(Evento.RES_ALTA_CLIENTE_KO);
				resultado.setObjeto(null);
				resultado.setMessage("No se pudo crear el cliente. Revise DNI duplicado y datos obligatorios.");
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_ALTA_CLIENTE_KO);
			resultado.setObjeto(null);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}
}

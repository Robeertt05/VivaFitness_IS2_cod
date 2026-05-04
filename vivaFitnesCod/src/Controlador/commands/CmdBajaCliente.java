package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.Cliente.SACliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdBajaCliente implements Command {

	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			int id = (Integer) datos;
			SACliente saCliente = FactoriaServicioAplicacion.getInstance().crearSACliente();
			int res = saCliente.baja_cliente(id);
			if (res > 0) {
				resultado.setEvento(Evento.RES_BAJA_CLIENTE_OK);
				resultado.setObjeto(id);
				resultado.setSuccess(true);
				resultado.setMessage("Cliente dado de baja correctamente.");
			} else {
				resultado.setEvento(Evento.RES_BAJA_CLIENTE_KO);
				resultado.setObjeto(null);
				resultado.setMessage("No se pudo dar de baja el cliente.");
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_BAJA_CLIENTE_KO);
			resultado.setObjeto(null);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}
}

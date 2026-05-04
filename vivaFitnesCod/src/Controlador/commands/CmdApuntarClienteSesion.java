package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.Cliente.SACliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdApuntarClienteSesion implements Command {
	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			Object[] params = (Object[]) datos;
			Integer idCliente = (Integer) params[0];
			Integer idSesion = (Integer) params[1];
			
			SACliente sa = FactoriaServicioAplicacion.getInstance().crearSACliente();
			int res = sa.apuntarse_sesion(idCliente, idSesion);
			
			if (res >= 0) {
				resultado.setEvento(Evento.RES_APUNTAR_CLIENTE_SESION_OK);
				resultado.setObjeto(res);
				resultado.setMessage("Cliente apuntado a sesión correctamente");
			} else {
				resultado.setEvento(Evento.RES_APUNTAR_CLIENTE_SESION_KO);
				resultado.setObjeto(null);
				resultado.setMessage("Error al apuntar cliente a sesión");
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_APUNTAR_CLIENTE_SESION_KO);
			resultado.setObjeto(null);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}
}

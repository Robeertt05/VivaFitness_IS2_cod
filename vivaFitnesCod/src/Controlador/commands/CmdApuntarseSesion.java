package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Integracion.Sesion.TClienteSesion;
import Negocio.Cliente.SACliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdApuntarseSesion implements Command {
	private final SACliente saCliente;

	public CmdApuntarseSesion() {
		this(FactoriaServicioAplicacion.getInstance().crearSACliente());
	}

	public CmdApuntarseSesion(SACliente saCliente) {
		this.saCliente = saCliente;
	}

	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			int res = saCliente.apuntarse_sesion((TClienteSesion) datos);
			if (res > 0) {
				resultado.setEvento(Evento.RES_APUNTARSE_SESION_OK);
				resultado.setSuccess(true);
				resultado.setMessage("Cliente apuntado a la sesion correctamente.");
			} else {
				resultado.setEvento(Evento.RES_APUNTARSE_SESION_KO);
				resultado.setMessage(mensajeError(res));
			}
			resultado.setObjeto(res);
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_APUNTARSE_SESION_KO);
			resultado.setMessage(e.getMessage());
		}
		return resultado;
	}

	private String mensajeError(int codigo) {
		switch (codigo) {
			case -2: return "El cliente no existe o no esta activo.";
			case -3: return "La sesion no existe o no esta activa.";
			case -4: return "El cliente ya esta apuntado a esta sesion.";
			case -5: return "La sesion no tiene plazas disponibles.";
			default: return "No se pudo apuntar al cliente a la sesion.";
		}
	}
}

package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Integracion.Sesion.TClienteSesion;
import Negocio.Cliente.SACliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdApuntarseSesion implements Command {

	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			SACliente saCliente = FactoriaServicioAplicacion.getInstance().crearSACliente();
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
			case -2: return "El cliente no existe o no está activo.";
			case -3: return "La sesión no existe o no está activa.";
			case -4: return "El cliente ya está apuntado a esta sesión.";
			case -5: return "La sesión no tiene plazas disponibles.";
			case -6: return "La fecha u hora son inválidas o error al procesar los datos.";
			case -7: return "El registro debe efectuarse el mismo día que la sesión.";
			case -8: return "Debe registrarse como mínimo 10 minutos antes de que comience la sesión.";
			default: return "No se pudo apuntar al cliente a la sesión.";
		}
	}
}

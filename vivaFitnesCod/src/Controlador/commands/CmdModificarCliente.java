package Controlador.commands;

import Controlador.Command;
import Controlador.Context;
import Negocio.Cliente.SACliente;
import Integracion.Cliente.TCliente;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

public class CmdModificarCliente implements Command {
	@Override
	public Context execute(Object datos) {
		Context resultado = new Context();
		try {
			TCliente t = (TCliente) datos;
			SACliente sa = FactoriaServicioAplicacion.getInstance().crearSACliente();
			int res = sa.modificar_cliente(t.getId(), t);
			if (res >= 0) {
				resultado.setEvento(Evento.RES_MODIFICAR_CLIENTE_OK);
				resultado.setObjeto(t);
			} else {
				resultado.setEvento(Evento.RES_MODIFICAR_CLIENTE_KO);
				resultado.setObjeto(null);
			}
		} catch (Exception e) {
			resultado.setEvento(Evento.RES_MODIFICAR_CLIENTE_KO);
			resultado.setObjeto(null);
		}
		return resultado;
	}
}


package Controlador;

import Controlador.commands.*;
import Presentacion.FactoriaPresentacion.Evento;

public class CommandFactory {

	private static CommandFactory instance;

	private CommandFactory() {}

	 
	public static CommandFactory getInstance() {
		if (instance == null) {
			instance = new CommandFactory();
		}
		return instance;
	}

	 
	public Command getCommand(Evento evento) {
		switch (evento) { 
			case ALTA_ENTRENADOR:
				return new CmdAltaEntrenador();
			case BAJA_ENTRENADOR:
				return new CmdBajaEntrenador();
			case MODIFICAR_ENTRENADOR:
				return new CmdModificarEntrenador();
			case MOSTRAR_ENTRENADOR:
				return new CmdMostrarEntrenador();

			case CREAR_SESION:
				return new CmdCrearSesion();
			 
			case ALTA_CLIENTE:
				return new CmdAltaCliente();
			case BAJA_CLIENTE:
				return new CmdBajaCliente();
			case MODIFICAR_CLIENTE:
				return new CmdModificarCliente();
			case MOSTRAR_CLIENTE:
				return new CmdMostrarCliente();
			case MOSTRAR_CLIENTES:
				return new CmdMostrarClientes();
			case APUNTARSE_SESION:
				return new CmdApuntarseSesion();
			case DESAPUNTARSE_SESION:
				return new CmdDesapuntarseSesion();
			case MOSTRAR_SESIONES_DISPONIBLES_CLIENTE:
				return new CmdMostrarSesionesDisponiblesCliente();
			case MOSTRAR_SESIONES_CLIENTE:
				return new CmdMostrarSesionesCliente();
 
			case ALTA_SALA:
				return new CommandAltaSala();
			case BAJA_SALA:
				return new CommandBajaSala();
			case MODIFICAR_SALA:
				return new CommandModificarSala();
		case MOSTRAR_SALA:
			return new CommandMostrarSala();
		case MOSTRAR_TODAS_SALAS:
			return new CommandMostrarTodasSalas();
		case OBTENER_SESIONES_SALA:
			return new CommandObtenerSesionesSala();
			 
			case ALTA_SESION:
				return new CommandAltaSesion();
			case BAJA_SESION:
				return new CommandEliminarSesion();
			case MODIFICAR_SESION:
				return new CommandModificarSesion();
			case MOSTRAR_SESION:
				return new CommandMostrarSesion();
			case MOSTRAR_SALA_SESION:
				return new CommandMostrarSalaSesion();
			case MOSTRAR_ENTRENADOR_SESION:
				return new CommandMostrarEntrenadorSesion();
			
			default:
				return null;
		}

	}
}

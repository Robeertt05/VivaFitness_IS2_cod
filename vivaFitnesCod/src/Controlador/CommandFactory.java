/**
 * Factoria de Commands.
 * Patron: Command + Factory Method + Singleton.
 * Mapea cada evento (int) a su Command concreto correspondiente.
 */
package Controlador;

import Controlador.commands.*;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Presentacion.FactoriaPresentacion.Evento;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class CommandFactory {

	/** Unica instancia (Singleton). */
	private static CommandFactory instance;

	/** Constructor privado para forzar uso del Singleton. */
	private CommandFactory() {}

	/**
	 * Devuelve la unica instancia de CommandFactory (Singleton).
	 * @return instancia de CommandFactory
	 */
	public static CommandFactory getInstance() {
		if (instance == null) {
			instance = new CommandFactory();
		}
		return instance;
	}

	/**
	 * Devuelve el Command asociado al evento (int) recibido.
	 * @param evento constante int de la clase Evento
	 * @return Command a ejecutar, o null si no tiene Command asociado
	 */
	public Command getCommand(Evento evento) {
		switch (evento) {
			// --- Entrenador ---
			case ALTA_ENTRENADOR:
				return new CmdAltaEntrenador();
			case BAJA_ENTRENADOR:
				return new CmdBajaEntrenador();
			case MODIFICAR_ENTRENADOR:
				return new CmdModificarEntrenador();
			case MOSTRAR_ENTRENADOR:
				return new CmdMostrarEntrenador();
			case MOSTRAR_ENTRENADORES:
				return new CmdMostrarEntrenadores();
			case CREAR_SESION:
				return new CmdCrearSesion();
			
			// --- Cliente ---
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

			// --- Sala ---
			case ALTA_SALA:
				return new CommandAltaSala();
			case BAJA_SALA:
				return new CommandBajaSala();
			case MODIFICAR_SALA:
				return new CommandModificarSala();
			case MOSTRAR_SALA:
			return new CommandMostrarSala(FactoriaServicioAplicacion.getInstance().generaSASala());
		case MOSTRAR_TODAS_SALAS:
			return new CommandMostrarTodasSalas(FactoriaServicioAplicacion.getInstance().generaSASala());
		case OBTENER_SESIONES_SALA:
			return new CommandObtenerSesionesSala(FactoriaServicioAplicacion.getInstance().generaSASala());
			
			// --- Sesion ---
			case ALTA_SESION:
				return new CommandAltaSesion();
			case BAJA_SESION:
				return new CommandEliminarSesion();
			case MODIFICAR_SESION:
				return new CommandModificarSesion();
			case MOSTRAR_SESION:
				return new CommandMostrarSesion();
			case MOSTRAR_TODAS_SESIONES:
				return new CommandMostrarTodasSesiones();
			case MOSTRAR_SALA_SESION:
				return new CommandMostrarSalaSesion();
			case MOSTRAR_ENTRENADOR_SESION:
				return new CommandMostrarEntrenadorSesion();
			
			default:
				return null;
		}

	}
}

/**
 * Factoria de Commands.
 * Patron: Command + Factory Method + Singleton.
 * Mapea cada evento (int) a su Command concreto correspondiente.
 */
package Controlador;

import Presentacion.FactoriaPresentacion.Evento;
import Controlador.commands.*;

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
	public Command getCommand(int evento) {
		switch (evento) {
			// --- Entrenador ---
			case Evento.ALTA_ENTRENADOR:
				return new CmdAltaEntrenador();
			case Evento.BAJA_ENTRENADOR:
				return new CmdBajaEntrenador();
			case Evento.MODIFICAR_ENTRENADOR:
				return new CmdModificarEntrenador();
			case Evento.MOSTRAR_ENTRENADOR:
				return new CmdMostrarEntrenador();
			case Evento.MOSTRAR_ENTRENADORES:
				return new CmdMostrarEntrenadores();
			case Evento.CREAR_SESION:
				return new CmdCrearSesion();
			// --- Cliente ---
			case Evento.ALTA_CLIENTE:
				return new CmdAltaCliente();
			case Evento.BAJA_CLIENTE:
				return new CmdBajaCliente();
			case Evento.MODIFICAR_CLIENTE:
				return new CmdModificarCliente();
			default:
				return null;
		}
	}
}

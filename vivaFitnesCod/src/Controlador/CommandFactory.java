/**
 * Factoria de Commands.
 * Patron: Command + Factory Method + Singleton.
 * Mapea cada evento (int) a su Command concreto correspondiente.
 */
package Controlador;

import Presentacion.FactoriaPresentacion.Evento;
import Controlador.commands.*;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.FactoriaNegocio.SASesion;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class CommandFactory {

	/** Unica instancia (Singleton). */
	private static CommandFactory instance;
	private final SASesion saSesion;

	/** Constructor privado para forzar uso del Singleton. */
	private CommandFactory() {
		FactoriaServicioAplicacion factory = FactoriaServicioAplicacion.getInstance();
		this.saSesion = factory.crearSASesion();
	}

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
			
			// --- Sesion ---
			case ALTA_SESION:
				return new CommandAltaSesion(saSesion);
			case BAJA_SESION:
				return new CommandEliminarSesion(saSesion);
			case MODIFICAR_SESION:
				return new CommandModificarSesion(saSesion);
			case MOSTRAR_SESION:
				return new CommandMostrarSesion(saSesion);
			case MOSTRAR_TODAS_SESIONES:
				return new CommandMostrarTodasSesiones(saSesion);
			case MOSTRAR_SALA_SESION:
				return new CommandMostrarSalaSesion(saSesion);
			case MOSTRAR_ENTRENADOR_SESION:
				return new CommandMostrarEntrenadorSesion(saSesion);
			
			default:
				return null;
		}

	}
}

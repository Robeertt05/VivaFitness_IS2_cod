/**
 * 
 */
package Controlador;

import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaSAImp;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.FactoriaNegocio.SASala;
import Negocio.FactoriaNegocio.SASesion;
import Negocio.FactoriaNegocio.SACliente;

/** 
 * Command Factory - Creates and returns Command objects
 * Singleton pattern for command creation
 * @author azuri
 */
public class CommandFactory {
	
	private static CommandFactory instance;
	private FactoriaServicioAplicacion saFactory;
	
	private CommandFactory() {
		// Initialize Service Application factory
		this.saFactory = new FactoriaSAImp();
	}

	/** 
	 * Get singleton instance of CommandFactory
	 * @return CommandFactory instance
	 */
	public static CommandFactory getInstance() {
		if (instance == null) {
			instance = new CommandFactory();
		}
		return instance;
	}

	/** 
	 * Get a Command based on the event type
	 * @param evento Event with command type information
	 * @return Command instance or null if not found
	 */
	public Command getCommand(String commandType) {
		// Get service applications
		SASala saSala = saFactory.generaSASala();
		SASesion saSesion = saFactory.generaSASesion();
		SACliente saCliente = saFactory.generaSACliente();
		
		// Create and return appropriate command based on type
		switch (commandType) {
			// Sala Commands
			case "ALTA_SALA":
				return new CommandAltaSala(saSala);
			case "BAJA_SALA":
				return new CommandBajaSala(saSala);
			case "MODIFICAR_SALA":
				return new CommandModificarSala(saSala);
			case "MOSTRAR_SALA":
				return new CommandMostrarSala(saSala);
			case "MOSTRAR_TODAS_SALAS":
				return new CommandMostrarTodasSalas(saSala);
			case "OBTENER_SESIONES_SALA":
				return new CommandObtenerSesionesSala(saSala);
				
			// Sesion Commands
			case "ALTA_SESION":
				return new CommandAltaSesion(saSesion);
			case "ELIMINAR_SESION":
				return new CommandEliminarSesion(saSesion);
			case "MODIFICAR_SESION":
				return new CommandModificarSesion(saSesion);
			case "MOSTRAR_SESION":
				return new CommandMostrarSesion(saSesion);
			case "MOSTRAR_TODAS_SESIONES":
				return new CommandMostrarTodasSesiones(saSesion);
				
			// Cliente Commands (when implemented)
			// case "ALTA_CLIENTE":
			// 	return new CommandAltaCliente(saCliente);
			
			default:
				return null;
		}
	}
	
	/** 
	 * Legacy method for Evento parameter
	 * Override if Evento class is properly implemented
	 */
	public Command getCommand(Evento evento) {
		// TODO: Implement if Evento class has proper attributes
		return null;
	}
}
/**
 * 
 */
package Controlador;

import Presentacion.FactoriaPresentacion.Evento;


public class CommandFactory {
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	private static CommandFactory instance;

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public static CommandFactory getInstance() {
		// begin-user-code
		// TODO Ap�ndice de m�todo generado autom�ticamente
		return null;
		// end-user-code
	}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param evento
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
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
			case MOSTRAR_SESIONES_CLIENTE:
				return new CmdMostrarSesionesCliente();
			case APUNTAR_CLIENTE_SESION:
				return new CmdApuntarClienteSesion();
			case DESAPUNTAR_CLIENTE_SESION:
				return new CmdDesapuntarClienteSesion();
			
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
/**
 * Implementacion concreta de la Factoria de Vistas.
 * Patron: Factory Method - cada caso del switch crea la vista concreta.
 */
package Presentacion.FactoriaPresentacion;

import Presentacion.Vistas.*;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class FactoriaVistasImp extends FactoriaVistas {

	/**
	 * Devuelve la vista correspondiente al evento indicado.
	 * @param evento constante int de la clase Evento
	 * @return IGUI correspondiente, o null si el evento no tiene vista asociada
	 */
	@Override
	public IGUI generarVistas(Evento evento) {
		switch (evento) {
			case ALTA_ENTRENADOR:
				return new VistaAltaEntrenador();
			case BAJA_ENTRENADOR:
				return new VistaBajaEntrenador();
			case MODIFICAR_ENTRENADOR:
				return new VistaModificarEntrenador();
			case MOSTRAR_ENTRENADOR:
				return new VistaMostrarEntrenador();
			case MOSTRAR_ENTRENADORES:
				return new VistaMostrarEntrenadores();
			case CREAR_SESION:
				return new VistaCrearSesion();
			case ALTA_SALA:
				return new VistaAltaSala();
			case BAJA_SALA:
				return new VistaBajaSala();
			case MODIFICAR_SALA:
				return new VistaModificarSala();
			case MOSTRAR_SALA:
				return new VistaMostrarSala();
			case MOSTRAR_TODAS_SALAS:
				return new VistaMostrarTodasSalas();
			case OBTENER_SESIONES_SALA:
				return new VistaObtenerSesionesSala();
			default:
				return super.generarVistas(evento);
		}
	}
}

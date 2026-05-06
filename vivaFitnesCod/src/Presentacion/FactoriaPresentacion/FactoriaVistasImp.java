
package Presentacion.FactoriaPresentacion;

import Presentacion.Vistas.*;


public class FactoriaVistasImp extends FactoriaVistas {

	
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

			case CREAR_SESION:
				return new VistaCrearSesion();
			case ALTA_CLIENTE:
				return new VistaAltaCliente();
			case BAJA_CLIENTE:
				return new VistaBajaCliente();
			case MODIFICAR_CLIENTE:
				return new VistaModificarCliente();
			case MOSTRAR_CLIENTE:
				return new VistaMostrarCliente();
			case MOSTRAR_CLIENTES:
				return new VistaMostrarClientes();
			case APUNTARSE_SESION:
				return new VistaApuntarseSesion();
			case DESAPUNTARSE_SESION:
				return new VistaDesapuntarseSesion();
			case MOSTRAR_SESIONES_CLIENTE:
				return new VistaMostrarSesionesCliente();
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

/**
 * 
 */
package Presentacion.FactoriaPresentacion;

import Presentacion.Entrenador.VistaCrearSesion;
import Presentacion.Entrenador.VistaEliminarSesion;
import Presentacion.Entrenador.VistaModificarSesion;
import Presentacion.Entrenador.VistaMostrarSesion;
import Presentacion.Entrenador.VistaMostrarTodasSesiones;
import Presentacion.Entrenador.VistaMostrarSalaSesion;
import Presentacion.Entrenador.VistaMostrarEntrenadorSesion;

/** 
 * Factory that creates the appropriate IGUI view for a given Evento.
 * @author azuri
 */
public class FactoriaVistas {

private static FactoriaVistas instance;

protected FactoriaVistas() {}

public static FactoriaVistas getInstance() {
if (instance == null) {
instance = new FactoriaVistasImp();
}
return instance;
}

public IGUI generarVistas(Evento evento) {
switch (evento) {
case ALTA_SESION:               return new VistaCrearSesion();
case BAJA_SESION:               return new VistaEliminarSesion();
case MODIFICAR_SESION:          return new VistaModificarSesion();
case MOSTRAR_SESION:            return new VistaMostrarSesion();
case MOSTRAR_TODAS_SESIONES:    return new VistaMostrarTodasSesiones();
case MOSTRAR_SALA_SESION:       return new VistaMostrarSalaSesion();
case MOSTRAR_ENTRENADOR_SESION: return new VistaMostrarEntrenadorSesion();
default:                        return null;
}
}
}

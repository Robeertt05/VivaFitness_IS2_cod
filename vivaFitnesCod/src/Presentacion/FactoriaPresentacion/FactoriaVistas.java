
package Presentacion.FactoriaPresentacion;

import Presentacion.Vistas.VistaCrearSesion;
import Presentacion.Vistas.VistaEliminarSesion;
import Presentacion.Vistas.VistaModificarSesion;
import Presentacion.Vistas.VistaMostrarEntrenadorSesion;
import Presentacion.Vistas.VistaMostrarSalaSesion;
import Presentacion.Vistas.VistaMostrarSesion;


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
case MOSTRAR_SALA_SESION:       return new VistaMostrarSalaSesion();
case MOSTRAR_ENTRENADOR_SESION: return new VistaMostrarEntrenadorSesion();
default:                        return null;
}
}
}

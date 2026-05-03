/**
 * 
 */
package Controlador;

import Presentacion.FactoriaPresentacion.Evento;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;
import Negocio.FactoriaNegocio.SASesion;

/** 
 * Factory that maps an Evento to the corresponding Command
 * @author azuri
 */
public class CommandFactory {

private static CommandFactory instance;
private final SASesion saSesion;

private CommandFactory() {
FactoriaServicioAplicacion factory = FactoriaServicioAplicacion.getInstance();
this.saSesion = factory.getSASesion();
}

public static CommandFactory getInstance() {
if (instance == null) {
instance = new CommandFactory();
}
return instance;
}

public Command getCommand(Evento evento) {
switch (evento) {
case ALTA_SESION:             return new CommandAltaSesion(saSesion);
case BAJA_SESION:             return new CommandEliminarSesion(saSesion);
case MODIFICAR_SESION:        return new CommandModificarSesion(saSesion);
case MOSTRAR_SESION:          return new CommandMostrarSesion(saSesion);
case MOSTRAR_TODAS_SESIONES:  return new CommandMostrarTodasSesiones(saSesion);
case MOSTRAR_SALA_SESION:     return new CommandMostrarSalaSesion(saSesion);
case MOSTRAR_ENTRENADOR_SESION: return new CommandMostrarEntrenadorSesion(saSesion);
default:                      return null;
}
}
}

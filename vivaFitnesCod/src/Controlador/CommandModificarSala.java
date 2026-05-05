/**
 * 
 */
package Controlador;

import Integracion.Sala.TSala;
import Negocio.Sala.SASala;
import Negocio.FactoriaNegocio.FactoriaServicioAplicacion;

/**
 * Command to update room details
 * CASO 3: Modificar sala (SRS)
 * @author azuri
 */
public class CommandModificarSala implements Command {

@Override
public Context execute(Object datos) {
Context ctx = new Context();

if (!(datos instanceof TSala)) {
ctx.setSuccess(false);
ctx.setMessage("Formato de datos invalido. Se esperaba TSala");
return ctx;
}

TSala sala = (TSala) datos;
if (sala.getIdSala() <= 0) {
ctx.setSuccess(false);
ctx.setMessage("ID de sala invalido");
return ctx;
}

int idSala = sala.getIdSala();
SASala saSala = FactoriaServicioAplicacion.getInstance().generaSASala();
int resultado = saSala.modificar_sala(idSala, sala);
if (resultado > 0) {
ctx.setSuccess(true);
ctx.setMessage("Sala modificada correctamente");
} else {
ctx.setSuccess(false);
ctx.setMessage("Error al modificar la sala");
}

return ctx;
}
}

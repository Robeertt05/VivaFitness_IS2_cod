package Negocio.Sesion;

import Integracion.Entrenador.TEntrenador;
import Integracion.Sala.TSala;
import Integracion.Sesion.TSesion;

public interface SASesion {

	public int alta_sesion(TSesion datos);

	public int baja_sesion(int idSesion);

	public int modificar_sesion(int idSesion, TSesion datos);

	public TSesion mostrar_sesion(int idSesion);

	public TSala mostrar_sala_sesion(int idSesion);

	public TEntrenador mostrar_entrenador_sesion(int idSesion);
}

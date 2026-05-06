
package Negocio.Entrenador;

import Integracion.Entrenador.TEntrenador;


public interface SAEntrenador {

	public int alta_entrenador(TEntrenador datos);

	public int baja_entrenador(int id);

	public int modificar_entrenador(int id, TEntrenador datos);

	public TEntrenador mostrar_entrenador(int id);

	public int crear_sesion(int idEntrenador, TEntrenador datos);
}

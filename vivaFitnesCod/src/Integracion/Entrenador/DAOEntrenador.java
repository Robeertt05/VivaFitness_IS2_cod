
package Integracion.Entrenador;

import java.util.Set;


public interface DAOEntrenador {

	public int create(TEntrenador datos);

	public TEntrenador read(int idEntrenador);

	public int update(TEntrenador tEntrenador);

	public int delete(int idEntrenador);

	public Set<TEntrenador> read_all();

	public TEntrenador read_by_dni(String dni);
}
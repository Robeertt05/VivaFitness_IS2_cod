
package Integracion.Sesion;

import Integracion.Sala.TSala;
import java.util.Set;


public interface DAOSesion {
	
	public int create(TSesion datos);
	public TSesion read(int idSesion);
	public int update(TSesion tSesion);
	public int delete(int idSesion);
	public Set<TSesion> read_all();
	public Set<TSesion> readByEntrenador(int idEntrenador);
	public Set<TSesion> readBySala(int idSala);
	public TSala getRoom(int idSesion);
	public Object getTrainer(int idSesion);
	
	public int countSalaHorario(int idSala, String horario);

	public int countConflictoHorarioSala(int idSala, String horario, int duracion);

	public int countConflictoHorarioSalaExcluyendo(int idSala, String horario, int duracion, int idSesionExcluir);

	public int countClientesActivos(int idSesion);
}

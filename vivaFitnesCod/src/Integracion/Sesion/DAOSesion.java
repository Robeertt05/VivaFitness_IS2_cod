/**
 * 
 */
package Integracion.Sesion;

import Integracion.Sala.TSala;
import java.util.Set;

/** 
 * Data Access Object interface for Sesion (SRS Aligned)
 * @author azuri
 */
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
	
	/**
	 * Count active sessions in sala at specific horario
	 */
	public int countSalaHorario(int idSala, String horario);

	/**
	 * Count active sessions in sala with time conflict considering duration
	 * Checks if there are any sessions in the same room that overlap with the given time slot
	 */
	public int countConflictoHorarioSala(int idSala, String horario, int duracion);

	/**
	 * Count active sessions in sala with time conflict considering duration, excluding a specific session
	 * Checks if there are any other sessions in the same room that overlap with the given time slot
	 */
	public int countConflictoHorarioSalaExcluyendo(int idSala, String horario, int duracion, int idSesionExcluir);

	/**
	 * Count active client inscriptions for a specific session
	 */
	public int countClientesActivos(int idSesion);
}

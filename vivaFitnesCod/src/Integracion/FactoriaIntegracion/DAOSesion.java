/**
 * 
 */
package Integracion.FactoriaIntegracion;

import java.util.Set;

/** 
 * Data Access Object interface for Sesion (SRS Aligned)
 * Supports 5 main SRS use cases:
 * 1. Baja sesión (delete with preconditions)
 * 2. Modificar sesión (update)
 * 3. Mostrar sesión (read details)
 * 4. Mostrar sala por sesión (get linked room)
 * 5. Mostrar entrenador por sesión (get linked trainer)
 * @author azuri
 */
public interface DAOSesion {
	
	/** 
	 * Create a new session
	 * @param datos Session transfer object
	 * @return Session ID if successful, 0 otherwise
	 */
	public int create(TSesion datos);

	/** 
	 * Read a session by ID
	 * @param idSesion Session ID
	 * @return Session transfer object or null if not found
	 */
	public TSesion read(int idSesion);

	/** 
	 * Update an existing session
	 * @param tSesion Session transfer object with updated data
	 * @return 1 if successful, 0 otherwise
	 */
	public int update(TSesion tSesion);

	/** 
	 * Delete a session by ID
	 * Precondition: Session must not have registered clients
	 * @param idSesion Session ID
	 * @return 1 if successful, 0 otherwise (or if has clients)
	 */
	public int delete(int idSesion);

	/** 
	 * Read all sessions
	 * @return Set of all session transfer objects
	 */
	public Set<TSesion> read_all();

	/** 
	 * Read sessions by trainer ID
	 * @param idEntrenador Trainer ID
	 * @return Set of sessions for this trainer
	 */
	public Set<TSesion> readByEntrenador(int idEntrenador);

	/** 
	 * Read sessions by room ID
	 * @param idSala Room ID
	 * @return Set of sessions in this room
	 */
	public Set<TSesion> readBySala(int idSala);
	
	/** 
	 * Get room associated with a session
	 * CASO 4: Mostrar sala por sesión
	 * @param idSesion Session ID
	 * @return TSala object with room details (idSala, nombreSala, aforo)
	 */
	public TSala getRoom(int idSesion);
	
	/** 
	 * Get trainer associated with a session
	 * CASO 5: Mostrar entrenador por sesión
	 * @param idSesion Session ID
	 * @return Trainer object with trainer details (idEntrenador, nombre, telefono, DNI)
	 */
	public Object getTrainer(int idSesion);
}
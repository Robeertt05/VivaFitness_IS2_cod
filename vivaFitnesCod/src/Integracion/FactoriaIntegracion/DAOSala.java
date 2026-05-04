/**
 * 
 */
package Integracion.FactoriaIntegracion;

import java.util.Set;

/** 
 * Data Access Object interface for Sala (Room)
 * Supports CRUD operations and queries for gym rooms/spaces
 * Manages 1-N relationship with Sesion (one room can have multiple sessions)
 * @author azuri
 */
public interface DAOSala {
	
	/** 
	 * Create a new room
	 * @param datos Room transfer object
	 * @return Room ID if successful, 0 otherwise
	 */
	public int create(TSala datos);

	/** 
	 * Read a room by ID
	 * @param idSala Room ID
	 * @return Room transfer object or null if not found
	 */
	public TSala read(int idSala);

	/** 
	 * Update an existing room
	 * @param tSala Room transfer object with updated data
	 * @return 1 if successful, 0 otherwise
	 */
	public int update(TSala tSala);

	/** 
	 * Delete a room by ID
	 * Precondition: Room must not have any active sessions
	 * @param idSala Room ID
	 * @return 1 if successful, 0 otherwise (or if has active sessions)
	 */
	public int delete(int idSala);

	/** 
	 * Read all rooms
	 * @return Set of all room transfer objects
	 */
	public Set<TSala> read_all();

	/** 
	 * Read sessions for a specific room
	 * @param idSala Room ID
	 * @return Set of sessions in this room
	 */
	public Set<TSesion> readSessionsByRoom(int idSala);
}
/**
 * 
 */
package Negocio.Sala;

import Integracion.Sala.TSala;
import Integracion.Sala.TSesion;
import java.util.Set;

/** 
 * Service Application interface for Sala (Room)
 * Implements use cases for room management:
 * 1. Alta sala - Create new room
 * 2. Baja sala - Delete room (only if no active sessions)
 * 3. Modificar sala - Update room details
 * 4. Mostrar sala - View room details by ID
 * 5. Mostrar todas salas - View all active rooms
 * @author azuri
 */
public interface SASala {
	
	/** 
	 * Alta sala - Create a new room
	 * Precondition: Room data must be valid (nombre not empty, aforo > 0)
	 * @param datos Room transfer object with details
	 * @return Room ID if successful, 0 otherwise
	 */
	public int alta_sala(TSala datos);

	/** 
	 * Baja sala - Delete a room
	 * Precondition: Room must not have any active sessions assigned to it
	 * @param idSala Room ID
	 * @return 1 if successful, 0 if room has sessions or doesn't exist
	 */
	public int baja_sala(int idSala);

	/** 
	 * Modificar sala - Update room attributes
	 * Precondition: Room must exist and be active
	 * @param idSala Room ID
	 * @param datos Updated room data (excluding ID)
	 * @return 1 if successful, 0 if data invalid or constraints violated
	 */
	public int modificar_sala(int idSala, TSala datos);

	/** 
	 * Mostrar sala - Get room information by ID
	 * Precondition: Room must exist and be active
	 * @param idSala Room ID
	 * @return Room transfer object (idSala, nombreSala, aforo, activo) or null if not found
	 */
	public TSala mostrar_sala(int idSala);

	/** 
	 * Mostrar todas salas - Get all active rooms
	 * @return Set of all active room transfer objects
	 */
	public Set<TSala> mostrar_todas_salas();

	/** 
	 * Get all sessions for a specific room
	 * Relationship: 1-N (one room can have multiple sessions)
	 * @param idSala Room ID
	 * @return Set of sessions for this room
	 */
	public Set<TSesion> obtener_sesiones_sala(int idSala);
}

/**
 * 
 */
package Negocio.FactoriaNegocio;

import Integracion.FactoriaIntegracion.TSesion;
import Integracion.FactoriaIntegracion.TSala;
import Negocio.entrenador.TEntrenador;

/** 
 * Service Application interface for Sesion (SRS Cases 1-5)
 * @author azuri
 */
public interface SASesion {
	
	/** 
	 * CASO 1: Baja sesión
	 * Delete a session (only if no clients are registered)
	 * Precondition: The session must not have registered clients to avoid loss of attendance data
	 * @param idSesion Session ID
	 * @return 1 if successful, 0 if session has clients or doesn't exist
	 */
	public int baja_sesion(int idSesion);

	/** 
	 * CASO 2: Modificar sesión
	 * Update a session attributes (except ID)
	 * Precondition: Session must exist and be active. New room must have sufficient capacity for registered clients
	 * @param idSesion Session ID
	 * @param datos Updated session data (excluding ID)
	 * @return 1 if successful, 0 if data invalid or constraints violated
	 */
	public int modificar_sesion(int idSesion, TSesion datos);

	/** 
	 * CASO 3: Mostrar sesión
	 * Get session information (objetivo, duración, horario, idSesión)
	 * Precondition: Session must exist and be active
	 * @param idSesion Session ID
	 * @return Session transfer object or null if not found or inactive
	 */
	public TSesion mostrar_sesion(int idSesion);

	/** 
	 * CASO 4: Mostrar sala por sesión
	 * Get the room assigned to a specific session
	 * Precondition: Session must exist and be active, must be linked to a room
	 * @param idSesion Session ID
	 * @return Room transfer object (idSala, nombreSala, aforo) or null if not found
	 */
	public TSala mostrar_sala_sesion(int idSesion);

	/** 
	 * CASO 5: Mostrar entrenador por sesión
	 * Get the trainer assigned to a specific session
	 * Precondition: Session must exist and be active, must be linked to a trainer
	 * @param idSesion Session ID
	 * @return Trainer transfer object (idEntrenador, nombreEntrenador, telefonoEntrenador, DNIEntrenador) or null if not found
	 */
	public TEntrenador mostrar_entrenador_sesion(int idSesion);
}
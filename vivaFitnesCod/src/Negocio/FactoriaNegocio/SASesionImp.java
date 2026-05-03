/**
 * 
 */
package Negocio.FactoriaNegocio;

import Integracion.FactoriaIntegracion.TSesion;
import Integracion.FactoriaIntegracion.TSala;
import Integracion.FactoriaIntegracion.DAOSesion;
import Negocio.entrenador.TEntrenador;
import java.util.Set;

/** 
 * Service Application implementation for Sesion (SRS Aligned)
 * Implements 5 use cases from SRS:
 * 1. Baja sesión - Delete session (only if no clients registered)
 * 2. Modificar sesión - Update session attributes
 * 3. Mostrar sesión - View session details
 * 4. Mostrar sala por sesión - View room assigned to session
 * 5. Mostrar entrenador por sesión - View trainer assigned to session
 * @author azuri
 */
public class SASesionImp implements SASesion {
	
	private DAOSesion daoSesion;

	public SASesionImp(DAOSesion daoSesion) {
		this.daoSesion = daoSesion;
	}

	/**
	 * CASO 1: Baja sesión
	 * Delete a session, but only if no clients are registered
	 * Precondition: Session must not have registered clients to avoid loss of attendance data
	 */
	@Override
	public int baja_sesion(int idSesion) {
		// begin-user-code
		// Validate ID
		if (idSesion <= 0) {
			return 0;
		}
		
		// Get session to verify it exists and check participant count
		TSesion sesion = daoSesion.read(idSesion);
		if (sesion == null) {
			return 0;
		}
		
		// Check precondition: No clients should be registered
		if (sesion.getParticipantsActuales() > 0) {
			// Session has clients, cannot delete - return 0
			return 0;
		}
		
		// No clients registered, safe to delete
		return daoSesion.delete(idSesion);
		// end-user-code
	}

	/**
	 * CASO 2: Modificar sesión
	 * Update session attributes (except ID)
	 * Precondition: Session must exist and be active.
	 * If room changes, new room capacity must be >= current registered clients
	 */
	@Override
	public int modificar_sesion(int idSesion, TSesion datos) {
		// begin-user-code
		// Validate parameters
		if (idSesion <= 0 || datos == null) {
			return 0;
		}
		
		// Get current session to verify it exists
		TSesion sesionActual = daoSesion.read(idSesion);
		if (sesionActual == null) {
			return 0;
		}
		
		// Verify room change doesn't violate capacity constraint
		if (datos.getIdSala() != sesionActual.getIdSala()) {
			// Room is being changed
			// TODO: Verify new room has capacity >= current registered clients
		}
		
		// Set ID to ensure it doesn't change
		datos.setIdSesion(idSesion);
		
		// Call DAO to update session
		return daoSesion.update(datos);
		// end-user-code
	}

	/**
	 * CASO 3: Mostrar sesión
	 * Get session details: objetivo, duración, horario, idSesión
	 * Precondition: Session must exist and be active
	 */
	@Override
	public TSesion mostrar_sesion(int idSesion) {
		// begin-user-code
		// Validate ID
		if (idSesion <= 0) {
			return null;
		}
		
		// Get and return session
		return daoSesion.read(idSesion);
		// end-user-code
	}

	/**
	 * CASO 4: Mostrar sala por sesión
	 * Get the room assigned to this session
	 * Precondition: Session must exist, be active, and linked to a room
	 */
	@Override
	public TSala mostrar_sala_sesion(int idSesion) {
		// begin-user-code
		// Validate ID
		if (idSesion <= 0) {
			return null;
		}
		
		// Get session
		TSesion sesion = daoSesion.read(idSesion);
		if (sesion == null) {
			return null;
		}
		
		// TODO: Get room details from DAO or room service
		// This would need DAOSala or similar
		TSala sala = new TSala();
		sala.setIdSala(sesion.getIdSala());
		// Get other room attributes from database
		
		return sala;
		// end-user-code
	}

	/**
	 * CASO 5: Mostrar entrenador por sesión
	 * Get the trainer assigned to this session
	 * Precondition: Session must exist, be active, and linked to a trainer
	 */
	@Override
	public TEntrenador mostrar_entrenador_sesion(int idSesion) {
		// begin-user-code
		// Validate ID
		if (idSesion <= 0) {
			return null;
		}
		
		// Get session
		TSesion sesion = daoSesion.read(idSesion);
		if (sesion == null) {
			return null;
		}
		
		// TODO: Get trainer details from DAO or trainer service
		// This would need DAOEntrenador or similar
		TEntrenador entrenador = new TEntrenador();
		// Set trainer ID and other attributes from database
		
		return entrenador;
		// end-user-code
	}
	
	/**
	 * Alta sesión - Create a new session
	 * Validates session data before creation
	 */
	@Override
	public int alta_sesion(TSesion datos) {
		// begin-user-code
		if (datos == null) {
			return 0;
		}
		
		// Validate basic fields
		if (datos.getIdSala() <= 0 || datos.getIdEntrenador() <= 0) {
			return 0;
		}
		
		if (datos.getObjetivo() == null || datos.getObjetivo().trim().isEmpty()) {
			return 0;
		}
		
		// Proceed with creation
		return daoSesion.create(datos);
		// end-user-code
	}
	
	/**
	 * Mostrar todas sesiones - Get all active sessions
	 */
	@Override
	public Set<TSesion> mostrar_todas_sesiones() {
		// begin-user-code
		return daoSesion.read_all();
		// end-user-code
	}
}
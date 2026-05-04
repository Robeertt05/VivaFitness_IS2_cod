/**
 * 
 */
package Negocio.FactoriaNegocio;

import Integracion.Sala.TSala;
import Integracion.Sesion.TSesion;
import Negocio.Sala.SASala;
import Integracion.Sala.DAOSala;
import java.util.Set;

/** 
 * Service Application implementation for Sala (Room)
 * Implements use cases for room management with business logic validation
 * @author azuri
 */
public class SASalaImp implements SASala {
	
	private DAOSala daoSala;

	public SASalaImp(DAOSala daoSala) {
		this.daoSala = daoSala;
	}

	/**
	 * CASO 1: Alta sala - Create a new room
	 * Validates room data before creation
	 * Precondition: Room data must be valid
	 */
	@Override
	public int alta_sala(TSala datos) {
		// begin-user-code
		// Validate input
		if (datos == null) {
			return 0;
		}
		
		// Validate room name not empty
		if (datos.getNombreSala() == null || datos.getNombreSala().trim().isEmpty()) {
			return 0;
		}
		
		// Validate capacity > 0
		if (datos.getAforo() <= 0) {
			return 0;
		}
		
		// Proceed with creation
		return daoSala.create(datos);
		// end-user-code
	}

	/**
	 * CASO 2: Baja sala - Delete a room
	 * Precondition: Room must not have any active sessions
	 */
	@Override
	public int baja_sala(int idSala) {
		// begin-user-code
		// Validate ID
		if (idSala <= 0) {
			return 0;
		}
		
		// Get room to verify it exists
		TSala sala = daoSala.read(idSala);
		if (sala == null) {
			return 0;
		}
		
		// Check precondition: Room must not have active sessions
		Set<TSesion> sesiones = daoSala.readSessionsByRoom(idSala);
		if (sesiones != null && !sesiones.isEmpty()) {
			// Room has active sessions, cannot delete
			return 0;
		}
		
		// No active sessions, safe to delete
		return daoSala.delete(idSala);
		// end-user-code
	}

	/**
	 * CASO 3: Modificar sala - Update room details
	 * Validates new data and ensures constraints are met
	 */
	@Override
	public int modificar_sala(int idSala, TSala datos) {
		// begin-user-code
		// Validate parameters
		if (idSala <= 0 || datos == null) {
			return 0;
		}
		
		// Get current room to verify it exists
		TSala salaActual = daoSala.read(idSala);
		if (salaActual == null) {
			return 0;
		}
		
		// Validate room name not empty
		if (datos.getNombreSala() == null || datos.getNombreSala().trim().isEmpty()) {
			return 0;
		}
		
		// Validate capacity > 0
		if (datos.getAforo() <= 0) {
			return 0;
		}
		
		// Set ID to ensure we update the correct room
		datos.setIdSala(idSala);
		
		// Proceed with update
		return daoSala.update(datos);
		// end-user-code
	}

	/**
	 * CASO 4: Mostrar sala - Get room information by ID
	 */
	@Override
	public TSala mostrar_sala(int idSala) {
		// begin-user-code
		// Validate ID
		if (idSala <= 0) {
			return null;
		}
		
		// Get room from DAO
		return daoSala.read(idSala);
		// end-user-code
	}

	/**
	 * CASO 5: Mostrar todas salas - Get all active rooms
	 */
	@Override
	public Set<TSala> mostrar_todas_salas() {
		// begin-user-code
		// Get all rooms from DAO
		return daoSala.read_all();
		// end-user-code
	}

	/**
	 * Get all sessions for a specific room
	 * Relationship: 1-N (one room can have multiple sessions)
	 */
	@Override
	public Set<TSesion> obtener_sesiones_sala(int idSala) {
		// begin-user-code
		// Validate ID
		if (idSala <= 0) {
			return null;
		}
		
		// Verify room exists
		TSala sala = daoSala.read(idSala);
		if (sala == null) {
			return null;
		}
		
		// Get sessions for this room
		return daoSala.readSessionsByRoom(idSala);
		// end-user-code
	}
}
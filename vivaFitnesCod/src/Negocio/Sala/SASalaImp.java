/**
 * 
 */
package Negocio.Sala;

import Integracion.Sala.TSala;
import Integracion.Sesion.TSesion;
import Integracion.Sala.DAOSala;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import java.util.Set;
import java.util.HashSet;

/** 
 * Service Application implementation for Sala (Room)
 * Implements use cases for room management with business logic validation
 * @author azuri
 */
public class SASalaImp implements SASala {

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
		
		// Reactivar sala inactiva con el mismo nombre si existe
		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		Set<TSala> salas = daoSala.read_all();
		if (salas != null) {
			String nombreNueva = datos.getNombreSala().trim();
			for (TSala sala : salas) {
				if (sala != null && sala.getNombreSala() != null
						&& sala.getNombreSala().trim().equalsIgnoreCase(nombreNueva)) {
					if (sala.getActivo() == 1) {
						return 0;
					}
					sala.setAforo(datos.getAforo());
					sala.setNombreSala(datos.getNombreSala());
					sala.setActivo(1);
					int updated = daoSala.update(sala);
					return updated > 0 ? sala.getIdSala() : 0;
				}
			}
		}

		// Proceed with creation
		datos.setActivo(1);
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
		
		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		
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
		
		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		
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
		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		TSala sala = daoSala.read(idSala);
		return (sala != null && sala.getActivo() == 1) ? sala : null;
		// end-user-code
	}

	/**
	 * CASO 5: Mostrar todas salas - Get all active rooms
	 */
	@Override
	public Set<TSala> mostrar_todas_salas() {
		// begin-user-code
		// Get all rooms from DAO
		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		Set<TSala> all = daoSala.read_all();
		if (all == null) {
			return null;
		}
		Set<TSala> activos = new HashSet<>();
		for (TSala s : all) {
			if (s != null && s.getActivo() == 1) {
				activos.add(s);
			}
		}
		return activos;
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
		
		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		
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

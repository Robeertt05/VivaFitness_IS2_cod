/**
 * 
 */
package Integracion.FactoriaIntegracion;

import java.util.Set;

/** 
 * Data Access Object implementation for Sala (Room)
 * Handles database operations for room management
 * @author azuri
 */
public class DAOSalaImp implements DAOSala {

	@Override
	public int create(TSala datos) {
		// begin-user-code
		// TODO: Implement database insert logic
		// INSERT INTO salas (nombreSala, aforo, activo)
		// VALUES (?, ?, ?)
		// Return generated idSala
		return 0;
		// end-user-code
	}

	@Override
	public TSala read(int idSala) {
		// begin-user-code
		// TODO: Implement database query logic
		// SELECT idSala, nombreSala, aforo, activo FROM salas 
		// WHERE idSala = ? AND activo = 1
		return null;
		// end-user-code
	}

	@Override
	public int update(TSala tSala) {
		// begin-user-code
		// TODO: Implement database update logic
		// UPDATE salas SET nombreSala=?, aforo=?, activo=?
		// WHERE idSala = ?
		return 0;
		// end-user-code
	}

	@Override
	public int delete(int idSala) {
		// begin-user-code
		// TODO: Implement database delete logic with precondition check
		// First check: SELECT COUNT(*) FROM sesiones WHERE idSala = ? AND activo = 1
		// If count > 0, return 0 (room has active sessions)
		// Otherwise: DELETE FROM salas WHERE idSala = ?
		return 0;
		// end-user-code
	}

	@Override
	public Set<TSala> read_all() {
		// begin-user-code
		// TODO: Implement database query to get all active rooms
		// SELECT idSala, nombreSala, aforo, activo FROM salas WHERE activo = 1
		return null;
		// end-user-code
	}

	@Override
	public Set<TSesion> readSessionsByRoom(int idSala) {
		// begin-user-code
		// TODO: Implement database query to get sessions in a room
		// SELECT * FROM sesiones WHERE idSala = ? AND activo = 1
		return null;
		// end-user-code
	}
}
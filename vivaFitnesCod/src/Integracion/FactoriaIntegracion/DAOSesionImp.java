/**
 * 
 */
package Integracion.FactoriaIntegracion;

import java.util.Set;

/** 
 * Data Access Object implementation for Sesion (SRS Aligned)
 * @author azuri
 */
public class DAOSesionImp implements DAOSesion {

	@Override
	public int create(TSesion datos) {
		// begin-user-code
		// TODO: Implement database insert logic
		// INSERT INTO sesiones (nombreSesion, descripcion, fecha, hora, idSala, idEntrenador, capacidadMaxima, participantsActuales, activo)
		// VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
		return 0;
		// end-user-code
	}

	@Override
	public TSesion read(int idSesion) {
		// begin-user-code
		// TODO: Implement database query logic
		// SELECT * FROM sesiones WHERE idSesion = ? AND activo = 1
		return null;
		// end-user-code
	}

	@Override
	public int update(TSesion tSesion) {
		// begin-user-code
		// TODO: Implement database update logic
		// UPDATE sesiones SET nombreSesion=?, descripcion=?, fecha=?, hora=?, idSala=?, idEntrenador=?, capacidadMaxima=?, participantsActuales=?
		// WHERE idSesion = ?
		return 0;
		// end-user-code
	}

	@Override
	public int delete(int idSesion) {
		// begin-user-code
		// TODO: Implement database delete logic with precondition check
		// First check: SELECT participantsActuales FROM sesiones WHERE idSesion = ?
		// If participantsActuales > 0, return 0
		// Otherwise: DELETE FROM sesiones WHERE idSesion = ?
		return 0;
		// end-user-code
	}

	@Override
	public Set<TSesion> read_all() {
		// begin-user-code
		// TODO: Implement database query to get all active sessions
		// SELECT * FROM sesiones WHERE activo = 1
		return null;
		// end-user-code
	}

	@Override
	public Set<TSesion> readByEntrenador(int idEntrenador) {
		// begin-user-code
		// TODO: Implement database query to get sessions by trainer
		// SELECT * FROM sesiones WHERE idEntrenador = ? AND activo = 1
		return null;
		// end-user-code
	}

	@Override
	public Set<TSesion> readBySala(int idSala) {
		// begin-user-code
		// TODO: Implement database query to get sessions by room
		// SELECT * FROM sesiones WHERE idSala = ? AND activo = 1
		return null;
		// end-user-code
	}

	@Override
	public TSala getRoom(int idSesion) {
		// begin-user-code
		// CASO 4: Mostrar sala por sesión
		// TODO: Get room associated with session
		// SELECT s.idSala, sa.nombreSala, sa.aforo 
		// FROM sesiones s JOIN salas sa ON s.idSala = sa.idSala 
		// WHERE s.idSesion = ? AND s.activo = 1
		return null;
		// end-user-code
	}

	@Override
	public Object getTrainer(int idSesion) {
		// begin-user-code
		// CASO 5: Mostrar entrenador por sesión
		// TODO: Get trainer associated with session
		// SELECT e.idEntrenador, e.nombreEntrenador, e.telefonoEntrenador, e.DNIEntrenador
		// FROM sesiones s JOIN entrenadores e ON s.idEntrenador = e.idEntrenador
		// WHERE s.idSesion = ? AND s.activo = 1
		return null;
		// end-user-code
	}
}
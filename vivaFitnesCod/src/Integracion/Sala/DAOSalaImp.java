/**
 * Implementacion del DAO de Sala.
 * Patron: DAO (Data Access Object) - abstrae el acceso a la fuente de datos.
 * En un proyecto real conectaria con la BD via JDBC/JPA usando la transaccion activa.
 */
package Integracion.Sala;

import Integracion.ConnectionManager.ConnectionManager;
import Integracion.Sesion.TSesion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * Data Access Object implementation for Sala (Room)
 * Handles database operations for room management
 * @author azuri
 */
public class DAOSalaImp implements DAOSala {

	private RuntimeException databaseError(SQLException e) {
		return new RuntimeException(
				"No se pudo conectar con la base de datos. Compruebe que esté encendida.",
				e);
	}

	/**
	 * Inserta una nueva sala en la base de datos.
	 * @param datos TSala con los datos a persistir
	 * @return id generado por la BD, o -1 si falla
	 */
	@Override
	public int create(TSala datos) {
		int id = -1;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			String query = "INSERT INTO sala (nombreSala, aforo, activo) VALUES (?, ?, ?)";
			ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, datos.getNombreSala());
			ps.setInt(2, datos.getAforo());
			ps.setInt(3, datos.getActivo());
			
			ps.executeUpdate();
			
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			throw databaseError(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				throw databaseError(e);
			}
		}
		
		return id;
	}

	/**
	 * Lee una sala por su identificador.
	 * @param idSala identificador de la sala
	 * @return TSala o null si no existe
	 */
	@Override
	public TSala read(int idSala) {
		TSala sala = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			String query = "SELECT * FROM sala WHERE idSala = ?";
			ps = connection.prepareStatement(query);
			ps.setInt(1, idSala);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				sala = new TSala();
				sala.setIdSala(rs.getInt("idSala"));
				sala.setNombreSala(rs.getString("nombreSala"));
				sala.setAforo(rs.getInt("aforo"));
				sala.setActivo(rs.getInt("activo"));
			}
			
		} catch (SQLException e) {
			throw databaseError(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				throw databaseError(e);
			}
		}
		
		return sala;
	}

	/**
	 * Actualiza los datos de una sala existente.
	 * @param tSala TSala con los datos actualizados
	 * @return 1 si correcto, 0 si falla
	 */
	@Override
	public int update(TSala tSala) {
		int result = 0;
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			String query = "UPDATE sala SET nombreSala = ?, aforo = ?, activo = ? WHERE idSala = ?";
			ps = connection.prepareStatement(query);
			
			ps.setString(1, tSala.getNombreSala());
			ps.setInt(2, tSala.getAforo());
			ps.setInt(3, tSala.getActivo());
			ps.setInt(4, tSala.getIdSala());
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			throw databaseError(e);
		} finally {
			try {
				if (ps != null) ps.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				throw databaseError(e);
			}
		}
		
		return result;
	}

	/**
	 * Elimina logicamente una sala por su id (baja logica: activo = 0).
	 * Precondición: la sala no debe tener sesiones activas.
	 * @param idSala identificador de la sala
	 * @return 1 si correcto, 0 si falla
	 */
	@Override
	public int delete(int idSala) {
		int result = 0;
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			// Baja logica: activo = 0
			String query = "UPDATE sala SET activo = 0 WHERE idSala = ?";
			ps = connection.prepareStatement(query);
			ps.setInt(1, idSala);
			
			result = ps.executeUpdate();
			
		} catch (SQLException e) {
			throw databaseError(e);
		} finally {
			try {
				if (ps != null) ps.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				throw databaseError(e);
			}
		}
		
		return result;
	}

	/**
	 * Devuelve todas las salas de la base de datos.
	 * @return Set de TSala
	 */
	@Override
	public Set<TSala> read_all() {
		Set<TSala> salas = new HashSet<>();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			String query = "SELECT * FROM sala";
			ps = connection.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				TSala sala = new TSala();
				sala.setIdSala(rs.getInt("idSala"));
				sala.setNombreSala(rs.getString("nombreSala"));
				sala.setAforo(rs.getInt("aforo"));
				sala.setActivo(rs.getInt("activo"));
				
				salas.add(sala);
			}
			
		} catch (SQLException e) {
			throw databaseError(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				throw databaseError(e);
			}
		}
		
		return salas;
	}

	/**
	 * Obtiene todas las sesiones asociadas a una sala.
	 * @param idSala identificador de la sala
	 * @return Set de TSesion
	 */
	@Override
	public Set<TSesion> readSessionsByRoom(int idSala) {
		Set<TSesion> sesiones = new HashSet<>();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			String query = "SELECT * FROM sesion WHERE idSala = ? AND activo = 1";
			ps = connection.prepareStatement(query);
			ps.setInt(1, idSala);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				TSesion sesion = new TSesion();
				sesion.setIdSesion(rs.getInt("idSesion"));
				sesion.setIdSala(rs.getInt("idSala"));
				sesion.setNombreSesion(rs.getString("nombreSesion"));
				sesion.setFecha(rs.getString("fecha"));
				sesion.setHora(rs.getString("hora"));
				sesion.setActivo(rs.getInt("activo"));
				
				sesiones.add(sesion);
			}
			
		} catch (SQLException e) {
			throw databaseError(e);
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (connection != null) connection.close();
			} catch (SQLException e) {
				throw databaseError(e);
			}
		}
		
		return sesiones;
	}
}

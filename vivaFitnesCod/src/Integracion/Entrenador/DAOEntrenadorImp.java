
package Integracion.Entrenador;


import Integracion.ConnectionManager.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DAOEntrenadorImp implements DAOEntrenador {

	private RuntimeException databaseError(SQLException e) {
		return new RuntimeException(
				"No se pudo conectar con la base de datos. Compruebe que est� encendida.",
				e);
	}

	@Override
	public int create(TEntrenador datos) {
		int id = -1;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			String query = "INSERT INTO entrenador (nombreEntrenador, DNI_entrenador, telefonoEntrenador, activo) VALUES (?, ?, ?, ?)";
			ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			
			ps.setString(1, datos.get_nombre());
			ps.setString(2, datos.get_dni());
			ps.setString(3, datos.get_telefono());
			ps.setInt(4, datos.get_activo());
			
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

	@Override
	public TEntrenador read(int idEntrenador) {
		TEntrenador entrenador = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			String query = "SELECT * FROM entrenador WHERE idEntrenador = ?";
			ps = connection.prepareStatement(query);
			ps.setInt(1, idEntrenador);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				entrenador = new TEntrenador();
				entrenador.set_id(rs.getInt("idEntrenador"));
				entrenador.set_nombre(rs.getString("nombreEntrenador"));
				entrenador.set_dni(rs.getString("DNI_entrenador"));
				entrenador.set_telefono(rs.getString("telefonoEntrenador"));
				entrenador.set_activo(rs.getInt("activo"));
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
		
		return entrenador;
	}

	@Override
	public int update(TEntrenador tEntrenador) {
		int result = -1;
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			String query = "UPDATE entrenador SET nombreEntrenador = ?, DNI_entrenador = ?, telefonoEntrenador = ?, activo = ? WHERE idEntrenador = ?";
			ps = connection.prepareStatement(query);
			
			ps.setString(1, tEntrenador.get_nombre());
			ps.setString(2, tEntrenador.get_dni());
			ps.setString(3, tEntrenador.get_telefono());
			ps.setInt(4, tEntrenador.get_activo());
			ps.setInt(5, tEntrenador.get_id());
			
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

	@Override
	public int delete(int idEntrenador) {
		// Validar que no existan sesiones activas para el entrenador
		String checkQuery = "SELECT COUNT(*) FROM sesion WHERE idEntrenador = ? AND activo = 1";
		try (Connection con = ConnectionManager.getConnection();
		     PreparedStatement checkPs = con.prepareStatement(checkQuery)) {
			checkPs.setInt(1, idEntrenador);
			try (ResultSet rs = checkPs.executeQuery()) {
				if (rs.next() && rs.getInt(1) > 0) {
					int countSesiones = rs.getInt(1);
					throw new RuntimeException(
						"No se puede dar de baja el entrenador con ID " + idEntrenador + " porque tiene " + 
						countSesiones + " sesión(es) activa(s) asignada(s).");
				}
			}
		} catch (RuntimeException re) {
			throw re;
		} catch (SQLException e) {
			throw databaseError(e);
		}
		
		int result = -1;
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			String query = "UPDATE entrenador SET activo = 0 WHERE idEntrenador = ?";
			ps = connection.prepareStatement(query);
			ps.setInt(1, idEntrenador);
			
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

	public Set<TEntrenador> read_all() {
		Set<TEntrenador> entrenadores = new HashSet<>();
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			String query = "SELECT * FROM entrenador";
			ps = connection.prepareStatement(query);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				TEntrenador entrenador = new TEntrenador();
				entrenador.set_id(rs.getInt("idEntrenador"));
				entrenador.set_nombre(rs.getString("nombreEntrenador"));
				entrenador.set_dni(rs.getString("DNI_entrenador"));
				entrenador.set_telefono(rs.getString("telefonoEntrenador"));
				entrenador.set_activo(rs.getInt("activo"));
				
				entrenadores.add(entrenador);
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
		
		return entrenadores;
	}

	public TEntrenador read_by_dni(String dni) {
		TEntrenador entrenador = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			String query = "SELECT * FROM entrenador WHERE DNI_entrenador = ?";
			ps = connection.prepareStatement(query);
			ps.setString(1, dni);
			
			rs = ps.executeQuery();
			
			if (rs.next()) {
				entrenador = new TEntrenador();
				entrenador.set_id(rs.getInt("idEntrenador"));
				entrenador.set_nombre(rs.getString("nombreEntrenador"));
				entrenador.set_dni(rs.getString("DNI_entrenador"));
				entrenador.set_telefono(rs.getString("telefonoEntrenador"));
				entrenador.set_activo(rs.getInt("activo"));
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
		
		return entrenador;
	}
}

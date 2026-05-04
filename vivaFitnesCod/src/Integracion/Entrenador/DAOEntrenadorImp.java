/**
 * Implementacion del DAO de Entrenador.
 * Patron: DAO (Data Access Object) - abstrae el acceso a la fuente de datos.
 * En un proyecto real conectaria con la BD via JDBC/JPA usando la transaccion activa.
 */
package Integracion.Entrenador;


import Integracion.ConnectionManager.ConnectionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class DAOEntrenadorImp implements DAOEntrenador {

	private RuntimeException databaseError(SQLException e) {
		return new RuntimeException(
				"No se pudo conectar con la base de datos. Compruebe que est� encendida.",
				e);
	}

	/**
	 * Inserta un nuevo entrenador en la base de datos.
	 * @param datos TEntrenador con los datos a persistir
	 * @return id generado por la BD, o -1 si falla
	 */
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

	/**
	 * Lee un entrenador por su identificador.
	 * @param idEntrenador identificador del entrenador
	 * @return TEntrenador o null si no existe
	 */
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

	/**
	 * Actualiza los datos de un entrenador existente.
	 * @param tEntrenador TEntrenador con los datos actualizados
	 * @return 0 si correcto, -1 si falla
	 */
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

	/**
	 * Elimina fisicamente un entrenador por su id.
	 * (Normalmente se usa baja logica; este metodo es para administracion.)
	 * @param idEntrenador identificador del entrenador
	 * @return 0 si correcto, -1 si falla
	 */
	@Override
	public int delete(int idEntrenador) {
		int result = -1;
		Connection connection = null;
		PreparedStatement ps = null;
		
		try {
			connection = ConnectionManager.getConnection();
			
			// Baja logica: activo = 0
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

	/**
	 * Devuelve todos los entrenadores de la base de datos.
	 * @return Set de TEntrenador
	 */
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

	/**
	 * Busca un entrenador por su DNI.
	 * @param dni DNI a buscar
	 * @return TEntrenador o null si no existe
	 */
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

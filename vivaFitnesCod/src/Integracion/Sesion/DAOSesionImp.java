/**
 * 
 */
package Integracion.Sesion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import Integracion.ConnectionManager.ConnectionManager;
import Integracion.Sala.TSala;
import Integracion.Entrenador.TEntrenador;

/** 
 * Data Access Object implementation for Sesion (SRS Aligned)
 * @author azuri
 */
public class DAOSesionImp implements DAOSesion {

	private Connection getConnection() throws SQLException {
		return ConnectionManager.getConnection();
	}

	// -----------------------------------------------------------------------
	// CRUD bsico
	// -----------------------------------------------------------------------

	@Override
	public int create(TSesion datos) {
		// begin-user-code
		String sql = "INSERT INTO sesion "
				+ "(nombreSesion, descripcion, fecha, hora, idSala, idEntrenador, "
				+ "capacidadMaxima, participantsActuales, activo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, datos.getNombreSesion());
			ps.setString(2, datos.getDescripcion());
			ps.setString(3, datos.getFecha());
			ps.setString(4, datos.getHora());
			ps.setInt(5, datos.getIdSala());
			ps.setInt(6, datos.getIdEntrenador());
			ps.setInt(7, datos.getCapacidadMaxima());
			ps.setInt(8, datos.getParticipantsActuales());
			ps.setInt(9, datos.getActivo());
			int rows = ps.executeUpdate();
			if (rows > 0) {
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						return keys.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al crear la sesion.", e);
		}
		return 0;
		// end-user-code
	}

	@Override
	public TSesion read(int idSesion) {
		// begin-user-code
		String sql = "SELECT * FROM sesion WHERE idSesion = ? AND activo = 1";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSesion);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRow(rs);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al consultar la sesion con ID " + idSesion + ".", e);
		}
		return null;
		// end-user-code
	}

	@Override
	public int update(TSesion tSesion) {
		// begin-user-code
		String sql = "UPDATE sesion SET nombreSesion=?, descripcion=?, fecha=?, hora=?, "
				+ "idSala=?, idEntrenador=?, capacidadMaxima=?, participantsActuales=?, activo=? "
				+ "WHERE idSesion=?";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, tSesion.getNombreSesion());
			ps.setString(2, tSesion.getDescripcion());
			ps.setString(3, tSesion.getFecha());
			ps.setString(4, tSesion.getHora());
			ps.setInt(5, tSesion.getIdSala());
			ps.setInt(6, tSesion.getIdEntrenador());
			ps.setInt(7, tSesion.getCapacidadMaxima());
			ps.setInt(8, tSesion.getParticipantsActuales());
			ps.setInt(9, tSesion.getActivo());
			ps.setInt(10, tSesion.getIdSesion());
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al modificar la sesion con ID " + tSesion.getIdSesion() + ".", e);
		}
		// end-user-code
	}

	@Override
	public int delete(int idSesion) {
		// begin-user-code
		// Precondition enforced in SQL: only deletes when no participants registered
		String sql = "DELETE FROM sesion WHERE idSesion = ? AND participantsActuales = 0";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSesion);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al eliminar la sesion con ID " + idSesion + ".", e);
		}
		// end-user-code
	}

	// -----------------------------------------------------------------------
	// Consultas
	// -----------------------------------------------------------------------

	@Override
	public Set<TSesion> read_all() {
		// begin-user-code
		String sql = "SELECT * FROM sesion WHERE activo = 1";
		Set<TSesion> sesiones = new HashSet<>();
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				sesiones.add(mapRow(rs));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al listar todas las sesiones.", e);
		}
		return sesiones;
		// end-user-code
	}

	@Override
	public Set<TSesion> readByEntrenador(int idEntrenador) {
		// begin-user-code
		return readByField("SELECT * FROM sesion WHERE idEntrenador = ? AND activo = 1", idEntrenador);
		// end-user-code
	}

	@Override
	public Set<TSesion> readBySala(int idSala) {
		// begin-user-code
		return readByField("SELECT * FROM sesion WHERE idSala = ? AND activo = 1", idSala);
		// end-user-code
	}

	private Set<TSesion> readByField(String sql, int id) {
		Set<TSesion> sesiones = new HashSet<>();
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					sesiones.add(mapRow(rs));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al filtrar sesiones.", e);
		}
		return sesiones;
	}

	// -----------------------------------------------------------------------
	// CASO 4: Mostrar sala por sesion
	// -----------------------------------------------------------------------

	@Override
	public TSala getRoom(int idSesion) {
		// begin-user-code
		String sql = "SELECT sa.idSala, sa.nombreSala, sa.aforo, sa.activo "
				+ "FROM sesion s "
				+ "JOIN sala sa ON s.idSala = sa.idSala "
				+ "WHERE s.idSesion = ? AND s.activo = 1";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSesion);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					TSala sala = new TSala();
					sala.setIdSala(rs.getInt("idSala"));
					sala.setNombreSala(rs.getString("nombreSala"));
					sala.setAforo(rs.getInt("aforo"));
					sala.setActivo(rs.getInt("activo"));
					return sala;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al obtener la sala de la sesion.", e);
		}
		return null;
		// end-user-code
	}

	// -----------------------------------------------------------------------
	// CASO 5: Mostrar entrenador por sesion
	// -----------------------------------------------------------------------

	@Override
	public Object getTrainer(int idSesion) {
		// begin-user-code
		String sql = "SELECT e.idEntrenador, e.DNI_entrenador, e.nombreEntrenador, e.telefonoEntrenador, e.activo "
				+ "FROM sesion s "
				+ "JOIN entrenador e ON s.idEntrenador = e.idEntrenador "
				+ "WHERE s.idSesion = ? AND s.activo = 1";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSesion);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					TEntrenador entrenador = new TEntrenador();
					entrenador.set_activo(rs.getInt("activo"));
					entrenador.set_nombre(rs.getString("nombreEntrenador"));
					entrenador.set_telefono(rs.getString("telefonoEntrenador"));
					entrenador.set_dni(rs.getString("DNI_entrenador"));
					return entrenador;
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al obtener el entrenador de la sesion.", e);
		}
		return null;
		// end-user-code
	}

	// -----------------------------------------------------------------------
	// Helper
	// -----------------------------------------------------------------------

	private TSesion mapRow(ResultSet rs) throws SQLException {
		TSesion sesion = new TSesion();
		sesion.setIdSesion(rs.getInt("idSesion"));
		sesion.setNombreSesion(rs.getString("nombreSesion"));
		sesion.setDescripcion(rs.getString("descripcion"));
		sesion.setFecha(rs.getString("fecha"));
		sesion.setHora(rs.getString("hora"));
		sesion.setIdSala(rs.getInt("idSala"));
		sesion.setIdEntrenador(rs.getInt("idEntrenador"));
		sesion.setCapacidadMaxima(rs.getInt("capacidadMaxima"));
		sesion.setParticipantsActuales(rs.getInt("participantsActuales"));
		sesion.setActivo(rs.getInt("activo"));
		return sesion;
	}
}

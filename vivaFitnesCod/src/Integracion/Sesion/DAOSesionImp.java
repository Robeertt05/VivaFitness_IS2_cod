
package Integracion.Sesion;

import Integracion.ConnectionManager.ConnectionManager;
import Integracion.Entrenador.TEntrenador;
import Integracion.Sala.TSala;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;


public class DAOSesionImp implements DAOSesion {

	private Connection getConnection() throws SQLException {
		return ConnectionManager.getConnection();
	}

	@Override
	public int create(TSesion datos) {
		String sql = "INSERT INTO sesion "
				+ "(objetivo, duracion, horario, idSala, idEntrenador, activo) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, datos.getObjetivo());
			ps.setInt(2, datos.getDuracion());
			ps.setString(3, datos.getFechaHora());
			ps.setInt(4, datos.getIdSala());
			ps.setInt(5, datos.getIdEntrenador());
			ps.setInt(6, datos.getActivo());
			int rows = ps.executeUpdate();
			if (rows > 0) {
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						return keys.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al crear la sesion: " + e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public TSesion read(int idSesion) {
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
	}

	@Override
	public int update(TSesion tSesion) {
		String sql = "UPDATE sesion SET objetivo=?, duracion=?, horario=?, "
				+ "idSala=?, idEntrenador=?, activo=? "
				+ "WHERE idSesion=?";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, tSesion.getObjetivo());
			ps.setInt(2, tSesion.getDuracion());
			ps.setString(3, tSesion.getFechaHora());
			ps.setInt(4, tSesion.getIdSala());
			ps.setInt(5, tSesion.getIdEntrenador());
			ps.setInt(6, tSesion.getActivo());
			ps.setInt(7, tSesion.getIdSesion());
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al modificar la sesion con ID " + tSesion.getIdSesion() + ": " + e.getMessage(), e);
		}
	}

	@Override
	public int delete(int idSesion) {
		String sql = "UPDATE sesion SET activo = 0 WHERE idSesion = ?";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSesion);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al eliminar la sesion con ID " + idSesion + ".", e);
		}
	}

	@Override
	public Set<TSesion> read_all() {
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
	}

	@Override
	public Set<TSesion> readByEntrenador(int idEntrenador) {
		return readByField("SELECT * FROM sesion WHERE idEntrenador = ? AND activo = 1", idEntrenador);
	}

	@Override
	public Set<TSesion> readBySala(int idSala) {
		return readByField("SELECT * FROM sesion WHERE idSala = ? AND activo = 1", idSala);
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


	@Override
	public TSala getRoom(int idSesion) {
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
	}

	@Override
	public Object getTrainer(int idSesion) {
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
					entrenador.set_id(rs.getInt("idEntrenador"));
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
	}

	private TSesion mapRow(ResultSet rs) throws SQLException {
		TSesion sesion = new TSesion();
		sesion.setIdSesion(rs.getInt("idSesion"));
		sesion.setObjetivo(rs.getString("objetivo"));
		sesion.setDuracion(rs.getInt("duracion"));
		sesion.setFechaHora(rs.getString("horario"));
		sesion.setIdSala(rs.getInt("idSala"));
		sesion.setIdEntrenador(rs.getInt("idEntrenador"));
		sesion.setActivo(rs.getInt("activo"));
		return sesion;
	}

	@Override
	public int countSalaHorario(int idSala, String horario) {
		String sql = "SELECT COUNT(*) FROM sesion WHERE idSala = ? AND horario = ? AND activo = 1";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSala);
			ps.setString(2, horario);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error checking sala horario: " + e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public int countConflictoHorarioSala(int idSala, String horario, int duracion) {
		String sql = "SELECT COUNT(*) FROM sesion s1 " +
				"WHERE s1.idSala = ? " +
				"AND s1.activo = 1 " +
				"AND DATE(s1.horario) = DATE(?) " +
				"AND STR_TO_DATE(s1.horario, '%Y-%m-%d %H:%i') < DATE_ADD(STR_TO_DATE(?, '%Y-%m-%d %H:%i'), INTERVAL ? MINUTE) " +
				"AND DATE_ADD(STR_TO_DATE(s1.horario, '%Y-%m-%d %H:%i'), INTERVAL s1.duracion MINUTE) > STR_TO_DATE(?, '%Y-%m-%d %H:%i')";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSala);
			ps.setString(2, horario);
			ps.setString(3, horario);
			ps.setInt(4, duracion);
			ps.setString(5, horario);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error checking conflicto horario sala: " + e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public int countConflictoHorarioSalaExcluyendo(int idSala, String horario, int duracion, int idSesionExcluir) {
		String sql = "SELECT COUNT(*) FROM sesion s1 " +
				"WHERE s1.idSala = ? " +
				"AND s1.idSesion != ? " +
				"AND s1.activo = 1 " +
				"AND DATE(s1.horario) = DATE(?) " +
				"AND STR_TO_DATE(s1.horario, '%Y-%m-%d %H:%i') < DATE_ADD(STR_TO_DATE(?, '%Y-%m-%d %H:%i'), INTERVAL ? MINUTE) " +
				"AND DATE_ADD(STR_TO_DATE(s1.horario, '%Y-%m-%d %H:%i'), INTERVAL s1.duracion MINUTE) > STR_TO_DATE(?, '%Y-%m-%d %H:%i')";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSala);
			ps.setInt(2, idSesionExcluir);
			ps.setString(3, horario);
			ps.setString(4, horario);
			ps.setInt(5, duracion);
			ps.setString(6, horario);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error checking conflicto horario sala excluyendo sesion: " + e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public int countClientesActivos(int idSesion) {
		String sql = "SELECT COUNT(*) FROM apunta WHERE idSesion = ? AND activo = 1";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSesion);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al contar clientes activos de la sesion con ID " + idSesion + ".", e);
		}
		return 0;
	}
}


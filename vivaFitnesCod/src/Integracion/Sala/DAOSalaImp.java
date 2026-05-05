
package Integracion.Sala;

import Integracion.ConnectionManager.ConnectionManager;
import Integracion.Sesion.TSesion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class DAOSalaImp implements DAOSala {

	private Connection getConnection() throws SQLException {
		return ConnectionManager.getConnection();
	}

	@Override
	public int create(TSala datos) {
		String sql = "INSERT INTO sala (nombreSala, aforo, activo) VALUES (?, ?, ?)";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, datos.getNombreSala());
			ps.setInt(2, datos.getAforo());
			ps.setInt(3, datos.getActivo());
			int rows = ps.executeUpdate();
			if (rows > 0) {
				try (ResultSet keys = ps.getGeneratedKeys()) {
					if (keys.next()) {
						return keys.getInt(1);
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al crear la sala: " + e.getMessage(), e);
		}
		return 0;
	}

	@Override
	public TSala read(int idSala) {
		String sql = "SELECT * FROM sala WHERE idSala = ?";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSala);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRow(rs);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al consultar la sala con ID " + idSala + ".", e);
		}
		return null;
	}

	@Override
	public int update(TSala tSala) {
		String sql = "UPDATE sala SET nombreSala=?, aforo=?, activo=? WHERE idSala=?";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, tSala.getNombreSala());
			ps.setInt(2, tSala.getAforo());
			ps.setInt(3, tSala.getActivo());
			ps.setInt(4, tSala.getIdSala());
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al modificar la sala con ID " + tSala.getIdSala() + ": " + e.getMessage(), e);
		}
	}

	@Override
	public int delete(int idSala) {
		Set<TSesion> sesionesActivas = readSessionsByRoom(idSala);
		if (!sesionesActivas.isEmpty()) {
			throw new RuntimeException(
				"No se puede dar de baja la sala con ID " + idSala + " porque tiene " + 
				sesionesActivas.size() + " sesión(es) activa(s) asignada(s).");
		}
		
		String sql = "UPDATE sala SET activo = 0 WHERE idSala = ?";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSala);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al eliminar la sala con ID " + idSala + ".", e);
		}
	}
	
	@Override
	public Set<TSala> read_all() {
		String sql = "SELECT * FROM sala";
		Set<TSala> salas = new HashSet<>();
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				salas.add(mapRow(rs));
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al listar todas las salas.", e);
		}
		return salas;
	}

	@Override
	public Set<TSesion> readSessionsByRoom(int idSala) {
		String sql = "SELECT * FROM sesion WHERE idSala = ? AND activo = 1";
		Set<TSesion> sesiones = new HashSet<>();
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSala);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					sesiones.add(mapSesion(rs));
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException("Error SQL al obtener sesiones de la sala con ID " + idSala + ".", e);
		}
		return sesiones;
	}

	private TSala mapRow(ResultSet rs) throws SQLException {
		TSala sala = new TSala();
		sala.setIdSala(rs.getInt("idSala"));
		sala.setNombreSala(rs.getString("nombreSala"));
		sala.setAforo(rs.getInt("aforo"));
		sala.setActivo(rs.getInt("activo"));
		return sala;
	}

	private TSesion mapSesion(ResultSet rs) throws SQLException {
		TSesion sesion = new TSesion();
		sesion.setIdSesion(rs.getInt("idSesion"));
		sesion.setIdSala(rs.getInt("idSala"));
		sesion.setObjetivo(rs.getString("objetivo"));
		sesion.setDuracion(rs.getInt("duracion"));
		sesion.setFechaHora(rs.getString("horario"));
		sesion.setActivo(rs.getInt("activo"));
		return sesion;
	}
}

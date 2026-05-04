package Integracion.Cliente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

import Integracion.ConnectionManager.ConnectionManager;
import Integracion.Sesion.TClienteSesion;
import Integracion.Sesion.TSesion;

public class DAOClienteImp implements DAOCliente {

	private RuntimeException databaseError(SQLException e) {
		return new RuntimeException("No se pudo completar la operacion de cliente en la base de datos.", e);
	}

	@Override
	public int create(TCliente datos) {
		String sql = "INSERT INTO cliente (DNI_cliente, nombreCliente, telefonoCliente, correoElectronico, activo) "
				+ "VALUES (?, ?, ?, ?, ?)";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			ps.setString(1, datos.get_dni());
			ps.setString(2, datos.get_nombre());
			ps.setString(3, datos.get_telefono());
			ps.setString(4, datos.get_correo());
			ps.setBoolean(5, datos.get_activo() == null || datos.get_activo());
			ps.executeUpdate();
			try (ResultSet rs = ps.getGeneratedKeys()) {
				return rs.next() ? rs.getInt(1) : -1;
			}
		} catch (SQLException e) {
			throw databaseError(e);
		}
	}

	@Override
	public TCliente read(int idCliente) {
		return readOne("SELECT * FROM cliente WHERE idCliente = ?", idCliente);
	}

	@Override
	public TCliente readByDni(String dni) {
		if (dni == null || dni.trim().isEmpty()) {
			return null;
		}
		String sql = "SELECT * FROM cliente WHERE DNI_cliente = ?";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, dni.trim());
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? mapCliente(rs) : null;
			}
		} catch (SQLException e) {
			throw databaseError(e);
		}
	}

	private TCliente readOne(String sql, int id) {
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? mapCliente(rs) : null;
			}
		} catch (SQLException e) {
			throw databaseError(e);
		}
	}

	@Override
	public int update(TCliente tCliente) {
		String sql = "UPDATE cliente SET DNI_cliente=?, nombreCliente=?, telefonoCliente=?, correoElectronico=?, activo=? "
				+ "WHERE idCliente=?";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, tCliente.get_dni());
			ps.setString(2, tCliente.get_nombre());
			ps.setString(3, tCliente.get_telefono());
			ps.setString(4, tCliente.get_correo());
			ps.setBoolean(5, tCliente.get_activo() == null || tCliente.get_activo());
			ps.setInt(6, tCliente.getId());
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw databaseError(e);
		}
	}

	@Override
	public int delete(int idCliente) {
		String sql = "UPDATE cliente SET activo = 0 WHERE idCliente = ?";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idCliente);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw databaseError(e);
		}
	}

	@Override
	public Set<TCliente> read_all() {
		Set<TCliente> clientes = new HashSet<>();
		String sql = "SELECT * FROM cliente ORDER BY idCliente";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				clientes.add(mapCliente(rs));
			}
			return clientes;
		} catch (SQLException e) {
			throw databaseError(e);
		}
	}

	@Override
	public int apuntarSesion(TClienteSesion datos) {
		String existeCliente = "SELECT activo FROM cliente WHERE idCliente = ?";
		String existeSesion = "SELECT s.idSesion, s.horario, sa.aforo, "
				+ "(SELECT COUNT(*) FROM apunta a WHERE a.idSesion = s.idSesion) AS inscritos "
				+ "FROM sesion s JOIN sala sa ON s.idSala = sa.idSala "
				+ "WHERE s.idSesion = ? AND s.activo = 1 AND sa.activo = 1";
		String existeApunte = "SELECT 1 FROM apunta WHERE idCliente = ? AND idSesion = ?";
		String insertar = "INSERT INTO apunta (idCliente, idSesion, fecha, hora) VALUES (?, ?, ?, ?)";

		try (Connection con = ConnectionManager.getConnection()) {
			boolean previousAutoCommit = con.getAutoCommit();
			con.setAutoCommit(false);
			try {
				if (!clienteActivo(con, existeCliente, datos.getIdCliente())) {
					con.rollback();
					return -2;
				}

				SesionInscripcion sesion = sesionParaInscripcion(con, existeSesion, datos.getIdSesion());
				if (sesion == null) {
					con.rollback();
					return -3;
				}
				if (yaApuntado(con, existeApunte, datos.getIdCliente(), datos.getIdSesion())) {
					con.rollback();
					return -4;
				}
				if (sesion.inscritos >= sesion.aforo) {
					con.rollback();
					return -5;
				}

				try (PreparedStatement ps = con.prepareStatement(insertar)) {
					ps.setInt(1, datos.getIdCliente());
					ps.setInt(2, datos.getIdSesion());
					ps.setDate(3, new Date(datos.getFecha().getTime()));
					ps.setTime(4, Time.valueOf(datos.getHora()));
					int result = ps.executeUpdate();
					con.commit();
					return result;
				}
			} catch (SQLException e) {
				con.rollback();
				throw e;
			} finally {
				con.setAutoCommit(previousAutoCommit);
			}
		} catch (SQLException e) {
			throw databaseError(e);
		}
	}

	@Override
	public int desapuntarSesion(int idCliente, int idSesion) {
		String sql = "DELETE FROM apunta WHERE idCliente = ? AND idSesion = ?";
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idCliente);
			ps.setInt(2, idSesion);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw databaseError(e);
		}
	}

	@Override
	public Set<TSesion> readSesionesCliente(int idCliente) {
		String sql = "SELECT s.*, a.fecha AS fechaApunte, a.hora AS horaApunte "
				+ "FROM apunta a JOIN sesion s ON a.idSesion = s.idSesion "
				+ "WHERE a.idCliente = ? ORDER BY s.horario";
		Set<TSesion> sesiones = new HashSet<>();
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idCliente);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					sesiones.add(mapSesionSql(rs));
				}
			}
			return sesiones;
		} catch (SQLException e) {
			throw databaseError(e);
		}
	}

	@Override
	public Set<TSesion> readSesionesDisponibles() {
		String sql = "SELECT s.*, sa.aforo, (SELECT COUNT(*) FROM apunta a WHERE a.idSesion = s.idSesion) AS inscritos "
				+ "FROM sesion s JOIN sala sa ON s.idSala = sa.idSala "
				+ "WHERE s.activo = 1 AND sa.activo = 1 ORDER BY s.horario";
		Set<TSesion> sesiones = new HashSet<>();
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				TSesion sesion = mapSesionSql(rs);
				sesion.setCapacidadMaxima(rs.getInt("aforo"));
				sesion.setParticipantsActuales(rs.getInt("inscritos"));
				sesiones.add(sesion);
			}
			return sesiones;
		} catch (SQLException e) {
			throw databaseError(e);
		}
	}

	private boolean clienteActivo(Connection con, String sql, int idCliente) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idCliente);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() && rs.getBoolean("activo");
			}
		}
	}

	private SesionInscripcion sesionParaInscripcion(Connection con, String sql, int idSesion) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSesion);
			try (ResultSet rs = ps.executeQuery()) {
				if (!rs.next()) {
					return null;
				}
				return new SesionInscripcion(rs.getInt("aforo"), rs.getInt("inscritos"));
			}
		}
	}

	private boolean yaApuntado(Connection con, String sql, int idCliente, int idSesion) throws SQLException {
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idCliente);
			ps.setInt(2, idSesion);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		}
	}

	private TCliente mapCliente(ResultSet rs) throws SQLException {
		TCliente cliente = new TCliente();
		cliente.setId(rs.getInt("idCliente"));
		cliente.set_dni(rs.getString("DNI_cliente"));
		cliente.set_nombre(rs.getString("nombreCliente"));
		cliente.set_telefono(rs.getString("telefonoCliente"));
		cliente.set_correo(rs.getString("correoElectronico"));
		cliente.set_activo(rs.getBoolean("activo"));
		return cliente;
	}

	private TSesion mapSesionSql(ResultSet rs) throws SQLException {
		TSesion sesion = new TSesion();
		sesion.setIdSesion(rs.getInt("idSesion"));
		sesion.setNombreSesion(rs.getString("objetivo"));
		sesion.setDescripcion(rs.getString("objetivo"));
		sesion.setIdEntrenador(rs.getInt("idEntrenador"));
		sesion.setIdSala(rs.getInt("idSala"));
		sesion.setActivo(rs.getInt("activo"));
		java.sql.Timestamp horario = rs.getTimestamp("horario");
		if (horario != null) {
			sesion.setFecha(horario.toLocalDateTime().toLocalDate().toString());
			sesion.setHora(horario.toLocalDateTime().toLocalTime().toString());
		}
		sesion.setCapacidadMaxima(0);
		sesion.setParticipantsActuales(0);
		return sesion;
	}

	private static class SesionInscripcion {
		private final int aforo;
		private final int inscritos;

		private SesionInscripcion(int aforo, int inscritos) {
			this.aforo = aforo;
			this.inscritos = inscritos;
		}
	}
}

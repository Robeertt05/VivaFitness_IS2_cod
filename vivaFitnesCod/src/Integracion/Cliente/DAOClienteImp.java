package Integracion.Cliente;

import Integracion.ConnectionManager.ConnectionManager;
import Integracion.Sesion.TClienteSesion;
import Integracion.Sesion.TSesion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

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
			ps.setInt(5, datos.get_activo());
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
			ps.setInt(5, tCliente.get_activo());
			ps.setInt(6, tCliente.getId());
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw databaseError(e);
		}
	}

	@Override
	public int delete(int idCliente) {
		Connection con = null;
		try {
			con = ConnectionManager.getConnection();
			
			// Validar que no existan inscripciones activas para el cliente
			String checkQuery = "SELECT COUNT(idClienteSesion) as count FROM apunta WHERE idCliente = ? AND activo = 1";
			try (PreparedStatement checkPs = con.prepareStatement(checkQuery)) {
				checkPs.setInt(1, idCliente);
				try (ResultSet rs = checkPs.executeQuery()) {
					if (rs.next()) {
						int countInscripciones = rs.getInt("count");
						if (countInscripciones > 0) {
							throw new RuntimeException(
								"No se puede dar de baja el cliente con ID " + idCliente + " porque tiene " + 
								countInscripciones + " inscripcion(es) activa(s) en sesiones.");
						}
					}
				}
			}
			
			// Actualizar cliente a inactivo
			String sql = "UPDATE cliente SET activo = 0 WHERE idCliente = ?";
			try (PreparedStatement ps = con.prepareStatement(sql)) {
				ps.setInt(1, idCliente);
				int result = ps.executeUpdate();
				return result;
			}
		} catch (RuntimeException re) {
			throw re;
		} catch (SQLException e) {
			throw databaseError(e);
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// Ignorar error al cerrar conexion
				}
			}
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
				+ "(SELECT COUNT(*) FROM apunta a WHERE a.idSesion = s.idSesion AND a.activo = 1) AS inscritos "
				+ "FROM sesion s JOIN sala sa ON s.idSala = sa.idSala "
				+ "WHERE s.idSesion = ? AND s.activo = 1 AND sa.activo = 1";
		String existeApunte = "SELECT 1 FROM apunta WHERE idCliente = ? AND idSesion = ? AND activo = 1";
		String insertar = "INSERT INTO apunta (idCliente, idSesion, fecha, hora) VALUES (?, ?, ?, ?)";

		try (Connection con = ConnectionManager.getConnection()) {
			boolean previousAutoCommit = con.getAutoCommit();
			con.setAutoCommit(false);
			try {
				if (!clienteActivo(con, existeCliente, datos.getIdCliente())) {
					con.rollback();
					return -2;
				}

				// Validar que fecha u hora no sean null
				if (datos.getFecha() == null || datos.getHora() == null) {
					con.rollback();
					return -6; // Fecha u hora inválidas
				}
				LocalDate fechaRegistro = datos.getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				SesionInscripcion sesion = sesionParaInscripcion(con, existeSesion, datos.getIdSesion());
				if (sesion == null) {
					con.rollback();
					return -3;
				}
				
				// Validar que la fecha de registro sea anterior o igual a la fecha de la sesión
				// y si es el mismo día, que sea al menos 10 minutos antes
				LocalTime horaRegistro = LocalTime.parse(datos.getHora());
				LocalTime horaRegistro10Min = horaRegistro.plusMinutes(10);
				if (sesion.horario != null) {
					try {
						String[] partes = sesion.horario.split(" ");
						if (partes.length >= 2) {
							LocalDate fechaSesion = LocalDate.parse(partes[0]);
							LocalTime horaSesion = LocalTime.parse(partes[1]);
							
							// Validar que la fecha de registro es anterior o igual a la de la sesión
							if (fechaRegistro.isAfter(fechaSesion)) {
								con.rollback();
								return -7; // El registro debe ser el mismo día o anterior a la sesión
							}
							
							// Si es el mismo día, validar que el registro es al menos 10 minutos antes
							if (fechaRegistro.equals(fechaSesion)) {
								if (horaRegistro10Min.isAfter(horaSesion)) {
									con.rollback();
									return -8; // No hay suficiente anticipación (debe ser al menos 10 minutos antes)
								}
							}
						}
					} catch (Exception e) {
						con.rollback();
						return -6; // Error al parsear fecha/hora de la sesión
					}
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
		String sql = "UPDATE apunta SET activo = 0 WHERE idCliente = ? AND idSesion = ?";
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
				+ "WHERE a.idCliente = ? AND a.activo = 1 AND s.activo = 1 ORDER BY s.horario";
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
		String sql = "SELECT s.*, sa.aforo, (SELECT COUNT(*) FROM apunta a WHERE a.idSesion = s.idSesion AND a.activo = 1) AS inscritos "
				+ "FROM sesion s JOIN sala sa ON s.idSala = sa.idSala "
				+ "WHERE s.activo = 1 AND sa.activo = 1 ORDER BY s.horario";
		Set<TSesion> sesiones = new HashSet<>();
		try (Connection con = ConnectionManager.getConnection();
				PreparedStatement ps = con.prepareStatement(sql);
				ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				TSesion sesion = mapSesionSql(rs);
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
				return rs.next() && rs.getInt("activo") == 1;
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
				return new SesionInscripcion(rs.getInt("aforo"), rs.getInt("inscritos"), rs.getString("horario"));
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
		cliente.set_activo(rs.getInt("activo"));
		return cliente;
	}

	private TSesion mapSesionSql(ResultSet rs) throws SQLException {
		TSesion sesion = new TSesion();
		sesion.setIdSesion(rs.getInt("idSesion"));
		sesion.setObjetivo(rs.getString("objetivo"));
		sesion.setDuracion(rs.getInt("duracion"));
		sesion.setIdEntrenador(rs.getInt("idEntrenador"));
		sesion.setIdSala(rs.getInt("idSala"));
		sesion.setActivo(rs.getInt("activo"));
		sesion.setFechaHora(rs.getString("horario"));
		return sesion;
	}

	private static class SesionInscripcion {
		private final int aforo;
		private final int inscritos;
		private final String horario;

		private SesionInscripcion(int aforo, int inscritos, String horario) {
			this.aforo = aforo;
			this.inscritos = inscritos;
			this.horario = horario;
		}
	}
}

/**
 * 
 */
package Integracion.FactoriaIntegracion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import Negocio.entrenador.TEntrenador;

/** 
 * Data Access Object implementation for Sesion (SRS Aligned)
 * @author azuri
 */
public class DAOSesionImp implements DAOSesion {

	private static final String DB_URL  = "jdbc:mysql://localhost:3306/vivafitness";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "root";

	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
	}

	// -----------------------------------------------------------------------
	// CRUD bÃÂ¡sico
	// -----------------------------------------------------------------------

	@Override
	public int create(TSesion datos) {
		// begin-user-code
		String sql = "INSERT INTO sesiones "
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
			e.printStackTrace();
		}
		return 0;
		// end-user-code
	}

	@Override
	public TSesion read(int idSesion) {
		// begin-user-code
		String sql = "SELECT * FROM sesiones WHERE idSesion = ? AND activo = 1";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSesion);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return mapRow(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		// end-user-code
	}

	@Override
	public int update(TSesion tSesion) {
		// begin-user-code
		String sql = "UPDATE sesiones SET nombreSesion=?, descripcion=?, fecha=?, hora=?, "
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
			e.printStackTrace();
		}
		return 0;
		// end-user-code
	}

	@Override
	public int delete(int idSesion) {
		// begin-user-code
		// Precondition enforced in SQL: only deletes when no participants registered
		String sql = "DELETE FROM sesiones WHERE idSesion = ? AND participantsActuales = 0";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSesion);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		// end-user-code
	}

	// -----------------------------------------------------------------------
	// Consultas
	// -----------------------------------------------------------------------

	@Override
	public Set<TSesion> read_all() {
		// begin-user-code
		String sql = "SELECT * FROM sesiones WHERE activo = 1";
		Set<TSesion> sesiones = new HashSet<>();
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql);
			 ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				sesiones.add(mapRow(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sesiones;
		// end-user-code
	}

	@Override
	public Set<TSesion> readByEntrenador(int idEntrenador) {
		// begin-user-code
		return readByField("SELECT * FROM sesiones WHERE idEntrenador = ? AND activo = 1", idEntrenador);
		// end-user-code
	}

	@Override
	public Set<TSesion> readBySala(int idSala) {
		// begin-user-code
		return readByField("SELECT * FROM sesiones WHERE idSala = ? AND activo = 1", idSala);
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
			e.printStackTrace();
		}
		return sesiones;
	}

	// -----------------------------------------------------------------------
	// CASO 4: Mostrar sala por sesiÃÂ³n
	// -----------------------------------------------------------------------

	@Override
	public TSala getRoom(int idSesion) {
		// begin-user-code
		String sql = "SELECT sa.idSala, sa.nombreSala, sa.aforo, sa.activo "
				+ "FROM sesiones s "
				+ "JOIN salas sa ON s.idSala = sa.idSala "
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
			e.printStackTrace();
		}
		return null;
		// end-user-code
	}

	// -----------------------------------------------------------------------
	// CASO 5: Mostrar entrenador por sesiÃÂ³n
	// -----------------------------------------------------------------------

	@Override
	public Object getTrainer(int idSesion) {
		// begin-user-code
		String sql = "SELECT e.id_entrenador, e.dni_entrenador, e.nombre, e.telefono, e.activo "
				+ "FROM sesiones s "
				+ "JOIN entrenadores e ON s.idEntrenador = e.id_entrenador "
				+ "WHERE s.idSesion = ? AND s.activo = 1";
		try (Connection con = getConnection();
			 PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idSesion);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					TEntrenador entrenador = new TEntrenador();
					entrenador.set_activo(rs.getInt("activo"));
					entrenador.set_nombre(rs.getString("nombre"));
					entrenador.set_telefono(rs.getString("telefono"));
					entrenador.set_dni(rs.getString("dni_entrenador"));
					return entrenador;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
package Negocio.Entrenador;

import Integracion.Entrenador.DAOEntrenador;
import Integracion.Entrenador.TEntrenador;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Sala.DAOSala;
import Integracion.Sala.TSala;
import Integracion.Sesion.DAOSesion;
import Integracion.Sesion.TSesion;
import java.util.Set;


public class SAEntrenadorImp implements SAEntrenador {

	@Override
	public int alta_entrenador(TEntrenador datos) {
		if (datos == null || datos.get_dni() == null || datos.get_dni().isEmpty()
				|| datos.get_nombre() == null || datos.get_nombre().isEmpty()) {
			return -1;
		}

		DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
		TEntrenador existente = dao.read_by_dni(datos.get_dni());
		if (existente != null) {
			if (existente.get_activo() == 1) {
				return -1;
			}
			existente.set_nombre(datos.get_nombre());
			existente.set_telefono(datos.get_telefono());
			existente.set_activo(1);
			int updated = dao.update(existente);
			return updated > 0 ? existente.get_id() : -1;
		}

		datos.set_activo(1);
		return dao.create(datos);
	}

	@Override
	public int baja_entrenador(int id) {
		if (id <= 0) {
			return -1;
		}

		DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
		TEntrenador entrenador = dao.read(id);
		if (entrenador == null || entrenador.get_activo() == 0) {
			return -1;
		}

		DAOSesion daoSesion = FactoriaIntegracion.getInstance().generaDAOSesion();
		Set<TSesion> sesiones = daoSesion.readByEntrenador(id);
		if (sesiones != null && !sesiones.isEmpty()) {
			return -2; 
		}

		entrenador.set_activo(0);
		return dao.update(entrenador);
	}

	@Override
	public int modificar_entrenador(int id, TEntrenador datos) {
		if (id <= 0 || datos == null) {
			return -1;
		}

		DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
		TEntrenador entrenador = dao.read(id);
		if (entrenador == null || entrenador.get_activo() == 0) {
			return -1;
		}

		if (datos.get_nombre() != null && !datos.get_nombre().isEmpty()) {
			entrenador.set_nombre(datos.get_nombre());
		}
		if (datos.get_telefono() != null && !datos.get_telefono().isEmpty()) {
			entrenador.set_telefono(datos.get_telefono());
		}
		if (datos.get_dni() != null && !datos.get_dni().isEmpty()) {
			TEntrenador existenteDni = dao.read_by_dni(datos.get_dni());
			if (existenteDni != null && existenteDni.get_id() != id) {
				if (existenteDni.get_activo() == 1) {
					return -2;
				}
				
				existenteDni.set_dni(null);
				if (dao.update(existenteDni) <= 0) {
					return -1;
				}
			}
			entrenador.set_dni(datos.get_dni());
		}

		return dao.update(entrenador);
	}

	@Override
	public TEntrenador mostrar_entrenador(int id) {
		if (id <= 0) {
			return null;
		}

		TEntrenador entrenador = FactoriaIntegracion.getInstance().generaDAOEntrenador().read(id);
		return (entrenador != null && entrenador.get_activo() == 1) ? entrenador : null;
	}

	@Override
	public int crear_sesion(TSesion datos) {
		validarSesionAlta(datos);

		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		TSala sala = daoSala.read(datos.getIdSala());
		if (sala == null || sala.getActivo() != 1) {
			throw new IllegalArgumentException("Sala inválida o inactiva: ID " + datos.getIdSala());
		}

		DAOEntrenador daoEntrenador = FactoriaIntegracion.getInstance().generaDAOEntrenador();
		TEntrenador entrenador = daoEntrenador.read(datos.getIdEntrenador());
		if (entrenador == null || entrenador.get_activo() != 1) {
			throw new IllegalArgumentException("Entrenador invalidos o inactivo: ID " + datos.getIdEntrenador());
		}

		DAOSesion daoCheck = FactoriaIntegracion.getInstance().generaDAOSesion();
		int count = daoCheck.countConflictoHorarioSala(datos.getIdSala(), datos.getFechaHora(), datos.getDuracion());
		if (count > 0) {
			throw new IllegalArgumentException("Sala ocupada en ese horario. Existe otra sesion en el mismo horario o con solapamiento.");
		}

		datos.setActivo(1);

		DAOSesion daoSesion = FactoriaIntegracion.getInstance().generaDAOSesion();
		try {
			int id = daoSesion.create(datos);
			if (id <= 0) {
				throw new RuntimeException("No se pudo crear la sesión en base de datos.");
			}
			return id;
		} catch (RuntimeException e) {
			throw e;
		}
	}

	private void validarSesionAlta(TSesion datos) {
		if (datos == null) {
			throw new IllegalArgumentException("Los datos de la sesion no pueden ser nulos.");
		}

		if (datos.getObjetivo() == null || datos.getObjetivo().trim().isEmpty()) {
			throw new IllegalArgumentException("El objetivo de la sesion es obligatorio.");
		}

		if (datos.getFechaHora() == null || datos.getFechaHora().trim().isEmpty()) {
			throw new IllegalArgumentException("El horario de la sesion es obligatorio.");
		}

		if (datos.getDuracion() <= 0) {
			throw new IllegalArgumentException("La duracion de la sesion debe ser mayor que 0.");
		}

		if (datos.getIdSala() <= 0) {
			throw new IllegalArgumentException("Debe indicar una sala valida para la sesion.");
		}

		if (datos.getIdEntrenador() <= 0) {
			throw new IllegalArgumentException("Debe indicar un entrenador valido para la sesion.");
		}
	}
}

package Negocio.Sesion;

import Integracion.Entrenador.DAOEntrenador;
import Integracion.Entrenador.TEntrenador;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Sala.DAOSala;
import Integracion.Sala.TSala;
import Integracion.Sesion.DAOSesion;
import Integracion.Sesion.TSesion;

public class SASesionImp implements SASesion {

	@Override
	public int alta_sesion(TSesion datos) {
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

	@Override
	public int baja_sesion(int idSesion) {
		if (idSesion <= 0) {
			throw new IllegalArgumentException("El ID de sesion debe ser mayor que 0.");
		}

		DAOSesion daoSesion = FactoriaIntegracion.getInstance().generaDAOSesion();
		TSesion sesion;
		try {
			sesion = daoSesion.read(idSesion);
		} catch (RuntimeException e) {
			throw e;
		}

		if (sesion == null) {
			throw new IllegalArgumentException("La sesion con ID " + idSesion + " no existe.");
		}

		int clientesActivos = daoSesion.countClientesActivos(idSesion);
		if (clientesActivos > 0) {
			return -2; 
		}

		int result = daoSesion.delete(idSesion);
		if (result <= 0) {
			throw new RuntimeException("No se pudo eliminar la sesion en base de datos.");
		}
		return result;
	}

	@Override
	public int modificar_sesion(int idSesion, TSesion datos) {
		if (idSesion <= 0) {
			throw new IllegalArgumentException("El ID de sesion debe ser mayor que 0.");
		}

		if (datos == null) {
			throw new IllegalArgumentException("Los datos de la sesion no pueden ser nulos.");
		}

		DAOSesion daoSesion = FactoriaIntegracion.getInstance().generaDAOSesion();
		TSesion existente = daoSesion.read(idSesion);

		if (existente == null) {
			throw new IllegalArgumentException("La sesion con ID " + idSesion + " no existe.");
		}

		TSesion actualizada = combinarDatos(existente, datos);
		validarSesionModificacion(actualizada);

		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		TSala sala = daoSala.read(actualizada.getIdSala());
		if (sala == null || sala.getActivo() != 1) {
			throw new IllegalArgumentException("Sala inválida o inactiva: ID " + actualizada.getIdSala());
		}

		DAOEntrenador daoEntrenador = FactoriaIntegracion.getInstance().generaDAOEntrenador();
		TEntrenador entrenador = daoEntrenador.read(actualizada.getIdEntrenador());
		if (entrenador == null || entrenador.get_activo() != 1) {
			throw new IllegalArgumentException("Entrenador inválido o inactivo: ID " + actualizada.getIdEntrenador());
		}

		if (actualizada.getIdSala() != existente.getIdSala() || 
			!actualizada.getFechaHora().equals(existente.getFechaHora()) ||
			actualizada.getDuracion() != existente.getDuracion()) {
			
			int conflictos = daoSesion.countConflictoHorarioSalaExcluyendo(
				actualizada.getIdSala(), 
				actualizada.getFechaHora(), 
				actualizada.getDuracion(), 
				idSesion
			);
			
			if (conflictos > 0) {
				throw new IllegalArgumentException("Sala ocupada en ese horario. Existe otra sesion en el mismo horario o con solapamiento.");
			}
		}

		int result = daoSesion.update(actualizada);
		if (result <= 0) {
			throw new RuntimeException("No se pudo modificar la sesion en base de datos.");
		}
		return result;
	}

	@Override
	public TSesion mostrar_sesion(int idSesion) {
		if (idSesion <= 0) {
			return null;
		}
		DAOSesion daoSesion = FactoriaIntegracion.getInstance().generaDAOSesion();
		TSesion sesion = daoSesion.read(idSesion);
		return (sesion != null && sesion.getActivo() == 1) ? sesion : null;
	}



	@Override
	public TSala mostrar_sala_sesion(int idSesion) {
		if (idSesion <= 0) {
			return null;
		}
		DAOSesion daoSesion = FactoriaIntegracion.getInstance().generaDAOSesion();
		return daoSesion.getRoom(idSesion);
	}

	@Override
	public TEntrenador mostrar_entrenador_sesion(int idSesion) {
		if (idSesion <= 0) {
			return null;
		}
		DAOSesion daoSesion = FactoriaIntegracion.getInstance().generaDAOSesion();
		return (TEntrenador) daoSesion.getTrainer(idSesion);
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

	private void validarSesionModificacion(TSesion datos) {
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

	private TSesion combinarDatos(TSesion existente, TSesion cambios) {
		TSesion merged = new TSesion();
		merged.setIdSesion(existente.getIdSesion());
		merged.setObjetivo(obtenerTexto(cambios.getObjetivo(), existente.getObjetivo()));
		merged.setDuracion(cambios.getDuracion() > 0 ? cambios.getDuracion() : existente.getDuracion());
		merged.setFechaHora(obtenerTexto(cambios.getFechaHora(), existente.getFechaHora()));
		merged.setIdSala(cambios.getIdSala() > 0 ? cambios.getIdSala() : existente.getIdSala());
		merged.setIdEntrenador(cambios.getIdEntrenador() > 0 ? cambios.getIdEntrenador() : existente.getIdEntrenador());
		merged.setActivo(existente.getActivo());
		return merged;
	}

	private String obtenerTexto(String nuevo, String actual) {
		if (nuevo == null || nuevo.trim().isEmpty()) {
			return actual;
		}
		return nuevo.trim();
	}
}

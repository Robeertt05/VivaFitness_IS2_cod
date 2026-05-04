package Negocio.FactoriaNegocio;


import Integracion.Sala.TSala;
import Integracion.Sesion.DAOSesion;
import Integracion.Sesion.TSesion;
import Integracion.Entrenador.TEntrenador;

import java.util.Set;

public class SASesionImp implements SASesion {

	private DAOSesion daoSesion;

	public SASesionImp(DAOSesion daoSesion) {
		this.daoSesion = daoSesion;
	}

	@Override
	public int alta_sesion(TSesion datos) {
		validarSesionAlta(datos);

		if (datos.getParticipantsActuales() < 0) {
			datos.setParticipantsActuales(0);
		}
		datos.setActivo(1);

		try {
			int id = daoSesion.create(datos);
			if (id <= 0) {
				throw new RuntimeException("No se pudo crear la sesion en base de datos.");
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

		TSesion sesion;
		try {
			sesion = daoSesion.read(idSesion);
		} catch (RuntimeException e) {
			throw e;
		}

		if (sesion == null) {
			throw new IllegalArgumentException("La sesion con ID " + idSesion + " no existe.");
		}

		if (sesion.getParticipantsActuales() > 0) {
			throw new IllegalArgumentException(
				"No se puede eliminar la sesion porque tiene " + sesion.getParticipantsActuales() + " participantes.");
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

		TSesion existente = daoSesion.read(idSesion);

		if (existente == null) {
			throw new IllegalArgumentException("La sesion con ID " + idSesion + " no existe.");
		}

		TSesion actualizada = combinarDatos(existente, datos);
		validarSesionModificacion(actualizada);

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
		return daoSesion.read(idSesion);
	}

	@Override
	public Set<TSesion> mostrar_todas_sesiones() {
		return daoSesion.read_all();
	}

	@Override
	public TSala mostrar_sala_sesion(int idSesion) {
		if (idSesion <= 0) {
			return null;
		}
		return daoSesion.getRoom(idSesion);
	}

	@Override
	public TEntrenador mostrar_entrenador_sesion(int idSesion) {
		if (idSesion <= 0) {
			return null;
		}
		return (TEntrenador) daoSesion.getTrainer(idSesion);
	}

	private void validarSesionAlta(TSesion datos) {
		if (datos == null) {
			throw new IllegalArgumentException("Los datos de la sesion no pueden ser nulos.");
		}

		if (datos.getNombreSesion() == null || datos.getNombreSesion().trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la sesion es obligatorio.");
		}

		if (datos.getHora() == null || datos.getHora().trim().isEmpty()) {
			throw new IllegalArgumentException("La hora de la sesion es obligatoria.");
		}

		if (datos.getFecha() == null || datos.getFecha().trim().isEmpty()) {
			throw new IllegalArgumentException("La fecha de la sesion es obligatoria.");
		}

		if (datos.getIdSala() <= 0) {
			throw new IllegalArgumentException("Debe indicar una sala valida para la sesion.");
		}

		if (datos.getIdEntrenador() <= 0) {
			throw new IllegalArgumentException("Debe indicar un entrenador valido para la sesion.");
		}

		if (datos.getCapacidadMaxima() <= 0) {
			throw new IllegalArgumentException("La capacidad maxima debe ser mayor que 0.");
		}

		if (datos.getParticipantsActuales() > datos.getCapacidadMaxima()) {
			throw new IllegalArgumentException("Los participantes actuales no pueden superar la capacidad maxima.");
		}
	}

	private void validarSesionModificacion(TSesion datos) {
		if (datos.getNombreSesion() == null || datos.getNombreSesion().trim().isEmpty()) {
			throw new IllegalArgumentException("El nombre de la sesion es obligatorio.");
		}

		if (datos.getHora() == null || datos.getHora().trim().isEmpty()) {
			throw new IllegalArgumentException("La hora de la sesion es obligatoria.");
		}

		if (datos.getFecha() == null || datos.getFecha().trim().isEmpty()) {
			throw new IllegalArgumentException("La fecha de la sesion es obligatoria.");
		}

		if (datos.getIdSala() <= 0) {
			throw new IllegalArgumentException("Debe indicar una sala valida para la sesion.");
		}

		if (datos.getIdEntrenador() <= 0) {
			throw new IllegalArgumentException("Debe indicar un entrenador valido para la sesion.");
		}

		if (datos.getCapacidadMaxima() <= 0) {
			throw new IllegalArgumentException("La capacidad maxima debe ser mayor que 0.");
		}

		if (datos.getParticipantsActuales() < 0) {
			throw new IllegalArgumentException("Los participantes actuales no pueden ser negativos.");
		}

		if (datos.getParticipantsActuales() > datos.getCapacidadMaxima()) {
			throw new IllegalArgumentException("Los participantes actuales no pueden superar la capacidad maxima.");
		}
	}

	private TSesion combinarDatos(TSesion existente, TSesion cambios) {
		TSesion merged = new TSesion();
		merged.setIdSesion(existente.getIdSesion());
		merged.setNombreSesion(obtenerTexto(cambios.getNombreSesion(), existente.getNombreSesion()));
		merged.setDescripcion(obtenerTexto(cambios.getDescripcion(), existente.getDescripcion()));
		merged.setFecha(obtenerTexto(cambios.getFecha(), existente.getFecha()));
		merged.setHora(obtenerTexto(cambios.getHora(), existente.getHora()));
		merged.setIdSala(cambios.getIdSala() > 0 ? cambios.getIdSala() : existente.getIdSala());
		merged.setIdEntrenador(cambios.getIdEntrenador() > 0 ? cambios.getIdEntrenador() : existente.getIdEntrenador());
		merged.setCapacidadMaxima(cambios.getCapacidadMaxima() > 0 ? cambios.getCapacidadMaxima() : existente.getCapacidadMaxima());
		merged.setParticipantsActuales(
				cambios.getParticipantsActuales() > 0 ? cambios.getParticipantsActuales() : existente.getParticipantsActuales());
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

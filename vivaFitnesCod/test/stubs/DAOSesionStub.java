package stubs;

import Integracion.Entrenador.TEntrenador;
import Integracion.Sala.TSala;
import Integracion.Sesion.DAOSesion;
import Integracion.Sesion.TSesion;
import java.util.*;

/**
 * Stub (fake) de DAOSesion para pruebas unitarias.
 * Almacena datos en memoria usando HashMap.
 */
public class DAOSesionStub implements DAOSesion {

	private Map<Integer, TSesion> sesiones = new HashMap<>();
	private int nextId = 1;

	// Referencias a otros stubs para relaciones
	private DAOSalaStub daoSalaStub;
	private DAOEntrenadorStub daoEntrenadorStub;

	public void setDAOSalaStub(DAOSalaStub stub) {
		this.daoSalaStub = stub;
	}

	public void setDAOEntrenadorStub(DAOEntrenadorStub stub) {
		this.daoEntrenadorStub = stub;
	}

	@Override
	public int create(TSesion datos) {
		datos.setIdSesion(nextId);
		sesiones.put(nextId, datos);
		return nextId++;
	}

	@Override
	public TSesion read(int idSesion) {
		return sesiones.get(idSesion);
	}

	@Override
	public int update(TSesion tSesion) {
		if (sesiones.containsKey(tSesion.getIdSesion())) {
			sesiones.put(tSesion.getIdSesion(), tSesion);
			return 1;
		}
		return 0;
	}

	@Override
	public int delete(int idSesion) {
		if (sesiones.containsKey(idSesion)) {
			sesiones.remove(idSesion);
			return 1;
		}
		return 0;
	}

	@Override
	public Set<TSesion> read_all() {
		return new HashSet<>(sesiones.values());
	}

	@Override
	public Set<TSesion> readByEntrenador(int idEntrenador) {
		Set<TSesion> result = new HashSet<>();
		for (TSesion s : sesiones.values()) {
			if (s.getIdEntrenador() == idEntrenador) {
				result.add(s);
			}
		}
		return result;
	}

	@Override
	public Set<TSesion> readBySala(int idSala) {
		Set<TSesion> result = new HashSet<>();
		for (TSesion s : sesiones.values()) {
			if (s.getIdSala() == idSala) {
				result.add(s);
			}
		}
		return result;
	}

	@Override
	public TSala getRoom(int idSesion) {
		TSesion sesion = sesiones.get(idSesion);
		if (sesion != null && daoSalaStub != null) {
			return daoSalaStub.read(sesion.getIdSala());
		}
		return null;
	}

	@Override
	public Object getTrainer(int idSesion) {
		TSesion sesion = sesiones.get(idSesion);
		if (sesion != null && daoEntrenadorStub != null) {
			return daoEntrenadorStub.read(sesion.getIdEntrenador());
		}
		return null;
	}

	@Override
	public int countSalaHorario(int idSala, String horario) {
		int count = 0;
		for (TSesion s : sesiones.values()) {
			if (s.getIdSala() == idSala && s.getActivo() == 1
					&& s.getFechaHora() != null && s.getFechaHora().equals(horario)) {
				count++;
			}
		}
		return count;
	}

	@Override
	public int countClientesActivos(int idSesion) {
		if (daoClienteStub != null) {
			int count = 0;
			for (Integracion.Sesion.TClienteSesion cs : daoClienteStub.getInscripciones()) {
				if (cs.getIdSesion() == idSesion) {
					count++;
				}
			}
			return count;
		}
		return 0;
	}

	// Referencia al stub de clientes para consultas cruzadas
	private DAOClienteStub daoClienteStub;

	public void setDAOClienteStub(DAOClienteStub stub) {
		this.daoClienteStub = stub;
	}

	public void clear() {
		sesiones.clear();
		nextId = 1;
	}
}

package stubs;

import Integracion.Entrenador.DAOEntrenador;
import Integracion.Entrenador.TEntrenador;
import java.util.*;

/**
 * Stub (fake) de DAOEntrenador para pruebas unitarias.
 * Almacena datos en memoria usando HashMap.
 */
public class DAOEntrenadorStub implements DAOEntrenador {

	private Map<Integer, TEntrenador> entrenadores = new HashMap<>();
	private int nextId = 1;

	@Override
	public int create(TEntrenador datos) {
		datos.set_id(nextId);
		entrenadores.put(nextId, datos);
		return nextId++;
	}

	@Override
	public TEntrenador read(int idEntrenador) {
		return entrenadores.get(idEntrenador);
	}

	@Override
	public int update(TEntrenador tEntrenador) {
		if (entrenadores.containsKey(tEntrenador.get_id())) {
			entrenadores.put(tEntrenador.get_id(), tEntrenador);
			return 1;
		}
		return 0;
	}

	@Override
	public int delete(int idEntrenador) {
		if (entrenadores.containsKey(idEntrenador)) {
			entrenadores.remove(idEntrenador);
			return 1;
		}
		return 0;
	}

	@Override
	public Set<TEntrenador> read_all() {
		return new HashSet<>(entrenadores.values());
	}

	@Override
	public TEntrenador read_by_dni(String dni) {
		for (TEntrenador e : entrenadores.values()) {
			if (e.get_dni() != null && e.get_dni().equals(dni)) {
				return e;
			}
		}
		return null;
	}

	public void clear() {
		entrenadores.clear();
		nextId = 1;
	}
}

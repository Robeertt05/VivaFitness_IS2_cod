package stubs;

import Integracion.Cliente.DAOCliente;
import Integracion.Cliente.TCliente;
import Integracion.Sesion.TClienteSesion;
import Integracion.Sesion.TSesion;
import java.util.*;

/**
 * Stub (fake) de DAOCliente para pruebas unitarias.
 * Almacena datos en memoria usando HashMap.
 */
public class DAOClienteStub implements DAOCliente {

	private Map<Integer, TCliente> clientes = new HashMap<>();
	private List<TClienteSesion> inscripciones = new ArrayList<>();
	private int nextId = 1;

	// Referencia al stub de sesiones para consultas cruzadas
	private DAOSesionStub daoSesionStub;

	public void setDAOSesionStub(DAOSesionStub stub) {
		this.daoSesionStub = stub;
	}

	@Override
	public int create(TCliente datos) {
		datos.setId(nextId);
		clientes.put(nextId, datos);
		return nextId++;
	}

	@Override
	public TCliente read(int idCliente) {
		return clientes.get(idCliente);
	}

	@Override
	public int update(TCliente tCliente) {
		if (clientes.containsKey(tCliente.getId())) {
			clientes.put(tCliente.getId(), tCliente);
			return 1;
		}
		return 0;
	}

	@Override
	public int delete(int idCliente) {
		if (clientes.containsKey(idCliente)) {
			clientes.remove(idCliente);
			return 1;
		}
		return 0;
	}

	@Override
	public Set<TCliente> read_all() {
		return new HashSet<>(clientes.values());
	}

	@Override
	public TCliente readByDni(String dni) {
		for (TCliente c : clientes.values()) {
			if (c.get_dni() != null && c.get_dni().equals(dni)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public int apuntarSesion(TClienteSesion datos) {
		inscripciones.add(datos);
		return 1;
	}

	@Override
	public int desapuntarSesion(int idCliente, int idSesion) {
		return inscripciones.removeIf(
			i -> i.getIdCliente() == idCliente && i.getIdSesion() == idSesion
		) ? 1 : 0;
	}

	@Override
	public Set<TSesion> readSesionesDisponibles() {
		if (daoSesionStub != null) {
			return daoSesionStub.read_all();
		}
		return new HashSet<>();
	}

	@Override
	public Set<TSesion> readSesionesCliente(int idCliente) {
		Set<TSesion> result = new HashSet<>();
		if (daoSesionStub != null) {
			for (TClienteSesion cs : inscripciones) {
				if (cs.getIdCliente() == idCliente) {
					TSesion s = daoSesionStub.read(cs.getIdSesion());
					if (s != null) result.add(s);
				}
			}
		}
		return result;
	}

	// Métodos auxiliares para tests
	public List<TClienteSesion> getInscripciones() {
		return inscripciones;
	}

	public void clear() {
		clientes.clear();
		inscripciones.clear();
		nextId = 1;
	}
}

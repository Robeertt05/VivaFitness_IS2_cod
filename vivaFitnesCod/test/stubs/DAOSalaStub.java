package stubs;

import Integracion.Sala.DAOSala;
import Integracion.Sala.TSala;
import Integracion.Sesion.TSesion;
import java.util.*;


public class DAOSalaStub implements DAOSala {

	private Map<Integer, TSala> salas = new HashMap<>();
	private int nextId = 1;


	private DAOSesionStub daoSesionStub;

	public void setDAOSesionStub(DAOSesionStub stub) {
		this.daoSesionStub = stub;
	}

	@Override
	public int create(TSala datos) {
		datos.setIdSala(nextId);
		salas.put(nextId, datos);
		return nextId++;
	}

	@Override
	public TSala read(int idSala) {
		return salas.get(idSala);
	}

	@Override
	public int update(TSala tSala) {
		if (salas.containsKey(tSala.getIdSala())) {
			salas.put(tSala.getIdSala(), tSala);
			return 1;
		}
		return 0;
	}

	@Override
	public int delete(int idSala) {
		if (salas.containsKey(idSala)) {
			salas.remove(idSala);
			return 1;
		}
		return 0;
	}

	@Override
	public Set<TSala> read_all() {
		return new HashSet<>(salas.values());
	}

	@Override
	public Set<TSesion> readSessionsByRoom(int idSala) {
		Set<TSesion> result = new HashSet<>();
		if (daoSesionStub != null) {
			for (TSesion s : daoSesionStub.read_all()) {
				if (s.getIdSala() == idSala && s.getActivo() == 1) {
					result.add(s);
				}
			}
		}
		return result;
	}

	public void clear() {
		salas.clear();
		nextId = 1;
	}
}

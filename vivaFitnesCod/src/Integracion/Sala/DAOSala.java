
package Integracion.Sala;

import java.util.Set;

import Integracion.Sesion.TSesion;

public interface DAOSala {

	public int create(TSala datos);

	public TSala read(int idSala);

	public int update(TSala tSala);

	public int delete(int idSala);

	public Set<TSala> read_all();

	public Set<TSesion> readSessionsByRoom(int idSala);
}

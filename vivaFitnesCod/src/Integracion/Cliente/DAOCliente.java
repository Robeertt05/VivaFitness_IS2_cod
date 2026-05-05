
package Integracion.Cliente;

import java.util.Set;

import Integracion.Sesion.TSesion;
import Integracion.Sesion.TClienteSesion;

 public interface DAOCliente {

	public int create(TCliente datos);

	public TCliente read(int idCliente);

	public int update(TCliente tCliente);

	public int delete(int idCliente);

	public Set<TCliente> read_all();

	public TCliente readByDni(String dni);

	public int apuntarSesion(Integracion.Sesion.TClienteSesion datos);

	public int desapuntarSesion(int idCliente, int idSesion);

	public Set<TSesion> readSesionesDisponibles();

	public Set<TSesion> readSesionesCliente(int idCliente);
}

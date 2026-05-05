
package Negocio.Cliente;

import Integracion.Cliente.TCliente;
import Integracion.Sesion.TClienteSesion;
import Integracion.Sesion.TSesion;
import java.util.Set;


public interface SACliente {

	public int alta_cliente(TCliente datos);

	public int baja_cliente(int id);

	public int modificar_cliente(int id, TCliente datos);

	public TCliente mostrar_cliente(int id);

	public Set<TCliente> mostrar_todos_clientes();

	public int apuntarse_sesion(TClienteSesion datos);

	public Set<TSesion> mostrar_sesiones();

	public int desapuntar_sesion(int id, int idSesion);

	public Set<TSesion> mostrar_sesiones_cliente(int idCliente);
}


package Negocio.Sala;

import Integracion.Sala.TSala;
import Integracion.Sesion.TSesion;
import java.util.Set;


public interface SASala {
	
	public int alta_sala(TSala datos);
	
	public int baja_sala(int idSala);
	
	public int modificar_sala(int idSala, TSala datos);
	
	public TSala mostrar_sala(int idSala);

	public Set<TSala> mostrar_todas_salas();

	public Set<TSesion> obtener_sesiones_sala(int idSala);
}

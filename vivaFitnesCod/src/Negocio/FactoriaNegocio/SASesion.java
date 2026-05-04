/**
 * 
 */
package Negocio.FactoriaNegocio;

import Integracion.FactoriaIntegracion.TSesion;
import Integracion.Sala.TSala;
import Negocio.entrenador.TEntrenador;
import java.util.Set;

/** 

 * @author azuri
 */
public interface SASesion {
	
	/** 
	 * Alta sesin  crear nueva sesin
	 * @param datos Session data
	 * @return ID de la sesin creada, 0 si falla
	 */
	public int alta_sesion(TSesion datos);

	/** 
	 * CASO 1: Baja sesin  eliminar sesin (solo si no tiene clientes apuntados)
	 * @param idSesion Session ID
	 * @return 1 si OK, 0 si tiene clientes o no existe
	 */
	public int baja_sesion(int idSesion);

	/** 
	 * CASO 2: Modificar sesin  actualizar atributos (excepto ID)
	 * @param idSesion Session ID
	 * @param datos Datos actualizados
	 * @return 1 si OK, 0 si falla
	 */
	public int modificar_sesion(int idSesion, TSesion datos);

	/** 
	 * CASO 3: Mostrar sesin  obtener detalles de una sesin
	 * @param idSesion Session ID
	 * @return TSesion o null si no existe
	 */
	public TSesion mostrar_sesion(int idSesion);

	/** 
	 * Mostrar todas las sesiones activas
	 * @return Set de todas las sesiones activas
	 */
	public Set<TSesion> mostrar_todas_sesiones();

	/** 
	 * CASO 4: Mostrar sala por sesin
	 * @param idSesion Session ID
	 * @return TSala o null si no existe
	 */
	public TSala mostrar_sala_sesion(int idSesion);

	/** 
	 * CASO 5: Mostrar entrenador por sesin
	 * @param idSesion Session ID
	 * @return TEntrenador o null si no existe
	 */
	public TEntrenador mostrar_entrenador_sesion(int idSesion);
	
	/** 
	 * Alta sesión - Create a new session
	 * @param datos Session data
	 * @return Session ID if successful, 0 otherwise
	 */
	public int alta_sesion(TSesion datos);
	
	/** 
	 * Mostrar todas sesiones - Get all active sessions
	 * @return Set of all active sessions
	 */
	public Set<TSesion> mostrar_todas_sesiones();
}
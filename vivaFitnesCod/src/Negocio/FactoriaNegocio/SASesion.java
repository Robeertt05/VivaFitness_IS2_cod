/**
 * 
 */
package Negocio.FactoriaNegocio;

import Integracion.FactoriaIntegracion.TSesion;
import Integracion.FactoriaIntegracion.TSala;
import Negocio.entrenador.TEntrenador;
import java.util.Set;

/** 
 * Service Application interface for Sesion
 * @author azuri
 */
public interface SASesion {
	
	/** 
	 * Alta sesiÃÂ³n Ã¢ÂÂ crear nueva sesiÃÂ³n
	 * @param datos Session data
	 * @return ID de la sesiÃÂ³n creada, 0 si falla
	 */
	public int alta_sesion(TSesion datos);

	/** 
	 * CASO 1: Baja sesiÃÂ³n Ã¢ÂÂ eliminar sesiÃÂ³n (solo si no tiene clientes apuntados)
	 * @param idSesion Session ID
	 * @return 1 si OK, 0 si tiene clientes o no existe
	 */
	public int baja_sesion(int idSesion);

	/** 
	 * CASO 2: Modificar sesiÃÂ³n Ã¢ÂÂ actualizar atributos (excepto ID)
	 * @param idSesion Session ID
	 * @param datos Datos actualizados
	 * @return 1 si OK, 0 si falla
	 */
	public int modificar_sesion(int idSesion, TSesion datos);

	/** 
	 * CASO 3: Mostrar sesiÃÂ³n Ã¢ÂÂ obtener detalles de una sesiÃÂ³n
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
	 * CASO 4: Mostrar sala por sesiÃÂ³n
	 * @param idSesion Session ID
	 * @return TSala o null si no existe
	 */
	public TSala mostrar_sala_sesion(int idSesion);

	/** 
	 * CASO 5: Mostrar entrenador por sesiÃÂ³n
	 * @param idSesion Session ID
	 * @return TEntrenador o null si no existe
	 */
	public TEntrenador mostrar_entrenador_sesion(int idSesion);
}
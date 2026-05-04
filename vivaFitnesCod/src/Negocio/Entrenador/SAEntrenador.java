/**
 * Servicio de Aplicacion para Entrenador.
 * Patron: Fachada (Facade) - expone las operaciones de negocio al Controlador.
 */
package Negocio.Entrenador;

import java.util.Set;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface SAEntrenador {

	/**
	 * Da de alta a un nuevo entrenador en el sistema.
	 * @param datos Transfer Object con los datos del entrenador
	 * @return id asignado al entrenador, o -1 si falla
	 */
	public int alta_entrenador(TEntrenador datos);

	/**
	 * Da de baja logica a un entrenador (activo = 0).
	 * @param id identificador del entrenador a dar de baja
	 * @return 0 si correcto, -1 si falla
	 */
	public int baja_entrenador(int id);

	/**
	 * Modifica los datos de un entrenador existente.
	 * @param id    identificador del entrenador
	 * @param datos Transfer Object con los nuevos datos
	 * @return 0 si correcto, -1 si falla
	 */
	public int modificar_entrenador(int id, TEntrenador datos);

	/**
	 * Muestra los datos de un entrenador por su id.
	 * @param id identificador del entrenador
	 * @return Transfer Object con los datos, o null si no existe
	 */
	public TEntrenador mostrar_entrenador(int id);

	/**
	 * Devuelve todos los entrenadores activos del sistema.
	 * @return conjunto de Transfer Objects
	 */
	public Set<TEntrenador> mostrar_entrenadores();

	/**
	 * Crea una nueva sesion de entrenamiento para el entrenador indicado.
	 * @param idEntrenador identificador del entrenador que imparte la sesion
	 * @param datos        datos de la sesion (se pasa como TEntrenador de contexto)
	 * @return id de la sesion creada, o -1 si falla
	 */
	public int crear_sesion(int idEntrenador, TEntrenador datos);
}

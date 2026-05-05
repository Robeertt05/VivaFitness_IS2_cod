

package Integracion.FactoriaIntegracion;

import Integracion.Entrenador.DAOEntrenador;
import Integracion.Sala.DAOSala;
import Integracion.Cliente.DAOCliente;
import Integracion.Sesion.DAOSesion;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public abstract class FactoriaIntegracion {

	/** Unica instancia de la factoria (Singleton). */
	private static FactoriaIntegracion instance;

	/**
	 * Devuelve la unica instancia de la factoria concreta (Singleton).
	 * Si aun no existe, crea una instancia de FactoriaIntegracionImp.
	 * @return instancia unica de FactoriaIntegracion
	 */
	public static FactoriaIntegracion getInstance() {
		if (instance == null) {
			instance = new FactoriaIntegracionImp();
		}
		return instance;
	}

	/**
	 * Crea y devuelve una implementacion de DAOEntrenador.
	 * @return DAOEntrenador
	 */
	public abstract DAOEntrenador generaDAOEntrenador();

	/**
	 * Crea y devuelve una implementacion de DAOCliente.
	 * @return DAOCliente
	 */
	public abstract DAOCliente generaDAOCliente();

	/**
	 * Crea y devuelve una implementacion de DAOSala.
	 * @return DAOSala
	 */
	public abstract DAOSala generaDAOSala();

	/**
	 * Crea y devuelve una implementacion de DAOSesion.
	 * @return DAOSesion
	 */
	public abstract DAOSesion generaDAOSesion();
}

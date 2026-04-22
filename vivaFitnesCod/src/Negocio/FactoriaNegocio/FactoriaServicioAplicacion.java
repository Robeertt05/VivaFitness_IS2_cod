/**
 * Factoria abstracta de Servicios de Aplicacion.
 * Patron: Abstract Factory + Singleton.
 * Permite desacoplar la creacion de SA de su implementacion concreta.
 */
package Negocio.FactoriaNegocio;

import Negocio.entrenador.SAentrrenador;
import Negocio.Cliente.SACliente;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public abstract class FactoriaServicioAplicacion {

	/** Unica instancia de la factoria (Singleton). */
	private static FactoriaServicioAplicacion instance;

	/**
	 * Devuelve la unica instancia de la factoria concreta (Singleton).
	 * Si aun no existe, crea una instancia de FactoriaSAImp.
	 * @return instancia unica de FactoriaServicioAplicacion
	 */
	public static FactoriaServicioAplicacion getInstance() {
		// begin-user-code
		if (instance == null) {
			instance = new FactoriaSAImp();
		}
		return instance;
		// end-user-code
	}

	/**
	 * Crea y devuelve un SA de Entrenador.
	 * @return SAentrrenador
	 */
	public abstract SAentrrenador crearSAEntrenador();

	/**
	 * Crea y devuelve un SA de Cliente.
	 * @return SACliente
	 */
	public abstract SACliente crearSACliente();
}

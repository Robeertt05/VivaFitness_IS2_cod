/**
 * Implementacion concreta de la Factoria de Servicios de Aplicacion.
 * Patron: Factory Method - cada metodo crea la implementacion concreta del SA.
 */
package Negocio.FactoriaNegocio;

import Negocio.entrenador.SAEntrenador;
import Negocio.entrenador.SAEntrenadorImp;
import Negocio.Cliente.SACliente;
import Negocio.Cliente.SAClienteImp;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class FactoriaSAImp extends FactoriaServicioAplicacion {

	/**
	 * Crea y devuelve una nueva instancia de SAEntrenadorImp.
	 * @return SAentrrenador implementado por SAEntrenadorImp
	 */
	@Override
	public SAEntrenador crearSAEntrenador() {
		// begin-user-code
		return new SAEntrenadorImp();
		// end-user-code
	}

	/**
	 * Crea y devuelve una nueva instancia de SAClienteImp.
	 * @return SACliente implementado por SAClienteImp
	 */
	@Override
	public SACliente crearSACliente() {
		// begin-user-code
		return new SAClienteImp();
		// end-user-code
	}
}

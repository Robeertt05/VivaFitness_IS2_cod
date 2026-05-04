/**
 * Implementacion concreta de la Factoria de DAOs.
 * Patron: Factory Method - cada metodo instancia el DAO concreto.
 */
package Integracion.FactoriaIntegracion;

import Integracion.Entrenador.DAOEntrenador;
import Integracion.Entrenador.DAOEntrenadorImp;
import Integracion.Sala.DAOSala;
import Integracion.Sala.DAOSalaImp;
import Integracion.Cliente.DAOCliente;
import Integracion.Cliente.DAOClienteImp;
import Integracion.Sesion.DAOSesion;
import Integracion.Sesion.DAOSesionImp;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class FactoriaIntegracionImp extends FactoriaIntegracion {

	/**
	 * Crea y devuelve una nueva instancia de DAOEntrenadorImp.
	 * @return DAOEntrenador implementado por DAOEntrenadorImp
	 */
	@Override
	public DAOEntrenador generaDAOEntrenador() {
		// begin-user-code
		return new DAOEntrenadorImp();
		// end-user-code
	}

	/**
	 * Crea y devuelve una nueva instancia de DAOClienteImp.
	 * @return DAOCliente implementado por DAOClienteImp
	 */
	@Override
	public DAOCliente generaDAOCliente() {
		// begin-user-code
		return new DAOClienteImp();
		// end-user-code
	}

	@Override
	public DAOSala generaDAOSala() {
		return new DAOSalaImp();
	}

	@Override
	public DAOSesion generaDAOSesion() {
		return new DAOSesionImp();
	}
}

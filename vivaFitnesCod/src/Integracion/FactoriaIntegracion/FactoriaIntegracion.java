/**
 * 
 */
package Integracion.FactoriaIntegracion;

import Integracion.Entrenador.DAOEntrenador;

/** 
* Factory for Data Access Objects
* @author azuri
*/
public class FactoriaIntegracion {
	/** 
	* Singleton instance
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	private static FactoriaIntegracion instance;

	/** 
	* Get singleton instance
	* @return FactoriaIntegracion instance
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public static FactoriaIntegracion getInstance() {
		// begin-user-code
		if (instance == null) {
			instance = new FactoriaIntegracion();
		}
		return instance;
		// end-user-code
	}

	/** 
	* Generate Data Access Object for Trainer
	* @return DAOEntrenador implementation
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public DAOEntrenador generaDAOEntrenador() {
		// begin-user-code
		return new Integracion.Entrenador.DAOEntrenadorImp();
		// end-user-code
	}

	/** 
	* Generate Data Access Object for Room
	* @return DAOSala implementation
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public DAOSala generaDAOSala() {
		// begin-user-code
		return new DAOSalaImp();
		// end-user-code
	}

	/** 
	* Generate Data Access Object for Session
	* @return DAOSesion implementation
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public DAOSesion generaDAOSesion() {
		// begin-user-code
		return new DAOSesionImp();
		// end-user-code
	}

	/** 
	* Generate Data Access Object for Client
	* @return DAOCliente implementation
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public DAOCliente generaDAOCliente() {
		// begin-user-code
		return new DAOClienteImp();
		// end-user-code
	}
}
/**
 * 
 */
package Integracion.Cliente;

import java.util.Set;
import Integracion.FactoriaIntegracion.TSesion;
import Integracion.Sesion.TClienteSesion;

public interface DAOCliente {
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param datos
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public int create(TCliente datos);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param idCliente
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public TCliente read(int idCliente);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param tCliente
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public int update(TCliente tCliente);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param idCliente
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public int delete(int idCliente);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public Set<TCliente> read_all();

	/** 
	* Read a client by DNI
	* @param dni
	* @return TCliente or null
	*/
	public TCliente readByDni(String dni);

	public int apuntarSesion(Integracion.Sesion.TClienteSesion datos);

	public int desapuntarSesion(int idCliente, int idSesion);

	public Set<TSesion> readSesionesDisponibles();

	public Set<TSesion> readSesionesCliente(int idCliente);
}

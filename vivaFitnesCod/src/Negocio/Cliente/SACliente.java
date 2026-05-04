/**
 * 
 */
package Negocio.Cliente;

<<<<<<< Updated upstream
=======
import Integracion.Cliente.TCliente;
import java.util.Set;

>>>>>>> Stashed changes
/** 
 * Service Application interface for Client
 * @author azuri
 */
public interface SACliente {
	
	public int alta_cliente(TCliente datos);

	public int baja_cliente(int id);

	public int modificar_cliente(int id, TCliente datos);

	public TCliente mostrar_cliente(int id);

	public Set<TCliente> mostrar_todos_clientes();

	public int apuntarse_sesion(int idCliente, int idSesion);

	public void mostrar_sesiones(int idCliente);

	public int desapuntar_sesion(int idCliente, int idSesion);

	public TCliente read_by_dni(String dni);
}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param idSesion
	* @param hora
	* @param fecha
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public int apuntarse_sesion(int idSesion, int hora, String fecha);

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void mostrar_sesiones();

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param id
	* @param idSesion
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void desapuntar_sesion(int id, int idSesion);
}
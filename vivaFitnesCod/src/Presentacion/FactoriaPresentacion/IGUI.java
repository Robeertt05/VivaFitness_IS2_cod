/**
 * Interfaz grafica de usuario.
 * Patron: Observer - la vista se actualiza cuando recibe un Context del controlador.
 */
package Presentacion.FactoriaPresentacion;

import Controlador.Context;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public interface IGUI {

	/**
	 * Actualiza la vista con el resultado de la operacion.
	 * @param context contexto con el evento de respuesta y los datos
	 */
	public void update(Context context);
}

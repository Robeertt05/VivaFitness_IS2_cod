/**
 * Factoria abstracta de Vistas (IGUIs).
 * Patron: Abstract Factory + Singleton.
 */
package Presentacion.FactoriaPresentacion;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public abstract class FactoriaVistas {

	/** Unica instancia (Singleton). */
	private static FactoriaVistas instance;

	/**
	 * Devuelve la unica instancia de FactoriaVistas (Singleton).
	 * @return instancia de FactoriaVistas
	 */
	public static FactoriaVistas getInstance() {
		if (instance == null) {
			instance = new FactoriaVistasImp();
		}
		return instance;
	}

	/**
	 * Crea y devuelve la vista correspondiente al evento indicado.
	 * @param evento constante int de la clase Evento
	 * @return IGUI (vista) correspondiente
	 */
	public abstract IGUI generarVistas(int evento);
}

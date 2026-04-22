/**
 * Transfer Object para la entidad Entrenador.
 * Patron: Transfer Object (Value Object)
 */
package Negocio.entrenador;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class TEntrenador {

	/** Identificador unico del entrenador */
	private int id_entrenador;

	/** Estado activo/inactivo (1=activo, 0=inactivo) */
	private int activo;

	/** DNI del entrenador */
	private String dni_entrenador;

	/** Nombre completo del entrenador */
	private String nombre;

	/** Telefono de contacto del entrenador */
	private String telefono;

	// Constructores

	/** Constructor por defecto */
	public TEntrenador() {}

	/**
	 * Constructor completo.
	 * @param id       identificador
	 * @param dni      DNI del entrenador
	 * @param nombre   nombre completo
	 * @param telefono telefono de contacto
	 * @param activo   1 si activo, 0 si baja
	 */
	public TEntrenador(int id, String dni, String nombre, String telefono, int activo) {
		this.id_entrenador  = id;
		this.dni_entrenador = dni;
		this.nombre         = nombre;
		this.telefono       = telefono;
		this.activo         = activo;
	}

	// Getters

	/** @return id_entrenador */
	public int get_id() {
		return id_entrenador;
	}

	/** @return activo */
	public int get_activo() {
		return activo;
	}

	/** @return dni_entrenador */
	public String get_dni() {
		return dni_entrenador;
	}

	/** @return nombre */
	public String get_nombre() {
		return nombre;
	}

	/** @return telefono */
	public String get_telefono() {
		return telefono;
	}

	// Setters

	/** @param id nuevo identificador */
	public void set_id(int id) {
		this.id_entrenador = id;
	}

	/** @param activo 1=activo, 0=baja */
	public void set_activo(int activo) {
		this.activo = activo;
	}

	/** @param dni nuevo DNI */
	public void set_dni(String dni) {
		this.dni_entrenador = dni;
	}

	/** @param nombre nuevo nombre */
	public void set_nombre(String nombre) {
		this.nombre = nombre;
	}

	/** @param telefono nuevo telefono */
	public void set_telefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Establece todos los campos de una vez (excepto id y activo).
	 * @param dni      DNI del entrenador
	 * @param nombre   nombre completo
	 * @param telefono telefono de contacto
	 */
	public void set_all(String dni, String nombre, String telefono) {
		this.dni_entrenador = dni;
		this.nombre         = nombre;
		this.telefono       = telefono;
	}

	@Override
	public String toString() {
		return "TEntrenador [id=" + id_entrenador
				+ ", dni=" + dni_entrenador
				+ ", nombre=" + nombre
				+ ", telefono=" + telefono
				+ ", activo=" + activo + "]";
	}
}


package Negocio.entrenador;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class TEntrenador {

	protected int id_entrenador;
	protected int activo;
	protected String dni_entrenador;
	protected String nombre;
	protected String telefono;

	public TEntrenador() {}

	public TEntrenador(int id, String dni, String nombre, String telefono, int activo) {
		this.id_entrenador  = id;
		this.dni_entrenador = dni;
		this.nombre         = nombre;
		this.telefono       = telefono;
		this.activo         = activo;
	}

	// Getters 
	
	public int get_idEntrenador() {
		return id_entrenador;
	}

	public int get_activo() {
		return activo;
	}

	public String get_dniEntrenador() {
		return dni_entrenador;
	}

	public String get_nombreEntrenador() {
		return nombre;
	}

	public String get_telefonoEntrenador() {
		return telefono;
	}

	// Setters

	public void set_idEntrenador(int id) {
		this.id_entrenador = id;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public void set_dniEntrenador(String dni) {
		this.dni_entrenador = dni;
	}

	public void set_nombreEntrenador(String nombre) {
		this.nombre = nombre;
	}

	public void set_telefonoEntrenador(String telefono) {
		this.telefono = telefono;
	}

	
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

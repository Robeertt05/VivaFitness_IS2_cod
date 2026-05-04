package Integracion.Entrenador;

/** 
 * Transfer Object for Entrenador
 * @author azuri
 */
public class TEntrenador {

	private int id_entrenador;
	private int activo;
	private String dni_entrenador;
	private String nombre;
	private String telefono;

	// Constructor vacio
	public TEntrenador() {}

	// Constructor completo
	public TEntrenador(int id, String dni, String nombre, String telefono, int activo) {
		this.id_entrenador  = id;
		this.dni_entrenador = dni;
		this.nombre         = nombre;
		this.telefono       = telefono;
		this.activo         = activo;
	}

	// Getters

	public int get_id() {
		return id_entrenador;
	}

	public int get_activo() {
		return activo;
	}

	public String get_dni() {
		return dni_entrenador;
	}

	public String get_nombre() {
		return nombre;
	}

	public String get_telefono() {
		return telefono;
	}

	// Setters

	public void set_id(int id) {
		this.id_entrenador = id;
	}

	public void set_activo(int activo) {
		this.activo = activo;
	}

	public void set_dni(String dni) {
		this.dni_entrenador = dni;
	}

	public void set_nombre(String nombre) {
		this.nombre = nombre;
	}

	public void set_telefono(String telefono) {
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
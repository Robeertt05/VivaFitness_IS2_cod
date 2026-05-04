/**
 * Transfer Object para Cliente
 */
package Integracion.Cliente;

public class TCliente {
	private int idCliente;
	private String dni_cliente;
	private String nombreCliente;
	private Boolean activo;
	private String telefonoCliente;
	private String correoCliente;

	public TCliente() {
		this.activo = true;
	}

	public TCliente(int idCliente, String dni_cliente, String nombreCliente, String telefonoCliente, String correoCliente, Boolean activo) {
		this.idCliente = idCliente;
		this.dni_cliente = dni_cliente;
		this.nombreCliente = nombreCliente;
		this.telefonoCliente = telefonoCliente;
		this.correoCliente = correoCliente;
		this.activo = activo;
	}

	public TCliente(String dni_cliente, String nombreCliente, String telefonoCliente, String correoCliente) {
		this.dni_cliente = dni_cliente;
		this.nombreCliente = nombreCliente;
		this.telefonoCliente = telefonoCliente;
		this.correoCliente = correoCliente;
		this.activo = true;
	}

	public int getId() {
		return idCliente;
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setId(int idCliente) {
		this.idCliente = idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String get_dni() {
		return dni_cliente;
	}

	public void set_dni(String dni_cliente) {
		this.dni_cliente = dni_cliente;
	}

	public String get_nombre() {
		return nombreCliente;
	}

	public void set_nombre(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public Boolean get_activo() {
		return activo;
	}

	public void set_activo(Boolean activo) {
		this.activo = activo;
	}

	public String get_telefono() {
		return telefonoCliente;
	}

	public void set_telefono(String telefonoCliente) {
		this.telefonoCliente = telefonoCliente;
	}

	public String get_correo() {
		return correoCliente;
	}

	public void set_correo(String correoCliente) {
		this.correoCliente = correoCliente;
	}

	@Override
	public String toString() {
		return "Cliente [ID=" + idCliente + ", DNI=" + dni_cliente + ", Nombre=" + nombreCliente 
			+ ", Telefono=" + telefonoCliente + ", Correo=" + correoCliente + ", Activo=" + activo + "]";
	}
}

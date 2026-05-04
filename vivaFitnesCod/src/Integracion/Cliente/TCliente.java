/**
 * 
 */
package Integracion.Cliente;

/**
 * Transfer Object for Cliente
 * @author azuri
 */
public class TCliente {
	private int idCliente;
	private String dniCliente;
	private String nombreCliente;
	private int activo;
	private String telefonoCliente;
	private String correoCliente;

<<<<<<< Updated upstream
	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public int getId() {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente
		return 0;
		// end-user-code
	}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public String get_dni() {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente
		return null;
		// end-user-code
=======
	public TCliente() {
		this.activo = 1;
	}

	public TCliente(int idCliente, String dniCliente, String nombreCliente, int activo, String telefonoCliente, String correoCliente) {
		this.idCliente = idCliente;
		this.dniCliente = dniCliente;
		this.nombreCliente = nombreCliente;
		this.activo = activo;
		this.telefonoCliente = telefonoCliente;
		this.correoCliente = correoCliente;
>>>>>>> Stashed changes
	}

	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public String getDniCliente() {
		return dniCliente;
	}

	public void setDniCliente(String dniCliente) {
		this.dniCliente = dniCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	public String getTelefonoCliente() {
		return telefonoCliente;
	}

	public void setTelefonoCliente(String telefonoCliente) {
		this.telefonoCliente = telefonoCliente;
	}

	public String getCorreoCliente() {
		return correoCliente;
	}

	public void setCorreoCliente(String correoCliente) {
		this.correoCliente = correoCliente;
	}

	// Backward compatibility methods
	public String get_nombre() {
<<<<<<< Updated upstream
		// begin-user-code
		// TODO Apéndice de método generado automáticamente
		return null;
		// end-user-code
	}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public Boolean get_activo() {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente
		return null;
		// end-user-code
	}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public String get_telefono() {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente
		return null;
		// end-user-code
	}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @return
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public String get_correo() {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente
		return null;
		// end-user-code
	}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param dni
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void set_dni(String dni) {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente

		// end-user-code
	}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param nombre
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void set_nombre(String nombre) {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente

		// end-user-code
	}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param activo
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void set_activo(Boolean activo) {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente

		// end-user-code
=======
		return nombreCliente;
	}

	public void set_nombre(String nombre) {
		this.nombreCliente = nombre;
	}

	public String get_dni() {
		return dniCliente;
	}

	public void set_dni(String dni) {
		this.dniCliente = dni;
	}

	public int getId() {
		return idCliente;
	}

	public void set_id(int id) {
		this.idCliente = id;
	}

	public Boolean get_activo() {
		return activo == 1;
	}

	public void set_activo(Boolean act) {
		this.activo = act ? 1 : 0;
	}

	public String get_telefono() {
		return telefonoCliente;
>>>>>>> Stashed changes
	}

	public void set_telefono(String telefono) {
<<<<<<< Updated upstream
		// begin-user-code
		// TODO Apéndice de método generado automáticamente

		// end-user-code
	}

	/** 
	* <!-- begin-UML-doc -->
	* <!-- end-UML-doc -->
	* @param correo
	* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
	*/
	public void set_correo(String correo) {
		// begin-user-code
		// TODO Apéndice de método generado automáticamente
=======
		this.telefonoCliente = telefono;
	}

	public String get_correo() {
		return correoCliente;
	}
>>>>>>> Stashed changes

	public void set_correo(String correo) {
		this.correoCliente = correo;
	}

	@Override
	public String toString() {
		return "TCliente{" +
				"idCliente=" + idCliente +
				", dniCliente='" + dniCliente + '\'' +
				", nombreCliente='" + nombreCliente + '\'' +
				", activo=" + activo +
				", telefonoCliente='" + telefonoCliente + '\'' +
				", correoCliente='" + correoCliente + '\'' +
				'}';
	}
}
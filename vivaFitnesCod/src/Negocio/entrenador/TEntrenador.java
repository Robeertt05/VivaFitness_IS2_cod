/**
 * 
 */
package Negocio.entrenador;

/** 
 * Transfer Object for Entrenador
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class TEntrenador {

private int id_entrenador;
private int activo;
private String dni_entrenador;
private String nombre;
private String telefono;

public int get_id() {
return id_entrenador;
}

public void set_id(int id) {
this.id_entrenador = id;
}

public int get_activo() {
return activo;
}

public void set_activo(int activo) {
this.activo = activo;
}

public String get_dni() {
return dni_entrenador;
}

public void set_dni(Object dni) {
this.dni_entrenador = dni != null ? dni.toString() : null;
}

public String get_nombre() {
return nombre;
}

public void set_nombre(Object nombre) {
this.nombre = nombre != null ? nombre.toString() : null;
}

public String get_telefono() {
return telefono;
}

public void set_telefono(Object telefono) {
this.telefono = telefono != null ? telefono.toString() : null;
}

public void set_all(Object dni, Object nombre, Object telefono) {
set_dni(dni);
set_nombre(nombre);
set_telefono(telefono);
}

@Override
public String toString() {
return "TEntrenador [id=" + id_entrenador + ", nombre=" + nombre
+ ", dni=" + dni_entrenador + ", telefono=" + telefono
+ ", activo=" + activo + "]";
}
}

package Integracion.Cliente;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Pruebas del Transfer Object TCliente")
class TClienteTest {



	@Test
	@DisplayName("Constructor vacío: activo debe ser 1 por defecto")
	void constructorVacio_activoPorDefecto() {
		TCliente cliente = new TCliente();
		assertEquals(1, cliente.get_activo(),
			"El constructor vacío debe inicializar activo a 1");
	}

	@Test
	@DisplayName("Constructor vacío: id debe ser 0 (valor por defecto de int)")
	void constructorVacio_idPorDefecto() {
		TCliente cliente = new TCliente();
		assertEquals(0, cliente.getId(),
			"El id debe ser 0 por defecto (no asignado)");
	}

	@Test
	@DisplayName("Constructor vacío: campos String deben ser null")
	void constructorVacio_camposNulos() {
		TCliente cliente = new TCliente();
		assertNull(cliente.get_dni(), "DNI debe ser null en constructor vacío");
		assertNull(cliente.get_nombre(), "Nombre debe ser null en constructor vacío");
		assertNull(cliente.get_telefono(), "Teléfono debe ser null en constructor vacío");
		assertNull(cliente.get_correo(), "Correo debe ser null en constructor vacío");
	}

	@Test
	@DisplayName("Constructor completo: todos los campos asignados correctamente")
	void constructorCompleto_todosLosCampos() {
		TCliente cliente = new TCliente(1, "12345678A", "Juan García", "600111222", "juan@mail.com", 1);

		assertEquals(1, cliente.getId());
		assertEquals("12345678A", cliente.get_dni());
		assertEquals("Juan García", cliente.get_nombre());
		assertEquals("600111222", cliente.get_telefono());
		assertEquals("juan@mail.com", cliente.get_correo());
		assertEquals(1, cliente.get_activo());
	}

	@Test
	@DisplayName("Constructor completo con activo=0: se respeta el valor")
	void constructorCompleto_activoInactivo() {
		TCliente cliente = new TCliente(2, "87654321B", "Ana López", "600333444", "ana@mail.com", 0);
		assertEquals(0, cliente.get_activo(),
			"El constructor completo debe respetar activo=0");
	}

	@Test
	@DisplayName("Constructor parcial (sin id): activo=1 y campos asignados")
	void constructorParcial_sinId() {
		TCliente cliente = new TCliente("11111111C", "Pedro Ruiz", "600555666", "pedro@mail.com");

		assertEquals(0, cliente.getId(), "Id debe ser 0 (no asignado)");
		assertEquals("11111111C", cliente.get_dni());
		assertEquals("Pedro Ruiz", cliente.get_nombre());
		assertEquals("600555666", cliente.get_telefono());
		assertEquals("pedro@mail.com", cliente.get_correo());
		assertEquals(1, cliente.get_activo(),
			"Constructor parcial debe inicializar activo a 1");
	}



	@Test
	@DisplayName("setId y getId: asignar y recuperar id")
	void setId_getId() {
		TCliente cliente = new TCliente();
		cliente.setId(42);
		assertEquals(42, cliente.getId());
	}

	@Test
	@DisplayName("setIdCliente y getIdCliente: alias de setId/getId")
	void setIdCliente_getIdCliente() {
		TCliente cliente = new TCliente();
		cliente.setIdCliente(99);
		assertEquals(99, cliente.getIdCliente());

		assertEquals(cliente.getId(), cliente.getIdCliente());
	}

	@Test
	@DisplayName("set_dni y get_dni")
	void setDni_getDni() {
		TCliente cliente = new TCliente();
		cliente.set_dni("99999999Z");
		assertEquals("99999999Z", cliente.get_dni());
	}

	@Test
	@DisplayName("set_nombre y get_nombre")
	void setNombre_getNombre() {
		TCliente cliente = new TCliente();
		cliente.set_nombre("María Fernández");
		assertEquals("María Fernández", cliente.get_nombre());
	}

	@Test
	@DisplayName("set_telefono y get_telefono")
	void setTelefono_getTelefono() {
		TCliente cliente = new TCliente();
		cliente.set_telefono("+34 611 222 333");
		assertEquals("+34 611 222 333", cliente.get_telefono());
	}

	@Test
	@DisplayName("set_correo y get_correo")
	void setCorreo_getCorreo() {
		TCliente cliente = new TCliente();
		cliente.set_correo("test@ejemplo.com");
		assertEquals("test@ejemplo.com", cliente.get_correo());
	}

	@Test
	@DisplayName("set_activo y get_activo: cambiar de 1 a 0")
	void setActivo_getActivo() {
		TCliente cliente = new TCliente();
		assertEquals(1, cliente.get_activo());
		cliente.set_activo(0);
		assertEquals(0, cliente.get_activo());
	}



	@Test
	@DisplayName("Encapsulación: modificar un campo no afecta a otros")
	void encapsulacion_independenciaDeCampos() {
		TCliente cliente = new TCliente("12345678A", "Juan", "600000000", "juan@mail.com");

		cliente.set_nombre("Nuevo Nombre");


		assertEquals("12345678A", cliente.get_dni());
		assertEquals("600000000", cliente.get_telefono());
		assertEquals("juan@mail.com", cliente.get_correo());
		assertEquals(1, cliente.get_activo());
	}

	@Test
	@DisplayName("Encapsulación: setId no afecta otros atributos")
	void encapsulacion_setIdNoAfectaOtros() {
		TCliente cliente = new TCliente(1, "DNI1", "Nombre1", "Tel1", "correo1@x.com", 1);
		cliente.setId(100);

		assertEquals(100, cliente.getId());
		assertEquals("DNI1", cliente.get_dni());
		assertEquals("Nombre1", cliente.get_nombre());
	}



	@Test
	@DisplayName("toString contiene todos los campos del cliente")
	void toString_contieneTodosLosCampos() {
		TCliente cliente = new TCliente(5, "44444444D", "Luis Martín", "622333444", "luis@mail.com", 1);
		String result = cliente.toString();

		assertTrue(result.contains("5"), "toString debe contener el ID");
		assertTrue(result.contains("44444444D"), "toString debe contener el DNI");
		assertTrue(result.contains("Luis Martín"), "toString debe contener el nombre");
		assertTrue(result.contains("622333444"), "toString debe contener el teléfono");
		assertTrue(result.contains("luis@mail.com"), "toString debe contener el correo");
	}



	@Test
	@DisplayName("Valores nulos: se permite asignar null a campos String")
	void valoresNulos_sePermiten() {
		TCliente cliente = new TCliente();
		cliente.set_dni(null);
		cliente.set_nombre(null);
		cliente.set_telefono(null);
		cliente.set_correo(null);

		assertNull(cliente.get_dni());
		assertNull(cliente.get_nombre());
		assertNull(cliente.get_telefono());
		assertNull(cliente.get_correo());
	}

	@Test
	@DisplayName("String vacío: se permite asignar cadena vacía")
	void stringVacio_sePermite() {
		TCliente cliente = new TCliente();
		cliente.set_dni("");
		cliente.set_nombre("");
		assertEquals("", cliente.get_dni());
		assertEquals("", cliente.get_nombre());
	}

	@Test
	@DisplayName("Id negativo: se permite asignar (no hay validación en TO)")
	void idNegativo_sePermite() {
		TCliente cliente = new TCliente();
		cliente.setId(-1);
		assertEquals(-1, cliente.getId());
	}
}

package Integracion.Entrenador;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


@DisplayName("Pruebas del Transfer Object TEntrenador")
class TEntrenadorTest {



	@Test
	@DisplayName("Constructor vacío: todos los campos en valores por defecto")
	void constructorVacio_valoresPorDefecto() {
		TEntrenador entrenador = new TEntrenador();

		assertEquals(0, entrenador.get_id(), "Id debe ser 0 por defecto");
		assertEquals(0, entrenador.get_activo(), "Activo debe ser 0 por defecto en constructor vacío");
		assertNull(entrenador.get_dni(), "DNI debe ser null");
		assertNull(entrenador.get_nombre(), "Nombre debe ser null");
		assertNull(entrenador.get_telefono(), "Teléfono debe ser null");
	}

	@Test
	@DisplayName("Constructor completo: todos los campos asignados correctamente")
	void constructorCompleto_todosLosCampos() {
		TEntrenador entrenador = new TEntrenador(1, "12345678A", "Carlos López", "611222333", 1);

		assertEquals(1, entrenador.get_id());
		assertEquals("12345678A", entrenador.get_dni());
		assertEquals("Carlos López", entrenador.get_nombre());
		assertEquals("611222333", entrenador.get_telefono());
		assertEquals(1, entrenador.get_activo());
	}

	@Test
	@DisplayName("Constructor completo con activo=0")
	void constructorCompleto_inactivo() {
		TEntrenador entrenador = new TEntrenador(2, "87654321B", "Laura Sánchez", "622333444", 0);
		assertEquals(0, entrenador.get_activo());
	}



	@Test
	@DisplayName("set_id y get_id")
	void setId_getId() {
		TEntrenador entrenador = new TEntrenador();
		entrenador.set_id(15);
		assertEquals(15, entrenador.get_id());
	}

	@Test
	@DisplayName("set_dni y get_dni")
	void setDni_getDni() {
		TEntrenador entrenador = new TEntrenador();
		entrenador.set_dni("55555555E");
		assertEquals("55555555E", entrenador.get_dni());
	}

	@Test
	@DisplayName("set_nombre y get_nombre")
	void setNombre_getNombre() {
		TEntrenador entrenador = new TEntrenador();
		entrenador.set_nombre("Miguel Torres");
		assertEquals("Miguel Torres", entrenador.get_nombre());
	}

	@Test
	@DisplayName("set_telefono y get_telefono")
	void setTelefono_getTelefono() {
		TEntrenador entrenador = new TEntrenador();
		entrenador.set_telefono("699888777");
		assertEquals("699888777", entrenador.get_telefono());
	}

	@Test
	@DisplayName("set_activo y get_activo")
	void setActivo_getActivo() {
		TEntrenador entrenador = new TEntrenador();
		entrenador.set_activo(1);
		assertEquals(1, entrenador.get_activo());
		entrenador.set_activo(0);
		assertEquals(0, entrenador.get_activo());
	}



	@Test
	@DisplayName("set_all: asigna DNI, nombre y teléfono de una vez")
	void setAll_asignaTresCampos() {
		TEntrenador entrenador = new TEntrenador();
		entrenador.set_all("99999999Z", "Entrenador Test", "666000111");

		assertEquals("99999999Z", entrenador.get_dni());
		assertEquals("Entrenador Test", entrenador.get_nombre());
		assertEquals("666000111", entrenador.get_telefono());
	}

	@Test
	@DisplayName("set_all: no modifica id ni activo")
	void setAll_noModificaIdNiActivo() {
		TEntrenador entrenador = new TEntrenador(10, "00000000A", "Original", "111", 1);
		entrenador.set_all("11111111B", "Modificado", "222");

		assertEquals(10, entrenador.get_id(), "set_all no debe modificar id");
		assertEquals(1, entrenador.get_activo(), "set_all no debe modificar activo");
	}

	@Test
	@DisplayName("set_all: sobreescribe valores anteriores")
	void setAll_sobreescribeValores() {
		TEntrenador entrenador = new TEntrenador(1, "AAAA", "Viejo", "000", 1);
		entrenador.set_all("BBBB", "Nuevo", "999");

		assertEquals("BBBB", entrenador.get_dni());
		assertEquals("Nuevo", entrenador.get_nombre());
		assertEquals("999", entrenador.get_telefono());
	}



	@Test
	@DisplayName("Encapsulación: modificar nombre no afecta DNI ni teléfono")
	void encapsulacion_independenciaCampos() {
		TEntrenador entrenador = new TEntrenador(1, "12345678A", "Original", "600000000", 1);
		entrenador.set_nombre("Modificado");

		assertEquals("12345678A", entrenador.get_dni());
		assertEquals("600000000", entrenador.get_telefono());
		assertEquals(1, entrenador.get_activo());
	}



	@Test
	@DisplayName("toString contiene todos los campos")
	void toString_contieneTodosLosCampos() {
		TEntrenador entrenador = new TEntrenador(3, "33333333C", "Sofía Pérez", "633444555", 1);
		String result = entrenador.toString();

		assertTrue(result.contains("3"), "toString debe contener el id");
		assertTrue(result.contains("33333333C"), "toString debe contener el DNI");
		assertTrue(result.contains("Sofía Pérez"), "toString debe contener el nombre");
		assertTrue(result.contains("633444555"), "toString debe contener el teléfono");
		assertTrue(result.contains("1"), "toString debe contener activo");
	}



	@Test
	@DisplayName("Valores nulos: se permite null en campos String")
	void valoresNulos() {
		TEntrenador entrenador = new TEntrenador();
		entrenador.set_dni(null);
		entrenador.set_nombre(null);
		entrenador.set_telefono(null);

		assertNull(entrenador.get_dni());
		assertNull(entrenador.get_nombre());
		assertNull(entrenador.get_telefono());
	}

	@Test
	@DisplayName("String vacío: se permite cadena vacía")
	void stringVacio() {
		TEntrenador entrenador = new TEntrenador();
		entrenador.set_nombre("");
		assertEquals("", entrenador.get_nombre());
	}

	@Test
	@DisplayName("Id negativo: el TO no valida, se almacena")
	void idNegativo() {
		TEntrenador entrenador = new TEntrenador();
		entrenador.set_id(-5);
		assertEquals(-5, entrenador.get_id());
	}
}

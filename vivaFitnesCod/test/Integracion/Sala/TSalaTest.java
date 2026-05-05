package Integracion.Sala;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas del Transfer Object TSala")
class TSalaTest {

	@Test
	@DisplayName("Constructor vacío: activo=1 por defecto")
	void constructorVacio_valoresPorDefecto() {
		TSala sala = new TSala();
		assertEquals(1, sala.getActivo());
		assertEquals(0, sala.getIdSala());
		assertNull(sala.getNombreSala());
		assertEquals(0, sala.getAforo());
	}

	@Test
	@DisplayName("Constructor con parámetros: campos asignados, activo=1")
	void constructorConParametros() {
		TSala sala = new TSala(1, "Sala Spinning", 25);
		assertEquals(1, sala.getIdSala());
		assertEquals("Sala Spinning", sala.getNombreSala());
		assertEquals(25, sala.getAforo());
		assertEquals(1, sala.getActivo());
	}

	@Test
	@DisplayName("Getters y Setters de todos los campos")
	void gettersSetters() {
		TSala sala = new TSala();
		sala.setIdSala(10);
		sala.setNombreSala("Sala Yoga");
		sala.setAforo(50);
		sala.setActivo(0);

		assertEquals(10, sala.getIdSala());
		assertEquals("Sala Yoga", sala.getNombreSala());
		assertEquals(50, sala.getAforo());
		assertEquals(0, sala.getActivo());
	}

	@Test
	@DisplayName("Encapsulación: modificar nombre no afecta aforo ni activo")
	void encapsulacion_independenciaCampos() {
		TSala sala = new TSala(1, "Original", 30);
		sala.setNombreSala("Modificada");
		assertEquals(30, sala.getAforo());
		assertEquals(1, sala.getActivo());
		assertEquals(1, sala.getIdSala());
	}

	@Test
	@DisplayName("toString contiene todos los campos")
	void toString_contieneCampos() {
		TSala sala = new TSala(5, "Sala Pesas", 40);
		String r = sala.toString();
		assertTrue(r.contains("5"));
		assertTrue(r.contains("Sala Pesas"));
		assertTrue(r.contains("40"));
	}

	@Test
	@DisplayName("Valores límite: aforo 0, negativo, MAX_VALUE")
	void valoresLimiteAforo() {
		TSala sala = new TSala();
		sala.setAforo(0);
		assertEquals(0, sala.getAforo());
		sala.setAforo(-10);
		assertEquals(-10, sala.getAforo());
		sala.setAforo(Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, sala.getAforo());
	}

	@Test
	@DisplayName("Nombre null y vacío se permiten en TO")
	void nombreNullYVacio() {
		TSala sala = new TSala();
		sala.setNombreSala(null);
		assertNull(sala.getNombreSala());
		sala.setNombreSala("");
		assertEquals("", sala.getNombreSala());
	}
}

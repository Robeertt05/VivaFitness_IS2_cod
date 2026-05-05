package Integracion.Sesion;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Pruebas del Transfer Object TSesion")
class TSesionTest {

	@Test
	@DisplayName("Constructor vacío: activo=1 por defecto")
	void constructorVacio() {
		TSesion s = new TSesion();
		assertEquals(1, s.getActivo());
		assertEquals(0, s.getIdSesion());
		assertNull(s.getObjetivo());
		assertEquals(0, s.getDuracion());
		assertNull(s.getFechaHora());
		assertEquals(0, s.getIdSala());
		assertEquals(0, s.getIdEntrenador());
	}

	@Test
	@DisplayName("Constructor completo: todos los campos asignados")
	void constructorCompleto() {
		TSesion s = new TSesion(1, "Cardio", 60, "2026-06-01 10:00", 2, 3);
		assertEquals(1, s.getIdSesion());
		assertEquals("Cardio", s.getObjetivo());
		assertEquals(60, s.getDuracion());
		assertEquals("2026-06-01 10:00", s.getFechaHora());
		assertEquals(2, s.getIdSala());
		assertEquals(3, s.getIdEntrenador());
		assertEquals(1, s.getActivo());
	}

	@Test
	@DisplayName("Getters y Setters de todos los campos")
	void gettersSetters() {
		TSesion s = new TSesion();
		s.setIdSesion(5);
		s.setObjetivo("Fuerza");
		s.setDuracion(45);
		s.setFechaHora("2026-07-15 18:00");
		s.setIdSala(10);
		s.setIdEntrenador(20);
		s.setActivo(0);

		assertEquals(5, s.getIdSesion());
		assertEquals("Fuerza", s.getObjetivo());
		assertEquals(45, s.getDuracion());
		assertEquals("2026-07-15 18:00", s.getFechaHora());
		assertEquals(10, s.getIdSala());
		assertEquals(20, s.getIdEntrenador());
		assertEquals(0, s.getActivo());
	}

	@Test
	@DisplayName("Relación FK: idSala e idEntrenador representan las FK")
	void fksSonConsistentes() {
		TSesion s = new TSesion(1, "Yoga", 90, "2026-08-01 09:00", 5, 7);
		assertEquals(5, s.getIdSala(), "FK idSala debe apuntar a la sala correcta");
		assertEquals(7, s.getIdEntrenador(), "FK idEntrenador debe apuntar al entrenador correcto");
	}

	@Test
	@DisplayName("Encapsulación: modificar objetivo no afecta FKs")
	void encapsulacion() {
		TSesion s = new TSesion(1, "Original", 60, "2026-01-01 10:00", 3, 4);
		s.setObjetivo("Modificado");
		assertEquals(3, s.getIdSala());
		assertEquals(4, s.getIdEntrenador());
		assertEquals(60, s.getDuracion());
	}

	@Test
	@DisplayName("toString contiene campos relevantes")
	void toStringTest() {
		TSesion s = new TSesion(2, "Pilates", 50, "2026-09-01 11:00", 6, 8);
		String r = s.toString();
		assertTrue(r.contains("Pilates"));
		assertTrue(r.contains("50"));
		assertTrue(r.contains("6"));
		assertTrue(r.contains("8"));
	}

	@Test
	@DisplayName("Valores límite: duración 0, negativa")
	void valoresLimite() {
		TSesion s = new TSesion();
		s.setDuracion(0);
		assertEquals(0, s.getDuracion());
		s.setDuracion(-1);
		assertEquals(-1, s.getDuracion());
	}

	@Test
	@DisplayName("Valores nulos en campos String")
	void valoresNulos() {
		TSesion s = new TSesion();
		s.setObjetivo(null);
		s.setFechaHora(null);
		assertNull(s.getObjetivo());
		assertNull(s.getFechaHora());
	}
}

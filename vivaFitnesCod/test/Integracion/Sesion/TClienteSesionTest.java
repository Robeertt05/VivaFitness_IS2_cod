package Integracion.Sesion;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Date;

@DisplayName("Pruebas del Transfer Object TClienteSesion (Relación M:N)")
class TClienteSesionTest {

	@Test
	@DisplayName("Constructor vacío: todos los campos en valores por defecto")
	void constructorVacio() {
		TClienteSesion cs = new TClienteSesion();
		assertEquals(0, cs.getIdCliente());
		assertEquals(0, cs.getIdSesion());
		assertNull(cs.getFecha());
		assertNull(cs.getHora());
	}

	@Test
	@DisplayName("Constructor completo: FKs y datos de inscripción correctos")
	void constructorCompleto() {
		Date fecha = new Date();
		TClienteSesion cs = new TClienteSesion(1, 5, fecha, "10:30");
		assertEquals(1, cs.getIdCliente());
		assertEquals(5, cs.getIdSesion());
		assertEquals(fecha, cs.getFecha());
		assertEquals("10:30", cs.getHora());
	}

	@Test
	@DisplayName("Getters y Setters de las FKs")
	void gettersSettersFKs() {
		TClienteSesion cs = new TClienteSesion();
		cs.setIdCliente(10);
		cs.setIdSesion(20);
		assertEquals(10, cs.getIdCliente());
		assertEquals(20, cs.getIdSesion());
	}

	@Test
	@DisplayName("Getters y Setters de fecha y hora")
	void gettersSettersFechaHora() {
		TClienteSesion cs = new TClienteSesion();
		Date fecha = new Date();
		cs.setFecha(fecha);
		cs.setHora("15:00");
		assertEquals(fecha, cs.getFecha());
		assertEquals("15:00", cs.getHora());
	}

	@Test
	@DisplayName("Relación M:N: FK idCliente e idSesion son coherentes")
	void relacionMN_fksCoherentes() {
		int idCliente = 3;
		int idSesion = 7;
		TClienteSesion cs = new TClienteSesion(idCliente, idSesion, new Date(), "09:00");
		assertEquals(idCliente, cs.getIdCliente(), "FK idCliente debe ser coherente");
		assertEquals(idSesion, cs.getIdSesion(), "FK idSesion debe ser coherente");
	}

	@Test
	@DisplayName("Encapsulación: modificar idCliente no afecta idSesion")
	void encapsulacion() {
		TClienteSesion cs = new TClienteSesion(1, 2, new Date(), "08:00");
		cs.setIdCliente(99);
		assertEquals(99, cs.getIdCliente());
		assertEquals(2, cs.getIdSesion(), "idSesion no debe cambiar");
	}

	@Test
	@DisplayName("toString contiene las FKs")
	void toStringTest() {
		TClienteSesion cs = new TClienteSesion(4, 8, new Date(), "12:00");
		String r = cs.toString();
		assertTrue(r.contains("4"));
		assertTrue(r.contains("8"));
		assertTrue(r.contains("12:00"));
	}

	@Test
	@DisplayName("Valores nulos en fecha y hora se permiten")
	void valoresNulos() {
		TClienteSesion cs = new TClienteSesion();
		cs.setFecha(null);
		cs.setHora(null);
		assertNull(cs.getFecha());
		assertNull(cs.getHora());
	}
}

package Negocio.Relaciones;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Integracion.Cliente.TCliente;
import Integracion.Entrenador.TEntrenador;
import Integracion.Sala.TSala;
import Integracion.Sesion.TClienteSesion;
import Integracion.Sesion.TSesion;
import java.util.*;


@DisplayName("Pruebas de Relaciones entre Entidades")
class RelacionesEntidadesTest {



	@Test
	@DisplayName("1:N Sala-Sesion: varias sesiones referencian la misma sala")
	void relacion1N_salaSesion_variasSesionesUnaSala() {
		TSala sala = new TSala(1, "Sala Principal", 30);

		TSesion s1 = new TSesion(1, "Cardio", 60, "2026-06-01 10:00", sala.getIdSala(), 1);
		TSesion s2 = new TSesion(2, "Yoga", 45, "2026-06-01 11:00", sala.getIdSala(), 1);
		TSesion s3 = new TSesion(3, "Pilates", 50, "2026-06-01 12:00", sala.getIdSala(), 2);

		List<TSesion> sesionesDeSala = Arrays.asList(s1, s2, s3);

		for (TSesion s : sesionesDeSala) {
			assertEquals(sala.getIdSala(), s.getIdSala(),
				"Todas las sesiones deben referenciar a la misma sala");
		}
		assertEquals(3, sesionesDeSala.size(), "Debe haber 3 sesiones en la sala");
	}

	@Test
	@DisplayName("1:N Sala-Sesion: una sala sin sesiones es válida")
	void relacion1N_salaSinSesiones() {
		TSala sala = new TSala(5, "Sala Vacía", 10);
		List<TSesion> sesiones = new ArrayList<>();

		assertTrue(sesiones.isEmpty(), "Una sala sin sesiones debe tener lista vacía");
		assertEquals(5, sala.getIdSala());
	}

	@Test
	@DisplayName("1:N Sala-Sesion: sesiones en salas distintas")
	void relacion1N_sesionesEnSalasDistintas() {
		TSala sala1 = new TSala(1, "Sala A", 20);
		TSala sala2 = new TSala(2, "Sala B", 30);

		TSesion s1 = new TSesion(1, "Cardio", 60, "2026-06-01 10:00", sala1.getIdSala(), 1);
		TSesion s2 = new TSesion(2, "Yoga", 45, "2026-06-01 10:00", sala2.getIdSala(), 1);

		assertNotEquals(s1.getIdSala(), s2.getIdSala(),
			"Sesiones en salas distintas deben tener idSala diferente");
	}



	@Test
	@DisplayName("1:N Entrenador-Sesion: varias sesiones del mismo entrenador")
	void relacion1N_entrenadorSesion() {
		TEntrenador entrenador = new TEntrenador(1, "12345678A", "Carlos", "600111222", 1);

		TSesion s1 = new TSesion(1, "Fuerza", 60, "2026-06-01 10:00", 1, entrenador.get_id());
		TSesion s2 = new TSesion(2, "Cardio", 45, "2026-06-02 10:00", 1, entrenador.get_id());

		assertEquals(entrenador.get_id(), s1.getIdEntrenador());
		assertEquals(entrenador.get_id(), s2.getIdEntrenador());
	}

	@Test
	@DisplayName("1:N Entrenador-Sesion: entrenador sin sesiones es válido")
	void relacion1N_entrenadorSinSesiones() {
		TEntrenador entrenador = new TEntrenador(10, "99999999Z", "Nuevo", "666000000", 1);
		List<TSesion> sesiones = new ArrayList<>();
		assertTrue(sesiones.isEmpty());
	}

	@Test
	@DisplayName("1:N Entrenador-Sesion: dos entrenadores con sesiones distintas")
	void relacion1N_dosEntrenadoresSesionesDistintas() {
		TEntrenador e1 = new TEntrenador(1, "AAA", "Ent1", "111", 1);
		TEntrenador e2 = new TEntrenador(2, "BBB", "Ent2", "222", 1);

		TSesion s1 = new TSesion(1, "Sess1", 60, "2026-06-01 10:00", 1, e1.get_id());
		TSesion s2 = new TSesion(2, "Sess2", 45, "2026-06-01 11:00", 1, e2.get_id());

		assertNotEquals(s1.getIdEntrenador(), s2.getIdEntrenador());
	}



	@Test
	@DisplayName("M:N Cliente-Sesion: un cliente se apunta a varias sesiones")
	void relacionMN_clienteVariasSesiones() {
		TCliente cliente = new TCliente(1, "11111111A", "Juan", "600000000", "juan@x.com", 1);
		TSesion s1 = new TSesion(1, "Cardio", 60, "2026-06-01 10:00", 1, 1);
		TSesion s2 = new TSesion(2, "Yoga", 45, "2026-06-02 10:00", 2, 1);

		Date fecha = new Date();
		TClienteSesion cs1 = new TClienteSesion(cliente.getId(), s1.getIdSesion(), fecha, "10:00");
		TClienteSesion cs2 = new TClienteSesion(cliente.getId(), s2.getIdSesion(), fecha, "10:00");

		assertEquals(cliente.getId(), cs1.getIdCliente());
		assertEquals(cliente.getId(), cs2.getIdCliente());
		assertEquals(s1.getIdSesion(), cs1.getIdSesion());
		assertEquals(s2.getIdSesion(), cs2.getIdSesion());
	}

	@Test
	@DisplayName("M:N Cliente-Sesion: varios clientes en una misma sesión")
	void relacionMN_variosClientesUnaSesion() {
		TSesion sesion = new TSesion(1, "Spinning", 45, "2026-06-01 10:00", 1, 1);
		TCliente c1 = new TCliente(1, "AAA", "Cliente1", "111", "c1@x.com", 1);
		TCliente c2 = new TCliente(2, "BBB", "Cliente2", "222", "c2@x.com", 1);
		TCliente c3 = new TCliente(3, "CCC", "Cliente3", "333", "c3@x.com", 1);

		Date fecha = new Date();
		List<TClienteSesion> inscripciones = Arrays.asList(
			new TClienteSesion(c1.getId(), sesion.getIdSesion(), fecha, "09:00"),
			new TClienteSesion(c2.getId(), sesion.getIdSesion(), fecha, "09:05"),
			new TClienteSesion(c3.getId(), sesion.getIdSesion(), fecha, "09:10")
		);

		assertEquals(3, inscripciones.size());
		for (TClienteSesion cs : inscripciones) {
			assertEquals(sesion.getIdSesion(), cs.getIdSesion(),
				"Todas las inscripciones deben referenciar la misma sesión");
		}
	}

	@Test
	@DisplayName("M:N: la inscripción enlaza correctamente cliente y sesión")
	void relacionMN_coherenciaFKs() {
		int idCli = 7;
		int idSes = 14;
		TClienteSesion cs = new TClienteSesion(idCli, idSes, new Date(), "16:00");

		assertEquals(idCli, cs.getIdCliente());
		assertEquals(idSes, cs.getIdSesion());
	}



	@Test
	@DisplayName("Grafo completo: Sala + Entrenador + Sesion + Cliente + Apunta")
	void grafoCompleto_navegabilidad() {

		TSala sala = new TSala(1, "Sala Fitness", 25);
		TEntrenador entrenador = new TEntrenador(1, "12345678A", "Carlos", "600111222", 1);
		TCliente cliente = new TCliente(1, "99999999Z", "María", "611222333", "maria@x.com", 1);


		TSesion sesion = new TSesion(1, "Crossfit", 60, "2026-06-01 10:00",
			sala.getIdSala(), entrenador.get_id());


		TClienteSesion inscripcion = new TClienteSesion(
			cliente.getId(), sesion.getIdSesion(), new Date(), "09:30");


		assertEquals(sala.getIdSala(), sesion.getIdSala(),
			"Sesión debe referenciar la sala correcta");


		assertEquals(entrenador.get_id(), sesion.getIdEntrenador(),
			"Sesión debe referenciar el entrenador correcto");


		assertEquals(cliente.getId(), inscripcion.getIdCliente(),
			"Inscripción debe referenciar el cliente correcto");


		assertEquals(sesion.getIdSesion(), inscripcion.getIdSesion(),
			"Inscripción debe referenciar la sesión correcta");

		TSesion sesRef = sesion;
		assertEquals(sala.getIdSala(), sesRef.getIdSala(),
			"Navegación transitiva: Inscripción → Sesión → Sala");


		assertEquals(entrenador.get_id(), sesRef.getIdEntrenador(),
			"Navegación transitiva: Inscripción → Sesión → Entrenador");
	}
}

package Negocio.Restricciones;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import Integracion.Cliente.TCliente;
import Integracion.Entrenador.TEntrenador;
import Integracion.Sala.TSala;
import Integracion.Sesion.TClienteSesion;
import Integracion.Sesion.TSesion;
import Negocio.Cliente.SACliente;
import Negocio.Cliente.SAClienteImp;
import Negocio.Entrenador.SAEntrenador;
import Negocio.Entrenador.SAEntrenadorImp;
import Negocio.Sala.SASala;
import Negocio.Sala.SASalaImp;
import Negocio.Sesion.SASesion;
import Negocio.Sesion.SASesionImp;
import stubs.FactoriaIntegracionStub;
import stubs.TestHelper;
import java.util.Date;
import java.util.Set;


@DisplayName("Pruebas de Restricciones de Baja con Relaciones Activas")
class RestriccionesBajaTest {

	private FactoriaIntegracionStub factoriaStub;
	private SASala saSala;
	private SAEntrenador saEntrenador;
	private SACliente saCliente;
	private SASesion saSesion;

	@BeforeEach
	void setUp() {
		factoriaStub = new FactoriaIntegracionStub();
		TestHelper.inyectarFactoria(factoriaStub);

		saSala = new SASalaImp();
		saEntrenador = new SAEntrenadorImp();
		saCliente = new SAClienteImp();
		saSesion = new SASesionImp();
	}

	@AfterEach
	void tearDown() {
		factoriaStub.limpiarTodo();
		TestHelper.restaurarFactoria();
	}



	@Test
	@DisplayName("Baja sala CON sesiones activas: debe retornar 0 (impedida)")
	void bajaSala_conSesionesActivas_debeRetornar0() {

		TSala sala = new TSala();
		sala.setNombreSala("Sala Ocupada");
		sala.setAforo(30);
		int idSala = saSala.alta_sala(sala);
		assertTrue(idSala > 0, "La sala debe crearse correctamente");


		TEntrenador entrenador = new TEntrenador();
		entrenador.set_dni("11111111A");
		entrenador.set_nombre("Entrenador1");
		entrenador.set_telefono("600111222");
		int idEntrenador = saEntrenador.alta_entrenador(entrenador);
		assertTrue(idEntrenador > 0, "El entrenador debe crearse correctamente");


		TSesion sesion = new TSesion();
		sesion.setObjetivo("Cardio");
		sesion.setDuracion(60);
		sesion.setFechaHora("2026-06-01 10:00");
		sesion.setIdSala(idSala);
		sesion.setIdEntrenador(idEntrenador);
		int idSesion = saEntrenador.crear_sesion(sesion);
		assertTrue(idSesion > 0, "La sesion debe crearse correctamente");


		int resultado = saSala.baja_sala(idSala);
		assertEquals(0, resultado,
			"No se debe poder dar de baja una sala que tiene sesiones activas");


		TSala salaLeida = saSala.mostrar_sala(idSala);
		assertNotNull(salaLeida, "La sala debe seguir existiendo tras intento de baja fallido");
	}

	@Test
	@DisplayName("Baja sala SIN sesiones: debe funcionar correctamente")
	void bajaSala_sinSesiones_debeRetornar1() {

		TSala sala = new TSala();
		sala.setNombreSala("Sala Libre");
		sala.setAforo(20);
		int idSala = saSala.alta_sala(sala);
		assertTrue(idSala > 0);


		int resultado = saSala.baja_sala(idSala);
		assertEquals(1, resultado,
			"Una sala sin sesiones debe poderse dar de baja");


		TSala salaLeida = saSala.mostrar_sala(idSala);
		assertNull(salaLeida, "La sala debe dejar de existir tras la baja");
	}



	@Test
	@DisplayName("Baja entrenador activo SIN sesiones: baja logica (activo=0)")
	void bajaEntrenador_sinSesiones_bajaLogica() {
		TEntrenador ent = new TEntrenador();
		ent.set_dni("22222222B");
		ent.set_nombre("Entrenador Libre");
		ent.set_telefono("600222333");
		int idEnt = saEntrenador.alta_entrenador(ent);
		assertTrue(idEnt > 0);


		int resultado = saEntrenador.baja_entrenador(idEnt);
		assertTrue(resultado >= 0, "Baja logica debe tener exito");


		TEntrenador entLeido = saEntrenador.mostrar_entrenador(idEnt);
		assertNotNull(entLeido);
		assertEquals(0, entLeido.get_activo(),
			"Tras baja lógica, activo debe ser 0");
	}

	@Test
	@DisplayName("Baja entrenador ya inactivo: debe retornar -1")
	void bajaEntrenador_yaInactivo_retornaMenos1() {
		TEntrenador ent = new TEntrenador();
		ent.set_dni("33333333C");
		ent.set_nombre("Entrenador Inactivo");
		ent.set_telefono("600333444");
		int idEnt = saEntrenador.alta_entrenador(ent);


		saEntrenador.baja_entrenador(idEnt);


		int resultado = saEntrenador.baja_entrenador(idEnt);
		assertEquals(-1, resultado,
			"No se debe poder dar de baja un entrenador ya inactivo");
	}



	@Test
	@DisplayName("Baja sesion existente: debe eliminarse")
	void bajaSesion_existente_debeEliminarse() {

		TSala sala = new TSala();
		sala.setNombreSala("Sala S");
		sala.setAforo(20);
		int idSala = saSala.alta_sala(sala);

		TEntrenador ent = new TEntrenador();
		ent.set_dni("44444444D");
		ent.set_nombre("Ent S");
		ent.set_telefono("600444555");
		int idEnt = saEntrenador.alta_entrenador(ent);

		TSesion sesion = new TSesion();
		sesion.setObjetivo("Fuerza");
		sesion.setDuracion(45);
		sesion.setFechaHora("2026-07-01 15:00");
		sesion.setIdSala(idSala);
		sesion.setIdEntrenador(idEnt);
		int idSes = saEntrenador.crear_sesion(sesion);
		assertTrue(idSes > 0);


		int resultado = saSesion.baja_sesion(idSes);
		assertTrue(resultado > 0, "La sesion debe poderse eliminar");


		TSesion sesLeida = saSesion.mostrar_sesion(idSes);
		assertNull(sesLeida, "La sesion debe dejar de existir tras la baja");
	}

	@Test
	@DisplayName("Baja sesion inexistente: debe lanzar excepcion")
	void bajaSesion_inexistente_lanzaExcepcion() {
		assertThrows(IllegalArgumentException.class, () -> {
			saSesion.baja_sesion(999);
		}, "Dar de baja una sesion inexistente debe lanzar excepcion");
	}

	@Test
	@DisplayName("Baja sesion con ID<=0: debe lanzar excepcion")
	void bajaSesion_idInvalido_lanzaExcepcion() {
		assertThrows(IllegalArgumentException.class, () -> {
			saSesion.baja_sesion(0);
		});
		assertThrows(IllegalArgumentException.class, () -> {
			saSesion.baja_sesion(-1);
		});
	}



	@Test
	@DisplayName("Baja cliente SIN inscripciones: debe funcionar")
	void bajaCliente_sinInscripciones_debeFuncionar() {
		TCliente cliente = new TCliente("55555555E", "Cliente Libre", "600555666", "libre@x.com");
		int idCliente = saCliente.alta_cliente(cliente);
		assertTrue(idCliente > 0);

		int resultado = saCliente.baja_cliente(idCliente);
		assertTrue(resultado > 0, "Un cliente sin inscripciones debe poderse dar de baja");

		TCliente cliLeido = saCliente.mostrar_cliente(idCliente);
		assertNull(cliLeido, "El cliente no debe existir tras la baja");
	}

	@Test
	@DisplayName("Baja cliente inexistente: debe retornar -1")
	void bajaCliente_inexistente_retornaMenos1() {
		int resultado = saCliente.baja_cliente(999);
		assertEquals(-1, resultado,
			"Dar de baja un cliente inexistente debe retornar -1");
	}

	@Test
	@DisplayName("Baja cliente con ID<=0: debe retornar -1")
	void bajaCliente_idInvalido() {
		assertEquals(-1, saCliente.baja_cliente(0));
		assertEquals(-1, saCliente.baja_cliente(-5));
	}



	@Test
	@DisplayName("Alta sesión con sala inexistente: debe lanzar excepción")
	void altaSesion_salaInexistente_lanzaExcepcion() {
		TEntrenador ent = new TEntrenador();
		ent.set_dni("66666666F");
		ent.set_nombre("Ent X");
		ent.set_telefono("600666777");
		int idEnt = saEntrenador.alta_entrenador(ent);

		TSesion sesion = new TSesion();
		sesion.setObjetivo("Test");
		sesion.setDuracion(30);
		sesion.setFechaHora("2026-08-01 10:00");
		sesion.setIdSala(999);
		sesion.setIdEntrenador(idEnt);

		assertThrows(IllegalArgumentException.class, () -> {
			saEntrenador.crear_sesion(sesion);
		}, "No se debe poder crear sesión con sala inexistente");
	}

	@Test
	@DisplayName("Alta sesión con entrenador inexistente: debe lanzar excepción")
	void altaSesion_entrenadorInexistente_lanzaExcepcion() {
		TSala sala = new TSala();
		sala.setNombreSala("Sala Y");
		sala.setAforo(20);
		int idSala = saSala.alta_sala(sala);

		TSesion sesion = new TSesion();
		sesion.setObjetivo("Test");
		sesion.setDuracion(30);
		sesion.setFechaHora("2026-08-01 10:00");
		sesion.setIdSala(idSala);
		sesion.setIdEntrenador(999);

		assertThrows(IllegalArgumentException.class, () -> {
			saEntrenador.crear_sesion(sesion);
		}, "No se debe poder crear sesión con entrenador inexistente");
	}

	@Test
	@DisplayName("Alta sesión con datos nulos: debe lanzar excepción")
	void altaSesion_datosNulos_lanzaExcepcion() {
		assertThrows(IllegalArgumentException.class, () -> {
			saEntrenador.crear_sesion(null);
		}, "No se debe poder crear sesión con datos nulos");
	}



	@Test
	@DisplayName("Alta cliente duplicado (mismo DNI): debe retornar -1")
	void altaCliente_dniDuplicado_retornaMenos1() {
		TCliente c1 = new TCliente("77777777G", "Cliente1", "600777888", "c1@x.com");
		int id1 = saCliente.alta_cliente(c1);
		assertTrue(id1 > 0);

		TCliente c2 = new TCliente("77777777G", "Cliente2", "600888999", "c2@x.com");
		int id2 = saCliente.alta_cliente(c2);
		assertEquals(-1, id2, "No se debe permitir dos clientes con el mismo DNI");
	}

	@Test
	@DisplayName("Alta entrenador duplicado (mismo DNI): debe retornar -1")
	void altaEntrenador_dniDuplicado_retornaMenos1() {
		TEntrenador e1 = new TEntrenador();
		e1.set_dni("88888888H");
		e1.set_nombre("Ent1");
		e1.set_telefono("600888999");
		int id1 = saEntrenador.alta_entrenador(e1);
		assertTrue(id1 > 0);

		TEntrenador e2 = new TEntrenador();
		e2.set_dni("88888888H");
		e2.set_nombre("Ent2");
		e2.set_telefono("600999000");
		int id2 = saEntrenador.alta_entrenador(e2);
		assertEquals(-1, id2, "No se debe permitir dos entrenadores con el mismo DNI");
	}

	@Test
	@DisplayName("Alta sala con datos inválidos: debe retornar 0")
	void altaSala_datosInvalidos() {
		assertEquals(0, saSala.alta_sala(null), "Datos null deben retornar 0");

		TSala sinNombre = new TSala();
		sinNombre.setAforo(20);
		assertEquals(0, saSala.alta_sala(sinNombre), "Sala sin nombre debe retornar 0");

		TSala sinAforo = new TSala();
		sinAforo.setNombreSala("Test");
		sinAforo.setAforo(0);
		assertEquals(0, saSala.alta_sala(sinAforo), "Sala con aforo 0 debe retornar 0");
	}

	@Test
	@DisplayName("Mostrar todas las salas: devuelve las creadas")
	void mostrarTodasSalas() {
		TSala s1 = new TSala();
		s1.setNombreSala("Sala 1");
		s1.setAforo(10);
		saSala.alta_sala(s1);

		TSala s2 = new TSala();
		s2.setNombreSala("Sala 2");
		s2.setAforo(20);
		saSala.alta_sala(s2);

		Set<TSala> todas = saSala.mostrar_todas_salas();
		assertNotNull(todas);
		assertEquals(2, todas.size());
	}

	@Test
	@DisplayName("Obtener sesiones de una sala: devuelve las vinculadas")
	void obtenerSesionesSala() {
		TSala sala = new TSala();
		sala.setNombreSala("Sala Multi");
		sala.setAforo(30);
		int idSala = saSala.alta_sala(sala);

		TEntrenador ent = new TEntrenador();
		ent.set_dni("AAABBBCCC");
		ent.set_nombre("Ent Multi");
		ent.set_telefono("600000111");
		int idEnt = saEntrenador.alta_entrenador(ent);

		TSesion s1 = new TSesion();
		s1.setObjetivo("Cardio");
		s1.setDuracion(60);
		s1.setFechaHora("2026-06-01 10:00");
		s1.setIdSala(idSala);
		s1.setIdEntrenador(idEnt);
		saEntrenador.crear_sesion(s1);

		TSesion s2 = new TSesion();
		s2.setObjetivo("Yoga");
		s2.setDuracion(45);
		s2.setFechaHora("2026-06-01 11:00");
		s2.setIdSala(idSala);
		s2.setIdEntrenador(idEnt);
		saEntrenador.crear_sesion(s2);

		Set<TSesion> sesiones = saSala.obtener_sesiones_sala(idSala);
		assertNotNull(sesiones);
		assertEquals(2, sesiones.size(),
			"La sala debe tener 2 sesiones asociadas");
	}
}

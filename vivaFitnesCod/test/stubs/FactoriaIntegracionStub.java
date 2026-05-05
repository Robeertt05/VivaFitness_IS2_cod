package stubs;

import Integracion.Cliente.DAOCliente;
import Integracion.Entrenador.DAOEntrenador;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Sala.DAOSala;
import Integracion.Sesion.DAOSesion;

/**
 * Factoria de Integracion para pruebas.
 * Devuelve DAOs stub (en memoria) en lugar de DAOs reales (con BD).
 * Permite ejecutar pruebas unitarias sin dependencia de MySQL.
 */
public class FactoriaIntegracionStub extends FactoriaIntegracion {

	private DAOClienteStub daoCliente;
	private DAOEntrenadorStub daoEntrenador;
	private DAOSalaStub daoSala;
	private DAOSesionStub daoSesion;

	public FactoriaIntegracionStub() {
		// Crear stubs
		daoCliente = new DAOClienteStub();
		daoEntrenador = new DAOEntrenadorStub();
		daoSala = new DAOSalaStub();
		daoSesion = new DAOSesionStub();

		// Conectar stubs entre sí para relaciones
		daoSesion.setDAOSalaStub(daoSala);
		daoSesion.setDAOEntrenadorStub(daoEntrenador);
		daoSala.setDAOSesionStub(daoSesion);
		daoCliente.setDAOSesionStub(daoSesion);
	}

	@Override
	public DAOCliente generaDAOCliente() {
		return daoCliente;
	}

	@Override
	public DAOEntrenador generaDAOEntrenador() {
		return daoEntrenador;
	}

	@Override
	public DAOSala generaDAOSala() {
		return daoSala;
	}

	@Override
	public DAOSesion generaDAOSesion() {
		return daoSesion;
	}

	/** Limpia todos los datos de los stubs */
	public void limpiarTodo() {
		daoCliente.clear();
		daoEntrenador.clear();
		daoSala.clear();
		daoSesion.clear();
	}

	// Getters para acceso directo en tests
	public DAOClienteStub getDAOClienteStub() { return daoCliente; }
	public DAOEntrenadorStub getDAOEntrenadorStub() { return daoEntrenador; }
	public DAOSalaStub getDAOSalaStub() { return daoSala; }
	public DAOSesionStub getDAOSesionStub() { return daoSesion; }
}

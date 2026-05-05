package stubs;

import Integracion.Cliente.DAOCliente;
import Integracion.Entrenador.DAOEntrenador;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Sala.DAOSala;
import Integracion.Sesion.DAOSesion;


public class FactoriaIntegracionStub extends FactoriaIntegracion {

	private DAOClienteStub daoCliente;
	private DAOEntrenadorStub daoEntrenador;
	private DAOSalaStub daoSala;
	private DAOSesionStub daoSesion;

	public FactoriaIntegracionStub() {
		daoCliente = new DAOClienteStub();
		daoEntrenador = new DAOEntrenadorStub();
		daoSala = new DAOSalaStub();
		daoSesion = new DAOSesionStub();

		// Conectar entre sí para relaciones
		daoSesion.setDAOSalaStub(daoSala);
		daoSesion.setDAOEntrenadorStub(daoEntrenador);
		daoSesion.setDAOClienteStub(daoCliente);
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

	// Limpia todos los datos  
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

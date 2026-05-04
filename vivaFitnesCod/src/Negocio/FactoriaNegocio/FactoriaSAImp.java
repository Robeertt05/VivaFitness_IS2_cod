/**
 * Implementacion concreta de la Factoria de Servicios de Aplicacion.
 * Patron: Factory Method - cada metodo crea la implementacion concreta del SA.
 */
package Negocio.FactoriaNegocio;

import Negocio.entrenador.SAEntrenador;
import Negocio.entrenador.SAEntrenadorImp;
import Negocio.Sala.SASala;
import Negocio.Sala.SASalaImp;
import Negocio.Cliente.SACliente;
import Negocio.Cliente.SAClienteImp;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;

import Integracion.FactoriaIntegracion.DAOSesion;
import Integracion.Sala.DAOSala;
import Integracion.FactoriaIntegracion.DAOCliente;

/**
 * Service Application Factory Implementation
 * Creates and provides access to all Service Application objects
 * @author azuri
 */
public class FactoriaSAImp extends FactoriaServicioAplicacion {



	
	private SASesion saSesion;
	private SACliente saCliente;
	private SASala saSala;
	
	public FactoriaSAImp() {
		// Initialize DAO factory
		FactoriaIntegracion daoFactory = FactoriaIntegracion.getInstance();
		
		// Create service applications
		DAOSesion daoSesion = daoFactory.generaDAOSesion();
		this.saSesion = new SASesionImp(daoSesion);
		
		// Create Room Service Application
		DAOSala daoSala = daoFactory.generaDAOSala();
		this.saSala = new SASalaImp(daoSala);
		
		// Create Client Service Application
		DAOCliente daoCliente = daoFactory.generaDAOCliente();
		this.saCliente = new SAClienteImp(daoCliente);
	}
	
	
	@Override
	public SASala generaSASala() {
		return this.saSala;
	}
	


	@Override
	public SAEntrenador crearSAEntrenador() {
		return new SAEntrenadorImp();
	}

	
	@Override
	public SACliente crearSACliente() {
		return new SAClienteImp(FactoriaIntegracion.getInstance().generaDAOCliente(), crearSASesion());
	}

	@Override
	public SASesion crearSASesion() {
		return new SASesionImp(FactoriaIntegracion.getInstance().generaDAOSesion());
	}
}

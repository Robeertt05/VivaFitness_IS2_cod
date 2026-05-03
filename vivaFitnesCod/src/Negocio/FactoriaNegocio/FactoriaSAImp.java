/**
 * 
 */
package Negocio.FactoriaNegocio;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.FactoriaIntegracion.DAOSesion;
import Integracion.FactoriaIntegracion.DAOSala;
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
	
	/**
	 * Get Room Service Application
	 * @return SASala instance
	 */
	@Override
	public SASala generaSASala() {
		return this.saSala;
	}
	
	/**
	 * Get Session Service Application
	 * @return SASesion instance
	 */
	@Override
	public SASesion generaSASesion() {
		return this.saSesion;
	}
	
	/**
	 * Get Client Service Application  
	 * @return SACliente instance
	 */
	@Override
	public SACliente generaSACliente() {
		return this.saCliente;
	}
	
	// Legacy methods for backward compatibility
	/**
	 * Get Session Service Application
	 * @return SASesion instance
	 */
	public SASesion getSASesion() {
		return this.saSesion;
	}
	
	/**
	 * Get Client Service Application  
	 * @return SACliente instance
	 */
	public SACliente getSACliente() {
		return this.saCliente;
	}
	
	/**
	 * Get Room Service Application
	 * @return SASala instance
	 */
	public SASala getSASala() {
		return this.saSala;
	}
}
/**
 * 
 */
package Negocio.FactoriaNegocio;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.FactoriaIntegracion.DAOSesion;

/** 
 * Service Application Factory Implementation
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
	}
	
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
<<<<<<< Updated upstream
	public SACliente getSACliente() {
		return this.saCliente;
=======
	@Override
	public SACliente crearSACliente() {
		return new SAClienteImp(FactoriaIntegracion.getInstance().generaDAOCliente());
>>>>>>> Stashed changes
	}
	
	/**
	 * Get Room Service Application
	 * @return SASala instance
	 */
	public SASala getSASala() {
		return this.saSala;
	}
}
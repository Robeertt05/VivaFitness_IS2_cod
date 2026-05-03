/**
 * 
 */
package Negocio.FactoriaNegocio;

/** 
 * Abstract Factory for Service Applications
 * Defines interface for creating and accessing Service Application objects
 * @author azuri
 */
public class FactoriaServicioAplicacion {
	/** 
	 * Singleton instance
	 */
	private static FactoriaServicioAplicacion instance;

	/** 
	 * Get singleton instance
	 * @return FactoriaServicioAplicacion instance
	 */
	public static FactoriaServicioAplicacion getInstance() {
		// begin-user-code
		if (instance == null) {
			instance = new FactoriaSAImp();
		}
		return instance;
		// end-user-code
	}

	/** 
	 * Generate Room Service Application
	 * @return SASala implementation
	 */
	public SASala generaSASala() {
		// begin-user-code
		// TODO: Override in implementation
		return null;
		// end-user-code
	}

	/** 
	 * Generate Session Service Application
	 * @return SASesion implementation
	 */
	public SASesion generaSASesion() {
		// begin-user-code
		// TODO: Override in implementation
		return null;
		// end-user-code
	}

	/** 
	 * Generate Client Service Application
	 * @return SACliente implementation
	 */
	public SACliente generaSACliente() {
		// begin-user-code
		// TODO: Override in implementation
		return null;
		// end-user-code
	}

	/** 
	 * Generate Trainer Service Application
	 * @return SAEntrenador implementation (if implemented)
	 */
	public Object generaSAEntrenador() {
		// begin-user-code
		// TODO: Implement when Trainer SA is created
		return null;
		// end-user-code
	}
}
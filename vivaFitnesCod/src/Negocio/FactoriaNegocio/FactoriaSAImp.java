/**
 * Implementacion concreta de la Factoria de Servicios de Aplicacion.
 * Patron: Factory Method - cada metodo crea la implementacion concreta del SA.
 */
package Negocio.FactoriaNegocio;

import Negocio.entrenador.SAEntrenador;
import Negocio.entrenador.SAEntrenadorImp;
import Negocio.Cliente.SACliente;
import Negocio.Cliente.SAClienteImp;

/**
 * Service Application Factory Implementation
 * @author azuri
 */
public class FactoriaSAImp extends FactoriaServicioAplicacion {

	/**
	 * Crea y devuelve una nueva instancia de SAEntrenadorImp.
	 */
	@Override
	public SAEntrenador crearSAEntrenador() {
		return new SAEntrenadorImp();
	}

	/**
	 * Crea y devuelve una nueva instancia de SAClienteImp.
	 */
	@Override
	public SACliente crearSACliente() {
		return new SAClienteImp();
	}
}
/**
 * Implementacion concreta de la Factoria de Servicios de Aplicacion.
 * Patron: Factory Method - cada metodo crea la implementacion concreta del SA.
 */
package Negocio.FactoriaNegocio;

import Negocio.Sala.SASala;
import Negocio.Sala.SASalaImp;
import Negocio.Cliente.SACliente;
import Negocio.Cliente.SAClienteImp;
import Negocio.Entrenador.SAEntrenador;
import Negocio.Entrenador.SAEntrenadorImp;
import Negocio.Sesion.SASesion;
import Negocio.Sesion.SASesionImp;


/**
 * Service Application Factory Implementation
 * Creates and provides access to all Service Application objects
 * @author azuri
 */
public class FactoriaSAImp extends FactoriaServicioAplicacion {

	@Override
	public SASala generaSASala() {
		return new SASalaImp();
	}

	@Override
	public SAEntrenador crearSAEntrenador() {
		return new SAEntrenadorImp();
	}

	@Override
	public SACliente crearSACliente() {
		return new SAClienteImp();
	}

	@Override
	public SASesion crearSASesion() {
		return new SASesionImp();
	}
}

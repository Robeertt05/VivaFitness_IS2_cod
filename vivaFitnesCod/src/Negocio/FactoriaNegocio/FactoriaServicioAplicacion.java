/**
 * Factoria abstracta de Servicios de Aplicacion.
 * Patron: Abstract Factory + Singleton.
 * Permite desacoplar la creacion de SA de su implementacion concreta.
 */
package Negocio.FactoriaNegocio;
import Negocio.entrenador.SAEntrenador;
import Negocio.Sala.SASala;
import Negocio.Cliente.SACliente;

/**
 * Abstract factory for Service Application objects.
 * Concrete implementation is FactoriaSAImp.
 * @author azuri
 */
public abstract class FactoriaServicioAplicacion {

	/** Unica instancia de la factoria (Singleton). */
	private static FactoriaServicioAplicacion instance;

	/**
	 * Devuelve la unica instancia de la factoria concreta (Singleton).
	 */
	public static FactoriaServicioAplicacion getInstance() {

		if (instance == null) {
			instance = new FactoriaSAImp();
		}
		return instance;

	}

	/**
	 * Crea y devuelve un SA de Entrenador.
	 */
	public abstract SAEntrenador crearSAEntrenador();

	/**
	 * Crea y devuelve un SA de Cliente.
	 */
	public abstract SACliente crearSACliente();

	/**
	 * Crea y devuelve un SA de Sesion.
	 */
	public abstract SASesion crearSASesion();


	
	public SASala generaSASala() {

		return null;
		// end-user-code
	}

	
}

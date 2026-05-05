
package Negocio.FactoriaNegocio;
import Negocio.Entrenador.SAEntrenador;
import Negocio.Sala.SASala;
import Negocio.Cliente.SACliente;
import Negocio.Sesion.SASesion;


public abstract class FactoriaServicioAplicacion {

	private static FactoriaServicioAplicacion instance;

	public static FactoriaServicioAplicacion getInstance() {

		if (instance == null) {
			instance = new FactoriaSAImp();
		}
		return instance;

	}

	public abstract SAEntrenador crearSAEntrenador();


	public abstract SACliente crearSACliente();

	public abstract SASesion crearSASesion();
	
	public abstract SASala generaSASala();

	
}

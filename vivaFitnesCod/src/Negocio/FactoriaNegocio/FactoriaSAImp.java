
package Negocio.FactoriaNegocio;

import Negocio.Sala.SASala;
import Negocio.Sala.SASalaImp;
import Negocio.Cliente.SACliente;
import Negocio.Cliente.SAClienteImp;
import Negocio.Entrenador.SAEntrenador;
import Negocio.Entrenador.SAEntrenadorImp;
import Negocio.Sesion.SASesion;
import Negocio.Sesion.SASesionImp;



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

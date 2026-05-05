
package Integracion.FactoriaIntegracion;

import Integracion.Entrenador.DAOEntrenador;
import Integracion.Entrenador.DAOEntrenadorImp;
import Integracion.Sala.DAOSala;
import Integracion.Sala.DAOSalaImp;
import Integracion.Cliente.DAOCliente;
import Integracion.Cliente.DAOClienteImp;
import Integracion.Sesion.DAOSesion;
import Integracion.Sesion.DAOSesionImp;


public class FactoriaIntegracionImp extends FactoriaIntegracion {


	@Override
	public DAOEntrenador generaDAOEntrenador() {
		return new DAOEntrenadorImp();
	}


	@Override
	public DAOCliente generaDAOCliente() {
		return new DAOClienteImp();
	}

	@Override
	public DAOSala generaDAOSala() {
		return new DAOSalaImp();
	}

	@Override
	public DAOSesion generaDAOSesion() {
		return new DAOSesionImp();
	}
}

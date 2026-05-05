

package Integracion.FactoriaIntegracion;

import Integracion.Entrenador.DAOEntrenador;
import Integracion.Sala.DAOSala;
import Integracion.Cliente.DAOCliente;
import Integracion.Sesion.DAOSesion;

public abstract class FactoriaIntegracion {

	private static FactoriaIntegracion instance;

	public static FactoriaIntegracion getInstance() {
		if (instance == null) {
			instance = new FactoriaIntegracionImp();
		}
		return instance;
	}

	public abstract DAOEntrenador generaDAOEntrenador();

	public abstract DAOCliente generaDAOCliente();

	public abstract DAOSala generaDAOSala();

	public abstract DAOSesion generaDAOSesion();
}

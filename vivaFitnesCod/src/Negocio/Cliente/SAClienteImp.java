/**
 * 
 */
package Negocio.Cliente;

import Integracion.Cliente.TCliente;
import Integracion.Cliente.DAOCliente;
import Negocio.FactoriaNegocio.SASesion;
import Integracion.Sesion.TSesion;
import java.util.Set;

/** 
 * Service Application implementation for Client
 * @author azuri
 */
public class SAClienteImp implements SACliente {
	
	private DAOCliente daoCliente;
	private SASesion saSesion;
	
	public SAClienteImp(DAOCliente daoCliente, SASesion saSesion) {
		this.daoCliente = daoCliente;
		this.saSesion = saSesion;
	}
	
	@Override
	public int alta_cliente(TCliente datos) {
		// begin-user-code
		// Validate data
		if (datos == null || datos.get_nombre() == null || 
			datos.get_nombre().isEmpty()) {
			return 0;
		}
		// Call DAO to create client
		return daoCliente.create(datos);
		// end-user-code
	}

	@Override
	public int baja_cliente(int id) {
		// begin-user-code
		// Validate ID
		if (id <= 0) {
			return 0;
		}
		// Call DAO to delete client
		return daoCliente.delete(id);
		// end-user-code
	}

	@Override
	public int modificar_cliente(int id, TCliente datos) {
		// begin-user-code
		// Validate data
		if (id <= 0 || datos == null) {
			return 0;
		}
		// Call DAO to update client
		return daoCliente.update(datos);
		// end-user-code
	}

	@Override
	public void mostrar_cliente(int id) {
		// begin-user-code
		// Validate ID
		if (id <= 0) {
			return;
		}
		// Call DAO to retrieve client
		TCliente cliente = daoCliente.read(id);
		if (cliente != null) {
			System.out.println(cliente.toString());
		}
		// end-user-code
	}

	@Override
	public int apuntarse_sesion(int idSesion, int hora, String fecha) {
		// begin-user-code
		// This flow belongs to the client module and is not handled by SASesion anymore.
		if (idSesion <= 0) {
			return 0;
		}
		return 0;
		// end-user-code
	}

	@Override
	public void mostrar_sesiones() {
		// begin-user-code
		// Get all sessions
		if (saSesion == null) {
			return;
		}
		
		Set<TSesion> sesiones = saSesion.mostrar_todas_sesiones();
		if (sesiones != null) {
			for (TSesion sesion : sesiones) {
				System.out.println(sesion.toString());
			}
		}
		// end-user-code
	}

	@Override
	public void desapuntar_sesion(int id, int idSesion) {
		// begin-user-code
		if (id <= 0 || idSesion <= 0) {
			return;
		}
		// end-user-code
	}
}
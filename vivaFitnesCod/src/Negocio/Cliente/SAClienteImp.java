/**
 * 
 */
package Negocio.Cliente;

import Integracion.Cliente.TCliente;
import Integracion.Cliente.DAOCliente;
import java.util.Set;

/** 
 * Service Application implementation for Client
 * @author azuri
 */
public class SAClienteImp implements SACliente {
	
	private DAOCliente daoCliente;
	
	public SAClienteImp(DAOCliente daoCliente) {
		this.daoCliente = daoCliente;
	}
	
	@Override
	public int alta_cliente(TCliente datos) {
		// Validate data
		if (datos == null || datos.getNombreCliente() == null || 
			datos.getNombreCliente().isEmpty()) {
			return 0;
		}
		// Call DAO to create client
		return daoCliente.create(datos);
	}

	@Override
	public int baja_cliente(int id) {
		// Validate ID
		if (id <= 0) {
			return 0;
		}
		// Call DAO to delete client
		return daoCliente.delete(id);
	}

	@Override
	public int modificar_cliente(int id, TCliente datos) {
		// Validate data
		if (id <= 0 || datos == null) {
			return 0;
		}
		datos.setIdCliente(id);
		// Call DAO to update client
		return daoCliente.update(datos);
	}

	@Override
	public TCliente mostrar_cliente(int id) {
		// Validate ID
		if (id <= 0) {
			return null;
		}
		// Call DAO to retrieve client
		return daoCliente.read(id);
	}

	@Override
<<<<<<< Updated upstream
	public int apuntarse_sesion(int idSesion, int hora, String fecha) {
		// begin-user-code
		// Validate parameters (note: hour and date are for reference, actual session uses its own values)
		if (idSesion <= 0) {
			return 0;
		}
		
		// Check if session is available using SASesion
		if (saSesion == null) {
			return 0;
		}
		
		TSesion sesion = saSesion.mostrar_sesion(idSesion);
		if (sesion == null) {
			return 0;
		}
		
		// Check available spaces
		int espacios = saSesion.espacios_disponibles(idSesion);
		if (espacios <= 0) {
			return 0; // Session is full
		}
		
		// Register client in session
		// TODO: This would need a ClientSesion mapping table
		return saSesion.apuntar_cliente_sesion(idSesion, 0); // 0 is placeholder for idCliente from context
		// end-user-code
=======
	public Set<TCliente> mostrar_todos_clientes() {
		// Get all clients
		return daoCliente.read_all();
	}

	@Override
	public int apuntarse_sesion(int idCliente, int idSesion) {
		// Validate parameters
		if (idCliente <= 0 || idSesion <= 0) {
			return 0;
		}
		// TODO: Implement when ClientSesion mapping table is available
		return 1; // Placeholder success
>>>>>>> Stashed changes
	}

	@Override
	public void mostrar_sesiones(int idCliente) {
		// TODO: Implement when ClientSesion mapping table is available
	}

	@Override
<<<<<<< Updated upstream
	public void desapuntar_sesion(int id, int idSesion) {
		// begin-user-code
		// Validate parameters
		if (id <= 0 || idSesion <= 0 || saSesion == null) {
			return;
		}
		
		// Unregister client from session
		saSesion.desapuntar_cliente_sesion(idSesion, id);
		// end-user-code
=======
	public int desapuntar_sesion(int idCliente, int idSesion) {
		// Validate parameters
		if (idCliente <= 0 || idSesion <= 0) {
			return 0;
		}
		// TODO: Implement when ClientSesion mapping table is available
		return 1; // Placeholder success
	}

	@Override
	public TCliente read_by_dni(String dni) {
		if (dni == null || dni.isEmpty()) {
			return null;
		}
		return daoCliente.read_by_dni(dni);
>>>>>>> Stashed changes
	}
}
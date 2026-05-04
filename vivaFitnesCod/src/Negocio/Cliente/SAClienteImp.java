
package Negocio.Cliente;

import Integracion.Cliente.TCliente;
import Integracion.Cliente.DAOCliente;
import java.util.Set;

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
	}

	@Override
	public void mostrar_sesiones(int idCliente) {
		// TODO: Implement when ClientSesion mapping table is available
	}

	@Override
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
	}
}
package Negocio.Cliente;

import java.util.Set;

import Integracion.Cliente.DAOCliente;
import Integracion.Cliente.TCliente;
import Integracion.Sesion.TClienteSesion;
import Integracion.Sesion.TSesion;
import Negocio.FactoriaNegocio.SASesion;

public class SAClienteImp implements SACliente {

	private DAOCliente daoCliente;
	@SuppressWarnings("unused")
	private SASesion saSesion;

	public SAClienteImp(DAOCliente daoCliente, SASesion saSesion) {
		this.daoCliente = daoCliente;
		this.saSesion = saSesion;
	}

	@Override
	public int alta_cliente(TCliente datos) {
		if (!clienteValido(datos) || daoCliente.readByDni(datos.get_dni()) != null) {
			return -1;
		}
		if (datos.get_activo() == null) {
			datos.set_activo(true);
		}
		return daoCliente.create(datos);
	}

	@Override
	public int baja_cliente(int id) {
		if (id <= 0 || daoCliente.read(id) == null) {
			return -1;
		}
		return daoCliente.delete(id);
	}

	@Override
	public int modificar_cliente(int id, TCliente datos) {
		if (id <= 0 || !clienteValido(datos) || daoCliente.read(id) == null) {
			return -1;
		}
		TCliente existenteDni = daoCliente.readByDni(datos.get_dni());
		if (existenteDni != null && existenteDni.getId() != id) {
			return -2;
		}
		datos.setId(id);
		return daoCliente.update(datos);
	}

	@Override
	public TCliente mostrar_cliente(int id) {
		return id > 0 ? daoCliente.read(id) : null;
	}

	@Override
	public Set<TCliente> mostrar_todos_clientes() {
		return daoCliente.read_all();
	}

	@Override
	public int apuntarse_sesion(TClienteSesion datos) {
		if (datos == null || datos.getIdCliente() <= 0 || datos.getIdSesion() <= 0
				|| datos.getFecha() == null || datos.getHora() == null || datos.getHora().trim().isEmpty()) {
			return -1;
		}
		return daoCliente.apuntarSesion(datos);
	}

	@Override
	public Set<TSesion> mostrar_sesiones() {
		return daoCliente.readSesionesDisponibles();
	}

	@Override
	public int desapuntar_sesion(int id, int idSesion) {
		if (id <= 0 || idSesion <= 0) {
			return -1;
		}
		return daoCliente.desapuntarSesion(id, idSesion);
	}

	@Override
	public Set<TSesion> mostrar_sesiones_cliente(int idCliente) {
		return idCliente > 0 ? daoCliente.readSesionesCliente(idCliente) : null;
	}

	private boolean clienteValido(TCliente datos) {
		return datos != null
				&& texto(datos.get_dni())
				&& texto(datos.get_nombre());
	}

	private boolean texto(String value) {
		return value != null && !value.trim().isEmpty();
	}
}

package Negocio.Cliente;

import Integracion.Cliente.DAOCliente;
import Integracion.Cliente.TCliente;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Sesion.TClienteSesion;
import Integracion.Sesion.TSesion;
import java.util.Set;

public class SAClienteImp implements SACliente {

	@Override
	public int alta_cliente(TCliente datos) {
		DAOCliente daoCliente = FactoriaIntegracion.getInstance().generaDAOCliente();
		if (!clienteValido(datos) || daoCliente.readByDni(datos.get_dni()) != null) {
			return -1;
		}
		datos.set_activo(1);
		return daoCliente.create(datos);
	}

	@Override
	public int baja_cliente(int id) {
		DAOCliente daoCliente = FactoriaIntegracion.getInstance().generaDAOCliente();
		if (id <= 0 || daoCliente.read(id) == null) {
			return -1;
		}
		return daoCliente.delete(id);
	}

	@Override
	public int modificar_cliente(int id, TCliente datos) {
		DAOCliente daoCliente = FactoriaIntegracion.getInstance().generaDAOCliente();
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
		DAOCliente daoCliente = FactoriaIntegracion.getInstance().generaDAOCliente();
		return id > 0 ? daoCliente.read(id) : null;
	}

	@Override
	public Set<TCliente> mostrar_todos_clientes() {
		DAOCliente daoCliente = FactoriaIntegracion.getInstance().generaDAOCliente();
		return daoCliente.read_all();
	}

	@Override
	public int apuntarse_sesion(TClienteSesion datos) {
		DAOCliente daoCliente = FactoriaIntegracion.getInstance().generaDAOCliente();
		if (datos == null || datos.getIdCliente() <= 0 || datos.getIdSesion() <= 0
				|| datos.getFecha() == null || datos.getHora() == null || datos.getHora().trim().isEmpty()) {
			return -1;
		}
		return daoCliente.apuntarSesion(datos);
	}

	@Override
	public Set<TSesion> mostrar_sesiones() {
		DAOCliente daoCliente = FactoriaIntegracion.getInstance().generaDAOCliente();
		return daoCliente.readSesionesDisponibles();
	}

	@Override
	public int desapuntar_sesion(int id, int idSesion) {
		DAOCliente daoCliente = FactoriaIntegracion.getInstance().generaDAOCliente();
		if (id <= 0 || idSesion <= 0) {
			return -1;
		}
		return daoCliente.desapuntarSesion(id, idSesion);
	}

	@Override
	public Set<TSesion> mostrar_sesiones_cliente(int idCliente) {
		DAOCliente daoCliente = FactoriaIntegracion.getInstance().generaDAOCliente();
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

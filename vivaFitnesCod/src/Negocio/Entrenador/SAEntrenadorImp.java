package Negocio.Entrenador;

import java.util.Set;
import java.util.HashSet;

import Integracion.Entrenador.TEntrenador;
import Integracion.Entrenador.DAOEntrenador;
import Integracion.Sesion.DAOSesion;
import Integracion.Sesion.TSesion;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;


public class SAEntrenadorImp implements SAEntrenador {

	@Override
	public int alta_entrenador(TEntrenador datos) {
		if (datos == null || datos.get_dni() == null || datos.get_dni().isEmpty()
				|| datos.get_nombre() == null || datos.get_nombre().isEmpty()) {
			return -1;
		}

		DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
		TEntrenador existente = dao.read_by_dni(datos.get_dni());
		if (existente != null) {
			if (existente.get_activo() == 1) {
				return -1;
			}
			existente.set_nombre(datos.get_nombre());
			existente.set_telefono(datos.get_telefono());
			existente.set_activo(1);
			int updated = dao.update(existente);
			return updated > 0 ? existente.get_id() : -1;
		}

		datos.set_activo(1);
		return dao.create(datos);
	}

	@Override
	public int baja_entrenador(int id) {
		if (id <= 0) {
			return -1;
		}

		DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
		TEntrenador entrenador = dao.read(id);
		if (entrenador == null || entrenador.get_activo() == 0) {
			return -1;
		}

		DAOSesion daoSesion = FactoriaIntegracion.getInstance().generaDAOSesion();
		Set<TSesion> sesiones = daoSesion.readByEntrenador(id);
		if (sesiones != null && !sesiones.isEmpty()) {
			return -2; 
		}

		entrenador.set_activo(0);
		return dao.update(entrenador);
	}

	@Override
	public int modificar_entrenador(int id, TEntrenador datos) {
		if (id <= 0 || datos == null) {
			return -1;
		}

		DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
		TEntrenador entrenador = dao.read(id);
		if (entrenador == null || entrenador.get_activo() == 0) {
			return -1;
		}

		if (datos.get_nombre() != null && !datos.get_nombre().isEmpty()) {
			entrenador.set_nombre(datos.get_nombre());
		}
		if (datos.get_telefono() != null && !datos.get_telefono().isEmpty()) {
			entrenador.set_telefono(datos.get_telefono());
		}
		if (datos.get_dni() != null && !datos.get_dni().isEmpty()) {
			TEntrenador existenteDni = dao.read_by_dni(datos.get_dni());
			if (existenteDni != null && existenteDni.get_id() != id) {
				if (existenteDni.get_activo() == 1) {
					return -2;
				}
				
				existenteDni.set_dni(null);
				if (dao.update(existenteDni) <= 0) {
					return -1;
				}
			}
			entrenador.set_dni(datos.get_dni());
		}

		return dao.update(entrenador);
	}

	@Override
	public TEntrenador mostrar_entrenador(int id) {
		if (id <= 0) {
			return null;
		}

		TEntrenador entrenador = FactoriaIntegracion.getInstance().generaDAOEntrenador().read(id);
		return (entrenador != null && entrenador.get_activo() == 1) ? entrenador : null;
	}

	@Override
	public Set<TEntrenador> mostrar_entrenadores() {
		Set<TEntrenador> all = FactoriaIntegracion.getInstance().generaDAOEntrenador().read_all();
		if (all == null) {
			return null;
		}
		Set<TEntrenador> activos = new HashSet<>();
		for (TEntrenador t : all) {
			if (t != null && t.get_activo() == 1) {
				activos.add(t);
			}
		}
		return activos;
	}

	@Override
	public int crear_sesion(int idEntrenador, TEntrenador datos) {
		if (idEntrenador <= 0) {
			return -1;
		}

		TEntrenador entrenador = FactoriaIntegracion.getInstance().generaDAOEntrenador().read(idEntrenador);
		if (entrenador == null || entrenador.get_activo() == 0) {
			return -1;
		}

		return 0;
	}
}

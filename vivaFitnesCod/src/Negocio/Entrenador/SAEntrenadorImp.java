/**
 * Implementacion del Servicio de Aplicacion de Entrenador.
 * Patron: Fachada + coordina con la capa de Integracion via FactoriaIntegracion.
 */
package Negocio.Entrenador;

import java.util.Set;

import Integracion.Entrenador.TEntrenador;
import Integracion.Entrenador.DAOEntrenador;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class SAEntrenadorImp implements SAEntrenador {

	public int alta_entrenador(TEntrenador datos) {
		if (datos == null || datos.get_dni() == null || datos.get_dni().isEmpty()
				|| datos.get_nombre() == null || datos.get_nombre().isEmpty()) {
			return -1;
		}

		DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
		TEntrenador existente = dao.read_by_dni(datos.get_dni());
		if (existente != null) {
			return -1;
		}

		datos.set_activo(1);
		return dao.create(datos);
	}

	public int baja_entrenador(int id) {
		if (id <= 0) {
			return -1;
		}

		DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
		TEntrenador entrenador = dao.read(id);
		if (entrenador == null || entrenador.get_activo() == 0) {
			return -1;
		}

		entrenador.set_activo(0);
		return dao.update(entrenador);
	}

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
			entrenador.set_dni(datos.get_dni());
		}

		return dao.update(entrenador);
	}

	public TEntrenador mostrar_entrenador(int id) {
		if (id <= 0) {
			return null;
		}

		return FactoriaIntegracion.getInstance().generaDAOEntrenador().read(id);
	}

	public Set<TEntrenador> mostrar_entrenadores() {
		return FactoriaIntegracion.getInstance().generaDAOEntrenador().read_all();
	}

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

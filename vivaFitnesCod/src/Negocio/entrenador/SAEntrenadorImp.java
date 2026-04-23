/**
 * Implementacion del Servicio de Aplicacion de Entrenador.
 * Patron: Fachada + coordina con la capa de Integracion via FactoriaIntegracion.
 * Patron: Transaction Script - cada metodo gestiona su propia transaccion.
 */
package Negocio.entrenador;

import java.util.Set;
import Integracion.Entrenador.DAOEntrenador;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import Integracion.Transaction.TManager;
import Integracion.Transaction.Transaction;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class SAEntrenadorImp implements SAEntrenador {

	/**
	 * Da de alta a un nuevo entrenador.
	 * Valida los datos, abre transaccion, delega en el DAO y cierra la transaccion.
	 * @param datos Transfer Object con los datos del nuevo entrenador
	 * @return id asignado, o -1 si los datos son invalidos o falla la operacion
	 */
	public int alta_entrenador(TEntrenador datos) {
		// begin-user-code
		// Validacion basica de datos de entrada
		if (datos == null || datos.get_dni() == null || datos.get_dni().isEmpty()
				|| datos.get_nombre() == null || datos.get_nombre().isEmpty()) {
			return -1;
		}

		TManager tManager = TManager.getInstance();
		Transaction t = tManager.createTransaction();
		int resultado = -1;
		try {
			t.start();
			DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
			// Comprobar que no existe ya un entrenador con ese DNI
			TEntrenador existente = dao.read_by_dni(datos.get_dni());
			if (existente != null) {
				t.rollback();
				return -1;
			}
			datos.set_activo(1);
			resultado = dao.create(datos);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			resultado = -1;
		} finally {
			tManager.deleteTransaction();
		}
		return resultado;
		// end-user-code
	}

	/**
	 * Da de baja logica al entrenador (marca activo = 0).
	 * @param id identificador del entrenador
	 * @return 0 si correcto, -1 si el entrenador no existe o la operacion falla
	 */
	public int baja_entrenador(int id) {
		// begin-user-code
		if (id <= 0) return -1;

		TManager tManager = TManager.getInstance();
		Transaction t = tManager.createTransaction();
		int resultado = -1;
		try {
			t.start();
			DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
			TEntrenador entrenador = dao.read(id);
			if (entrenador == null || entrenador.get_activo() == 0) {
				t.rollback();
				return -1;
			}
			entrenador.set_activo(0);
			resultado = dao.upadate(entrenador);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			resultado = -1;
		} finally {
			tManager.deleteTransaction();
		}
		return resultado;
		// end-user-code
	}

	/**
	 * Modifica los datos de un entrenador existente.
	 * @param id    identificador del entrenador
	 * @param datos Transfer Object con los nuevos datos
	 * @return 0 si correcto, -1 si falla
	 */
	public int modificar_entrenador(int id, TEntrenador datos) {
		// begin-user-code
		if (id <= 0 || datos == null) return -1;

		TManager tManager = TManager.getInstance();
		Transaction t = tManager.createTransaction();
		int resultado = -1;
		try {
			t.start();
			DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
			TEntrenador entrenador = dao.read(id);
			if (entrenador == null || entrenador.get_activo() == 0) {
				t.rollback();
				return -1;
			}
			// Actualizar solo los campos no nulos/vacios del DTO
			if (datos.get_nombre() != null && !datos.get_nombre().isEmpty())
				entrenador.set_nombre(datos.get_nombre());
			if (datos.get_telefono() != null && !datos.get_telefono().isEmpty())
				entrenador.set_telefono(datos.get_telefono());
			if (datos.get_dni() != null && !datos.get_dni().isEmpty())
				entrenador.set_dni(datos.get_dni());

			resultado = dao.upadate(entrenador);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			resultado = -1;
		} finally {
			tManager.deleteTransaction();
		}
		return resultado;
		// end-user-code
	}

	/**
	 * Devuelve los datos de un entrenador por su identificador.
	 * @param id identificador del entrenador
	 * @return TEntrenador o null si no existe
	 */
	public TEntrenador mostrar_entrenador(int id) {
		// begin-user-code
		if (id <= 0) return null;

		TManager tManager = TManager.getInstance();
		Transaction t = tManager.createTransaction();
		TEntrenador entrenador = null;
		try {
			t.start();
			DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
			entrenador = dao.read(id);
			t.commit();
		} catch (Exception e) {
			t.rollback();
			entrenador = null;
		} finally {
			tManager.deleteTransaction();
		}
		return entrenador;
		// end-user-code
	}

	/**
	 * Devuelve todos los entrenadores del sistema.
	 * @return Set de TEntrenador
	 */
	public Set<TEntrenador> mostrar_entrenadores() {
		// begin-user-code
		TManager tManager = TManager.getInstance();
		Transaction t = tManager.createTransaction();
		Set<TEntrenador> entrenadores = null;
		try {
			t.start();
			DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
			entrenadores = dao.read_all();
			t.commit();
		} catch (Exception e) {
			t.rollback();
			entrenadores = null;
		} finally {
			tManager.deleteTransaction();
		}
		return entrenadores;
		// end-user-code
	}

	/**
	 * Crea una sesion de entrenamiento para el entrenador indicado.
	 * @param idEntrenador identificador del entrenador
	 * @param datos        datos adicionales de contexto
	 * @return 0 si correcto, -1 si falla
	 */
	public int crear_sesion(int idEntrenador, TEntrenador datos) {
		// begin-user-code
		if (idEntrenador <= 0) return -1;

		TManager tManager = TManager.getInstance();
		Transaction t = tManager.createTransaction();
		int resultado = -1;
		try {
			t.start();
			DAOEntrenador dao = FactoriaIntegracion.getInstance().generaDAOEntrenador();
			TEntrenador entrenador = dao.read(idEntrenador);
			if (entrenador == null || entrenador.get_activo() == 0) {
				t.rollback();
				return -1;
			}
			// La logica de creacion de sesion se delega en el DAO de Sesion
			// (aqui se valida que el entrenador exista y este activo)
			resultado = 0;
			t.commit();
		} catch (Exception e) {
			t.rollback();
			resultado = -1;
		} finally {
			tManager.deleteTransaction();
		}
		return resultado;
		// end-user-code
	}
}

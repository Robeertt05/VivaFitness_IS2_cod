package stubs;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import java.lang.reflect.Field;

/**
 * Helper para inyectar la factoria stub mediante reflexión.
 * Sustituye el Singleton de FactoriaIntegracion por el stub de test.
 */
public class TestHelper {

	/**
	 * Inyecta la FactoriaIntegracionStub como instancia Singleton
	 * de FactoriaIntegracion usando reflexión.
	 * 
	 * @param stub la factoria stub a inyectar
	 */
	public static void inyectarFactoria(FactoriaIntegracionStub stub) {
		try {
			Field instanceField = FactoriaIntegracion.class.getDeclaredField("instance");
			instanceField.setAccessible(true);
			instanceField.set(null, stub);
		} catch (Exception e) {
			throw new RuntimeException("No se pudo inyectar la factoria de test", e);
		}
	}

	/**
	 * Restaura la factoria de integracion a null para que
	 * el siguiente getInstance() cree la real.
	 */
	public static void restaurarFactoria() {
		try {
			Field instanceField = FactoriaIntegracion.class.getDeclaredField("instance");
			instanceField.setAccessible(true);
			instanceField.set(null, null);
		} catch (Exception e) {
			throw new RuntimeException("No se pudo restaurar la factoria", e);
		}
	}
}

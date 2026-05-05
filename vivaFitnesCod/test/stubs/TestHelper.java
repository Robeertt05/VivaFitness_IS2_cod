package stubs;

import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import java.lang.reflect.Field;


public class TestHelper {

	//Inyecta la FactoriaIntegracionStub como instancia Singleton
	public static void inyectarFactoria(FactoriaIntegracionStub stub) {
		try {
			Field instanceField = FactoriaIntegracion.class.getDeclaredField("instance");
			instanceField.setAccessible(true);
			instanceField.set(null, stub);
		} catch (Exception e) {
			throw new RuntimeException("No se pudo inyectar la factoria de test", e);
		}
	}

	
	//Restaura la factoria de integracion  a null
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

/**
 * Clase de constantes de eventos del sistema.
 * Cada constante identifica un caso de uso o una respuesta del sistema.
 * Patron: Command - el evento identifica que Command debe ejecutar el Controlador.
 */
package Presentacion.FactoriaPresentacion;

/**
 * <!-- begin-UML-doc -->
 * <!-- end-UML-doc -->
 * @author azuri
 * @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
 */
public class Evento {

	// ─── Eventos de Entrenador (1xx) ─────────────────────────────────────────
	public static final int ALTA_ENTRENADOR        = 101;
	public static final int BAJA_ENTRENADOR        = 102;
	public static final int MODIFICAR_ENTRENADOR   = 103;
	public static final int MOSTRAR_ENTRENADOR     = 104;
	public static final int MOSTRAR_ENTRENADORES   = 105;
	public static final int CREAR_SESION           = 106;

	// ─── Eventos de Cliente (2xx) ────────────────────────────────────────────
	public static final int ALTA_CLIENTE           = 201;
	public static final int BAJA_CLIENTE           = 202;
	public static final int MODIFICAR_CLIENTE      = 203;
	public static final int MOSTRAR_CLIENTE        = 204;
	public static final int APUNTARSE_SESION       = 205;
	public static final int DESAPUNTAR_SESION      = 206;
	public static final int MOSTRAR_SESIONES       = 207;

	// ─── Respuestas Entrenador OK (3xx) ──────────────────────────────────────
	public static final int RES_ALTA_ENTRENADOR_OK       = 301;
	public static final int RES_BAJA_ENTRENADOR_OK       = 302;
	public static final int RES_MODIFICAR_ENTRENADOR_OK  = 303;
	public static final int RES_MOSTRAR_ENTRENADOR_OK    = 304;
	public static final int RES_MOSTRAR_ENTRENADORES_OK  = 305;
	public static final int RES_CREAR_SESION_OK          = 306;

	// ─── Respuestas Entrenador KO (4xx) ──────────────────────────────────────
	public static final int RES_ALTA_ENTRENADOR_KO       = 401;
	public static final int RES_BAJA_ENTRENADOR_KO       = 402;
	public static final int RES_MODIFICAR_ENTRENADOR_KO  = 403;
	public static final int RES_MOSTRAR_ENTRENADOR_KO    = 404;
	public static final int RES_MOSTRAR_ENTRENADORES_KO  = 405;
	public static final int RES_CREAR_SESION_KO          = 406;

	// ─── Respuestas Cliente OK (5xx) ─────────────────────────────────────────
	public static final int RES_ALTA_CLIENTE_OK          = 501;
	public static final int RES_BAJA_CLIENTE_OK          = 502;
	public static final int RES_MODIFICAR_CLIENTE_OK     = 503;
	public static final int RES_MOSTRAR_CLIENTE_OK       = 504;

	// ─── Respuestas Cliente KO (6xx) ─────────────────────────────────────────
	public static final int RES_ALTA_CLIENTE_KO          = 601;
	public static final int RES_BAJA_CLIENTE_KO          = 602;
	public static final int RES_MODIFICAR_CLIENTE_KO     = 603;
	public static final int RES_MOSTRAR_CLIENTE_KO       = 604;

	// ─── Navegacion (9xx) ────────────────────────────────────────────────────
	public static final int VOLVER                       = 901;
	public static final int SALIR                        = 902;
}

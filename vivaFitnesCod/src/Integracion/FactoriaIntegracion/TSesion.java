/**
 * 
 */
package Integracion.FactoriaIntegracion;

/** 
 * Transfer Object for Sesion
 * Attributes according to ER diagram: idSesion, objetivo, duración, horario, activo
 * Also includes foreign keys to Sala and Entrenador
 * @author azuri
 */
public class TSesion {
	/** 
	 * Session ID
	 */
	private int idSesion;
	
	/** 
	 * Session objective/goal
	 */
	private String objetivo;
	
	/** 
	 * Session duration (e.g., "60" minutes or "1:30" format)
	 */
	private String duracion;
	
	/** 
	 * Session schedule/time (e.g., "10:00" or time range)
	 */
	private String horario;
	
	/** 
	 * Gym room/location ID
	 */
	private int idSala;
	
	/** 
	 * Trainer ID
	 */
	private int idEntrenador;
	
	/** 
	 * Session status (1 = active, 0 = inactive)
	 */
	private int activo;

	// Constructors
	public TSesion() {
	}

	public TSesion(int idSesion, String objetivo, String duracion, String horario, 
			int idSala, int idEntrenador) {
		this.idSesion = idSesion;
		this.objetivo = objetivo;
		this.duracion = duracion;
		this.horario = horario;
		this.idSala = idSala;
		this.idEntrenador = idEntrenador;
		this.activo = 1;
	}

	// Getters and Setters
	public int getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(int idSesion) {
		this.idSesion = idSesion;
	}

	public String getObjetivo() {
		return objetivo;
	}

	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}

	public String getDuracion() {
		return duracion;
	}

	public void setDuracion(String duracion) {
		this.duracion = duracion;
	}

	public String getHorario() {
		return horario;
	}

	public void setHorario(String horario) {
		this.horario = horario;
	}

	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public int getIdEntrenador() {
		return idEntrenador;
	}

	public void setIdEntrenador(int idEntrenador) {
		this.idEntrenador = idEntrenador;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "TSesion [idSesion=" + idSesion + ", objetivo=" + objetivo + 
				", duracion=" + duracion + ", horario=" + horario + 
				", idSala=" + idSala + ", idEntrenador=" + idEntrenador + 
				", activo=" + activo + "]";
	}

	@Override
	public String toString() {
		return "TSesion [idSesion=" + idSesion + ", nombreSesion=" + nombreSesion + 
				", descripcion=" + descripcion + ", fecha=" + fecha + ", hora=" + hora + 
				", idSala=" + idSala + ", idEntrenador=" + idEntrenador + 
				", capacidadMaxima=" + capacidadMaxima + ", participantsActuales=" + 
				participantsActuales + ", activo=" + activo + "]";
	}
}

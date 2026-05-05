/**
 * 
 */
package Integracion.Sesion;

/** 
 * Transfer Object for Sesion
 * Attributes (aligned with DB table sesion):
 *   idSesion, objetivo, duracion, horario, idSala, idEntrenador, activo
 * @author azuri
 */
public class TSesion {

	private int idSesion;
	private String objetivo;
	private int duracion;
	private String fechaHora;
	private int idSala;
	private int idEntrenador;
	private int activo;

	public TSesion() {
		this.activo = 1;
	}

	public TSesion(int idSesion, String objetivo, int duracion, String fechaHora,
			int idSala, int idEntrenador) {
		this.idSesion = idSesion;
		this.objetivo = objetivo;
		this.duracion = duracion;
		this.fechaHora = fechaHora;
		this.idSala = idSala;
		this.idEntrenador = idEntrenador;
		this.activo = 1;
	}

	public int getIdSesion() { return idSesion; }
	public void setIdSesion(int idSesion) { this.idSesion = idSesion; }

	public String getObjetivo() { return objetivo; }
	public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

	public int getDuracion() { return duracion; }
	public void setDuracion(int duracion) { this.duracion = duracion; }

	public String getFechaHora() { return fechaHora; }
	public void setFechaHora(String fechaHora) { this.fechaHora = fechaHora; }

	public int getIdSala() { return idSala; }
	public void setIdSala(int idSala) { this.idSala = idSala; }

	public int getIdEntrenador() { return idEntrenador; }
	public void setIdEntrenador(int idEntrenador) { this.idEntrenador = idEntrenador; }

	public int getActivo() { return activo; }
	public void setActivo(int activo) { this.activo = activo; }

	@Override
	public String toString() {
		return "idSesion=" + idSesion + ", objetivo=" + objetivo +
			", duracion=" + duracion + " (min), fechaHora=" + fechaHora +
				", idSala=" + idSala + ", idEntrenador=" + idEntrenador +
				", activo=" + activo;
	}
}

/**
 * 
 */
package Integracion.FactoriaIntegracion;

/** 
 * Transfer Object for Sesion
 * Attributes: idSesion, nombreSesion, descripcion, fecha, hora,
 *             idSala, idEntrenador, capacidadMaxima, participantsActuales, activo
 * @author azuri
 */
public class TSesion {

	private int idSesion;
	private String nombreSesion;
	private String descripcion;
	private String fecha;
	private String hora;
	private int idSala;
	private int idEntrenador;
	private int capacidadMaxima;
	private int participantsActuales;
	private int activo;

	public TSesion() {
	}

	public TSesion(int idSesion, String nombreSesion, String descripcion, String fecha,
			String hora, int idSala, int idEntrenador, int capacidadMaxima) {
		this.idSesion = idSesion;
		this.nombreSesion = nombreSesion;
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.hora = hora;
		this.idSala = idSala;
		this.idEntrenador = idEntrenador;
		this.capacidadMaxima = capacidadMaxima;
		this.participantsActuales = 0;
		this.activo = 1;
	}

	public int getIdSesion() { return idSesion; }
	public void setIdSesion(int idSesion) { this.idSesion = idSesion; }

	public String getNombreSesion() { return nombreSesion; }
	public void setNombreSesion(String nombreSesion) { this.nombreSesion = nombreSesion; }

	public String getDescripcion() { return descripcion; }
	public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

	public String getFecha() { return fecha; }
	public void setFecha(String fecha) { this.fecha = fecha; }

	public String getHora() { return hora; }
	public void setHora(String hora) { this.hora = hora; }

	public int getIdSala() { return idSala; }
	public void setIdSala(int idSala) { this.idSala = idSala; }

	public int getIdEntrenador() { return idEntrenador; }
	public void setIdEntrenador(int idEntrenador) { this.idEntrenador = idEntrenador; }

	public int getCapacidadMaxima() { return capacidadMaxima; }
	public void setCapacidadMaxima(int capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }

	public int getParticipantsActuales() { return participantsActuales; }
	public void setParticipantsActuales(int participantsActuales) { this.participantsActuales = participantsActuales; }

	public int getActivo() { return activo; }
	public void setActivo(int activo) { this.activo = activo; }

	@Override
	public String toString() {
		return "TSesion [idSesion=" + idSesion + ", nombreSesion=" + nombreSesion +
				", descripcion=" + descripcion + ", fecha=" + fecha + ", hora=" + hora +
				", idSala=" + idSala + ", idEntrenador=" + idEntrenador +
				", capacidadMaxima=" + capacidadMaxima + ", participantsActuales=" +
				participantsActuales + ", activo=" + activo + "]";
	}
}

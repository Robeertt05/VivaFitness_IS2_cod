/**
 * 
 */
package Integracion.Sala;

/** 
 * Transfer Object for Sala (Room/Gym Space)
 * @author azuri
 */
public class TSala {
	
	/** 
	 * Room ID
	 */
	private int idSala;
	
	/** 
	 * Room name
	 */
	private String nombreSala;
	
	/** 
	 * Room capacity (aforo)
	 */
	private int aforo;
	
	/** 
	 * Room status (1 = active, 0 = inactive)
	 */
	private int activo;

	// Constructors
	public TSala() {
		this.activo = 1;
	}

	public TSala(int idSala, String nombreSala, int aforo) {
		this.idSala = idSala;
		this.nombreSala = nombreSala;
		this.aforo = aforo;
		this.activo = 1;
	}

	// Getters and Setters
	public int getIdSala() {
		return idSala;
	}

	public void setIdSala(int idSala) {
		this.idSala = idSala;
	}

	public String getNombreSala() {
		return nombreSala;
	}

	public void setNombreSala(String nombreSala) {
		this.nombreSala = nombreSala;
	}

	public int getAforo() {
		return aforo;
	}

	public void setAforo(int aforo) {
		this.aforo = aforo;
	}

	public int getActivo() {
		return activo;
	}

	public void setActivo(int activo) {
		this.activo = activo;
	}

	@Override
	public String toString() {
		return "TSala [idSala=" + idSala + ", nombreSala=" + nombreSala + 
				", aforo=" + aforo + ", activo=" + activo + "]";
	}
}

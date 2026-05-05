
package Integracion.Sala;

public class TSala {
	

	private int idSala;
	
	private String nombreSala;
	
	private int aforo;

	private int activo;

	public TSala() {
		this.activo = 1;
	}

	public TSala(int idSala, String nombreSala, int aforo) {
		this.idSala = idSala;
		this.nombreSala = nombreSala;
		this.aforo = aforo;
		this.activo = 1;
	}

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
		return "idSala=" + idSala + ", nombreSala=" + nombreSala + 
				", aforo=" + aforo + ", activo=" + activo;
	}
}

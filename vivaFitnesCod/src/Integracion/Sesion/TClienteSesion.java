/**
 * 
 */
package Integracion.Sesion;

import java.util.Date;

/** 
 * Transfer Object for the "apunta" relationship (M:N) between Cliente and Sesion
 * Represents a client's registration to a session with date and time
 * @author azuri
 */
public class TClienteSesion {
	
	/** 
	 * Client ID (Foreign Key)
	 */
	private int idCliente;
	
	/** 
	 * Session ID (Foreign Key)
	 */
	private int idSesion;
	
	/** 
	 * Registration date (when the client signed up)
	 */
	private Date fecha;
	
	/** 
	 * Registration time (when the client signed up)
	 */
	private String hora;

	// Constructors
	public TClienteSesion() {
	}

	public TClienteSesion(int idCliente, int idSesion, Date fecha, String hora) {
		this.idCliente = idCliente;
		this.idSesion = idSesion;
		this.fecha = fecha;
		this.hora = hora;
	}

	// Getters and Setters
	public int getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(int idCliente) {
		this.idCliente = idCliente;
	}

	public int getIdSesion() {
		return idSesion;
	}

	public void setIdSesion(int idSesion) {
		this.idSesion = idSesion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	@Override
	public String toString() {
		return "TClienteSesion [idCliente=" + idCliente + ", idSesion=" + idSesion + 
				", fecha=" + fecha + ", hora=" + hora + "]";
	}
}

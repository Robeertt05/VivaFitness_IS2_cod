
package Integracion.Sesion;

import java.util.Date;


public class TClienteSesion {

	private int idCliente;

	private int idSesion;

	private Date fecha;

	private String hora;

	public TClienteSesion() {
	}

	public TClienteSesion(int idCliente, int idSesion, Date fecha, String hora) {
		this.idCliente = idCliente;
		this.idSesion = idSesion;
		this.fecha = fecha;
		this.hora = hora;
	}

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

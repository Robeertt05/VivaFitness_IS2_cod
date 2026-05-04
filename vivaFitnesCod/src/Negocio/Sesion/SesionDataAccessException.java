package Negocio.Sesion;

public class SesionDataAccessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SesionDataAccessException(String message) {
		super(message);
	}

	public SesionDataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}

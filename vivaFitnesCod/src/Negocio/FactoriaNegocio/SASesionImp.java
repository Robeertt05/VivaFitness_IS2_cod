package Negocio.FactoriaNegocio;

import Integracion.Sala.TSala;
import Integracion.Sesion.DAOSesion;
import Integracion.Sesion.TSesion;
import Negocio.entrenador.TEntrenador;
import java.util.Set;

public class SASesionImp implements SASesion {

	private DAOSesion daoSesion;

	public SASesionImp(DAOSesion daoSesion) {
		this.daoSesion = daoSesion;
	}

	@Override
	public int alta_sesion(TSesion datos) {
		if (datos == null) {
			return 0;
		}
		return daoSesion.create(datos);
	}

	@Override
	public int baja_sesion(int idSesion) {
		if (idSesion <= 0) {
			return 0;
		}

		TSesion sesion = daoSesion.read(idSesion);
		if (sesion == null || sesion.getParticipantsActuales() > 0) {
			return 0;
		}

		return daoSesion.delete(idSesion);
	}

	@Override
	public int modificar_sesion(int idSesion, TSesion datos) {
		if (idSesion <= 0 || datos == null || daoSesion.read(idSesion) == null) {
			return 0;
		}

		datos.setIdSesion(idSesion);
		return daoSesion.update(datos);
	}

	@Override
	public TSesion mostrar_sesion(int idSesion) {
		if (idSesion <= 0) {
			return null;
		}
		return daoSesion.read(idSesion);
	}

	@Override
	public Set<TSesion> mostrar_todas_sesiones() {
		return daoSesion.read_all();
	}

	@Override
	public TSala mostrar_sala_sesion(int idSesion) {
		if (idSesion <= 0) {
			return null;
		}
		return daoSesion.getRoom(idSesion);
	}

	@Override
	public TEntrenador mostrar_entrenador_sesion(int idSesion) {
		if (idSesion <= 0) {
			return null;
		}
		return (TEntrenador) daoSesion.getTrainer(idSesion);
	}
}

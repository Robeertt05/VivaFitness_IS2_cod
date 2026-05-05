
package Negocio.Sala;

import Integracion.Sala.TSala;
import Integracion.Sesion.TSesion;
import Integracion.Sala.DAOSala;
import Integracion.FactoriaIntegracion.FactoriaIntegracion;
import java.util.Set;
import java.util.HashSet;


public class SASalaImp implements SASala {

	
	@Override
	public int alta_sala(TSala datos) {
		
		if (datos == null) {
			return 0;
		}
		
		if (datos.getNombreSala() == null || datos.getNombreSala().trim().isEmpty()) {
			return 0;
		}
		
		if (datos.getAforo() <= 0) {
			return 0;
		}
		
		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		Set<TSala> salas = daoSala.read_all();
		if (salas != null) {
			String nombreNueva = datos.getNombreSala().trim();
			for (TSala sala : salas) {
				if (sala != null && sala.getNombreSala() != null
						&& sala.getNombreSala().trim().equalsIgnoreCase(nombreNueva)
						&& sala.getActivo() == 0) {
					// Solo reactivar si está inactiva
					sala.setAforo(datos.getAforo());
					sala.setNombreSala(datos.getNombreSala());
					sala.setActivo(1);
					int updated = daoSala.update(sala);
					return updated > 0 ? sala.getIdSala() : 0;
				}
			}
		}

		datos.setActivo(1);
		return daoSala.create(datos);
	}

	
	@Override
	public int baja_sala(int idSala) {
		if (idSala <= 0) {
			return 0;
		}
		
		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		
		TSala sala = daoSala.read(idSala);
		if (sala == null) {
			return 0;
		}
		
		Set<TSesion> sesiones = daoSala.readSessionsByRoom(idSala);
		if (sesiones != null && !sesiones.isEmpty()) {
			return 0;
		}
		
		return daoSala.delete(idSala);
	}

	@Override
	public int modificar_sala(int idSala, TSala datos) {
		if (idSala <= 0 || datos == null) {
			return 0;
		}
		
		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		
		TSala salaActual = daoSala.read(idSala);
		if (salaActual == null) {
			return 0;
		}
		
		if (datos.getNombreSala() == null || datos.getNombreSala().trim().isEmpty()) {
			return 0;
		}
		
		if (datos.getAforo() <= 0) {
			return 0;
		}
		
		datos.setIdSala(idSala);
		
		return daoSala.update(datos);
	}

	@Override
	public TSala mostrar_sala(int idSala) {
		if (idSala <= 0) {
			return null;
		}
		
		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		TSala sala = daoSala.read(idSala);
		return (sala != null && sala.getActivo() == 1) ? sala : null;
	}

	@Override
	public Set<TSala> mostrar_todas_salas() {
		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		Set<TSala> all = daoSala.read_all();
		if (all == null) {
			return null;
		}
		Set<TSala> activos = new HashSet<>();
		for (TSala s : all) {
			if (s != null && s.getActivo() == 1) {
				activos.add(s);
			}
		}
		return activos;
	}

	@Override
	public Set<TSesion> obtener_sesiones_sala(int idSala) {
		if (idSala <= 0) {
			return null;
		}
		
		DAOSala daoSala = FactoriaIntegracion.getInstance().generaDAOSala();
		
		TSala sala = daoSala.read(idSala);
		if (sala == null) {
			return null;
		}
		
		return daoSala.readSessionsByRoom(idSala);
	}
}

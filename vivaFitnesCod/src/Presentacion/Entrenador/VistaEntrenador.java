/**
 * Vista principal del menu de Entrenador.
 * Muestra las opciones: Alta, Baja, Modificar, Mostrar, Crear Sesion.
 */
package Presentacion.Entrenador;

import javax.swing.*;
import java.awt.*;
import Presentacion.FactoriaPresentacion.IGUI;
import Controlador.Context;

public class VistaEntrenador extends JFrame implements IGUI {

	private JPanel panel;
	private JButton btnAlta, btnBaja, btnModificar, btnMostrar, btnCrearSesion, btnVolver;

	public VistaEntrenador() {
		setTitle("Gestion de Entrenadores - VivaFitness");
		setSize(400, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel();
		panel.setLayout(new GridLayout(6, 1, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

		btnAlta = new JButton("Alta Entrenador");
		btnBaja = new JButton("Baja Entrenador");
		btnModificar = new JButton("Modificar Entrenador");
		btnMostrar = new JButton("Mostrar Entrenador");
		btnCrearSesion = new JButton("Crear Sesion");
		btnVolver = new JButton("Volver");

		btnAlta.addActionListener(e -> {
			new VistaAltaEntrenador().setVisible(true);
			dispose();
		});
		btnBaja.addActionListener(e -> {
			new VistaBajaEntrenador().setVisible(true);
			dispose();
		});
		btnModificar.addActionListener(e -> {
			new VistaModificarEntrenador().setVisible(true);
			dispose();
		});
		btnMostrar.addActionListener(e -> {
			new VistaMostrarEntrenador().setVisible(true);
			dispose();
		});
		btnCrearSesion.addActionListener(e -> {
			new VistaCrearSesion().setVisible(true);
			dispose();
		});
		btnVolver.addActionListener(e -> dispose());

		panel.add(btnAlta);
		panel.add(btnBaja);
		panel.add(btnModificar);
		panel.add(btnMostrar);
		panel.add(btnCrearSesion);
		panel.add(btnVolver);
		add(panel);
	}

	@Override
	public void update(Context context) {
		// La vista menu no procesa respuestas; las subvistas lo hacen.
	}
}
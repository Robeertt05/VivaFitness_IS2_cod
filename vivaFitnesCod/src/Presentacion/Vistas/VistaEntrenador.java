/**
 * Vista principal del menu de Entrenador.
 * Muestra las opciones: Alta, Baja, Modificar, Mostrar, Crear Sesion.
 */
package Presentacion.Vistas;

import javax.swing.*;
import java.awt.*;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.FactoriaVistas;
import Presentacion.FactoriaPresentacion.IGUI;
import Controlador.Context;

public class VistaEntrenador extends JFrame implements IGUI {

	public VistaEntrenador() {
		setTitle("Entrenadores");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initComponents();
	}

	private void initComponents() {
		JPanel root = new JPanel(new BorderLayout(10, 16));
		root.setBorder(BorderFactory.createEmptyBorder(20, 26, 24, 26));
		root.setBackground(new Color(236, 236, 236));

		JLabel title = new JLabel("Menu de Entrenadores", SwingConstants.CENTER);
		title.setFont(new Font("SansSerif", Font.BOLD, 34));

		JPanel grid = new JPanel(new GridLayout(3, 2, 24, 20));
		grid.setOpaque(false);

		JButton btnAlta = crearBotonAccion("Alta entrenador");
		JButton btnMostrar = crearBotonAccion("Mostrar entrenador");
		JButton btnBaja = crearBotonAccion("Baja entrenador");
		JButton btnMostrarTodos = crearBotonAccion("Mostrar todos los entrenadores");
		JButton btnModificar = crearBotonAccion("Modificar entrenador");
		JButton btnCrearSesion = crearBotonAccion("Crear sesion");

		btnAlta.addActionListener(e -> abrirVista(Evento.ALTA_ENTRENADOR));
		btnBaja.addActionListener(e -> abrirVista(Evento.BAJA_ENTRENADOR));
		btnModificar.addActionListener(e -> abrirVista(Evento.MODIFICAR_ENTRENADOR));
		btnMostrar.addActionListener(e -> abrirVista(Evento.MOSTRAR_ENTRENADOR));
		btnMostrarTodos.addActionListener(e -> abrirVista(Evento.MOSTRAR_ENTRENADORES));
		btnCrearSesion.addActionListener(e -> abrirVista(Evento.CREAR_SESION));

		grid.add(btnAlta);
		grid.add(btnMostrar);
		grid.add(btnBaja);
		grid.add(btnMostrarTodos);
		grid.add(btnModificar);
		grid.add(btnCrearSesion);

		root.add(title, BorderLayout.NORTH);
		root.add(grid, BorderLayout.CENTER);

		setContentPane(root);
	}

	private JButton crearBotonAccion(String texto) {
		JButton button = new JButton(texto);
		button.setFont(new Font("SansSerif", Font.PLAIN, 28));
		button.setFocusPainted(false);
		button.setBackground(new Color(188, 208, 225));
		button.setOpaque(true);
		button.setBorder(BorderFactory.createLineBorder(new Color(45, 45, 45), 2, true));
		return button;
	}

	private void abrirVista(Evento evento) {
		IGUI vista = FactoriaVistas.getInstance().generarVistas(evento);
		if (vista instanceof JFrame) {
			((JFrame) vista).setVisible(true);
		}
	}

	@Override
	public void update(Context context) {
		// La vista menu no procesa respuestas; las subvistas lo hacen.
	}
}

/**
 * Vista principal del menu de Sala.
 * Muestra las opciones: Alta, Baja, Modificar, Mostrar, Mostrar todas las salas, Obtener sesiones.
 */
package Presentacion.Vistas;

import javax.swing.*;
import java.awt.*;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.FactoriaVistas;
import Presentacion.FactoriaPresentacion.IGUI;
import Controlador.Context;

public class VistaSala extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaSala() {
		setTitle("Salas");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initComponents();
	}

	private void initComponents() {
		JPanel root = new JPanel(new BorderLayout(10, 16));
		root.setBorder(BorderFactory.createEmptyBorder(20, 26, 24, 26));
		root.setBackground(new Color(236, 236, 236));

		JLabel title = new JLabel("Menu de Salas", SwingConstants.CENTER);
		title.setFont(new Font("SansSerif", Font.BOLD, 34));

		JPanel grid = new JPanel(new GridLayout(3, 2, 24, 20));
		grid.setOpaque(false);

		JButton btnAlta = crearBotonAccion("Alta sala");
		JButton btnMostrar = crearBotonAccion("Mostrar sala");
		JButton btnBaja = crearBotonAccion("Baja sala");
		JButton btnMostrarTodas = crearBotonAccion("Mostrar todas las salas");
		JButton btnModificar = crearBotonAccion("Modificar sala");
		JButton btnSesiones = crearBotonAccion("Obtener sesiones de sala");

		btnAlta.addActionListener(e -> abrirVista(Evento.ALTA_SALA));
		btnBaja.addActionListener(e -> abrirVista(Evento.BAJA_SALA));
		btnModificar.addActionListener(e -> abrirVista(Evento.MODIFICAR_SALA));
		btnMostrar.addActionListener(e -> abrirVista(Evento.MOSTRAR_SALA));
		btnMostrarTodas.addActionListener(e -> abrirVista(Evento.MOSTRAR_TODAS_SALAS));
		btnSesiones.addActionListener(e -> abrirVista(Evento.OBTENER_SESIONES_SALA));

		grid.add(btnAlta);
		grid.add(btnMostrar);
		grid.add(btnBaja);
		grid.add(btnMostrarTodas);
		grid.add(btnModificar);
		grid.add(btnSesiones);

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

package Presentacion.Vistas;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.FactoriaVistas;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VistaCliente extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;

	public VistaCliente() {
		setTitle("Clientes");
		setSize(900, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initComponents();
	}

	private void initComponents() {
		JPanel root = new JPanel(new BorderLayout(10, 16));
		root.setBorder(BorderFactory.createEmptyBorder(20, 26, 24, 26));
		root.setBackground(new Color(236, 236, 236));

		JLabel title = new JLabel("Menu de Clientes", SwingConstants.CENTER);
		title.setFont(new Font("SansSerif", Font.BOLD, 34));

		JPanel grid = new JPanel(new GridLayout(4, 2, 24, 20));
		grid.setOpaque(false);

		addButton(grid, "Alta cliente", Evento.ALTA_CLIENTE);
		addButton(grid, "Mostrar cliente", Evento.MOSTRAR_CLIENTE);
		addButton(grid, "Mostrar clientes", Evento.MOSTRAR_CLIENTES);
		addButton(grid, "Modificar cliente", Evento.MODIFICAR_CLIENTE);
		addButton(grid, "Baja cliente", Evento.BAJA_CLIENTE);
		addButton(grid, "Apuntarse a sesion", Evento.APUNTARSE_SESION);
		addButton(grid, "Desapuntarse de sesion", Evento.DESAPUNTARSE_SESION);
		addButton(grid, "Sesiones del cliente", Evento.MOSTRAR_SESIONES_CLIENTE);

		root.add(title, BorderLayout.NORTH);
		root.add(grid, BorderLayout.CENTER);
		setContentPane(root);
	}

	private void addButton(JPanel grid, String texto, Evento evento) {
		JButton button = new JButton(texto);
		button.setFont(new Font("SansSerif", Font.BOLD, 16));
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.addActionListener(e -> abrirVista(evento));
		grid.add(button);
	}

	private void abrirVista(Evento evento) {
		IGUI vista = FactoriaVistas.getInstance().generarVistas(evento);
		if (vista instanceof JFrame) {
			((JFrame) vista).setVisible(true);
		}
	}

	@Override
	public void update(Controlador.Context context) {
	}
}

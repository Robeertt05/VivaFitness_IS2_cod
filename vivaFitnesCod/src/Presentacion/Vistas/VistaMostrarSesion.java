/**
 * 
 */
package Presentacion.Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.FactoriaPresentacion.Evento;
import Controlador.Controller;
import Controlador.Context;

/** 
 * View for displaying session details
 * @author azuri
 */
public class VistaMostrarSesion extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JLabel> jLabel;
	
	private JComboBox<Integer> cbSesion;
	private JTextArea txtDetalles;
	private JButton btnMostrar;
	private JButton btnCerrar;
	
	public VistaMostrarSesion() {
		setTitle("Detalles de sesion");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel topPanel = new JPanel(new GridLayout(2, 1, 10, 10));
		
		// Select Session
		jLabel.add(new JLabel("Seleccionar sesion:"));
		topPanel.add(new JLabel("Seleccionar sesion:"));
		cbSesion = new JComboBox<>();
		cargarIdsDemo(cbSesion);
		topPanel.add(cbSesion);
		
		// Details area
		txtDetalles = new JTextArea();
		txtDetalles.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(txtDetalles);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnMostrar = new JButton("Mostrar");
		btnCerrar = new JButton("Cerrar");
		jButton.add(btnMostrar);
		jButton.add(btnCerrar);
		buttonPanel.add(btnMostrar);
		buttonPanel.add(btnCerrar);
		
		jPanel.add(topPanel);
		jPanel.add(buttonPanel);
		
		add(topPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		btnMostrar.addActionListener(e -> update(Controller.getInstance().action(
				new Context(Evento.MOSTRAR_SESION, getSelectedSessionId()))));
		btnCerrar.addActionListener(e -> dispose());
	}

	private void cargarIdsDemo(JComboBox<Integer> combo) {
		for (int i = 1; i <= 20; i++) {
			combo.addItem(i);
		}
	}
	
	public int getSelectedSessionId() {
		Object selected = cbSesion.getSelectedItem();
		return selected != null ? (Integer) selected : -1;
	}
	
	public void displaySessionDetails(String details) {
		txtDetalles.setText(details);
	}
	
	public void addShowButtonListener(ActionListener listener) {
		btnMostrar.addActionListener(listener);
		actionListener.add(listener);
	}
	
	public void addCloseButtonListener(ActionListener listener) {
		btnCerrar.addActionListener(listener);
		actionListener.add(listener);
	}

	@Override
	public void update(Context context) {
		if (context != null && context.isSuccess()) {
			displaySessionDetails(String.valueOf(context.getData()));
		} else if (context != null) {
			displaySessionDetails(context.getMessage());
		}
	}
}

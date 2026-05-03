/**
 * 
 */
package Presentacion.Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
 * View for deleting a session
 * @author azuri
 */
public class VistaEliminarSesion extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JLabel> jLabel;
	
	private JComboBox<Integer> cbSesion;
	private JButton btnEliminar;
	private JButton btnCancelar;
	
	public VistaEliminarSesion() {
		setTitle("Eliminar sesion");
		setSize(300, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		
		// Select Session
		jLabel.add(new JLabel("Seleccionar sesion:"));
		mainPanel.add(new JLabel("Seleccionar sesion:"));
		cbSesion = new JComboBox<>();
		cargarIdsDemo(cbSesion);
		mainPanel.add(cbSesion);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnEliminar = new JButton("Eliminar");
		btnCancelar = new JButton("Cancelar");
		jButton.add(btnEliminar);
		jButton.add(btnCancelar);
		buttonPanel.add(btnEliminar);
		buttonPanel.add(btnCancelar);
		
		jPanel.add(mainPanel);
		jPanel.add(buttonPanel);
		
		add(mainPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		btnEliminar.addActionListener(e -> {
			Context result = Controller.getInstance().action(new Context(Evento.BAJA_SESION, getSelectedSessionId()));
			JOptionPane.showMessageDialog(this, result.getMessage());
			update(result);
		});
		btnCancelar.addActionListener(e -> dispose());
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
	
	public void addDeleteButtonListener(ActionListener listener) {
		btnEliminar.addActionListener(listener);
		actionListener.add(listener);
	}
	
	public void addCancelButtonListener(ActionListener listener) {
		btnCancelar.addActionListener(listener);
		actionListener.add(listener);
	}

	@Override
	public void update(Context context) {
		if (context != null && context.isSuccess()) {
			setVisible(false);
		}
	}
}

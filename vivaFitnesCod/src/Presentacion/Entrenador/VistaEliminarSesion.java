/**
 * 
 */
package Presentacion.Entrenador;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import Presentacion.FactoriaPresentacion.IGUI;
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
		setTitle("Delete Session");
		setSize(300, 150);
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
		jLabel.add(new JLabel("Select Session:"));
		mainPanel.add(new JLabel("Select Session:"));
		cbSesion = new JComboBox<>();
		mainPanel.add(cbSesion);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnEliminar = new JButton("Delete");
		btnCancelar = new JButton("Cancel");
		jButton.add(btnEliminar);
		jButton.add(btnCancelar);
		buttonPanel.add(btnEliminar);
		buttonPanel.add(btnCancelar);
		
		jPanel.add(mainPanel);
		jPanel.add(buttonPanel);
		
		add(mainPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
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

/**
 * 
 */
package Presentacion.Entrenador;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import Presentacion.FactoriaPresentacion.IGUI;
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
	
	private JTextField txtIdSesion;
	private JTextArea txtDetalles;
	private JButton btnMostrar;
	private JButton btnCerrar;
	
	public VistaMostrarSesion() {
		setTitle("Detalles de la Sesion");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel topPanel = new JPanel(new GridLayout(2, 1, 10, 10));
		
		// Session ID input
		jLabel.add(new JLabel("ID Sesion:"));
		topPanel.add(new JLabel("ID Sesion:"));
		txtIdSesion = new JTextField(10);
		topPanel.add(txtIdSesion);
		
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
	}
	
	public int getSelectedSessionId() {
		try { return Integer.parseInt(txtIdSesion.getText().trim()); } catch (NumberFormatException ex) { return -1; }
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
		// begin-user-code
		// Update display with context data
		// end-user-code
	}
}

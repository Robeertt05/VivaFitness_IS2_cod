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
 * View for registering a client to a session
 * @author azuri
 */
public class VistaApuntarSesion extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JLabel> jLabel;
	
	private JComboBox<Integer> cbSesion;
	private JComboBox<Integer> cbCliente;
	private JButton btnApuntar;
	private JButton btnCancelar;
	
	public VistaApuntarSesion() {
		setTitle("Register for Session");
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		
		// Select Session
		jLabel.add(new JLabel("Session:"));
		mainPanel.add(new JLabel("Session:"));
		cbSesion = new JComboBox<>();
		mainPanel.add(cbSesion);
		
		// Select Client
		jLabel.add(new JLabel("Client:"));
		mainPanel.add(new JLabel("Client:"));
		cbCliente = new JComboBox<>();
		mainPanel.add(cbCliente);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnApuntar = new JButton("Register");
		btnCancelar = new JButton("Cancel");
		jButton.add(btnApuntar);
		jButton.add(btnCancelar);
		buttonPanel.add(btnApuntar);
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
	
	public int getSelectedClientId() {
		Object selected = cbCliente.getSelectedItem();
		return selected != null ? (Integer) selected : -1;
	}
	
	public void addRegisterButtonListener(ActionListener listener) {
		btnApuntar.addActionListener(listener);
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

/**
 * 
 */
package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Sesion.TSesion;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/** 
 * Vista para crear una nueva sesion
 * @author azuri
 */
public class VistaCrearSesion extends JFrame implements IGUI {
	/** 
	 * Action listeners
	 */
	private Set<ActionListener> actionListener;
	/** 
	 * Buttons
	 */
	private Set<JButton> jButton;
	/** 
	 * Panels
	 */
	private Set<JPanel> jPanel;
	/** 
	 * Text fields
	 */
	private Set<JTextField> jTextField;
	/** 
	 * Labels
	 */
	private Set<JLabel> jLabel;
	
	private JTextField txtObjetivo;
	private JTextField txtDuracion;
	private JTextField txtHorario;
	private JTextField txtIdSala;
	private JTextField txtIdEntrenador;
	private JButton btnCrear;
	private JButton btnCancelar;
	
	public VistaCrearSesion() {
		setTitle("Crear sesion");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jTextField = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
		
		// Objetivo
		jLabel.add(new JLabel("Objetivo:"));
		mainPanel.add(new JLabel("Objetivo:"));
		txtObjetivo = new JTextField();
		jTextField.add(txtObjetivo);
		mainPanel.add(txtObjetivo);
		
		// Duracion
		jLabel.add(new JLabel("Duracion (min):"));
		mainPanel.add(new JLabel("Duracion (min):"));
		txtDuracion = new JTextField();
		jTextField.add(txtDuracion);
		mainPanel.add(txtDuracion);
		
		// Horario
		jLabel.add(new JLabel("Horario (yyyy-MM-dd HH:mm):"));
		mainPanel.add(new JLabel("Horario (yyyy-MM-dd HH:mm):"));
		txtHorario = new JTextField("2026-05-10 10:00");
		jTextField.add(txtHorario);
		mainPanel.add(txtHorario);
		
		// Room
		jLabel.add(new JLabel("ID Sala:"));
		mainPanel.add(new JLabel("ID Sala:"));
		txtIdSala = new JTextField();
		jTextField.add(txtIdSala);
		mainPanel.add(txtIdSala);
		
		// Trainer
		jLabel.add(new JLabel("ID Entrenador:"));
		mainPanel.add(new JLabel("ID Entrenador:"));
		txtIdEntrenador = new JTextField();
		jTextField.add(txtIdEntrenador);
		mainPanel.add(txtIdEntrenador);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnCrear = new JButton("Crear");
		btnCancelar = new JButton("Cancelar");
		jButton.add(btnCrear);
		jButton.add(btnCancelar);
		buttonPanel.add(btnCrear);
		buttonPanel.add(btnCancelar);
		
		jPanel.add(mainPanel);
		jPanel.add(buttonPanel);
		
		add(mainPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		btnCrear.addActionListener(e -> {
			if (validateInputs()) {
				Context result = Controller.getInstance().action(new Context(Evento.ALTA_SESION, getSessionData()));
				JOptionPane.showMessageDialog(this, result.getMessage() != null ? result.getMessage() : "Error desconocido");
				if (result.isSuccess()) {
					dispose();
				}
				update(result);
			}
		});
		btnCancelar.addActionListener(e -> dispose());
	}

	public TSesion getSessionData() {
		TSesion sesion = new TSesion();
		sesion.setObjetivo(txtObjetivo.getText().trim());
		sesion.setDuracion(Integer.parseInt(txtDuracion.getText().trim()));
		sesion.setHorario(txtHorario.getText().trim());
		sesion.setIdSala(Integer.parseInt(txtIdSala.getText().trim()));
		sesion.setIdEntrenador(Integer.parseInt(txtIdEntrenador.getText().trim()));
		return sesion;
	}

	private boolean validateInputs() {
		String obj = txtObjetivo.getText().trim();
		if (obj.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El objetivo es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		String hor = txtHorario.getText().trim();
		if (hor.isEmpty()) {
			JOptionPane.showMessageDialog(this, "El horario es obligatorio (yyyy-MM-dd).", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			int dur = Integer.parseInt(txtDuracion.getText().trim());
			if (dur <= 0) throw new NumberFormatException();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Duración debe ser número > 0.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			int sala = Integer.parseInt(txtIdSala.getText().trim());
			if (sala <= 0) throw new NumberFormatException();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "ID Sala debe ser número > 0.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		try {
			int ent = Integer.parseInt(txtIdEntrenador.getText().trim());
			if (ent <= 0) throw new NumberFormatException();
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "ID Entrenador debe ser número > 0.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		// Basic date format check
		if (!hor.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}")) {
			JOptionPane.showMessageDialog(this, "Horario formato: yyyy-MM-dd HH:mm.", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}
	
	public void addCreateButtonListener(ActionListener listener) {
		btnCrear.addActionListener(listener);
		actionListener.add(listener);
	}
	
	public void addCancelButtonListener(ActionListener listener) {
		btnCancelar.addActionListener(listener);
		actionListener.add(listener);
	}

	@Override
	public void update(Context context) {
		// begin-user-code
		if (context != null && context.isSuccess()) {
			setVisible(false);
		}
		// end-user-code
	}
}

/**
 * 
 */
package Presentacion.Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
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
import Integracion.Sesion.TSesion;

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
		jLabel.add(new JLabel("Duracion:"));
		mainPanel.add(new JLabel("Duracion:"));
		txtDuracion = new JTextField();
		jTextField.add(txtDuracion);
		mainPanel.add(txtDuracion);
		
		// Horario
		jLabel.add(new JLabel("Horario:"));
		mainPanel.add(new JLabel("Horario:"));
		txtHorario = new JTextField();
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
			Context result = Controller.getInstance().action(new Context(Evento.ALTA_SESION, getSessionData()));
			JOptionPane.showMessageDialog(this, result.getMessage());
			update(result);
		});
		btnCancelar.addActionListener(e -> dispose());
	}

	public TSesion getSessionData() {
		TSesion sesion = new TSesion();
		sesion.setObjetivo(txtObjetivo.getText());
		sesion.setDuracion(txtDuracion.getText());
		sesion.setHorario(txtHorario.getText());
		try { sesion.setIdSala(Integer.parseInt(txtIdSala.getText().trim())); } catch (NumberFormatException ex) { sesion.setIdSala(0); }
		try { sesion.setIdEntrenador(Integer.parseInt(txtIdEntrenador.getText().trim())); } catch (NumberFormatException ex) { sesion.setIdEntrenador(0); }
		return sesion;
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

/**
 * 
 */
package Presentacion.Entrenador;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

import Presentacion.FactoriaPresentacion.IGUI;
import Controlador.Context;
import Integracion.FactoriaIntegracion.TSesion;

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
	
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtHora;
	private JSpinner spinCapacidad;
	private JComboBox<Integer> cbSala;
	private JComboBox<Integer> cbEntrenador;
	private JButton btnCrear;
	private JButton btnCancelar;
	
	public VistaCrearSesion() {
		setTitle("Crear Sesion");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jTextField = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel mainPanel = new JPanel(new GridLayout(7, 2, 10, 10));
		
		// Session name
		jLabel.add(new JLabel("Nombre sesion:"));
		mainPanel.add(new JLabel("Nombre sesion:"));
		txtNombre = new JTextField();
		jTextField.add(txtNombre);
		mainPanel.add(txtNombre);
		
		// Description
		jLabel.add(new JLabel("Descripcion:"));
		mainPanel.add(new JLabel("Descripcion:"));
		txtDescripcion = new JTextField();
		jTextField.add(txtDescripcion);
		mainPanel.add(txtDescripcion);
		
		// Hour
		jLabel.add(new JLabel("Hora:"));
		mainPanel.add(new JLabel("Hora:"));
		txtHora = new JTextField("10:00");
		jTextField.add(txtHora);
		mainPanel.add(txtHora);
		
		// Capacity
		jLabel.add(new JLabel("Capacidad Maxima:"));
		mainPanel.add(new JLabel("Capacidad Maxima:"));
		spinCapacidad = new JSpinner();
		mainPanel.add(spinCapacidad);
		
		// Room
		jLabel.add(new JLabel("Sala:"));
		mainPanel.add(new JLabel("Sala:"));
		cbSala = new JComboBox<>();
		mainPanel.add(cbSala);
		
		// Trainer
		jLabel.add(new JLabel("Entrenador:"));
		mainPanel.add(new JLabel("Entrenador:"));
		cbEntrenador = new JComboBox<>();
		mainPanel.add(cbEntrenador);
		
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
	}
	
	public TSesion getSessionData() {
		TSesion sesion = new TSesion();
		sesion.setNombreSesion(txtNombre.getText());
		sesion.setDescripcion(txtDescripcion.getText());
		sesion.setHora(txtHora.getText());
		sesion.setCapacidadMaxima((Integer) spinCapacidad.getValue());
		sesion.setIdSala((Integer) cbSala.getSelectedItem());
		sesion.setIdEntrenador((Integer) cbEntrenador.getSelectedItem());
		sesion.setFecha(new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
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
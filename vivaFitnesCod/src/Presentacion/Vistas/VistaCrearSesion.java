/**
 * 
 */
package Presentacion.Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import java.util.Date;

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
	
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtHora;
	private JSpinner spinCapacidad;
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
		jLabel.add(new JLabel("Capacidad maxima:"));
		mainPanel.add(new JLabel("Capacidad maxima:"));
		spinCapacidad = new JSpinner();
		mainPanel.add(spinCapacidad);
		
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
		sesion.setNombreSesion(txtNombre.getText());
		sesion.setDescripcion(txtDescripcion.getText());
		sesion.setHora(txtHora.getText());
		sesion.setCapacidadMaxima((Integer) spinCapacidad.getValue());
		try { sesion.setIdSala(Integer.parseInt(txtIdSala.getText().trim())); } catch (NumberFormatException ex) { sesion.setIdSala(0); }
		try { sesion.setIdEntrenador(Integer.parseInt(txtIdEntrenador.getText().trim())); } catch (NumberFormatException ex) { sesion.setIdEntrenador(0); }
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

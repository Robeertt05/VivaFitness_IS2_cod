/**
 * 
 */
package Presentacion.Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
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
import Integracion.FactoriaIntegracion.TSesion;

/** 
 * View for modifying a session
 * @author azuri
 */
public class VistaModificarSesion extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JTextField> jTextField;
	private Set<JLabel> jLabel;
	
	private JComboBox<Integer> cbSesion;
	private JTextField txtNombre;
	private JTextField txtDescripcion;
	private JTextField txtHora;
	private JButton btnModificar;
	private JButton btnCancelar;
	
	public VistaModificarSesion() {
		setTitle("Modificar sesion");
		setSize(400, 250);
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
		
		// Select Session
		jLabel.add(new JLabel("Seleccionar sesion:"));
		mainPanel.add(new JLabel("Seleccionar sesion:"));
		cbSesion = new JComboBox<>();
		cargarIdsDemo(cbSesion);
		mainPanel.add(cbSesion);
		
		// Name
		jLabel.add(new JLabel("Nombre:"));
		mainPanel.add(new JLabel("Nombre:"));
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
		txtHora = new JTextField();
		jTextField.add(txtHora);
		mainPanel.add(txtHora);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnModificar = new JButton("Modificar");
		btnCancelar = new JButton("Cancelar");
		jButton.add(btnModificar);
		jButton.add(btnCancelar);
		buttonPanel.add(btnModificar);
		buttonPanel.add(btnCancelar);
		
		jPanel.add(mainPanel);
		jPanel.add(buttonPanel);
		
		add(mainPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		btnModificar.addActionListener(e -> {
			Object[] params = { getSelectedSessionId(), getSessionData() };
			Context result = Controller.getInstance().action(new Context(Evento.MODIFICAR_SESION, params));
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
	
	public TSesion getSessionData() {
		TSesion sesion = new TSesion();
		sesion.setNombreSesion(txtNombre.getText());
		sesion.setDescripcion(txtDescripcion.getText());
		sesion.setHora(txtHora.getText());
		return sesion;
	}
	
	public void loadSessionData(TSesion sesion) {
		txtNombre.setText(sesion.getNombreSesion());
		txtDescripcion.setText(sesion.getDescripcion());
		txtHora.setText(sesion.getHora());
	}
	
	public void addModifyButtonListener(ActionListener listener) {
		btnModificar.addActionListener(listener);
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

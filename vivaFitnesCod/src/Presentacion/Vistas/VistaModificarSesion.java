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
 * View for modifying a session
 * @author azuri
 */
public class VistaModificarSesion extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JTextField> jTextField;
	private Set<JLabel> jLabel;
	
	private JTextField txtIdSesion;
	private JTextField txtObjetivo;
	private JTextField txtDuracion;
	private JTextField txtHorario;
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
		
		// Session ID input
		jLabel.add(new JLabel("ID sesion:"));
		mainPanel.add(new JLabel("ID sesion:"));
		txtIdSesion = new JTextField(10);
		jTextField.add(txtIdSesion);
		mainPanel.add(txtIdSesion);
		
		// Name
		jLabel.add(new JLabel("Objetivo:"));
		mainPanel.add(new JLabel("Objetivo:"));
		txtObjetivo = new JTextField();
		jTextField.add(txtObjetivo);
		mainPanel.add(txtObjetivo);
		
		// Description
		jLabel.add(new JLabel("Duracion:"));
		mainPanel.add(new JLabel("Duracion:"));
		txtDuracion = new JTextField();
		jTextField.add(txtDuracion);
		mainPanel.add(txtDuracion);
		
		// Hour
		jLabel.add(new JLabel("Horario:"));
		mainPanel.add(new JLabel("Horario:"));
		txtHorario = new JTextField();
		jTextField.add(txtHorario);
		mainPanel.add(txtHorario);
		
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
			int id = getSelectedSessionId();
			if (id <= 0) {
				JOptionPane.showMessageDialog(this, "Introduzca un ID de sesion valido", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Object[] params = { id, getSessionData() };
			Context result = Controller.getInstance().action(new Context(Evento.MODIFICAR_SESION, params));
			JOptionPane.showMessageDialog(this, result.getMessage());
			update(result);
		});
		btnCancelar.addActionListener(e -> dispose());
	}

	public int getSelectedSessionId() {
		try {
			return Integer.parseInt(txtIdSesion.getText().trim());
		} catch (NumberFormatException ex) {
			return -1;
		}
	}
	
	public TSesion getSessionData() {
		TSesion sesion = new TSesion();
		sesion.setObjetivo(txtObjetivo.getText());
		sesion.setDuracion(txtDuracion.getText());
		sesion.setHorario(txtHorario.getText());
		return sesion;
	}
	
	public void loadSessionData(TSesion sesion) {
		txtObjetivo.setText(sesion.getObjetivo());
		txtDuracion.setText(sesion.getDuracion());
		txtHorario.setText(sesion.getHorario());
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

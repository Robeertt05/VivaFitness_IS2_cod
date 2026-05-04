/**
 * 
 */
package Presentacion.Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
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
		setTitle("Detalles de sesion");
		setSize(400, 300);
		setLocationRelativeTo(null);
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
		jLabel.add(new JLabel("ID sesion:"));
		topPanel.add(new JLabel("ID sesion:"));
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

		btnMostrar.addActionListener(e -> {
			int id = getSelectedSessionId();
			if (id <= 0) {
				JOptionPane.showMessageDialog(this, "Introduzca un ID de sesion valido", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			update(Controller.getInstance().action(new Context(Evento.MOSTRAR_SESION, id)));
		});
		btnCerrar.addActionListener(e -> dispose());
	}

	public int getSelectedSessionId() {
		try {
			return Integer.parseInt(txtIdSesion.getText().trim());
		} catch (NumberFormatException ex) {
			return -1;
		}
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
		if (context != null && context.isSuccess() && context.getData() instanceof TSesion) {
			TSesion s = (TSesion) context.getData();
			String detalles = "ID: " + s.getIdSesion() + "\n"
					+ "Objetivo: " + s.getObjetivo() + "\n"
					+ "Duracion: " + s.getDuracion() + " min\n"
					+ "Horario: " + s.getHorario() + "\n"
					+ "Sala: " + s.getIdSala() + "\n"
					+ "Entrenador: " + s.getIdEntrenador() + "\n"
					+ "Activo: " + s.getActivo();
			displaySessionDetails(detalles);
		} else if (context != null) {
			displaySessionDetails(context.getMessage());
		}
	}
}

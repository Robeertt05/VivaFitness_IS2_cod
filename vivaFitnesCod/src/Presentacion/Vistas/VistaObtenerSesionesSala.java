/**
 * 
 */
package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/** 
 * View for displaying all sessions in a room
	 * CASO 6: Obtener sesiones de una sala (Relacion 1-N)
 * @author azuri
 */
public class VistaObtenerSesionesSala extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JLabel> jLabel;
	
	private JTextField txtIdSala;
	private JTextArea txtSesiones;
	private JButton btnMostrar;
	private JButton btnCerrar;
	
	public VistaObtenerSesionesSala() {
		setTitle("Sesiones de Sala");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		
		// Select Room
		jLabel.add(new JLabel("ID Sala:"));
		topPanel.add(new JLabel("ID Sala:"));
		txtIdSala = new JTextField();
		topPanel.add(txtIdSala);
		
		// Sessions area
		txtSesiones = new JTextArea();
		txtSesiones.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(txtSesiones);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnMostrar = new JButton("Mostrar Sesiones");
		btnCerrar = new JButton("Cerrar");
		btnMostrar.addActionListener(e -> mostrarSesionesSala());
		btnCerrar.addActionListener(e -> dispose());
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
	
	private void mostrarSesionesSala() {
		try {
			int idSala = Integer.parseInt(txtIdSala.getText().trim());
			Context res = Controller.getInstance().action(new Context(Evento.OBTENER_SESIONES_SALA, idSala));
			update(res);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Introduzca un ID valido.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void displaySessions(String sessionsList) {
		txtSesiones.setText(sessionsList);
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
		if (context != null && context.isSuccess() && context.getData() instanceof Set) {
			Set<?> sesiones = (Set<?>) context.getData();
			StringBuilder sb = new StringBuilder();
			for (Object sesion : sesiones) {
				sb.append(sesion.toString()).append("\n\n");
			}
			displaySessions(sb.toString());
		} else if (context != null) {
			displaySessions("");
			JOptionPane.showMessageDialog(this,
				context.getMessage() != null ? context.getMessage() : "No se encontraron sesiones para esta sala.",
				"Informacion",
				JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

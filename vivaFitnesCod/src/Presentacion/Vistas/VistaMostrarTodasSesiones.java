/**
 * 
 */
package Presentacion.Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.FactoriaPresentacion.Evento;
import Controlador.Controller;
import Controlador.Context;

/** 
 * View for listing all sessions
 * @author azuri
 */
public class VistaMostrarTodasSesiones extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	
	private JTextArea txtSesiones;
	private JButton btnActualizar;
	private JButton btnCerrar;
	
	public VistaMostrarTodasSesiones() {
		setTitle("Todas las sesiones");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		// Sessions list area
		txtSesiones = new JTextArea();
		txtSesiones.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(txtSesiones);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnActualizar = new JButton("Actualizar");
		btnCerrar = new JButton("Cerrar");
		jButton.add(btnActualizar);
		jButton.add(btnCerrar);
		buttonPanel.add(btnActualizar);
		buttonPanel.add(btnCerrar);
		
		jPanel.add(buttonPanel);
		
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		btnActualizar.addActionListener(e -> update(Controller.getInstance().action(
				new Context(Evento.MOSTRAR_TODAS_SESIONES, null))));
		btnCerrar.addActionListener(e -> dispose());
	}
	
	public void displaySessions(String sessionsList) {
		txtSesiones.setText(sessionsList);
	}
	
	public void addRefreshButtonListener(ActionListener listener) {
		btnActualizar.addActionListener(listener);
		actionListener.add(listener);
	}
	
	public void addCloseButtonListener(ActionListener listener) {
		btnCerrar.addActionListener(listener);
		actionListener.add(listener);
	}

	@Override
	public void update(Context context) {
		if (context != null && context.getData() instanceof Set) {
			Set<?> sesiones = (Set<?>) context.getData();
			StringBuilder sb = new StringBuilder();
			for (Object sesion : sesiones) {
				sb.append(sesion.toString()).append("\n\n");
			}
			displaySessions(sb.toString());
		} else if (context != null) {
			displaySessions(context.getMessage());
		}
	}
}

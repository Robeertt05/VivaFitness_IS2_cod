/**
 * 
 */
package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/** 
 * View for listing all rooms
 * @author azuri
 */
public class VistaMostrarTodasSalas extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	
	private JTextArea txtSalas;
	private JButton btnActualizar;
	private JButton btnCerrar;
	
	public VistaMostrarTodasSalas() {
		setTitle("Todas las Salas");
		setSize(500, 400);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		// Rooms list area
		txtSalas = new JTextArea();
		txtSalas.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(txtSalas);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnActualizar = new JButton("Actualizar");
		btnCerrar = new JButton("Cerrar");
		btnActualizar.addActionListener(e -> refrescarSalas());
		btnCerrar.addActionListener(e -> dispose());
		jButton.add(btnActualizar);
		jButton.add(btnCerrar);
		buttonPanel.add(btnActualizar);
		buttonPanel.add(btnCerrar);
		
		jPanel.add(buttonPanel);
		
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void refrescarSalas() {
		Context res = Controller.getInstance().action(new Context(Evento.MOSTRAR_TODAS_SALAS, null));
		update(res);
	}
	
	public void displayRooms(String roomsList) {
		txtSalas.setText(roomsList);
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
		if (context != null && context.isSuccess() && context.getData() instanceof Set) {
			Set<?> salas = (Set<?>) context.getData();
			StringBuilder sb = new StringBuilder();
			for (Object sala : salas) {
				sb.append(sala.toString()).append("\n\n");
			}
			displayRooms(sb.toString());
		} else if (context != null) {
			displayRooms("");
			JOptionPane.showMessageDialog(this,
				context.getMessage() != null ? context.getMessage() : "No se encontraron salas.",
				"Informacion",
				JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

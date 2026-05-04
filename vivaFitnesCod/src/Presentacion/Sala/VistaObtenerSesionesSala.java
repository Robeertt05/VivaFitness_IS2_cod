/**
 * 
 */
package Presentacion.Sala;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import Presentacion.FactoriaPresentacion.IGUI;
import Controlador.Context;

/** 
 * View for displaying all sessions in a room
 * CASO 6: Obtener sesiones de una sala (Relación 1-N)
 * @author azuri
 */
public class VistaObtenerSesionesSala extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JLabel> jLabel;
	
	private JComboBox<Integer> cbSala;
	private JTextArea txtSesiones;
	private JButton btnMostrar;
	private JButton btnCerrar;
	
	public VistaObtenerSesionesSala() {
		setTitle("Room Sessions");
		setSize(500, 400);
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
		jLabel.add(new JLabel("Select Room:"));
		topPanel.add(new JLabel("Select Room:"));
		cbSala = new JComboBox<>();
		topPanel.add(cbSala);
		
		// Sessions area
		txtSesiones = new JTextArea();
		txtSesiones.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(txtSesiones);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnMostrar = new JButton("Show Sessions");
		btnCerrar = new JButton("Close");
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
	
	public int getSelectedRoomId() {
		Object selected = cbSala.getSelectedItem();
		return selected != null ? (Integer) selected : -1;
	}
	
	public void setRooms(Set<Integer> roomIds) {
		cbSala.removeAllItems();
		for (Integer id : roomIds) {
			cbSala.addItem(id);
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
		if (context != null && context.getData() instanceof Set) {
			Set<?> sesiones = (Set<?>) context.getData();
			StringBuilder sb = new StringBuilder();
			for (Object sesion : sesiones) {
				sb.append(sesion.toString()).append("\n\n");
			}
			displaySessions(sb.toString());
		}
	}
}

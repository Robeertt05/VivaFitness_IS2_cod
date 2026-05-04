/**
 * 
 */
package Presentacion.Entrenador;

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
 * View for displaying room details
 * @author azuri
 */
public class VistaMostrarSala extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JLabel> jLabel;
	
	private JComboBox<Integer> cbSala;
	private JTextArea txtDetalles;
	private JButton btnMostrar;
	private JButton btnCerrar;
	
	public VistaMostrarSala() {
		setTitle("Room Details");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel topPanel = new JPanel(new GridLayout(2, 1, 10, 10));
		
		// Select Room
		jLabel.add(new JLabel("Select Room:"));
		topPanel.add(new JLabel("Select Room:"));
		cbSala = new JComboBox<>();
		topPanel.add(cbSala);
		
		// Details area
		txtDetalles = new JTextArea();
		txtDetalles.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(txtDetalles);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnMostrar = new JButton("Show");
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
	
	public void displayRoomDetails(String details) {
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
		if (context != null && context.getData() != null) {
			displayRoomDetails(context.getData().toString());
		}
	}
}

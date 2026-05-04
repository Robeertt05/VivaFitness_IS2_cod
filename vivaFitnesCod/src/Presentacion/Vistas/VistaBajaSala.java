/**
 * 
 */
package Presentacion.Entrenador;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import Presentacion.FactoriaPresentacion.IGUI;
import Controlador.Context;

/** 
 * View for deleting a room
 * @author azuri
 */
public class VistaBajaSala extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JLabel> jLabel;
	
	private JComboBox<Integer> cbSala;
	private JButton btnEliminar;
	private JButton btnCancelar;
	
	public VistaBajaSala() {
		setTitle("Delete Room");
		setSize(300, 150);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		
		// Select Room
		jLabel.add(new JLabel("Select Room:"));
		mainPanel.add(new JLabel("Select Room:"));
		cbSala = new JComboBox<>();
		mainPanel.add(cbSala);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnEliminar = new JButton("Delete");
		btnCancelar = new JButton("Cancel");
		jButton.add(btnEliminar);
		jButton.add(btnCancelar);
		buttonPanel.add(btnEliminar);
		buttonPanel.add(btnCancelar);
		
		jPanel.add(mainPanel);
		jPanel.add(buttonPanel);
		
		add(mainPanel, BorderLayout.CENTER);
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
	
	public void addDeleteButtonListener(ActionListener listener) {
		btnEliminar.addActionListener(listener);
		actionListener.add(listener);
	}
	
	public void addCancelButtonListener(ActionListener listener) {
		btnCancelar.addActionListener(listener);
		actionListener.add(listener);
	}

	@Override
	public void update(Context context) {
		// Update room list when rooms are refreshed
	}
}

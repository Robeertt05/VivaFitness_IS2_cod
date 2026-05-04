/**
 * 
 */
package Presentacion.Sala;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import Presentacion.FactoriaPresentacion.IGUI;
import Controlador.Context;
import Integracion.Sala.TSala;

/** 
 * View for updating room details
 * @author azuri
 */
public class VistaModificarSala extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JTextField> jTextField;
	private Set<JLabel> jLabel;
	
	private JComboBox<Integer> cbSala;
	private JTextField txtNombreSala;
	private JSpinner spinAforo;
	private JButton btnModificar;
	private JButton btnCancelar;
	
	public VistaModificarSala() {
		setTitle("Update Room");
		setSize(300, 250);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jTextField = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel mainPanel = new JPanel(new GridLayout(4, 2, 10, 10));
		
		// Select Room
		jLabel.add(new JLabel("Select Room:"));
		mainPanel.add(new JLabel("Select Room:"));
		cbSala = new JComboBox<>();
		mainPanel.add(cbSala);
		
		// Room Name
		jLabel.add(new JLabel("Room Name:"));
		mainPanel.add(new JLabel("Room Name:"));
		txtNombreSala = new JTextField();
		jTextField.add(txtNombreSala);
		mainPanel.add(txtNombreSala);
		
		// Capacity
		jLabel.add(new JLabel("Capacity:"));
		mainPanel.add(new JLabel("Capacity:"));
		spinAforo = new JSpinner();
		mainPanel.add(spinAforo);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnModificar = new JButton("Update");
		btnCancelar = new JButton("Cancel");
		jButton.add(btnModificar);
		jButton.add(btnCancelar);
		buttonPanel.add(btnModificar);
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
	
	public TSala getRoomData() {
		TSala sala = new TSala();
		sala.setIdSala(getSelectedRoomId());
		sala.setNombreSala(txtNombreSala.getText());
		sala.setAforo((Integer) spinAforo.getValue());
		return sala;
	}
	
	public void loadRoomData(TSala sala) {
		if (sala != null) {
			txtNombreSala.setText(sala.getNombreSala());
			spinAforo.setValue(sala.getAforo());
		}
	}
	
	public void addUpdateButtonListener(ActionListener listener) {
		btnModificar.addActionListener(listener);
		actionListener.add(listener);
	}
	
	public void addCancelButtonListener(ActionListener listener) {
		btnCancelar.addActionListener(listener);
		actionListener.add(listener);
	}

	@Override
	public void update(Context context) {
		// Update with result from controller
	}
}

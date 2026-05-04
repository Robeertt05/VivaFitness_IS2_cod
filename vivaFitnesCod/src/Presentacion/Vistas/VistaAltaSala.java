/**
 * 
 */
package Presentacion.Entrenador;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
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
 * View for creating a new room
 * @author azuri
 */
public class VistaAltaSala extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JTextField> jTextField;
	private Set<JLabel> jLabel;
	
	private JTextField txtNombreSala;
	private JSpinner spinAforo;
	private JButton btnCrear;
	private JButton btnCancelar;
	
	public VistaAltaSala() {
		setTitle("Create Room");
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
		JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
		
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
		btnCrear = new JButton("Create");
		btnCancelar = new JButton("Cancel");
		jButton.add(btnCrear);
		jButton.add(btnCancelar);
		buttonPanel.add(btnCrear);
		buttonPanel.add(btnCancelar);
		
		jPanel.add(mainPanel);
		jPanel.add(buttonPanel);
		
		add(mainPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public TSala getRoomData() {
		TSala sala = new TSala();
		sala.setNombreSala(txtNombreSala.getText());
		sala.setAforo((Integer) spinAforo.getValue());
		return sala;
	}
	
	public void clearFields() {
		txtNombreSala.setText("");
		spinAforo.setValue(0);
	}
	
	public void addCreateButtonListener(ActionListener listener) {
		btnCrear.addActionListener(listener);
		actionListener.add(listener);
	}
	
	public void addCancelButtonListener(ActionListener listener) {
		btnCancelar.addActionListener(listener);
		actionListener.add(listener);
	}

	@Override
	public void update(Context context) {
		if (context != null && context.isSuccess()) {
			clearFields();
		}
	}
}

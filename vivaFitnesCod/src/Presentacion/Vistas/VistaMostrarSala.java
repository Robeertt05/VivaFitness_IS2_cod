/**
 * 
 */
package Presentacion.Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
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
import Controlador.Context;
import Controlador.Controller;

/** 
 * View for displaying room details
 * @author azuri
 */
public class VistaMostrarSala extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JLabel> jLabel;
	
	private JTextField txtIdSala;
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
		jLabel.add(new JLabel("Room ID:"));
		topPanel.add(new JLabel("Room ID:"));
		txtIdSala = new JTextField();
		topPanel.add(txtIdSala);
		
		// Details area
		txtDetalles = new JTextArea();
		txtDetalles.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(txtDetalles);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnMostrar = new JButton("Show");
		btnCerrar = new JButton("Close");
		btnMostrar.addActionListener(e -> mostrarSala());
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
	
	private void mostrarSala() {
		try {
			int idSala = Integer.parseInt(txtIdSala.getText().trim());
			Context res = Controller.getInstance().action(new Context(Evento.MOSTRAR_SALA, idSala));
			update(res);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Introduzca un ID valido.", "Error", JOptionPane.ERROR_MESSAGE);
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
		if (context != null && context.isSuccess() && context.getData() != null) {
			displayRoomDetails(context.getData().toString());
		} else if (context != null) {
			displayRoomDetails("");
			JOptionPane.showMessageDialog(this,
				context.getMessage() != null ? context.getMessage() : "No se pudo mostrar la sala.",
				"Informacion",
				JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

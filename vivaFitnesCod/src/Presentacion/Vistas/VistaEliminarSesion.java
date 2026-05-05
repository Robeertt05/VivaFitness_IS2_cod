
package Presentacion.Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
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

public class VistaEliminarSesion extends JFrame implements IGUI {
	
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JLabel> jLabel;
	
	private JTextField txtIdSesion;
	private JButton btnEliminar;
	private JButton btnCancelar;
	
	public VistaEliminarSesion() {
		setTitle("Eliminar sesion");
		setSize(300, 150);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel mainPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		
		
		JLabel lblId = new JLabel("ID sesion:");
		jLabel.add(lblId);
		mainPanel.add(lblId);
		txtIdSesion = new JTextField(10);
		mainPanel.add(txtIdSesion);
		
		
		JPanel buttonPanel = new JPanel();
		btnEliminar = new JButton("Eliminar");
		btnCancelar = new JButton("Cancelar");
		jButton.add(btnEliminar);
		jButton.add(btnCancelar);
		buttonPanel.add(btnEliminar);
		buttonPanel.add(btnCancelar);
		
		jPanel.add(mainPanel);
		jPanel.add(buttonPanel);
		
		add(mainPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		btnEliminar.addActionListener(e -> {
			int id = getSelectedSessionId();
			if (id <= 0) {
				JOptionPane.showMessageDialog(this, "Introduzca un ID de sesion valido", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Context result = Controller.getInstance().action(new Context(Evento.BAJA_SESION, id));
			JOptionPane.showMessageDialog(this, result.getMessage());
			update(result);
		});
		btnCancelar.addActionListener(e -> dispose());
	}
	
	public int getSelectedSessionId() {
		try {
			return Integer.parseInt(txtIdSesion.getText().trim());
		} catch (NumberFormatException ex) {
			return -1;
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
		if (context != null && context.isSuccess()) {
			setVisible(false);
		}
	}
}

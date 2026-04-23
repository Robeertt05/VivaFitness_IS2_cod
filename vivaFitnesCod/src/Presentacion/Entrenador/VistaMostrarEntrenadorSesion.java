/**
 * 
 */
package Presentacion.Entrenador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Controlador.CommandMostrarEntrenadorSesion;
import Controlador.Context;
import Negocio.entrenador.TEntrenador;

/**
 * View to display trainer details for a specific session
 * CASO 5: Mostrar entrenador por sesión (SRS)
 * @author azuri
 */
public class VistaMostrarEntrenadorSesion extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private CommandMostrarEntrenadorSesion command;
	private JTextField txtIdSesion;
	private JLabel lblIdEntrenador;
	private JLabel lblNombre;
	private JLabel lblTelefono;
	private JLabel lblDNI;
	private JButton btnBuscar;
	private JButton btnLimpiar;
	
	public VistaMostrarEntrenadorSesion(CommandMostrarEntrenadorSesion command) {
		this.command = command;
		initializeUI();
	}
	
	private void initializeUI() {
		setTitle("Mostrar Entrenador por Sesión");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// Main panel with GridLayout
		JPanel panelPrincipal = new JPanel(new GridLayout(6, 2, 10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		// ID Session input
		JLabel lblSesion = new JLabel("ID Sesión:");
		txtIdSesion = new JTextField(10);
		panelPrincipal.add(lblSesion);
		panelPrincipal.add(txtIdSesion);
		
		// Trainer ID (read-only)
		JLabel lblTrainerId = new JLabel("ID Entrenador:");
		lblIdEntrenador = new JLabel("-");
		lblIdEntrenador.setForeground(Color.BLUE);
		panelPrincipal.add(lblTrainerId);
		panelPrincipal.add(lblIdEntrenador);
		
		// Trainer Name (read-only)
		JLabel lblNombreLabel = new JLabel("Nombre:");
		lblNombre = new JLabel("-");
		lblNombre.setForeground(Color.BLUE);
		panelPrincipal.add(lblNombreLabel);
		panelPrincipal.add(lblNombre);
		
		// Trainer Phone (read-only)
		JLabel lblTelefonoLabel = new JLabel("Teléfono:");
		lblTelefono = new JLabel("-");
		lblTelefono.setForeground(Color.BLUE);
		panelPrincipal.add(lblTelefonoLabel);
		panelPrincipal.add(lblTelefono);
		
		// Trainer DNI (read-only)
		JLabel lblDNILabel = new JLabel("DNI:");
		lblDNI = new JLabel("-");
		lblDNI.setForeground(Color.BLUE);
		panelPrincipal.add(lblDNILabel);
		panelPrincipal.add(lblDNI);
		
		// Buttons panel
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		btnBuscar = new JButton("Buscar");
		btnLimpiar = new JButton("Limpiar");
		panelBotones.add(btnBuscar);
		panelBotones.add(btnLimpiar);
		panelPrincipal.add(panelBotones);
		
		// Add action listeners
		btnBuscar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				buscarEntrenador();
			}
		});
		
		btnLimpiar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiar();
			}
		});
		
		// Add main panel to frame
		add(panelPrincipal);
	}
	
	private void buscarEntrenador() {
		String idSesionStr = txtIdSesion.getText().trim();
		
		if (idSesionStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor ingrese un ID de sesión", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			int idSesion = Integer.parseInt(idSesionStr);
			Context ctx = command.execute(idSesion);
			
			if (ctx.isSuccess()) {
				TEntrenador entrenador = (TEntrenador) ctx.getData();
				lblIdEntrenador.setText(String.valueOf(entrenador.getIdEntrenador()));
				lblNombre.setText(entrenador.getNombreEntrenador());
				lblTelefono.setText(entrenador.getTelefonoEntrenador());
				lblDNI.setText(entrenador.getDNIEntrenador());
			} else {
				JOptionPane.showMessageDialog(this, ctx.getMessage(), "Información", JOptionPane.INFORMATION_MESSAGE);
				limpiar();
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El ID de sesión debe ser un número entero", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void limpiar() {
		txtIdSesion.setText("");
		lblIdEntrenador.setText("-");
		lblNombre.setText("-");
		lblTelefono.setText("-");
		lblDNI.setText("-");
		txtIdSesion.requestFocus();
	}
}

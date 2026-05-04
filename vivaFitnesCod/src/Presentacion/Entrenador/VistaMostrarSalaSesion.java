/**
 * 
 */
package Presentacion.Entrenador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Controlador.CommandMostrarSalaSesion;
import Controlador.Context;
import Integracion.Sala.TSala;
import Presentacion.FactoriaPresentacion.IGUI;

/**
 * View to display room details for a specific session
 * CASO 4: Mostrar sala por sesiÃÂ³n (SRS)
 * @author azuri
 */
public class VistaMostrarSalaSesion extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;
	private CommandMostrarSalaSesion command;
	private JTextField txtIdSesion;
	private JLabel lblIdSala;
	private JLabel lblNombreSala;
	private JLabel lblAforo;
	private JButton btnBuscar;
	private JButton btnLimpiar;
	
	public VistaMostrarSalaSesion() {
		initializeUI();
	}

	public VistaMostrarSalaSesion(CommandMostrarSalaSesion command) {
		this.command = command;
		initializeUI();
	}
	
	private void initializeUI() {
		setTitle("Mostrar Sala por Sesion");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// Main panel with GridLayout
		JPanel panelPrincipal = new JPanel(new GridLayout(5, 2, 10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		// ID Session input
		JLabel lblSesion = new JLabel("ID Sesion:");
		txtIdSesion = new JTextField(10);
		panelPrincipal.add(lblSesion);
		panelPrincipal.add(txtIdSesion);
		
		// Room ID (read-only)
		JLabel lblSalaId = new JLabel("ID Sala:");
		lblIdSala = new JLabel("-");
		lblIdSala.setForeground(Color.BLUE);
		panelPrincipal.add(lblSalaId);
		panelPrincipal.add(lblIdSala);
		
		// Room Name (read-only)
		JLabel lblNombre = new JLabel("Nombre Sala:");
		lblNombreSala = new JLabel("-");
		lblNombreSala.setForeground(Color.BLUE);
		panelPrincipal.add(lblNombre);
		panelPrincipal.add(lblNombreSala);
		
		// Room Capacity (read-only)
		JLabel lblCapacidad = new JLabel("Aforo:");
		lblAforo = new JLabel("-");
		lblAforo.setForeground(Color.BLUE);
		panelPrincipal.add(lblCapacidad);
		panelPrincipal.add(lblAforo);
		
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
				buscarSala();
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
	
	private void buscarSala() {
		String idSesionStr = txtIdSesion.getText().trim();
		
		if (idSesionStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor ingrese un ID de sesiÃÂ³n", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			int idSesion = Integer.parseInt(idSesionStr);
			Context ctx = command.execute(idSesion);
			
			if (ctx.isSuccess()) {
				TSala sala = (TSala) ctx.getData();
				lblIdSala.setText(String.valueOf(sala.getIdSala()));
				lblNombreSala.setText(sala.getNombreSala());
				lblAforo.setText(String.valueOf(sala.getAforo()));
			} else {
				JOptionPane.showMessageDialog(this, ctx.getMessage(), "InformaciÃÂ³n", JOptionPane.INFORMATION_MESSAGE);
				limpiar();
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El ID de sesiÃÂ³n debe ser un nÃÂºmero entero", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void limpiar() {
		txtIdSesion.setText("");
		lblIdSala.setText("-");
		lblNombreSala.setText("-");
		lblAforo.setText("-");
		txtIdSesion.requestFocus();
	}

	@Override
	public void update(Context context) {
		if (context != null && context.isSuccess() && context.getData() instanceof TSala) {
			TSala sala = (TSala) context.getData();
			lblIdSala.setText(String.valueOf(sala.getIdSala()));
			lblNombreSala.setText(sala.getNombreSala());
			lblAforo.setText(String.valueOf(sala.getAforo()));
		}
	}
}

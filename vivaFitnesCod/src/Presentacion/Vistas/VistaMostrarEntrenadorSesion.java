
package Presentacion.Vistas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import Controlador.CommandMostrarEntrenadorSesion;
import Controlador.Controller;
import Controlador.Context;
import Integracion.Entrenador.TEntrenador;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;


public class VistaMostrarEntrenadorSesion extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;
	private CommandMostrarEntrenadorSesion command;
	private JTextField txtIdSesion;
	private JLabel lblIdEntrenador;
	private JLabel lblNombre;
	private JLabel lblTelefono;
	private JLabel lblDNI;
	private JButton btnBuscar;
	private JButton btnLimpiar;
	
	public VistaMostrarEntrenadorSesion() {
		initializeUI();
	}

	public VistaMostrarEntrenadorSesion(CommandMostrarEntrenadorSesion command) {
		this.command = command;
		initializeUI();
	}
	
	private void initializeUI() {
		setTitle("Mostrar entrenador por sesion");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JPanel panelPrincipal = new JPanel(new GridLayout(6, 2, 10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		
		
		JLabel lblSesion = new JLabel("ID sesion:");
		txtIdSesion = new JTextField(10);
		panelPrincipal.add(lblSesion);
		panelPrincipal.add(txtIdSesion);
		
		
		JLabel lblTrainerId = new JLabel("ID Entrenador:");
		lblIdEntrenador = new JLabel("-");
		lblIdEntrenador.setForeground(Color.BLUE);
		panelPrincipal.add(lblTrainerId);
		panelPrincipal.add(lblIdEntrenador);
		
		
		JLabel lblNombreLabel = new JLabel("Nombre:");
		lblNombre = new JLabel("-");
		lblNombre.setForeground(Color.BLUE);
		panelPrincipal.add(lblNombreLabel);
		panelPrincipal.add(lblNombre);
		
		
		JLabel lblTelefonoLabel = new JLabel("Telefono:");
		lblTelefono = new JLabel("-");
		lblTelefono.setForeground(Color.BLUE);
		panelPrincipal.add(lblTelefonoLabel);
		panelPrincipal.add(lblTelefono);
		
		
		JLabel lblDNILabel = new JLabel("DNI:");
		lblDNI = new JLabel("-");
		lblDNI.setForeground(Color.BLUE);
		panelPrincipal.add(lblDNILabel);
		panelPrincipal.add(lblDNI);
		
		
		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		btnBuscar = new JButton("Buscar");
		btnLimpiar = new JButton("Limpiar");
		panelBotones.add(btnBuscar);
		panelBotones.add(btnLimpiar);
		panelPrincipal.add(panelBotones);
		
		
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
		
		
		add(panelPrincipal);
	}
	
	private void buscarEntrenador() {
		String idSesionStr = txtIdSesion.getText().trim();
		
		if (idSesionStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor ingrese un ID de sesion", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			int idSesion = Integer.parseInt(idSesionStr);
			Context ctx = command != null
					? command.execute(idSesion)
					: Controller.getInstance().action(new Context(Evento.MOSTRAR_ENTRENADOR_SESION, idSesion));
			
			if (ctx.isSuccess()) {
				TEntrenador entrenador = (TEntrenador) ctx.getData();
				lblIdEntrenador.setText(String.valueOf(entrenador.get_id()));
				lblNombre.setText(entrenador.get_nombre());
				lblTelefono.setText(entrenador.get_telefono());
				lblDNI.setText(entrenador.get_dni());
			} else {
				JOptionPane.showMessageDialog(this, ctx.getMessage(), "Informacion", JOptionPane.INFORMATION_MESSAGE);
				limpiar();
			}
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "El ID de sesion debe ser un numero entero", "Error", JOptionPane.ERROR_MESSAGE);
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

	@Override
	public void update(Context context) {
		if (context != null && context.isSuccess() && context.getData() instanceof TEntrenador) {
			TEntrenador e = (TEntrenador) context.getData();
			lblIdEntrenador.setText(String.valueOf(e.get_id()));
			lblNombre.setText(e.get_nombre());
			lblTelefono.setText(e.get_telefono());
			lblDNI.setText(e.get_dni());
		}
	}
}

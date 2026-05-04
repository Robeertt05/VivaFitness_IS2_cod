package Presentacion.Sala;

import javax.swing.*;
import java.awt.*;

import Controlador.CommandMostrarSalaSesion;
import Controlador.Controller;
import Controlador.Context;
import Integracion.Sala.TSala;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

/**
 * Vista para mostrar los datos de la sala asociada a una sesion.
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
		setTitle("Mostrar sala por sesion");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panelPrincipal = new JPanel(new GridLayout(5, 2, 10, 10));
		panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		JLabel lblSesion = new JLabel("ID sesion:");
		txtIdSesion = new JTextField(10);
		panelPrincipal.add(lblSesion);
		panelPrincipal.add(txtIdSesion);

		JLabel lblSalaId = new JLabel("ID sala:");
		lblIdSala = new JLabel("-");
		lblIdSala.setForeground(Color.BLUE);
		panelPrincipal.add(lblSalaId);
		panelPrincipal.add(lblIdSala);

		JLabel lblNombre = new JLabel("Nombre sala:");
		lblNombreSala = new JLabel("-");
		lblNombreSala.setForeground(Color.BLUE);
		panelPrincipal.add(lblNombre);
		panelPrincipal.add(lblNombreSala);

		JLabel lblCapacidad = new JLabel("Aforo:");
		lblAforo = new JLabel("-");
		lblAforo.setForeground(Color.BLUE);
		panelPrincipal.add(lblCapacidad);
		panelPrincipal.add(lblAforo);

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		btnBuscar = new JButton("Buscar");
		btnLimpiar = new JButton("Limpiar");
		panelBotones.add(btnBuscar);
		panelBotones.add(btnLimpiar);
		panelPrincipal.add(panelBotones);

		btnBuscar.addActionListener(e -> buscarSala());
		btnLimpiar.addActionListener(e -> limpiar());

		add(panelPrincipal);
	}

	private void buscarSala() {
		String idSesionStr = txtIdSesion.getText().trim();

		if (idSesionStr.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Por favor ingrese un ID de sesion", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			int idSesion = Integer.parseInt(idSesionStr);
			Context ctx = command != null
					? command.execute(idSesion)
					: Controller.getInstance().action(new Context(Evento.MOSTRAR_SALA_SESION, idSesion));

			if (ctx.isSuccess()) {
				TSala sala = (TSala) ctx.getData();
				lblIdSala.setText(String.valueOf(sala.getIdSala()));
				lblNombreSala.setText(sala.getNombreSala());
				lblAforo.setText(String.valueOf(sala.getAforo()));
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

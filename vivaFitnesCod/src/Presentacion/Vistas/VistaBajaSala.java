/**
 * Vista para dar de baja a una Sala.
 */
package Presentacion.Vistas;

import javax.swing.*;
import java.awt.*;
import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.FactoriaPresentacion.Evento;
import Controlador.Context;
import Controlador.Controller;

public class VistaBajaSala extends JFrame implements IGUI {

	private JPanel panel;
	private JLabel lblId;
	private JTextField txtId;
	private JButton btnAceptar, btnCancelar;

	public VistaBajaSala() {
		setTitle("Baja Sala - VivaFitness");
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel(new GridLayout(2, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		lblId = new JLabel("ID Sala:");
		txtId = new JTextField();
		btnAceptar = new JButton("Dar de Baja");
		btnCancelar = new JButton("Cancelar");

		btnAceptar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtId.getText().trim());
				Context ctx = new Context(Evento.BAJA_SALA, id);
				Context res = Controller.getInstance().action(ctx);
				update(res);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Introduzca un ID valido.",
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnCancelar.addActionListener(e -> dispose());

		panel.add(lblId);      panel.add(txtId);
		panel.add(btnAceptar); panel.add(btnCancelar);
		add(panel);
	}

	@Override
	public void update(Context context) {
		if (context == null) return;

		Evento evento = context.getEvento();

		if (evento == Evento.RES_BAJA_SALA_OK) {
			JOptionPane.showMessageDialog(this,
				"Sala eliminada correctamente.",
				"Exito", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} 
		else if (evento == Evento.RES_BAJA_SALA_KO) {
			JOptionPane.showMessageDialog(this,
				"Error al eliminar la sala. La sala no existe, tiene sesiones activas o ya esta inactiva.",
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

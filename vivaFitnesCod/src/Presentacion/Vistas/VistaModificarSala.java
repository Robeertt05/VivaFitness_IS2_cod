
package Presentacion.Vistas;

import javax.swing.*;
import java.awt.*;
import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.FactoriaPresentacion.Evento;
import Controlador.Context;
import Controlador.Controller;
import Integracion.Sala.TSala;

public class VistaModificarSala extends JFrame implements IGUI {

	private JPanel panel;
	private JLabel lblId, lblNombre, lblAforo;
	private JTextField txtId, txtNombre;
	private JSpinner spinAforo;
	private JButton btnAceptar, btnCancelar;

	public VistaModificarSala() {
		setTitle("Modificar Sala - VivaFitness");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel(new GridLayout(4, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		lblId = new JLabel("ID Sala:");
		txtId = new JTextField();
		lblNombre = new JLabel("Nuevo Nombre:");
		txtNombre = new JTextField();
		lblAforo = new JLabel("Nuevo Aforo:");
		spinAforo = new JSpinner();

		btnAceptar = new JButton("Modificar");
		btnCancelar = new JButton("Cancelar");

		btnAceptar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtId.getText().trim());

				TSala sala = new TSala();
				sala.setIdSala(id);
				sala.setNombreSala(txtNombre.getText().trim());
				sala.setAforo((Integer) spinAforo.getValue());
				sala.setActivo(1);

				Context ctx = new Context(Evento.MODIFICAR_SALA, sala);
				Context res = Controller.getInstance().action(ctx);
				update(res);

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this,
					"Introduzca un ID valido.",
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnCancelar.addActionListener(e -> dispose());

		panel.add(lblId);       panel.add(txtId);
		panel.add(lblNombre);   panel.add(txtNombre);
		panel.add(lblAforo);    panel.add(spinAforo);
		panel.add(btnAceptar);  panel.add(btnCancelar);
		add(panel);
	}

	@Override
	public void update(Context context) {
		if (context == null) return;

		if (context.isSuccess()) {
			JOptionPane.showMessageDialog(this,
				context.getMessage() != null ? context.getMessage() : "Sala modificada correctamente.",
				"Exito", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} 
		else {
			JOptionPane.showMessageDialog(this,
				context.getMessage() != null ? context.getMessage() : "Error al modificar la sala.",
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

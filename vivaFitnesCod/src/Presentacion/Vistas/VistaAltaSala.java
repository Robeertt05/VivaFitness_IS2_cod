
package Presentacion.Vistas;

import javax.swing.*;
import java.awt.*;
import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.FactoriaPresentacion.Evento;
import Controlador.Context;
import Controlador.Controller;
import Integracion.Sala.TSala;

public class VistaAltaSala extends JFrame implements IGUI {

	private JPanel panel;
	private JLabel lblNombre, lblAforo;
	private JTextField txtNombre;
	private JSpinner spinAforo;
	private JButton btnAceptar, btnCancelar;

	public VistaAltaSala() {
		setTitle("Alta Sala - VivaFitness");
		setSize(400, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel(new GridLayout(3, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		lblNombre = new JLabel("Nombre Sala:");
		txtNombre = new JTextField();
		lblAforo = new JLabel("Aforo:");
		spinAforo = new JSpinner();

		btnAceptar = new JButton("Crear");
		btnCancelar = new JButton("Cancelar");

		btnAceptar.addActionListener(e -> {
			TSala sala = new TSala();
			sala.setNombreSala(txtNombre.getText().trim());
			sala.setAforo((Integer) spinAforo.getValue());
			sala.setActivo(1);

			Context ctx = new Context(Evento.ALTA_SALA, sala);
			Context res = Controller.getInstance().action(ctx);
			update(res);
		});

		btnCancelar.addActionListener(e -> dispose());

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
				context.getMessage() != null ? context.getMessage() : "Sala creada correctamente.",
				"Exito", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} 
		else {
			JOptionPane.showMessageDialog(this,
				context.getMessage() != null ? context.getMessage() : "Error al crear la sala. Datos invalidos.",
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

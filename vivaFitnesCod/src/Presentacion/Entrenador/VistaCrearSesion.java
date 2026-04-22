/**
 * Vista para crear una sesion de entrenamiento.
 */
package Presentacion.Entrenador;

import javax.swing.*;
import java.awt.*;
import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.FactoriaPresentacion.Evento;
import Controlador.Context;
import Controlador.Controller;
import Negocio.entrenador.TEntrenador;

public class VistaCrearSesion extends JFrame implements IGUI {

	private JPanel panel;
	private JLabel lblIdEntrenador;
	private JTextField txtIdEntrenador;
	private JButton btnCrear, btnCancelar;

	public VistaCrearSesion() {
		setTitle("Crear Sesion - VivaFitness");
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel(new GridLayout(2, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		lblIdEntrenador = new JLabel("ID Entrenador:");
		txtIdEntrenador = new JTextField();
		btnCrear = new JButton("Crear Sesion");
		btnCancelar = new JButton("Cancelar");

		btnCrear.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtIdEntrenador.getText().trim());
				TEntrenador t = new TEntrenador();
				t.set_id(id);
				Context ctx = new Context(Evento.CREAR_SESION, t);
				Context res = Controller.getInstance().action(ctx);
				update(res);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Introduzca un ID valido.",
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnCancelar.addActionListener(e -> dispose());

		panel.add(lblIdEntrenador); panel.add(txtIdEntrenador);
		panel.add(btnCrear);        panel.add(btnCancelar);
		add(panel);
	}

	@Override
	public void update(Context context) {
		if (context == null) return;
		int evento = context.getEvento();
		if (evento == Evento.RES_CREAR_SESION_OK) {
			JOptionPane.showMessageDialog(this,
				"Sesion creada correctamente.",
				"Exito", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} else if (evento == Evento.RES_CREAR_SESION_KO) {
			JOptionPane.showMessageDialog(this,
				"Error al crear la sesion. El entrenador no existe o esta inactivo.",
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

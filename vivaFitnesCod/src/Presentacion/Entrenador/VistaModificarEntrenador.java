/**
 * Vista para modificar los datos de un Entrenador.
 */
package Presentacion.Entrenador;

import javax.swing.*;
import java.awt.*;
import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.FactoriaPresentacion.Evento;
import Controlador.Context;
import Controlador.Controller;
import Negocio.entrenador.TEntrenador;

public class VistaModificarEntrenador extends JFrame implements IGUI {

	private JPanel panel;
	private JLabel lblId, lblDni, lblNombre, lblTelefono;
	private JTextField txtId, txtDni, txtNombre, txtTelefono;
	private JButton btnAceptar, btnCancelar;

	public VistaModificarEntrenador() {
		setTitle("Modificar Entrenador - VivaFitness");
		setSize(400, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel(new GridLayout(5, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		lblId = new JLabel("ID Entrenador:");
		txtId = new JTextField();
		lblDni = new JLabel("Nuevo DNI:");
		txtDni = new JTextField();
		lblNombre = new JLabel("Nuevo Nombre:");
		txtNombre = new JTextField();
		lblTelefono = new JLabel("Nuevo Telefono:");
		txtTelefono = new JTextField();

		btnAceptar = new JButton("Modificar");
		btnCancelar = new JButton("Cancelar");

		btnAceptar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtId.getText().trim());

				TEntrenador t = new TEntrenador();
				t.set_id(id);
				t.set_dni(txtDni.getText().trim());
				t.set_nombre(txtNombre.getText().trim());
				t.set_telefono(txtTelefono.getText().trim());

				Context ctx = new Context(Evento.MODIFICAR_ENTRENADOR, t);
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
		panel.add(lblDni);      panel.add(txtDni);
		panel.add(lblNombre);   panel.add(txtNombre);
		panel.add(lblTelefono); panel.add(txtTelefono);
		panel.add(btnAceptar);  panel.add(btnCancelar);
		add(panel);
	}

	@Override
	public void update(Context context) {
		if (context == null) return;

		int evento = context.getEvento();

		if (evento == Evento.RES_MODIFICAR_ENTRENADOR_OK) {
			JOptionPane.showMessageDialog(this,
				"Entrenador modificado correctamente.",
				"Exito", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} 
		else if (evento == Evento.RES_MODIFICAR_ENTRENADOR_KO) {
			JOptionPane.showMessageDialog(this,
				"Error al modificar el entrenador.",
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
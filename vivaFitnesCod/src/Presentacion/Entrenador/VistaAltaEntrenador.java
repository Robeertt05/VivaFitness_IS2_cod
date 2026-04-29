/**
 * Vista para dar de alta a un nuevo Entrenador.
 */
package Presentacion.Entrenador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.FactoriaPresentacion.Evento;
import Controlador.Context;
import Controlador.Controller;
import Negocio.entrenador.TEntrenador;

public class VistaAltaEntrenador extends JFrame implements IGUI {

	private JPanel panel;
	private JLabel lblDni, lblNombre, lblTelefono;
	private JTextField txtDni, txtNombre, txtTelefono;
	private JButton btnAceptar, btnCancelar;

	public VistaAltaEntrenador() {
		setTitle("Alta Entrenador - VivaFitness");
		setSize(400, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel(new GridLayout(4, 2, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		lblDni = new JLabel("DNI:");
		txtDni = new JTextField();
		lblNombre = new JLabel("Nombre:");
		txtNombre = new JTextField();
		lblTelefono = new JLabel("Telefono:");
		txtTelefono = new JTextField();

		btnAceptar = new JButton("Aceptar");
		btnCancelar = new JButton("Cancelar");

		btnAceptar.addActionListener(e -> {
			TEntrenador t = new TEntrenador();
			t.set_dniEntrenador(txtDni.getText().trim());
			t.set_nombreEntrenador(txtNombre.getText().trim());
			t.set_telefonoEntrenador(txtTelefono.getText().trim());
			Context ctx = new Context(Evento.ALTA_ENTRENADOR, t);
			Context res = Controller.getInstance().action(ctx);
			update(res);
		});

		btnCancelar.addActionListener(e -> dispose());

		panel.add(lblDni);     panel.add(txtDni);
		panel.add(lblNombre);  panel.add(txtNombre);
		panel.add(lblTelefono); panel.add(txtTelefono);
		panel.add(btnAceptar); panel.add(btnCancelar);
		add(panel);
	}

	@Override
	public void update(Context context) {
		if (context == null) return;
		int evento = context.getEvento();
		if (evento == Evento.RES_ALTA_ENTRENADOR_OK) {
			JOptionPane.showMessageDialog(this,
				"Entrenador dado de alta correctamente. ID: " + context.getObjeto(),
				"Exito", JOptionPane.INFORMATION_MESSAGE);
			dispose();
		} else if (evento == Evento.RES_ALTA_ENTRENADOR_KO) {
			JOptionPane.showMessageDialog(this,
				"Error al dar de alta al entrenador. Compruebe los datos.",
				"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

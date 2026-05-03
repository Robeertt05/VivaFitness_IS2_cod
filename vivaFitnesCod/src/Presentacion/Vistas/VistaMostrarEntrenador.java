/**
 * Vista para mostrar los datos de un Entrenador por ID.
 */
package Presentacion.Vistas;

import javax.swing.*;
import java.awt.*;
import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.FactoriaPresentacion.Evento;
import Controlador.Context;
import Controlador.Controller;
import Negocio.entrenador.TEntrenador;

public class VistaMostrarEntrenador extends JFrame implements IGUI {

	private JPanel panel;
	private JLabel lblId, lblResultado;
	private JTextField txtId;
	private JButton btnBuscar, btnCancelar;
	private JTextArea txtResultado;

	public VistaMostrarEntrenador() {
		setTitle("Mostrar Entrenador - VivaFitness");
		setSize(450, 350);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel panelSuperior = new JPanel(new GridLayout(1, 3, 10, 10));
		lblId = new JLabel("ID Entrenador:");
		txtId = new JTextField();
		btnBuscar = new JButton("Buscar");

		panelSuperior.add(lblId);
		panelSuperior.add(txtId);
		panelSuperior.add(btnBuscar);

		txtResultado = new JTextArea();
		txtResultado.setEditable(false);

		btnCancelar = new JButton("Cerrar");
		btnCancelar.addActionListener(e -> dispose());

		btnBuscar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtId.getText().trim());
				Context ctx = new Context(Evento.MOSTRAR_ENTRENADOR, id);
				Context res = Controller.getInstance().action(ctx);
				update(res);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this,
					"Introduzca un ID valido.",
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		panel.add(panelSuperior, BorderLayout.NORTH);
		panel.add(new JScrollPane(txtResultado), BorderLayout.CENTER);
		panel.add(btnCancelar, BorderLayout.SOUTH);
		add(panel);
	}

	@Override
	public void update(Context context) {
		if (context == null) return;

		Evento evento = context.getEvento();

		if (evento == Evento.RES_MOSTRAR_ENTRENADOR_OK) {
			TEntrenador t = (TEntrenador) context.getObjeto();

			txtResultado.setText(
				"ID: " + t.get_id() + "\n" +
				"DNI: " + t.get_dni() + "\n" +
				"Nombre: " + t.get_nombre() + "\n" +
				"Telefono: " + t.get_telefono() + "\n" +
				"Activo: " + (t.get_activo() == 1 ? "Si" : "No")
			);
		} 
		else if (evento == Evento.RES_MOSTRAR_ENTRENADOR_KO) {
			txtResultado.setText("No se encontro el entrenador con ese ID.");
		}
	}
}

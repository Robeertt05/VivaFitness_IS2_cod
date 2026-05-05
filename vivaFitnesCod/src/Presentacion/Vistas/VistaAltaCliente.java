package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Cliente.TCliente;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VistaAltaCliente extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private final JTextField dni = new JTextField();
	private final JTextField nombre = new JTextField();
	private final JTextField telefono = new JTextField();
	private final JTextField correo = new JTextField();

	public VistaAltaCliente() {
		setTitle("Alta cliente");
		setSize(460, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
		panel.add(new JLabel("DNI:")); panel.add(dni);
		panel.add(new JLabel("Nombre:")); panel.add(nombre);
		panel.add(new JLabel("Telefono:")); panel.add(telefono);
		panel.add(new JLabel("Correo:")); panel.add(correo);
		JButton guardar = new JButton("Guardar");
		JButton limpiar = new JButton("Limpiar");
		guardar.addActionListener(e -> guardar());
		limpiar.addActionListener(e -> limpiar());
		panel.add(guardar); panel.add(limpiar);
		setContentPane(panel);
	}

	private void guardar() {
		TCliente cliente = new TCliente(dni.getText().trim(), nombre.getText().trim(),
				telefono.getText().trim(), correo.getText().trim());
		update(Controller.getInstance().action(new Context(Evento.ALTA_CLIENTE, cliente)));
	}

	private void limpiar() {
		dni.setText(""); nombre.setText(""); telefono.setText(""); correo.setText("");
	}

	@Override
	public void update(Context context) {
		JOptionPane.showMessageDialog(this, context.getMessage());
		if (context.isSuccess()) {
			limpiar();
		}
	}
}

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

public class VistaModificarCliente extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private final JTextField id = new JTextField();
	private final JTextField dni = new JTextField();
	private final JTextField nombre = new JTextField();
	private final JTextField telefono = new JTextField();
	private final JTextField correo = new JTextField();

	public VistaModificarCliente() {
		setTitle("Modificar cliente");
		setSize(480, 240);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
		panel.add(new JLabel("ID:")); panel.add(id);
		panel.add(new JLabel("DNI:")); panel.add(dni);
		panel.add(new JLabel("Nombre:")); panel.add(nombre);
		panel.add(new JLabel("Telefono:")); panel.add(telefono);
		panel.add(new JLabel("Correo:")); panel.add(correo);
		JButton guardar = new JButton("Modificar");
		JButton limpiar = new JButton("Limpiar");
		guardar.addActionListener(e -> modificar());
		limpiar.addActionListener(e -> limpiar());
		panel.add(guardar); panel.add(limpiar);
		setContentPane(panel);
	}

	private void modificar() {
		try {
			TCliente cliente = new TCliente(Integer.parseInt(id.getText().trim()), dni.getText().trim(),
					nombre.getText().trim(), telefono.getText().trim(), correo.getText().trim(), 1);
			update(Controller.getInstance().action(new Context(Evento.MODIFICAR_CLIENTE, cliente)));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El ID debe ser numerico.");
		}
	}

	private void limpiar() {
		id.setText(""); dni.setText(""); nombre.setText(""); telefono.setText(""); correo.setText("");
	}

	@Override
	public void update(Context context) {
		JOptionPane.showMessageDialog(this, context.getMessage());
	}
}

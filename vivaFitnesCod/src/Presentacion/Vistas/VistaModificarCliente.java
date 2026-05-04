package Presentacion.Vistas;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Cliente.TCliente;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaModificarCliente extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTextField id = new JTextField();
	private JTextField dni = new JTextField();
	private JTextField nombre = new JTextField();
	private JTextField telefono = new JTextField();
	private JTextField correo = new JTextField();
	private JCheckBox activo = new JCheckBox("Activo", true);

	public VistaModificarCliente() {
		setTitle("Modificar cliente");
		setSize(480, 320);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new GridLayout(7, 2, 8, 8));
		panel.add(new JLabel("ID:")); panel.add(id);
		panel.add(new JLabel("DNI:")); panel.add(dni);
		panel.add(new JLabel("Nombre:")); panel.add(nombre);
		panel.add(new JLabel("Telefono:")); panel.add(telefono);
		panel.add(new JLabel("Correo:")); panel.add(correo);
		panel.add(new JLabel("Estado:")); panel.add(activo);
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
					nombre.getText().trim(), telefono.getText().trim(), correo.getText().trim(), activo.isSelected());
			update(Controller.getInstance().action(new Context(Evento.MODIFICAR_CLIENTE, cliente)));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El ID debe ser numerico.");
		}
	}

	private void limpiar() {
		id.setText(""); dni.setText(""); nombre.setText(""); telefono.setText(""); correo.setText(""); activo.setSelected(true);
	}

	@Override
	public void update(Context context) {
		JOptionPane.showMessageDialog(this, context.getMessage());
	}
}

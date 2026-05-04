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

public class VistaAltaCliente extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTextField dni = new JTextField();
	private JTextField nombre = new JTextField();
	private JTextField telefono = new JTextField();
	private JTextField correo = new JTextField();
	private JCheckBox activo = new JCheckBox("Activo", true);

	public VistaAltaCliente() {
		setTitle("Alta cliente");
		setSize(460, 280);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new GridLayout(6, 2, 8, 8));
		panel.add(new JLabel("DNI:")); panel.add(dni);
		panel.add(new JLabel("Nombre:")); panel.add(nombre);
		panel.add(new JLabel("Telefono:")); panel.add(telefono);
		panel.add(new JLabel("Correo:")); panel.add(correo);
		panel.add(new JLabel("Estado:")); panel.add(activo);
		JButton guardar = new JButton("Guardar");
		JButton limpiar = new JButton("Limpiar");
		guardar.addActionListener(e -> guardar());
		limpiar.addActionListener(e -> limpiar());
		panel.add(guardar); panel.add(limpiar);
		setContentPane(panel);
	}

	private void guardar() {
		TCliente cliente = new TCliente(0, dni.getText().trim(), nombre.getText().trim(),
				telefono.getText().trim(), correo.getText().trim(), activo.isSelected());
		update(Controller.getInstance().action(new Context(Evento.ALTA_CLIENTE, cliente)));
	}

	private void limpiar() {
		dni.setText(""); nombre.setText(""); telefono.setText(""); correo.setText(""); activo.setSelected(true);
	}

	@Override
	public void update(Context context) {
		JOptionPane.showMessageDialog(this, context.getMessage());
		if (context.isSuccess()) {
			limpiar();
		}
	}
}

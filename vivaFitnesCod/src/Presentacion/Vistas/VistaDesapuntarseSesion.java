package Presentacion.Vistas;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controlador.Context;
import Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaDesapuntarseSesion extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTextField idCliente = new JTextField();
	private JTextField idSesion = new JTextField();

	public VistaDesapuntarseSesion() {
		setTitle("Desapuntarse de sesion");
		setSize(380, 170);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new GridLayout(3, 2, 8, 8));
		panel.add(new JLabel("ID cliente:")); panel.add(idCliente);
		panel.add(new JLabel("ID sesion:")); panel.add(idSesion);
		JButton aceptar = new JButton("Desapuntar");
		aceptar.addActionListener(e -> desapuntar());
		panel.add(aceptar);
		setContentPane(panel);
	}

	private void desapuntar() {
		try {
			Object[] datos = { Integer.parseInt(idCliente.getText().trim()), Integer.parseInt(idSesion.getText().trim()) };
			update(Controller.getInstance().action(new Context(Evento.DESAPUNTARSE_SESION, datos)));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Los IDs deben ser numericos.");
		}
	}

	@Override
	public void update(Context context) {
		JOptionPane.showMessageDialog(this, context.getMessage());
	}
}

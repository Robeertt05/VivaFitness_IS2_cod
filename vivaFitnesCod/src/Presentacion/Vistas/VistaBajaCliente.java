package Presentacion.Vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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

public class VistaBajaCliente extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTextField id = new JTextField(10);

	public VistaBajaCliente() {
		setTitle("Baja cliente");
		setSize(360, 130);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new FlowLayout());
		panel.add(new JLabel("ID cliente:"));
		panel.add(id);
		JButton baja = new JButton("Dar de baja");
		baja.addActionListener(e -> baja());
		panel.add(baja);
		add(panel, BorderLayout.CENTER);
	}

	private void baja() {
		try {
			update(Controller.getInstance().action(new Context(Evento.BAJA_CLIENTE, Integer.parseInt(id.getText().trim()))));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El ID debe ser numerico.");
		}
	}

	@Override
	public void update(Context context) {
		JOptionPane.showMessageDialog(this, context.getMessage());
	}
}

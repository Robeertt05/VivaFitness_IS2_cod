package Presentacion.Vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Cliente.TCliente;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaMostrarCliente extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTextField id = new JTextField(10);
	private JTextArea resultado = new JTextArea();

	public VistaMostrarCliente() {
		setTitle("Mostrar cliente");
		setSize(520, 320);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel top = new JPanel(new FlowLayout());
		top.add(new JLabel("ID cliente:"));
		top.add(id);
		JButton buscar = new JButton("Buscar");
		buscar.addActionListener(e -> buscar());
		top.add(buscar);
		resultado.setEditable(false);
		add(top, BorderLayout.NORTH);
		add(new JScrollPane(resultado), BorderLayout.CENTER);
	}

	private void buscar() {
		try {
			update(Controller.getInstance().action(new Context(Evento.MOSTRAR_CLIENTE, Integer.parseInt(id.getText().trim()))));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El ID debe ser numerico.");
		}
	}

	@Override
	public void update(Context context) {
		if (context.isSuccess() && context.getObjeto() instanceof TCliente) {
			resultado.setText(((TCliente) context.getObjeto()).toString());
		} else {
			resultado.setText("");
			JOptionPane.showMessageDialog(this, context.getMessage());
		}
	}
}

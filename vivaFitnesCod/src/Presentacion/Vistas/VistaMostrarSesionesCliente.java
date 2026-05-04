package Presentacion.Vistas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Set;

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
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaMostrarSesionesCliente extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTextField idCliente = new JTextField(10);
	private JTextArea resultado = new JTextArea();

	public VistaMostrarSesionesCliente() {
		setTitle("Sesiones del cliente");
		setSize(650, 420);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel top = new JPanel(new FlowLayout());
		top.add(new JLabel("ID cliente:"));
		top.add(idCliente);
		JButton buscar = new JButton("Buscar");
		buscar.addActionListener(e -> buscar());
		top.add(buscar);
		resultado.setEditable(false);
		add(top, BorderLayout.NORTH);
		add(new JScrollPane(resultado), BorderLayout.CENTER);
	}

	private void buscar() {
		try {
			update(Controller.getInstance().action(new Context(Evento.MOSTRAR_SESIONES_CLIENTE,
					Integer.parseInt(idCliente.getText().trim()))));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "El ID debe ser numerico.");
		}
	}

	@Override
	public void update(Context context) {
		if (context.isSuccess() && context.getObjeto() instanceof Set) {
			StringBuilder sb = new StringBuilder();
			for (Object sesion : (Set<?>) context.getObjeto()) {
				sb.append(sesion).append("\n\n");
			}
			resultado.setText(sb.length() == 0 ? "El cliente no tiene sesiones." : sb.toString());
		} else {
			JOptionPane.showMessageDialog(this, context.getMessage());
		}
	}
}

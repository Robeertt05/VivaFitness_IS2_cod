package Presentacion.Vistas;

import java.awt.BorderLayout;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Controlador.Context;
import Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaMostrarClientes extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTextArea resultado = new JTextArea();

	public VistaMostrarClientes() {
		setTitle("Mostrar clientes");
		setSize(640, 420);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JButton actualizar = new JButton("Actualizar");
		actualizar.addActionListener(e -> cargar());
		JPanel top = new JPanel();
		top.add(actualizar);
		resultado.setEditable(false);
		add(top, BorderLayout.NORTH);
		add(new JScrollPane(resultado), BorderLayout.CENTER);
		cargar();
	}

	private void cargar() {
		update(Controller.getInstance().action(new Context(Evento.MOSTRAR_CLIENTES, null)));
	}

	@Override
	public void update(Context context) {
		if (context.isSuccess() && context.getObjeto() instanceof Set) {
			StringBuilder sb = new StringBuilder();
			for (Object cliente : (Set<?>) context.getObjeto()) {
				sb.append(cliente).append("\n\n");
			}
			resultado.setText(sb.length() == 0 ? "No hay clientes registrados." : sb.toString());
		} else {
			JOptionPane.showMessageDialog(this, context.getMessage());
		}
	}
}

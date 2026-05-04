package Presentacion.Vistas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
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
import Integracion.Sesion.TClienteSesion;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaApuntarseSesion extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTextField idCliente = new JTextField();
	private JTextField idSesion = new JTextField();
	private JTextField fecha = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
	private JTextField hora = new JTextField(new SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
	private JTextArea sesiones = new JTextArea();

	public VistaApuntarseSesion() {
		setTitle("Apuntarse a sesion");
		setSize(760, 520);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
		panel.add(new JLabel("ID cliente:")); panel.add(idCliente);
		panel.add(new JLabel("ID sesion:")); panel.add(idSesion);
		panel.add(new JLabel("Fecha apunte (yyyy-MM-dd):")); panel.add(fecha);
		panel.add(new JLabel("Hora apunte (HH:mm:ss):")); panel.add(hora);
		JButton apuntar = new JButton("Apuntar");
		JButton limpiar = new JButton("Limpiar");
		JButton refrescar = new JButton("Refrescar sesiones");
		apuntar.addActionListener(e -> apuntar());
		limpiar.addActionListener(e -> limpiar());
		refrescar.addActionListener(e -> cargarSesiones());
		panel.add(apuntar); panel.add(limpiar);
		JPanel root = new JPanel(new BorderLayout(8, 8));
		sesiones.setEditable(false);
		root.add(panel, BorderLayout.NORTH);
		root.add(new JScrollPane(sesiones), BorderLayout.CENTER);
		root.add(refrescar, BorderLayout.SOUTH);
		setContentPane(root);
		cargarSesiones();
	}

	private void apuntar() {
		try {
			TClienteSesion datos = new TClienteSesion();
			datos.setIdCliente(Integer.parseInt(idCliente.getText().trim()));
			datos.setIdSesion(Integer.parseInt(idSesion.getText().trim()));
			datos.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(fecha.getText().trim()));
			datos.setHora(normalizarHora(hora.getText().trim()));
			update(Controller.getInstance().action(new Context(Evento.APUNTARSE_SESION, datos)));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Revise ID, fecha y hora. Formatos esperados: yyyy-MM-dd y HH:mm:ss.");
		}
	}

	private String normalizarHora(String value) {
		return value.length() == 5 ? value + ":00" : value;
	}

	private void limpiar() {
		idCliente.setText(""); idSesion.setText("");
	}

	private void cargarSesiones() {
		Context context = Controller.getInstance().action(new Context(Evento.MOSTRAR_SESIONES_DISPONIBLES_CLIENTE, null));
		if (context.isSuccess() && context.getObjeto() instanceof Set) {
			StringBuilder sb = new StringBuilder();
			for (Object sesion : (Set<?>) context.getObjeto()) {
				sb.append(sesion).append("\n\n");
			}
			sesiones.setText(sb.length() == 0 ? "No hay sesiones activas disponibles." : sb.toString());
		} else {
			sesiones.setText(context.getMessage());
		}
	}

	@Override
	public void update(Context context) {
		JOptionPane.showMessageDialog(this, context.getMessage());
	}
}

package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Sesion.TClienteSesion;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VistaApuntarseSesion extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTextField idCliente = new JTextField();
	private JTextField idSesion = new JTextField();
	private JTextField fecha = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
	private JTextField hora = new JTextField(new SimpleDateFormat("HH:mm").format(new java.util.Date()));

	public VistaApuntarseSesion() {
		setTitle("Apuntarse a sesion");
		setSize(400, 250);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel(new GridLayout(5, 2, 8, 8));
		panel.add(new JLabel("ID cliente:"));
		panel.add(idCliente);
		panel.add(new JLabel("ID sesion:"));
		panel.add(idSesion);
		panel.add(new JLabel("Fecha apunte (yyyy-MM-dd):"));
		panel.add(fecha);
		panel.add(new JLabel("Hora apunte (HH:mm):"));
		panel.add(hora);
		JButton apuntar = new JButton("Apuntar");
		JButton limpiar = new JButton("Limpiar");
		apuntar.addActionListener(e -> apuntar());
		limpiar.addActionListener(e -> limpiar());
		panel.add(apuntar);
		panel.add(limpiar);
		setContentPane(panel);
	}

	private void apuntar() {
		try {
			TClienteSesion datos = new TClienteSesion();
			datos.setIdCliente(Integer.parseInt(idCliente.getText().trim()));
			datos.setIdSesion(Integer.parseInt(idSesion.getText().trim()));
			datos.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(fecha.getText().trim()));
			datos.setHora(normalizarHora(hora.getText().trim()));
			update(Controller.getInstance().action(new Context(Evento.APUNTARSE_SESION, datos)));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Error en los datos: ID cliente e ID sesion deben ser numeros.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (java.text.ParseException e) {
			JOptionPane.showMessageDialog(this, "Formato de fecha/hora incorrecto. Use: yyyy-MM-dd para fecha y HH:mm para hora.", "Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error inesperado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private String normalizarHora(String value) {
		return value.length() == 5 ? value + ":00" : value;
	}

	private void limpiar() {
		idCliente.setText("");
		idSesion.setText("");
		fecha.setText(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
		hora.setText(new SimpleDateFormat("HH:mm").format(new java.util.Date()));
	}

	@Override
	public void update(Context context) {
		JOptionPane.showMessageDialog(this, context.getMessage());
	}
}

package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Sesion.TClienteSesion;
import Integracion.Sesion.TSesion;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VistaApuntarseSesion extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTextField idCliente = new JTextField();
	private JTextField idSesion = new JTextField();
	private JTextField fecha = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date()));
	private JTextField hora = new JTextField(new SimpleDateFormat("HH:mm").format(new java.util.Date()));
	private JTable tablaSesiones;
	private DefaultTableModel modeloTabla;

	public VistaApuntarseSesion() {
		setTitle("Apuntarse a sesion");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JPanel panelSuperior = new JPanel(new GridLayout(3, 2, 8, 8));
		panelSuperior.add(new JLabel("ID cliente:"));
		panelSuperior.add(idCliente);
		panelSuperior.add(new JLabel("ID sesion:"));
		panelSuperior.add(idSesion);
		panelSuperior.add(new JLabel("Fecha apunte (yyyy-MM-dd):"));
		panelSuperior.add(fecha);
		
		
		JPanel panelBotones = new JPanel(new GridLayout(1, 3, 8, 8));
		JButton apuntar = new JButton("Apuntar");
		JButton limpiar = new JButton("Limpiar");
		JButton recargar = new JButton("Recargar sesiones");
		apuntar.addActionListener(e -> apuntar());
		limpiar.addActionListener(e -> limpiar());
		recargar.addActionListener(e -> cargarSesionesDisponibles());
		panelBotones.add(apuntar);
		panelBotones.add(limpiar);
		panelBotones.add(recargar);
		
		
		JPanel panelHora = new JPanel(new GridLayout(1, 2, 8, 8));
		panelHora.add(new JLabel("Hora apunte (HH:mm):"));
		panelHora.add(hora);
		
		JPanel panelControles = new JPanel(new BorderLayout(8, 8));
		panelControles.add(panelSuperior, BorderLayout.NORTH);
		panelControles.add(panelHora, BorderLayout.CENTER);
		panelControles.add(panelBotones, BorderLayout.SOUTH);
		
		
		String[] columnNames = {"ID", "Objetivo", "Duracion (min)", "Fecha y Hora", "Sala", "Entrenador"};
		modeloTabla = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		tablaSesiones = new JTable(modeloTabla);
		tablaSesiones.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		tablaSesiones.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				if (e.getClickCount() == 2) {
					int fila = tablaSesiones.getSelectedRow();
					if (fila >= 0) {
						int idSesionSeleccionada = (int) modeloTabla.getValueAt(fila, 0);
						idSesion.setText(String.valueOf(idSesionSeleccionada));
					}
				}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane(tablaSesiones);
		
		
		JPanel panelPrincipal = new JPanel(new BorderLayout(8, 8));
		panelPrincipal.add(panelControles, BorderLayout.NORTH);
		panelPrincipal.add(scrollPane, BorderLayout.CENTER);
		
		setContentPane(panelPrincipal);
		
		
		cargarSesionesDisponibles();
	}

	private void cargarSesionesDisponibles() {
		try {
			Context result = Controller.getInstance().action(
				new Context(Evento.MOSTRAR_SESIONES_DISPONIBLES_CLIENTE, null));
			
			if (result.getEvento() == Evento.RES_MOSTRAR_SESIONES_DISPONIBLES_CLIENTE_OK) {
				@SuppressWarnings("unchecked")
				Set<TSesion> sesiones = (Set<TSesion>) result.getData();
				actualizarTabla(sesiones);
			} else {
				modeloTabla.setRowCount(0);
				JOptionPane.showMessageDialog(this, "No hay sesiones disponibles.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (Exception e) {
			modeloTabla.setRowCount(0);
			JOptionPane.showMessageDialog(this, "Error al obtener sesiones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void actualizarTabla(Set<TSesion> sesiones) {
		modeloTabla.setRowCount(0);
		
		java.util.List<TSesion> sesionesOrdenadas = sesiones.stream()
			.sorted((s1, s2) -> Integer.compare(s1.getIdSesion(), s2.getIdSesion()))
			.collect(Collectors.toList());

		for (TSesion sesion : sesionesOrdenadas) {
			Object[] row = {
				sesion.getIdSesion(),
				sesion.getObjetivo(),
				sesion.getDuracion(),
				sesion.getFechaHora(),
				sesion.getIdSala(),
				sesion.getIdEntrenador()
			};
			modeloTabla.addRow(row);
		}
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
		fecha.setText("yyyy-MM-dd");
		hora.setText(new SimpleDateFormat("HH:mm").format(new java.util.Date()));
	}

	@Override
	public void update(Context context) {
		JOptionPane.showMessageDialog(this, context.getMessage());
	}
}

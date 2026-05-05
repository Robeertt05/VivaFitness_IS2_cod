package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Sesion.TSesion;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
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

public class VistaMostrarSesionesCliente extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTextField idCliente = new JTextField(10);
	private JTable table;
	private DefaultTableModel tableModel;

	public VistaMostrarSesionesCliente() {
		setTitle("Sesiones del cliente");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel top = new JPanel(new FlowLayout());
		top.add(new JLabel("ID cliente:"));
		top.add(idCliente);
		JButton buscar = new JButton("Buscar");
		buscar.addActionListener(e -> buscar());
		top.add(buscar);
		
		// Create table
		String[] columnNames = {"ID", "Objetivo", "Duracion (min)", "Fecha y Hora", "Sala", "Entrenador"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		add(top, BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
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
		tableModel.setRowCount(0);
		
		if (context.isSuccess() && context.getObjeto() instanceof Set) {
			Set<?> sesiones = (Set<?>) context.getObjeto();
			
			if (sesiones.isEmpty()) {
				JOptionPane.showMessageDialog(this, "El cliente no tiene sesiones.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			// Sort by ID and add to table
			List<TSesion> sesionList = sesiones.stream()
				.filter(s -> s instanceof TSesion)
				.map(s -> (TSesion) s)
				.sorted((a, b) -> Integer.compare(a.getIdSesion(), b.getIdSesion()))
				.collect(Collectors.toList());
			
			for (TSesion sesion : sesionList) {
				Object[] row = {
					sesion.getIdSesion(),
					sesion.getObjetivo(),
					sesion.getDuracion(),
					sesion.getFechaHora(),
					sesion.getIdSala(),
					sesion.getIdEntrenador()
				};
				tableModel.addRow(row);
			}
		} else {
			JOptionPane.showMessageDialog(this, context.getMessage());
		}
	}
}

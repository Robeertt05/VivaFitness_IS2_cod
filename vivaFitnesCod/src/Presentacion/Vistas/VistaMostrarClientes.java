package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Cliente.TCliente;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VistaMostrarClientes extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnActualizar;
	private JButton btnCerrar;

	public VistaMostrarClientes() {
		setTitle("Mostrar clientes");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		String[] columnNames = {"ID", "DNI", "Nombre", "Telefono", "Correo"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(e -> cargar());
		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(e -> dispose());
		
		JPanel top = new JPanel();
		top.add(btnActualizar);
		top.add(btnCerrar);
		
		add(top, BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
		cargar();
	}

	private void cargar() {
		update(Controller.getInstance().action(new Context(Evento.MOSTRAR_CLIENTES, null)));
	}

	@Override
	public void update(Context context) {
		tableModel.setRowCount(0);
		
		if (context.isSuccess() && context.getObjeto() instanceof Set) {
			Set<?> clientes = (Set<?>) context.getObjeto();
			
			if (clientes.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay clientes registrados.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			
			List<TCliente> clienteList = clientes.stream()
				.filter(c -> c instanceof TCliente)
				.map(c -> (TCliente) c)
				.sorted((a, b) -> Integer.compare(a.getId(), b.getId()))
				.collect(Collectors.toList());
			
			for (TCliente cliente : clienteList) {
				Object[] row = {
					cliente.getId(),
					cliente.get_dni(),
					cliente.get_nombre(),
					cliente.get_telefono(),
					cliente.get_correo()
				};
				tableModel.addRow(row);
			}
		} else {
			JOptionPane.showMessageDialog(this, context.getMessage());
		}
	}
}

package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Cliente.TCliente;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class VistaMostrarCliente extends JFrame implements IGUI {
	private static final long serialVersionUID = 1L;
	private JTextField id = new JTextField(10);
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnBuscar, btnCerrar;

	public VistaMostrarCliente() {
		setTitle("Mostrar cliente");
		setSize(900, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel top = new JPanel(new GridLayout(1, 3, 10, 10));
		top.add(new JLabel("ID cliente:"));
		top.add(id);
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(e -> buscar());
		top.add(btnBuscar);
		
		
		String[] columnNames = {"ID", "DNI", "Nombre", "Telefono", "Correo"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		
		JPanel bottom = new JPanel();
		btnCerrar = new JButton("Cerrar");
		btnCerrar.addActionListener(e -> dispose());
		bottom.add(btnCerrar);
		
		add(top, BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
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
		tableModel.setRowCount(0);
		
		if (context.isSuccess() && context.getObjeto() instanceof TCliente) {
			TCliente cliente = (TCliente) context.getObjeto();
			Object[] row = {
				cliente.getId(),
				cliente.get_dni(),
				cliente.get_nombre(),
				cliente.get_telefono(),
				cliente.get_correo()
			};
			tableModel.addRow(row);
		} else {
			JOptionPane.showMessageDialog(this, context.getMessage());
		}
	}
}

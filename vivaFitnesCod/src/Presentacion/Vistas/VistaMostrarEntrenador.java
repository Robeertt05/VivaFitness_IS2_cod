
package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Entrenador.TEntrenador;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaMostrarEntrenador extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JLabel lblId;
	private JTextField txtId;
	private JButton btnBuscar, btnCancelar;
	private JTable table;
	private DefaultTableModel tableModel;

	public VistaMostrarEntrenador() {
		setTitle("Mostrar Entrenador - VivaFitness");
		setSize(600, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel panelSuperior = new JPanel(new GridLayout(1, 3, 10, 10));
		lblId = new JLabel("ID Entrenador:");
		txtId = new JTextField();
		btnBuscar = new JButton("Buscar");

		panelSuperior.add(lblId);
		panelSuperior.add(txtId);
		panelSuperior.add(btnBuscar);

		String[] columnNames = {"ID", "DNI", "Nombre", "Telefono"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

		btnCancelar = new JButton("Cerrar");
		btnCancelar.addActionListener(e -> dispose());

		btnBuscar.addActionListener(e -> {
			try {
				int id = Integer.parseInt(txtId.getText().trim());
				Context ctx = new Context(Evento.MOSTRAR_ENTRENADOR, id);
				Context res = Controller.getInstance().action(ctx);
				update(res);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this,
					"Introduzca un ID valido.",
					"Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		panel.add(panelSuperior, BorderLayout.NORTH);
		panel.add(new JScrollPane(table), BorderLayout.CENTER);
		panel.add(btnCancelar, BorderLayout.SOUTH);
		add(panel);
	}

	@Override
	public void update(Context context) {
		tableModel.setRowCount(0);
		
		if (context == null) return;

		Evento evento = context.getEvento();

		if (evento == Evento.RES_MOSTRAR_ENTRENADOR_OK) {
			TEntrenador t = (TEntrenador) context.getObjeto();
			Object[] row = {
				t.get_id(),
				t.get_dni(),
				t.get_nombre(),
				t.get_telefono()
			};
			tableModel.addRow(row);
		} 
		else if (evento == Evento.RES_MOSTRAR_ENTRENADOR_KO) {
			JOptionPane.showMessageDialog(this, "No se encontro el entrenador con ese ID.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

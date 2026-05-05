package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Entrenador.TEntrenador;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class VistaMostrarEntrenadores extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnActualizar;
	private JButton btnCerrar;

	public VistaMostrarEntrenadores() {
		setTitle("Mostrar todos los Entrenadores - VivaFitness");
		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initComponents();
		cargarEntrenadores();
	}

	private void initComponents() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Create table
		String[] columnNames = {"ID", "DNI", "Nombre", "Telefono"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(table);

		JPanel panelBotones = new JPanel();
		btnActualizar = new JButton("Actualizar");
		btnCerrar = new JButton("Cerrar");
		panelBotones.add(btnActualizar);
		panelBotones.add(btnCerrar);

		btnActualizar.addActionListener(e -> cargarEntrenadores());
		btnCerrar.addActionListener(e -> dispose());

		panel.add(scrollPane, BorderLayout.CENTER);
		panel.add(panelBotones, BorderLayout.SOUTH);
		add(panel);
	}

	private void cargarEntrenadores() {
		Context ctx = new Context(Evento.MOSTRAR_ENTRENADORES, null);
		Context res = Controller.getInstance().action(ctx);
		update(res);
	}

	@Override
	public void update(Context context) {
		tableModel.setRowCount(0);
		
		if (context == null) {
			return;
		}

		if (context.getEvento() == Evento.RES_MOSTRAR_ENTRENADORES_OK && context.getObjeto() instanceof Set) {
			Set<?> entrenadores = (Set<?>) context.getObjeto();
			if (entrenadores.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No hay entrenadores registrados.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			// Sort by ID and add to table
			List<TEntrenador> entrenadorList = entrenadores.stream()
				.filter(e -> e instanceof TEntrenador)
				.map(e -> (TEntrenador) e)
				.sorted((a, b) -> Integer.compare(a.get_id(), b.get_id()))
				.collect(Collectors.toList());
			
			for (TEntrenador entrenador : entrenadorList) {
				Object[] row = {
					entrenador.get_id(),
					entrenador.get_dni(),
					entrenador.get_nombre(),
					entrenador.get_telefono()
				};
				tableModel.addRow(row);
			}
		} else {
			JOptionPane.showMessageDialog(this, "No se pudieron cargar los entrenadores.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}

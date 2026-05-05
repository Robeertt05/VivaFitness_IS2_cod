/**
 * 
 */
package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Sala.TSala;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
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

/** 
 * View for listing all rooms in a table format
 * @author azuri
 */
public class VistaMostrarTodasSalas extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnActualizar;
	private JButton btnCerrar;
	
	public VistaMostrarTodasSalas() {
		setTitle("Todas las Salas");
		setSize(700, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		
		initComponents();
		refrescarSalas();
	}
	
	private void initComponents() {
		// Create table
		String[] columnNames = {"ID", "Nombre", "Aforo"};
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		table = new JTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(table);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnActualizar = new JButton("Actualizar");
		btnCerrar = new JButton("Cerrar");
		btnActualizar.addActionListener(e -> refrescarSalas());
		btnCerrar.addActionListener(e -> dispose());
		jButton.add(btnActualizar);
		jButton.add(btnCerrar);
		buttonPanel.add(btnActualizar);
		buttonPanel.add(btnCerrar);
		
		jPanel.add(buttonPanel);
		
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void refrescarSalas() {
		Context res = Controller.getInstance().action(new Context(Evento.MOSTRAR_TODAS_SALAS, null));
		update(res);
	}
	
	public void addRefreshButtonListener(ActionListener listener) {
		btnActualizar.addActionListener(listener);
		actionListener.add(listener);
	}
	
	public void addCloseButtonListener(ActionListener listener) {
		btnCerrar.addActionListener(listener);
		actionListener.add(listener);
	}

	@Override
	public void update(Context context) {
		tableModel.setRowCount(0);
		
		if (context != null && context.isSuccess() && context.getData() instanceof Set) {
			Set<?> salas = (Set<?>) context.getData();
			
			if (salas.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No se encontraron salas.", "Información", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			// Sort by ID and add to table
			List<TSala> salasList = salas.stream()
				.filter(s -> s instanceof TSala)
				.map(s -> (TSala) s)
				.sorted((a, b) -> Integer.compare(a.getIdSala(), b.getIdSala()))
				.collect(Collectors.toList());
			
			for (TSala sala : salasList) {
				Object[] row = {
					sala.getIdSala(),
					sala.getNombreSala(),
					sala.getAforo()
				};
				tableModel.addRow(row);
			}
		} else if (context != null) {
			JOptionPane.showMessageDialog(this,
				context.getMessage() != null ? context.getMessage() : "No se encontraron salas.",
				"Información",
				JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

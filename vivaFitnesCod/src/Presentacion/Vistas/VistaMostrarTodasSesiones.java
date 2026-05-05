/**
 * 
 */
package Presentacion.Vistas;

import Controlador.Context;
import Controlador.Controller;
import Integracion.Sesion.TSesion;
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
 * View for listing all sessions in a table format
 * @author azuri
 */
public class VistaMostrarTodasSesiones extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnActualizar;
	private JButton btnCerrar;
	
	public VistaMostrarTodasSesiones() {
		setTitle("Todas las sesiones");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		
		initComponents();
		refrescarSesiones();
	}
	
	private void refrescarSesiones() {
		Context res = Controller.getInstance().action(new Context(Evento.MOSTRAR_TODAS_SESIONES, null));
		update(res);
	}
	
	private void initComponents() {
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
		JScrollPane scrollPane = new JScrollPane(table);
		
		// Buttons
		JPanel buttonPanel = new JPanel();
		btnActualizar = new JButton("Actualizar");
		btnCerrar = new JButton("Cerrar");
		jButton.add(btnActualizar);
		jButton.add(btnCerrar);
		buttonPanel.add(btnActualizar);
		buttonPanel.add(btnCerrar);
		
		jPanel.add(buttonPanel);
		
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		btnActualizar.addActionListener(e -> update(Controller.getInstance().action(
				new Context(Evento.MOSTRAR_TODAS_SESIONES, null))));
		btnCerrar.addActionListener(e -> dispose());
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
		
		if (context != null && context.getData() instanceof Set) {
			Set<?> sesiones = (Set<?>) context.getData();
			
			if (sesiones.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No se encontraron sesiones.", "Informacion", JOptionPane.INFORMATION_MESSAGE);
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
		} else if (context != null) {
			JOptionPane.showMessageDialog(this, context.getMessage());
		}
	}
}

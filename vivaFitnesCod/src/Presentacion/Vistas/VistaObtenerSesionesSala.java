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
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
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

/** 
 * View for displaying all sessions in a room in table format
 * CASO 6: Obtener sesiones de una sala (Relacion 1-N)
 * @author azuri
 */
public class VistaObtenerSesionesSala extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JLabel> jLabel;
	
	private JTextField txtIdSala;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnMostrar;
	private JButton btnCerrar;
	
	public VistaObtenerSesionesSala() {
		setTitle("Sesiones de Sala");
		setSize(900, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel topPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		
		// Select Room
		jLabel.add(new JLabel("ID Sala:"));
		topPanel.add(new JLabel("ID Sala:"));
		txtIdSala = new JTextField();
		topPanel.add(txtIdSala);
		
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
		btnMostrar = new JButton("Mostrar Sesiones");
		btnCerrar = new JButton("Cerrar");
		btnMostrar.addActionListener(e -> mostrarSesionesSala());
		btnCerrar.addActionListener(e -> dispose());
		jButton.add(btnMostrar);
		jButton.add(btnCerrar);
		buttonPanel.add(btnMostrar);
		buttonPanel.add(btnCerrar);
		
		jPanel.add(topPanel);
		jPanel.add(buttonPanel);
		
		add(topPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void mostrarSesionesSala() {
		try {
			int idSala = Integer.parseInt(txtIdSala.getText().trim());
			Context res = Controller.getInstance().action(new Context(Evento.OBTENER_SESIONES_SALA, idSala));
			update(res);
		} catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(this, "Introduzca un ID valido.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void addShowButtonListener(ActionListener listener) {
		btnMostrar.addActionListener(listener);
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
			Set<?> sesiones = (Set<?>) context.getData();
			if (sesiones.isEmpty()) {
				JOptionPane.showMessageDialog(this,
					"No hay sesiones disponibles para esta sala.",
					"Sin Sesiones",
					JOptionPane.INFORMATION_MESSAGE);
			} else {
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
			}
		} else if (context != null) {
			JOptionPane.showMessageDialog(this,
				context.getMessage() != null ? context.getMessage() : "No se encontraron sesiones para esta sala.",
				"Información",
				JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

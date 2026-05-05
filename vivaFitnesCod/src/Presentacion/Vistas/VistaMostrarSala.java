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
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
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
 * View for displaying room details in table format
 * @author azuri
 */
public class VistaMostrarSala extends JFrame implements IGUI {
	
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
	
	public VistaMostrarSala() {
		setTitle("Detalles de Sala");
		setSize(700, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		actionListener = new HashSet<>();
		jButton = new HashSet<>();
		jPanel = new HashSet<>();
		jLabel = new HashSet<>();
		
		initComponents();
	}
	
	private void initComponents() {
		JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 10));
		
		// Select Room
		jLabel.add(new JLabel("ID Sala:"));
		topPanel.add(new JLabel("ID Sala:"));
		txtIdSala = new JTextField();
		topPanel.add(txtIdSala);
		btnMostrar = new JButton("Mostrar");
		topPanel.add(btnMostrar);
		
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
		btnCerrar = new JButton("Cerrar");
		btnMostrar.addActionListener(e -> mostrarSala());
		btnCerrar.addActionListener(e -> dispose());
		jButton.add(btnMostrar);
		jButton.add(btnCerrar);
		buttonPanel.add(btnCerrar);
		
		jPanel.add(topPanel);
		jPanel.add(buttonPanel);
		
		add(topPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private void mostrarSala() {
		try {
			int idSala = Integer.parseInt(txtIdSala.getText().trim());
			Context res = Controller.getInstance().action(new Context(Evento.MOSTRAR_SALA, idSala));
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
		
		if (context != null && context.isSuccess() && context.getData() instanceof TSala) {
			TSala sala = (TSala) context.getData();
			Object[] row = {
				sala.getIdSala(),
				sala.getNombreSala(),
				sala.getAforo()
			};
			tableModel.addRow(row);
		} else if (context != null) {
			JOptionPane.showMessageDialog(this,
				context.getMessage() != null ? context.getMessage() : "No se pudo mostrar la sala.",
				"Informacion",
				JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

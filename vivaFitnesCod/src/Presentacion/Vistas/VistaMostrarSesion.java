/**
 * 
 */
package Presentacion.Vistas;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.FactoriaPresentacion.Evento;
import Controlador.Controller;
import Controlador.Context;
import Integracion.Sesion.TSesion;

/** 
 * View for displaying session details in table format
 * @author azuri
 */
public class VistaMostrarSesion extends JFrame implements IGUI {
	
	private static final long serialVersionUID = 1L;
	private Set<ActionListener> actionListener;
	private Set<JButton> jButton;
	private Set<JPanel> jPanel;
	private Set<JLabel> jLabel;
	
	private JTextField txtIdSesion;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton btnMostrar;
	private JButton btnCerrar;
	
	public VistaMostrarSesion() {
		setTitle("Detalles de sesion");
		setSize(900, 300);
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
		
		// Session ID input
		jLabel.add(new JLabel("ID sesion:"));
		topPanel.add(new JLabel("ID sesion:"));
		txtIdSesion = new JTextField(10);
		topPanel.add(txtIdSesion);
		btnMostrar = new JButton("Mostrar");
		topPanel.add(btnMostrar);
		
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
		btnCerrar = new JButton("Cerrar");
		jButton.add(btnMostrar);
		jButton.add(btnCerrar);
		buttonPanel.add(btnCerrar);
		
		jPanel.add(topPanel);
		jPanel.add(buttonPanel);
		
		add(topPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		btnMostrar.addActionListener(e -> {
			int id = getSelectedSessionId();
			if (id <= 0) {
				JOptionPane.showMessageDialog(this, "Introduzca un ID de sesion valido", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			update(Controller.getInstance().action(new Context(Evento.MOSTRAR_SESION, id)));
		});
		btnCerrar.addActionListener(e -> dispose());
	}

	public int getSelectedSessionId() {
		try {
			return Integer.parseInt(txtIdSesion.getText().trim());
		} catch (NumberFormatException ex) {
			return -1;
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
		
		if (context != null && context.isSuccess() && context.getData() instanceof TSesion) {
			TSesion s = (TSesion) context.getData();
			Object[] row = {
				s.getIdSesion(),
				s.getObjetivo(),
				s.getDuracion(),
				s.getFechaHora(),
				s.getIdSala(),
				s.getIdEntrenador()
			};
			tableModel.addRow(row);
		} else if (context != null) {
			JOptionPane.showMessageDialog(this, context.getMessage());
		}
	}
}

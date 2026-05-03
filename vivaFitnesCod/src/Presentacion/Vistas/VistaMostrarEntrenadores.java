package Presentacion.Vistas;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

import Controlador.Context;
import Controlador.Controller;
import Negocio.entrenador.TEntrenador;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

public class VistaMostrarEntrenadores extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;
	private JTextArea txtResultado;
	private JButton btnActualizar;
	private JButton btnCerrar;

	public VistaMostrarEntrenadores() {
		setTitle("Mostrar todos los Entrenadores - VivaFitness");
		setSize(600, 420);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initComponents();
		cargarEntrenadores();
	}

	private void initComponents() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		txtResultado = new JTextArea();
		txtResultado.setEditable(false);

		JPanel panelBotones = new JPanel();
		btnActualizar = new JButton("Actualizar");
		btnCerrar = new JButton("Cerrar");
		panelBotones.add(btnActualizar);
		panelBotones.add(btnCerrar);

		btnActualizar.addActionListener(e -> cargarEntrenadores());
		btnCerrar.addActionListener(e -> dispose());

		panel.add(new JScrollPane(txtResultado), BorderLayout.CENTER);
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
		if (context == null) {
			return;
		}

		if (context.getEvento() == Evento.RES_MOSTRAR_ENTRENADORES_OK && context.getObjeto() instanceof Set) {
			Set<?> entrenadores = (Set<?>) context.getObjeto();
			if (entrenadores.isEmpty()) {
				txtResultado.setText("No hay entrenadores registrados.");
				return;
			}

			StringBuilder sb = new StringBuilder();
			for (Object item : entrenadores) {
				if (item instanceof TEntrenador) {
					TEntrenador entrenador = (TEntrenador) item;
					sb.append("ID: ").append(entrenador.get_id()).append("\n");
					sb.append("DNI: ").append(entrenador.get_dni()).append("\n");
					sb.append("Nombre: ").append(entrenador.get_nombre()).append("\n");
					sb.append("Telefono: ").append(entrenador.get_telefono()).append("\n");
					sb.append("Activo: ").append(entrenador.get_activo() == 1 ? "Si" : "No").append("\n");
					sb.append("----------------------------------------\n");
				}
			}
			txtResultado.setText(sb.toString());
		} else {
			txtResultado.setText("No se pudieron cargar los entrenadores.");
		}
	}
}

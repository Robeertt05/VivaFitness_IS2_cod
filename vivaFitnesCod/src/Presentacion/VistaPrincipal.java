/**
 * 
 */
package Presentacion;

import Controlador.Controller;
import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.IGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import Controlador.Context;

/** 
* <!-- begin-UML-doc -->
* <!-- end-UML-doc -->
* @author azuri
* @generated "UML a Java (com.ibm.xtools.transform.uml2.java5.internal.UML2JavaTransform)"
*/
public class VistaPrincipal extends JFrame implements IGUI {

	private static final long serialVersionUID = 1L;

	public VistaPrincipal() {
		super("[VIVA FITNESS]");
		initIGUI();
	}

	private void initIGUI() {
		JPanel mainPanel = new JPanel();
		this.setContentPane(mainPanel);
		this.setPreferredSize(new Dimension(600, 200));
		JPanel controlPanel = new JPanel();
		rellenaControlPanel(controlPanel);
		mainPanel.add(controlPanel, BorderLayout.WEST);

		executeMessageOnClose();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);

	}

	private void executeMessageOnClose() {
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {

			}

			@Override
			public void windowClosing(WindowEvent e) {
				int n = JOptionPane.showOptionDialog(getWindow(VistaPrincipal.this), "Are sure you want to quit?",
						"Quit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);

				if (n == 0) {
					System.exit(0);
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowIconified(WindowEvent e) {

			}

			@Override
			public void windowDeiconified(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {

			}

			@Override
			public void windowDeactivated(WindowEvent e) {

			}
		});
	}

	private void rellenaControlPanel(JPanel c) {
		c.setLayout(new BorderLayout());
		JToolBar _toolaBar = new JToolBar();
		JToolBar _toolaBarJPA = new JToolBar();
		JPanel separador = new JPanel();
		separador.setPreferredSize(new Dimension(0, 30));
		c.add(_toolaBar, BorderLayout.PAGE_START);
		c.add(separador, BorderLayout.CENTER);
		c.add(_toolaBarJPA, BorderLayout.PAGE_END);

		_toolaBar.add(crearPanelConEtiquetaYBoton("Cliente", "resources/cliente.png", e -> {
			Context context = new Context(Evento.CLIENTE, null);
			Controller.getInstance().action(context);
			this.dispose();
		}));

		_toolaBar.add(crearPanelConEtiquetaYBoton("Entrenador", "resources/entrenador.png", e -> {
			Context context = new Context(Evento.ENTRENADOR, null);
			Controller.getInstance().action(context);
			this.dispose();
		}));

		_toolaBar.add(crearPanelConEtiquetaYBoton("SesiÛn", "resources/sesion.png", e -> {
			Context context = new Context(Evento.SESION, null);
			Controller.getInstance().action(context);
			this.dispose();
		}));

		_toolaBar.add(crearPanelConEtiquetaYBoton("Sala", "resources/sala.png", e -> {
			Context context = new Context(Evento.SALA, null);
			Controller.getInstance().action(context);
			this.dispose();
		}));

		_toolaBar.setFloatable(false);
	}

	private JPanel crearPanelConEtiquetaYBoton(String texto, String iconoPath, ActionListener action) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(texto, SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 11));
		JButton button = new JButton(new ImageIcon(iconoPath));
		button.setToolTipText(texto);
		button.addActionListener(action);

		panel.add(label, BorderLayout.NORTH); // Etiqueta encima
		panel.add(button, BorderLayout.CENTER); // Bot√≥n debajo
		return panel;
	}

	@Override
	public void update(Context c) {
		setVisible(true);
		// Context context=new Context(Evento.VISTA_PRINCIPAL,null);
		// Controller.getInstancia().accion(context);
		// dispose();
	}

	static Frame getWindow(Component c) {
		Frame w = null;
		if (c != null) {
			if (c instanceof Frame)
				w = (Frame) c;
			else
				w = (Frame) SwingUtilities.getWindowAncestor(c);
		}
		return w;
	}
}
/**
 * 
 */
package Presentacion;

import javax.swing.*;
import java.awt.*;
import Presentacion.FactoriaPresentacion.IGUI;
import Presentacion.Vistas.VistaEntrenador;
import Presentacion.Vistas.VistaSala;
import Presentacion.Vistas.VistaCliente;
import Controlador.Context;

/** 
 * Main application window with all entities.
 * Only Sessions flow is functional for now.
 * @author azuri
 */
public class VistaPrincipal extends JFrame implements IGUI {

private static final long serialVersionUID = 1L;

public VistaPrincipal() {
setTitle("Vista Principal");
setSize(760, 520);
setLocationRelativeTo(null);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
initComponents();
}

private void initComponents() {
JPanel root = new JPanel(new BorderLayout(10, 20));
root.setBorder(BorderFactory.createEmptyBorder(18, 24, 24, 24));
root.setBackground(new Color(236, 236, 236));

JLabel titulo = new JLabel("Vista Principal", SwingConstants.CENTER);
titulo.setFont(new Font("SansSerif", Font.BOLD, 36));

JLabel subtitulo = new JLabel("", SwingConstants.LEFT);
subtitulo.setFont(new Font("SansSerif", Font.PLAIN, 30));

JPanel top = new JPanel(new BorderLayout(10, 12));
top.setOpaque(false);
top.add(titulo, BorderLayout.NORTH);
top.add(subtitulo, BorderLayout.SOUTH);

JPanel entidades = new JPanel(new GridLayout(2, 2, 36, 32));
entidades.setOpaque(false);

JButton btnClientes = crearBotonEntidad("Clientes");
JButton btnEntrenadores = crearBotonEntidad("Entrenadores");
JButton btnSesiones = crearBotonEntidad("Sesiones");
JButton btnSalas = crearBotonEntidad("Salas");

btnClientes.addActionListener(e -> new VistaCliente().setVisible(true));
btnEntrenadores.addActionListener(e -> new VistaEntrenador().setVisible(true));
btnSalas.addActionListener(e -> new VistaSala().setVisible(true));
btnSesiones.addActionListener(e -> new VistaMenuSesiones().setVisible(true));

entidades.add(btnClientes);
entidades.add(btnEntrenadores);
entidades.add(btnSesiones);
entidades.add(btnSalas);

root.add(top, BorderLayout.NORTH);
root.add(entidades, BorderLayout.CENTER);
setContentPane(root);
}

private JButton crearBotonEntidad(String texto) {
JButton button = new JButton(texto);
button.setFont(new Font("SansSerif", Font.PLAIN, 34));
button.setFocusPainted(false);
button.setBackground(new Color(188, 208, 225));
button.setOpaque(true);
button.setBorder(BorderFactory.createLineBorder(new Color(55, 55, 55), 2, true));
return button;
}

@Override
public void update(Context context) {
// VistaPrincipal no necesita actualizacion de datos
}

public static void main(String[] args) {
SwingUtilities.invokeLater(() -> {
VistaPrincipal vp = new VistaPrincipal();
vp.setVisible(true);
});
}
}

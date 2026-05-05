package Presentacion;

import Presentacion.FactoriaPresentacion.Evento;
import Presentacion.FactoriaPresentacion.FactoriaVistas;
import Presentacion.FactoriaPresentacion.IGUI;
import java.awt.*;
import javax.swing.*;


public class VistaMenuSesiones extends JFrame {

    private static final long serialVersionUID = 1L;

    public VistaMenuSesiones() {
        setTitle("Sesiones");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        JPanel root = new JPanel(new BorderLayout(10, 16));
        root.setBorder(BorderFactory.createEmptyBorder(20, 26, 24, 26));
        root.setBackground(new Color(236, 236, 236));

        JLabel title = new JLabel("Menu de Sesiones", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 34));

        JPanel grid = new JPanel(new GridLayout(3, 2, 24, 20));
        grid.setOpaque(false);

        JButton btnMostrar = crearBotonAccion("Mostrar sesion");
        JButton btnBaja = crearBotonAccion("Baja sesion");
        JButton btnMostrarTodas = crearBotonAccion("Mostrar todas las sesiones");
        JButton btnModificar = crearBotonAccion("Modificar sesion");
        JButton btnMostrarSala = crearBotonAccion("Mostrar sala de sesion");
        JButton btnMostrarEntrenador = crearBotonAccion("Mostrar entrenador de sesion");

        btnBaja.addActionListener(e -> abrirVista(Evento.BAJA_SESION));
        btnModificar.addActionListener(e -> abrirVista(Evento.MODIFICAR_SESION));
        btnMostrar.addActionListener(e -> abrirVista(Evento.MOSTRAR_SESION));
        btnMostrarTodas.addActionListener(e -> abrirVista(Evento.MOSTRAR_TODAS_SESIONES));
        btnMostrarSala.addActionListener(e -> abrirVista(Evento.MOSTRAR_SALA_SESION));
        btnMostrarEntrenador.addActionListener(e -> abrirVista(Evento.MOSTRAR_ENTRENADOR_SESION));

        grid.add(btnMostrar);
        grid.add(btnBaja);
        grid.add(btnMostrarTodas);
        grid.add(btnModificar);
        grid.add(btnMostrarSala);
        grid.add(btnMostrarEntrenador);

        root.add(title, BorderLayout.NORTH);
        root.add(grid, BorderLayout.CENTER);

        setContentPane(root);
    }

    private JButton crearBotonAccion(String texto) {
        JButton button = new JButton(texto);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setHorizontalAlignment(SwingConstants.CENTER);
        return button;
    }

    private void abrirVista(Evento evento) {
        IGUI vista = FactoriaVistas.getInstance().generarVistas(evento);
        if (vista instanceof JFrame) {
            ((JFrame) vista).setVisible(true);
        }
    }
}

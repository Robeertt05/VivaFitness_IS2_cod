package App;

import Presentacion.VistaPrincipal;

/**
 * Main entry point for VivaFitness application
 * Starts the GUI interface
 */
public class Main {
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            VistaPrincipal ventanaPrincipal = new VistaPrincipal();
            ventanaPrincipal.setVisible(true);
        });
    }
}

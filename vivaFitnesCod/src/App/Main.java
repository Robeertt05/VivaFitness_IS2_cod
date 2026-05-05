package App;

import Presentacion.VistaPrincipal;
public class Main {
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            VistaPrincipal ventanaPrincipal = new VistaPrincipal();
            ventanaPrincipal.setVisible(true);
        });
    }
}

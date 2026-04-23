package Integracion.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//NO SE SI ESTO ESTÁ BIEN, ES LO DEFAULT QUE ME HA DADO CHATGPT
public class ConnectionManager {
    
    private static final String URL = "jdbc:mysql://localhost:3306/nombre_base_datos";
    private static final String USER = "root";
    private static final String PASSWORD = "tu_password";
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al cargar el driver de MySQL", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
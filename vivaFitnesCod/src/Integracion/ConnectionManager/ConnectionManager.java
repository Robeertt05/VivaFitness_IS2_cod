package Integracion.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    
    private static final String URL = System.getProperty(
            "vivafitness.db.url",
            "jdbc:mysql://127.0.0.1:3306/vivafitness?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
    
    private static final String USER = System.getProperty("vivafitness.db.user", "root");
    
    private static final String PASSWORD = System.getProperty("vivafitness.db.password", ""); 
    
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("No se encontro el driver JDBC de MySQL.", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

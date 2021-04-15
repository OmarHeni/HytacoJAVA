package Utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MaConnexion {
     private static MaConnexion instance;
    private Connection cnx;

    private final String URL = "jdbc:mysql://localhost:3306/Hytaco";
    private final String LOGIN = "root";
    private final String PASSWORD = "";

    private MaConnexion() {
        try {
            cnx = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println("Connecting !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static MaConnexion getInstance() {
        if (instance == null) {
            instance = new MaConnexion();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
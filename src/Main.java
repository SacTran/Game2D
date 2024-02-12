import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/qlbh";
        String user = "root";
        String password = "12345678";
        try(Connection conn = DriverManager.getConnection(url,user,password)){
            System.out.println(conn.getCatalog());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
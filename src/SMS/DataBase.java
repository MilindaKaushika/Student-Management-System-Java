package SMS;



import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {

    static Connection getcConnection() throws Exception {

        Class.forName("com.mysql.jdbc.Driver");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/CGTTI_OB_DB", "root", "123");
        return c;

    }
}

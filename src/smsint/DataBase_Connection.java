package smsint;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase_Connection {

    private static Connection con = null;
    private static String driver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/CGTTI_OB_DB";
    private static String user = "root";
    private static String password = "123";

    public static synchronized Connection getCon() throws Exception {

        if (con == null) {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
            return con;
        }
        return con;
    }

    public static synchronized void putdata(String sql) throws Exception {
        Statement st = DataBase_Connection.getCon().createStatement();
        st.executeUpdate(sql);
    }

    public static synchronized ResultSet getData(String sql) throws Exception {
        Statement st = DataBase_Connection.getCon().createStatement();
        ResultSet rs = st.executeQuery(sql);
        return rs;
    }

}

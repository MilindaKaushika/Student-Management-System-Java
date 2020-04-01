/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Theja
 */
public class DBConnManager {
    
    String sourceURL;

    public DBConnManager() {
    }


            public static Connection Connect(){
   		try
   		{
   			String host =("jdbc:sqlserver://HOME;databaseName=Student");
			String username = "NEW_USER";
			String password = "123";
			Connection connection = DriverManager.getConnection(host,username,password);
            		return connection;
   		}
   		catch(Exception ex)
   		{
   			return null;
   		}
     }
public void con_close(Connection dbConn) {
        try {
            dbConn.close();
        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------DB connection closing failure");
}
    
}
 
}

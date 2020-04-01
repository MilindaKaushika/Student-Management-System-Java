/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;
import Data.DBConnManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Home-PC
 */
public class UserDetails {
      private DBConnManager dbConnManager = null;



    public UserDetails() {

        dbConnManager = new DBConnManager();
    }
 public boolean addUser(User cs) {

        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();




            String query = "INSERT INTO login(username,password,usertype)  " +
                    "VALUES( '" + cs.getusername()+ "','" + cs.getpassword()+ "','" + cs.getusertype()+ "')";

            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Insert query failed");

            result = false;
        }finally{
            dbConnManager.con_close(dbConn);
        }
        return result;
    }

   public boolean updateuser(User cs) {
        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
              java.sql.Statement stmt = dbConn.createStatement();

            String query = "UPDATE login SET PassWord= '"+ cs.getpassword()+ "' Where UserName= '" + cs.getusername()+"'";
                
                  

            System.out.println(query);

            int val = stmt.executeUpdate(query);

            if (val == 1) {
                result = true;
            } else {
                result = false;
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Update query failed");
            result = false;
        } finally {
               dbConnManager.con_close(dbConn);
        }
        return result;
    }
     public User login(String uname, String pwd) {

        User empdetails = null;
        boolean result = false;

        Connection dbConn = null;

        try {

            //Connect to th DB
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();

            String query = "SELECT usertype FROM login WHERE username='" + uname + "' AND password='" + pwd + "'";

            System.out.println(query);

            ResultSet rs = stmt.executeQuery(query);

            empdetails = new User();

            if (rs.next()) {

                empdetails.setusertype(rs.getString(1));

            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Select query failed");
        } finally {
            //Close the db connection
            dbConnManager.con_close(dbConn);
        }
        return empdetails;
     }
}

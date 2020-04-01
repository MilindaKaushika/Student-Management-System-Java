/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;
import Data.DBConnManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Home
 */
public class ModuleDetails {
     private DBConnManager dbConnManager = null;

    public ModuleDetails() {
        
          dbConnManager = new DBConnManager();
  
    }
    public boolean addModule(Module cs) {

        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();


            String query = "INSERT INTO module(mod_num,name,num_of_units,code,course) " +
                    "VALUES( '" + cs.getmod_num()+ "','" + cs.getName()+ "','" + cs.getnum_of_units()+ "','" + cs.getcode()+ "','" + cs.getcourse()+ "')";

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
    public Vector getModuleDetails() {

        Vector<Vector<String>>ModuleDetailsVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT mod_num,name,num_of_units,code,course FROM  module";
            ResultSet rs = stmt.executeQuery(query);
            ModuleDetailsVector = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> ModuleDetails = new Vector<String>();

               ModuleDetails.add(rs.getString(1));
               ModuleDetails.add(rs.getString(2));
               ModuleDetails.add(rs.getString(3));
               ModuleDetails.add(rs.getString(4));
               ModuleDetails.add(rs.getString(5));

               ModuleDetailsVector.add(ModuleDetails);

            }
        } catch (Exception ex) {
            System.out.println(ex + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }

        return ModuleDetailsVector;
    }
        public ArrayList getModule () {

            int ModuleId = 0;

        ArrayList ModuleList = null;
        Connection dbConn = null;

        try {
            //Connect to th DB
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            //Select the JobCatNames
            String query = "SELECT DISTINCT mod_num FROM module";

            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            ModuleList = new ArrayList();

            while (rs.next()) {
                String catName = rs.getString(1);
                System.out.println(ModuleId);
                ModuleList.add(catName);
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Select query failed at JobCatNames");
        } finally {
            //Close the db connection
            dbConnManager.con_close(dbConn);
        }
        return ModuleList;
         } 
 public Module searchDetails(int Id){
        
	  Module couDetails = null;
            Connection dbConn = null;
         

        try {

            //Connect to th DB
            dbConn = dbConnManager.Connect();
            java.sql.Statement stmt = dbConn.createStatement();

            String query ="SELECT mod_num,name,num_of_units,code,course FROM module WHERE mod_num='"+Id+"'";
                  

            System.out.println(query);

           ResultSet rs = stmt.executeQuery(query);
//           jobDetails = new JobDetails();
           int i=0;
           
          couDetails = new Module();

            if (rs.next()) {
             
                couDetails.setmod_num(rs.getInt(1));
                couDetails.setName(rs.getString(2)); 
                couDetails.setnum_of_units(rs.getInt(3)); 
                 couDetails.setcode(rs.getString(4));
                 couDetails.setcode(rs.getString(5));
                i++;
            }
             if(i==0){
                          JOptionPane.showMessageDialog(null,"No Matching Value Found !");
                      }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Select query failed");
        } finally {
            //Close the db connection
            dbConnManager.con_close(dbConn);
        }
        return couDetails;

       }
  public boolean deleteBrand(Module cs) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            String query = "DELETE FROM module WHERE mod_num= '" + cs.getmod_num()+ "'";
            System.out.println(query);
            int val = stmt.executeUpdate(query);
            if (val == 1) {
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            System.out.println(e + "not deleted from database");
        } finally {
            dbConnManager.con_close(dbConn);
        }

        return result;
    }
      
}
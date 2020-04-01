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
import java.util.ArrayList; 
import javax.swing.JOptionPane;

/**
 *
 * @author Home
 */
public class LeacherDetails {
 private DBConnManager dbConnManager = null;
    public LeacherDetails() {
        dbConnManager = new DBConnManager();
  
    }
    public boolean addLeacher(Lectures cs) {

        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();


            String query = "INSERT INTO lecturer(lec_id,fname,lname,DOB) " +
                    "VALUES( '" + cs.getlec_id()+ "','" + cs.getfname()+ "','" + cs.getlname()+ "','" + cs.getDOB()+ "')";

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
    public Vector getLeacherDetails() {
     Vector<Vector<String>> LeacherDetailsVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT lec_id,fname,lname,DOB FROM lecturer";
            ResultSet rs = stmt.executeQuery(query);
             LeacherDetailsVector = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> LeacherDetails = new Vector<String>();

                LeacherDetails.add(rs.getString(1));
                LeacherDetails.add(rs.getString(2));
                LeacherDetails.add(rs.getString(3));
                LeacherDetails.add(rs.getString(4));
             

                LeacherDetailsVector.add(LeacherDetails);

            }
        } catch (Exception e) {
            System.out.println(e + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }
              return LeacherDetailsVector;
    }
     public ArrayList getLeacher () {

            int LeacherId = 0;

        ArrayList LeacherList = null;
        Connection dbConn = null;

        try {
            //Connect to th DB
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            //Select the JobCatNames
            String query = "SELECT DISTINCT lec_id FROM lecturer";

            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            LeacherList  = new ArrayList();

            while (rs.next()) {
                String catName = rs.getString(1);
                System.out.println(LeacherId);
               LeacherList .add(catName);
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Select query failed at JobCatNames");
        } finally {
            //Close the db connection
            dbConnManager.con_close(dbConn);
        }
        return LeacherList ;
         } 
    public boolean deleteBrand(Lectures cs) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            String query = "DELETE FROM  lecturer WHERE  lec_id= '" + cs.getlec_id()+ "'";
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

     public Lectures searchDetails(int Id){
        
	  Lectures couDetails = null;
            Connection dbConn = null;
         

        try {

            //Connect to th DB
            dbConn = dbConnManager.Connect();
            java.sql.Statement stmt = dbConn.createStatement();

            String query ="SELECT lec_id,fname FROM lecturer WHERE lec_id='"+Id+"'";
                  

            System.out.println(query);

           ResultSet rs = stmt.executeQuery(query);
//           jobDetails = new JobDetails();
           int i=0;
           
          couDetails = new Lectures();

            if (rs.next()) {
             
                couDetails.setlec_id(rs.getInt(1));
                couDetails.setfname(rs.getString(2));
               
                
                
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
        public boolean updateLeacher(Lectures cs) {
            
        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();
          java.sql.Statement stmt = dbConn.createStatement();


String query = "UPDATE lecturer SET fname='" + cs.getfname()+ "',lname='" + cs.getlname()+ 
        "',DOB='" + cs.getDOB()+ "'WHERE lec_id = '"+cs.getlec_id()+"'";
            /*String query = "UPDATE brand SET Description = '"
                    + bd.getDescription() + "'  WHERE BrandId = " + bd.getBrandId();
             *
             *
             */

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
}


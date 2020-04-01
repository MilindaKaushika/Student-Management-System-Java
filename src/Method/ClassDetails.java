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
 * @author Home-PC
 */
public class ClassDetails {
    

      private DBConnManager dbConnManager = null;

    public ClassDetails() {
        dbConnManager = new DBConnManager();
    }


    public boolean addClass(ClassM cs) {

        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();




            String query = "INSERT INTO Class(Class_ID,Class_Name,lec_id,Schedule,Leacher_Name,Class_Time_In,Class_Time_Out)  " +
                    "VALUES( '" + cs.getClass_ID()+ "','" + cs.getClass_Name()+ "','" + cs.getlec_id()+ "','" + cs.getSchedule()+ "','" + cs.getLecturer_Name()+ "','" + cs.getClass_Time_In()+ "','" + cs.getClass_Time_out()+ "')";

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
      public Vector getClassDetails() {

        Vector<Vector<String>> classDetailsVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT Class_ID,Class_Name,Schedule,Leacher_Name,Class_Time_In,Class_Time_Out FROM  Class";
            ResultSet rs = stmt.executeQuery(query);
            classDetailsVector = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> classDetails = new Vector<String>();

                classDetails.add(rs.getString(1));
                classDetails.add(rs.getString(2));
                classDetails.add(rs.getString(3));
                classDetails.add(rs.getString(4));
                classDetails.add(rs.getString(5));
                 classDetails.add(rs.getString(6));
              
               classDetailsVector.add(classDetails);

            }
        } catch (Exception e) {
            System.out.println(e + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }

        return classDetailsVector;
    }
        public boolean updateClass(ClassM cs) {
            
        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();
          java.sql.Statement stmt = dbConn.createStatement();


String query = "UPDATE Class SET Class_Name='"+ cs.getClass_Name()+ "',lec_id='" + cs.getlec_id()+ "',Schedule='" + cs.getSchedule()+ 
        "',Leacher_Name='" + cs.getLecturer_Name()+ "',Class_Time_In='" + cs.getClass_Time_In()+ "',Class_Time_Out='" + cs.getClass_Time_out()+ "'WHERE Class_ID = '"+cs.getClass_ID()+"'";
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
         public boolean deleteclass(ClassM cs) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            String query = "DELETE FROM Class WHERE Class_ID= '" + cs.getClass_ID()+ "'";
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
 public Vector getClassDetailss() {

        Vector<Vector<String>> classDetailsVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT Class_Name,Schedule,Leacher_Name,Class_Time_In,Class_Time_Out FROM  Class";
            ResultSet rs = stmt.executeQuery(query);
            classDetailsVector = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> classDetails = new Vector<String>();

                classDetails.add(rs.getString(1));
                classDetails.add(rs.getString(2));
                classDetails.add(rs.getString(3));
                classDetails.add(rs.getString(4));
                classDetails.add(rs.getString(5));
              
              
               classDetailsVector.add(classDetails);

            }
        } catch (Exception e) {
            System.out.println(e + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }

        return classDetailsVector;
    }
}
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Method;

import Data.DBConnManager;
import java.beans.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;


/**
 *
 * @author ESOFT
 */
public class CourseDetails {

      private DBConnManager dbConnManager = null;

    public CourseDetails() {
        dbConnManager = new DBConnManager();
    }


    public boolean addCourse(Course cs) {

        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();




            String query = "INSERT INTO course(code,name)  " +
                    "VALUES( '" + cs.getCode()+ "','" + cs.getName()+ "')";

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

     public Vector getCourseDetails() {

        Vector<Vector<String>> courseDetailsVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT code,name FROM course";
            ResultSet rs = stmt.executeQuery(query);
            courseDetailsVector = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> courseDetails = new Vector<String>();

                courseDetails.add(rs.getString(1));
                courseDetails.add(rs.getString(2));
              

               courseDetailsVector.add(courseDetails);

            }
        } catch (Exception e) {
            System.out.println(e + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }

        return courseDetailsVector;
    }

         public ArrayList getCourse () {

            int courseId = 0;

        ArrayList courseList = null;
        Connection dbConn = null;

        try {
            //Connect to th DB
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            //Select the JobCatNames
            String query = "SELECT DISTINCT code FROM course";

            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            courseList = new ArrayList();

            while (rs.next()) {
                String catName = rs.getString(1);
                System.out.println(courseId);
                courseList.add(catName);
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Select query failed at JobCatNames");
        } finally {
            //Close the db connection
            dbConnManager.con_close(dbConn);
        }
        return courseList;
         }     
         
        public boolean updateCourse(Course c) {
            
        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();
          java.sql.Statement stmt = dbConn.createStatement();


            String query = "UPDATE course SET name = '"+c.getName()+"'WHERE code = '"+c.getCode()+"'";

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
        public boolean deleteBrand(Course cs) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            String query = "DELETE FROM course WHERE code= '" + cs.getCode()+ "'";
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
         public Course searchDetails(String Id){
            
            
            Course couDetails = null;
            Connection dbConn = null;
         

        try {

            //Connect to th DB
            dbConn = dbConnManager.Connect();
            java.sql.Statement stmt = dbConn.createStatement();

            String query ="SELECT code,name FROM course WHERE code='"+Id+"'";
                  

            System.out.println(query);

           ResultSet rs = stmt.executeQuery(query);
//           jobDetails = new JobDetails();
           int i=0;
           
          couDetails = new Course();

            if (rs.next()) {
             
                couDetails.setCode(rs.getString(1));
                couDetails.setName(rs.getString(2)); 
          
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
           public ArrayList getCourseName () {

            int courseId = 0;

        ArrayList courseList = null;
        Connection dbConn = null;

        try {
            //Connect to th DB
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            //Select the JobCatNames
            String query = "SELECT DISTINCT name FROM course";

            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

            courseList = new ArrayList();

            while (rs.next()) {
                String catName = rs.getString(1);
                System.out.println(courseId);
                courseList.add(catName);
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Select query failed at JobCatNames");
        } finally {
            //Close the db connection
            dbConnManager.con_close(dbConn);
        }
        return courseList;
         }     

    public void addCourse(Payment cs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   }


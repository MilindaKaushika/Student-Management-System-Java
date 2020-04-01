/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

import Data.DBConnManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Vector;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Home-PC
 */
public class GradeDetails{

       private DBConnManager dbConnManager = null; 

  public GradeDetails() {
        dbConnManager = new DBConnManager();
    }
  public boolean addgrades(Grades cs) {

        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            String query = "INSERT INTO Grade(Batch_No,sid,code,Grade,Semester,Student_Name,year,Course)  " +
                    "VALUES( '" + cs.getBatch_No()+ "','" + cs.getsid()+ "','" + cs.getcode()+ "','" + cs.getGrade()+ "','" + cs.getSemester()+ "','" + cs.getStudent_Name()+ "','" + cs.getyear()+ "','" + cs.getCourse()+ "')";

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
     public Vector getGradeDetails() {

        Vector<Vector<String>> GradeDetailsVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT Batch_No,sid,Grade,Semester,Student_Name,year,Course FROM Grade";
            ResultSet rs = stmt.executeQuery(query);
            GradeDetailsVector = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> GradeDetails = new Vector<String>();

                GradeDetails.add(rs.getString(1));
                GradeDetails.add(rs.getString(2));
                GradeDetails.add(rs.getString(3));
                GradeDetails.add(rs.getString(4));
                GradeDetails.add(rs.getString(5));
                GradeDetails.add(rs.getString(6));
                 GradeDetails.add(rs.getString(7));
                 
               GradeDetailsVector.add(GradeDetails);

            }
        } catch (Exception e) {
            System.out.println(e + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }

        return GradeDetailsVector;
    }
         public boolean deleteBrand(Grades cs) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            String query = "DELETE FROM Grade WHERE  sid= '" + cs.getsid()+"'";
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
            public boolean updateGrades(Grades cs) {
            
        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();
          java.sql.Statement stmt = dbConn.createStatement();


            String query = "UPDATE Grade SET sid = '"+cs.getsid()+"',Code='"+ cs.getcode()+ "',Grade='" + cs.getGrade()+ 
                    "',Semester='" + cs.getSemester()+ "',Student_Name='"
                    + cs.getStudent_Name()+ "',year='" + cs.getyear()+ "',Course='" + cs.getCourse()+ "' WHERE Batch_No = '"+cs.getBatch_No()+"'";

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
     public Student searchDetails(int Id){
        
	  Student couDetails = null;
            Connection dbConn = null;
         

        try {

            //Connect to th DB
            dbConn = dbConnManager.Connect();
            java.sql.Statement stmt = dbConn.createStatement();

            String query ="SELECT sid,fname,code,Course FROM student WHERE sid='"+Id+"'";
                  

            System.out.println(query);

           ResultSet rs = stmt.executeQuery(query);
//           jobDetails = new JobDetails();
           int i=0;
           
          couDetails = new Student();

            if (rs.next()) {
             
                couDetails.setsid(rs.getInt(1));
                couDetails.setfname(rs.getString(2));
                couDetails.setcode(rs.getString(3)); 
                couDetails.setCourse(rs.getString(4)); 
                
                
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
    
}
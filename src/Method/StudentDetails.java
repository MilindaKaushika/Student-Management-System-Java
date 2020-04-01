/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

import Data.DBConnManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.Vector;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;



/**
 *
 * @author Home
 */
public class StudentDetails {
    
      private DBConnManager dbConnManager = null;



    public StudentDetails() {

        dbConnManager = new DBConnManager();
    }


    public boolean addStudent(Student cs) {

        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();




            String query = "INSERT INTO student(sid,fname,mname,lname,DOB,phone_num,code,image,Course,Email,username,password,usertype) " +
                    "VALUES('" + cs.getsid()+ "','" + cs.getfname()+ "','"+ cs.getmname()+ "','" + cs.getlname()+ "','" + cs.getDOB()+ "','" + cs.getphone_num()+ "','" + cs.getcode()+ "','" + cs.getimage()+ "','" + cs.getCourse()+ "','" + cs.getEmail()+ "',"
                    + "'" + cs.getusername()+ "','" + cs.getPassword()+ "','" + cs.getusertype()+ "')";

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
     public Student searchStudentDetails(int sid){
        
	  Student couDetails = null;
            Connection dbConn = null;
         

        try {

            //Connect to th DB
            dbConn = dbConnManager.Connect();
            java.sql.Statement stmt = dbConn.createStatement();

            String query ="SELECT sid,fname,Course FROM student WHERE sid='"+sid+"'";
                  

            System.out.println(query);

           ResultSet rs = stmt.executeQuery(query);
//           jobDetails = new JobDetails();
           int i=0;
           
          couDetails = new Student();

            if (rs.next()) {
               
                 couDetails.setsid(rs.getInt(1));
                couDetails.setfname(rs.getString(2));
                couDetails.setCourse(rs.getString(3));
                 
             
                
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
    public Vector getStudentDetails() {
     Vector<Vector<String>>  StudentDetailsVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT sid,fname,mname,lname,DOB,phone_num,code,Course,Email,username,password,usertype FROM student";
            ResultSet rs = stmt.executeQuery(query);
             StudentDetailsVector = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> StudentDetails = new Vector<String>();

                StudentDetails.add(rs.getString(1));
                StudentDetails.add(rs.getString(2));
                StudentDetails.add(rs.getString(3));
                StudentDetails.add(rs.getString(4));
                StudentDetails.add(rs.getString(5));
                StudentDetails.add(rs.getString(6));
                StudentDetails.add(rs.getString(7));
                StudentDetails.add(rs.getString(8));
                StudentDetails.add(rs.getString(9));
                StudentDetails.add(rs.getString(10));
                StudentDetails.add(rs.getString(11));
                StudentDetails.add(rs.getString(12));
                StudentDetailsVector.add(StudentDetails);

            }
        } catch (Exception e) {
            System.out.println(e + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }
              return StudentDetailsVector;
    }
     public Vector getStudentDetailss() {
     Vector<Vector<String>>  StudentDetailsVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT sid,fname,phone_num FROM student";
            ResultSet rs = stmt.executeQuery(query);
             StudentDetailsVector = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> StudentDetails = new Vector<String>();

                StudentDetails.add(rs.getString(1));
                StudentDetails.add(rs.getString(2));
                StudentDetails.add(rs.getString(3));
                
                StudentDetailsVector.add(StudentDetails);

            }
        } catch (Exception e) {
            System.out.println(e + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }
              return StudentDetailsVector;
    }
        public ArrayList getStudent() {

            int StudentId = 0;

        ArrayList StudentList = null;
        Connection dbConn = null;

        try {
            //Connect to th DB
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            //Select the JobCatNames
            String query = "SELECT DISTINCT sid FROM student";

            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

             StudentList = new ArrayList();

            while (rs.next()) {
                String catName = rs.getString(1);
                System.out.println(StudentId);
                StudentList.add(catName);
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Select query failed at JobCatNames");
        } finally {
            //Close the db connection
            dbConnManager.con_close(dbConn);
        }
        return StudentList;
    }
    
         public boolean updateStudent(Student cs) {
            
        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();
          java.sql.Statement stmt = dbConn.createStatement();


            String query = "UPDATE Student SET fname = '"+cs.getfname()+"',mname='"+ cs.getmname()+ "',lname='" + cs.getlname()+ "',DOB='" + cs.getDOB()+ "',phone_num='" + cs.getphone_num()+ "',"
                    + "code='" + cs.getcode()+ "',Course='" + cs.getCourse()+ "',Email='" + cs.getEmail()+ "',UserName='" + cs.getusername()+ "',PassWord='" + cs.getPassword()+ "',usertype='" + cs.getusertype()+ "' WHERE sid = '"+cs.getsid()+"'";

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
   public boolean deleteBrand(Student cs) {

        boolean result = false;
        Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            String query = "DELETE FROM Student WHERE sid= '" + cs.getsid()+ "'";
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
       public static boolean email_Validation(String email) {
        boolean status=false;
        
        String email_pattern ="^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(email_pattern);
        Matcher matcher =pattern.matcher(email);
        
        if(matcher.matches()){
           status=true;
        }else{
            status=false;
        }
        return status;
}
       public ArrayList getStudentsdetails () {

            int StudentId = 0;

        ArrayList StudentList = null;
        Connection dbConn = null;

        try {
            //Connect to th DB
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            //Select the JobCatNames
            String query = "SELECT DISTINCT fname FROM  student";

            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

             StudentList = new ArrayList();

            while (rs.next()) {
                String catName = rs.getString(1);
                System.out.println(StudentId);
                StudentList.add(catName);
            }

        } catch (SQLException sQLException) {
            System.out.println(sQLException + "-----------Select query failed at JobCatNames");
        } finally {
            //Close the db connection
            dbConnManager.con_close(dbConn);
        }
        return StudentList;
    }
        public Student searchDetails(String fname){
        
	  Student couDetails = null;
            Connection dbConn = null;
         

        try {

            //Connect to th DB
            dbConn = dbConnManager.Connect();
            java.sql.Statement stmt = dbConn.createStatement();

            String query ="SELECT fname,Course FROM student WHERE fname='"+fname+"'";
                  

            System.out.println(query);

           ResultSet rs = stmt.executeQuery(query);
//           jobDetails = new JobDetails();
           int i=0;
           
          couDetails = new Student();

            if (rs.next()) {
               
                couDetails.setfname(rs.getString(1));
 couDetails.setCourse(rs.getString(2));
              
                
                
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
        
    public boolean addAttendance(Student cs) {

        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();




            String query = "INSERT INTO attendance(sid,StudentName,Course,Date,Present) " +
                    "VALUES('" + cs.getsid()+ "','" + cs.getfname()+ "','" + cs.getCourse()+ "','" + cs.getDate()+ "','" + cs.getPresent()+ "')";

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
       public Vector getAttenDetails() {
     Vector<Vector<String>>  StudentDetailsVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT AtteId,sid,StudentName,Course,Date,Present FROM attendance";
            ResultSet rs = stmt.executeQuery(query);
             StudentDetailsVector = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> StudentDetails = new Vector<String>();

                StudentDetails.add(rs.getString(1));
                StudentDetails.add(rs.getString(2));
                StudentDetails.add(rs.getString(3));
                StudentDetails.add(rs.getString(4));
                StudentDetails.add(rs.getString(5));
                StudentDetails.add(rs.getString(6));
                StudentDetailsVector.add(StudentDetails);

            }
        } catch (Exception e) {
            System.out.println(e + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }
              return StudentDetailsVector;
    }      
}
  


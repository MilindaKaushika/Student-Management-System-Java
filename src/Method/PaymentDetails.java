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
import javax.swing.JOptionPane;
import java.util.ArrayList;
/**
 *
 * @author Home-PC
 */
public class PaymentDetails {
    private DBConnManager dbConnManager = null;  

  public PaymentDetails() {

        dbConnManager = new DBConnManager();
     }


    public boolean addPayment(Payment cs) {

        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();




            String query = "INSERT INTO Amount(StudentName,Course,Date,Amount) " +
                    "VALUES('" + cs.getStudentName()+ "','" + cs.getCourse()+ "','" + cs.getDate()+ "','" + cs.getAmount()+ "')";

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
  public Vector getPaymentDetails() {

        Vector<Vector<String>> PaymentDetailsVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT StudentName,Course,Date,Amount FROM Amount";
            ResultSet rs = stmt.executeQuery(query);
            PaymentDetailsVector = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> PaymentDetails = new Vector<String>();

                PaymentDetails.add(rs.getString(1));
                PaymentDetails.add(rs.getString(2));
                PaymentDetails.add(rs.getString(3));
                PaymentDetails.add(rs.getString(4));
                
               PaymentDetailsVector.add(PaymentDetails);

            }
        } catch (Exception e) {
            System.out.println(e + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }

        return PaymentDetailsVector;
    }
   public ArrayList getAmountdetails () {

            int AmountId = 0;

        ArrayList StudentList = null;
        Connection dbConn = null;

        try {
            //Connect to th DB
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();

            //Select the JobCatNames
            String query = "SELECT DISTINCT PId FROM  Amount";

            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);

             StudentList = new ArrayList();

            while (rs.next()) {
                String catName = rs.getString(1);
                System.out.println(AmountId);
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
    public boolean updatepayment(Payment c) {
            
        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();
          java.sql.Statement stmt = dbConn.createStatement();


            String query = "UPDATE Paymentbalance SET  StudentName='" + c.getStudentName()+ "',  Course='" + c.getCourse()+ "', Date='" + c.getDate()+ "',Amount='" + c.getAmount()+ "', Payed='" + c.getPayed()+ "', Balance='" + c.getBalance()+ "' WHERE PId = '"+c.getPId()+"'";
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
   public Payment searchPaymentDetails(String fname){
        
	  Payment couDetails = null;
            Connection dbConn = null;
         

        try {

            //Connect to th DB
            dbConn = dbConnManager.Connect();
            java.sql.Statement stmt = dbConn.createStatement();

            String query ="SELECT StudentName,Course,Amount FROM Amount WHERE StudentName='"+fname+"'";
                  

            System.out.println(query);

           ResultSet rs = stmt.executeQuery(query);
//           jobDetails = new JobDetails();
           int i=0;
           
          couDetails = new Payment();

            if (rs.next()) {
               
                couDetails.setStudentName(rs.getString(1));
                couDetails.setCourse(rs.getString(2));
                 couDetails.setAmount(rs.getInt(3)); 
             
                
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
     public Payment searchBalanceDetails(int PId){
        
	  Payment couDetails = null;
            Connection dbConn = null;
         

        try {

            //Connect to th DB
            dbConn = dbConnManager.Connect();
            java.sql.Statement stmt = dbConn.createStatement();

            String query ="SELECT PId,StudentName,Course,Amount FROM Amount WHERE PId='"+PId+"'";
                  

            System.out.println(query);

           ResultSet rs = stmt.executeQuery(query);
//           jobDetails = new JobDetails();
           int i=0;
           
          couDetails = new Payment();

            if (rs.next()) {
               
                 couDetails.setPId(rs.getInt(1));
                couDetails.setStudentName(rs.getString(2));
                couDetails.setCourse(rs.getString(3));
                 couDetails.setAmount(rs.getInt(4)); 
             
                
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
    public boolean addPaymentBalance(Payment cs) {

        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();




            String query = "INSERT INTO Paymentbalance(PId,StudentName,Course,Date,Amount,Payed,Balance) " +
                    "VALUES('" + cs.getPId()+ "','" + cs.getStudentName()+ "','" + cs.getCourse()+ "','" + cs.getDate()+ "','" + cs.getAmount()+ "','" + cs.getPayed()+ "','" + cs.getBalance()+ "')";

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
   public Vector getPaymentBalanceDetails() {

        Vector<Vector<String>> PaymentBalanceVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT BalanceID,PId,StudentName,Course,Date,Amount,Payed,Balance FROM Paymentbalance";
            ResultSet rs = stmt.executeQuery(query);
           PaymentBalanceVector = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> PaymentDetails = new Vector<String>();

                PaymentDetails.add(rs.getString(1));
                PaymentDetails.add(rs.getString(2));
                PaymentDetails.add(rs.getString(3));
                PaymentDetails.add(rs.getString(4));
                PaymentDetails.add(rs.getString(5));
                PaymentDetails.add(rs.getString(6));
                PaymentDetails.add(rs.getString(7));
                 PaymentDetails.add(rs.getString(8));     
              PaymentBalanceVector.add(PaymentDetails);

            }
        } catch (Exception e) {
            System.out.println(e + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }

        return PaymentBalanceVector;
    }  
}
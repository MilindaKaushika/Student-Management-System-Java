/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

import Data.DBConnManager;
import java.sql.Connection;
import java.util.Vector;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 * @author Home-PC
 */
public class Detailsdata {

    private DBConnManager dbConnManager = null;

public Detailsdata(){
 dbConnManager = new DBConnManager();
}
 public Vector getLecDetails() {
     Vector<Vector<String>> LeacDetailsVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT lec_id,mod_num FROM lec_conduct_module";
            ResultSet rs = stmt.executeQuery(query);
             LeacDetailsVector  = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> LeacDetails = new Vector<String>();

                LeacDetails.add(rs.getString(1));
                LeacDetails.add(rs.getString(2));

                LeacDetailsVector .add(LeacDetails);

            }
        } catch (Exception e) {
            System.out.println(e + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }
              return LeacDetailsVector ;
    }
 public Vector getst_moDetails() {
     Vector<Vector<String>> stmoDetailsVector = null;
        Connection dbConn = null;

        try {
            dbConn = DBConnManager.Connect();
             java.sql.Statement stmt = dbConn.createStatement();
            String query = "SELECT sid,mod_num FROM student_follows_modules";
            ResultSet rs = stmt.executeQuery(query);
            stmoDetailsVector = new Vector<Vector<String>>();

            while (rs.next()) {
                Vector<String> LeacDetails = new Vector<String>();

                LeacDetails.add(rs.getString(1));
                LeacDetails.add(rs.getString(2));

               stmoDetailsVector .add(LeacDetails);

            }
        } catch (Exception e) {
            System.out.println(e + "cannot get details");
        } finally {
            dbConnManager.con_close(dbConn);
        }
              return stmoDetailsVector ;
    }
  public boolean addLecmod(DdDetails cs) {

        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();


            String query = "INSERT INTO lec_conduct_module(lec_id,mod_num) " +
                    "VALUES( '" + cs.getlec_id()+ "','" + cs.getmod_num()+ "')";

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
   public boolean addslLecmod(Simodeldetails cs) {

        boolean result = false;
	Connection dbConn = null;

        try {
            dbConn = dbConnManager.Connect();

            java.sql.Statement stmt = dbConn.createStatement();


            String query = "INSERT INTO student_follows_modules(sid,mod_num) " +
                    "VALUES( '" + cs.getsid()+ "','" + cs.getmod_num()+ "')";

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
}
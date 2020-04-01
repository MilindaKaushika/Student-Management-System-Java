/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;

/**
 *
 * @author Home
 */
public class Lectures {
  private  int lec_id;
  private  String fname;
  private  String lname;
  private  String DOB;
  
  public Lectures() {
    }

    public Lectures(int lec_id ,String fname, String lname,String DOB) {
    
       this.lec_id=lec_id;
       this.fname=fname;
       this.lname=lname;
       this.DOB=DOB;
     
    } 

      public int getlec_id() {
      return lec_id; 
        
    }
        public void setlec_id(int lec_id) {
        this.lec_id = lec_id;
    }
      public String getfname() {
      return fname; 
   
}
       public void setfname(String fname) {
       this.fname = fname;
    }
      public String getlname() {
      return lname; 
}
      public void setlname(String lname) {
       this.lname = lname;
    }
      
      public String getDOB() {
      return DOB; 
      }
     public void setDOB(String DOB) {
       this.DOB = DOB;
    }    
    
}      

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Method;



/**
 *
 * @author Home-PC
 */
public class Payment {
  int PId;
   String StudentName;
  String Course;
   String Date;
       int Amount;
  int Payed;
     String Balance;
  
    public Payment() {
    }

    public Payment(String StudentName, String Course, String Date, int Amount) {
  
        this.StudentName = StudentName;
           this.Course = Course;
              this.Date = Date;
              this.Amount = Amount;
    }
 public Payment(int PId, String StudentName, String Course, String Date, int Amount,int Payed,String Balance) {
  
       this.PId = PId;
     this.StudentName = StudentName;
           this.Course = Course;
              this.Date = Date;
              this.Amount = Amount;
              this.Payed = Payed;
              this.Balance = Balance;
              
    }

  public int getPId() {
        return PId;
    }

    public void setPId(int PId) {
        this.PId = PId;
    }
    
    
    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String StudentName) {
        this.StudentName = StudentName;
    }
 public String getCourse() {
        return Course;
    }

    public void setCourse(String Course) {
        this.Course = Course;
    }
     public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
 public int getAmount() {
        return Amount;
    }

    public void setAmount(int Amount) {
        this.Amount = Amount;
    }

     public int getPayed() {
        return Payed;
    }

    public void setPayed(int Payed) {
        this.Payed = Payed;
    }
    
         public String getBalance() {
        return Balance;
    }

    public void setBalance(String Balance) {
        this.Balance = Balance;
    }
     


}
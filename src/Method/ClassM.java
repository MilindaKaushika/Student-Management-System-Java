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
public class ClassM {
    int Class_ID;
    String Class_Name;
    int lec_id;
    String Schedule;
    String Lecturer_Name;
    String Class_Time_In;
    String Class_Time_out;
  public ClassM() {
    }

    public ClassM (int Class_ID,String Class_Name,int lec_id,String Lecturer_Name,String Class_Time_In, String Class_Time_out, String Schedule) {
        this.Class_ID = Class_ID;
        this.Class_Name= Class_Name;
        this.lec_id = lec_id;
        this.Schedule = Schedule;
        this.Lecturer_Name = Lecturer_Name;  
        this.Class_Time_In = Class_Time_In;
        this.Class_Time_out=Class_Time_out;
        
    }

    public int getClass_ID() {
        return Class_ID;
    }

    public void setClass_ID(int Class_ID) {
        this.Class_ID= Class_ID;
    }
 public String getClass_Name() {
        return Class_Name;
    }

    public void setClass_Name(String Class_Name) {
        this.Class_Name= Class_Name;
    }
    public int getlec_id() {
        return lec_id;
    }

    public void setlec_id(int lec_id) {
        this.lec_id= lec_id;
    }
     public String getLecturer_Name() {
        return Lecturer_Name;
    }
    public void setLecturer_Name(String Lecturer_Name) {
        this.Lecturer_Name=Lecturer_Name;
    }
     public String getClass_Time_In() {
        return Class_Time_In;
    }
     public void setClass_Time_In(String Class_Time_In) {
        this.Class_Time_In=Class_Time_In;
    }
      public String getClass_Time_out() {
        return Class_Time_out;
    }
     public void setClass_Time_out(String Class_Time_out) {
        this.Class_Time_out=Class_Time_out;
    }
     public String getSchedule() {
        return Schedule;
    }  
       public void setSchedule(String Schedule) {
        this.Schedule=Schedule;
    }
  
}
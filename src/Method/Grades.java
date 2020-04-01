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
public class Grades {
   String Batch_No;
   int sid;
   String code;
   String Grade;
   String Semester;
   String Student_Name;
     String year;
     String Course;
    public Grades() {
    }

    public Grades(String Batch_No,int sid,String code,String Grade,String Semester,String Student_Name,String year,String Course) {
        this.Batch_No = Batch_No;
        this.sid = sid;
         this.code = code;
          this.Grade = Grade;
            this.Semester = Semester;
            this.Student_Name = Student_Name;         
            this.year = year;
            this.Course = Course;
    }

    public String getBatch_No() {
        return Batch_No;
    }

    public void setBatch_No(String Batch_No) {
        this.Batch_No = Batch_No;
    }

    public int getsid() {
        return sid;
    }

    public void setsid(int sid) {
        this.sid = sid;
    }
  public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code = code;
    }

 public String getGrade() {
        return Grade;
    }

    public void setGrade(String Grade) {
        this.Grade = Grade;
    }

public String getSemester() {
        return Semester;
    }

    public void setSemester(String Semester) {
        this.Semester = Semester;
    }
    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String Student_Name) {
        this.Student_Name = Student_Name;
    }
    public String getyear() {
        return year;
    }

    public void setyear(String year) {
        this.year = year;
    }
    
    public String getCourse() {
        return Course;
    }

    public void setCourse(String Course) {
        this.Course = Course;
    }

}
  


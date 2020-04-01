/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Method;

import java.util.Vector;

/**
 *
 * @author ESOFT
 */
public class Student {
    int sid;
    String fname;
    String mname;
    String lname;
    String DOB;
    String phone_num;
    String code;
    String image;
    String Course;
    String Email;
    String username;
    String password;
    String usertype;
      String Present;
 String Date;
        
    public Student() {
    }

    public Student(int sid, String fname,String mname,String lname,String DOB,String phone_num,String code,String image,String Course,String Email,String userName, String password,String usertype) {
        this.sid=sid;
        this.fname=fname;
        this.mname=mname;
        this.lname=lname;
        this.DOB = DOB;
        this.phone_num=phone_num;
        this.code =code;
        this.image=image;
        this.Course=Course;
        this.Email=Email;
        this.username = userName;
        this.password = password;
        this.usertype=usertype;
    }
 public Student(int sid, String fname,String Course,String Date,String Present) {
        this.sid=sid;
        this.fname=fname;
        this.Course=Course;
        this.Date=Date;
         this.Present=Present;
    }

    public int getsid() {
        return sid;
    }

    public void setsid(int sid) {
        this.sid = sid;
    }

    public String getfname() {
        return fname;
    }

    public void setfname(String fname) {
        this.fname = fname;
    }
    
    public String getmname() {
        return mname;
    }

    public void setmname(String mname) {
        this.mname = mname;
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
    
    public String getphone_num() {
        return phone_num;
    }

    public void setsphone_num(String phone_num) {
        this.phone_num = phone_num;
    }
 public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code= code;
    }

  public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image= image;
    }
        public String getCourse() {
        return Course;
    }

    public void setCourse(String Course) {
        this.Course= Course;
    }
           public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email= Email;
    }
      public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

     public String getusertype() {
        return usertype;
    }

    public void setusertype(String usertype) {
        this.usertype = usertype;
    }
      public String getPresent() {
        return Present;
    }

    public void setPresent(String Present) {
        this.Present = Present;
    }
        public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
}
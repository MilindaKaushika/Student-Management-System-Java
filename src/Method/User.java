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
public class User {

    String username;
    String password;
    String usertype;

    public User() {
    }
    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
    }
        public String getusertype() {
        return usertype;
    }

    public void setusertype(String usertype) {
        this.usertype = usertype;
    }
    public User( String usertype) {
   
         this.usertype = usertype;
    }
  
public User(String username, String password) {
        this.username = username;
        this.password = password;
   
    }
  
}

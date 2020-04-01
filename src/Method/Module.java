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
public class Module {

  
    int mod_num;
    String name;
    int num_of_units;
    String code;
    String course;
    public Module() {
    }

    public Module( int mod_num, String name, int num_of_units,String code,String course) {
        this.mod_num =  mod_num;
        this.name = name;
        this.num_of_units=num_of_units;
        this.code=code;
        this.course=course;
    }

    public int  getmod_num() {
        return  mod_num;
    }

    public void setmod_num(int  mod_num) {
        this. mod_num =  mod_num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 public int getnum_of_units() {
        return num_of_units;
    }

    public void setnum_of_units(int num_of_units) {
        this.num_of_units = num_of_units;
    }
    public String getcode() {
        return code;
    }

    public void setcode(String code) {
        this.code =code;
    }
    public String getcourse() {
        return course;
    }

    public void setcourse(String course) {
        this.course =course;
    }
   
}





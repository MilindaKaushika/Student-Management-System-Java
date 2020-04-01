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
public class Simodeldetails {
     int sid;
    int mod_num;

public Simodeldetails() {
    }

    public Simodeldetails(int sid, int mod_num) {
        this.sid = sid;
        this.mod_num = mod_num;
    
    }

    public int getsid() {
        return sid;
    }

    public void setsid(int sid) {
        this.sid = sid;
    }

    public int getmod_num() {
        return mod_num;
    }

    public void setmod_num(int mod_num) {
        this.mod_num = mod_num;
    }

}

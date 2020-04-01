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
public class DdDetails {
    int lec_id;
    int mod_num;


  public DdDetails() {
    }

    public DdDetails(int lec_id, int mod_num) {
        this.lec_id = lec_id;
        this.mod_num = mod_num;
    
    }

    public int getlec_id() {
        return lec_id;
    }

    public void setlec_id(int lec_id) {
        this.lec_id = lec_id;
    }

    public int getmod_num() {
        return mod_num;
    }

    public void setmod_num(int mod_num) {
        this.mod_num = mod_num;
    }

}

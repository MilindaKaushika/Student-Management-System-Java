/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SMS;

import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class testing {

    public static void main(String[] args) {
        try {
            Sender ss = new Sender("0788346116", "Dear Customer");

              ss.send();
        //  String receiveMessage = ss.receiveMessage();
         //   outputt.out(receiveMessage);
         //System.out.println(receiveMessage);
          //  JOptionPane.showMessageDialog(null, receiveMessage, "USSD", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

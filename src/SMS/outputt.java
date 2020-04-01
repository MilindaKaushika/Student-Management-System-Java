/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SMS;

import org.ajwcc.pduUtils.gsm3040.PduUtils;

/**
 *
 * @author dell
 */
public class outputt {
    public static void main(String[] args) {
        out("07914987050000F504039101F1000051212220247222564F7118343B82D0E9331A348797CB64D0BD4C478741EDB0BC0EBA97CBA0301D1D06BD40CE72D97D0EB34169F89B8EAE836647103A7D4683E6F072990C9A97EDE17C9A0DAAB3D965F939CC7601");
    }
    public static void out(String s) {

        org.ajwcc.pduUtils.gsm3040.Pdu pdu = new org.ajwcc.pduUtils.gsm3040.PduParser().parsePdu(s);
//        byte[] responded = PduUtils.pduToBytes(s);
//        String bite = PduUtils.bytesToBits(responded);
//        System.out.println(bite);
//
//        String resp = PduUtils.decode7bitEncoding(null, responded);
//        System.out.println(resp);

        System.out.println(pdu);

        // System.out.println(s);
    }

}

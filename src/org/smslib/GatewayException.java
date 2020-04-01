/*  1:   */ package org.smslib;

import javax.swing.JOptionPane;

/*  2:   */
/*  3:   */ public class GatewayException
        /*  4:   */ extends SMSLibException /*  5:   */ {
    /*  6:   */ private static final long serialVersionUID = 2L;
    /*  7:   */ public static int cou = 0;
    /*  8:   */ public GatewayException(String errorMessage) /*  9:   */ {
        /* 10:32 */ super(errorMessage);
        System.out.println("errrr - " + errorMessage);

        if (errorMessage.equals("Cannot add gateways while Service is running!")) {
            JOptionPane.showMessageDialog(null, "SMS Gateway Already Ruuning.\n Please Remove Dongle And Reconnect to correct Port OR update Port!", "System Alert", JOptionPane.WARNING_MESSAGE);
// System.exit(0);
        }

  //JOptionPane.showMessageDialog(null, errorMessage,"System Alert",JOptionPane.WARNING_MESSAGE);
        if (cou == 0) {
           
             
         // JOptionPane.showMessageDialog(null, cou,"System Alert",JOptionPane.WARNING_MESSAGE);
            
            if (errorMessage.equals("Comm library exception: java.lang.RuntimeException: javax.comm.NoSuchPortException")&&cou==0) {
                 cou = 1;
             JOptionPane.showMessageDialog(null, "Port Change Detected,Please Update Port or Connect Dongle To Correct Port\nOR Not Connect Dongle To Computer\nTo Update Port Go HOME -> Modem Setting and Update Port", "System Alert", JOptionPane.WARNING_MESSAGE);
               
            }
             

        }
        /* 11:   */    }
    /* 12:   */
    /* 13:   */ public GatewayException() {
    }
    /* 14:   */
    /* 15:   */ public GatewayException(Throwable e) /* 16:   */ {
        /* 17:42 */ super(e.getMessage());
        /* 18:   */    }
    /* 19:   */
    /* 20:   */ public GatewayException(String errorMessage, Throwable e) /* 21:   */ {
        /* 22:47 */ super(errorMessage);
        /* 23:   */    }
    /* 24:   */ }



/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.GatewayException

 * JD-Core Version:    0.7.0.1

 */

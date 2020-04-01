/*  1:   */ package org.smslib.modem;
/*  2:   */
/*  3:   */ public class SerialModemGateway
        /*  4:   */ extends ModemGateway /*  5:   */ {
    /*  6:   */ public SerialModemGateway(String id, String comPort, int baudRate, String manufacturer, String model) /*  7:   */ {

        // System.out.println("ooooooooooooooooooooo");
        /*  8:45 */ super(ModemGateway.ModemTypes.SERIAL, id, comPort, baudRate, manufacturer, model);
        //System.out.println("ooooooooooooooooooooo");
        //

        /*  9:   */    }
    /* 10:   */ }

/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.modem.SerialModemGateway

 * JD-Core Version:    0.7.0.1

 */

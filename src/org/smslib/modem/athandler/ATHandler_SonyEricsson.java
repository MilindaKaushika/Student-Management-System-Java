/*  1:   */ package org.smslib.modem.athandler;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.smslib.GatewayException;
/*  5:   */ import org.smslib.TimeoutException;
/*  6:   */ import org.smslib.modem.AModemDriver;
/*  7:   */ import org.smslib.modem.ModemGateway;
/*  8:   */ 
/*  9:   */ public class ATHandler_SonyEricsson
/* 10:   */   extends ATHandler
/* 11:   */ {
/* 12:   */   public ATHandler_SonyEricsson(ModemGateway myGateway)
/* 13:   */   {
/* 14:32 */     super(myGateway);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public boolean setPduProtocol()
/* 18:   */     throws TimeoutException, GatewayException, IOException, InterruptedException
/* 19:   */   {
/* 20:44 */     getModemDriver().write("AT+CMGF=0\r");
/* 21:45 */     return getModemDriver().getResponse().indexOf("OK") >= 0;
/* 22:   */   }
/* 23:   */   
/* 24:   */   protected String formatUSSDCommand(String presentation, String ussdCommand, String dcs)
/* 25:   */   {
/* 26:56 */     return super.formatUSSDCommand(presentation, ussdCommand, "15");
/* 27:   */   }
/* 28:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.modem.athandler.ATHandler_SonyEricsson
 * JD-Core Version:    0.7.0.1
 */
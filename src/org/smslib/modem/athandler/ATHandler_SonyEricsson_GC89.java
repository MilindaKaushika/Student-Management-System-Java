/*  1:   */ package org.smslib.modem.athandler;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.smslib.GatewayException;
/*  5:   */ import org.smslib.Service;
/*  6:   */ import org.smslib.Settings;
/*  7:   */ import org.smslib.TimeoutException;
/*  8:   */ import org.smslib.modem.AModemDriver;
/*  9:   */ import org.smslib.modem.ModemGateway;
/* 10:   */ 
/* 11:   */ public class ATHandler_SonyEricsson_GC89
/* 12:   */   extends ATHandler_SonyEricsson
/* 13:   */ {
/* 14:   */   public ATHandler_SonyEricsson_GC89(ModemGateway myGateway)
/* 15:   */   {
/* 16:33 */     super(myGateway);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean setPduProtocol()
/* 20:   */     throws TimeoutException, GatewayException, IOException, InterruptedException
/* 21:   */   {
/* 22:45 */     getModemDriver().write("AT+CMGF=0\r");
/* 23:46 */     return getModemDriver().getResponse().indexOf("OK") >= 0;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void reset()
/* 27:   */     throws TimeoutException, GatewayException, IOException, InterruptedException
/* 28:   */   {
/* 29:52 */     super.reset();
/* 30:53 */     getModemDriver().write("AT+CFUN=1\r");
/* 31:54 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT_AFTER_RESET);
/* 32:55 */     getModemDriver().clearBuffer();
/* 33:   */   }
/* 34:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.modem.athandler.ATHandler_SonyEricsson_GC89
 * JD-Core Version:    0.7.0.1
 */
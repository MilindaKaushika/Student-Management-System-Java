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
/* 11:   */ public class ATHandler_Siemens_HC25
/* 12:   */   extends ATHandler
/* 13:   */ {
/* 14:   */   public ATHandler_Siemens_HC25(ModemGateway myGateway)
/* 15:   */   {
/* 16:49 */     super(myGateway);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void sync()
/* 20:   */     throws IOException, InterruptedException
/* 21:   */   {
/* 22:56 */     getModemDriver().write("ATZ\r");
/* 23:57 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT);
/* 24:   */     
/* 25:59 */     getModemDriver().write("AT+CSCA?\r");
/* 26:60 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT);
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void reset()
/* 30:   */     throws TimeoutException, GatewayException, IOException, InterruptedException
/* 31:   */   {
/* 32:67 */     getModemDriver().write("\033");
/* 33:68 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT);
/* 34:69 */     getModemDriver().write("+++");
/* 35:70 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT);
/* 36:71 */     getModemDriver().write("ATZ");
/* 37:72 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT);
/* 38:73 */     getModemDriver().write("AT+CSCA?\r");
/* 39:74 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT);
/* 40:75 */     getModemDriver().clearBuffer();
/* 41:   */   }
/* 42:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.modem.athandler.ATHandler_Siemens_HC25
 * JD-Core Version:    0.7.0.1
 */
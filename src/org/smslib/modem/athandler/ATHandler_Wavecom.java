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
/* 11:   */ public class ATHandler_Wavecom
/* 12:   */   extends ATHandler
/* 13:   */ {
/* 14:   */   public ATHandler_Wavecom(ModemGateway myGateway)
/* 15:   */   {
/* 16:36 */     super(myGateway);
/* 17:37 */     setStorageLocations("SMSR");
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void reset()
/* 21:   */     throws TimeoutException, GatewayException, IOException, InterruptedException
/* 22:   */   {
/* 23:43 */     super.reset();
/* 24:44 */     getModemDriver().write("AT+CFUN=1\r");
/* 25:45 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT_AFTER_RESET);
/* 26:46 */     getModemDriver().clearBuffer();
/* 27:   */   }
/* 28:   */   
/* 29:   */   public void done()
/* 30:   */     throws TimeoutException, GatewayException, IOException, InterruptedException
/* 31:   */   {
/* 32:52 */     getModemDriver().write("AT+WATH=31\r");
/* 33:53 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT);
/* 34:54 */     getModemDriver().write("AT+CFUN=0\r");
/* 35:55 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT_AFTER_RESET);
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.modem.athandler.ATHandler_Wavecom
 * JD-Core Version:    0.7.0.1
 */
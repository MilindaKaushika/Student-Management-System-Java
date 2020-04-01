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
/* 11:   */ public class ATHandler_Siemens_MC35i_FD
/* 12:   */   extends ATHandler_Siemens_MC35i
/* 13:   */ {
/* 14:   */   public ATHandler_Siemens_MC35i_FD(ModemGateway myGateway)
/* 15:   */   {
/* 16:33 */     super(myGateway);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void sync()
/* 20:   */     throws IOException, InterruptedException
/* 21:   */   {
/* 22:39 */     getModemDriver().write("AT&F\r");
/* 23:40 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public void reset()
/* 27:   */     throws TimeoutException, GatewayException, IOException, InterruptedException
/* 28:   */   {
/* 29:46 */     getModemDriver().write("\033");
/* 30:47 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT);
/* 31:48 */     getModemDriver().write("+++");
/* 32:49 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT);
/* 33:50 */     getModemDriver().write("AT&F");
/* 34:51 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT);
/* 35:52 */     getModemDriver().clearBuffer();
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.modem.athandler.ATHandler_Siemens_MC35i_FD
 * JD-Core Version:    0.7.0.1
 */
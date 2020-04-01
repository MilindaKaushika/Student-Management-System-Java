/*  1:   */ package org.smslib.modem.athandler;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.smslib.GatewayException;
/*  5:   */ import org.smslib.Service;
/*  6:   */ import org.smslib.Settings;
/*  7:   */ import org.smslib.TimeoutException;
/*  8:   */ import org.smslib.helper.Logger;
/*  9:   */ import org.smslib.modem.AModemDriver;
/* 10:   */ import org.smslib.modem.ModemGateway;
/* 11:   */ 
/* 12:   */ public class ATHandler_Siemens_MC35i
/* 13:   */   extends ATHandler
/* 14:   */ {
/* 15:   */   public static final int RETRIES = 5;
/* 16:   */   public static final int WAIT = 1500;
/* 17:   */   
/* 18:   */   public ATHandler_Siemens_MC35i(ModemGateway myGateway)
/* 19:   */   {
/* 20:37 */     super(myGateway);
/* 21:   */   }
/* 22:   */   
/* 23:   */   public void init()
/* 24:   */     throws TimeoutException, GatewayException, IOException, InterruptedException
/* 25:   */   {
/* 26:43 */     for (int i = 0; i < 5; i++)
/* 27:   */     {
/* 28:45 */       getModemDriver().write("AT+CLIP=1\r");
/* 29:46 */       getModemDriver().getResponse();
/* 30:47 */       if (getModemDriver().isOk()) {
/* 31:   */         break;
/* 32:   */       }
/* 33:48 */       Logger.getInstance().logDebug("Modem didn't respond correctly on AT+CLIP. Retrying...", null, getGateway().getGatewayId());
/* 34:49 */       Thread.sleep(1500L);
/* 35:   */     }
/* 36:51 */     if (!getModemDriver().isOk()) {
/* 37:52 */       Logger.getInstance().logDebug("Modem didn't respond correctly on AT+CLIP correctly on 5 attemts. Giving up.", null, getGateway().getGatewayId());
/* 38:   */     }
/* 39:53 */     getModemDriver().write("AT+COPS=0\r");
/* 40:54 */     getModemDriver().getResponse();
/* 41:   */   }
/* 42:   */   
/* 43:   */   public void echoOff()
/* 44:   */     throws IOException, InterruptedException
/* 45:   */   {
/* 46:60 */     getModemDriver().write("ATV1\r");
/* 47:61 */     getModemDriver().write("ATQ0\r");
/* 48:62 */     getModemDriver().write("ATE0\r");
/* 49:63 */     Thread.sleep(Service.getInstance().getSettings().AT_WAIT);
/* 50:64 */     getModemDriver().clearBuffer();
/* 51:   */   }
/* 52:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.modem.athandler.ATHandler_Siemens_MC35i
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.smslib.modem.athandler;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.smslib.GatewayException;
/*  5:   */ import org.smslib.TimeoutException;
/*  6:   */ import org.smslib.modem.AModemDriver;
/*  7:   */ import org.smslib.modem.ModemGateway;
/*  8:   */ 
/*  9:   */ public class ATHandler_MultiTech
/* 10:   */   extends ATHandler
/* 11:   */ {
/* 12:   */   public ATHandler_MultiTech(ModemGateway myGateway)
/* 13:   */   {
/* 14:35 */     super(myGateway);
/* 15:36 */     this.terminators[11] = "\\+CRING:\\s*VOICE\\s";
/* 16:37 */     setStorageLocations("SM");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public void init()
/* 20:   */     throws TimeoutException, GatewayException, IOException, InterruptedException
/* 21:   */   {
/* 22:43 */     super.init();
/* 23:44 */     getModemDriver().write("AT+WIND=0\r");
/* 24:   */   }
/* 25:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.modem.athandler.ATHandler_MultiTech
 * JD-Core Version:    0.7.0.1
 */
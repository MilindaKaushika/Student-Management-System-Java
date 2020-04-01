/*  1:   */ package org.smslib.modem.athandler;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.smslib.GatewayException;
/*  5:   */ import org.smslib.TimeoutException;
/*  6:   */ import org.smslib.modem.AModemDriver;
/*  7:   */ import org.smslib.modem.ModemGateway;
/*  8:   */ 
/*  9:   */ public class ATHandler_Huawei
/* 10:   */   extends ATHandler
/* 11:   */ {
/* 12:   */   public ATHandler_Huawei(ModemGateway myGateway)
/* 13:   */   {
/* 14:35 */     super(myGateway);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void init()
/* 18:   */     throws TimeoutException, GatewayException, IOException, InterruptedException
/* 19:   */   {
/* 20:41 */     getModemDriver().write("AT^CURC=0\r");
/* 21:42 */     getModemDriver().getResponse();
/* 22:43 */     getModemDriver().write("AT+CLIP=1\r");
/* 23:44 */     getModemDriver().getResponse();
/* 24:   */   }
/* 25:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.modem.athandler.ATHandler_Huawei
 * JD-Core Version:    0.7.0.1
 */
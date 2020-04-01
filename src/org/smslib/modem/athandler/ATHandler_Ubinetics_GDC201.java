/*  1:   */ package org.smslib.modem.athandler;
/*  2:   */ 
/*  3:   */ import java.io.IOException;
/*  4:   */ import org.smslib.GatewayException;
/*  5:   */ import org.smslib.TimeoutException;
/*  6:   */ import org.smslib.modem.AModemDriver;
/*  7:   */ import org.smslib.modem.ModemGateway;
/*  8:   */ 
/*  9:   */ public class ATHandler_Ubinetics_GDC201
/* 10:   */   extends ATHandler
/* 11:   */ {
/* 12:   */   public ATHandler_Ubinetics_GDC201(ModemGateway myGateway)
/* 13:   */   {
/* 14:40 */     super(myGateway);
/* 15:   */     
/* 16:42 */     setStorageLocations("SM");
/* 17:   */   }
/* 18:   */   
/* 19:   */   public boolean switchStorageLocation(String mem)
/* 20:   */     throws TimeoutException, GatewayException, IOException, InterruptedException
/* 21:   */   {
/* 22:61 */     getModemDriver().write("AT+CPMS=\"" + mem + "\",\"" + mem + "\",\"" + mem + "\"\r");
/* 23:62 */     getModemDriver().getResponse();
/* 24:63 */     return getModemDriver().isOk();
/* 25:   */   }
/* 26:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.modem.athandler.ATHandler_Ubinetics_GDC201
 * JD-Core Version:    0.7.0.1
 */
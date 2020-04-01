/*  1:   */ package org.smslib.modem.athandler;
/*  2:   */ 
/*  3:   */ import org.smslib.modem.ModemGateway;
/*  4:   */ 
/*  5:   */ public class ATHandler_Teltonika_ModemUSB
/*  6:   */   extends ATHandler
/*  7:   */ {
/*  8:   */   public ATHandler_Teltonika_ModemUSB(ModemGateway myGateway)
/*  9:   */   {
/* 10:30 */     super(myGateway);
/* 11:31 */     setStorageLocations("SMME");
/* 12:   */   }
/* 13:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.modem.athandler.ATHandler_Teltonika_ModemUSB
 * JD-Core Version:    0.7.0.1
 */
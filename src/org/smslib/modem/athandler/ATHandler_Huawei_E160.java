/*  1:   */ package org.smslib.modem.athandler;
/*  2:   */ 
/*  3:   */ import org.ajwcc.pduUtils.gsm3040.PduUtils;
/*  4:   */ import org.smslib.modem.ModemGateway;
/*  5:   */ 
/*  6:   */ public class ATHandler_Huawei_E160
/*  7:   */   extends ATHandler_Huawei
/*  8:   */ {
/*  9:   */   public ATHandler_Huawei_E160(ModemGateway myGateway)
/* 10:   */   {
/* 11:33 */     super(myGateway);
/* 12:34 */     setStorageLocations("SMME");
/* 13:   */   }
/* 14:   */   
/* 15:   */   protected String formatUSSDCommand(String presentation, String ussdCommand, String dcs)
/* 16:   */   {
/* 17:45 */     byte[] textSeptets = PduUtils.stringToUnencodedSeptets(ussdCommand);
/* 18:46 */     byte[] alphaNumBytes = PduUtils.unencodedSeptetsToEncodedSeptets(textSeptets);
/* 19:47 */     String ussdCommandEncoded = PduUtils.bytesToPdu(alphaNumBytes);
/* 20:48 */     return super.formatUSSDCommand(presentation, ussdCommandEncoded, "15");
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String formatUSSDResponse(String ussdResponse)
/* 24:   */   {
/* 25:57 */     byte[] responseEncodedSeptets = PduUtils.pduToBytes(ussdResponse);
/* 26:58 */     byte[] responseUnencodedSeptets = PduUtils.encodedSeptetsToUnencodedSeptets(responseEncodedSeptets);
/* 27:59 */     return PduUtils.unencodedSeptetsToString(responseUnencodedSeptets);
/* 28:   */   }
/* 29:   */ }



/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.modem.athandler.ATHandler_Huawei_E160

 * JD-Core Version:    0.7.0.1

 */
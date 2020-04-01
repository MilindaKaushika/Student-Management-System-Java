/*   1:    */ package org.ajwcc.pduUtils.gsm3040;
/*   2:    */ 
/*   3:    */ import org.ajwcc.pduUtils.wappush.WapSiPdu;
/*   4:    */ 
/*   5:    */ public class PduFactory
/*   6:    */ {
/*   7:    */   public static SmsSubmitPdu newSmsSubmitPdu()
/*   8:    */   {
/*   9: 27 */     int additionalFields = 16;
/*  10: 28 */     return newSmsSubmitPdu(additionalFields);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public static SmsSubmitPdu newSmsSubmitPdu(int additionalFields)
/*  14:    */   {
/*  15: 34 */     int firstOctet = 0x1 | additionalFields;
/*  16: 35 */     return (SmsSubmitPdu)createPdu(firstOctet);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public static WapSiPdu newWapSiPdu()
/*  20:    */   {
/*  21: 41 */     int additionalFields = 16;
/*  22: 42 */     return newWapSiPdu(additionalFields);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public static WapSiPdu newWapSiPdu(int additionalFields)
/*  26:    */   {
/*  27: 49 */     int firstOctet = 0x1 | additionalFields;
/*  28: 50 */     int messageType = getFirstOctetField(firstOctet, 252);
/*  29:    */     WapSiPdu pdu;
/*  30: 51 */     switch (messageType)
/*  31:    */     {
/*  32:    */     case 1: 
/*  33: 54 */       pdu = new WapSiPdu();
/*  34: 55 */       break;
/*  35:    */     default: 
/*  36: 57 */       throw new RuntimeException("Invalid TP-MTI value: " + messageType);
/*  37:    */     }
/*  38: 59 */     pdu.setFirstOctet(firstOctet);
/*  39: 60 */     return pdu;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public static SmsSubmitPdu newSmsDeliveryPdu()
/*  43:    */   {
/*  44: 67 */     int firstOctet = 4;
/*  45: 68 */     return (SmsSubmitPdu)createPdu(firstOctet);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static SmsDeliveryPdu newSmsDeliveryPdu(int additionalFields)
/*  49:    */   {
/*  50: 74 */     int firstOctet = 0x0 | additionalFields;
/*  51: 75 */     return (SmsDeliveryPdu)createPdu(firstOctet);
/*  52:    */   }
/*  53:    */   
/*  54:    */   private static int getFirstOctetField(int firstOctet, int fieldName)
/*  55:    */   {
/*  56: 80 */     return firstOctet & (fieldName ^ 0xFFFFFFFF);
/*  57:    */   }
/*  58:    */   
/*  59:    */   public static Pdu createPdu(int firstOctet)
/*  60:    */   {
/*  61: 87 */     Pdu pdu = null;
/*  62: 88 */     int messageType = getFirstOctetField(firstOctet, 252);
/*  63: 89 */     switch (messageType)
/*  64:    */     {
/*  65:    */     case 0: 
/*  66: 92 */       pdu = new SmsDeliveryPdu();
/*  67: 93 */       break;
/*  68:    */     case 2: 
/*  69: 95 */       pdu = new SmsStatusReportPdu();
/*  70: 96 */       break;
/*  71:    */     case 1: 
/*  72: 98 */       pdu = new SmsSubmitPdu();
/*  73: 99 */       break;
/*  74:    */     default: 
/*  75:101 */       throw new RuntimeException("Invalid TP-MTI value: " + messageType);
/*  76:    */     }
/*  77:105 */     pdu.setFirstOctet(firstOctet);
/*  78:106 */     return pdu;
/*  79:    */   }
/*  80:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.ajwcc.pduUtils.gsm3040.PduFactory
 * JD-Core Version:    0.7.0.1
 */
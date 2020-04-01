/*  1:   */ package org.smslib;
/*  2:   */ 
/*  3:   */ import org.ajwcc.pduUtils.gsm3040.PduUtils;
/*  4:   */ import org.ajwcc.pduUtils.gsm3040.SmsDeliveryPdu;
/*  5:   */ 
/*  6:   */ public class InboundBinaryMessage
/*  7:   */   extends InboundMessage
/*  8:   */ {
/*  9:   */   private static final long serialVersionUID = 2L;
/* 10:   */   private byte[] dataBytes;
/* 11:   */   
/* 12:   */   public String getText()
/* 13:   */   {
/* 14:38 */     throw new RuntimeException("getText() not supported");
/* 15:   */   }
/* 16:   */   
/* 17:   */   public void setText(String s)
/* 18:   */   {
/* 19:44 */     throw new RuntimeException("setText() not supported");
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void addText(String s)
/* 23:   */   {
/* 24:50 */     throw new RuntimeException("addText() not supported");
/* 25:   */   }
/* 26:   */   
/* 27:   */   public byte[] getDataBytes()
/* 28:   */   {
/* 29:55 */     return this.dataBytes;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void setDataBytes(byte[] myDataBytes)
/* 33:   */   {
/* 34:60 */     this.dataBytes = myDataBytes;
/* 35:61 */     setEncoding(Message.MessageEncodings.ENC8BIT);
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void addDataBytes(byte[] myDataBytes)
/* 39:   */   {
/* 40:66 */     byte[] newArray = new byte[this.dataBytes.length + myDataBytes.length];
/* 41:67 */     System.arraycopy(this.dataBytes, 0, newArray, 0, this.dataBytes.length);
/* 42:68 */     System.arraycopy(myDataBytes, 0, newArray, this.dataBytes.length, myDataBytes.length);
/* 43:69 */     this.dataBytes = newArray;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public InboundBinaryMessage(SmsDeliveryPdu pdu, int memIndex, String memLocation)
/* 47:   */   {
/* 48:74 */     super(pdu, memIndex, memLocation);
/* 49:   */   }
/* 50:   */   
/* 51:   */   protected void extractData(SmsDeliveryPdu pdu)
/* 52:   */   {
/* 53:81 */     if (pdu.isBinary()) {
/* 54:83 */       setDataBytes(pdu.getUserDataAsBytes());
/* 55:   */     } else {
/* 56:87 */       throw new RuntimeException("Trying to apply a text pdu to an InboundBinaryMessage");
/* 57:   */     }
/* 58:   */   }
/* 59:   */   
/* 60:   */   public String getPduUserData()
/* 61:   */   {
/* 62:94 */     return PduUtils.bytesToPdu(getDataBytes());
/* 63:   */   }
/* 64:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.InboundBinaryMessage
 * JD-Core Version:    0.7.0.1
 */
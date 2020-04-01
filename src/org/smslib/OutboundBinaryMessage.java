/*   1:    */ package org.smslib;
/*   2:    */ 
/*   3:    */ import org.ajwcc.pduUtils.gsm3040.SmsSubmitPdu;
/*   4:    */ 
/*   5:    */ public class OutboundBinaryMessage
/*   6:    */   extends OutboundMessage
/*   7:    */ {
/*   8:    */   private static final long serialVersionUID = 2L;
/*   9:    */   private byte[] dataBytes;
/*  10:    */   
/*  11:    */   public OutboundBinaryMessage()
/*  12:    */   {
/*  13: 37 */     setEncoding(Message.MessageEncodings.ENC8BIT);
/*  14:    */   }
/*  15:    */   
/*  16:    */   public OutboundBinaryMessage(String myRecipient, byte[] bytes)
/*  17:    */   {
/*  18: 51 */     this.recipient = myRecipient;
/*  19: 52 */     setEncoding(Message.MessageEncodings.ENC8BIT);
/*  20: 53 */     setDataBytes(bytes);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public String getText()
/*  24:    */   {
/*  25: 59 */     throw new RuntimeException("getText() not supported");
/*  26:    */   }
/*  27:    */   
/*  28:    */   public void setText(String s)
/*  29:    */   {
/*  30: 65 */     throw new RuntimeException("setText() not supported");
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void addText(String s)
/*  34:    */   {
/*  35: 71 */     throw new RuntimeException("addText() not supported");
/*  36:    */   }
/*  37:    */   
/*  38:    */   public byte[] getDataBytes()
/*  39:    */   {
/*  40: 76 */     return this.dataBytes;
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void setDataBytes(byte[] myDataBytes)
/*  44:    */   {
/*  45: 81 */     this.dataBytes = myDataBytes;
/*  46: 82 */     setEncoding(Message.MessageEncodings.ENC8BIT);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void addDataBytes(byte[] myDataBytes)
/*  50:    */   {
/*  51: 87 */     if (this.dataBytes != null)
/*  52:    */     {
/*  53: 89 */       byte[] newArray = new byte[this.dataBytes.length + myDataBytes.length];
/*  54: 90 */       System.arraycopy(this.dataBytes, 0, newArray, 0, this.dataBytes.length);
/*  55: 91 */       System.arraycopy(myDataBytes, 0, newArray, this.dataBytes.length, this.dataBytes.length);
/*  56: 92 */       setDataBytes(newArray);
/*  57:    */     }
/*  58:    */     else
/*  59:    */     {
/*  60: 96 */       setDataBytes(this.dataBytes);
/*  61:    */     }
/*  62:    */   }
/*  63:    */   
/*  64:    */   protected void setPduPayload(SmsSubmitPdu pdu)
/*  65:    */   {
/*  66:103 */     pdu.setDataBytes(this.dataBytes);
/*  67:    */   }
/*  68:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.OutboundBinaryMessage
 * JD-Core Version:    0.7.0.1
 */
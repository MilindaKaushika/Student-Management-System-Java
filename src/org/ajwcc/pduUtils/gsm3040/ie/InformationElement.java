/*  1:   */ package org.ajwcc.pduUtils.gsm3040.ie;
/*  2:   */ 
/*  3:   */ import org.ajwcc.pduUtils.gsm3040.PduUtils;
/*  4:   */ 
/*  5:   */ public class InformationElement
/*  6:   */ {
/*  7:   */   private byte identifier;
/*  8:   */   private byte[] data;
/*  9:   */   
/* 10:   */   InformationElement(byte id, byte[] ieData)
/* 11:   */   {
/* 12:33 */     initialize(id, ieData);
/* 13:   */   }
/* 14:   */   
/* 15:   */   InformationElement() {}
/* 16:   */   
/* 17:   */   void initialize(byte id, byte[] ieData)
/* 18:   */   {
/* 19:43 */     this.identifier = id;
/* 20:44 */     this.data = ieData;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int getIdentifier()
/* 24:   */   {
/* 25:49 */     return this.identifier & 0xFF;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int getLength()
/* 29:   */   {
/* 30:54 */     return this.data.length;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public byte[] getData()
/* 34:   */   {
/* 35:59 */     return this.data;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public String toString()
/* 39:   */   {
/* 40:65 */     StringBuffer sb = new StringBuffer();
/* 41:66 */     sb.append(getClass().getSimpleName() + "[");
/* 42:67 */     sb.append(PduUtils.byteToPdu(this.identifier));
/* 43:68 */     sb.append(", ");
/* 44:69 */     sb.append(PduUtils.byteToPdu(this.data.length));
/* 45:70 */     sb.append(", ");
/* 46:71 */     sb.append(PduUtils.bytesToPdu(this.data));
/* 47:72 */     sb.append("]");
/* 48:73 */     return sb.toString();
/* 49:   */   }
/* 50:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.ajwcc.pduUtils.gsm3040.ie.InformationElement
 * JD-Core Version:    0.7.0.1
 */
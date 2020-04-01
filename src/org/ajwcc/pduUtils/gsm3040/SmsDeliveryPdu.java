/*   1:    */ package org.ajwcc.pduUtils.gsm3040;
/*   2:    */ 
/*   3:    */ import java.util.Calendar;
/*   4:    */ import java.util.Date;
/*   5:    */ 
/*   6:    */ public class SmsDeliveryPdu
/*   7:    */   extends Pdu
/*   8:    */ {
/*   9:    */   private Calendar timestamp;
/*  10:    */   
/*  11:    */   public void setTimestamp(Calendar timestamp)
/*  12:    */   {
/*  13: 36 */     this.timestamp = timestamp;
/*  14:    */   }
/*  15:    */   
/*  16:    */   public Date getTimestamp()
/*  17:    */   {
/*  18: 41 */     return this.timestamp.getTime();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public Calendar getTimestampAsCalendar()
/*  22:    */   {
/*  23: 46 */     return this.timestamp;
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void setTpMms(int value)
/*  27:    */   {
/*  28: 54 */     checkTpMti(new int[] { 0, 2 });
/*  29:    */     
/*  30: 56 */     setFirstOctetField(251, value, new int[] { 4, 0 });
/*  31:    */   }
/*  32:    */   
/*  33:    */   public boolean hasTpMms()
/*  34:    */   {
/*  35: 61 */     checkTpMti(new int[] { 0, 2 });
/*  36:    */     
/*  37: 63 */     return getFirstOctetField(251) == 4;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setTpSri(int value)
/*  41:    */   {
/*  42: 68 */     setFirstOctetField(223, value, new int[] { 0, 32 });
/*  43:    */   }
/*  44:    */   
/*  45:    */   public boolean hasTpSri()
/*  46:    */   {
/*  47: 73 */     return getFirstOctetField(223) == 32;
/*  48:    */   }
/*  49:    */   
/*  50:    */   protected String pduSubclassInfo()
/*  51:    */   {
/*  52: 79 */     StringBuffer sb = new StringBuffer();
/*  53: 82 */     if (getAddress() != null)
/*  54:    */     {
/*  55: 84 */       sb.append("Originator Address: [Length: " + getAddress().length() + " (" + PduUtils.byteToPdu((byte)getAddress().length()) + ")");
/*  56: 85 */       sb.append(", Type: " + PduUtils.byteToPdu(getAddressType()) + " (" + PduUtils.byteToBits((byte)getAddressType()) + ")");
/*  57: 86 */       sb.append(", Address: " + getAddress());
/*  58: 87 */       sb.append("]");
/*  59:    */     }
/*  60:    */     else
/*  61:    */     {
/*  62: 91 */       sb.append("Originator Address: [Length: 0");
/*  63: 92 */       sb.append(", Type: " + PduUtils.byteToPdu(getAddressType()) + " (" + PduUtils.byteToBits((byte)getAddressType()) + ")");
/*  64: 93 */       sb.append("]");
/*  65:    */     }
/*  66: 96 */     sb.append("\n");
/*  67:    */     
/*  68: 98 */     sb.append("TP-PID: " + PduUtils.byteToPdu(getProtocolIdentifier()) + " (" + PduUtils.byteToBits((byte)getProtocolIdentifier()) + ")");
/*  69: 99 */     sb.append("\n");
/*  70:    */     
/*  71:101 */     sb.append("TP-DCS: " + PduUtils.byteToPdu(getDataCodingScheme()) + " (" + PduUtils.decodeDataCodingScheme(this) + ") (" + PduUtils.byteToBits((byte)getDataCodingScheme()) + ")");
/*  72:102 */     sb.append("\n");
/*  73:    */     
/*  74:104 */     sb.append("TP-SCTS: " + formatTimestamp(getTimestampAsCalendar()));
/*  75:105 */     sb.append("\n");
/*  76:106 */     return sb.toString();
/*  77:    */   }
/*  78:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.ajwcc.pduUtils.gsm3040.SmsDeliveryPdu
 * JD-Core Version:    0.7.0.1
 */
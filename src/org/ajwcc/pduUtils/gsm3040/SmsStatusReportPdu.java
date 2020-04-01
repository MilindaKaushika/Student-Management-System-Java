/*   1:    */ package org.ajwcc.pduUtils.gsm3040;
/*   2:    */ 
/*   3:    */ import java.util.Calendar;
/*   4:    */ import java.util.Date;
/*   5:    */ 
/*   6:    */ public class SmsStatusReportPdu
/*   7:    */   extends Pdu
/*   8:    */ {
/*   9:    */   public void setTpMms(int value)
/*  10:    */   {
/*  11: 34 */     checkTpMti(new int[] { 0, 2 });
/*  12:    */     
/*  13: 36 */     setFirstOctetField(251, value, new int[] { 4, 0 });
/*  14:    */   }
/*  15:    */   
/*  16:    */   public boolean hasTpMms()
/*  17:    */   {
/*  18: 41 */     checkTpMti(new int[] { 0, 2 });
/*  19:    */     
/*  20: 43 */     return getFirstOctetField(251) == 4;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void setTpSri(int value)
/*  24:    */   {
/*  25: 48 */     setFirstOctetField(223, value, new int[] { 0, 32 });
/*  26:    */   }
/*  27:    */   
/*  28:    */   public boolean hasTpSri()
/*  29:    */   {
/*  30: 53 */     return getFirstOctetField(223) == 32;
/*  31:    */   }
/*  32:    */   
/*  33: 60 */   private int messageReference = 0;
/*  34:    */   
/*  35:    */   public void setMessageReference(int reference)
/*  36:    */   {
/*  37: 64 */     this.messageReference = reference;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public int getMessageReference()
/*  41:    */   {
/*  42: 69 */     return this.messageReference;
/*  43:    */   }
/*  44:    */   
/*  45: 75 */   private int status = 0;
/*  46:    */   private Calendar timestamp;
/*  47:    */   private Calendar dischargeTime;
/*  48:    */   
/*  49:    */   public void setStatus(int status)
/*  50:    */   {
/*  51: 79 */     this.status = status;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public int getStatus()
/*  55:    */   {
/*  56: 84 */     return this.status;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setTimestamp(Calendar timestamp)
/*  60:    */   {
/*  61: 94 */     this.timestamp = timestamp;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Date getTimestamp()
/*  65:    */   {
/*  66: 99 */     return this.timestamp.getTime();
/*  67:    */   }
/*  68:    */   
/*  69:    */   public Calendar getTimestampAsCalendar()
/*  70:    */   {
/*  71:104 */     return this.timestamp;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void setDischargeTime(Calendar myDischargeTime)
/*  75:    */   {
/*  76:115 */     this.dischargeTime = myDischargeTime;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Date getDischargeTime()
/*  80:    */   {
/*  81:120 */     return this.dischargeTime.getTime();
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Calendar getDischargeTimeAsCalendar()
/*  85:    */   {
/*  86:125 */     return this.dischargeTime;
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected String pduSubclassInfo()
/*  90:    */   {
/*  91:132 */     StringBuffer sb = new StringBuffer();
/*  92:    */     
/*  93:134 */     sb.append("Message Reference: " + PduUtils.byteToPdu(getMessageReference()));
/*  94:135 */     sb.append("\n");
/*  95:138 */     if (getAddress() != null)
/*  96:    */     {
/*  97:140 */       sb.append("Destination Address: [Length: " + getAddress().length() + " (" + PduUtils.byteToPdu((byte)getAddress().length()) + ")");
/*  98:141 */       sb.append(", Type: " + PduUtils.byteToPdu(getAddressType()) + " (" + PduUtils.byteToBits((byte)getAddressType()) + ")");
/*  99:142 */       sb.append(", Address: " + getAddress());
/* 100:143 */       sb.append("]");
/* 101:    */     }
/* 102:    */     else
/* 103:    */     {
/* 104:147 */       sb.append("Destination Address: [Length: 0");
/* 105:148 */       sb.append(", Type: " + PduUtils.byteToPdu(getAddressType()) + " (" + PduUtils.byteToBits((byte)getAddressType()) + ")");
/* 106:149 */       sb.append("]");
/* 107:    */     }
/* 108:152 */     sb.append("\n");
/* 109:    */     
/* 110:154 */     sb.append("TP-SCTS: " + formatTimestamp(getTimestampAsCalendar()));
/* 111:155 */     sb.append("\n");
/* 112:    */     
/* 113:157 */     sb.append("Discharge Time: " + formatTimestamp(getDischargeTimeAsCalendar()));
/* 114:158 */     sb.append("\n");
/* 115:    */     
/* 116:160 */     sb.append("Status: " + PduUtils.byteToPdu(getStatus()));
/* 117:161 */     sb.append("\n");
/* 118:162 */     return sb.toString();
/* 119:    */   }
/* 120:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.ajwcc.pduUtils.gsm3040.SmsStatusReportPdu
 * JD-Core Version:    0.7.0.1
 */
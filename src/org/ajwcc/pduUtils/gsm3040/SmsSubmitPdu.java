/*   1:    */ package org.ajwcc.pduUtils.gsm3040;
/*   2:    */ 
/*   3:    */ import java.util.Calendar;
/*   4:    */ import java.util.Date;
/*   5:    */ 
/*   6:    */ public class SmsSubmitPdu
/*   7:    */   extends Pdu
/*   8:    */ {
/*   9:    */   public void setTpRd(int value)
/*  10:    */   {
/*  11: 29 */     setFirstOctetField(251, value, new int[] { 0, 4 });
/*  12:    */   }
/*  13:    */   
/*  14:    */   public boolean hasTpRd()
/*  15:    */   {
/*  16: 34 */     return getFirstOctetField(251) == 4;
/*  17:    */   }
/*  18:    */   
/*  19:    */   public void setTpVpf(int value)
/*  20:    */   {
/*  21: 39 */     setFirstOctetField(231, value, new int[] { 16, 0, 24 });
/*  22:    */   }
/*  23:    */   
/*  24:    */   public int getTpVpf()
/*  25:    */   {
/*  26: 44 */     return getFirstOctetField(231);
/*  27:    */   }
/*  28:    */   
/*  29:    */   public boolean hasTpVpf()
/*  30:    */   {
/*  31: 49 */     return getFirstOctetField(231) != 0;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setTpSrr(int value)
/*  35:    */   {
/*  36: 54 */     setFirstOctetField(223, value, new int[] { 0, 32 });
/*  37:    */   }
/*  38:    */   
/*  39:    */   public boolean hasTpSrr()
/*  40:    */   {
/*  41: 59 */     return getFirstOctetField(223) == 32;
/*  42:    */   }
/*  43:    */   
/*  44: 66 */   private int messageReference = 0;
/*  45:    */   
/*  46:    */   public void setMessageReference(int reference)
/*  47:    */   {
/*  48: 70 */     this.messageReference = reference;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public int getMessageReference()
/*  52:    */   {
/*  53: 75 */     return this.messageReference;
/*  54:    */   }
/*  55:    */   
/*  56: 82 */   private int validityPeriod = -1;
/*  57:    */   private Calendar validityPeriodTimeStamp;
/*  58:    */   
/*  59:    */   public int getValidityPeriod()
/*  60:    */   {
/*  61: 88 */     return this.validityPeriod;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setValidityPeriod(int validityPeriod)
/*  65:    */   {
/*  66: 93 */     this.validityPeriod = validityPeriod;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setValidityTimestamp(Calendar date)
/*  70:    */   {
/*  71: 98 */     this.validityPeriodTimeStamp = date;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public Date getValidityDate()
/*  75:    */   {
/*  76:103 */     return this.validityPeriodTimeStamp.getTime();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public Calendar getValidityDateAsCalendar()
/*  80:    */   {
/*  81:108 */     return this.validityPeriodTimeStamp;
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected String pduSubclassInfo()
/*  85:    */   {
/*  86:115 */     StringBuffer sb = new StringBuffer();
/*  87:    */     
/*  88:117 */     sb.append("Message Reference: " + PduUtils.byteToPdu(getMessageReference()));
/*  89:118 */     sb.append("\n");
/*  90:121 */     if (getAddress() != null)
/*  91:    */     {
/*  92:123 */       sb.append("Destination Address: [Length: " + getAddress().length() + " (" + PduUtils.byteToPdu((byte)getAddress().length()) + ")");
/*  93:124 */       sb.append(", Type: " + PduUtils.byteToPdu(getAddressType()) + " (" + PduUtils.byteToBits((byte)getAddressType()) + ")");
/*  94:125 */       sb.append(", Address: " + getAddress());
/*  95:126 */       sb.append("]");
/*  96:    */     }
/*  97:    */     else
/*  98:    */     {
/*  99:130 */       sb.append("Destination Address: [Length: 0");
/* 100:131 */       sb.append(", Type: " + PduUtils.byteToPdu(getAddressType()) + " (" + PduUtils.byteToBits((byte)getAddressType()) + ")");
/* 101:132 */       sb.append("]");
/* 102:    */     }
/* 103:135 */     sb.append("\n");
/* 104:    */     
/* 105:137 */     sb.append("TP-PID: " + PduUtils.byteToPdu(getProtocolIdentifier()) + " (" + PduUtils.byteToBits((byte)getProtocolIdentifier()) + ")");
/* 106:138 */     sb.append("\n");
/* 107:    */     
/* 108:140 */     sb.append("TP-DCS: " + PduUtils.byteToPdu(getDataCodingScheme()) + " (" + PduUtils.decodeDataCodingScheme(this) + ") (" + PduUtils.byteToBits((byte)getDataCodingScheme()) + ")");
/* 109:141 */     sb.append("\n");
/* 110:143 */     switch (getTpVpf())
/* 111:    */     {
/* 112:    */     case 16: 
/* 113:146 */       sb.append("TP-VPF: " + getValidityPeriod() + " hours");
/* 114:147 */       break;
/* 115:    */     case 24: 
/* 116:149 */       sb.append("TP-VPF: " + formatTimestamp(getValidityDateAsCalendar()));
/* 117:    */     }
/* 118:152 */     sb.append("\n");
/* 119:153 */     return sb.toString();
/* 120:    */   }
/* 121:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.ajwcc.pduUtils.gsm3040.SmsSubmitPdu
 * JD-Core Version:    0.7.0.1
 */
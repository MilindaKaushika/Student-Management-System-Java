/*   1:    */ package org.ajwcc.pduUtils.wappush;
/*   2:    */ 
/*   3:    */ import java.util.Date;
/*   4:    */ import org.ajwcc.pduUtils.gsm3040.SmsSubmitPdu;
/*   5:    */ 
/*   6:    */ public class WapSiPdu
/*   7:    */   extends SmsSubmitPdu
/*   8:    */ {
/*   9:    */   public static final int WAP_SIGNAL_NONE = 5;
/*  10:    */   public static final int WAP_SIGNAL_LOW = 6;
/*  11:    */   public static final int WAP_SIGNAL_MEDIUM = 7;
/*  12:    */   public static final int WAP_SIGNAL_HIGH = 8;
/*  13:    */   public static final int WAP_SIGNAL_DELETE = 9;
/*  14: 40 */   private int wapSignal = 7;
/*  15:    */   private String indicationText;
/*  16:    */   private String url;
/*  17:    */   private Date createDate;
/*  18:    */   private Date expireDate;
/*  19:    */   private String siId;
/*  20:    */   private String siClass;
/*  21:    */   
/*  22:    */   public WapSiPdu()
/*  23:    */   {
/*  24: 56 */     setDataCodingScheme(244);
/*  25:    */   }
/*  26:    */   
/*  27:    */   public String getSiId()
/*  28:    */   {
/*  29: 61 */     return this.siId;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setSiId(String siId)
/*  33:    */   {
/*  34: 66 */     this.siId = siId;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String getSiClass()
/*  38:    */   {
/*  39: 71 */     return this.siClass;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setSiClass(String siClass)
/*  43:    */   {
/*  44: 76 */     this.siClass = siClass;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public String getIndicationText()
/*  48:    */   {
/*  49: 81 */     return this.indicationText;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void setIndicationText(String indicationText)
/*  53:    */   {
/*  54: 86 */     this.indicationText = indicationText;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public String getUrl()
/*  58:    */   {
/*  59: 91 */     return this.url;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void setUrl(String url)
/*  63:    */   {
/*  64: 96 */     this.url = url;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public Date getCreateDate()
/*  68:    */   {
/*  69:101 */     return this.createDate;
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setCreateDate(Date createDate)
/*  73:    */   {
/*  74:106 */     this.createDate = createDate;
/*  75:    */   }
/*  76:    */   
/*  77:    */   public Date getExpireDate()
/*  78:    */   {
/*  79:111 */     return this.expireDate;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setExpireDate(Date expireDate)
/*  83:    */   {
/*  84:116 */     this.expireDate = expireDate;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public int getWapSignal()
/*  88:    */   {
/*  89:121 */     return this.wapSignal;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setWapSignalFromString(String s)
/*  93:    */   {
/*  94:126 */     if (s == null)
/*  95:    */     {
/*  96:128 */       this.wapSignal = 7;
/*  97:129 */       return;
/*  98:    */     }
/*  99:131 */     s = s.trim();
/* 100:132 */     if (s.equalsIgnoreCase("none")) {
/* 101:134 */       this.wapSignal = 5;
/* 102:136 */     } else if (s.equalsIgnoreCase("low")) {
/* 103:138 */       this.wapSignal = 6;
/* 104:140 */     } else if ((s.equalsIgnoreCase("medium")) || (s.equals(""))) {
/* 105:142 */       this.wapSignal = 7;
/* 106:144 */     } else if (s.equalsIgnoreCase("high")) {
/* 107:146 */       this.wapSignal = 8;
/* 108:148 */     } else if (s.equalsIgnoreCase("delete")) {
/* 109:150 */       this.wapSignal = 9;
/* 110:    */     } else {
/* 111:154 */       throw new RuntimeException("Cannot determine WAP signal to use");
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public void setWapSignal(int i)
/* 116:    */   {
/* 117:160 */     switch (i)
/* 118:    */     {
/* 119:    */     case 5: 
/* 120:    */     case 6: 
/* 121:    */     case 7: 
/* 122:    */     case 8: 
/* 123:    */     case 9: 
/* 124:167 */       this.wapSignal = i;
/* 125:168 */       break;
/* 126:    */     default: 
/* 127:170 */       throw new RuntimeException("Invalid wap signal value: " + i);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public byte[] getDataBytes()
/* 132:    */   {
/* 133:177 */     if (super.getDataBytes() == null)
/* 134:    */     {
/* 135:179 */       WapSiUserDataGenerator udGenerator = new WapSiUserDataGenerator();
/* 136:180 */       udGenerator.setWapSiPdu(this);
/* 137:181 */       setDataBytes(udGenerator.generateWapSiUDBytes());
/* 138:    */     }
/* 139:183 */     return super.getDataBytes();
/* 140:    */   }
/* 141:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.ajwcc.pduUtils.wappush.WapSiPdu
 * JD-Core Version:    0.7.0.1
 */
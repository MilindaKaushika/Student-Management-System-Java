/*   1:    */ package org.ajwcc.pduUtils.gsm3040.ie;
/*   2:    */ 
/*   3:    */ public class ConcatInformationElement
/*   4:    */   extends InformationElement
/*   5:    */ {
/*   6:    */   private static final int CONCAT_IE_LENGTH_16BIT = 6;
/*   7:    */   private static final int CONCAT_IE_LENGTH_8BIT = 5;
/*   8:    */   public static final int CONCAT_8BIT_REF = 0;
/*   9:    */   public static final int CONCAT_16BIT_REF = 8;
/*  10: 30 */   private static int defaultConcatType = 0;
/*  11: 32 */   private static int defaultConcatLength = 5;
/*  12:    */   
/*  13:    */   public static int getDefaultConcatLength()
/*  14:    */   {
/*  15: 36 */     return defaultConcatLength;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public static int getDefaultConcatType()
/*  19:    */   {
/*  20: 41 */     return defaultConcatType;
/*  21:    */   }
/*  22:    */   
/*  23:    */   public static void setDefaultConcatType(int identifier)
/*  24:    */   {
/*  25: 46 */     switch (identifier)
/*  26:    */     {
/*  27:    */     case 0: 
/*  28: 49 */       defaultConcatType = 0;
/*  29: 50 */       defaultConcatLength = 5;
/*  30: 51 */       break;
/*  31:    */     case 8: 
/*  32: 53 */       defaultConcatType = 8;
/*  33: 54 */       defaultConcatLength = 6;
/*  34: 55 */       break;
/*  35:    */     default: 
/*  36: 57 */       throw new RuntimeException("Invalid Concat type");
/*  37:    */     }
/*  38:    */   }
/*  39:    */   
/*  40:    */   ConcatInformationElement(byte identifier, byte[] data)
/*  41:    */   {
/*  42: 63 */     super(identifier, data);
/*  43: 64 */     if (getIdentifier() == 0)
/*  44:    */     {
/*  45: 71 */       if (data.length != 3) {
/*  46: 71 */         throw new RuntimeException("Invalid data length in: " + getClass().getSimpleName());
/*  47:    */       }
/*  48:    */     }
/*  49: 73 */     else if (getIdentifier() == 8)
/*  50:    */     {
/*  51: 80 */       if (data.length != 4) {
/*  52: 80 */         throw new RuntimeException("Invalid data length in: " + getClass().getSimpleName());
/*  53:    */       }
/*  54:    */     }
/*  55:    */     else {
/*  56: 84 */       throw new RuntimeException("Invalid identifier in data in: " + getClass().getSimpleName());
/*  57:    */     }
/*  58: 86 */     validate();
/*  59:    */   }
/*  60:    */   
/*  61:    */   ConcatInformationElement(int identifier, int mpRefNo, int mpMaxNo, int mpSeqNo)
/*  62:    */   {
/*  63: 92 */     byte[] data = null;
/*  64: 93 */     switch (identifier)
/*  65:    */     {
/*  66:    */     case 0: 
/*  67: 96 */       data = new byte[3];
/*  68: 97 */       data[0] = ((byte)(mpRefNo & 0xFF));
/*  69: 98 */       data[1] = ((byte)(mpMaxNo & 0xFF));
/*  70: 99 */       data[2] = ((byte)(mpSeqNo & 0xFF));
/*  71:100 */       break;
/*  72:    */     case 8: 
/*  73:102 */       data = new byte[4];
/*  74:103 */       data[0] = ((byte)((mpRefNo & 0xFF00) >>> 8));
/*  75:104 */       data[1] = ((byte)(mpRefNo & 0xFF));
/*  76:105 */       data[2] = ((byte)(mpMaxNo & 0xFF));
/*  77:106 */       data[3] = ((byte)(mpSeqNo & 0xFF));
/*  78:107 */       break;
/*  79:    */     default: 
/*  80:109 */       throw new RuntimeException("Invalid identifier for " + getClass().getSimpleName());
/*  81:    */     }
/*  82:111 */     initialize((byte)(identifier & 0xFF), data);
/*  83:112 */     validate();
/*  84:    */   }
/*  85:    */   
/*  86:    */   public int getMpRefNo()
/*  87:    */   {
/*  88:118 */     byte[] data = getData();
/*  89:119 */     if (getIdentifier() == 0) {
/*  90:121 */       return data[0] & 0xFF;
/*  91:    */     }
/*  92:123 */     if (getIdentifier() == 8) {
/*  93:123 */       return (data[0] << 8 | data[1]) & 0xFFFF;
/*  94:    */     }
/*  95:124 */     throw new RuntimeException("Invalid identifier");
/*  96:    */   }
/*  97:    */   
/*  98:    */   public void setMpRefNo(int mpRefNo)
/*  99:    */   {
/* 100:130 */     byte[] data = getData();
/* 101:131 */     if (getIdentifier() == 0)
/* 102:    */     {
/* 103:133 */       data[0] = ((byte)(mpRefNo & 0xFF));
/* 104:    */     }
/* 105:135 */     else if (getIdentifier() == 8)
/* 106:    */     {
/* 107:137 */       data[0] = ((byte)(mpRefNo >>> 8 & 0xFF));
/* 108:138 */       data[1] = ((byte)(mpRefNo & 0xFF));
/* 109:    */     }
/* 110:    */     else
/* 111:    */     {
/* 112:142 */       throw new RuntimeException("Invalid identifier");
/* 113:    */     }
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getMpMaxNo()
/* 117:    */   {
/* 118:148 */     byte[] data = getData();
/* 119:149 */     if (getIdentifier() == 0) {
/* 120:151 */       return data[1] & 0xFF;
/* 121:    */     }
/* 122:153 */     if (getIdentifier() == 8) {
/* 123:153 */       return data[2] & 0xFF;
/* 124:    */     }
/* 125:154 */     throw new RuntimeException("Invalid identifier");
/* 126:    */   }
/* 127:    */   
/* 128:    */   public void setMpMaxNo(int mpMaxNo)
/* 129:    */   {
/* 130:159 */     byte[] data = getData();
/* 131:160 */     if (getIdentifier() == 0) {
/* 132:162 */       data[1] = ((byte)(mpMaxNo & 0xFF));
/* 133:164 */     } else if (getIdentifier() == 8) {
/* 134:166 */       data[2] = ((byte)(mpMaxNo & 0xFF));
/* 135:    */     } else {
/* 136:170 */       throw new RuntimeException("Invalid identifier");
/* 137:    */     }
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int getMpSeqNo()
/* 141:    */   {
/* 142:176 */     byte[] data = getData();
/* 143:177 */     if (getIdentifier() == 0) {
/* 144:179 */       return data[2] & 0xFF;
/* 145:    */     }
/* 146:181 */     if (getIdentifier() == 8) {
/* 147:181 */       return data[3] & 0xFF;
/* 148:    */     }
/* 149:182 */     throw new RuntimeException("Invalid identifier");
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setMpSeqNo(int mpSeqNo)
/* 153:    */   {
/* 154:187 */     byte[] data = getData();
/* 155:188 */     if (getIdentifier() == 0) {
/* 156:190 */       data[2] = ((byte)(mpSeqNo & 0xFF));
/* 157:192 */     } else if (getIdentifier() == 8) {
/* 158:194 */       data[3] = ((byte)(mpSeqNo & 0xFF));
/* 159:    */     } else {
/* 160:198 */       throw new RuntimeException("Invalid identifier");
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   public String toString()
/* 165:    */   {
/* 166:205 */     StringBuffer sb = new StringBuffer();
/* 167:206 */     sb.append(super.toString());
/* 168:207 */     sb.append("[MpRefNo: ");
/* 169:208 */     sb.append(getMpRefNo());
/* 170:209 */     sb.append(", MpMaxNo: ");
/* 171:210 */     sb.append(getMpMaxNo());
/* 172:211 */     sb.append(", MpSeqNo: ");
/* 173:212 */     sb.append(getMpSeqNo());
/* 174:213 */     sb.append("]");
/* 175:214 */     return sb.toString();
/* 176:    */   }
/* 177:    */   
/* 178:    */   private void validate()
/* 179:    */   {
/* 180:219 */     if (getMpMaxNo() == 0) {
/* 181:219 */       throw new RuntimeException("mpMaxNo must be > 0");
/* 182:    */     }
/* 183:220 */     if (getMpSeqNo() == 0) {
/* 184:220 */       throw new RuntimeException("mpSeqNo must be > 0");
/* 185:    */     }
/* 186:    */   }
/* 187:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.ajwcc.pduUtils.gsm3040.ie.ConcatInformationElement
 * JD-Core Version:    0.7.0.1
 */
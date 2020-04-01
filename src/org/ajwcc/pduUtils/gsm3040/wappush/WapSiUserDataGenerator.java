/*   1:    */ package org.ajwcc.pduUtils.wappush;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.text.SimpleDateFormat;
/*   5:    */ import java.util.Date;
/*   6:    */ import org.ajwcc.pduUtils.gsm3040.PduUtils;
/*   7:    */ 
/*   8:    */ public class WapSiUserDataGenerator
/*   9:    */ {
/*  10:    */   private WapSiPdu pdu;
/*  11: 39 */   private ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  12:    */   
/*  13:    */   public void setWapSiPdu(WapSiPdu pdu)
/*  14:    */   {
/*  15: 43 */     this.pdu = pdu;
/*  16:    */   }
/*  17:    */   
/*  18:    */   public byte[] generateWapSiUDBytes()
/*  19:    */   {
/*  20:    */     try
/*  21:    */     {
/*  22: 50 */       this.baos = new ByteArrayOutputStream();
/*  23:    */       
/*  24: 52 */       writeWspHeader();
/*  25:    */       
/*  26: 54 */       writeWapSiData();
/*  27: 55 */       return this.baos.toByteArray();
/*  28:    */     }
/*  29:    */     catch (Exception e)
/*  30:    */     {
/*  31: 59 */       throw new RuntimeException(e);
/*  32:    */     }
/*  33:    */   }
/*  34:    */   
/*  35:    */   private void writeWspHeader()
/*  36:    */   {
/*  37: 68 */     this.baos.write(1);
/*  38:    */     
/*  39: 70 */     this.baos.write(6);
/*  40:    */     
/*  41:    */ 
/*  42:    */ 
/*  43: 74 */     this.baos.write(4);
/*  44:    */     
/*  45: 76 */     this.baos.write(3);
/*  46:    */     
/*  47: 78 */     this.baos.write(174);
/*  48:    */     
/*  49: 80 */     this.baos.write(129);
/*  50:    */     
/*  51: 82 */     this.baos.write(234);
/*  52:    */   }
/*  53:    */   
/*  54:    */   private void writeWapSiData()
/*  55:    */     throws Exception
/*  56:    */   {
/*  57: 89 */     this.baos.write(2);
/*  58:    */     
/*  59: 91 */     this.baos.write(5);
/*  60:    */     
/*  61: 93 */     this.baos.write(106);
/*  62:    */     
/*  63: 95 */     this.baos.write(0);
/*  64:    */     
/*  65: 97 */     this.baos.write(69);
/*  66:    */     
/*  67: 99 */     this.baos.write(198);
/*  68:    */     
/*  69:101 */     writeHrefAttribute(this.pdu.getUrl());
/*  70:    */     
/*  71:103 */     writeCreatedAttribute(this.pdu.getCreateDate());
/*  72:    */     
/*  73:105 */     writeExpiresAttribute(this.pdu.getExpireDate());
/*  74:    */     
/*  75:107 */     writeActionAttribute(this.pdu.getWapSignal());
/*  76:    */     
/*  77:109 */     writeSiIdAttribute(this.pdu.getSiId());
/*  78:    */     
/*  79:    */ 
/*  80:    */ 
/*  81:113 */     this.baos.write(1);
/*  82:    */     
/*  83:115 */     writeText(this.pdu.getIndicationText());
/*  84:    */     
/*  85:117 */     this.baos.write(1);
/*  86:    */     
/*  87:119 */     this.baos.write(1);
/*  88:    */   }
/*  89:    */   
/*  90:    */   private void writeHrefAttribute(String url)
/*  91:    */     throws Exception
/*  92:    */   {
/*  93:125 */     if ((url == null) || (url.trim().equals(""))) {
/*  94:125 */       throw new RuntimeException("Invalid URL: '" + url + "'");
/*  95:    */     }
/*  96:127 */     boolean protocolFound = false;
/*  97:128 */     for (String protocol : WapPushUtils.getProtocols()) {
/*  98:130 */       if (url.startsWith(protocol))
/*  99:    */       {
/* 100:133 */         this.baos.write(WapPushUtils.getProtocolByteFor(protocol));
/* 101:134 */         protocolFound = true;
/* 102:135 */         url = url.substring(protocol.length());
/* 103:136 */         break;
/* 104:    */       }
/* 105:    */     }
/* 106:139 */     if (!protocolFound) {
/* 107:142 */       this.baos.write(11);
/* 108:    */     }
/* 109:145 */     this.baos.write(3);
/* 110:    */     
/* 111:147 */     int i = 0;
/* 112:147 */     for (int lastPosition = 0; i < url.length(); i++) {
/* 113:149 */       for (String domain : WapPushUtils.getDomains())
/* 114:    */       {
/* 115:152 */         if (i + domain.length() > url.length())
/* 116:    */         {
/* 117:155 */           String currentPortion = url.substring(lastPosition, url.length());
/* 118:156 */           this.baos.write(currentPortion.getBytes("UTF-8"));
/* 119:    */           
/* 120:158 */           i += domain.length();
/* 121:159 */           break;
/* 122:    */         }
/* 123:161 */         if (url.substring(i, i + domain.length()).equalsIgnoreCase(domain))
/* 124:    */         {
/* 125:164 */           if (lastPosition < i)
/* 126:    */           {
/* 127:166 */             String currentPortion = url.substring(lastPosition, i);
/* 128:167 */             this.baos.write(currentPortion.getBytes("UTF-8"));
/* 129:    */           }
/* 130:170 */           this.baos.write(0);
/* 131:171 */           this.baos.write(WapPushUtils.getDomainByteFor(domain));
/* 132:172 */           this.baos.write(3);
/* 133:    */           
/* 134:174 */           i += domain.length();
/* 135:175 */           lastPosition = i;
/* 136:    */           
/* 137:177 */           break;
/* 138:    */         }
/* 139:    */       }
/* 140:    */     }
/* 141:181 */     this.baos.write(0);
/* 142:    */   }
/* 143:    */   
/* 144:    */   private void writeCreatedAttribute(Date createDate)
/* 145:    */     throws Exception
/* 146:    */   {
/* 147:186 */     if (createDate != null)
/* 148:    */     {
/* 149:190 */       this.baos.write(10);
/* 150:191 */       writeDate(createDate);
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   private void writeExpiresAttribute(Date expireDate)
/* 155:    */     throws Exception
/* 156:    */   {
/* 157:197 */     if (expireDate != null)
/* 158:    */     {
/* 159:201 */       this.baos.write(16);
/* 160:202 */       writeDate(expireDate);
/* 161:    */     }
/* 162:    */   }
/* 163:    */   
/* 164:    */   private void writeSiIdAttribute(String siId)
/* 165:    */   {
/* 166:208 */     if ((siId != null) && (siId.trim().equals("")))
/* 167:    */     {
/* 168:210 */       this.baos.write(17);
/* 169:211 */       writeText(siId);
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   private void writeActionAttribute(int wapSignal)
/* 174:    */   {
/* 175:222 */     if (wapSignal != 7) {
/* 176:225 */       this.baos.write(wapSignal);
/* 177:    */     }
/* 178:    */   }
/* 179:    */   
/* 180:    */   private void writeText(String text)
/* 181:    */   {
/* 182:    */     try
/* 183:    */     {
/* 184:236 */       this.baos.write(3);
/* 185:    */       
/* 186:    */ 
/* 187:239 */       this.baos.write(text.getBytes("UTF-8"));
/* 188:240 */       this.baos.write(0);
/* 189:    */     }
/* 190:    */     catch (Exception e)
/* 191:    */     {
/* 192:244 */       throw new RuntimeException(e);
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   private void writeDate(Date date)
/* 197:    */     throws Exception
/* 198:    */   {
/* 199:252 */     SimpleDateFormat sdf = new SimpleDateFormat();
/* 200:253 */     sdf.applyPattern("yyyyMMddHHmmss");
/* 201:254 */     String dateData = sdf.format(date);
/* 202:256 */     for (int i = 6; i >= 0; i--)
/* 203:    */     {
/* 204:258 */       if (!dateData.endsWith("00")) {
/* 205:    */         break;
/* 206:    */       }
/* 207:260 */       dateData = dateData.substring(0, i * 2);
/* 208:    */     }
/* 209:268 */     byte[] dataBytes = PduUtils.pduToBytes(dateData);
/* 210:    */     
/* 211:270 */     this.baos.write(195);
/* 212:    */     
/* 213:272 */     this.baos.write(dataBytes.length);
/* 214:    */     
/* 215:274 */     this.baos.write(dataBytes);
/* 216:    */   }
/* 217:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.ajwcc.pduUtils.wappush.WapSiUserDataGenerator
 * JD-Core Version:    0.7.0.1
 */
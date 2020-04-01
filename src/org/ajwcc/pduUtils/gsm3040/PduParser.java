/*   1:    */ package org.ajwcc.pduUtils.gsm3040;
/*   2:    */ 
/*   3:    */ import java.util.Calendar;
/*   4:    */ import java.util.TimeZone;
/*   5:    */ import org.ajwcc.pduUtils.gsm3040.ie.InformationElement;
/*   6:    */ import org.ajwcc.pduUtils.gsm3040.ie.InformationElementFactory;
/*   7:    */ 
/*   8:    */ public class PduParser
/*   9:    */ {
/*  10:    */   private int position;
/*  11:    */   private byte[] pduByteArray;
/*  12:    */   
/*  13:    */   private int readByte()
/*  14:    */   {
/*  15: 41 */     int retVal = this.pduByteArray[this.position] & 0xFF;
/*  16: 42 */     this.position += 1;
/*  17: 43 */     return retVal;
/*  18:    */   }
/*  19:    */   
/*  20:    */   private int readSwappedNibbleBCDByte()
/*  21:    */   {
/*  22: 49 */     int data = readByte();
/*  23: 50 */     data = PduUtils.swapNibbles((byte)data);
/*  24: 51 */     int retVal = 0;
/*  25: 52 */     retVal += (data >>> 4 & 0xF) * 10;
/*  26: 53 */     retVal += (data & 0xF);
/*  27: 54 */     return retVal;
/*  28:    */   }
/*  29:    */   
/*  30:    */   private Calendar readTimeStamp()
/*  31:    */   {
/*  32: 61 */     int year = readSwappedNibbleBCDByte();
/*  33: 62 */     int month = readSwappedNibbleBCDByte();
/*  34: 63 */     int day = readSwappedNibbleBCDByte();
/*  35: 64 */     int hour = readSwappedNibbleBCDByte();
/*  36: 65 */     int minute = readSwappedNibbleBCDByte();
/*  37: 66 */     int second = readSwappedNibbleBCDByte();
/*  38:    */     
/*  39:    */ 
/*  40: 69 */     int timestamp = readByte();
/*  41: 70 */     boolean negative = (timestamp & 0x8) == 8;
/*  42: 71 */     int timezone = PduUtils.swapNibbles(timestamp) & 0x7F;
/*  43:    */     
/*  44: 73 */     TimeZone tz = null;
/*  45: 74 */     if (negative)
/*  46:    */     {
/*  47: 78 */       int bcdTimeZone = 0;
/*  48: 79 */       bcdTimeZone += (timezone >>> 4 & 0xF) * 10;
/*  49: 80 */       bcdTimeZone += (timezone & 0xF);
/*  50: 81 */       timezone = bcdTimeZone;
/*  51: 82 */       int totalMinutes = timezone * 15;
/*  52: 83 */       int hours = totalMinutes / 60;
/*  53: 84 */       int minutes = totalMinutes % 60;
/*  54: 85 */       String gmtString = "GMT-" + hours + ":" + (minutes < 10 ? "0" : "") + minutes;
/*  55:    */       
/*  56: 87 */       tz = TimeZone.getTimeZone(gmtString);
/*  57:    */     }
/*  58:    */     else
/*  59:    */     {
/*  60: 91 */       int bcdTimeZone = 0;
/*  61: 92 */       bcdTimeZone += (timezone >>> 4 & 0xF) * 10;
/*  62: 93 */       bcdTimeZone += (timezone & 0xF);
/*  63: 94 */       timezone = bcdTimeZone;
/*  64: 95 */       int totalMinutes = timezone * 15;
/*  65: 96 */       int hours = totalMinutes / 60;
/*  66: 97 */       int minutes = totalMinutes % 60;
/*  67: 98 */       String gmtString = "GMT+" + hours + ":" + (minutes < 10 ? "0" : "") + minutes;
/*  68:    */       
/*  69:100 */       tz = TimeZone.getTimeZone(gmtString);
/*  70:    */     }
/*  71:102 */     Calendar cal = Calendar.getInstance(tz);
/*  72:103 */     cal.set(1, year + 2000);
/*  73:104 */     cal.set(2, month - 1);
/*  74:105 */     cal.set(5, day);
/*  75:106 */     cal.set(11, hour);
/*  76:107 */     cal.set(12, minute);
/*  77:108 */     cal.set(13, second);
/*  78:109 */     return cal;
/*  79:    */   }
/*  80:    */   
/*  81:    */   private String readAddress(int addressLength, int addressType)
/*  82:    */   {
/*  83:117 */     if (addressLength > 0)
/*  84:    */     {
/*  85:120 */       int addressDataOctetLength = addressLength / 2 + (addressLength % 2 == 1 ? 1 : 0);
/*  86:    */       
/*  87:122 */       byte[] addressData = new byte[addressDataOctetLength];
/*  88:123 */       System.arraycopy(this.pduByteArray, this.position, addressData, 0, addressDataOctetLength);
/*  89:124 */       this.position += addressDataOctetLength;
/*  90:125 */       switch (PduUtils.extractAddressType(addressType))
/*  91:    */       {
/*  92:    */       case 80: 
/*  93:129 */         return stripAddressFieldPadding(addressLength, PduUtils.decode7bitEncoding(addressData));
/*  94:    */       }
/*  95:132 */       return PduUtils.readBCDNumbers(addressLength, addressData);
/*  96:    */     }
/*  97:135 */     return null;
/*  98:    */   }
/*  99:    */   
/* 100:    */   private int readValidityPeriodInt()
/* 101:    */   {
/* 102:141 */     int validity = readByte();
/* 103:142 */     int minutes = 0;
/* 104:143 */     if ((validity > 0) && (validity <= 143)) {
/* 105:146 */       minutes = (validity + 1) * 5;
/* 106:148 */     } else if ((validity > 143) && (validity <= 167)) {
/* 107:151 */       minutes = 720 + (validity - 143) * 30;
/* 108:153 */     } else if ((validity > 167) && (validity <= 196)) {
/* 109:156 */       minutes = (validity - 166) * 24 * 60;
/* 110:158 */     } else if ((validity > 197) && (validity <= 255)) {
/* 111:161 */       minutes = (validity - 192) * 7 * 24 * 60;
/* 112:    */     }
/* 113:163 */     return minutes;
/* 114:    */   }
/* 115:    */   
/* 116:    */   public Pdu parsePdu(String rawPdu)
/* 117:    */   {
/* 118:169 */     this.pduByteArray = PduUtils.pduToBytes(rawPdu);
/* 119:170 */     this.position = 0;
/* 120:    */     
/* 121:172 */     Pdu pdu = parseStart();
/* 122:173 */     pdu.setRawPdu(rawPdu);
/* 123:175 */     switch (pdu.getTpMti())
/* 124:    */     {
/* 125:    */     case 0: 
/* 126:178 */       parseSmsDeliverMessage((SmsDeliveryPdu)pdu);
/* 127:179 */       break;
/* 128:    */     case 1: 
/* 129:181 */       parseSmsSubmitMessage((SmsSubmitPdu)pdu);
/* 130:182 */       break;
/* 131:    */     case 2: 
/* 132:184 */       parseSmsStatusReportMessage((SmsStatusReportPdu)pdu);
/* 133:    */     }
/* 134:187 */     return pdu;
/* 135:    */   }
/* 136:    */   
/* 137:    */   private Pdu parseStart()
/* 138:    */   {
/* 139:196 */     int addressLength = readByte();
/* 140:197 */     Pdu pdu = null;
/* 141:198 */     if (addressLength > 0)
/* 142:    */     {
/* 143:200 */       int addressType = readByte();
/* 144:201 */       String smscAddress = readAddress((addressLength - 1) * 2, addressType);
/* 145:    */       
/* 146:203 */       int firstOctet = readByte();
/* 147:204 */       pdu = PduFactory.createPdu(firstOctet);
/* 148:    */       
/* 149:206 */       pdu.setSmscAddressType(addressType);
/* 150:207 */       pdu.setSmscAddress(smscAddress);
/* 151:208 */       pdu.setSmscInfoLength(addressLength);
/* 152:    */     }
/* 153:    */     else
/* 154:    */     {
/* 155:213 */       int firstOctet = readByte();
/* 156:214 */       pdu = PduFactory.createPdu(firstOctet);
/* 157:    */     }
/* 158:216 */     return pdu;
/* 159:    */   }
/* 160:    */   
/* 161:    */   private void parseUserData(Pdu pdu)
/* 162:    */   {
/* 163:225 */     int udLength = readByte();
/* 164:226 */     pdu.setUDLength(udLength);
/* 165:    */     
/* 166:    */ 
/* 167:229 */     int udOctetLength = this.pduByteArray.length - this.position;
/* 168:230 */     byte[] udData = new byte[udOctetLength];
/* 169:231 */     System.arraycopy(this.pduByteArray, this.position, udData, 0, udOctetLength);
/* 170:    */     
/* 171:233 */     pdu.setUDData(udData);
/* 172:236 */     if (pdu.hasTpUdhi())
/* 173:    */     {
/* 174:239 */       int udhLength = readByte();
/* 175:    */       
/* 176:    */ 
/* 177:    */ 
/* 178:    */ 
/* 179:244 */       int endUdh = this.position + udhLength;
/* 180:245 */       while (this.position < endUdh)
/* 181:    */       {
/* 182:247 */         int iei = readByte();
/* 183:248 */         int iedl = readByte();
/* 184:249 */         byte[] ieData = new byte[iedl];
/* 185:250 */         System.arraycopy(this.pduByteArray, this.position, ieData, 0, iedl);
/* 186:251 */         InformationElement ie = InformationElementFactory.createInformationElement(iei, ieData);
/* 187:252 */         pdu.addInformationElement(ie);
/* 188:253 */         this.position += iedl;
/* 189:254 */         if (this.position > endUdh) {
/* 190:257 */           throw new RuntimeException("UDH is shorter than expected endUdh=" + endUdh + ", position=" + this.position);
/* 191:    */         }
/* 192:    */       }
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   private void parseSmsDeliverMessage(SmsDeliveryPdu pdu)
/* 197:    */   {
/* 198:269 */     int addressLength = readByte();
/* 199:270 */     int addressType = readByte();
/* 200:271 */     String originatorAddress = readAddress(addressLength, addressType);
/* 201:272 */     pdu.setAddressType(addressType);
/* 202:273 */     pdu.setAddress(originatorAddress);
/* 203:    */     
/* 204:275 */     int protocolId = readByte();
/* 205:276 */     pdu.setProtocolIdentifier(protocolId);
/* 206:    */     
/* 207:278 */     int dcs = readByte();
/* 208:279 */     pdu.setDataCodingScheme(dcs);
/* 209:    */     
/* 210:281 */     Calendar timestamp = readTimeStamp();
/* 211:282 */     pdu.setTimestamp(timestamp);
/* 212:    */     
/* 213:284 */     parseUserData(pdu);
/* 214:    */   }
/* 215:    */   
/* 216:    */   private void parseSmsStatusReportMessage(SmsStatusReportPdu pdu)
/* 217:    */   {
/* 218:290 */     int messageReference = readByte();
/* 219:291 */     pdu.setMessageReference(messageReference);
/* 220:    */     
/* 221:293 */     int addressLength = readByte();
/* 222:294 */     int addressType = readByte();
/* 223:295 */     String destinationAddress = readAddress(addressLength, addressType);
/* 224:296 */     pdu.setAddressType(addressType);
/* 225:297 */     pdu.setAddress(destinationAddress);
/* 226:    */     
/* 227:299 */     Calendar timestamp = readTimeStamp();
/* 228:300 */     pdu.setTimestamp(timestamp);
/* 229:    */     
/* 230:302 */     Calendar timestamp2 = readTimeStamp();
/* 231:303 */     pdu.setDischargeTime(timestamp2);
/* 232:    */     
/* 233:305 */     int status = readByte();
/* 234:306 */     pdu.setStatus(status);
/* 235:    */   }
/* 236:    */   
/* 237:    */   private void parseSmsSubmitMessage(SmsSubmitPdu pdu)
/* 238:    */   {
/* 239:314 */     int messageReference = readByte();
/* 240:315 */     pdu.setMessageReference(messageReference);
/* 241:    */     
/* 242:317 */     int addressLength = readByte();
/* 243:318 */     int addressType = readByte();
/* 244:319 */     String destinationAddress = readAddress(addressLength, addressType);
/* 245:320 */     pdu.setAddressType(addressType);
/* 246:321 */     pdu.setAddress(destinationAddress);
/* 247:    */     
/* 248:323 */     int protocolId = readByte();
/* 249:324 */     pdu.setProtocolIdentifier(protocolId);
/* 250:    */     
/* 251:326 */     int dcs = readByte();
/* 252:327 */     pdu.setDataCodingScheme(dcs);
/* 253:329 */     switch (pdu.getTpVpf())
/* 254:    */     {
/* 255:    */     case 0: 
/* 256:    */       break;
/* 257:    */     case 16: 
/* 258:334 */       int validityInt = readValidityPeriodInt();
/* 259:335 */       pdu.setValidityPeriod(validityInt / 60);
/* 260:336 */       break;
/* 261:    */     case 24: 
/* 262:338 */       Calendar validityDate = readTimeStamp();
/* 263:339 */       pdu.setValidityTimestamp(validityDate);
/* 264:    */     }
/* 265:342 */     parseUserData(pdu);
/* 266:    */   }
/* 267:    */   
/* 268:    */   private String stripAddressFieldPadding(int validSemiOctetNumber, String addressFieldString)
/* 269:    */   {
/* 270:361 */     int validBitNumber = validSemiOctetNumber * 4;
/* 271:362 */     int valid7BitCharNumber = validBitNumber / 7;
/* 272:363 */     return addressFieldString.substring(0, valid7BitCharNumber);
/* 273:    */   }
/* 274:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.ajwcc.pduUtils.gsm3040.PduParser
 * JD-Core Version:    0.7.0.1
 */
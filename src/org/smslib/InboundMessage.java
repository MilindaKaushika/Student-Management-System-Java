/*   1:    */ package org.smslib;
/*   2:    */ 
/*   3:    */ import java.io.UnsupportedEncodingException;
/*   4:    */ import java.util.Date;
/*   5:    */ import org.ajwcc.pduUtils.gsm3040.PduUtils;
/*   6:    */ import org.ajwcc.pduUtils.gsm3040.SmsDeliveryPdu;
/*   7:    */ 
/*   8:    */ public class InboundMessage
/*   9:    */   extends Message
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 2L;
/*  12:    */   private String originator;
/*  13:    */   private int memIndex;
/*  14:    */   private String memLocation;
/*  15:    */   private int mpRefNo;
/*  16:    */   private int mpMaxNo;
/*  17:    */   private int mpSeqNo;
/*  18:    */   private String mpMemIndex;
/*  19:    */   protected String ud;
/*  20:    */   protected String udh;
/*  21:    */   protected String smscNumber;
/*  22:    */   private boolean endsWithMultiChar;
/*  23:    */   
/*  24:    */   public static enum MessageClasses
/*  25:    */   {
/*  26: 42 */     READ,  UNREAD,  ALL;
/*  27:    */     
/*  28:    */     private MessageClasses() {}
/*  29:    */   }
/*  30:    */   
/*  31:    */   public InboundMessage(Date date, String myOriginator, String text, int myMemIndex, String myMemLocation)
/*  32:    */   {
/*  33: 77 */     super(Message.MessageTypes.INBOUND, date, text);
/*  34: 78 */     setOriginator(myOriginator);
/*  35: 79 */     setMemIndex(myMemIndex);
/*  36: 80 */     setMemLocation(myMemLocation);
/*  37: 81 */     setMpRefNo(0);
/*  38: 82 */     setMpMaxNo(0);
/*  39: 83 */     setMpSeqNo(0);
/*  40: 84 */     setMpMemIndex(-1);
/*  41: 85 */     setSmscNumber(null);
/*  42:    */   }
/*  43:    */   
/*  44:    */   public InboundMessage(Message.MessageTypes type, int myMemIndex, String myMemLocation)
/*  45:    */   {
/*  46: 90 */     super(type, null, null);
/*  47: 91 */     setOriginator("");
/*  48: 92 */     setMemIndex(myMemIndex);
/*  49: 93 */     setMemLocation(myMemLocation);
/*  50: 94 */     setMpRefNo(0);
/*  51: 95 */     setMpMaxNo(0);
/*  52: 96 */     setMpSeqNo(0);
/*  53: 97 */     setMpMemIndex(-1);
/*  54: 98 */     setSmscNumber(null);
/*  55:    */   }
/*  56:    */   
/*  57:    */   public InboundMessage(SmsDeliveryPdu pdu, int myMemIndex, String myMemLocation)
/*  58:    */   {
/*  59:103 */     super(Message.MessageTypes.INBOUND, null, null);
/*  60:104 */     setMemIndex(myMemIndex);
/*  61:105 */     setMemLocation(myMemLocation);
/*  62:106 */     setMpRefNo(0);
/*  63:107 */     setMpMaxNo(0);
/*  64:108 */     setMpSeqNo(0);
/*  65:109 */     setMpMemIndex(-1);
/*  66:110 */     int dcsEncoding = PduUtils.extractDcsEncoding(pdu.getDataCodingScheme());
/*  67:111 */     switch (dcsEncoding)
/*  68:    */     {
/*  69:    */     case 0: 
/*  70:114 */       setEncoding(Message.MessageEncodings.ENC7BIT);
/*  71:115 */       break;
/*  72:    */     case 4: 
/*  73:117 */       setEncoding(Message.MessageEncodings.ENC8BIT);
/*  74:118 */       break;
/*  75:    */     case 8: 
/*  76:120 */       setEncoding(Message.MessageEncodings.ENCUCS2);
/*  77:121 */       break;
/*  78:    */     default: 
/*  79:123 */       throw new RuntimeException("Unknown encoding value: " + dcsEncoding);
/*  80:    */     }
/*  81:125 */     if ((pdu.getAddressType() & 0x50) == 80) {
/*  82:125 */       setOriginator(pdu.getAddress());
/*  83:126 */     } else if ((pdu.getAddressType() & 0x10) == 16) {
/*  84:126 */       setOriginator("+" + pdu.getAddress());
/*  85:    */     } else {
/*  86:127 */       setOriginator(pdu.getAddress());
/*  87:    */     }
/*  88:128 */     setDate(pdu.getTimestamp());
/*  89:129 */     setSmscNumber(pdu.getSmscAddress());
/*  90:130 */     extractData(pdu);
/*  91:131 */     if (pdu.isConcatMessage())
/*  92:    */     {
/*  93:133 */       setMpRefNo(pdu.getMpRefNo());
/*  94:134 */       setMpMaxNo(pdu.getMpMaxNo());
/*  95:135 */       setMpSeqNo(pdu.getMpSeqNo());
/*  96:    */     }
/*  97:137 */     if (pdu.isPortedMessage())
/*  98:    */     {
/*  99:139 */       setDstPort(pdu.getDestPort());
/* 100:140 */       setSrcPort(pdu.getSrcPort());
/* 101:    */     }
/* 102:142 */     if (pdu.hasTpUdhi()) {
/* 103:144 */       this.udh = PduUtils.bytesToPdu(pdu.getUDHData());
/* 104:    */     }
/* 105:146 */     this.ud = PduUtils.bytesToPdu(pdu.getUserDataAsBytes());
/* 106:148 */     if (getEncoding() == Message.MessageEncodings.ENC7BIT)
/* 107:    */     {
/* 108:152 */       byte[] temp = PduUtils.encodedSeptetsToUnencodedSeptets(pdu.getUDData());
/* 109:153 */       if (temp.length == 0) {
/* 110:155 */         this.endsWithMultiChar = false;
/* 111:157 */       } else if (temp[(temp.length - 1)] == 27) {
/* 112:159 */         this.endsWithMultiChar = true;
/* 113:    */       }
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void setEndsWithMultiChar(boolean b)
/* 118:    */   {
/* 119:166 */     this.endsWithMultiChar = b;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean getEndsWithMultiChar()
/* 123:    */   {
/* 124:171 */     return this.endsWithMultiChar;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String getOriginator()
/* 128:    */   {
/* 129:181 */     return this.originator;
/* 130:    */   }
/* 131:    */   
/* 132:    */   void setOriginator(String myOriginator)
/* 133:    */   {
/* 134:186 */     this.originator = myOriginator;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public int getMemIndex()
/* 138:    */   {
/* 139:198 */     return this.memIndex;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void setMemIndex(int myMemIndex)
/* 143:    */   {
/* 144:203 */     this.memIndex = myMemIndex;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getMemLocation()
/* 148:    */   {
/* 149:215 */     return this.memLocation;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public void setMemLocation(String myMemLocation)
/* 153:    */   {
/* 154:220 */     this.memLocation = myMemLocation;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public int getMpMaxNo()
/* 158:    */   {
/* 159:225 */     return this.mpMaxNo;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public void setMpMaxNo(int myMpMaxNo)
/* 163:    */   {
/* 164:230 */     this.mpMaxNo = myMpMaxNo;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public String getMpMemIndex()
/* 168:    */   {
/* 169:235 */     return this.mpMemIndex;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void setMpMemIndex(int myMpMemIndex)
/* 173:    */   {
/* 174:240 */     if (myMpMemIndex == -1) {
/* 175:240 */       this.mpMemIndex = "";
/* 176:    */     } else {
/* 177:241 */       this.mpMemIndex = (this.mpMemIndex + (this.mpMemIndex.length() == 0 ? "" : ",") + myMpMemIndex);
/* 178:    */     }
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int getMpRefNo()
/* 182:    */   {
/* 183:246 */     return this.mpRefNo;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setMpRefNo(int myMpRefNo)
/* 187:    */   {
/* 188:251 */     this.mpRefNo = myMpRefNo;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public int getMpSeqNo()
/* 192:    */   {
/* 193:256 */     return this.mpSeqNo;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void setMpSeqNo(int myMpSeqNo)
/* 197:    */   {
/* 198:261 */     this.mpSeqNo = myMpSeqNo;
/* 199:    */   }
/* 200:    */   
/* 201:    */   public String toString()
/* 202:    */   {
/* 203:267 */     String str = "\n";
/* 204:268 */     str = str + "===============================================================================";
/* 205:269 */     str = str + "\n";
/* 206:270 */     str = str + "<< " + getClass().getSimpleName() + " >>";
/* 207:271 */     str = str + "\n";
/* 208:272 */     str = str + "-------------------------------------------------------------------------------";
/* 209:273 */     str = str + "\n";
/* 210:274 */     str = str + " Gateway Id: " + getGatewayId();
/* 211:275 */     str = str + "\n";
/* 212:276 */     str = str + " Message UUID: " + getUuid();
/* 213:277 */     str = str + "\n";
/* 214:278 */     str = str + " Encoding: " + (getEncoding() == Message.MessageEncodings.ENC8BIT ? "8-bit" : getEncoding() == Message.MessageEncodings.ENC7BIT ? "7-bit" : "UCS2 (Unicode)");
/* 215:279 */     str = str + "\n";
/* 216:280 */     str = str + " Date: " + getDate();
/* 217:281 */     str = str + "\n";
/* 218:282 */     str = str + " Dispatched via SMSC: " + getSmscNumber();
/* 219:283 */     str = str + "\n";
/* 220:284 */     if ((this instanceof InboundBinaryMessage))
/* 221:    */     {
/* 222:286 */       InboundBinaryMessage binaryMessage = (InboundBinaryMessage)this;
/* 223:287 */       if (binaryMessage.getDataBytes() != null)
/* 224:    */       {
/* 225:289 */         String binaryString = PduUtils.bytesToPdu(binaryMessage.getDataBytes());
/* 226:290 */         str = str + " Binary: " + binaryString;
/* 227:291 */         str = str + "\n";
/* 228:    */       }
/* 229:    */       else
/* 230:    */       {
/* 231:295 */         str = str + " Binary: null";
/* 232:296 */         str = str + "\n";
/* 233:    */       }
/* 234:298 */       if ((this instanceof InboundEncryptedMessage)) {
/* 235:    */         try
/* 236:    */         {
/* 237:302 */           InboundEncryptedMessage encryptedMessage = (InboundEncryptedMessage)this;
/* 238:303 */           str = str + " Message is **encrypted**, decrypted text: " + encryptedMessage.getDecryptedText() + "\n";
/* 239:    */         }
/* 240:    */         catch (Exception e)
/* 241:    */         {
/* 242:307 */           str = str + " Could not decrypt message: " + e.getMessage() + "\n";
/* 243:    */         }
/* 244:    */       }
/* 245:    */     }
/* 246:    */     else
/* 247:    */     {
/* 248:313 */       str = str + " Text: " + getText();
/* 249:314 */       str = str + "\n";
/* 250:    */       try
/* 251:    */       {
/* 252:317 */         str = str + " PDU data: " + getPduUserData();
/* 253:318 */         str = str + "\n";
/* 254:    */       }
/* 255:    */       catch (Exception e)
/* 256:    */       {
/* 257:322 */         str = str + " PDU data: <cannot extract properly, udh present>";
/* 258:323 */         str = str + "\n";
/* 259:    */       }
/* 260:    */     }
/* 261:326 */     str = str + " Originator: " + this.originator;
/* 262:327 */     str = str + "\n";
/* 263:328 */     if ((this instanceof StatusReportMessage))
/* 264:    */     {
/* 265:330 */       str = str + " Original Recipient: " + ((StatusReportMessage)this).getRecipient();
/* 266:331 */       str = str + "\n";
/* 267:332 */       str = str + " Delivery Status: " + ((StatusReportMessage)this).getStatus();
/* 268:333 */       str = str + "\n";
/* 269:334 */       str = str + " SMSC Ref No: " + ((StatusReportMessage)this).getRefNo();
/* 270:335 */       str = str + "\n";
/* 271:    */     }
/* 272:337 */     str = str + " Memory Index: " + getMemIndex();
/* 273:338 */     str = str + "\n";
/* 274:339 */     str = str + " Multi-part Memory Index: " + getMpMemIndex();
/* 275:340 */     str = str + "\n";
/* 276:341 */     str = str + " Memory Location: " + getMemLocation();
/* 277:342 */     str = str + "\n";
/* 278:343 */     str = str + " Source / Destination Ports: " + getSrcPort() + " / " + getDstPort();
/* 279:344 */     str = str + "\n";
/* 280:345 */     str = str + "===============================================================================";
/* 281:346 */     str = str + "\n";
/* 282:347 */     return str;
/* 283:    */   }
/* 284:    */   
/* 285:    */   protected void extractData(SmsDeliveryPdu pdu)
/* 286:    */   {
/* 287:353 */     if (pdu.isBinary()) {
/* 288:353 */       throw new RuntimeException("Trying to apply a binary pdu to an InboundMessage");
/* 289:    */     }
/* 290:354 */     setText(pdu.getDecodedText());
/* 291:    */   }
/* 292:    */   
/* 293:    */   public String getPduUserData()
/* 294:    */   {
/* 295:360 */     if ((this.udh != null) && (getEncoding() == Message.MessageEncodings.ENC7BIT)) {
/* 296:360 */       throw new RuntimeException("getPduUserData() not supported for 7-bit messages with UDH");
/* 297:    */     }
/* 298:361 */     return this.ud;
/* 299:    */   }
/* 300:    */   
/* 301:    */   public String getPduUserDataHeader()
/* 302:    */   {
/* 303:371 */     return this.udh;
/* 304:    */   }
/* 305:    */   
/* 306:    */   public void addText(String s)
/* 307:    */     throws UnsupportedEncodingException
/* 308:    */   {
/* 309:377 */     super.addText(s);
/* 310:382 */     if (getEncoding() == Message.MessageEncodings.ENCUCS2) {
/* 311:384 */       this.ud += PduUtils.bytesToPdu(s.getBytes("UTF-16BE"));
/* 312:    */     }
/* 313:    */   }
/* 314:    */   
/* 315:    */   public String getSmscNumber()
/* 316:    */   {
/* 317:390 */     return this.smscNumber;
/* 318:    */   }
/* 319:    */   
/* 320:    */   private void setSmscNumber(String mySmscNumber)
/* 321:    */   {
/* 322:395 */     this.smscNumber = mySmscNumber;
/* 323:    */   }
/* 324:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.InboundMessage
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.ajwcc.pduUtils.gsm3040;
/*   2:    */ 
/*   3:    */ import java.io.ByteArrayOutputStream;
/*   4:    */ import java.io.IOException;
/*   5:    */ import java.util.ArrayList;
/*   6:    */ import java.util.Calendar;
/*   7:    */ import java.util.Date;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ import java.util.TimeZone;
/*  11:    */ import org.ajwcc.pduUtils.gsm3040.ie.ConcatInformationElement;
/*  12:    */ import org.ajwcc.pduUtils.gsm3040.ie.InformationElement;
/*  13:    */ import org.ajwcc.pduUtils.gsm3040.ie.InformationElementFactory;
/*  14:    */ 
/*  15:    */ public class PduGenerator
/*  16:    */ {
/*  17:    */   private ByteArrayOutputStream baos;
/*  18: 28 */   private int firstOctetPosition = -1;
/*  19: 30 */   private boolean updateFirstOctet = false;
/*  20:    */   
/*  21:    */   protected void writeSmscInfo(Pdu pdu)
/*  22:    */     throws Exception
/*  23:    */   {
/*  24: 34 */     if (pdu.getSmscAddress() != null) {
/*  25: 36 */       writeBCDAddress(pdu.getSmscAddress(), pdu.getSmscAddressType(), pdu.getSmscInfoLength());
/*  26:    */     } else {
/*  27: 40 */       writeByte(0);
/*  28:    */     }
/*  29:    */   }
/*  30:    */   
/*  31:    */   protected void writeFirstOctet(Pdu pdu)
/*  32:    */   {
/*  33: 47 */     this.firstOctetPosition = (pdu.getSmscInfoLength() + 1);
/*  34: 48 */     writeByte(pdu.getFirstOctet());
/*  35:    */   }
/*  36:    */   
/*  37:    */   protected void writeValidityPeriodInteger(int validityPeriod)
/*  38:    */   {
/*  39: 54 */     if (validityPeriod == -1)
/*  40:    */     {
/*  41: 56 */       this.baos.write(255);
/*  42:    */     }
/*  43:    */     else
/*  44:    */     {
/*  45:    */       int validityInt;
/*  46:    */      
/*  47: 61 */       if (validityPeriod <= 12)
/*  48:    */       {
/*  49: 61 */         validityInt = validityPeriod * 12 - 1;
/*  50:    */       }
/*  51:    */       else
/*  52:    */       {
/*  53:    */        
/*  54: 62 */         if (validityPeriod <= 24)
/*  55:    */         {
/*  56: 62 */           validityInt = (validityPeriod - 12) * 2 + 143;
/*  57:    */         }
/*  58:    */         else
/*  59:    */         {
/*  60:    */          
/*  61: 63 */           if (validityPeriod <= 720) {
/*  62: 63 */             validityInt = validityPeriod / 24 + 166;
/*  63:    */           } else {
/*  64: 64 */             validityInt = validityPeriod / 168 + 192;
/*  65:    */           }
/*  66:    */         }
/*  67:    */       }
/*  68: 65 */       this.baos.write(validityInt);
/*  69:    */     }
/*  70:    */   }
/*  71:    */   
/*  72:    */   protected void writeTimeStampStringForDate(Date timestamp)
/*  73:    */   {
/*  74: 71 */     Calendar cal = Calendar.getInstance();
/*  75: 72 */     cal.setTime(timestamp);
/*  76: 73 */     int year = cal.get(1) - 2000;
/*  77: 74 */     int month = cal.get(2) + 1;
/*  78: 75 */     int dayOfMonth = cal.get(5);
/*  79: 76 */     int hourOfDay = cal.get(11);
/*  80: 77 */     int minute = cal.get(12);
/*  81: 78 */     int sec = cal.get(13);
/*  82: 79 */     TimeZone tz = cal.getTimeZone();
/*  83: 80 */     int offset = tz.getOffset(timestamp.getTime());
/*  84: 81 */     int minOffset = offset / 60000;
/*  85: 82 */     int tzValue = minOffset / 15;
/*  86: 84 */     if (tzValue < 0) {
/*  87: 86 */       tzValue = 128 - tzValue;
/*  88:    */     }
/*  89: 89 */     this.baos.write(PduUtils.createSwappedBCD(year));
/*  90: 90 */     this.baos.write(PduUtils.createSwappedBCD(month));
/*  91: 91 */     this.baos.write(PduUtils.createSwappedBCD(dayOfMonth));
/*  92: 92 */     this.baos.write(PduUtils.createSwappedBCD(hourOfDay));
/*  93: 93 */     this.baos.write(PduUtils.createSwappedBCD(minute));
/*  94: 94 */     this.baos.write(PduUtils.createSwappedBCD(sec));
/*  95: 95 */     this.baos.write(PduUtils.createSwappedBCD(tzValue));
/*  96:    */   }
/*  97:    */   
/*  98:    */   protected void writeAddress(String address, int addressType, int addressLength)
/*  99:    */     throws Exception
/* 100:    */   {
/* 101:100 */     switch (PduUtils.extractAddressType(addressType))
/* 102:    */     {
/* 103:    */     case 80: 
/* 104:103 */       byte[] textSeptets = PduUtils.stringToUnencodedSeptets(address);
/* 105:104 */       byte[] alphaNumBytes = PduUtils.encode7bitUserData(null, textSeptets);
/* 106:    */       
/* 107:    */ 
/* 108:107 */       this.baos.write(alphaNumBytes.length * 2);
/* 109:    */       
/* 110:109 */       this.baos.write(addressType);
/* 111:    */       
/* 112:111 */       this.baos.write(alphaNumBytes);
/* 113:112 */       break;
/* 114:    */     default: 
/* 115:115 */       writeBCDAddress(address, addressType, addressLength);
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected void writeBCDAddress(String address, int addressType, int addressLength)
/* 120:    */     throws Exception
/* 121:    */   {
/* 122:123 */     this.baos.write(addressLength);
/* 123:    */     
/* 124:125 */     this.baos.write(addressType);
/* 125:128 */     if (address.length() % 2 == 1) {
/* 126:130 */       address = address + "F";
/* 127:    */     }
/* 128:132 */     int digit = 0;
/* 129:133 */     for (int i = 0; i < address.length(); i++)
/* 130:    */     {
/* 131:135 */       char c = address.charAt(i);
/* 132:136 */       if (i % 2 == 1)
/* 133:    */       {
/* 134:138 */         digit |= Integer.parseInt(Character.toString(c), 16) << 4;
/* 135:139 */         this.baos.write(digit);
/* 136:    */         
/* 137:141 */         digit = 0;
/* 138:    */       }
/* 139:    */       else
/* 140:    */       {
/* 141:145 */         digit |= Integer.parseInt(Character.toString(c), 16) & 0xF;
/* 142:    */       }
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   protected void writeUDData(Pdu pdu, int mpRefNo, int partNo)
/* 147:    */   {
/* 148:152 */     int dcs = pdu.getDataCodingScheme();
/* 149:    */     try
/* 150:    */     {
/* 151:155 */       switch (PduUtils.extractDcsEncoding(dcs))
/* 152:    */       {
/* 153:    */       case 0: 
/* 154:158 */         writeUDData7bit(pdu, mpRefNo, partNo);
/* 155:159 */         break;
/* 156:    */       case 4: 
/* 157:161 */         writeUDData8bit(pdu, mpRefNo, partNo);
/* 158:162 */         break;
/* 159:    */       case 8: 
/* 160:164 */         writeUDDataUCS2(pdu, mpRefNo, partNo);
/* 161:165 */         break;
/* 162:    */       default: 
/* 163:167 */         throw new RuntimeException("Invalid DCS encoding: " + PduUtils.extractDcsEncoding(dcs));
/* 164:    */       }
/* 165:    */     }
/* 166:    */     catch (Exception e)
/* 167:    */     {
/* 168:172 */       throw new RuntimeException(e);
/* 169:    */     }
/* 170:    */   }
/* 171:    */   
/* 172:    */   protected void writeUDH(Pdu pdu)
/* 173:    */     throws IOException
/* 174:    */   {
/* 175:179 */     writeUDH(pdu, this.baos);
/* 176:    */   }
/* 177:    */   
/* 178:    */   protected void writeUDH(Pdu pdu, ByteArrayOutputStream udhBaos)
/* 179:    */     throws IOException
/* 180:    */   {
/* 181:188 */     udhBaos.write(pdu.getUDHLength());
/* 182:189 */     for (Iterator<InformationElement> ieIterator = pdu.getInformationElements(); ieIterator.hasNext();)
/* 183:    */     {
/* 184:191 */       InformationElement ie = (InformationElement)ieIterator.next();
/* 185:192 */       udhBaos.write(ie.getIdentifier());
/* 186:193 */       udhBaos.write(ie.getLength());
/* 187:194 */       udhBaos.write(ie.getData());
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   protected int computeOffset(Pdu pdu, int maxMessageLength, int partNo)
/* 192:    */   {
/* 193:203 */     int maxParts = 1;
/* 194:204 */     if (!pdu.isBinary()) {
/* 195:206 */       maxParts = pdu.getDecodedText().length() / maxMessageLength + 1;
/* 196:    */     } else {
/* 197:210 */       maxParts = pdu.getDataBytes().length / maxMessageLength + 1;
/* 198:    */     }
/* 199:212 */     if (pdu.hasTpUdhi()) {
/* 200:214 */       if (pdu.getConcatInfo() != null) {
/* 201:216 */         if (partNo > 0) {
/* 202:218 */           pdu.getConcatInfo().setMpMaxNo(maxParts);
/* 203:    */         }
/* 204:    */       }
/* 205:    */     }
/* 206:    */     int offset;
/* 207:    */     
/* 208:222 */     if ((maxParts > 1) && (partNo > 0))
/* 209:    */     {
/* 210:226 */       if (partNo > maxParts) {
/* 211:226 */         throw new RuntimeException("Invalid partNo: " + partNo + ", maxParts=" + maxParts);
/* 212:    */       }
/* 213:227 */       offset = (partNo - 1) * maxMessageLength;
/* 214:    */     }
/* 215:    */     else
/* 216:    */     {
/* 217:232 */       offset = 0;
/* 218:    */     }
/* 219:234 */     return offset;
/* 220:    */   }
/* 221:    */   
/* 222:    */   protected void checkForConcat(Pdu pdu, int lengthOfText, int maxLength, int maxLengthWithUdh, int mpRefNo, int partNo)
/* 223:    */   {
/* 224:239 */     if ((lengthOfText > maxLengthWithUdh) && ((lengthOfText <= maxLengthWithUdh) || (lengthOfText > maxLength))) {
/* 225:246 */       if (pdu.getConcatInfo() != null)
/* 226:    */       {
/* 227:249 */         pdu.getConcatInfo().setMpRefNo(mpRefNo);
/* 228:250 */         pdu.getConcatInfo().setMpSeqNo(partNo);
/* 229:    */       }
/* 230:    */       else
/* 231:    */       {
/* 232:258 */         ConcatInformationElement concatInfo = InformationElementFactory.generateConcatInfo(mpRefNo, partNo);
/* 233:259 */         pdu.addInformationElement(concatInfo);
/* 234:260 */         this.updateFirstOctet = true;
/* 235:    */       }
/* 236:    */     }
/* 237:    */   }
/* 238:    */   
/* 239:    */   protected int computePotentialUdhLength(Pdu pdu)
/* 240:    */   {
/* 241:267 */     int currentUdhLength = pdu.getTotalUDHLength();
/* 242:268 */     if (currentUdhLength == 0) {
/* 243:271 */       return ConcatInformationElement.getDefaultConcatLength() + 1;
/* 244:    */     }
/* 245:276 */     return currentUdhLength + ConcatInformationElement.getDefaultConcatLength();
/* 246:    */   }
/* 247:    */   
/* 248:    */   protected void writeUDData7bit(Pdu pdu, int mpRefNo, int partNo)
/* 249:    */     throws Exception
/* 250:    */   {
/* 251:282 */     String decodedText = pdu.getDecodedText();
/* 252:    */     
/* 253:    */ 
/* 254:    */ 
/* 255:    */ 
/* 256:287 */     byte[] textSeptetsForDecodedText = PduUtils.stringToUnencodedSeptets(decodedText);
/* 257:288 */     int potentialUdhLength = PduUtils.getNumSeptetsForOctets(computePotentialUdhLength(pdu));
/* 258:    */     
/* 259:290 */     checkForConcat(pdu, textSeptetsForDecodedText.length, 160 - PduUtils.getNumSeptetsForOctets(pdu.getTotalUDHLength()), 160 - potentialUdhLength, mpRefNo, partNo);
/* 260:    */     
/* 261:    */ 
/* 262:    */ 
/* 263:    */ 
/* 264:    */ 
/* 265:    */ 
/* 266:    */ 
/* 267:    */ 
/* 268:299 */     int totalUDHLength = pdu.getTotalUDHLength();
/* 269:300 */     int maxMessageLength = 160 - PduUtils.getNumSeptetsForOctets(totalUDHLength);
/* 270:    */     
/* 271:    */ 
/* 272:303 */     byte[] textSeptets = getUnencodedSeptetsForPart(pdu, maxMessageLength, partNo);
/* 273:    */     
/* 274:    */ 
/* 275:306 */     int udLength = PduUtils.getNumSeptetsForOctets(totalUDHLength) + textSeptets.length;
/* 276:307 */     this.baos.write(udLength);
/* 277:    */     
/* 278:    */ 
/* 279:    */ 
/* 280:311 */     byte[] udhBytes = null;
/* 281:312 */     if (pdu.hasTpUdhi())
/* 282:    */     {
/* 283:314 */       ByteArrayOutputStream udhBaos = new ByteArrayOutputStream();
/* 284:315 */       writeUDH(pdu, udhBaos);
/* 285:    */       
/* 286:317 */       udhBytes = udhBaos.toByteArray();
/* 287:    */     }
/* 288:320 */     byte[] udBytes = PduUtils.encode7bitUserData(udhBytes, textSeptets);
/* 289:    */     
/* 290:322 */     this.baos.write(udBytes);
/* 291:    */   }
/* 292:    */   
/* 293:    */   private byte[] getUnencodedSeptetsForPart(Pdu pdu, int maxMessageLength, int partNo)
/* 294:    */   {
/* 295:330 */     int maxParts = 1;
/* 296:    */     
/* 297:    */ 
/* 298:    */ 
/* 299:334 */     byte[] unencodedSeptets = PduUtils.stringToUnencodedSeptets(pdu.getDecodedText());
/* 300:    */     
/* 301:336 */     maxParts = unencodedSeptets.length / maxMessageLength + 1;
/* 302:338 */     if (pdu.hasTpUdhi()) {
/* 303:340 */       if (pdu.getConcatInfo() != null) {
/* 304:342 */         if (partNo > 0) {
/* 305:344 */           pdu.getConcatInfo().setMpMaxNo(maxParts);
/* 306:    */         }
/* 307:    */       }
/* 308:    */     }
/* 309:    */     int offset;
/* 310:    */     
/* 311:348 */     if ((maxParts > 1) && (partNo > 0))
/* 312:    */     {
/* 313:352 */       if (partNo > maxParts) {
/* 314:352 */         throw new RuntimeException("Invalid partNo: " + partNo + ", maxParts=" + maxParts);
/* 315:    */       }
/* 316:353 */       offset = (partNo - 1) * maxMessageLength;
/* 317:    */     }
/* 318:    */     else
/* 319:    */     {
/* 320:358 */       offset = 0;
/* 321:    */     }
/* 322:362 */     byte[] septetsForPart = new byte[Math.min(maxMessageLength, unencodedSeptets.length - offset)];
/* 323:363 */     System.arraycopy(unencodedSeptets, offset, septetsForPart, 0, septetsForPart.length);
/* 324:    */     
/* 325:365 */     return septetsForPart;
/* 326:    */   }
/* 327:    */   
/* 328:    */   protected void writeUDData8bit(Pdu pdu, int mpRefNo, int partNo)
/* 329:    */     throws Exception
/* 330:    */   {
/* 331:    */     byte[] data;
/* 332:    */     
/* 333:373 */     if (pdu.isBinary()) {
/* 334:376 */       data = pdu.getDataBytes();
/* 335:    */     } else {
/* 336:381 */       data = PduUtils.encode8bitUserData(pdu.getDecodedText());
/* 337:    */     }
/* 338:387 */     int potentialUdhLength = computePotentialUdhLength(pdu);
/* 339:    */     
/* 340:389 */     checkForConcat(pdu, data.length, 140 - pdu.getTotalUDHLength(), 140 - potentialUdhLength, mpRefNo, partNo);
/* 341:    */     
/* 342:    */ 
/* 343:    */ 
/* 344:    */ 
/* 345:    */ 
/* 346:    */ 
/* 347:    */ 
/* 348:    */ 
/* 349:398 */     int totalUDHLength = pdu.getTotalUDHLength();
/* 350:399 */     int maxMessageLength = 140 - totalUDHLength;
/* 351:    */     
/* 352:401 */     int offset = computeOffset(pdu, maxMessageLength, partNo);
/* 353:402 */     byte[] dataToWrite = new byte[Math.min(maxMessageLength, data.length - offset)];
/* 354:403 */     System.arraycopy(data, offset, dataToWrite, 0, dataToWrite.length);
/* 355:    */     
/* 356:    */ 
/* 357:    */ 
/* 358:407 */     int udLength = totalUDHLength + dataToWrite.length;
/* 359:    */     
/* 360:409 */     this.baos.write(udLength);
/* 361:411 */     if (pdu.hasTpUdhi()) {
/* 362:413 */       writeUDH(pdu, this.baos);
/* 363:    */     }
/* 364:416 */     this.baos.write(dataToWrite);
/* 365:    */   }
/* 366:    */   
/* 367:    */   protected void writeUDDataUCS2(Pdu pdu, int mpRefNo, int partNo)
/* 368:    */     throws Exception
/* 369:    */   {
/* 370:421 */     String decodedText = pdu.getDecodedText();
/* 371:    */     
/* 372:    */ 
/* 373:    */ 
/* 374:    */ 
/* 375:426 */     int potentialUdhLength = computePotentialUdhLength(pdu);
/* 376:    */     
/* 377:428 */     checkForConcat(pdu, decodedText.length(), (140 - pdu.getTotalUDHLength()) / 2, (140 - potentialUdhLength) / 2, mpRefNo, partNo);
/* 378:    */     
/* 379:    */ 
/* 380:    */ 
/* 381:    */ 
/* 382:    */ 
/* 383:    */ 
/* 384:    */ 
/* 385:    */ 
/* 386:437 */     int totalUDHLength = pdu.getTotalUDHLength();
/* 387:438 */     int maxMessageLength = (140 - totalUDHLength) / 2;
/* 388:    */     
/* 389:440 */     int offset = computeOffset(pdu, maxMessageLength, partNo);
/* 390:441 */     String textToEncode = decodedText.substring(offset, Math.min(offset + maxMessageLength, decodedText.length()));
/* 391:    */     
/* 392:    */ 
/* 393:    */ 
/* 394:445 */     int udLength = totalUDHLength + textToEncode.length() * 2;
/* 395:    */     
/* 396:447 */     this.baos.write(udLength);
/* 397:449 */     if (pdu.hasTpUdhi()) {
/* 398:451 */       writeUDH(pdu, this.baos);
/* 399:    */     }
/* 400:454 */     this.baos.write(PduUtils.encodeUcs2UserData(textToEncode));
/* 401:    */   }
/* 402:    */   
/* 403:    */   protected void writeByte(int i)
/* 404:    */   {
/* 405:459 */     this.baos.write(i);
/* 406:    */   }
/* 407:    */   
/* 408:    */   protected void writeBytes(byte[] b)
/* 409:    */     throws Exception
/* 410:    */   {
/* 411:464 */     this.baos.write(b);
/* 412:    */   }
/* 413:    */   
/* 414:    */   public List<String> generatePduList(Pdu pdu, int mpRefNo)
/* 415:    */   {
/* 416:471 */     ArrayList<String> pduList = new ArrayList();
/* 417:472 */     for (int i = 1; i <= pdu.getMpMaxNo(); i++)
/* 418:    */     {
/* 419:474 */       String pduString = generatePduString(pdu, mpRefNo, i);
/* 420:475 */       pduList.add(pduString);
/* 421:    */     }
/* 422:477 */     return pduList;
/* 423:    */   }
/* 424:    */   
/* 425:    */   public String generatePduString(Pdu pdu)
/* 426:    */   {
/* 427:482 */     return generatePduString(pdu, -1, -1);
/* 428:    */   }
/* 429:    */   
/* 430:    */   public String generatePduString(Pdu pdu, int mpRefNo, int partNo)
/* 431:    */   {
/* 432:    */     try
/* 433:    */     {
/* 434:492 */       this.baos = new ByteArrayOutputStream();
/* 435:493 */       this.firstOctetPosition = -1;
/* 436:494 */       this.updateFirstOctet = false;
/* 437:496 */       switch (pdu.getTpMti())
/* 438:    */       {
/* 439:    */       case 0: 
/* 440:499 */         generateSmsDeliverPduString((SmsDeliveryPdu)pdu, mpRefNo, partNo);
/* 441:500 */         break;
/* 442:    */       case 1: 
/* 443:502 */         generateSmsSubmitPduString((SmsSubmitPdu)pdu, mpRefNo, partNo);
/* 444:503 */         break;
/* 445:    */       case 2: 
/* 446:505 */         generateSmsStatusReportPduString((SmsStatusReportPdu)pdu);
/* 447:    */       }
/* 448:511 */       byte[] pduBytes = this.baos.toByteArray();
/* 449:512 */       if (this.updateFirstOctet) {
/* 450:514 */         pduBytes[this.firstOctetPosition] = ((byte)(pdu.getFirstOctet() & 0xFF));
/* 451:    */       }
/* 452:516 */       return PduUtils.bytesToPdu(pduBytes);
/* 453:    */     }
/* 454:    */     catch (Exception e)
/* 455:    */     {
/* 456:520 */       throw new RuntimeException(e);
/* 457:    */     }
/* 458:    */   }
/* 459:    */   
/* 460:    */   protected void generateSmsSubmitPduString(SmsSubmitPdu pdu, int mpRefNo, int partNo)
/* 461:    */     throws Exception
/* 462:    */   {
/* 463:527 */     writeSmscInfo(pdu);
/* 464:    */     
/* 465:529 */     writeFirstOctet(pdu);
/* 466:    */     
/* 467:531 */     writeByte(pdu.getMessageReference());
/* 468:    */     
/* 469:533 */     writeAddress(pdu.getAddress(), pdu.getAddressType(), pdu.getAddress().length());
/* 470:    */     
/* 471:535 */     writeByte(pdu.getProtocolIdentifier());
/* 472:    */     
/* 473:537 */     writeByte(pdu.getDataCodingScheme());
/* 474:539 */     switch (pdu.getTpVpf())
/* 475:    */     {
/* 476:    */     case 16: 
/* 477:542 */       writeValidityPeriodInteger(pdu.getValidityPeriod());
/* 478:543 */       break;
/* 479:    */     case 24: 
/* 480:545 */       writeTimeStampStringForDate(pdu.getValidityDate());
/* 481:    */     }
/* 482:550 */     writeUDData(pdu, mpRefNo, partNo);
/* 483:    */   }
/* 484:    */   
/* 485:    */   protected void generateSmsDeliverPduString(SmsDeliveryPdu pdu, int mpRefNo, int partNo)
/* 486:    */     throws Exception
/* 487:    */   {
/* 488:558 */     writeSmscInfo(pdu);
/* 489:    */     
/* 490:560 */     writeFirstOctet(pdu);
/* 491:    */     
/* 492:562 */     writeAddress(pdu.getAddress(), pdu.getAddressType(), pdu.getAddress().length());
/* 493:    */     
/* 494:564 */     writeByte(pdu.getProtocolIdentifier());
/* 495:    */     
/* 496:566 */     writeByte(pdu.getDataCodingScheme());
/* 497:    */     
/* 498:568 */     writeTimeStampStringForDate(pdu.getTimestamp());
/* 499:    */     
/* 500:    */ 
/* 501:571 */     writeUDData(pdu, mpRefNo, partNo);
/* 502:    */   }
/* 503:    */   
/* 504:    */   protected void generateSmsStatusReportPduString(SmsStatusReportPdu pdu)
/* 505:    */     throws Exception
/* 506:    */   {
/* 507:577 */     writeSmscInfo(pdu);
/* 508:    */     
/* 509:579 */     writeFirstOctet(pdu);
/* 510:    */     
/* 511:581 */     writeByte(pdu.getMessageReference());
/* 512:    */     
/* 513:583 */     writeAddress(pdu.getAddress(), pdu.getAddressType(), pdu.getAddress().length());
/* 514:    */     
/* 515:585 */     writeTimeStampStringForDate(pdu.getTimestamp());
/* 516:    */     
/* 517:587 */     writeTimeStampStringForDate(pdu.getDischargeTime());
/* 518:    */     
/* 519:589 */     writeByte(pdu.getStatus());
/* 520:    */   }
/* 521:    */ }



/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.ajwcc.pduUtils.gsm3040.PduGenerator

 * JD-Core Version:    0.7.0.1

 */
/*   1:    */ package org.smslib;
/*   2:    */ 
/*   3:    */ import java.util.Date;
/*   4:    */ import java.util.List;
/*   5:    */ import org.ajwcc.pduUtils.gsm3040.Pdu;
/*   6:    */ import org.ajwcc.pduUtils.gsm3040.PduFactory;
/*   7:    */ import org.ajwcc.pduUtils.gsm3040.PduGenerator;
/*   8:    */ import org.ajwcc.pduUtils.gsm3040.PduParser;
/*   9:    */ import org.ajwcc.pduUtils.gsm3040.PduUtils;
/*  10:    */ import org.ajwcc.pduUtils.gsm3040.SmsSubmitPdu;
/*  11:    */ import org.ajwcc.pduUtils.gsm3040.ie.InformationElementFactory;
/*  12:    */ 
/*  13:    */ public class OutboundMessage
/*  14:    */   extends Message
/*  15:    */ {
/*  16:    */   private static final long serialVersionUID = 2L;
/*  17:    */   protected String recipient;
/*  18:    */   private Date dispatchDate;
/*  19:    */   private int validityPeriod;
/*  20:    */   private boolean statusReport;
/*  21:    */   private String from;
/*  22:    */   private MessageStatuses messageStatus;
/*  23:    */   private FailureCauses failureCause;
/*  24:    */   private int retryCount;
/*  25:    */   private int priority;
/*  26:    */   private String refNo;
/*  27:    */   private String errorMessage;
/*  28:    */   private Date scheduledDeliveryDate;
/*  29:    */   
/*  30:    */   public static enum FailureCauses
/*  31:    */   {
/*  32: 43 */     NO_ERROR,  BAD_NUMBER,  BAD_FORMAT,  GATEWAY_FAILURE,  NO_CREDIT,  GATEWAY_AUTH,  NO_ROUTE,  UNKNOWN;
/*  33:    */     
/*  34:    */     private FailureCauses() {}
/*  35:    */   }
/*  36:    */   
/*  37:    */   public static enum MessageStatuses
/*  38:    */   {
/*  39: 82 */     UNSENT,  SENT,  FAILED;
/*  40:    */     
/*  41:    */     private MessageStatuses() {}
/*  42:    */   }
/*  43:    */   
/*  44:    */   public OutboundMessage()
/*  45:    */   {
/*  46:127 */     super(Message.MessageTypes.OUTBOUND, null, null);
/*  47:128 */     setRecipient("");
/*  48:129 */     setValidityPeriod(-1);
/*  49:130 */     setStatusReport(false);
/*  50:131 */     setDCSMessageClass(Message.MessageClasses.MSGCLASS_NONE);
/*  51:132 */     setFrom("");
/*  52:133 */     setDispatchDate(null);
/*  53:134 */     setDate(new Date());
/*  54:135 */     setEncoding(Message.MessageEncodings.ENC7BIT);
/*  55:136 */     setMessageStatus(MessageStatuses.UNSENT);
/*  56:137 */     setFailureCause(FailureCauses.NO_ERROR);
/*  57:138 */     setPriority(0);
/*  58:139 */     setRefNo("");
/*  59:140 */     setGatewayId("*");
/*  60:141 */     setRetryCount(0);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public OutboundMessage(String myRecipient, String text)
/*  64:    */   {
/*  65:154 */     super(Message.MessageTypes.OUTBOUND, new Date(), text);
/*  66:155 */     setRecipient(myRecipient);
/*  67:156 */     setValidityPeriod(-1);
/*  68:157 */     setStatusReport(false);
/*  69:158 */     setDCSMessageClass(Message.MessageClasses.MSGCLASS_NONE);
/*  70:159 */     setFrom("");
/*  71:160 */     setDispatchDate(null);
/*  72:161 */     setDate(new Date());
/*  73:162 */     setEncoding(Message.MessageEncodings.ENC7BIT);
/*  74:163 */     setMessageStatus(MessageStatuses.UNSENT);
/*  75:164 */     setFailureCause(FailureCauses.NO_ERROR);
/*  76:165 */     setPriority(0);
/*  77:166 */     setRefNo("");
/*  78:167 */     setGatewayId("*");
/*  79:168 */     setRetryCount(0);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public String getRecipient()
/*  83:    */   {
/*  84:179 */     return this.recipient;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setRecipient(String myRecipient)
/*  88:    */   {
/*  89:191 */     this.recipient = myRecipient;
/*  90:    */   }
/*  91:    */   
/*  92:    */   public Date getDispatchDate()
/*  93:    */   {
/*  94:202 */     if (this.dispatchDate != null) {
/*  95:202 */       return new Date(this.dispatchDate.getTime());
/*  96:    */     }
/*  97:203 */     return null;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setDispatchDate(Date myDispatchDate)
/* 101:    */   {
/* 102:208 */     this.dispatchDate = myDispatchDate;
/* 103:    */   }
/* 104:    */   
/* 105:    */   public boolean getFlashSms()
/* 106:    */   {
/* 107:220 */     if (getDCSMessageClass() == Message.MessageClasses.MSGCLASS_FLASH) {
/* 108:220 */       return true;
/* 109:    */     }
/* 110:221 */     return false;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void setFlashSms(boolean flashSms)
/* 114:    */   {
/* 115:238 */     if (flashSms) {
/* 116:238 */       setDCSMessageClass(Message.MessageClasses.MSGCLASS_FLASH);
/* 117:    */     } else {
/* 118:239 */       setDCSMessageClass(Message.MessageClasses.MSGCLASS_NONE);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public boolean getStatusReport()
/* 123:    */   {
/* 124:249 */     return this.statusReport;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void setStatusReport(boolean myStatusReport)
/* 128:    */   {
/* 129:264 */     this.statusReport = myStatusReport;
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int getValidityPeriod()
/* 133:    */   {
/* 134:275 */     return this.validityPeriod;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public void setValidityPeriod(int myValidityPeriod)
/* 138:    */   {
/* 139:287 */     this.validityPeriod = myValidityPeriod;
/* 140:    */   }
/* 141:    */   
/* 142:    */   public String getFrom()
/* 143:    */   {
/* 144:299 */     return this.from;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public void setFrom(String myFrom)
/* 148:    */   {
/* 149:316 */     this.from = myFrom;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public MessageStatuses getMessageStatus()
/* 153:    */   {
/* 154:327 */     return this.messageStatus;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public void setMessageStatus(MessageStatuses myMessageStatus)
/* 158:    */   {
/* 159:332 */     this.messageStatus = myMessageStatus;
/* 160:    */   }
/* 161:    */   
/* 162:    */   public FailureCauses getFailureCause()
/* 163:    */   {
/* 164:337 */     return this.failureCause;
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void setFailureCause(FailureCauses myFailureCause)
/* 168:    */   {
/* 169:348 */     if (myFailureCause != FailureCauses.NO_ERROR) {
/* 170:348 */       this.messageStatus = MessageStatuses.FAILED;
/* 171:    */     }
/* 172:349 */     this.failureCause = myFailureCause;
/* 173:    */   }
/* 174:    */   
/* 175:    */   public int getRetryCount()
/* 176:    */   {
/* 177:359 */     return this.retryCount;
/* 178:    */   }
/* 179:    */   
/* 180:    */   public void setRetryCount(int myRetryCount)
/* 181:    */   {
/* 182:364 */     this.retryCount = myRetryCount;
/* 183:    */   }
/* 184:    */   
/* 185:    */   void incrementRetryCount()
/* 186:    */   {
/* 187:369 */     this.retryCount += 1;
/* 188:    */   }
/* 189:    */   
/* 190:    */   public int getPriority()
/* 191:    */   {
/* 192:379 */     return this.priority;
/* 193:    */   }
/* 194:    */   
/* 195:    */   public void setPriority(int myPriority)
/* 196:    */   {
/* 197:390 */     this.priority = myPriority;
/* 198:    */   }
/* 199:    */   
/* 200:    */   public String getRefNo()
/* 201:    */   {
/* 202:403 */     return this.refNo;
/* 203:    */   }
/* 204:    */   
/* 205:    */   public void setRefNo(String myRefNo)
/* 206:    */   {
/* 207:408 */     this.refNo = myRefNo;
/* 208:    */   }
/* 209:    */   
/* 210:    */   public String getErrorMessage()
/* 211:    */   {
/* 212:419 */     return this.errorMessage;
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void setErrorMessage(String errorMessage)
/* 216:    */   {
/* 217:424 */     this.errorMessage = errorMessage;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public String toString()
/* 221:    */   {
/* 222:430 */     String str = "\n";
/* 223:431 */     str = str + "===============================================================================";
/* 224:432 */     str = str + "\n";
/* 225:433 */     str = str + "<< " + getClass().getSimpleName() + " >>";
/* 226:434 */     str = str + "\n";
/* 227:435 */     str = str + "-------------------------------------------------------------------------------";
/* 228:436 */     str = str + "\n";
/* 229:437 */     str = str + " Gateway Id: " + getGatewayId();
/* 230:438 */     str = str + "\n";
/* 231:439 */     str = str + " Message Id: " + getMessageId();
/* 232:440 */     str = str + "\n";
/* 233:441 */     str = str + " Message UUID: " + getUuid();
/* 234:442 */     str = str + "\n";
/* 235:443 */     str = str + " Encoding: " + (getEncoding() == Message.MessageEncodings.ENC8BIT ? "8-bit" : getEncoding() == Message.MessageEncodings.ENC7BIT ? "7-bit" : "UCS2 (Unicode)");
/* 236:444 */     str = str + "\n";
/* 237:445 */     str = str + " Date: " + getDate();
/* 238:446 */     str = str + "\n";
/* 239:447 */     str = str + " SMSC Ref No: " + getRefNo();
/* 240:448 */     str = str + "\n";
/* 241:449 */     str = str + " Recipient: " + getRecipient();
/* 242:450 */     str = str + "\n";
/* 243:451 */     str = str + " Dispatch Date: " + getDispatchDate();
/* 244:452 */     str = str + "\n";
/* 245:453 */     str = str + " Message Status: " + getMessageStatus();
/* 246:454 */     str = str + "\n";
/* 247:455 */     str = str + " Failure Cause: " + getFailureCause();
/* 248:456 */     str = str + "\n";
/* 249:457 */     str = str + " Validity Period (Hours): " + getValidityPeriod();
/* 250:458 */     str = str + "\n";
/* 251:459 */     str = str + " Status Report: " + getStatusReport();
/* 252:460 */     str = str + "\n";
/* 253:461 */     str = str + " Source / Destination Ports: " + getSrcPort() + " / " + getDstPort();
/* 254:462 */     str = str + "\n";
/* 255:463 */     str = str + " Flash SMS: " + getFlashSms();
/* 256:464 */     str = str + "\n";
/* 257:465 */     if ((this instanceof OutboundBinaryMessage))
/* 258:    */     {
/* 259:467 */       OutboundBinaryMessage binaryMessage = (OutboundBinaryMessage)this;
/* 260:468 */       if (binaryMessage.getDataBytes() != null)
/* 261:    */       {
/* 262:470 */         String binaryString = PduUtils.bytesToPdu(binaryMessage.getDataBytes());
/* 263:471 */         str = str + " Binary: " + binaryString;
/* 264:472 */         str = str + "\n";
/* 265:    */       }
/* 266:    */       else
/* 267:    */       {
/* 268:476 */         str = str + " Binary: null";
/* 269:477 */         str = str + "\n";
/* 270:    */       }
/* 271:    */     }
/* 272:    */     else
/* 273:    */     {
/* 274:482 */       str = str + " Text: " + getText();
/* 275:483 */       str = str + "\n";
/* 276:    */       try
/* 277:    */       {
/* 278:486 */         str = str + " PDU data: " + getPduUserData();
/* 279:487 */         str = str + "\n";
/* 280:    */       }
/* 281:    */       catch (Exception e)
/* 282:    */       {
/* 283:491 */         str = str + " PDU data: <cannot extract properly, udh present>";
/* 284:492 */         str = str + "\n";
/* 285:    */       }
/* 286:    */     }
/* 287:495 */     str = str + " Scheduled Delivery: " + this.scheduledDeliveryDate;
/* 288:496 */     str = str + "\n";
/* 289:497 */     str = str + "===============================================================================";
/* 290:498 */     str = str + "\n";
/* 291:499 */     return str;
/* 292:    */   }
/* 293:    */   
/* 294:    */   public List<String> getPdus(String smscNumber, int mpRefNo)
/* 295:    */   {
/* 296:504 */     PduGenerator pduGenerator = new PduGenerator();
/* 297:505 */     SmsSubmitPdu pdu = createPduObject();
/* 298:506 */     initPduObject(pdu, smscNumber);
/* 299:507 */     return pduGenerator.generatePduList(pdu, mpRefNo);
/* 300:    */   }
/* 301:    */   
/* 302:    */   protected SmsSubmitPdu createPduObject()
/* 303:    */   {
/* 304:    */     SmsSubmitPdu pdu;
/* 305:    */  
/* 306:515 */     if (this.statusReport) {
/* 307:517 */       pdu = PduFactory.newSmsSubmitPdu(48);
/* 308:    */     } else {
/* 309:521 */       pdu = PduFactory.newSmsSubmitPdu();
/* 310:    */     }
/* 311:523 */     return pdu;
/* 312:    */   }
/* 313:    */   
/* 314:    */   protected void initPduObject(SmsSubmitPdu pdu, String smscNumber)
/* 315:    */   {
/* 316:528 */     if ((getDstPort() > -1) && (getSrcPort() > -1)) {
/* 317:531 */       pdu.addInformationElement(InformationElementFactory.generatePortInfo(getDstPort(), getSrcPort()));
/* 318:    */     }
/* 319:536 */     String smscNumberForLengthCheck = smscNumber;
/* 320:537 */     if (smscNumber.startsWith("+")) {
/* 321:539 */       smscNumberForLengthCheck = smscNumber.substring(1);
/* 322:    */     }
/* 323:541 */     pdu.setSmscInfoLength(1 + smscNumberForLengthCheck.length() / 2 + (smscNumberForLengthCheck.length() % 2 == 1 ? 1 : 0));
/* 324:    */     
/* 325:543 */     pdu.setSmscAddress(smscNumber);
/* 326:    */     
/* 327:545 */     pdu.setSmscAddressType(PduUtils.getAddressTypeFor(smscNumber));
/* 328:    */     
/* 329:    */ 
/* 330:548 */     pdu.setMessageReference(0);
/* 331:    */     
/* 332:550 */     pdu.setAddress(getRecipient());
/* 333:    */     
/* 334:552 */     pdu.setProtocolIdentifier(0);
/* 335:554 */     if (!pdu.isBinary())
/* 336:    */     {
/* 337:556 */       int dcs = 0;
/* 338:557 */       if (getEncoding() == Message.MessageEncodings.ENC7BIT) {
/* 339:559 */         dcs = 0;
/* 340:561 */       } else if (getEncoding() == Message.MessageEncodings.ENC8BIT) {
/* 341:563 */         dcs = 4;
/* 342:565 */       } else if (getEncoding() == Message.MessageEncodings.ENCUCS2) {
/* 343:567 */         dcs = 8;
/* 344:569 */       } else if (getEncoding() == Message.MessageEncodings.ENCCUSTOM) {
/* 345:572 */         dcs = 0;
/* 346:    */       }
/* 347:574 */       if (getDCSMessageClass() == Message.MessageClasses.MSGCLASS_FLASH) {
/* 348:576 */         dcs |= 0x10;
/* 349:578 */       } else if (getDCSMessageClass() == Message.MessageClasses.MSGCLASS_ME) {
/* 350:580 */         dcs |= 0x11;
/* 351:582 */       } else if (getDCSMessageClass() == Message.MessageClasses.MSGCLASS_SIM) {
/* 352:584 */         dcs |= 0x12;
/* 353:586 */       } else if (getDCSMessageClass() == Message.MessageClasses.MSGCLASS_TE) {
/* 354:588 */         dcs |= 0x13;
/* 355:    */       }
/* 356:590 */       pdu.setDataCodingScheme(dcs);
/* 357:    */     }
/* 358:593 */     pdu.setValidityPeriod(this.validityPeriod);
/* 359:    */     
/* 360:595 */     setPduPayload(pdu);
/* 361:    */   }
/* 362:    */   
/* 363:    */   protected void setPduPayload(SmsSubmitPdu pdu)
/* 364:    */   {
/* 365:600 */     pdu.setDecodedText(getText());
/* 366:    */   }
/* 367:    */   
/* 368:    */   public String getPduUserData()
/* 369:    */   {
/* 370:607 */     PduGenerator pduGenerator = new PduGenerator();
/* 371:608 */     SmsSubmitPdu pdu = createPduObject();
/* 372:609 */     initPduObject(pdu, "");
/* 373:    */     
/* 374:    */ 
/* 375:    */ 
/* 376:    */ 
/* 377:    */ 
/* 378:    */ 
/* 379:    */ 
/* 380:    */ 
/* 381:618 */     List<String> pdus = pduGenerator.generatePduList(pdu, 1);
/* 382:620 */     if ((pdu.hasTpUdhi()) && (getEncoding() == Message.MessageEncodings.ENC7BIT)) {
/* 383:620 */       throw new RuntimeException("getPduUserData() not supported for 7-bit messages with UDH");
/* 384:    */     }
/* 385:622 */     StringBuffer ud = new StringBuffer();
/* 386:623 */     for (String pduString : pdus)
/* 387:    */     {
/* 388:625 */       Pdu newPdu = new PduParser().parsePdu(pduString);
/* 389:626 */       ud.append(PduUtils.bytesToPdu(newPdu.getUserDataAsBytes()));
/* 390:    */     }
/* 391:628 */     return ud.toString();
/* 392:    */   }
/* 393:    */   
/* 394:    */   public String getPduUserDataHeader()
/* 395:    */   {
/* 396:635 */     PduGenerator pduGenerator = new PduGenerator();
/* 397:636 */     SmsSubmitPdu pdu = createPduObject();
/* 398:637 */     initPduObject(pdu, "");
/* 399:    */     
/* 400:    */ 
/* 401:    */ 
/* 402:    */ 
/* 403:642 */     List<String> pdus = pduGenerator.generatePduList(pdu, 1);
/* 404:643 */     Pdu newPdu = new PduParser().parsePdu((String)pdus.get(0));
/* 405:644 */     byte[] udh = newPdu.getUDHData();
/* 406:645 */     if (udh != null) {
/* 407:645 */       return PduUtils.bytesToPdu(udh);
/* 408:    */     }
/* 409:646 */     return null;
/* 410:    */   }
/* 411:    */   
/* 412:    */   public void setEncoding(Message.MessageEncodings encoding)
/* 413:    */   {
/* 414:652 */     if (encoding == Message.MessageEncodings.ENC8BIT)
/* 415:    */     {
/* 416:654 */       if ((this instanceof OutboundBinaryMessage)) {
/* 417:654 */         super.setEncoding(encoding);
/* 418:    */       } else {
/* 419:655 */         throw new RuntimeException("Cannot use 8-bit encoding with OutgoingMessage, use OutgoingBinaryMessage instead");
/* 420:    */       }
/* 421:    */     }
/* 422:    */     else {
/* 423:660 */       super.setEncoding(encoding);
/* 424:    */     }
/* 425:    */   }
/* 426:    */   
/* 427:    */   protected void copyTo(OutboundMessage msg)
/* 428:    */   {
/* 429:666 */     super.copyTo(msg);
/* 430:667 */     msg.setRecipient(getRecipient());
/* 431:668 */     msg.setDispatchDate(getDispatchDate());
/* 432:669 */     msg.setValidityPeriod(getValidityPeriod());
/* 433:670 */     msg.setStatusReport(getStatusReport());
/* 434:671 */     msg.setFlashSms(getFlashSms());
/* 435:672 */     msg.setFrom(getFrom());
/* 436:673 */     msg.setMessageStatus(getMessageStatus());
/* 437:674 */     msg.setFailureCause(getFailureCause());
/* 438:675 */     msg.retryCount = getRetryCount();
/* 439:676 */     msg.setPriority(getPriority());
/* 440:677 */     msg.setRefNo(getRefNo());
/* 441:    */   }
/* 442:    */   
/* 443:    */   public void setScheduledDeliveryDate(Date scheduledDeliveryDate)
/* 444:    */   {
/* 445:681 */     this.scheduledDeliveryDate = scheduledDeliveryDate;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public Date getScheduledDeliveryDate()
/* 449:    */   {
/* 450:685 */     return this.scheduledDeliveryDate;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public long getDeliveryDelay()
/* 454:    */   {
/* 455:689 */     return this.scheduledDeliveryDate == null ? 0L : this.scheduledDeliveryDate.getTime() - System.currentTimeMillis();
/* 456:    */   }
/* 457:    */   
/* 458:    */   public void setDeliveryDelay(long deliveryDelay)
/* 459:    */   {
/* 460:693 */     this.scheduledDeliveryDate = new Date(System.currentTimeMillis() + deliveryDelay);
/* 461:    */   }
/* 462:    */ }



/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.OutboundMessage

 * JD-Core Version:    0.7.0.1

 */
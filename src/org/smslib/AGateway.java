/*   1:    */ package org.smslib;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.concurrent.PriorityBlockingQueue;
/*   6:    */ import org.smslib.helper.Logger;
/*   7:    */ import org.smslib.notify.GatewayStatusNotification;
/*   8:    */ import org.smslib.notify.NotifyQueueManager;
/*   9:    */ import org.smslib.notify.OutboundMessageNotification;
/*  10:    */ import org.smslib.queues.AbstractQueueManager;
/*  11:    */ import org.smslib.threading.AServiceThread;
/*  12:    */ 
/*  13:    */ public abstract class AGateway
/*  14:    */ {
/*  15:    */   private String gatewayId;
/*  16:    */   private int attributes;
/*  17:    */   private boolean inbound;
/*  18:    */   private boolean outbound;
/*  19:    */   private Protocols protocol;
/*  20:    */   private Statistics statistics;
/*  21:    */   private String from;
/*  22:    */   private int deliveryErrorCode;
/*  23:    */   protected GatewayStatuses status;
/*  24:    */   protected int restartCount;
/*  25:    */   private QueueManager queueManager;
/*  26:    */   
/*  27:    */   public static enum Protocols
/*  28:    */   {
/*  29: 48 */     PDU,  TEXT;
/*  30:    */     
/*  31:    */     private Protocols() {}
/*  32:    */   }
/*  33:    */   
/*  34:    */   public static enum GatewayStatuses
/*  35:    */   {
/*  36: 58 */     STOPPED,  STOPPING,  STARTING,  STARTED,  FAILURE,  RESTART;
/*  37:    */     
/*  38:    */     private GatewayStatuses() {}
/*  39:    */   }
/*  40:    */   
/*  41:    */   public static enum AsyncEvents
/*  42:    */   {
/*  43: 63 */     DELETE,  NOTHING,  INBOUNDMESSAGE,  INBOUNDSTATUSREPORTMESSAGE,  INBOUNDCALL,  USSDRESPONSE;
/*  44:    */     
/*  45:    */     private AsyncEvents() {}
/*  46:    */   }
/*  47:    */   
/*  48:    */   public AGateway(String id)
/*  49:    */   {
/*  50:109 */     this.gatewayId = id;
/*  51:110 */     this.inbound = false;
/*  52:111 */     this.outbound = false;
/*  53:112 */     this.attributes = 0;
/*  54:113 */     this.protocol = Protocols.PDU;
/*  55:114 */     this.from = "";
/*  56:115 */     this.statistics = new Statistics();
/*  57:116 */     this.from = "";
/*  58:117 */     this.deliveryErrorCode = -1;
/*  59:118 */     this.status = GatewayStatuses.STOPPED;
/*  60:119 */     this.restartCount = 0;
/*  61:    */   }
/*  62:    */   
/*  63:    */   public void setAttributes(int myAttributes)
/*  64:    */   {
/*  65:124 */     this.attributes = myAttributes;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int getAttributes()
/*  69:    */   {
/*  70:129 */     return this.attributes;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public AGateway getMyself()
/*  74:    */   {
/*  75:134 */     return this;
/*  76:    */   }
/*  77:    */   
/*  78:    */   public boolean isInbound()
/*  79:    */   {
/*  80:144 */     return this.inbound;
/*  81:    */   }
/*  82:    */   
/*  83:    */   public void setInbound(boolean value)
/*  84:    */   {
/*  85:156 */     if ((this.attributes & 0x2) != 0) {
/*  86:156 */       this.inbound = value;
/*  87:    */     }
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean isOutbound()
/*  91:    */   {
/*  92:166 */     return this.outbound;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setOutbound(boolean value)
/*  96:    */   {
/*  97:178 */     if ((this.attributes & 0x1) != 0) {
/*  98:178 */       this.outbound = value;
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public void setProtocol(Protocols myProtocoll)
/* 103:    */   {
/* 104:192 */     this.protocol = myProtocoll;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public Protocols getProtocol()
/* 108:    */   {
/* 109:204 */     return this.protocol;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public String getGatewayId()
/* 113:    */   {
/* 114:214 */     return this.gatewayId;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public GatewayStatuses getStatus()
/* 118:    */   {
/* 119:225 */     return this.status;
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void setStatus(GatewayStatuses myStatus)
/* 123:    */   {
/* 124:237 */     Service.getInstance().getNotifyQueueManager().getNotifyQueue().add(new GatewayStatusNotification(getMyself(), getStatus(), myStatus));
/* 125:238 */     this.status = myStatus;
/* 126:    */   }
/* 127:    */   
/* 128:    */   public int getInboundMessageCount()
/* 129:    */   {
/* 130:248 */     return this.statistics.inbound;
/* 131:    */   }
/* 132:    */   
/* 133:    */   public void incInboundMessageCount()
/* 134:    */   {
/* 135:253 */     this.statistics.inbound += 1;
/* 136:    */   }
/* 137:    */   
/* 138:    */   public int getOutboundMessageCount()
/* 139:    */   {
/* 140:263 */     return this.statistics.outbound;
/* 141:    */   }
/* 142:    */   
/* 143:    */   public void incOutboundMessageCount()
/* 144:    */   {
/* 145:268 */     this.statistics.outbound += 1;
/* 146:    */   }
/* 147:    */   
/* 148:    */   public String getFrom()
/* 149:    */   {
/* 150:280 */     return this.from;
/* 151:    */   }
/* 152:    */   
/* 153:    */   public void setFrom(String myFrom)
/* 154:    */   {
/* 155:293 */     this.from = myFrom;
/* 156:    */   }
/* 157:    */   
/* 158:    */   public void startGateway()
/* 159:    */     throws Exception
/* 160:    */   {
/* 161:298 */     setStatus(GatewayStatuses.STARTING);
/* 162:299 */     this.queueManager = new QueueManager("QueueManager [" + this.gatewayId + "]", getQueueSchedulingInterval());
/* 163:300 */     this.restartCount += 1;
/* 164:301 */     setStatus(GatewayStatuses.STARTED);
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void stopGateway()
/* 168:    */     throws Exception
/* 169:    */   {
/* 170:306 */     setStatus(GatewayStatuses.STOPPING);
/* 171:307 */     if (this.queueManager != null)
/* 172:    */     {
/* 173:309 */       this.queueManager.cancel();
/* 174:310 */       this.queueManager = null;
/* 175:    */     }
/* 176:312 */     setStatus(GatewayStatuses.STOPPED);
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void readMessages(Collection<InboundMessage> msgList, InboundMessage.MessageClasses msgClass)
/* 180:    */     throws Exception
/* 181:    */   {
/* 182:317 */     throw new Exception("Feature not supported.");
/* 183:    */   }
/* 184:    */   
/* 185:    */   public InboundMessage readMessage(String memLoc, int memIndex)
/* 186:    */     throws Exception
/* 187:    */   {
/* 188:322 */     throw new Exception("Feature not supported.");
/* 189:    */   }
/* 190:    */   
/* 191:    */   public boolean sendMessage(OutboundMessage msg)
/* 192:    */     throws Exception
/* 193:    */   {
/* 194:327 */     throw new Exception("Feature not supported.");
/* 195:    */   }
/* 196:    */   
/* 197:    */   public int sendMessages(Collection<OutboundMessage> msgList)
/* 198:    */     throws Exception
/* 199:    */   {
/* 200:332 */     int cnt = 0;
/* 201:333 */     for (OutboundMessage msg : msgList) {
/* 202:334 */       if (sendMessage(msg)) {
/* 203:334 */         cnt++;
/* 204:    */       }
/* 205:    */     }
/* 206:335 */     return cnt;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public boolean deleteMessage(InboundMessage msg)
/* 210:    */     throws Exception
/* 211:    */   {
/* 212:340 */     throw new Exception("Feature not supported.");
/* 213:    */   }
/* 214:    */   
/* 215:    */   public float queryBalance()
/* 216:    */     throws Exception
/* 217:    */   {
/* 218:358 */     throw new Exception("Feature not supported.");
/* 219:    */   }
/* 220:    */   
/* 221:    */   public boolean queryCoverage(OutboundMessage msg)
/* 222:    */     throws Exception
/* 223:    */   {
/* 224:379 */     throw new Exception("Feature not supported.");
/* 225:    */   }
/* 226:    */   
/* 227:    */   public StatusReportMessage.DeliveryStatuses queryMessage(OutboundMessage msg)
/* 228:    */     throws Exception
/* 229:    */   {
/* 230:403 */     return queryMessage(msg.getRefNo());
/* 231:    */   }
/* 232:    */   
/* 233:    */   public StatusReportMessage.DeliveryStatuses queryMessage(String refNo)
/* 234:    */     throws Exception
/* 235:    */   {
/* 236:428 */     throw new Exception("Feature not supported.");
/* 237:    */   }
/* 238:    */   
/* 239:    */   public int readPhonebook(Phonebook phonebook)
/* 240:    */     throws Exception
/* 241:    */   {
/* 242:433 */     throw new Exception("Feature not supported.");
/* 243:    */   }
/* 244:    */   
/* 245:    */   public int getDeliveryErrorCode()
/* 246:    */   {
/* 247:445 */     return this.deliveryErrorCode;
/* 248:    */   }
/* 249:    */   
/* 250:    */   public void setDeliveryErrorCode(int error)
/* 251:    */   {
/* 252:450 */     this.deliveryErrorCode = error;
/* 253:    */   }
/* 254:    */   
/* 255:    */   boolean isCapableOf(int att)
/* 256:    */   {
/* 257:455 */     return (att & this.attributes) == att;
/* 258:    */   }
/* 259:    */   
/* 260:    */   boolean conformsTo(int attrib, boolean required)
/* 261:    */   {
/* 262:460 */     if ((required) && (!isCapableOf(attrib))) {
/* 263:460 */       return false;
/* 264:    */     }
/* 265:461 */     return true;
/* 266:    */   }
/* 267:    */   
/* 268:    */   public static class GatewayAttributes
/* 269:    */   {
/* 270:    */     public static final int SEND = 1;
/* 271:    */     public static final int RECEIVE = 2;
/* 272:    */     public static final int CUSTOMFROM = 4;
/* 273:    */     public static final int BIGMESSAGES = 8;
/* 274:    */     public static final int WAPSI = 16;
/* 275:    */     public static final int PORTADDRESSING = 32;
/* 276:    */     public static final int FLASHSMS = 64;
/* 277:    */     public static final int DELIVERYREPORTS = 128;
/* 278:    */   }
/* 279:    */   
/* 280:    */   static class Statistics
/* 281:    */   {
/* 282:    */     public int inbound;
/* 283:    */     public int outbound;
/* 284:    */     
/* 285:    */     public Statistics()
/* 286:    */     {
/* 287:472 */       this.inbound = 0;
/* 288:473 */       this.outbound = 0;
/* 289:    */     }
/* 290:    */   }
/* 291:    */   
/* 292:    */   public int getRestartCount()
/* 293:    */   {
/* 294:479 */     return this.restartCount;
/* 295:    */   }
/* 296:    */   
/* 297:    */   public abstract int getQueueSchedulingInterval();
/* 298:    */   
/* 299:    */   private class QueueManager
/* 300:    */     extends AServiceThread
/* 301:    */   {
/* 302:    */     public QueueManager(String name, int delay)
/* 303:    */     {
/* 304:486 */       super(delay, 0, true);
/* 305:    */     }
/* 306:    */     
/* 307:    */     public void process()
/* 308:    */       throws Exception
/* 309:    */     {
/* 310:492 */       OutboundMessage msg = null;
/* 311:    */       try
/* 312:    */       {
/* 313:495 */         if (AGateway.this.getStatus() == AGateway.GatewayStatuses.STARTED)
/* 314:    */         {
/* 315:497 */           msg = Service.getInstance().getQueueManager().pollPendingMessage(AGateway.this.getGatewayId());
/* 316:498 */           if (msg != null)
/* 317:    */           {
/* 318:500 */             if (Service.getInstance().getQueueSendingNotification() != null) {
/* 319:500 */               Service.getInstance().getQueueSendingNotification().process(AGateway.this.getMyself(), msg);
/* 320:    */             }
/* 321:    */             try
/* 322:    */             {
/* 323:503 */               if (!AGateway.this.sendMessage(msg))
/* 324:    */               {
/* 325:505 */                 if (msg.getRetryCount() < Service.getInstance().getSettings().QUEUE_RETRIES)
/* 326:    */                 {
/* 327:507 */                   Logger.getInstance().logInfo("Reinserting message to queue.", null, AGateway.this.getGatewayId());
/* 328:508 */                   msg.incrementRetryCount();
/* 329:509 */                   Service.getInstance().getQueueManager().queueMessage(msg);
/* 330:    */                 }
/* 331:    */                 else
/* 332:    */                 {
/* 333:513 */                   Logger.getInstance().logWarn("Maximum number of queue retries exceeded, message lost.", null, AGateway.this.getGatewayId());
/* 334:514 */                   msg.setFailureCause(OutboundMessage.FailureCauses.UNKNOWN);
/* 335:515 */                   Service.getInstance().getNotifyQueueManager().getNotifyQueue().add(new OutboundMessageNotification(AGateway.this.getMyself(), msg));
/* 336:    */                 }
/* 337:    */               }
/* 338:    */               else {
/* 339:520 */                 Service.getInstance().getNotifyQueueManager().getNotifyQueue().add(new OutboundMessageNotification(AGateway.this.getMyself(), msg));
/* 340:    */               }
/* 341:    */             }
/* 342:    */             catch (Exception e)
/* 343:    */             {
/* 344:525 */               Service.getInstance().getQueueManager().queueMessage(msg);
/* 345:526 */               throw e;
/* 346:    */             }
/* 347:    */           }
/* 348:    */         }
/* 349:    */       }
/* 350:    */       catch (InterruptedException e)
/* 351:    */       {
/* 352:533 */         if ((msg != null) && (msg.getMessageStatus() != OutboundMessage.MessageStatuses.SENT)) {
/* 353:533 */           Service.getInstance().getQueueManager().queueMessage(msg);
/* 354:    */         }
/* 355:534 */         Logger.getInstance().logInfo("QueueManager interrupted.", e, AGateway.this.getGatewayId());
/* 356:    */       }
/* 357:    */       catch (Exception e)
/* 358:    */       {
/* 359:538 */         Logger.getInstance().logWarn("Queue exception, marking gateway for reset.", e, AGateway.this.getGatewayId());
/* 360:539 */         AGateway.this.setStatus(AGateway.GatewayStatuses.RESTART);
/* 361:540 */         Service.getInstance().getNotifyQueueManager().getNotifyQueue().add(new OutboundMessageNotification(AGateway.this.getMyself(), msg));
/* 362:    */       }
/* 363:    */     }
/* 364:    */   }
/* 365:    */   
/* 366:    */   public String sendUSSDCommand(String ussdCommand)
/* 367:    */   throws Exception
/* 368:    */   {
/* 369:555 */    throw new Exception("Feature not supported.");
/* 370:    */   }
/* 371:    */   
/* 372:    */   public String sendUSSDCommand(String ussdCommand, boolean interactive)
/* 373:    */     throws Exception
/* 374:    */   {
/* 375:560 */   throw new Exception("Feature not supported.");   
/* 376:    */   }
/* 377:    */   
/* 378:    */   public boolean sendUSSDRequest(USSDRequest request)
/* 379:    */     throws Exception
/* 380:    */   {
/* 381:565 */    throw new Exception("Feature not supported.");     
/* 382:    */   }
/* 383:    */ }



/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.AGateway

 * JD-Core Version:    0.7.0.1

 */
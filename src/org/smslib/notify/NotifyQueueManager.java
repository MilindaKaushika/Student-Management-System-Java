/*   1:    */ package org.smslib.notify;
/*   2:    */ 
/*   3:    */ import java.util.concurrent.PriorityBlockingQueue;
/*   4:    */ import org.smslib.ICallNotification;
/*   5:    */ import org.smslib.IGatewayStatusNotification;
/*   6:    */ import org.smslib.IInboundMessageNotification;
/*   7:    */ import org.smslib.IOutboundMessageNotification;
/*   8:    */ import org.smslib.Service;
/*   9:    */ import org.smslib.helper.Logger;
/*  10:    */ import org.smslib.threading.AServiceThread;
/*  11:    */ 
/*  12:    */ public class NotifyQueueManager
/*  13:    */ {
/*  14:    */   NotificationQueueManager notificationQueueManager;
/*  15:    */   PriorityBlockingQueue<Notification> notifyQueue;
/*  16:    */   
/*  17:    */   public NotifyQueueManager()
/*  18:    */   {
/*  19: 36 */     setNotifyQueue(new PriorityBlockingQueue());
/*  20: 37 */     setNotifyQueueManager(new NotificationQueueManager("NotifyQueueManager", 100));
/*  21:    */   }
/*  22:    */   
/*  23:    */   public void cancel()
/*  24:    */   {
/*  25: 42 */     int counter = 0;
/*  26: 43 */     int prevSize = 0;
/*  27: 45 */     while (getNotifyQueue().size() != 0)
/*  28:    */     {
/*  29: 47 */       if (prevSize != getNotifyQueue().size())
/*  30:    */       {
/*  31: 49 */         prevSize = getNotifyQueue().size();
/*  32: 50 */         counter = 0;
/*  33:    */       }
/*  34:    */       else
/*  35:    */       {
/*  36: 52 */         if ((prevSize == getNotifyQueue().size()) && (counter == 25)) {
/*  37:    */           break;
/*  38:    */         }
/*  39:    */       }
/*  40:    */       try
/*  41:    */       {
/*  42: 55 */         Thread.sleep(200L);
/*  43:    */       }
/*  44:    */       catch (Exception e) {}
/*  45: 61 */       counter++;
/*  46:    */     }
/*  47: 63 */     getNotifyQueueManager().cancel();
/*  48:    */   }
/*  49:    */   
/*  50:    */   public PriorityBlockingQueue<Notification> getNotifyQueue()
/*  51:    */   {
/*  52: 68 */     return this.notifyQueue;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setNotifyQueue(PriorityBlockingQueue<Notification> notifyQueue)
/*  56:    */   {
/*  57: 73 */     this.notifyQueue = notifyQueue;
/*  58:    */   }
/*  59:    */   
/*  60:    */   protected NotificationQueueManager getNotifyQueueManager()
/*  61:    */   {
/*  62: 78 */     return this.notificationQueueManager;
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void setNotifyQueueManager(NotificationQueueManager notifyQueueManager)
/*  66:    */   {
/*  67: 83 */     this.notificationQueueManager = notifyQueueManager;
/*  68:    */   }
/*  69:    */   
/*  70:    */   class NotificationQueueManager
/*  71:    */     extends AServiceThread
/*  72:    */   {
/*  73:    */     public NotificationQueueManager(String name, int delay)
/*  74:    */     {
/*  75: 90 */       super(delay, 0, true);
/*  76:    */     }
/*  77:    */     
/*  78:    */     public void process()
/*  79:    */       throws Exception
/*  80:    */     {
/*  81: 96 */       Logger.getInstance().logDebug("NotifyQueueManager running...", null, null);
/*  82: 97 */       Notification notification = (Notification)NotifyQueueManager.this.getNotifyQueue().take();
/*  83: 98 */       if ((notification instanceof GatewayStatusNotification))
/*  84:    */       {
/*  85:100 */         if (Service.getInstance().getGatewayStatusNotification() != null)
/*  86:    */         {
/*  87:102 */           GatewayStatusNotification n = (GatewayStatusNotification)notification;
/*  88:103 */           Service.getInstance().getGatewayStatusNotification().process(n.getGateway(), n.getOldStatus(), n.getNewStatus());
/*  89:    */         }
/*  90:    */       }
/*  91:106 */       else if ((notification instanceof CallNotification))
/*  92:    */       {
/*  93:108 */         if (Service.getInstance().getCallNotification() != null)
/*  94:    */         {
/*  95:110 */           CallNotification n = (CallNotification)notification;
/*  96:111 */           Service.getInstance().getCallNotification().process(n.getGateway(), n.getCallerId());
/*  97:    */         }
/*  98:    */       }
/*  99:114 */       else if ((notification instanceof InboundMessageNotification))
/* 100:    */       {
/* 101:116 */         if (Service.getInstance().getInboundMessageNotification() != null)
/* 102:    */         {
/* 103:118 */           InboundMessageNotification n = (InboundMessageNotification)notification;
/* 104:119 */           Service.getInstance().getInboundMessageNotification().process(n.getGateway(), n.getMsgType(), n.getMsg());
/* 105:    */         }
/* 106:    */       }
/* 107:122 */       else if ((notification instanceof OutboundMessageNotification)) {
/* 108:124 */         if (Service.getInstance().getOutboundMessageNotification() != null)
/* 109:    */         {
/* 110:126 */           OutboundMessageNotification n = (OutboundMessageNotification)notification;
/* 111:127 */           Service.getInstance().getOutboundMessageNotification().process(n.getGateway(), n.getMsg());
/* 112:    */         }
/* 113:    */       }
/* 114:130 */       Logger.getInstance().logDebug("NotifyQueueManager end...", null, null);
/* 115:    */     }
/* 116:    */   }
/* 117:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.notify.NotifyQueueManager
 * JD-Core Version:    0.7.0.1
 */
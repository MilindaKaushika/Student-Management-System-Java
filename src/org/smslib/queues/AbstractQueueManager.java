/*   1:    */ package org.smslib.queues;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import org.smslib.OutboundMessage;
/*   5:    */ import org.smslib.helper.Logger;
/*   6:    */ import org.smslib.threading.AServiceThread;
/*   7:    */ 
/*   8:    */ public abstract class AbstractQueueManager
/*   9:    */ {
/*  10:    */   protected int queueDelay;
/*  11:    */   private DelayQueueManager delayQueueManager;
/*  12:    */   
/*  13:    */   public AbstractQueueManager()
/*  14:    */   {
/*  15: 45 */     this(200);
/*  16:    */   }
/*  17:    */   
/*  18:    */   public AbstractQueueManager(int queueDelay)
/*  19:    */   {
/*  20: 50 */     this.queueDelay = queueDelay;
/*  21: 51 */     init();
/*  22:    */   }
/*  23:    */   
/*  24:    */   protected void init() {}
/*  25:    */   
/*  26:    */   public abstract boolean queueMessage(OutboundMessage paramOutboundMessage);
/*  27:    */   
/*  28:    */   public abstract boolean removePendingMessage(OutboundMessage paramOutboundMessage);
/*  29:    */   
/*  30:    */   public abstract boolean removePendingMessage(String paramString);
/*  31:    */   
/*  32:    */   public abstract boolean removeDelayedMessage(OutboundMessage paramOutboundMessage);
/*  33:    */   
/*  34:    */   public abstract boolean removeDelayedMessage(String paramString);
/*  35:    */   
/*  36:    */   public abstract boolean removeAllPendingMessages(String paramString);
/*  37:    */   
/*  38:    */   public abstract boolean removeAllPendingMessages();
/*  39:    */   
/*  40:    */   public abstract boolean removeAllDelayedMessages();
/*  41:    */   
/*  42:    */   public abstract OutboundMessage pollDelayedMessage();
/*  43:    */   
/*  44:    */   public abstract OutboundMessage pollPendingMessage(String paramString);
/*  45:    */   
/*  46:    */   public abstract Collection<OutboundMessage> getPendingMessages(String paramString);
/*  47:    */   
/*  48:    */   public abstract int pendingQueueSize(String paramString);
/*  49:    */   
/*  50:    */   public abstract Collection<OutboundMessage> getDelayedMessages();
/*  51:    */   
/*  52:    */   public abstract int delayedQueueSize(String paramString);
/*  53:    */   
/*  54:    */   public int getQueueDelay()
/*  55:    */   {
/*  56:100 */     return this.queueDelay;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setQueueDelay(int queueDelay)
/*  60:    */   {
/*  61:105 */     this.queueDelay = queueDelay;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void start()
/*  65:    */   {
/*  66:110 */     if ((this.delayQueueManager == null) || (this.delayQueueManager.isCanceled())) {
/*  67:112 */       this.delayQueueManager = new DelayQueueManager("DelayQueueManager", this.queueDelay);
/*  68:    */     }
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void stop()
/*  72:    */   {
/*  73:118 */     if (this.delayQueueManager != null) {
/*  74:120 */       this.delayQueueManager.cancel();
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void pause()
/*  79:    */   {
/*  80:126 */     if (this.delayQueueManager != null) {
/*  81:128 */       this.delayQueueManager.disable();
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void resume()
/*  86:    */   {
/*  87:134 */     if (this.delayQueueManager != null) {
/*  88:136 */       this.delayQueueManager.enable();
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   class DelayQueueManager
/*  93:    */     extends AServiceThread
/*  94:    */   {
/*  95:    */     public DelayQueueManager(String name, int delay)
/*  96:    */     {
/*  97:145 */       super(delay, 0, true);
/*  98:    */     }
/*  99:    */     
/* 100:    */     public void process()
/* 101:    */       throws Exception
/* 102:    */     {
/* 103:151 */       Logger.getInstance().logDebug("DelayQueueManager running...", null, null);
/* 104:152 */       OutboundMessage message = AbstractQueueManager.this.pollDelayedMessage();
/* 105:153 */       if (message != null) {
/* 106:153 */         AbstractQueueManager.this.queueMessage(message);
/* 107:    */       }
/* 108:155 */       Logger.getInstance().logDebug("DelayQueueManager end...", null, null);
/* 109:    */     }
/* 110:    */   }
/* 111:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.queues.AbstractQueueManager
 * JD-Core Version:    0.7.0.1
 */
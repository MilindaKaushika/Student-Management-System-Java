/*  1:   */ package org.smslib.queues;
/*  2:   */ 
/*  3:   */ import java.util.concurrent.Delayed;
/*  4:   */ import java.util.concurrent.TimeUnit;
/*  5:   */ import org.smslib.OutboundMessage;
/*  6:   */ 
/*  7:   */ public class ScheduledOutboundMessage
/*  8:   */   implements Delayed
/*  9:   */ {
/* 10:   */   private OutboundMessage message;
/* 11:   */   
/* 12:   */   public ScheduledOutboundMessage(OutboundMessage message)
/* 13:   */   {
/* 14:37 */     this.message = message;
/* 15:   */   }
/* 16:   */   
/* 17:   */   public long getDelay(TimeUnit unit)
/* 18:   */   {
/* 19:46 */     return unit.convert(this.message.getDeliveryDelay(), TimeUnit.MILLISECONDS);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public int compareTo(Delayed o)
/* 23:   */   {
/* 24:55 */     if (this.message.getDeliveryDelay() < ((ScheduledOutboundMessage)o).message.getDeliveryDelay()) {
/* 25:55 */       return -1;
/* 26:   */     }
/* 27:56 */     if (this.message.getDeliveryDelay() > ((ScheduledOutboundMessage)o).message.getDeliveryDelay()) {
/* 28:56 */       return 1;
/* 29:   */     }
/* 30:57 */     return 0;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public OutboundMessage getMessage()
/* 34:   */   {
/* 35:62 */     return this.message;
/* 36:   */   }
/* 37:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.queues.ScheduledOutboundMessage
 * JD-Core Version:    0.7.0.1
 */
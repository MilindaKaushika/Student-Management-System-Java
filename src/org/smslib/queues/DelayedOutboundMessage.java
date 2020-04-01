/*  1:   */ package org.smslib.queues;
/*  2:   */ 
/*  3:   */ import java.util.Date;
/*  4:   */ import java.util.concurrent.Delayed;
/*  5:   */ import java.util.concurrent.TimeUnit;
/*  6:   */ import org.smslib.OutboundMessage;
/*  7:   */ 
/*  8:   */ class DelayedOutboundMessage
/*  9:   */   implements Delayed
/* 10:   */ {
/* 11:   */   OutboundMessage msg;
/* 12:   */   Date at;
/* 13:   */   
/* 14:   */   public DelayedOutboundMessage(OutboundMessage msg, Date at)
/* 15:   */   {
/* 16:36 */     setMsg(msg);
/* 17:37 */     setAt(at);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int compareTo(Delayed object)
/* 21:   */   {
/* 22:42 */     if (getAt().getTime() < ((DelayedOutboundMessage)object).getAt().getTime()) {
/* 23:42 */       return -1;
/* 24:   */     }
/* 25:43 */     if (getAt().getTime() > ((DelayedOutboundMessage)object).getAt().getTime()) {
/* 26:43 */       return 1;
/* 27:   */     }
/* 28:44 */     return 0;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public long getDelay(TimeUnit unit)
/* 32:   */   {
/* 33:49 */     long n = getAt().getTime() - System.currentTimeMillis();
/* 34:50 */     return unit.convert(n, TimeUnit.MILLISECONDS);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public OutboundMessage getMsg()
/* 38:   */   {
/* 39:55 */     return this.msg;
/* 40:   */   }
/* 41:   */   
/* 42:   */   public void setMsg(OutboundMessage msg)
/* 43:   */   {
/* 44:60 */     this.msg = msg;
/* 45:   */   }
/* 46:   */   
/* 47:   */   public Date getAt()
/* 48:   */   {
/* 49:65 */     return this.at;
/* 50:   */   }
/* 51:   */   
/* 52:   */   public void setAt(Date at)
/* 53:   */   {
/* 54:70 */     this.at = at;
/* 55:   */   }
/* 56:   */   
/* 57:   */   public String toString()
/* 58:   */   {
/* 59:76 */     return "Scheduled: " + getAt();
/* 60:   */   }
/* 61:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.queues.DelayedOutboundMessage
 * JD-Core Version:    0.7.0.1
 */
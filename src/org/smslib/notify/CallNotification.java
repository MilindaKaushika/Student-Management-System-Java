/*  1:   */ package org.smslib.notify;
/*  2:   */ 
/*  3:   */ import org.smslib.AGateway;
/*  4:   */ 
/*  5:   */ public class CallNotification
/*  6:   */   extends Notification
/*  7:   */ {
/*  8:   */   private String callerId;
/*  9:   */   
/* 10:   */   public CallNotification(AGateway gateway, String callerId)
/* 11:   */   {
/* 12:31 */     super(gateway);
/* 13:32 */     setCallerId(callerId);
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getCallerId()
/* 17:   */   {
/* 18:37 */     return this.callerId;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public void setCallerId(String callerId)
/* 22:   */   {
/* 23:42 */     this.callerId = callerId;
/* 24:   */   }
/* 25:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.notify.CallNotification
 * JD-Core Version:    0.7.0.1
 */
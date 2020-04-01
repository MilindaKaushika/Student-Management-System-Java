/*  1:   */ package org.smslib.notify;
/*  2:   */ 
/*  3:   */ import org.smslib.AGateway;
/*  4:   */ import org.smslib.OutboundMessage;
/*  5:   */ 
/*  6:   */ public class OutboundMessageNotification
/*  7:   */   extends Notification
/*  8:   */ {
/*  9:   */   private OutboundMessage msg;
/* 10:   */   
/* 11:   */   public OutboundMessageNotification(AGateway gateway, OutboundMessage msg)
/* 12:   */   {
/* 13:32 */     super(gateway);
/* 14:33 */     setMsg(msg);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public OutboundMessage getMsg()
/* 18:   */   {
/* 19:38 */     return this.msg;
/* 20:   */   }
/* 21:   */   
/* 22:   */   public void setMsg(OutboundMessage msg)
/* 23:   */   {
/* 24:43 */     this.msg = msg;
/* 25:   */   }
/* 26:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.notify.OutboundMessageNotification
 * JD-Core Version:    0.7.0.1
 */
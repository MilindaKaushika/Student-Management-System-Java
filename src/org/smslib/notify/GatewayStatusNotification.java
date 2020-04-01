/*  1:   */ package org.smslib.notify;
/*  2:   */ 
/*  3:   */ import org.smslib.AGateway;
/*  4:   */ import org.smslib.AGateway.GatewayStatuses;
/*  5:   */ 
/*  6:   */ public class GatewayStatusNotification
/*  7:   */   extends Notification
/*  8:   */ {
/*  9:   */   private AGateway.GatewayStatuses oldStatus;
/* 10:   */   private AGateway.GatewayStatuses newStatus;
/* 11:   */   
/* 12:   */   public GatewayStatusNotification(AGateway gateway, AGateway.GatewayStatuses oldStatus, AGateway.GatewayStatuses newStatus)
/* 13:   */   {
/* 14:32 */     super(gateway);
/* 15:33 */     setOldStatus(oldStatus);
/* 16:34 */     setNewStatus(newStatus);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public AGateway.GatewayStatuses getOldStatus()
/* 20:   */   {
/* 21:39 */     return this.oldStatus;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public void setOldStatus(AGateway.GatewayStatuses oldStatus)
/* 25:   */   {
/* 26:44 */     this.oldStatus = oldStatus;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public AGateway.GatewayStatuses getNewStatus()
/* 30:   */   {
/* 31:49 */     return this.newStatus;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public void setNewStatus(AGateway.GatewayStatuses newStatus)
/* 35:   */   {
/* 36:54 */     this.newStatus = newStatus;
/* 37:   */   }
/* 38:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.notify.GatewayStatusNotification
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.smslib.notify;
/*  2:   */ 
/*  3:   */ import org.smslib.AGateway;
/*  4:   */ 
/*  5:   */ public class Notification
/*  6:   */   implements Comparable<Object>
/*  7:   */ {
/*  8:   */   private AGateway gateway;
/*  9:   */   
/* 10:   */   public Notification(AGateway gateway)
/* 11:   */   {
/* 12:31 */     setGateway(gateway);
/* 13:   */   }
/* 14:   */   
/* 15:   */   public AGateway getGateway()
/* 16:   */   {
/* 17:36 */     return this.gateway;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setGateway(AGateway gateway)
/* 21:   */   {
/* 22:41 */     this.gateway = gateway;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int compareTo(Object arg0)
/* 26:   */   {
/* 27:47 */     return 0;
/* 28:   */   }
/* 29:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.notify.Notification
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.smslib.balancing;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collection;
/*  5:   */ import org.smslib.AGateway;
/*  6:   */ import org.smslib.OutboundMessage;
/*  7:   */ 
/*  8:   */ public final class RoundRobinLoadBalancer
/*  9:   */   extends LoadBalancer
/* 10:   */ {
/* 11:   */   private int currentGateway;
/* 12:   */   
/* 13:   */   public RoundRobinLoadBalancer()
/* 14:   */   {
/* 15:38 */     this.currentGateway = 0;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public AGateway balance(OutboundMessage msg, Collection<AGateway> candidates)
/* 19:   */   {
/* 20:49 */     ArrayList<AGateway> c = new ArrayList(candidates);
/* 21:   */     int currentIndex;
/* 22:50 */     synchronized (this)
/* 23:   */     {
/* 24:51 */       if (this.currentGateway >= c.size()) {
/* 25:51 */         this.currentGateway = 0;
/* 26:   */       }
/* 27:52 */       currentIndex = this.currentGateway++;
/* 28:   */     }
/* 29:55 */     return (AGateway)c.get(currentIndex);
/* 30:   */   }
/* 31:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.balancing.RoundRobinLoadBalancer
 * JD-Core Version:    0.7.0.1
 */
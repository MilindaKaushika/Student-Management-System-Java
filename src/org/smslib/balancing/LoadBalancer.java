/*  1:   */ package org.smslib.balancing;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collection;
/*  5:   */ import org.smslib.AGateway;
/*  6:   */ import org.smslib.OutboundMessage;
/*  7:   */ 
/*  8:   */ public class LoadBalancer
/*  9:   */ {
/* 10:   */   public AGateway balance(OutboundMessage msg, Collection<AGateway> candidates)
/* 11:   */   {
/* 12:51 */     return (AGateway)new ArrayList(candidates).get(0);
/* 13:   */   }
/* 14:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.balancing.LoadBalancer
 * JD-Core Version:    0.7.0.1
 */
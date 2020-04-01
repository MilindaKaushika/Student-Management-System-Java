/*  1:   */ package org.smslib.routing;
/*  2:   */ 
/*  3:   */ import java.util.Collection;
/*  4:   */ import org.smslib.AGateway;
/*  5:   */ import org.smslib.OutboundMessage;
/*  6:   */ 
/*  7:   */ public class DefaultRouter
/*  8:   */   extends ARouter
/*  9:   */ {
/* 10:   */   public Collection<AGateway> customRoute(OutboundMessage msg, Collection<AGateway> gateways)
/* 11:   */   {
/* 12:26 */     return gateways;
/* 13:   */   }
/* 14:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.routing.DefaultRouter
 * JD-Core Version:    0.7.0.1
 */
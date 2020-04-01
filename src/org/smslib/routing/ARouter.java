/*  1:   */ package org.smslib.routing;
/*  2:   */ 
/*  3:   */ import java.util.ArrayList;
/*  4:   */ import java.util.Collection;
/*  5:   */ import org.smslib.AGateway;
/*  6:   */ import org.smslib.AGateway.GatewayStatuses;
/*  7:   */ import org.smslib.OutboundMessage;
/*  8:   */ 
/*  9:   */ public abstract class ARouter
/* 10:   */ {
/* 11:   */   public Collection<AGateway> route(OutboundMessage msg, Collection<AGateway> gateways)
/* 12:   */   {
/* 13:31 */     ArrayList<AGateway> candidates = new ArrayList();
/* 14:32 */     for (AGateway gtw : gateways) {
/* 15:33 */       if ((gtw.isOutbound()) && (gtw.getStatus() == AGateway.GatewayStatuses.STARTED)) {
/* 16:35 */         if (msg.getGatewayId().equalsIgnoreCase("*")) {
/* 17:35 */           candidates.add(gtw);
/* 18:36 */         } else if (msg.getGatewayId().equalsIgnoreCase(gtw.getGatewayId())) {
/* 19:36 */           candidates.add(gtw);
/* 20:   */         }
/* 21:   */       }
/* 22:   */     }
/* 23:38 */     return customRoute(msg, candidates);
/* 24:   */   }
/* 25:   */   
/* 26:   */   public abstract Collection<AGateway> customRoute(OutboundMessage paramOutboundMessage, Collection<AGateway> paramCollection);
/* 27:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.routing.ARouter
 * JD-Core Version:    0.7.0.1
 */
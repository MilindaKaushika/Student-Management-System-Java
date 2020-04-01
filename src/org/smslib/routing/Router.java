/*   1:    */ package org.smslib.routing;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import org.smslib.AGateway;
/*   6:    */ import org.smslib.AGateway.GatewayStatuses;
/*   7:    */ import org.smslib.OutboundMessage;
/*   8:    */ 
/*   9:    */ public class Router
/*  10:    */ {
/*  11:    */   private ArrayList<AGateway> candidates;
/*  12:    */   private ArrayList<AGateway> allowed;
/*  13:    */   
/*  14:    */   public Router()
/*  15:    */   {
/*  16: 49 */     this.candidates = new ArrayList();
/*  17: 50 */     this.allowed = new ArrayList();
/*  18:    */   }
/*  19:    */   
/*  20:    */   protected ArrayList<AGateway> getCandidates()
/*  21:    */   {
/*  22: 55 */     return this.candidates;
/*  23:    */   }
/*  24:    */   
/*  25:    */   protected ArrayList<AGateway> getAllowed()
/*  26:    */   {
/*  27: 60 */     return this.allowed;
/*  28:    */   }
/*  29:    */   
/*  30:    */   protected void preroute(OutboundMessage msg, Collection<AGateway> gateways)
/*  31:    */   {
/*  32: 72 */     for (AGateway gtw : gateways) {
/*  33: 73 */       if ((gtw.isOutbound()) && (gtw.getStatus() == AGateway.GatewayStatuses.STARTED)) {
/*  34: 75 */         if (msg.getGatewayId().equalsIgnoreCase("*")) {
/*  35: 75 */           getCandidates().add(gtw);
/*  36: 76 */         } else if (msg.getGatewayId().equalsIgnoreCase(gtw.getGatewayId())) {
/*  37: 76 */           getCandidates().add(gtw);
/*  38:    */         }
/*  39:    */       }
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   public Collection<AGateway> route(OutboundMessage msg, Collection<AGateway> gateways)
/*  44:    */   {
/*  45: 87 */     beginRouting();
/*  46: 88 */     preroute(msg, gateways);
/*  47: 89 */     customRouting(msg);
/*  48: 90 */     return getAllowed();
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void customRouting(OutboundMessage msg)
/*  52:    */   {
/*  53:118 */     getAllowed().addAll(getCandidates());
/*  54:    */   }
/*  55:    */   
/*  56:    */   protected final void beginRouting()
/*  57:    */   {
/*  58:127 */     getCandidates().clear();
/*  59:128 */     getAllowed().clear();
/*  60:    */   }
/*  61:    */   
/*  62:    */   protected final void finishRouting()
/*  63:    */   {
/*  64:136 */     getCandidates().clear();
/*  65:137 */     getAllowed().clear();
/*  66:    */   }
/*  67:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.routing.Router
 * JD-Core Version:    0.7.0.1
 */
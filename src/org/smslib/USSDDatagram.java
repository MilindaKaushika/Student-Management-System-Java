/*  1:   */ package org.smslib;
/*  2:   */ 
/*  3:   */ import java.io.Serializable;
/*  4:   */ 
/*  5:   */ public abstract class USSDDatagram
/*  6:   */   implements Serializable
/*  7:   */ {
/*  8:   */   private static final long serialVersionUID = 1L;
/*  9:   */   private String gtwId;
/* 10:   */   private String content;
/* 11:   */   private USSDDcs dcs;
/* 12:   */   
/* 13:   */   public String getGatewayId()
/* 14:   */   {
/* 15:37 */     return this.gtwId;
/* 16:   */   }
/* 17:   */   
/* 18:   */   public void setGatewayId(String aGtwId)
/* 19:   */   {
/* 20:42 */     this.gtwId = aGtwId;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public String getContent()
/* 24:   */   {
/* 25:47 */     return this.content;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public void setContent(String aContent)
/* 29:   */   {
/* 30:52 */     this.content = aContent;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public USSDDcs getDcs()
/* 34:   */   {
/* 35:57 */     return this.dcs;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public void setDcs(USSDDcs aDcs)
/* 39:   */   {
/* 40:62 */     this.dcs = aDcs;
/* 41:   */   }
/* 42:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.USSDDatagram
 * JD-Core Version:    0.7.0.1
 */
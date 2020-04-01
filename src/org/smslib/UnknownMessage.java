/*  1:   */ package org.smslib;
/*  2:   */ 
/*  3:   */ public class UnknownMessage
/*  4:   */   extends InboundMessage
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 2L;
/*  7:   */   private String pdu;
/*  8:   */   
/*  9:   */   public UnknownMessage(String myPdu, int memIndex, String memLocation)
/* 10:   */   {
/* 11:35 */     super(Message.MessageTypes.UNKNOWN, memIndex, memLocation);
/* 12:36 */     this.pdu = myPdu;
/* 13:   */   }
/* 14:   */   
/* 15:   */   public String getPDU()
/* 16:   */   {
/* 17:46 */     return this.pdu;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String toString()
/* 21:   */   {
/* 22:52 */     String str = "";
/* 23:53 */     str = str + "===============================================================================";
/* 24:54 */     str = str + "\n";
/* 25:55 */     str = str + "<< UNKNOWN MESSAGE DUMP >>";
/* 26:56 */     str = str + "\n";
/* 27:57 */     str = str + "-------------------------------------------------------------------------------";
/* 28:58 */     str = str + "\n";
/* 29:59 */     str = str + " Gateway Id: " + getGatewayId();
/* 30:60 */     str = str + "\n";
/* 31:61 */     str = str + " Memory Index: " + getMemIndex();
/* 32:62 */     str = str + "\n";
/* 33:63 */     str = str + " Memory Location: " + getMemLocation();
/* 34:64 */     str = str + "\n";
/* 35:65 */     str = str + "===============================================================================";
/* 36:66 */     str = str + "\n";
/* 37:67 */     return str;
/* 38:   */   }
/* 39:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.UnknownMessage
 * JD-Core Version:    0.7.0.1
 */
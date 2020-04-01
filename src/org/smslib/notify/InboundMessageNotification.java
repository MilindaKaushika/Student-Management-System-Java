/*  1:   */ package org.smslib.notify;
/*  2:   */ 
/*  3:   */ import org.smslib.AGateway;
/*  4:   */ import org.smslib.InboundMessage;
import org.smslib.Message;
/*  5:   */ import org.smslib.Message.MessageTypes;
/*  6:   */ 
/*  7:   */ public class InboundMessageNotification
/*  8:   */   extends Notification
/*  9:   */ {
/* 10:   */   private Message.MessageTypes msgType;
/* 11:   */   private InboundMessage msg;
/* 12:   */   
/* 13:   */   public InboundMessageNotification(AGateway gateway, Message.MessageTypes msgType, InboundMessage msg)
/* 14:   */   {
/* 15:35 */     super(gateway);
/* 16:36 */     setMsgType(msgType);
/* 17:37 */     setMsg(msg);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public Message.MessageTypes getMsgType()
/* 21:   */   {
/* 22:42 */     return this.msgType;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public void setMsgType(Message.MessageTypes msgType)
/* 26:   */   {
/* 27:47 */     this.msgType = msgType;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public InboundMessage getMsg()
/* 31:   */   {
/* 32:52 */     return this.msg;
/* 33:   */   }
/* 34:   */   
/* 35:   */   public void setMsg(InboundMessage msg)
/* 36:   */   {
/* 37:57 */     this.msg = msg;
/* 38:   */   }
/* 39:   */ }



/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.notify.InboundMessageNotification

 * JD-Core Version:    0.7.0.1

 */
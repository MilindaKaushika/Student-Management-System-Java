/*   1:    */ package org.smslib;
/*   2:    */ 
/*   3:    */ import java.util.Date;
/*   4:    */ import org.ajwcc.pduUtils.gsm3040.SmsStatusReportPdu;
/*   5:    */ 
/*   6:    */ public class StatusReportMessage
/*   7:    */   extends InboundMessage
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 1L;
/*  10:    */   private String recipient;
/*  11:    */   private Date sent;
/*  12:    */   private Date received;
/*  13:    */   private DeliveryStatuses status;
/*  14:    */   private String refNo;
/*  15:    */   
/*  16:    */   public static enum DeliveryStatuses
/*  17:    */   {
/*  18: 41 */     UNKNOWN,  DELIVERED,  KEEPTRYING,  ABORTED;
/*  19:    */     
/*  20:    */     private DeliveryStatuses() {}
/*  21:    */   }
/*  22:    */   
/*  23:    */   public StatusReportMessage(SmsStatusReportPdu pdu, int memIndex, String memLocation)
/*  24:    */   {
/*  25: 68 */     super(Message.MessageTypes.STATUSREPORT, memIndex, memLocation);
/*  26: 69 */     this.refNo = String.valueOf(pdu.getMessageReference());
/*  27: 70 */     this.recipient = pdu.getAddress();
/*  28: 71 */     this.sent = pdu.getTimestamp();
/*  29: 72 */     this.received = pdu.getDischargeTime();
/*  30: 73 */     int i = pdu.getStatus();
/*  31: 74 */     if ((i & 0x60) == 0)
/*  32:    */     {
/*  33: 76 */       setText("00 - Succesful Delivery.");
/*  34: 77 */       this.status = DeliveryStatuses.DELIVERED;
/*  35:    */     }
/*  36: 79 */     if ((i & 0x20) == 32)
/*  37:    */     {
/*  38: 81 */       setText("01 - Errors, will retry dispatch.");
/*  39: 82 */       this.status = DeliveryStatuses.KEEPTRYING;
/*  40:    */     }
/*  41: 84 */     if ((i & 0x40) == 64)
/*  42:    */     {
/*  43: 86 */       setText("02 - Errors, stopped retrying dispatch.");
/*  44: 87 */       this.status = DeliveryStatuses.ABORTED;
/*  45:    */     }
/*  46: 89 */     if ((i & 0x60) == 96)
/*  47:    */     {
/*  48: 91 */       setText("03 - Errors, stopped retrying dispatch.");
/*  49: 92 */       this.status = DeliveryStatuses.ABORTED;
/*  50:    */     }
/*  51: 94 */     setDate(null);
/*  52:    */   }
/*  53:    */   
/*  54:    */   public StatusReportMessage(String myRefNo, int memIndex, String memLocation, Date dateOriginal, Date dateReceived)
/*  55:    */   {
/*  56: 99 */     super(Message.MessageTypes.STATUSREPORT, memIndex, memLocation);
/*  57:100 */     this.refNo = myRefNo;
/*  58:101 */     this.sent = dateOriginal;
/*  59:102 */     this.received = dateReceived;
/*  60:103 */     setText("");
/*  61:104 */     this.status = DeliveryStatuses.UNKNOWN;
/*  62:105 */     setDate(null);
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getRecipient()
/*  66:    */   {
/*  67:116 */     return this.recipient;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public Date getReceived()
/*  71:    */   {
/*  72:128 */     return new Date(this.received.getTime());
/*  73:    */   }
/*  74:    */   
/*  75:    */   public void setReceived(Date myReceived)
/*  76:    */   {
/*  77:133 */     this.received = myReceived;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public Date getSent()
/*  81:    */   {
/*  82:144 */     return new Date(this.sent.getTime());
/*  83:    */   }
/*  84:    */   
/*  85:    */   public void setSent(Date mySent)
/*  86:    */   {
/*  87:149 */     this.sent = mySent;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public DeliveryStatuses getStatus()
/*  91:    */   {
/*  92:161 */     return this.status;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void setStatus(DeliveryStatuses myStatus)
/*  96:    */   {
/*  97:166 */     this.status = myStatus;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public String getRefNo()
/* 101:    */   {
/* 102:177 */     return this.refNo;
/* 103:    */   }
/* 104:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.StatusReportMessage
 * JD-Core Version:    0.7.0.1
 */
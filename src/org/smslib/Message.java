/*   1:    */ package org.smslib;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import java.io.UnsupportedEncodingException;
/*   5:    */ import java.util.Date;
/*   6:    */ import java.util.UUID;
/*   7:    */ 
/*   8:    */ public abstract class Message
/*   9:    */   implements Serializable
/*  10:    */ {
/*  11:    */   private static final long serialVersionUID = 2L;
/*  12: 34 */   private static long messageIdSeed = 0L;
/*  13:    */   private final long messageId;
/*  14:    */   private String gtwId;
/*  15:    */   private MessageTypes type;
/*  16:    */   private Date date;
/*  17:    */   private String id;
/*  18:    */   private String text;
/*  19:    */   private String uuid;
/*  20:    */   private MessageEncodings encoding;
/*  21:    */   private MessageClasses messageClass;
/*  22:    */   protected int dstPort;
/*  23:    */   protected int srcPort;
/*  24:    */   protected int messageCharCount;
/*  25:    */   
/*  26:    */   public static enum MessageEncodings
/*  27:    */   {
/*  28: 46 */     ENC7BIT,  ENC8BIT,  ENCUCS2,  ENCCUSTOM;
/*  29:    */     
/*  30:    */     private MessageEncodings() {}
/*  31:    */   }
/*  32:    */   
/*  33:    */   public static enum MessageClasses
/*  34:    */   {
/*  35: 69 */     MSGCLASS_NONE,  MSGCLASS_FLASH,  MSGCLASS_ME,  MSGCLASS_SIM,  MSGCLASS_TE;
/*  36:    */     
/*  37:    */     private MessageClasses() {}
/*  38:    */   }
/*  39:    */   
/*  40:    */   public static enum MessageTypes
/*  41:    */   {
/*  42: 96 */     INBOUND,  OUTBOUND,  STATUSREPORT,  WAPSI,  UNKNOWN;
/*  43:    */     
/*  44:    */     private MessageTypes() {}
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Message(MessageTypes myType, Date myDate, String myText)
/*  48:    */   {
/*  49:139 */     this.messageId = (messageIdSeed++);
/*  50:140 */     this.uuid = UUID.randomUUID().toString();
/*  51:141 */     setGatewayId("");
/*  52:142 */     setType(myType);
/*  53:143 */     setId("");
/*  54:144 */     setDate(myDate);
/*  55:145 */     if (myText != null) {
/*  56:145 */       setText(myText);
/*  57:    */     }
/*  58:146 */     setEncoding(MessageEncodings.ENC7BIT);
/*  59:147 */     setSrcPort(-1);
/*  60:148 */     setDstPort(-1);
/*  61:149 */     this.messageCharCount = 0;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public Date getDate()
/*  65:    */   {
/*  66:162 */     return this.date == null ? null : new Date(this.date.getTime());
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void setDate(Date myDate)
/*  70:    */   {
/*  71:174 */     this.date = (myDate != null ? new Date(myDate.getTime()) : null);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public MessageEncodings getEncoding()
/*  75:    */   {
/*  76:186 */     return this.encoding;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public MessageClasses getDCSMessageClass()
/*  80:    */   {
/*  81:198 */     return this.messageClass;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void setDCSMessageClass(MessageClasses messageClass)
/*  85:    */   {
/*  86:211 */     this.messageClass = messageClass;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public String getGatewayId()
/*  90:    */   {
/*  91:223 */     return this.gtwId;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void setGatewayId(String myGtwId)
/*  95:    */   {
/*  96:235 */     this.gtwId = myGtwId;
/*  97:    */   }
/*  98:    */   
/*  99:    */   public String getId()
/* 100:    */   {
/* 101:246 */     return this.id;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void setId(String myId)
/* 105:    */   {
/* 106:258 */     this.id = myId;
/* 107:    */   }
/* 108:    */   
/* 109:    */   public long getMessageId()
/* 110:    */   {
/* 111:268 */     return this.messageId;
/* 112:    */   }
/* 113:    */   
/* 114:    */   public String getText()
/* 115:    */   {
/* 116:279 */     return this.text;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void setText(String myText)
/* 120:    */   {
/* 121:291 */     this.text = myText;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void addText(String addText)
/* 125:    */     throws UnsupportedEncodingException
/* 126:    */   {
/* 127:296 */     if (this.text == null) {
/* 128:296 */       setText(addText);
/* 129:    */     } else {
/* 130:297 */       this.text += addText;
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public MessageTypes getType()
/* 135:    */   {
/* 136:309 */     return this.type;
/* 137:    */   }
/* 138:    */   
/* 139:    */   void setType(MessageTypes myType)
/* 140:    */   {
/* 141:314 */     this.type = myType;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int getDstPort()
/* 145:    */   {
/* 146:330 */     return this.dstPort;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void setDstPort(int myDstPort)
/* 150:    */   {
/* 151:349 */     this.dstPort = myDstPort;
/* 152:    */   }
/* 153:    */   
/* 154:    */   public int getSrcPort()
/* 155:    */   {
/* 156:365 */     return this.srcPort;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public void setSrcPort(int mySrcPort)
/* 160:    */   {
/* 161:384 */     this.srcPort = mySrcPort;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void setEncoding(MessageEncodings myEncoding)
/* 165:    */   {
/* 166:397 */     this.encoding = myEncoding;
/* 167:    */   }
/* 168:    */   
/* 169:    */   public abstract String getPduUserData();
/* 170:    */   
/* 171:    */   public abstract String getPduUserDataHeader();
/* 172:    */   
/* 173:    */   protected void copyTo(Message msg)
/* 174:    */   {
/* 175:406 */     msg.setDate(getDate());
/* 176:407 */     msg.setEncoding(getEncoding());
/* 177:408 */     msg.setDCSMessageClass(getDCSMessageClass());
/* 178:409 */     msg.setId(getId());
/* 179:410 */     msg.setGatewayId(getGatewayId());
/* 180:411 */     msg.setSrcPort(getSrcPort());
/* 181:412 */     msg.setDstPort(getDstPort());
/* 182:413 */     msg.setType(getType());
/* 183:414 */     msg.setText(getText());
/* 184:415 */     msg.messageCharCount = this.messageCharCount;
/* 185:    */   }
/* 186:    */   
/* 187:    */   public String getUuid()
/* 188:    */   {
/* 189:419 */     return this.uuid;
/* 190:    */   }
/* 191:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.Message
 * JD-Core Version:    0.7.0.1
 */
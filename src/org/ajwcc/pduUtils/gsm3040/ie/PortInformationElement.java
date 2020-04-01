/*  1:   */ package org.ajwcc.pduUtils.gsm3040.ie;
/*  2:   */ 
/*  3:   */ public class PortInformationElement
/*  4:   */   extends InformationElement
/*  5:   */ {
/*  6:   */   public static final int PORT_16BIT = 5;
/*  7:   */   
/*  8:   */   PortInformationElement(byte id, byte[] data)
/*  9:   */   {
/* 10:26 */     super(id, data);
/* 11:27 */     if (getIdentifier() != 5) {
/* 12:27 */       throw new RuntimeException("Invalid identifier " + getIdentifier() + " in data in: " + getClass().getSimpleName());
/* 13:   */     }
/* 14:32 */     if (data.length != 4) {
/* 15:32 */       throw new RuntimeException("Invalid data length in: " + getClass().getSimpleName());
/* 16:   */     }
/* 17:   */   }
/* 18:   */   
/* 19:   */   PortInformationElement(int identifier, int destPort, int srcPort)
/* 20:   */   {
/* 21:38 */     byte[] data = null;
/* 22:39 */     switch (identifier)
/* 23:   */     {
/* 24:   */     case 5: 
/* 25:42 */       data = new byte[4];
/* 26:43 */       data[0] = ((byte)((destPort & 0xFF00) >>> 8));
/* 27:44 */       data[1] = ((byte)(destPort & 0xFF));
/* 28:45 */       data[2] = ((byte)((srcPort & 0xFF00) >>> 8));
/* 29:46 */       data[3] = ((byte)(srcPort & 0xFF));
/* 30:47 */       break;
/* 31:   */     default: 
/* 32:49 */       throw new RuntimeException("Invalid identifier for " + getClass().getSimpleName());
/* 33:   */     }
/* 34:51 */     initialize((byte)(identifier & 0xFF), data);
/* 35:   */   }
/* 36:   */   
/* 37:   */   public int getDestPort()
/* 38:   */   {
/* 39:57 */     byte[] data = getData();
/* 40:58 */     return (data[0] & 0xFF) << 8 | data[1] & 0xFF;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int getSrcPort()
/* 44:   */   {
/* 45:64 */     byte[] data = getData();
/* 46:65 */     return (data[2] & 0xFF) << 8 | data[3] & 0xFF;
/* 47:   */   }
/* 48:   */   
/* 49:   */   public String toString()
/* 50:   */   {
/* 51:71 */     StringBuffer sb = new StringBuffer();
/* 52:72 */     sb.append(super.toString());
/* 53:73 */     sb.append("[Dst Port: ");
/* 54:74 */     sb.append(getDestPort());
/* 55:75 */     sb.append(", Src Port: ");
/* 56:76 */     sb.append(getSrcPort());
/* 57:77 */     sb.append("]");
/* 58:78 */     return sb.toString();
/* 59:   */   }
/* 60:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.ajwcc.pduUtils.gsm3040.ie.PortInformationElement
 * JD-Core Version:    0.7.0.1
 */
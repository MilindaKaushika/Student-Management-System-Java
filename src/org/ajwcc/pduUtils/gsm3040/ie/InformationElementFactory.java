/*  1:   */ package org.ajwcc.pduUtils.gsm3040.ie;
/*  2:   */ 
/*  3:   */ public class InformationElementFactory
/*  4:   */ {
/*  5:   */   public static InformationElement createInformationElement(int id, byte[] data)
/*  6:   */   {
/*  7:26 */     byte iei = (byte)(id & 0xFF);
/*  8:27 */     switch (iei)
/*  9:   */     {
/* 10:   */     case 0: 
/* 11:   */     case 8: 
/* 12:31 */       return new ConcatInformationElement(iei, data);
/* 13:   */     case 5: 
/* 14:33 */       return new PortInformationElement(iei, data);
/* 15:   */     }
/* 16:35 */     return new InformationElement(iei, data);
/* 17:   */   }
/* 18:   */   
/* 19:   */   public static ConcatInformationElement generateConcatInfo(int mpRefNo, int partNo)
/* 20:   */   {
/* 21:41 */     ConcatInformationElement concatInfo = new ConcatInformationElement(ConcatInformationElement.getDefaultConcatType(), mpRefNo, 1, partNo);
/* 22:42 */     return concatInfo;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public static ConcatInformationElement generateConcatInfo(int identifier, int mpRefNo, int partNo)
/* 26:   */   {
/* 27:47 */     ConcatInformationElement concatInfo = new ConcatInformationElement(identifier, mpRefNo, 1, partNo);
/* 28:48 */     return concatInfo;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public static PortInformationElement generatePortInfo(int destPort, int srcPort)
/* 32:   */   {
/* 33:53 */     PortInformationElement portInfo = new PortInformationElement(5, destPort, srcPort);
/* 34:54 */     return portInfo;
/* 35:   */   }
/* 36:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.ajwcc.pduUtils.gsm3040.ie.InformationElementFactory
 * JD-Core Version:    0.7.0.1
 */
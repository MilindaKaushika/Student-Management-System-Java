/*   1:    */ package org.smslib;
/*   2:    */ 
/*   3:    */ public class USSDRequest
/*   4:    */   extends USSDDatagram
/*   5:    */ {
/*   6:    */   private static final long serialVersionUID = 1L;
/*   7:    */   private USSDResultPresentation presentation;
/*   8:    */   
/*   9:    */   public USSDRequest()
/*  10:    */   {
/*  11: 42 */     this.presentation = null;
/*  12:    */   }
/*  13:    */   
/*  14:    */   public USSDRequest(USSDResultPresentation aPresentation, String aContent, USSDDcs aDcs, String aGatewayId)
/*  15:    */     throws IllegalArgumentException
/*  16:    */   {
/*  17: 51 */     this.presentation = aPresentation;
/*  18: 52 */     setContent(aContent);
/*  19: 53 */     setDcs(aDcs);
/*  20: 54 */     setGatewayId(aGatewayId);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public USSDRequest(String aContent)
/*  24:    */   {
/*  25: 63 */     this.presentation = USSDResultPresentation.PRESENTATION_ENABLED;
/*  26: 64 */     setContent(aContent);
/*  27: 65 */     setDcs(USSDDcs.UNSPECIFIED_7BIT);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public String getRawRequest()
/*  31:    */   {
/*  32: 70 */     StringBuffer buf = new StringBuffer();
/*  33: 71 */     buf.append("AT+CUSD=");
/*  34: 72 */     buf.append(this.presentation.getNumeric());
/*  35: 73 */     buf.append(",");
/*  36: 74 */     buf.append("\"");
/*  37: 75 */     buf.append(getContent());
/*  38: 76 */     buf.append("\"");
/*  39: 77 */     buf.append(",");
/*  40: 78 */     buf.append(getDcs().getNumeric());
/*  41: 79 */     buf.append("\r");
/*  42: 80 */     return buf.toString();
/*  43:    */   }
/*  44:    */   
/*  45:    */   public USSDResultPresentation getResultPresentation()
/*  46:    */   {
/*  47: 85 */     return this.presentation;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void setUSSDResultPresentation(USSDResultPresentation aResultPresentation)
/*  51:    */   {
/*  52: 90 */     this.presentation = aResultPresentation;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String toString()
/*  56:    */   {
/*  57: 96 */     StringBuffer buf = new StringBuffer("Gateway: ");
/*  58: 97 */     buf.append(getGatewayId());
/*  59: 98 */     buf.append("\n");
/*  60: 99 */     buf.append("Result presentation: ");
/*  61:100 */     buf.append(this.presentation);
/*  62:101 */     buf.append("\n");
/*  63:102 */     buf.append("Data coding scheme: ");
/*  64:103 */     buf.append(getDcs());
/*  65:104 */     buf.append("\n");
/*  66:105 */     buf.append("Content: ");
/*  67:106 */     buf.append(getContent() != null ? getContent() : "(EMPTY)");
/*  68:107 */     return buf.toString();
/*  69:    */   }
/*  70:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.USSDRequest
 * JD-Core Version:    0.7.0.1
 */
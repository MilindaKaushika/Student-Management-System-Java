/*  1:   */ package org.smslib;
/*  2:   */ 
/*  3:   */ public class Library
/*  4:   */ {
/*  5:   */ //  private static final String LIB_INFOTEXT = "SMSLib: A Java API library for sending and receiving SMS via a GSM modem or other supported gateways.\nThis software is distributed under the terms of the Apache v2.0 License.\nWeb Site: http://smslib.org";
/*  6:   */  /*  5:   */   private static final String LIB_INFOTEXT = "Inta Solutions";

private static final int LIB_VERSION = 3;
/*  7:   */   private static final int LIB_RELEASE = 5;
/*  8:   */   private static final int LIB_SUBRELEASE = 0;
/*  9:   */   private static final String LIB_STATUS = "";
/* 10:   */   
/* 11:   */   public static final String getLibraryDescription()
/* 12:   */   {
/* 13:40 */    // return "SMSLib: A Java API library for sending and receiving SMS via a GSM modem or other supported gateways.\nThis software is distributed under the terms of the Apache v2.0 License.\nWeb Site: http://smslib.org";
/* 14:   */  
/* 13:40 */     return "Inta Solutions";


}
/* 15:   */   
/* 16:   */   public static final String getLibraryVersion()
/* 17:   */   {
/* 18:46 */     String text = "3.5.0";
/* 19:47 */     if ("".length() != 0) {
/* 20:47 */       text = text + "-" + "";
/* 21:   */     }
/* 22:48 */     return text;
/* 23:   */   }
/* 24:   */ }



/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.Library

 * JD-Core Version:    0.7.0.1

 */
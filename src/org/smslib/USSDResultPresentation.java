/*  1:   */ package org.smslib;
/*  2:   */ 
/*  3:   */ public enum USSDResultPresentation
/*  4:   */ {
/*  5:32 */   PRESENTATION_DISABLED(0),  PRESENTATION_ENABLED(1),  CANCEL_SESSION(2);
/*  6:   */   
/*  7:   */   private final int numeric;
/*  8:   */   
/*  9:   */   private USSDResultPresentation(int aNumeric)
/* 10:   */   {
/* 11:45 */     this.numeric = aNumeric;
/* 12:   */   }
/* 13:   */   
/* 14:   */   public int getNumeric()
/* 15:   */   {
/* 16:50 */     return this.numeric;
/* 17:   */   }
/* 18:   */   
/* 19:   */   public String toString()
/* 20:   */   {
/* 21:56 */     return super.toString() + " (" + this.numeric + ")";
/* 22:   */   }
/* 23:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.USSDResultPresentation
 * JD-Core Version:    0.7.0.1
 */
/*  1:   */ package org.smslib;
/*  2:   */ 
/*  3:   */ public class SMSLibException
/*  4:   */   extends Exception
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 2L;
/*  7:   */   private Throwable originalE;
/*  8:   */   
/*  9:   */   public SMSLibException(String errorMessage)
/* 10:   */   {
/* 11:34 */     super(errorMessage);

/* 12:   */   }
/* 13:   */   
/* 14:   */   public SMSLibException() {}
/* 15:   */   
/* 16:   */   public SMSLibException(Throwable originalE)
/* 17:   */   {
/* 18:45 */     this.originalE = originalE;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public SMSLibException(String errorMessage, Throwable originalE)
/* 22:   */   {
/* 23:50 */     super(errorMessage);
/* 24:51 */     this.originalE = originalE;
/* 25:   */   }
/* 26:   */   
/* 27:   */   public Throwable getOriginalException()
/* 28:   */   {
/* 29:56 */     return this.originalE;
/* 30:   */   }
/* 31:   */ }



/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.SMSLibException

 * JD-Core Version:    0.7.0.1

 */
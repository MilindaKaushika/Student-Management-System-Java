/*  1:   */ package org.smslib;
/*  2:   */ 
/*  3:   */ public class TimeoutException
/*  4:   */   extends SMSLibException
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 2L;
/*  7:   */   
/*  8:   */   public TimeoutException(String errorMessage)
/*  9:   */   {
/* 10:32 */     super(errorMessage);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public TimeoutException() {}
/* 14:   */   
/* 15:   */   public TimeoutException(Throwable e)
/* 16:   */   {
/* 17:42 */     super(e.getMessage());
/* 18:   */   }
/* 19:   */   
/* 20:   */   public TimeoutException(String errorMessage, Throwable e)
/* 21:   */   {
/* 22:47 */     super(errorMessage);
/* 23:   */   }
/* 24:   */ }



/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.TimeoutException

 * JD-Core Version:    0.7.0.1

 */
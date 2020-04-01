/*  1:   */ package org.smslib;
/*  2:   */ 
/*  3:   */ public class InvalidMessageException
/*  4:   */   extends GatewayException
/*  5:   */ {
/*  6:   */   private static final long serialVersionUID = 1L;
/*  7:   */   
/*  8:   */   public InvalidMessageException(String errorMessage)
/*  9:   */   {
/* 10:34 */     super(errorMessage);
/* 11:   */   }
/* 12:   */   
/* 13:   */   public InvalidMessageException() {}
/* 14:   */   
/* 15:   */   public InvalidMessageException(Throwable e)
/* 16:   */   {
/* 17:44 */     super(e);
/* 18:   */   }
/* 19:   */   
/* 20:   */   public InvalidMessageException(String errorMessage, Throwable e)
/* 21:   */   {
/* 22:49 */     super(errorMessage, e);
/* 23:   */   }
/* 24:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.InvalidMessageException
 * JD-Core Version:    0.7.0.1
 */
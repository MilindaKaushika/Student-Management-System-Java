/*  1:   */ package org.smslib.crypto;
/*  2:   */ 
/*  3:   */ public abstract class AKey
/*  4:   */ {
/*  5:   */   public static String asHex(byte[] buf)
/*  6:   */   {
/*  7:30 */     StringBuffer strbuf = new StringBuffer(buf.length * 2);
/*  8:32 */     for (int i = 0; i < buf.length; i++)
/*  9:   */     {
/* 10:34 */       if ((buf[i] & 0xFF) < 16) {
/* 11:34 */         strbuf.append("0");
/* 12:   */       }
/* 13:35 */       strbuf.append(Long.toString(buf[i] & 0xFF, 16));
/* 14:   */     }
/* 15:37 */     return strbuf.toString();
/* 16:   */   }
/* 17:   */   
/* 18:   */   public static String asString(byte[] bytes)
/* 19:   */   {
/* 20:42 */     StringBuffer buffer = new StringBuffer();
/* 21:43 */     for (int i = 0; i < bytes.length; i++) {
/* 22:44 */       buffer.append((char)bytes[i]);
/* 23:   */     }
/* 24:45 */     return buffer.toString();
/* 25:   */   }
/* 26:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.crypto.AKey
 * JD-Core Version:    0.7.0.1
 */
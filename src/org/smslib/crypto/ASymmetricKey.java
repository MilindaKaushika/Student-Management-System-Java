/*  1:   */ package org.smslib.crypto;
/*  2:   */ 
/*  3:   */ import java.security.InvalidKeyException;
/*  4:   */ import java.security.NoSuchAlgorithmException;
/*  5:   */ import javax.crypto.BadPaddingException;
/*  6:   */ import javax.crypto.IllegalBlockSizeException;
/*  7:   */ import javax.crypto.NoSuchPaddingException;
/*  8:   */ import javax.crypto.spec.SecretKeySpec;
/*  9:   */ 
/* 10:   */ public abstract class ASymmetricKey
/* 11:   */   extends AKey
/* 12:   */ {
/* 13:   */   private SecretKeySpec key;
/* 14:   */   
/* 15:   */   public SecretKeySpec getKey()
/* 16:   */   {
/* 17:45 */     return this.key;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public void setKey(SecretKeySpec key)
/* 21:   */   {
/* 22:56 */     this.key = key;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public abstract SecretKeySpec generateKey()
/* 26:   */     throws NoSuchAlgorithmException;
/* 27:   */   
/* 28:   */   public abstract byte[] encrypt(byte[] paramArrayOfByte)
/* 29:   */     throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException;
/* 30:   */   
/* 31:   */   public abstract byte[] decrypt(byte[] paramArrayOfByte)
/* 32:   */     throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException;
/* 33:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.crypto.ASymmetricKey
 * JD-Core Version:    0.7.0.1
 */
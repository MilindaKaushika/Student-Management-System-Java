/*  1:   */ package org.smslib.crypto;
/*  2:   */ 
/*  3:   */ import java.io.PrintStream;
/*  4:   */ import java.security.InvalidKeyException;
/*  5:   */ import java.security.NoSuchAlgorithmException;
/*  6:   */ import javax.crypto.BadPaddingException;
/*  7:   */ import javax.crypto.Cipher;
/*  8:   */ import javax.crypto.IllegalBlockSizeException;
/*  9:   */ import javax.crypto.KeyGenerator;
/* 10:   */ import javax.crypto.NoSuchPaddingException;
/* 11:   */ import javax.crypto.SecretKey;
/* 12:   */ import javax.crypto.spec.SecretKeySpec;
/* 13:   */ 
/* 14:   */ public class AESKey
/* 15:   */   extends ASymmetricKey
/* 16:   */ {
/* 17:   */   public AESKey()
/* 18:   */     throws NoSuchAlgorithmException
/* 19:   */   {
/* 20:41 */     setKey(generateKey());
/* 21:   */   }
/* 22:   */   
/* 23:   */   public AESKey(SecretKeySpec key)
/* 24:   */   {
/* 25:46 */     setKey(key);
/* 26:   */   }
/* 27:   */   
/* 28:   */   public SecretKeySpec generateKey()
/* 29:   */     throws NoSuchAlgorithmException
/* 30:   */   {
/* 31:52 */     KeyGenerator keyGen = KeyGenerator.getInstance("AES");
/* 32:53 */     keyGen.init(128);
/* 33:54 */     SecretKey secretKey = keyGen.generateKey();
/* 34:55 */     byte[] raw = secretKey.getEncoded();
/* 35:56 */     return new SecretKeySpec(raw, "AES");
/* 36:   */   }
/* 37:   */   
/* 38:   */   public byte[] encrypt(byte[] message)
/* 39:   */     throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException
/* 40:   */   {
/* 41:62 */     Cipher cipher = Cipher.getInstance("AES");
/* 42:63 */     cipher.init(1, getKey());
/* 43:64 */     return cipher.doFinal(message);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public byte[] decrypt(byte[] message)
/* 47:   */     throws NoSuchAlgorithmException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, InvalidKeyException
/* 48:   */   {
/* 49:70 */     Cipher cipher = Cipher.getInstance("AES");
/* 50:71 */     cipher.init(2, getKey());
/* 51:72 */     return cipher.doFinal(message);
/* 52:   */   }
/* 53:   */   
/* 54:   */   public static void main(String[] args)
/* 55:   */   {
/* 56:   */     try
/* 57:   */     {
/* 58:79 */       AESKey k = new AESKey();
/* 59:80 */       k.setKey(k.generateKey());
/* 60:   */       
/* 61:82 */       String message = "Hello from Thanasis :)";
/* 62:83 */       System.out.println(">>> " + message);
/* 63:84 */       byte[] enc = k.encrypt(message.getBytes());
/* 64:85 */       byte[] dec = k.decrypt(enc);
/* 65:86 */       System.out.println(">>> " + asString(dec));
/* 66:   */     }
/* 67:   */     catch (Exception e)
/* 68:   */     {
/* 69:90 */       e.printStackTrace();
/* 70:   */     }
/* 71:   */   }
/* 72:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.crypto.AESKey
 * JD-Core Version:    0.7.0.1
 */
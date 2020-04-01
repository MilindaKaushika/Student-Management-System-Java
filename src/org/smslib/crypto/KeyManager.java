/*   1:    */ package org.smslib.crypto;
/*   2:    */ 
/*   3:    */ import java.security.InvalidKeyException;
/*   4:    */ import java.security.NoSuchAlgorithmException;
/*   5:    */ import java.util.HashMap;
/*   6:    */ import javax.crypto.BadPaddingException;
/*   7:    */ import javax.crypto.IllegalBlockSizeException;
/*   8:    */ import javax.crypto.NoSuchPaddingException;
/*   9:    */ import org.smslib.SMSLibException;
/*  10:    */ 
/*  11:    */ public class KeyManager
/*  12:    */ {
/*  13: 37 */   private static KeyManager _instance = null;
/*  14:    */   HashMap<String, AKey> keys;
/*  15:    */   
/*  16:    */   private KeyManager()
/*  17:    */   {
/*  18: 43 */     this.keys = new HashMap();
/*  19:    */   }
/*  20:    */   
/*  21:    */   public static KeyManager getInstance()
/*  22:    */   {
/*  23: 48 */     if (_instance == null) {
/*  24: 48 */       _instance = new KeyManager();
/*  25:    */     }
/*  26: 49 */     return _instance;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void registerKey(String mobileNumber, AKey key)
/*  30:    */   {
/*  31: 66 */     this.keys.put(mobileNumber.charAt(0) == '+' ? mobileNumber.substring(1) : mobileNumber, key);
/*  32:    */   }
/*  33:    */   
/*  34:    */   public AKey unregisterKey(String mobileNumber)
/*  35:    */   {
/*  36: 82 */     return (AKey)this.keys.remove(mobileNumber.charAt(0) == '+' ? mobileNumber.substring(1) : mobileNumber);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void unregisterAllKeys()
/*  40:    */   {
/*  41: 90 */     this.keys.clear();
/*  42:    */   }
/*  43:    */   
/*  44:    */   public AKey getKey(String mobileNumber)
/*  45:    */   {
/*  46:103 */     return (AKey)this.keys.get(mobileNumber.charAt(0) == '+' ? mobileNumber.substring(1) : mobileNumber);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public byte[] encrypt(String mobileNumber, byte[] message)
/*  50:    */     throws SMSLibException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException
/*  51:    */   {
/*  52:125 */     AKey k = getKey(mobileNumber);
/*  53:126 */     if (k == null) {
/*  54:126 */       throw new SMSLibException("Could not find Encryption Key for the specific number.");
/*  55:    */     }
/*  56:127 */     if ((k instanceof ASymmetricKey)) {
/*  57:127 */       return ((ASymmetricKey)k).encrypt(message);
/*  58:    */     }
/*  59:128 */     return new byte[0];
/*  60:    */   }
/*  61:    */   
/*  62:    */   public byte[] decrypt(String mobileNumber, byte[] message)
/*  63:    */     throws SMSLibException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException
/*  64:    */   {
/*  65:149 */     AKey k = getKey(mobileNumber);
/*  66:150 */     if (k == null) {
/*  67:150 */       throw new SMSLibException("Could not find Encryption Key for the specific number.");
/*  68:    */     }
/*  69:151 */     if ((k instanceof ASymmetricKey)) {
/*  70:151 */       return ((ASymmetricKey)k).decrypt(message);
/*  71:    */     }
/*  72:152 */     return new byte[0];
/*  73:    */   }
/*  74:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.crypto.KeyManager
 * JD-Core Version:    0.7.0.1
 */
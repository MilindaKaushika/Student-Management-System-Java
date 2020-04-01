/*  1:   */ package org.smslib;
/*  2:   */ 
/*  3:   */ import java.security.InvalidKeyException;
/*  4:   */ import java.security.NoSuchAlgorithmException;
/*  5:   */ import javax.crypto.BadPaddingException;
/*  6:   */ import javax.crypto.IllegalBlockSizeException;
/*  7:   */ import javax.crypto.NoSuchPaddingException;
/*  8:   */ import org.ajwcc.pduUtils.gsm3040.SmsDeliveryPdu;
/*  9:   */ import org.smslib.crypto.AKey;
/* 10:   */ import org.smslib.crypto.KeyManager;
/* 11:   */ 
/* 12:   */ public class InboundEncryptedMessage
/* 13:   */   extends InboundBinaryMessage
/* 14:   */ {
/* 15:   */   private static final long serialVersionUID = 2L;
/* 16:   */   
/* 17:   */   public InboundEncryptedMessage(SmsDeliveryPdu pdu, int memIndex, String memLocation)
/* 18:   */   {
/* 19:41 */     super(pdu, memIndex, memLocation);
/* 20:   */   }
/* 21:   */   
/* 22:   */   public String getDecryptedText()
/* 23:   */     throws SMSLibException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException
/* 24:   */   {
/* 25:46 */     KeyManager km = KeyManager.getInstance();
/* 26:47 */     if (km.getKey(getOriginator()) != null) {
/* 27:47 */       setDataBytes(km.decrypt(getOriginator(), getDataBytes()));
/* 28:   */     } else {
/* 29:48 */       throw new SMSLibException("Message is not encrypted, have you defined the key in KeyManager?");
/* 30:   */     }
/* 31:49 */     return AKey.asString(getDataBytes());
/* 32:   */   }
/* 33:   */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.InboundEncryptedMessage
 * JD-Core Version:    0.7.0.1
 */
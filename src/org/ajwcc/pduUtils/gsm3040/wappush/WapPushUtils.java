/*   1:    */ package org.ajwcc.pduUtils.wappush;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.List;
/*   6:    */ 
/*   7:    */ public class WapPushUtils
/*   8:    */ {
/*   9:    */   public static final int WBXML_VERSION_1_2 = 2;
/*  10:    */   public static final int WBXML_SI_1_0_PUBLIC_IDENTIFIER = 5;
/*  11:    */   public static final int WBXML_SL_1_0_PUBLIC_IDENTIFIER = 6;
/*  12:    */   public static final int WBXML_CHARSET_UTF8 = 106;
/*  13:    */   public static final int WBXML_CLOSE_TAG = 1;
/*  14:    */   public static final int WBXML_OPAQUE_DATA = 195;
/*  15:    */   public static final int WBXML_STRING_START = 3;
/*  16:    */   public static final int WBXML_STRING_END = 0;
/*  17:    */   public static final int WBXML_SL_TAG_CONTENT_NO_ATTRIBUTES = 133;
/*  18:    */   public static final int WBXML_SI_TAG_CONTENT_NO_ATTRIBUTES = 69;
/*  19:    */   public static final int WBXML_INDICATION_TAG_CONTENT_AND_ATTRIBUTES = 198;
/*  20: 59 */   private static final List<String> WBXML_PROTOCOLS = new ArrayList();
/*  21: 61 */   private static final HashMap<String, Integer> WBXML_PROTOCOL_BYTES = new HashMap();
/*  22: 63 */   private static final List<String> WBXML_DOMAINS = new ArrayList();
/*  23: 65 */   private static final HashMap<String, Integer> WBXML_DOMAIN_BYTES = new HashMap();
/*  24:    */   public static final int WBXML_HREF_UNKNOWN = 11;
/*  25:    */   public static final int WBXML_HREF_HTTP = 12;
/*  26:    */   public static final int WBXML_HREF_HTTP_WWW = 13;
/*  27:    */   public static final int WBXML_HREF_HTTPS = 14;
/*  28:    */   public static final int WBXML_HREF_HTTPS_WWW = 15;
/*  29:    */   public static final int WBXML_DOMAIN_COM = 133;
/*  30:    */   public static final int WBXML_DOMAIN_EDU = 134;
/*  31:    */   public static final int WBXML_DOMAIN_NET = 135;
/*  32:    */   public static final int WBXML_DOMAIN_ORG = 136;
/*  33:    */   public static final int PUSH_CREATED = 10;
/*  34:    */   public static final int PUSH_EXPIRES = 16;
/*  35:    */   public static final int PUSH_SI_ID = 17;
/*  36:    */   public static final int PUSH_CLASS = 18;
/*  37:    */   public static final int PUSH_SIGNAL_NONE = 5;
/*  38:    */   public static final int PUSH_SIGNAL_LOW = 6;
/*  39:    */   public static final int PUSH_SIGNAL_MEDIUM = 7;
/*  40:    */   public static final int PUSH_SIGNAL_HIGH = 8;
/*  41:    */   public static final int PUSH_SIGNAL_DELETE = 9;
/*  42:    */   
/*  43:    */   static
/*  44:    */   {
/*  45: 88 */     WBXML_PROTOCOLS.add("http://www.");
/*  46: 89 */     WBXML_PROTOCOLS.add("http://");
/*  47: 90 */     WBXML_PROTOCOLS.add("https://www.");
/*  48: 91 */     WBXML_PROTOCOLS.add("https://");
/*  49: 92 */     WBXML_PROTOCOL_BYTES.put("http://www.", Integer.valueOf(13));
/*  50: 93 */     WBXML_PROTOCOL_BYTES.put("http://", Integer.valueOf(12));
/*  51: 94 */     WBXML_PROTOCOL_BYTES.put("https://www.", Integer.valueOf(15));
/*  52: 95 */     WBXML_PROTOCOL_BYTES.put("https://", Integer.valueOf(14));
/*  53: 96 */     WBXML_DOMAINS.add(".com/");
/*  54: 97 */     WBXML_DOMAINS.add(".edu/");
/*  55: 98 */     WBXML_DOMAINS.add(".net/");
/*  56: 99 */     WBXML_DOMAINS.add(".org/");
/*  57:100 */     WBXML_DOMAIN_BYTES.put(".com/", Integer.valueOf(133));
/*  58:101 */     WBXML_DOMAIN_BYTES.put(".edu/", Integer.valueOf(134));
/*  59:102 */     WBXML_DOMAIN_BYTES.put(".net/", Integer.valueOf(135));
/*  60:103 */     WBXML_DOMAIN_BYTES.put(".org/", Integer.valueOf(136));
/*  61:    */   }
/*  62:    */   
/*  63:    */   public static List<String> getProtocols()
/*  64:    */   {
/*  65:108 */     return WBXML_PROTOCOLS;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public static List<String> getDomains()
/*  69:    */   {
/*  70:113 */     return WBXML_DOMAINS;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public static int getProtocolByteFor(String protocol)
/*  74:    */   {
/*  75:118 */     return ((Integer)WBXML_PROTOCOL_BYTES.get(protocol)).intValue();
/*  76:    */   }
/*  77:    */   
/*  78:    */   public static int getDomainByteFor(String domain)
/*  79:    */   {
/*  80:123 */     return ((Integer)WBXML_DOMAIN_BYTES.get(domain)).intValue();
/*  81:    */   }
/*  82:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.ajwcc.pduUtils.wappush.WapPushUtils
 * JD-Core Version:    0.7.0.1
 */
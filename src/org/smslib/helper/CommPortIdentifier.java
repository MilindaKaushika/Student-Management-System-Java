/*   1:    */ package org.smslib.helper;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Field;
/*   4:    */ import java.lang.reflect.InvocationTargetException;
/*   5:    */ import java.lang.reflect.Method;
/*   6:    */ import java.util.Enumeration;
/*   7:    */ import java.util.Vector;
/*   8:    */ 
/*   9:    */ public class CommPortIdentifier
/*  10:    */ {
/*  11:    */   private static Class<?> classCommPortIdentifier;
/*  12:    */   public static final int PORT_SERIAL;
/*  13:    */   private Object realObject;
/*  14:    */   
/*  15:    */   static
/*  16:    */   {
/*  17:    */     try
/*  18:    */     {
/*  19: 66 */       classCommPortIdentifier = Class.forName("javax.comm.CommPortIdentifier");
/*  20:    */     }
/*  21:    */     catch (ClassNotFoundException e1)
/*  22:    */     {
/*  23:    */       try
/*  24:    */       {
/*  25: 72 */         classCommPortIdentifier = Class.forName("gnu.io.CommPortIdentifier");
/*  26:    */       }
/*  27:    */       catch (ClassNotFoundException e2)
/*  28:    */       {
/*  29: 76 */         throw new RuntimeException("CommPortIdentifier class not found");
/*  30:    */       }
/*  31:    */     }
/*  32:    */     try
/*  33:    */     {
/*  34: 82 */       Field f = classCommPortIdentifier.getField("PORT_SERIAL");
/*  35: 83 */       PORT_SERIAL = f.getInt(null);
/*  36:    */     }
/*  37:    */     catch (Exception e)
/*  38:    */     {
/*  39: 87 */       throw new RuntimeException(e);
/*  40:    */     }
/*  41:    */   }
/*  42:    */   
/*  43:    */   protected CommPortIdentifier(Object myRealObject)
/*  44:    */   {
/*  45: 95 */     this.realObject = myRealObject;
/*  46:    */   }
/*  47:    */   
/*  48:    */   public int getPortType()
/*  49:    */   {
/*  50:    */     try
/*  51:    */     {
/*  52:107 */       Method method = classCommPortIdentifier.getMethod("getPortType", (Class[])null);
/*  53:108 */       return ((Integer)method.invoke(this.realObject, new Object[0])).intValue();
/*  54:    */     }
/*  55:    */     catch (InvocationTargetException e)
/*  56:    */     {
/*  57:112 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/*  58:    */     }
/*  59:    */     catch (Exception e)
/*  60:    */     {
/*  61:116 */       throw new RuntimeException(e);
/*  62:    */     }
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getName()
/*  66:    */   {
/*  67:    */     try
/*  68:    */     {
/*  69:129 */       Method method = classCommPortIdentifier.getMethod("getName", (Class[])null);
/*  70:130 */       return (String)method.invoke(this.realObject, new Object[0]);
/*  71:    */     }
/*  72:    */     catch (InvocationTargetException e)
/*  73:    */     {
/*  74:134 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/*  75:    */     }
/*  76:    */     catch (Exception e)
/*  77:    */     {
/*  78:138 */       throw new RuntimeException(e);
/*  79:    */     }
/*  80:    */   }
/*  81:    */   
/*  82:    */   public SerialPort open(String appname, int timeout)
/*  83:    */   {
/*  84:145 */     Class<?>[] paramTypes = { String.class, Integer.TYPE };
/*  85:    */     try
/*  86:    */     {
/*  87:148 */       Method method = classCommPortIdentifier.getMethod("open", paramTypes);
/*  88:149 */       return new SerialPort(method.invoke(this.realObject, new Object[] { appname, Integer.valueOf(timeout) }));
/*  89:    */     }
/*  90:    */     catch (InvocationTargetException e)
/*  91:    */     {
/*  92:153 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/*  93:    */     }
/*  94:    */     catch (Exception e)
/*  95:    */     {
/*  96:157 */       throw new RuntimeException(e);
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public static Enumeration<CommPortIdentifier> getPortIdentifiers()
/* 101:    */   {
/* 102:170 */     if (classCommPortIdentifier == null) {
/* 103:170 */       throw new RuntimeException("CommPortIdentifier class not found");
/* 104:    */     }
/* 105:    */     Enumeration<CommPortIdentifier> list;
/* 106:    */     try
/* 107:    */     {
/* 108:175 */       Method method = classCommPortIdentifier.getMethod("getPortIdentifiers", (Class[])null);
/* 109:176 */       CommPortIdentifier type = null;
/* 110:177 */       list = ReflectionHelper.invokeAndCastEnumeration(type, method, null, new Object[0]);
/* 111:    */     }
/* 112:    */     catch (Exception e)
/* 113:    */     {
/* 114:181 */       throw new RuntimeException(e);
/* 115:    */     }
/* 116:184 */     Vector<CommPortIdentifier> vec = new Vector();
/* 117:185 */     while (list.hasMoreElements()) {
/* 118:186 */       vec.add(new CommPortIdentifier(list.nextElement()));
/* 119:    */     }
/* 120:187 */     return vec.elements();
/* 121:    */   }
/* 122:    */   
/* 123:    */   public static CommPortIdentifier getPortIdentifier(String portName)
/* 124:    */   {
/* 125:202 */     if (classCommPortIdentifier == null) {
/* 126:202 */       throw new RuntimeException("CommPortIdentifier class not found");
/* 127:    */     }
/* 128:    */     CommPortIdentifier port;
/* 129:    */     try
/* 130:    */     {
/* 131:207 */       Method method = classCommPortIdentifier.getMethod("getPortIdentifier", new Class[] { String.class });
/* 132:208 */       port = new CommPortIdentifier(method.invoke(null, new Object[] { portName }));
/* 133:    */     }
/* 134:    */     catch (InvocationTargetException e)
/* 135:    */     {
/* 136:212 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 137:    */     }
/* 138:    */     catch (Exception e)
/* 139:    */     {
/* 140:216 */       throw new RuntimeException(e);
/* 141:    */     }
/* 142:218 */     return port;
/* 143:    */   }
/* 144:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.helper.CommPortIdentifier
 * JD-Core Version:    0.7.0.1
 */
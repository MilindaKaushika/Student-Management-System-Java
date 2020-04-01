/*   1:    */ package org.smslib.helper;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.lang.reflect.Field;
/*   5:    */ import java.lang.reflect.InvocationTargetException;
/*   6:    */ import java.lang.reflect.Method;
/*   7:    */ 
/*   8:    */ public class SerialPortEvent
/*   9:    */ {
/*  10:    */   private static Class<?> classSerialPortEvent;
/*  11:    */   public static final int BI;
/*  12:    */   public static final int CD;
/*  13:    */   public static final int CTS;
/*  14:    */   public static final int DATA_AVAILABLE;
/*  15:    */   public static final int DSR;
/*  16:    */   public static final int FE;
/*  17:    */   public static final int OE;
/*  18:    */   public static final int OUTPUT_BUFFER_EMPTY;
/*  19:    */   public static final int PE;
/*  20:    */   public static final int RI;
/*  21:    */   private Object realObject;
/*  22:    */   
/*  23:    */   static
/*  24:    */   {
/*  25:    */     try
/*  26:    */     {
/*  27: 57 */       classSerialPortEvent = Class.forName("javax.comm.SerialPortEvent");
/*  28:    */     }
/*  29:    */     catch (ClassNotFoundException e1)
/*  30:    */     {
/*  31:    */       try
/*  32:    */       {
/*  33: 63 */         classSerialPortEvent = Class.forName("gnu.io.SerialPortEvent");
/*  34:    */       }
/*  35:    */       catch (ClassNotFoundException e2)
/*  36:    */       {
/*  37: 67 */         throw new RuntimeException("SerialPortEvent class not found");
/*  38:    */       }
/*  39:    */     }
/*  40:    */     try
/*  41:    */     {
/*  42: 74 */       Field f = classSerialPortEvent.getField("BI");
/*  43: 75 */       BI = f.getInt(null);
/*  44: 76 */       f = classSerialPortEvent.getField("CD");
/*  45: 77 */       CD = f.getInt(null);
/*  46: 78 */       f = classSerialPortEvent.getField("CTS");
/*  47: 79 */       CTS = f.getInt(null);
/*  48: 80 */       f = classSerialPortEvent.getField("DATA_AVAILABLE");
/*  49: 81 */       DATA_AVAILABLE = f.getInt(null);
/*  50: 82 */       f = classSerialPortEvent.getField("DSR");
/*  51: 83 */       DSR = f.getInt(null);
/*  52: 84 */       f = classSerialPortEvent.getField("FE");
/*  53: 85 */       FE = f.getInt(null);
/*  54: 86 */       f = classSerialPortEvent.getField("OE");
/*  55: 87 */       OE = f.getInt(null);
/*  56: 88 */       f = classSerialPortEvent.getField("OUTPUT_BUFFER_EMPTY");
/*  57: 89 */       OUTPUT_BUFFER_EMPTY = f.getInt(null);
/*  58: 90 */       f = classSerialPortEvent.getField("PE");
/*  59: 91 */       PE = f.getInt(null);
/*  60: 92 */       f = classSerialPortEvent.getField("RI");
/*  61: 93 */       RI = f.getInt(null);
/*  62:    */     }
/*  63:    */     catch (Exception e)
/*  64:    */     {
/*  65: 97 */       throw new RuntimeException(e);
/*  66:    */     }
/*  67:    */   }
/*  68:    */   
/*  69:    */   public SerialPortEvent(SerialPort srcport, int eventtype, boolean oldvalue, boolean newvalue)
/*  70:    */   {
/*  71:119 */     if (classSerialPortEvent == null) {
/*  72:119 */       throw new RuntimeException("SerialPortEvent class not found");
/*  73:    */     }
/*  74:    */     try
/*  75:    */     {
/*  76:122 */       Constructor<?> constr = classSerialPortEvent.getConstructor(new Class[] { SerialPort.class, Integer.TYPE, Boolean.TYPE, Boolean.TYPE });
/*  77:123 */       this.realObject = constr.newInstance(new Object[] { srcport, Integer.valueOf(eventtype), Boolean.valueOf(oldvalue), Boolean.valueOf(newvalue) });
/*  78:    */     }
/*  79:    */     catch (Exception e)
/*  80:    */     {
/*  81:127 */       throw new RuntimeException(e);
/*  82:    */     }
/*  83:    */   }
/*  84:    */   
/*  85:    */   SerialPortEvent(Object obj)
/*  86:    */   {
/*  87:133 */     if (classSerialPortEvent == null) {
/*  88:133 */       throw new RuntimeException("SerialPortEvent class not found");
/*  89:    */     }
/*  90:134 */     this.realObject = obj;
/*  91:    */   }
/*  92:    */   
/*  93:    */   public int getEventType()
/*  94:    */   {
/*  95:    */     int eventType;
/*  96:    */     try
/*  97:    */     {
/*  98:151 */       Method method = classSerialPortEvent.getMethod("getEventType", (Class[])null);
/*  99:152 */       eventType = ((Integer)method.invoke(this.realObject, new Object[0])).intValue();
/* 100:    */     }
/* 101:    */     catch (InvocationTargetException e)
/* 102:    */     {
/* 103:156 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 104:    */     }
/* 105:    */     catch (Exception e)
/* 106:    */     {
/* 107:160 */       throw new RuntimeException(e);
/* 108:    */     }
/* 109:162 */     return eventType;
/* 110:    */   }
/* 111:    */   
/* 112:    */   public boolean getNewValue()
/* 113:    */   {
/* 114:    */     boolean newValue;
/* 115:    */     try
/* 116:    */     {
/* 117:175 */       Method method = classSerialPortEvent.getMethod("getNewValue", (Class[])null);
/* 118:176 */       newValue = ((Boolean)method.invoke(this.realObject, new Object[0])).booleanValue();
/* 119:    */     }
/* 120:    */     catch (InvocationTargetException e)
/* 121:    */     {
/* 122:180 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 123:    */     }
/* 124:    */     catch (Exception e)
/* 125:    */     {
/* 126:184 */       throw new RuntimeException(e);
/* 127:    */     }
/* 128:186 */     return newValue;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public boolean getOldValue()
/* 132:    */   {
/* 133:    */     boolean oldValue;
/* 134:    */     try
/* 135:    */     {
/* 136:199 */       Method method = classSerialPortEvent.getMethod("getOldValue", (Class[])null);
/* 137:200 */       oldValue = ((Boolean)method.invoke(this.realObject, new Object[0])).booleanValue();
/* 138:    */     }
/* 139:    */     catch (InvocationTargetException e)
/* 140:    */     {
/* 141:204 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 142:    */     }
/* 143:    */     catch (Exception e)
/* 144:    */     {
/* 145:208 */       throw new RuntimeException(e);
/* 146:    */     }
/* 147:210 */     return oldValue;
/* 148:    */   }
/* 149:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.helper.SerialPortEvent
 * JD-Core Version:    0.7.0.1
 */
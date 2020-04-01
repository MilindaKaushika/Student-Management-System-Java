/*   1:    */ package org.smslib.helper;
/*   2:    */ 
/*   3:    */ import java.io.InputStream;
/*   4:    */ import java.io.OutputStream;
/*   5:    */ import java.lang.reflect.Constructor;
/*   6:    */ import java.lang.reflect.Field;
/*   7:    */ import java.lang.reflect.InvocationHandler;
/*   8:    */ import java.lang.reflect.InvocationTargetException;
/*   9:    */ import java.lang.reflect.Method;
/*  10:    */ import java.lang.reflect.Proxy;
/*  11:    */ 
/*  12:    */ public class SerialPort
/*  13:    */ {
/*  14:    */   private static Class<?> classSerialPort;
/*  15:    */   public static final int FLOWCONTROL_RTSCTS_IN;
/*  16:    */   public static final int DATABITS_8;
/*  17:    */   public static final int STOPBITS_1;
/*  18:    */   public static final int PARITY_NONE;
/*  19:    */   public static final int FLOWCONTROL_RTSCTS_OUT;
/*  20:    */   private Object realObject;
/*  21:    */   
/*  22:    */   private class SerialPortEventListenerHandler
/*  23:    */     implements InvocationHandler
/*  24:    */   {
/*  25:    */     private SerialPortEventListener realListenerObject;
/*  26:    */     
/*  27:    */     public SerialPortEventListenerHandler(SerialPortEventListener realListenerObj)
/*  28:    */     {
/*  29: 56 */       this.realListenerObject = realListenerObj;
/*  30:    */     }
/*  31:    */     
/*  32:    */     public Object invoke(Object proxy, Method method, Object[] args)
/*  33:    */       throws Throwable
/*  34:    */     {
/*  35: 65 */       this.realListenerObject.serialEvent(new SerialPortEvent(args[0]));
/*  36:    */       
/*  37: 67 */       return null;
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   static
/*  42:    */   {
/*  43:    */     try
/*  44:    */     {
/*  45: 91 */       classSerialPort = Class.forName("javax.comm.SerialPort");
/*  46:    */     }
/*  47:    */     catch (ClassNotFoundException e1)
/*  48:    */     {
/*  49:    */       try
/*  50:    */       {
/*  51: 97 */         classSerialPort = Class.forName("gnu.io.SerialPort");
/*  52:    */       }
/*  53:    */       catch (ClassNotFoundException e2)
/*  54:    */       {
/*  55:101 */         throw new RuntimeException("SerialPort class not found");
/*  56:    */       }
/*  57:    */     }
/*  58:    */     try
/*  59:    */     {
/*  60:108 */       Field f = classSerialPort.getField("FLOWCONTROL_RTSCTS_IN");
/*  61:109 */       FLOWCONTROL_RTSCTS_IN = f.getInt(null);
/*  62:110 */       f = classSerialPort.getField("DATABITS_8");
/*  63:111 */       DATABITS_8 = f.getInt(null);
/*  64:112 */       f = classSerialPort.getField("STOPBITS_1");
/*  65:113 */       STOPBITS_1 = f.getInt(null);
/*  66:114 */       f = classSerialPort.getField("PARITY_NONE");
/*  67:115 */       PARITY_NONE = f.getInt(null);
/*  68:116 */       f = classSerialPort.getField("FLOWCONTROL_RTSCTS_OUT");
/*  69:117 */       FLOWCONTROL_RTSCTS_OUT = f.getInt(null);
/*  70:    */     }
/*  71:    */     catch (Exception e)
/*  72:    */     {
/*  73:121 */       throw new RuntimeException(e);
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected SerialPort(Object myRealObject)
/*  78:    */   {
/*  79:129 */     this.realObject = myRealObject;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void addEventListener(SerialPortEventListener lsnr)
/*  83:    */   {
/*  84:    */     try
/*  85:    */     {
/*  86:160 */       Method method = ReflectionHelper.getMethodOnlyByName(classSerialPort, "addEventListener");
/*  87:    */       
/*  88:162 */       Class<?> eventI = method.getParameterTypes()[0];
/*  89:163 */       InvocationHandler handler = new SerialPortEventListenerHandler(lsnr);
/*  90:    */       
/*  91:165 */       Class<?> proxyClass = Proxy.getProxyClass(eventI.getClassLoader(), new Class[] { eventI });
/*  92:    */       
/*  93:167 */       method.invoke(this.realObject, new Object[] { proxyClass.getConstructor(new Class[] { InvocationHandler.class }).newInstance(new Object[] { handler }) });
/*  94:    */     }
/*  95:    */     catch (InvocationTargetException e)
/*  96:    */     {
/*  97:171 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/*  98:    */     }
/*  99:    */     catch (Exception e)
/* 100:    */     {
/* 101:175 */       throw new RuntimeException(e);
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public void close()
/* 106:    */   {
/* 107:    */     try
/* 108:    */     {
/* 109:189 */       Method method = classSerialPort.getMethod("close", (Class[])null);
/* 110:190 */       method.invoke(this.realObject, new Object[0]);
/* 111:    */     }
/* 112:    */     catch (InvocationTargetException e)
/* 113:    */     {
/* 114:194 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 115:    */     }
/* 116:    */     catch (Exception e)
/* 117:    */     {
/* 118:198 */       throw new RuntimeException(e);
/* 119:    */     }
/* 120:    */   }
/* 121:    */   
/* 122:    */   public void enableReceiveThreshold(int i)
/* 123:    */   {
/* 124:    */     try
/* 125:    */     {
/* 126:227 */       Method method = classSerialPort.getMethod("enableReceiveThreshold", new Class[] { Integer.TYPE });
/* 127:228 */       method.invoke(this.realObject, new Object[] { Integer.valueOf(i) });
/* 128:    */     }
/* 129:    */     catch (InvocationTargetException e)
/* 130:    */     {
/* 131:232 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 132:    */     }
/* 133:    */     catch (Exception e)
/* 134:    */     {
/* 135:236 */       throw new RuntimeException(e);
/* 136:    */     }
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void enableReceiveTimeout(int rcvTimeout)
/* 140:    */   {
/* 141:265 */     Class<?>[] paramTypes = { Integer.TYPE };
/* 142:    */     try
/* 143:    */     {
/* 144:268 */       Method method = classSerialPort.getMethod("enableReceiveTimeout", paramTypes);
/* 145:269 */       method.invoke(this.realObject, new Object[] { Integer.valueOf(rcvTimeout) });
/* 146:    */     }
/* 147:    */     catch (InvocationTargetException e)
/* 148:    */     {
/* 149:273 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 150:    */     }
/* 151:    */     catch (Exception e)
/* 152:    */     {
/* 153:277 */       throw new RuntimeException(e);
/* 154:    */     }
/* 155:    */   }
/* 156:    */   
/* 157:    */   public InputStream getInputStream()
/* 158:    */   {
/* 159:    */     try
/* 160:    */     {
/* 161:352 */       Method method = classSerialPort.getMethod("getInputStream", (Class[])null);
/* 162:353 */       return (InputStream)method.invoke(this.realObject, new Object[0]);
/* 163:    */     }
/* 164:    */     catch (InvocationTargetException e)
/* 165:    */     {
/* 166:357 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 167:    */     }
/* 168:    */     catch (Exception e)
/* 169:    */     {
/* 170:361 */       throw new RuntimeException(e);
/* 171:    */     }
/* 172:    */   }
/* 173:    */   
/* 174:    */   public OutputStream getOutputStream()
/* 175:    */   {
/* 176:    */     try
/* 177:    */     {
/* 178:376 */       Method method = classSerialPort.getMethod("getOutputStream", (Class[])null);
/* 179:377 */       return (OutputStream)method.invoke(this.realObject, new Object[0]);
/* 180:    */     }
/* 181:    */     catch (InvocationTargetException e)
/* 182:    */     {
/* 183:381 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 184:    */     }
/* 185:    */     catch (Exception e)
/* 186:    */     {
/* 187:385 */       throw new RuntimeException(e);
/* 188:    */     }
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void notifyOnBreakInterrupt(boolean b)
/* 192:    */   {
/* 193:    */     try
/* 194:    */     {
/* 195:403 */       Method method = classSerialPort.getMethod("notifyOnBreakInterrupt", new Class[] { Boolean.TYPE });
/* 196:404 */       method.invoke(this.realObject, new Object[] { Boolean.valueOf(b) });
/* 197:    */     }
/* 198:    */     catch (InvocationTargetException e)
/* 199:    */     {
/* 200:408 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 201:    */     }
/* 202:    */     catch (Exception e)
/* 203:    */     {
/* 204:412 */       throw new RuntimeException(e);
/* 205:    */     }
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void notifyOnDataAvailable(boolean b)
/* 209:    */   {
/* 210:    */     try
/* 211:    */     {
/* 212:433 */       Method method = classSerialPort.getMethod("notifyOnDataAvailable", new Class[] { Boolean.TYPE });
/* 213:434 */       method.invoke(this.realObject, new Object[] { Boolean.valueOf(b) });
/* 214:    */     }
/* 215:    */     catch (InvocationTargetException e)
/* 216:    */     {
/* 217:438 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 218:    */     }
/* 219:    */     catch (Exception e)
/* 220:    */     {
/* 221:442 */       throw new RuntimeException(e);
/* 222:    */     }
/* 223:    */   }
/* 224:    */   
/* 225:    */   public void notifyOnFramingError(boolean b)
/* 226:    */   {
/* 227:    */     try
/* 228:    */     {
/* 229:460 */       Method method = classSerialPort.getMethod("notifyOnFramingError", new Class[] { Boolean.TYPE });
/* 230:461 */       method.invoke(this.realObject, new Object[] { Boolean.valueOf(b) });
/* 231:    */     }
/* 232:    */     catch (InvocationTargetException e)
/* 233:    */     {
/* 234:465 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 235:    */     }
/* 236:    */     catch (Exception e)
/* 237:    */     {
/* 238:469 */       throw new RuntimeException(e);
/* 239:    */     }
/* 240:    */   }
/* 241:    */   
/* 242:    */   public void notifyOnOutputEmpty(boolean b)
/* 243:    */   {
/* 244:    */     try
/* 245:    */     {
/* 246:490 */       Method method = classSerialPort.getMethod("notifyOnOutputEmpty", new Class[] { Boolean.TYPE });
/* 247:491 */       method.invoke(this.realObject, new Object[] { Boolean.valueOf(b) });
/* 248:    */     }
/* 249:    */     catch (InvocationTargetException e)
/* 250:    */     {
/* 251:495 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 252:    */     }
/* 253:    */     catch (Exception e)
/* 254:    */     {
/* 255:499 */       throw new RuntimeException(e);
/* 256:    */     }
/* 257:    */   }
/* 258:    */   
/* 259:    */   public void notifyOnOverrunError(boolean b)
/* 260:    */   {
/* 261:    */     try
/* 262:    */     {
/* 263:517 */       Method method = classSerialPort.getMethod("notifyOnOverrunError", new Class[] { Boolean.TYPE });
/* 264:518 */       method.invoke(this.realObject, new Object[] { Boolean.valueOf(b) });
/* 265:    */     }
/* 266:    */     catch (InvocationTargetException e)
/* 267:    */     {
/* 268:522 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 269:    */     }
/* 270:    */     catch (Exception e)
/* 271:    */     {
/* 272:526 */       throw new RuntimeException(e);
/* 273:    */     }
/* 274:    */   }
/* 275:    */   
/* 276:    */   public void notifyOnParityError(boolean b)
/* 277:    */   {
/* 278:    */     try
/* 279:    */     {
/* 280:544 */       Method method = classSerialPort.getMethod("notifyOnParityError", new Class[] { Boolean.TYPE });
/* 281:545 */       method.invoke(this.realObject, new Object[] { Boolean.valueOf(b) });
/* 282:    */     }
/* 283:    */     catch (InvocationTargetException e)
/* 284:    */     {
/* 285:549 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 286:    */     }
/* 287:    */     catch (Exception e)
/* 288:    */     {
/* 289:553 */       throw new RuntimeException(e);
/* 290:    */     }
/* 291:    */   }
/* 292:    */   
/* 293:    */   public void setFlowControlMode(int flowcontrol)
/* 294:    */   {
/* 295:576 */     Class<?>[] paramTypes = { Integer.TYPE };
/* 296:    */     try
/* 297:    */     {
/* 298:579 */       Method method = classSerialPort.getMethod("setFlowControlMode", paramTypes);
/* 299:580 */       method.invoke(this.realObject, new Object[] { Integer.valueOf(flowcontrol) });
/* 300:    */     }
/* 301:    */     catch (InvocationTargetException e)
/* 302:    */     {
/* 303:584 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 304:    */     }
/* 305:    */     catch (Exception e)
/* 306:    */     {
/* 307:588 */       throw new RuntimeException(e);
/* 308:    */     }
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void setInputBufferSize(int serial_buffer_size)
/* 312:    */   {
/* 313:    */     try
/* 314:    */     {
/* 315:604 */       Method method = classSerialPort.getMethod("setInputBufferSize", new Class[] { Integer.TYPE });
/* 316:605 */       method.invoke(this.realObject, new Object[] { Integer.valueOf(serial_buffer_size) });
/* 317:    */     }
/* 318:    */     catch (InvocationTargetException e)
/* 319:    */     {
/* 320:609 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 321:    */     }
/* 322:    */     catch (Exception e)
/* 323:    */     {
/* 324:613 */       throw new RuntimeException(e);
/* 325:    */     }
/* 326:    */   }
/* 327:    */   
/* 328:    */   public void setOutputBufferSize(int serial_buffer_size)
/* 329:    */   {
/* 330:    */     try
/* 331:    */     {
/* 332:628 */       Method method = classSerialPort.getMethod("setOutputBufferSize", new Class[] { Integer.TYPE });
/* 333:629 */       method.invoke(this.realObject, new Object[] { Integer.valueOf(serial_buffer_size) });
/* 334:    */     }
/* 335:    */     catch (InvocationTargetException e)
/* 336:    */     {
/* 337:633 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 338:    */     }
/* 339:    */     catch (Exception e)
/* 340:    */     {
/* 341:637 */       throw new RuntimeException(e);
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   public void setSerialPortParams(int baudrate, int dataBits, int stopBits, int parity)
/* 346:    */   {
/* 347:670 */     Class<?>[] paramTypes = { Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE };
/* 348:    */     try
/* 349:    */     {
/* 350:673 */       Method method = classSerialPort.getMethod("setSerialPortParams", paramTypes);
/* 351:674 */       method.invoke(this.realObject, new Object[] { Integer.valueOf(baudrate), Integer.valueOf(dataBits), Integer.valueOf(stopBits), Integer.valueOf(parity) });
/* 352:    */     }
/* 353:    */     catch (InvocationTargetException e)
/* 354:    */     {
/* 355:678 */       throw new RuntimeException(new RuntimeException(e.getTargetException().toString()));
/* 356:    */     }
/* 357:    */     catch (Exception e)
/* 358:    */     {
/* 359:682 */       throw new RuntimeException(e);
/* 360:    */     }
/* 361:    */   }
/* 362:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.helper.SerialPort
 * JD-Core Version:    0.7.0.1
 */
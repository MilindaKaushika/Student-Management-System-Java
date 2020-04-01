/*   1:    */ package org.smslib.modem;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.util.StringTokenizer;
/*   7:    */ import org.smslib.GatewayException;
/*   8:    */ import org.smslib.Service;
/*   9:    */ import org.smslib.Settings;
/*  10:    */ import org.smslib.helper.CommPortIdentifier;
/*  11:    */ import org.smslib.helper.Logger;
/*  12:    */ import org.smslib.helper.SerialPort;
/*  13:    */ import org.smslib.helper.SerialPortEvent;
/*  14:    */ import org.smslib.helper.SerialPortEventListener;
/*  15:    */ import org.smslib.threading.AServiceThread;
/*  16:    */ 
/*  17:    */ class SerialModemDriver
/*  18:    */   extends AModemDriver
/*  19:    */   implements SerialPortEventListener
/*  20:    */ {
/*  21:    */   private String comPort;
/*  22:    */   private int baudRate;
/*  23:    */   private CommPortIdentifier portId;
/*  24:    */   private SerialPort serialPort;
/*  25:    */   private InputStream in;
/*  26:    */   private OutputStream out;
/*  27:    */   private PortReader portReader;
/*  28:    */   
/*  29:    */   protected SerialModemDriver(ModemGateway myGateway, String deviceParms)
/*  30:    */   {
/*  31: 54 */     super(myGateway, deviceParms);
/*  32: 55 */     StringTokenizer tokens = new StringTokenizer(deviceParms, ":");
/*  33: 56 */     setComPort(tokens.nextToken());
/*  34: 57 */     setBaudRate(Integer.parseInt(tokens.nextToken()));
/*  35: 58 */     setSerialPort(null);
/*  36:    */   }
/*  37:    */   
/*  38:    */   protected void connectPort()
/*  39:    */     throws GatewayException, IOException, InterruptedException
/*  40:    */   {
/*  41: 64 */     if (Service.getInstance().getSettings().SERIAL_NOFLUSH) {
/*  42: 64 */       Logger.getInstance().logInfo("Comm port flushing is disabled.", null, getGateway().getGatewayId());
/*  43:    */     }
/*  44: 65 */     if (Service.getInstance().getSettings().SERIAL_POLLING) {
/*  45: 65 */       Logger.getInstance().logInfo("Using polled serial port mode.", null, getGateway().getGatewayId());
/*  46:    */     }
/*  47:    */     try
/*  48:    */     {
/*  49: 68 */       Logger.getInstance().logInfo("Opening: " + getComPort() + " @" + getBaudRate(), null, getGateway().getGatewayId());
/*  50: 69 */       CommPortIdentifier.getPortIdentifiers();
/*  51: 70 */       setPortId(CommPortIdentifier.getPortIdentifier(getComPort()));
/*  52: 71 */       setSerialPort(getPortId().open("org.smslib", 1971));
/*  53: 72 */       setIn(getSerialPort().getInputStream());
/*  54: 73 */       setOut(getSerialPort().getOutputStream());
/*  55: 74 */       if (!Service.getInstance().getSettings().SERIAL_POLLING)
/*  56:    */       {
/*  57: 76 */         getSerialPort().notifyOnDataAvailable(true);
/*  58: 77 */         getSerialPort().notifyOnOutputEmpty(true);
/*  59:    */       }
/*  60: 79 */       if (!Service.getInstance().getSettings().SERIAL_NOEVENTS)
/*  61:    */       {
/*  62: 81 */         getSerialPort().notifyOnBreakInterrupt(true);
/*  63: 82 */         getSerialPort().notifyOnFramingError(true);
/*  64: 83 */         getSerialPort().notifyOnOverrunError(true);
/*  65: 84 */         getSerialPort().notifyOnParityError(true);
/*  66:    */       }
/*  67:    */       else
/*  68:    */       {
/*  69: 86 */         Logger.getInstance().logInfo("Skipping registration of serial port events!", null, null);
/*  70:    */       }
/*  71: 87 */       if (Service.getInstance().getSettings().SERIAL_RTSCTS_OUT) {
/*  72: 87 */         getSerialPort().setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
/*  73:    */       } else {
/*  74: 88 */         getSerialPort().setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
/*  75:    */       }
/*  76: 89 */       getSerialPort().addEventListener(this);
/*  77: 90 */       getSerialPort().setSerialPortParams(getBaudRate(), SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
/*  78: 91 */       getSerialPort().setInputBufferSize(Service.getInstance().getSettings().SERIAL_BUFFER_SIZE);
/*  79: 92 */       getSerialPort().setOutputBufferSize(Service.getInstance().getSettings().SERIAL_BUFFER_SIZE);
/*  80: 93 */       getSerialPort().enableReceiveThreshold(1);
/*  81: 94 */       getSerialPort().enableReceiveTimeout(Service.getInstance().getSettings().SERIAL_TIMEOUT);
/*  82: 95 */       if (Service.getInstance().getSettings().SERIAL_POLLING) {
/*  83: 97 */         setPortReader(new PortReader("PortReader() [" + getComPort() + "]", Service.getInstance().getSettings().SERIAL_POLLING_INTERVAL));
/*  84:    */       }
/*  85:    */     }
/*  86:    */     catch (Exception e)
/*  87:    */     {
/*  88:102 */       throw new GatewayException("Comm library exception: " + e.getMessage());
/*  89:    */     }
/*  90:    */   }
/*  91:    */   
/*  92:    */   protected void disconnectPort()
/*  93:    */     throws IOException, InterruptedException
/*  94:    */   {
/*  95:109 */     synchronized (getSYNCReader())
/*  96:    */     {
/*  97:111 */       if (Service.getInstance().getSettings().SERIAL_POLLING) {
/*  98:113 */         if (getPortReader() != null)
/*  99:    */         {
/* 100:115 */           getPortReader().cancel();
/* 101:116 */           setPortReader(null);
/* 102:    */         }
/* 103:    */       }
/* 104:119 */       if (getSerialPort() != null) {
/* 105:119 */         getSerialPort().close();
/* 106:    */       }
/* 107:120 */       Logger.getInstance().logInfo("Closing: " + getComPort() + " @" + getBaudRate(), null, getGateway().getGatewayId());
/* 108:    */     }
/* 109:    */   }
/* 110:    */   
/* 111:    */   protected void clear()
/* 112:    */     throws IOException
/* 113:    */   {
/* 114:127 */     while (portHasData()) {
/* 115:128 */       read();
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   protected boolean portHasData()
/* 120:    */     throws IOException
/* 121:    */   {
/* 122:134 */     return getIn().available() > 0;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void serialEvent(SerialPortEvent event)
/* 126:    */   {
/* 127:139 */     int eventType = event.getEventType();
/* 128:140 */     if (eventType == SerialPortEvent.OE) {
/* 129:140 */       Logger.getInstance().logError("Overrun Error!", null, getGateway().getGatewayId());
/* 130:141 */     } else if (eventType == SerialPortEvent.FE) {
/* 131:141 */       Logger.getInstance().logError("Framing Error!", null, getGateway().getGatewayId());
/* 132:142 */     } else if (eventType == SerialPortEvent.PE) {
/* 133:142 */       Logger.getInstance().logError("Parity Error!", null, getGateway().getGatewayId());
/* 134:143 */     } else if (eventType == SerialPortEvent.DATA_AVAILABLE) {
/* 135:145 */       if (!Service.getInstance().getSettings().SERIAL_POLLING) {
/* 136:147 */         synchronized (getSYNCReader())
/* 137:    */         {
/* 138:149 */           setDataReceived(true);
/* 139:150 */           getSYNCReader().notifyAll();
/* 140:    */         }
/* 141:    */       }
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void write(char c)
/* 146:    */     throws IOException
/* 147:    */   {
/* 148:159 */     getOut().write(c);
/* 149:160 */     if (!Service.getInstance().getSettings().SERIAL_NOFLUSH) {
/* 150:160 */       getOut().flush();
/* 151:    */     }
/* 152:    */   }
/* 153:    */   
/* 154:    */   public void write(byte[] s)
/* 155:    */     throws IOException
/* 156:    */   {
/* 157:166 */     getOut().write(s);
/* 158:167 */     if (!Service.getInstance().getSettings().SERIAL_NOFLUSH) {
/* 159:167 */       getOut().flush();
/* 160:    */     }
/* 161:    */   }
/* 162:    */   
/* 163:    */   protected int read()
/* 164:    */     throws IOException
/* 165:    */   {
/* 166:173 */     return getIn().read();
/* 167:    */   }
/* 168:    */   
/* 169:    */   PortReader getPortReader()
/* 170:    */   {
/* 171:178 */     return this.portReader;
/* 172:    */   }
/* 173:    */   
/* 174:    */   void setPortReader(PortReader myPortReader)
/* 175:    */   {
/* 176:183 */     this.portReader = myPortReader;
/* 177:    */   }
/* 178:    */   
/* 179:    */   private class PortReader
/* 180:    */     extends AServiceThread
/* 181:    */   {
/* 182:    */     public PortReader(String name, int delay)
/* 183:    */     {
/* 184:190 */       super(delay, 0, true);
/* 185:    */     }
/* 186:    */     
/* 187:    */     public void process()
/* 188:    */       throws Exception
/* 189:    */     {
/* 190:196 */       if (SerialModemDriver.this.portHasData()) {
/* 191:198 */         synchronized (SerialModemDriver.this.getSYNCReader())
/* 192:    */         {
/* 193:200 */           SerialModemDriver.this.setDataReceived(true);
/* 194:201 */           SerialModemDriver.this.getSYNCReader().notifyAll();
/* 195:    */         }
/* 196:    */       }
/* 197:    */     }
/* 198:    */   }
/* 199:    */   
/* 200:    */   String getComPort()
/* 201:    */   {
/* 202:209 */     return this.comPort;
/* 203:    */   }
/* 204:    */   
/* 205:    */   void setComPort(String myComPort)
/* 206:    */   {
/* 207:214 */     this.comPort = myComPort;
/* 208:    */   }
/* 209:    */   
/* 210:    */   int getBaudRate()
/* 211:    */   {
/* 212:219 */     return this.baudRate;
/* 213:    */   }
/* 214:    */   
/* 215:    */   void setBaudRate(int myBaudRate)
/* 216:    */   {
/* 217:224 */     this.baudRate = myBaudRate;
/* 218:    */   }
/* 219:    */   
/* 220:    */   CommPortIdentifier getPortId()
/* 221:    */   {
/* 222:229 */     return this.portId;
/* 223:    */   }
/* 224:    */   
/* 225:    */   void setPortId(CommPortIdentifier myPortId)
/* 226:    */   {
/* 227:234 */     this.portId = myPortId;
/* 228:    */   }
/* 229:    */   
/* 230:    */   SerialPort getSerialPort()
/* 231:    */   {
/* 232:239 */     return this.serialPort;
/* 233:    */   }
/* 234:    */   
/* 235:    */   void setSerialPort(SerialPort mySerialPort)
/* 236:    */   {
/* 237:244 */     this.serialPort = mySerialPort;
/* 238:    */   }
/* 239:    */   
/* 240:    */   InputStream getIn()
/* 241:    */   {
/* 242:249 */     return this.in;
/* 243:    */   }
/* 244:    */   
/* 245:    */   void setIn(InputStream myIn)
/* 246:    */   {
/* 247:254 */     this.in = myIn;
/* 248:    */   }
/* 249:    */   
/* 250:    */   OutputStream getOut()
/* 251:    */   {
/* 252:259 */     return this.out;
/* 253:    */   }
/* 254:    */   
/* 255:    */   void setOut(OutputStream myOut)
/* 256:    */   {
/* 257:264 */     this.out = myOut;
/* 258:    */   }
/* 259:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.modem.SerialModemDriver
 * JD-Core Version:    0.7.0.1
 */
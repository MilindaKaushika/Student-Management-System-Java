/*   1:    */ package org.smslib.modem;
/*   2:    */ 
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.io.InputStream;
/*   5:    */ import java.io.OutputStream;
/*   6:    */ import java.security.NoSuchAlgorithmException;
/*   7:    */ import java.util.StringTokenizer;
/*   8:    */ import javax.net.ssl.SSLContext;
import org.apache.commons.net.SocketFactory;
/*   9:    */ import org.apache.commons.net.telnet.EchoOptionHandler;
/*  10:    */ import org.apache.commons.net.telnet.InvalidTelnetOptionException;
/*  11:    */ import org.apache.commons.net.telnet.SimpleOptionHandler;
/*  12:    */ import org.apache.commons.net.telnet.SuppressGAOptionHandler;
/*  13:    */ import org.apache.commons.net.telnet.TelnetClient;
/*  14:    */ import org.apache.commons.net.telnet.TerminalTypeOptionHandler;
import org.smslib.AGateway;
/*  15:    */ import org.smslib.AGateway.GatewayStatuses;
/*  16:    */ import org.smslib.GatewayException;
/*  17:    */ import org.smslib.Service;
/*  18:    */ import org.smslib.Settings;
/*  19:    */ import org.smslib.helper.Logger;
/*  20:    */ 
/*  21:    */ class IPModemDriver
/*  22:    */   extends AModemDriver
/*  23:    */ {
/*  24:    */   private String ipAddress;
/*  25:    */   private int ipPort;
/*  26:    */   private TelnetClient tc;
/*  27:    */   private InputStream in;
/*  28:    */   private OutputStream out;
/*  29:    */   private Peeker peeker;
/*  30: 55 */   private TerminalTypeOptionHandler ttopt = new TerminalTypeOptionHandler("VT100", false, false, true, false);
/*  31: 57 */   private SimpleOptionHandler binaryopt = new SimpleOptionHandler(0, true, false, true, false);
/*  32: 59 */   private EchoOptionHandler echoopt = new EchoOptionHandler(true, false, true, false);
/*  33: 61 */   private SuppressGAOptionHandler gaopt = new SuppressGAOptionHandler(true, true, true, true);
/*  34:    */   
/*  35:    */   protected IPModemDriver(ModemGateway myGateway, String deviceParms)
/*  36:    */   {
/*  37: 65 */     super(myGateway, deviceParms);
/*  38: 66 */     StringTokenizer tokens = new StringTokenizer(deviceParms, ":");
/*  39: 67 */     this.ipAddress = tokens.nextToken();
/*  40: 68 */     this.ipPort = Integer.parseInt(tokens.nextToken());
/*  41: 69 */     this.tc = null;
/*  42:    */   }
/*  43:    */   
/*  44:    */   protected void connectPort()
/*  45:    */     throws GatewayException, IOException, InterruptedException
/*  46:    */   {
/*  47:    */     try
/*  48:    */     {
/*  49: 77 */       Logger.getInstance().logInfo("Opening: " + this.ipAddress + " @" + this.ipPort, null, getGateway().getGatewayId());
/*  50: 78 */       this.tc = new TelnetClient();
/*  51: 79 */       this.tc.addOptionHandler(this.ttopt);
/*  52: 80 */       this.tc.addOptionHandler(this.echoopt);
/*  53: 81 */       this.tc.addOptionHandler(this.gaopt);
/*  54: 82 */       if (getGateway().getIpProtocol() == ModemGateway.IPProtocols.BINARY) {
/*  55: 82 */         this.tc.addOptionHandler(this.binaryopt);
/*  56:    */       }
/*  57: 83 */       if (getGateway().getIpEncryption()) {
/*  58:    */         try
/*  59:    */         {
/*  60: 87 */           this.tc.setSocketFactory((SocketFactory) SSLContext.getInstance("Default").getSocketFactory());
/*  61:    */         }
/*  62:    */         catch (NoSuchAlgorithmException e)
/*  63:    */         {
/*  64: 91 */           Logger.getInstance().logError("Unable to find algorithm needed for using SSL", e, getGateway().getGatewayId());
/*  65:    */         }
/*  66:    */       }
/*  67: 95 */       this.tc.connect(this.ipAddress, this.ipPort);
/*  68: 96 */       this.in = this.tc.getInputStream();
/*  69: 97 */       this.out = this.tc.getOutputStream();
/*  70: 98 */       this.peeker = new Peeker();
/*  71:    */     }
/*  72:    */     catch (InvalidTelnetOptionException e)
/*  73:    */     {
/*  74:102 */       throw new GatewayException("Unsupported telnet option for the selected IP connection.");
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void disconnectPort()
/*  79:    */     throws IOException, InterruptedException
/*  80:    */   {
/*  81:109 */     Logger.getInstance().logInfo("Closing: " + this.ipAddress + " @" + this.ipPort, null, getGateway().getGatewayId());
/*  82:110 */     synchronized (getSYNCReader())
/*  83:    */     {
/*  84:112 */       if (this.tc != null) {
/*  85:112 */         this.tc.disconnect();
/*  86:    */       }
/*  87:113 */       this.tc = null;
/*  88:114 */       this.peeker.interrupt();
/*  89:115 */       this.peeker.join();
/*  90:    */     }
/*  91:    */   }
/*  92:    */   
/*  93:    */   protected void clear()
/*  94:    */     throws IOException
/*  95:    */   {
/*  96:122 */     while (portHasData()) {
/*  97:123 */       read();
/*  98:    */     }
/*  99:    */   }
/* 100:    */   
/* 101:    */   protected boolean portHasData()
/* 102:    */     throws IOException
/* 103:    */   {
/* 104:129 */     return this.in.available() > 0;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void write(char c)
/* 108:    */     throws IOException
/* 109:    */   {
/* 110:135 */     this.out.write((short)c);
/* 111:136 */     this.out.flush();
/* 112:    */   }
/* 113:    */   
/* 114:    */   public void write(byte[] s)
/* 115:    */     throws IOException
/* 116:    */   {
/* 117:142 */     this.out.write(s);
/* 118:143 */     this.out.flush();
/* 119:    */   }
/* 120:    */   
/* 121:    */   protected int read()
/* 122:    */     throws IOException
/* 123:    */   {
/* 124:149 */     return this.in.read();
/* 125:    */   }
/* 126:    */   
/* 127:    */   TelnetClient getTc()
/* 128:    */   {
/* 129:154 */     return this.tc;
/* 130:    */   }
/* 131:    */   
/* 132:    */   private class Peeker
/* 133:    */     extends Thread
/* 134:    */   {
/* 135:    */     public Peeker()
/* 136:    */     {
/* 137:161 */       setPriority(1);
/* 138:162 */       start();
/* 139:    */     }
/* 140:    */     
/* 141:    */     public void run()
/* 142:    */     {
/* 143:168 */       Logger.getInstance().logDebug("Peeker started.", null, IPModemDriver.this.getGateway().getGatewayId());
/* 144:    */       for (;;)
/* 145:    */       {
/* 146:    */         try
/* 147:    */         {
/* 148:173 */           if (IPModemDriver.this.getTc() != null) {
/* 149:175 */             if (IPModemDriver.this.portHasData()) {
/* 150:177 */               synchronized (IPModemDriver.this.getSYNCReader())
/* 151:    */               {
/* 152:179 */                 IPModemDriver.this.setDataReceived(true);
/* 153:180 */                 IPModemDriver.this.getSYNCReader().notifyAll();
/* 154:    */               }
/* 155:    */             }
/* 156:    */           }
/* 157:184 */           sleep(Service.getInstance().getSettings().SERIAL_POLLING_INTERVAL);
/* 158:    */         }
/* 159:    */         catch (InterruptedException e)
/* 160:    */         {
/* 161:188 */           if (IPModemDriver.this.getTc() == null) {
/* 162:    */             break;
/* 163:    */           }
/* 164:    */         }
/* 165:    */         catch (IOException e)
/* 166:    */         {
/* 167:192 */           IPModemDriver.this.getGateway().setStatus(AGateway.GatewayStatuses.RESTART);
/* 168:    */         }
/* 169:    */       }
/* 170:195 */       Logger.getInstance().logDebug("Peeker stopped.", null, IPModemDriver.this.getGateway().getGatewayId());
/* 171:    */     }
/* 172:    */   }
/* 173:    */ }



/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.modem.IPModemDriver

 * JD-Core Version:    0.7.0.1

 */
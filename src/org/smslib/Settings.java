/*   1:    */ package org.smslib;
/*   2:    */ 
/*   3:    */ public class Settings
/*   4:    */ {
/*   5: 32 */   public boolean SERIAL_NOFLUSH = false;
/*   6: 37 */   public boolean SERIAL_NOEVENTS = false;
/*   7: 43 */   public boolean SERIAL_POLLING = false;
/*   8: 48 */   public int SERIAL_POLLING_INTERVAL = 200;
/*   9: 53 */   public int SERIAL_TIMEOUT = 15000;
/*  10: 58 */   public int SERIAL_KEEPALIVE_INTERVAL = 60;
/*  11: 63 */   public int SERIAL_BUFFER_SIZE = 16384;
/*  12: 68 */   public int SERIAL_CLEAR_WAIT = 1000;
/*  13: 74 */   public boolean SERIAL_RTSCTS_OUT = false;
/*  14: 80 */   public int QUEUE_RETRIES = 3;
/*  15: 85 */   public int AT_WAIT = 200;
/*  16: 90 */   public int AT_WAIT_AFTER_RESET = 10000;
/*  17: 95 */   public int AT_WAIT_CMD = 1100;
/*  18:100 */   public int AT_WAIT_CGMS = 200;
/*  19:105 */   public int AT_WAIT_NETWORK = 5000;
/*  20:110 */   public int AT_WAIT_SIMPIN = 5000;
/*  21:115 */   public int AT_WAIT_CNMI = 3000;
/*  22:120 */   public int OUTBOUND_RETRIES = 3;
/*  23:125 */   public int OUTBOUND_RETRY_WAIT = 3000;
/*  24:130 */   public int WATCHDOG_INTERVAL = 15;
/*  25:138 */   public int CNMI_EMULATOR_INTERVAL = 30;
/*  26:143 */   public boolean MASK_IMSI = true;
/*  27:145 */   public boolean DISABLE_CMTI = false;
/*  28:150 */   public int HOURS_TO_ORPHAN = 72;
/*  29:155 */   public boolean CONCURRENT_GATEWAY_START = true;
/*  30:160 */   public boolean DISABLE_CMMS = false;
/*  31:165 */   public boolean DISABLE_COPS = false;
/*  32:170 */   public String CACHE_DIRECTORY = System.getProperty("user.home");
/*  33:175 */   public String QUEUE_DIRECTORY = null;
/*  34:    */   
/*  35:    */   Settings()
/*  36:    */   {
/*  37:179 */     if (System.getProperty("smslib.serial.noflush") != null) {
/*  38:179 */       this.SERIAL_NOFLUSH = true;
/*  39:    */     }
/*  40:180 */     if (System.getProperty("smslib.serial.noevents") != null) {
/*  41:180 */       this.SERIAL_NOEVENTS = true;
/*  42:    */     }
/*  43:181 */     if (System.getProperty("smslib.serial.polling") != null) {
/*  44:181 */       this.SERIAL_POLLING = true;
/*  45:    */     }
/*  46:182 */     if (System.getProperty("smslib.serial.pollinginterval") != null) {
/*  47:182 */       this.SERIAL_POLLING_INTERVAL = Integer.parseInt(System.getProperty("smslib.serial.pollinginterval"));
/*  48:    */     }
/*  49:183 */     if (System.getProperty("smslib.serial.timeout") != null) {
/*  50:183 */       this.SERIAL_TIMEOUT = Integer.parseInt(System.getProperty("smslib.serial.timeout"));
/*  51:    */     }
/*  52:184 */     if (System.getProperty("smslib.serial.keepalive") != null) {
/*  53:184 */       this.SERIAL_KEEPALIVE_INTERVAL = Integer.parseInt(System.getProperty("smslib.serial.keepalive"));
/*  54:    */     }
/*  55:185 */     if (System.getProperty("smslib.serial.buffer") != null) {
/*  56:185 */       this.SERIAL_BUFFER_SIZE = Integer.parseInt(System.getProperty("smslib.serial.buffer"));
/*  57:    */     }
/*  58:186 */     if (System.getProperty("smslib.serial.clearwait") != null) {
/*  59:186 */       this.SERIAL_CLEAR_WAIT = Integer.parseInt(System.getProperty("smslib.serial.clearwait"));
/*  60:    */     }
/*  61:187 */     if (System.getProperty("smslib.queue.retries") != null) {
/*  62:187 */       this.QUEUE_RETRIES = Integer.parseInt(System.getProperty("smslib.queue.retries"));
/*  63:    */     }
/*  64:188 */     if (System.getProperty("smslib.outbound.retries") != null) {
/*  65:188 */       this.OUTBOUND_RETRIES = Integer.parseInt(System.getProperty("smslib.outbound.retries"));
/*  66:    */     }
/*  67:189 */     if (System.getProperty("smslib.outbound.retrywait") != null) {
/*  68:189 */       this.OUTBOUND_RETRY_WAIT = Integer.parseInt(System.getProperty("smslib.outbound.retrywait"));
/*  69:    */     }
/*  70:190 */     if (System.getProperty("smslib.at.wait") != null) {
/*  71:190 */       this.AT_WAIT = Integer.parseInt(System.getProperty("smslib.at.wait"));
/*  72:    */     }
/*  73:191 */     if (System.getProperty("smslib.at.resetwait") != null) {
/*  74:191 */       this.AT_WAIT_AFTER_RESET = Integer.parseInt(System.getProperty("smslib.at.resetwait"));
/*  75:    */     }
/*  76:192 */     if (System.getProperty("smslib.at.cmdwait") != null) {
/*  77:192 */       this.AT_WAIT_CMD = Integer.parseInt(System.getProperty("smslib.at.cmdwait"));
/*  78:    */     }
/*  79:193 */     if (System.getProperty("smslib.at.cmgswait") != null) {
/*  80:193 */       this.AT_WAIT_CGMS = Integer.parseInt(System.getProperty("smslib.at.cmgswait"));
/*  81:    */     }
/*  82:194 */     if (System.getProperty("smslib.at.networkwait") != null) {
/*  83:194 */       this.AT_WAIT_NETWORK = Integer.parseInt(System.getProperty("smslib.at.networkwait"));
/*  84:    */     }
/*  85:195 */     if (System.getProperty("smslib.at.simpinwait") != null) {
/*  86:195 */       this.AT_WAIT_SIMPIN = Integer.parseInt(System.getProperty("smslib.at.simpinwait"));
/*  87:    */     }
/*  88:196 */     if (System.getProperty("smslib.at.cnmiwait") != null) {
/*  89:196 */       this.AT_WAIT_CNMI = Integer.parseInt(System.getProperty("smslib.at.cnmiwait"));
/*  90:    */     }
/*  91:197 */     if (System.getProperty("smslib.watchdog") != null) {
/*  92:197 */       this.WATCHDOG_INTERVAL = Integer.parseInt(System.getProperty("smslib.watchdog"));
/*  93:    */     }
/*  94:198 */     if (System.getProperty("smslib.disable.concurrent.gateway.startup") != null) {
/*  95:198 */       this.CONCURRENT_GATEWAY_START = false;
/*  96:    */     }
/*  97:199 */     if (System.getProperty("smslib.nocmti") != null) {
/*  98:199 */       this.DISABLE_CMTI = true;
/*  99:    */     }
/* 100:200 */     if (System.getProperty("smslib.nocmms") != null) {
/* 101:200 */       this.DISABLE_CMMS = true;
/* 102:    */     }
/* 103:201 */     if (System.getProperty("smslib.nocops") != null) {
/* 104:201 */       this.DISABLE_COPS = true;
/* 105:    */     }
/* 106:202 */     if (System.getProperty("smslib.cachedir") != null) {
/* 107:202 */       this.CACHE_DIRECTORY = System.getProperty("smslib.cachedir");
/* 108:    */     }
/* 109:203 */     if (System.getProperty("smslib.queuedir") != null) {
/* 110:203 */       this.QUEUE_DIRECTORY = System.getProperty("smslib.queuedir");
/* 111:    */     }
/* 112:    */   }
/* 113:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.Settings
 * JD-Core Version:    0.7.0.1
 */
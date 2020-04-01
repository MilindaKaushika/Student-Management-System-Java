/*  1:   */ package org.smslib.helper;
/*  2:   */ 



import SMS.ModamSMS;
/*  3:   */ import java.io.File;
import java.io.IOException;
/*  4:   */ import java.util.Properties;
import javax.swing.JOptionPane;
/*  5:   */ import org.apache.log4j.Level;
/*  6:   */ import org.apache.log4j.PropertyConfigurator;
import org.smslib.Service;
/*  7:   */ 
/*  8:   */ public class Logger
/*  9:   */ {
    
    int logwarn=0;
    
    
/* 10:29 */   private static final Logger logger = new Logger();
/* 11:   */   org.apache.log4j.Logger log4jLogger;
/* 12:33 */   private static final String FQCN = Logger.class.getName();
/* 13:   */   
/* 14:   */   private Logger()
/* 15:   */   {
/* 16:37 */     if (System.getProperties().getProperty("java.vm.name").equalsIgnoreCase("ikvm.net"))
/* 17:   */     {
/* 18:39 */       File f = new File("log4j.properties");
/* 19:40 */       if (!f.exists())
/* 20:   */       {
/* 21:40 */         this.log4jLogger = null;
/* 22:   */       }
/* 23:   */       else
/* 24:   */       {
/* 25:43 */         this.log4jLogger = org.apache.log4j.Logger.getLogger("smslib");
/* 26:44 */         PropertyConfigurator.configure("log4j.properties");
/* 27:   */       }
/* 28:   */     }
/* 29:   */     else
/* 30:   */     {
/* 31:49 */       this.log4jLogger = org.apache.log4j.Logger.getLogger("smslib");
/* 32:50 */       PropertyConfigurator.configure("log4j.properties");
/* 33:   */     }
/* 34:   */   }
/* 35:   */   
/* 36:   */   public static Logger getInstance()
/* 37:   */   {
/* 38:56 */     return logger;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public void logInfo(String message, Exception e, String gatewayId)
/* 42:   */   {
    
   // System.out.println("meeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
/* 43:61 */     if (this.log4jLogger == null) {
/* 44:61 */       return;
/* 45:   */     }
/* 46:62 */     this.log4jLogger.log(FQCN, Level.INFO, formatMessage(message, gatewayId), e);
/* 47:   */   }
/* 48:   */   
/* 49:   */   public void logWarn(String message, Exception e, String gatewayId)
/* 50:   */   {
    if(logwarn==0){
        ++logwarn;
  //  JOptionPane.showMessageDialog(null, "Error Happen Not Sent trying resend", "SMS INFO", JOptionPane.WARNING_MESSAGE);
      ++logwarn;
    JOptionPane.showMessageDialog(null, "Error happing while sending trying to resend", "SMS INFO", JOptionPane.ERROR_MESSAGE);
        try {
           // Service.getInstance().stopService();
             // Service.getInstance().removeGateway(ModamSMS.gateway);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex, "SMS INFO", JOptionPane.ERROR_MESSAGE);
        }}
    else if(logwarn==1){
    
    }
    
    
/* 51:67 */     if (this.log4jLogger == null) {
/* 52:67 */       return;
/* 53:   */     }
/* 54:68 */     this.log4jLogger.log(FQCN, Level.WARN, formatMessage(message, gatewayId), e);
/* 55:   */   }
/* 56:   */   
/* 57:   */   public void logDebug(String message, Exception e, String gatewayId)
/* 58:   */   {
                //JOptionPane.showMessageDialog(null, message, "USSD", JOptionPane.INFORMATION_MESSAGE);

    //System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");
/* 59:73 */     if (this.log4jLogger == null) {
/* 60:73 */       return;
/* 61:   */     }
/* 62:74 */     this.log4jLogger.log(FQCN, Level.DEBUG, formatMessage(message, gatewayId), e);
/* 63:   */   }
/* 64:   */   
/* 65:   */   public void logError(String message, Exception e, String gatewayId)
/* 66:   */   {
     //
     try {
         if(message.equals("Overrun Error!")&&logwarn==0){
             ++logwarn;
        JOptionPane.showMessageDialog(null, "Communication Error Try Again System Will Exit Recover This Error", "SMS INFO", JOptionPane.ERROR_MESSAGE);
         System.exit(0);
       Service.getInstance().stopService();
            Service.getInstance().removeGateway(ModamSMS.gateway);
         
          
      }
    } catch (Exception e1) {
       JOptionPane.showMessageDialog(null, e1, "System INFO", JOptionPane.ERROR_MESSAGE);  
    }
/* 67:79 */     if (this.log4jLogger == null) {
/* 68:79 */       return;
/* 69:   */     }
/* 70:80 */     this.log4jLogger.log(FQCN, Level.ERROR, formatMessage(message, gatewayId), e);
/* 71:   */   }
/* 72:   */   
/* 73:   */   private String formatMessage(String message, String gatewayId)
/* 74:   */   {
    if (true) {
        
    }
    
    /* 75:85 */     return  message;

/* 76:   */   }
/* 77:   */ }



/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.helper.Logger

 * JD-Core Version:    0.7.0.1

 */
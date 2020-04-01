/*   1:    */ package org.smslib.threading;
/*   2:    */ 
/*   3:    */ import org.smslib.helper.Logger;
/*   4:    */ 
/*   5:    */ public abstract class AServiceThread
/*   6:    */   extends Thread
/*   7:    */ {
/*   8:    */   private int delay;
/*   9:    */   private int initialDelay;
/*  10:    */   private boolean enabled;
/*  11:    */   private boolean canceled;
/*  12:    */   
/*  13:    */   public AServiceThread( int delay, int initialDelay, boolean enabled)
/*  14:    */   {
/*  15: 34 */    // setName(name);
/*  16: 35 */     setDelay(delay);
/*  17: 36 */     if (enabled) {
/*  18: 36 */       enable();
/*  19:    */     } else {
/*  20: 37 */       disable();
/*  21:    */     }
/*  22: 38 */     this.canceled = false;
/*  23: 39 */     Logger.getInstance().logDebug("Initialized.", null, null);
/*  24: 40 */     start();
/*  25:    */   }
/*  26:    */   
/*  27:    */   public int getDelay()
/*  28:    */   {
/*  29: 45 */     return this.delay;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public void setDelay(int delay)
/*  33:    */   {
/*  34: 50 */     this.delay = delay;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int getInitialDelay()
/*  38:    */   {
/*  39: 55 */     return this.initialDelay;
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setInitialDelay(int initialDelay)
/*  43:    */   {
/*  44: 60 */     this.initialDelay = initialDelay;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean isEnabled()
/*  48:    */   {
/*  49: 65 */     return this.enabled;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public void enable()
/*  53:    */   {
/*  54: 70 */     this.enabled = true;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public void disable()
/*  58:    */   {
/*  59: 75 */     this.enabled = false;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public boolean isCanceled()
/*  63:    */   {
/*  64: 80 */     return this.canceled;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void cancel()
/*  68:    */   {
/*  69: 85 */     this.canceled = true;
/*  70: 86 */     interrupt();
/*  71:    */     try
/*  72:    */     {
/*  73: 89 */       join();
/*  74:    */     }
/*  75:    */     catch (InterruptedException e) {}
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void run()
/*  79:    */   {
/*  80:100 */     while (!isCanceled()) {
/*  81:    */       try
/*  82:    */       {
/*  83:104 */         Logger.getInstance().logDebug(isEnabled() ? "Running..." : "** disabled **", null, null);
/*  84:105 */         if (isEnabled()) {
/*  85:105 */           process();
/*  86:    */         }
/*  87:106 */         sleep(getDelay());
/*  88:    */       }
/*  89:    */       catch (InterruptedException e)
/*  90:    */       {
/*  91:110 */         if (isCanceled())
/*  92:    */         {
/*  93:112 */           Logger.getInstance().logDebug("Stopped.", null, null);
/*  94:113 */           break;
/*  95:    */         }
/*  96:115 */         Logger.getInstance().logDebug("Interrupted!", null, null);
/*  97:    */       }
/*  98:    */       catch (Exception e)
/*  99:    */       {
/* 100:119 */         Logger.getInstance().logError("Error!", e, null);
/* 101:    */       }
/* 102:    */     }
/* 103:    */   }
/* 104:    */   
/* 105:    */   public abstract void process()
/* 106:    */     throws Exception;
/* 107:    */ }



/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.threading.AServiceThread

 * JD-Core Version:    0.7.0.1

 */
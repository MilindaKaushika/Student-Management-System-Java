/*   1:    */ package org.smslib.modem;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import java.util.regex.Matcher;
/*   6:    */ import java.util.regex.Pattern;
/*   7:    */ import org.smslib.Service;
/*   8:    */ import org.smslib.Settings;
/*   9:    */ import org.smslib.helper.Logger;
/*  10:    */ 
/*  11:    */ public class CNMIDetector
/*  12:    */ {
/*  13: 39 */   private static final Pattern splitterPattern = Pattern.compile("(\\(\\d([-,]\\d)*\\)|\\d)");
/*  14: 41 */   private String rawSentence = null;
/*  15: 43 */   private final String[] bestMode = { "3", "2" };
/*  16: 45 */   private String mode = null;
/*  17: 47 */   private final String[] bestMt = { "1" };
/*  18: 49 */   private String mt = null;
/*  19: 51 */   private final String[] bestBm = { "0" };
/*  20: 53 */   private String bm = null;
/*  21: 55 */   private final String[] bestDs = { "2" };
/*  22: 57 */   private String ds = null;
/*  23: 59 */   private final String[] bestBfr = { "0" };
/*  24: 61 */   private String bfr = null;
/*  25:    */   
/*  26:    */   public CNMIDetector(String cnmi)
/*  27:    */   {
/*  28: 71 */     this.rawSentence = cnmi;
/*  29: 72 */     detect();
/*  30:    */   }
/*  31:    */   
/*  32:    */   String[] convertRange(String myRange)
/*  33:    */   {
/*  34: 86 */     String range = myRange;
/*  35: 87 */     if (range.startsWith("(")) {
/*  36: 87 */       range = range.substring(1, range.length() - 1);
/*  37:    */     }
/*  38: 88 */     String[] retValue = null;
/*  39: 89 */     if (range.indexOf(",") != -1)
/*  40:    */     {
/*  41: 91 */       retValue = range.split(",");
/*  42:    */     }
/*  43: 93 */     else if (range.indexOf("-") != -1)
/*  44:    */     {
/*  45: 95 */       int pos = range.indexOf("-");
/*  46: 96 */       int begin = Integer.parseInt(range.substring(0, pos));
/*  47: 97 */       int end = Integer.parseInt(range.substring(pos + 1));
/*  48: 98 */       retValue = new String[end - begin + 1];
/*  49: 99 */       for (int i = 0; begin <= end; i++)
/*  50:    */       {
/*  51:101 */         retValue[i] = String.valueOf(begin);begin++;
/*  52:    */       }
/*  53:    */     }
/*  54:    */     else
/*  55:    */     {
/*  56:106 */       retValue = new String[1];
/*  57:107 */       retValue[0] = range;
/*  58:    */     }
/*  59:109 */     return retValue;
/*  60:    */   }
/*  61:    */   
/*  62:    */   String getBestMatch(String[] availableOptions, String[] searchedOptions)
/*  63:    */   {
/*  64:128 */     for (int i = 0; i < searchedOptions.length; i++)
/*  65:    */     {
/*  66:130 */       String search = searchedOptions[i];
/*  67:131 */       for (int j = 0; j < availableOptions.length; j++)
/*  68:    */       {
/*  69:133 */         String option = availableOptions[j];
/*  70:134 */         if (search.equals(option))
/*  71:    */         {
/*  72:136 */           Logger.getInstance().logDebug("CNMI: Found best match: " + search, null, null);
/*  73:137 */           return search;
/*  74:    */         }
/*  75:    */       }
/*  76:    */     }
/*  77:141 */     String bestAvailableOption = availableOptions[(availableOptions.length - 1)];
/*  78:142 */     Logger.getInstance().logInfo("CNMI: No best match, returning: " + bestAvailableOption, null, null);
/*  79:143 */     return bestAvailableOption;
/*  80:    */   }
/*  81:    */   
/*  82:    */   void detect()
/*  83:    */   {
/*  84:153 */     Matcher m = splitterPattern.matcher(this.rawSentence);
/*  85:154 */     List<String> options = new ArrayList();
/*  86:155 */     while (m.find()) {
/*  87:156 */       options.add(m.group());
/*  88:    */     }
/*  89:157 */     if (options.size() < 5) {
/*  90:157 */       throw new IllegalArgumentException("Missing parameters");
/*  91:    */     }
/*  92:158 */     this.mode = getBestMatch(convertRange((String)options.get(0)), this.bestMode);
/*  93:159 */     this.mt = getBestMatch(convertRange((String)options.get(1)), this.bestMt);
/*  94:160 */     this.bm = getBestMatch(convertRange((String)options.get(2)), this.bestBm);
/*  95:161 */     this.ds = getBestMatch(convertRange((String)options.get(3)), this.bestDs);
/*  96:162 */     this.bfr = getBestMatch(convertRange((String)options.get(4)), this.bestBfr);
/*  97:    */   }
/*  98:    */   
/*  99:    */   String getMode()
/* 100:    */   {
/* 101:167 */     if (Service.getInstance().getSettings().DISABLE_CMTI) {
/* 102:167 */       return "0";
/* 103:    */     }
/* 104:168 */     return this.mode;
/* 105:    */   }
/* 106:    */   
/* 107:    */   String getMt()
/* 108:    */   {
/* 109:173 */     return this.mt;
/* 110:    */   }
/* 111:    */   
/* 112:    */   String getBm()
/* 113:    */   {
/* 114:178 */     return this.bm;
/* 115:    */   }
/* 116:    */   
/* 117:    */   String getDs()
/* 118:    */   {
/* 119:183 */     return this.ds;
/* 120:    */   }
/* 121:    */   
/* 122:    */   String getBfr()
/* 123:    */   {
/* 124:188 */     return this.bfr;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public String toString()
/* 128:    */   {
/* 129:197 */     return "AT+CNMI=" + getMode() + "," + getMt() + "," + getBm() + "," + getDs() + "," + getBfr();
/* 130:    */   }
/* 131:    */   
/* 132:    */   public String getATCommand()
/* 133:    */   {
/* 134:205 */     return toString() + "\r";
/* 135:    */   }
/* 136:    */   
/* 137:    */   String getATCommand(String ending)
/* 138:    */   {
/* 139:213 */     return toString() + ending;
/* 140:    */   }
/* 141:    */ }


/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar
 * Qualified Name:     org.smslib.modem.CNMIDetector
 * JD-Core Version:    0.7.0.1
 */
/*   1:    */ package org.smslib.modem.athandler;
/*   2:    */
/*   3:    */ import java.io.IOException;
/*   4:    */ import java.lang.reflect.Constructor;
import org.smslib.AGateway;
/*   5:    */ import org.smslib.AGateway.AsyncEvents;
/*   6:    */ import org.smslib.GatewayException;
import org.smslib.InboundMessage;
/*   7:    */ import org.smslib.InboundMessage.MessageClasses;
/*   8:    */ import org.smslib.TimeoutException;
/*   9:    */ import org.smslib.modem.CNMIDetector;
import org.smslib.modem.CNMIDetector;
/*  10:    */ import org.smslib.modem.ModemGateway;
import org.smslib.modem.ModemGateway;
import org.smslib.modem.athandler.ATHandler;
/*  11:    */
/*  12:    */ public abstract class AATHandler /*  13:    */ {
    /*  14:    */ private ModemGateway gateway;
    /*  15:    */ private String storageLocations;
    /*  16:    */ private String description;
    /*  17:    */ String[] terminators;
    /*  18:    */ String[] unsolicitedResponses;
    /*  19:    */
    /*  20:    */ public AATHandler(ModemGateway myGateway) /*  21:    */ {
        /*  22: 46 */ this.gateway = myGateway;
        /*  23: 47 */ this.storageLocations = "";
        /*  24:    */    }
    /*  25:    */
    /*  26:    */ public ModemGateway getGateway() /*  27:    */ {
        /*  28: 52 */ return this.gateway;
        /*  29:    */    }
    /*  30:    */
    /*  31:    */ public String getDescription() /*  32:    */ {
        /*  33: 57 */ return this.description;
        /*  34:    */    }
    /*  35:    */
    /*  36:    */ public String getStorageLocations() /*  37:    */ {
        /*  38: 62 */ return this.storageLocations;
        /*  39:    */    }
    /*  40:    */
    /*  41:    */ public void setStorageLocations(String myStorageLocations) /*  42:    */ {
        /*  43: 67 */ this.storageLocations = myStorageLocations;
        /*  44:    */    }
    /*  45:    */
    /*  46:    */ public void addStorageLocation(String myStorageLocation) /*  47:    */ {
        /*  48: 72 */ this.storageLocations += myStorageLocation;
        /*  49:    */    }
    /*  50:    */
    /*  51:    */ public String[] getTerminators() /*  52:    */ {
        /*  53: 77 */ return this.terminators;
        /*  54:    */    }
    /*  55:    */
    /*  56:    */ public String[] getUnsolicitedResponses() /*  57:    */ {
        /*  58: 82 */ return this.unsolicitedResponses;
        /*  59:    */    }
    /*  60:    */
    /*  61:    */ public String getUnsolicitedResponse(int index) /*  62:    */ {
        /*  63: 87 */ return this.unsolicitedResponses[index];
        /*  64:    */    }
    /*  65:    */
    /*  66:    */ public abstract void sync()
            /*  67:    */ throws IOException, InterruptedException;
    /*  68:    */
    /*  69:    */ public abstract void reset()
            /*  70:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /*  71:    */
    /*  72:    */ public abstract void echoOff()
            /*  73:    */ throws IOException, InterruptedException;
    /*  74:    */
    /*  75:    */ public abstract void init()
            /*  76:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /*  77:    */
    /*  78:    */ public abstract void done()
            /*  79:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /*  80:    */
    /*  81:    */ public abstract boolean isAlive()
            /*  82:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /*  83:    */
    /*  84:    */ public abstract String getSimStatus()
            /*  85:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /*  86:    */
    /*  87:    */ public abstract boolean enterPin(String paramString)
            /*  88:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /*  89:    */
    /*  90:    */ public abstract boolean setVerboseErrors()
            /*  91:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /*  92:    */
    /*  93:    */ public abstract boolean setPduProtocol()
            /*  94:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /*  95:    */
    /*  96:    */ public abstract boolean setTextProtocol()
            /*  97:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /*  98:    */
    /*  99:    */ public abstract boolean setIndications()
            /* 100:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 101:    */
    /* 102:    */ public abstract CNMIDetector getIndications();
    /* 103:    */
    /* 104:    */ public abstract String getManufacturer()
            /* 105:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 106:    */
    /* 107:    */ public abstract String getModel()
            /* 108:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 109:    */
    /* 110:    */ public abstract String getSerialNo()
            /* 111:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 112:    */
    /* 113:    */ public abstract String getImsi()
            /* 114:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 115:    */
    /* 116:    */ public abstract String getSwVersion()
            /* 117:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 118:    */
    /* 119:    */ public abstract String getBatteryLevel()
            /* 120:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 121:    */
    /* 122:    */ public abstract String getSignalLevel()
            /* 123:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 124:    */
    /* 125:    */ public abstract boolean switchStorageLocation(String paramString)
            /* 126:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 127:    */
    /* 128:    */ public abstract void switchToCmdMode()
            /* 129:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 130:    */
    /* 131:    */ public abstract void keepLinkOpen()
            /* 132:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 133:    */
    /* 134:    */ public abstract int sendMessage(int paramInt, String paramString1, String paramString2, String paramString3)
            /* 135:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 136:    */
    /* 137:    */ public abstract String listMessages(InboundMessage.MessageClasses paramMessageClasses)
            /* 138:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 139:    */
    /* 140:    */ public abstract String getMessageByIndex(int paramInt)
            /* 141:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 142:    */
    /* 143:    */ public abstract boolean deleteMessage(int paramInt, String paramString)
            /* 144:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 145:    */
    /* 146:    */ public abstract String getGprsStatus()
            /* 147:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 148:    */
    /* 149:    */ public abstract String send(String paramString)
            /* 150:    */ throws TimeoutException, GatewayException, IOException, InterruptedException;
    /* 151:    */
    /* 152:    */ public abstract String getNetworkRegistration()
            /* 153:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;
    /* 154:    */
    /* 155:    */ public abstract void readStorageLocations()
            /* 156:    */ throws Exception;
    /* 157:    */
    /* 158:    */ public abstract String sendCustomATCommand(String paramString)
            /* 159:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;
    /* 160:    */
    /* 161:    */ public abstract String sendUSSDCommand(String paramString)
            /* 162:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;
    /* 163:    */
    /* 164:    */ public abstract String sendUSSDCommand(String paramString, boolean paramBoolean)
            /* 165:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;
    /* 166:    */
    /* 167:    */ public abstract boolean sendUSSDRequest(String paramString1, String paramString2, String paramString3)
            /* 168:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;
    /* 169:    */
    /* 170:    */ public abstract String formatUSSDResponse(String paramString);
    /* 171:    */
    /* 172:    */ public abstract String readPhonebookLocations()
            /* 173:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;
    /* 174:    */
    /* 175:    */ public abstract String readPhonebook(String paramString)
            /* 176:    */ throws GatewayException, TimeoutException, IOException;
    /* 177:    */
    /* 178:    */ public abstract AGateway.AsyncEvents processUnsolicitedEvents(String paramString)
            /* 179:    */ throws IOException;

    public abstract String put2GOnlyMode()
            /* 173:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;

    public abstract String put3GOnlyMode()
            /* 173:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;

    public abstract String putAnyMode()
            /* 173:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;

    public abstract String getCurrentOperatorSelection()
            /* 173:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;

    public abstract String getSignalLevelRange()
            /* 173:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;

    public abstract String getSignalToNoiseRatioRange()
            /* 173:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;

    public abstract String getSignalToNoiseRatio()
            /* 173:    */ throws GatewayException, TimeoutException, IOException, InterruptedException;

    /* 180:    */
    /* 181:    */ public static AATHandler load(ModemGateway gateway, String gsmManuf, String gsmModel)
            /* 182:    */ throws RuntimeException /* 183:    */ {
        /* 184:170 */ String BASE_HANDLER = ATHandler.class.getName();
        /* 185:171 */ String[] handlerClassNames = {null, null, BASE_HANDLER};
        /* 186:172 */ String[] handlerDescriptions = {null, null, "Generic"};
        /* 187:173 */ StringBuffer handlerClassName = new StringBuffer(BASE_HANDLER);
        /* 188:174 */ if ((gsmManuf != null) && (gsmManuf.length() != 0)) /* 189:    */ {
            /* 190:176 */ handlerClassName.append("_").append(gsmManuf);
            /* 191:177 */ handlerClassNames[1] = handlerClassName.toString();
            /* 192:178 */ handlerDescriptions[1] = (gsmManuf + " (Generic)");
            /* 193:179 */ if ((gsmModel != null) && (gsmModel.length() != 0)) /* 194:    */ {
                /* 195:181 */ handlerClassName.append("_").append(gsmModel);
                /* 196:182 */ handlerClassNames[0] = handlerClassName.toString();
                /* 197:183 */ handlerDescriptions[0] = (gsmManuf + " " + gsmModel);
                /* 198:    */            }
            /* 199:    */        }
        /* 200:186 */ AATHandler atHandler = null;
        /* 201:187 */ for (int i = 0; i < 3; i++) {
            /* 202:    */ try /* 203:    */ {
                /* 204:191 */ if (handlerClassNames[i] != null) /* 205:    */ {
                    /* 206:193 */ Class<?> handlerClass = Class.forName(handlerClassNames[i]);
                    /* 207:194 */ Constructor<?> handlerConstructor = handlerClass.getConstructor(new Class[]{ModemGateway.class});
                    /* 208:195 */ atHandler = (AATHandler) handlerConstructor.newInstance(new Object[]{gateway});
                    /* 209:196 */ atHandler.description = handlerDescriptions[i];
                    /* 210:197 */ break;
                    /* 211:    */                }
                /* 212:    */            } /* 213:    */ catch (Exception ex) /* 214:    */ {
                /* 215:202 */ if (i == 2) /* 216:    */ {
                    /* 217:204 */ ex.printStackTrace();
                    /* 218:205 */ throw new RuntimeException("Class AATHandler: Cannot initialize handler!");
                    /* 219:    */                }
                /* 220:    */            }
            /* 221:    */        }
        /* 222:209 */ return atHandler;
        /* 223:    */    }
    /* 224:    */
    /* 225:    */ public int findMatchingTerminator(String response) /* 226:    */ {
        /* 227:220 */ for (int i = 0; i < this.terminators.length; i++) {
            /* 228:222 */ if (response.matches(this.terminators[i])) {
                /* 229:222 */ return i;
                /* 230:    */            }
            /* 231:    */        }
        /* 232:224 */ return -1;
        /* 233:    */    }
    /* 234:    */
    /* 235:    */ public boolean isUnsolicitedResponse(int terminatorIndex) /* 236:    */ {
        /* 237:238 */ return terminatorIndex >= this.terminators.length - 5;
        /* 238:    */    }
    /* 239:    */
    /* 240:    */ public boolean isUnsolicitedResponse(String response) /* 241:    */ {
        /* 242:251 */ int i = findMatchingTerminator(response);
        /* 243:252 */ return isUnsolicitedResponse(i);
        /* 244:    */    }
    /* 245:    */
    /* 246:    */ public boolean matchesTerminator(String response) /* 247:    */ {
        /* 248:263 */ return findMatchingTerminator(response) >= 0;
        /* 249:    */    }
    /* 250:    */ }

/* Location:           E:\Inta Soft\De Compile\smslib-3.5.0.jar

 * Qualified Name:     org.smslib.modem.athandler.AATHandler

 * JD-Core Version:    0.7.0.1

 */

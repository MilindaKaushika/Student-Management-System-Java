// SMSLib for Java v3
// A Java API library for sending and receiving SMS via a GSM modem
// or other supported gateways.
// Web Site: http://www.smslib.org
//
// Copyright (C) 2002-2010, Thanasis Delenikas, Athens/GREECE.
// SMSLib is distributed under the terms of the Apache License version 2.0
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package org.smslib.modem;

import org.smslib.modem.athandler.AATHandler;
import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.ajwcc.pduUtils.gsm3040.Pdu;
import org.ajwcc.pduUtils.gsm3040.PduParser;
import org.ajwcc.pduUtils.gsm3040.PduUtils;
import org.ajwcc.pduUtils.gsm3040.SmsDeliveryPdu;
import org.ajwcc.pduUtils.gsm3040.SmsStatusReportPdu;
import org.smslib.AGateway;
import org.smslib.Contact;
import org.smslib.GatewayException;
import org.smslib.InboundBinaryMessage;
import org.smslib.InboundEncryptedMessage;
import org.smslib.InboundMessage;
import org.smslib.OutboundMessage;
import org.smslib.Phonebook;
import org.smslib.StatusReportMessage;
import org.smslib.TimeoutException;
import org.smslib.UnknownMessage;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.OutboundMessage.FailureCauses;
import org.smslib.OutboundMessage.MessageStatuses;
import org.smslib.Service;
import org.smslib.helper.Logger;
import org.smslib.modem.athandler.ATHandler;

/**
 * Class representing GSM modems or phones. Extends AGateway with modem specific
 * operations.
 */
public class ModemGateway extends AGateway {

    /**
     * Class representing different types of GSM modems / phones.
     */
    public enum ModemTypes {

        /**
         * Serially connected modem. These modems are connected via a serial
         * port, either physical or emulated (i.e. USB, IrDA, etc).
         */
        SERIAL,
        /**
         * IP connected modem.
         */
        IP
    }

    public enum IPProtocols {

        TEXT, BINARY
    }

    private AModemDriver driver;

    private AATHandler atHandler;

    private String modemDevice;

    private int modemParms;

    private IPProtocols ipProtocol;

    private boolean ipEncryption;

    private long lastKeepLinkOpen;

    private String manufacturer;

    private String model;

    private String simPin, simPin2;

    private String customInitString;

    private String smscNumber;

    private int outMpRefNo;

    private List<List<InboundMessage>> mpMsgList;

    public ModemGateway(ModemTypes myType, String id, String myModemDevice, int myModemParms, String myManufacturer, String myModel) {
        super(id);
        init(myType, myModemDevice, myModemParms, myManufacturer, myModel, null);
    }

    public ModemGateway(String id, String myModemDevice, int myModemParms, String myManufacturer, String myModel, AModemDriver myDriver) {
        super(id);
        init(null, myModemDevice, myModemParms, myManufacturer, myModel, myDriver);
    }

    private void init(ModemTypes myType, String myModemDevice, int myModemParms, String myManufacturer, String myModel, AModemDriver myDriver) {
        setModemDevice(myModemDevice);
        setModemParms(myModemParms);
        setIpProtocol(IPProtocols.BINARY);
        setIpEncryption(false);
        setLastKeepLinkOpen(-1);
        this.manufacturer = myManufacturer;
        this.model = myModel;
        setAttributes(AGateway.GatewayAttributes.SEND | AGateway.GatewayAttributes.RECEIVE | AGateway.GatewayAttributes.BIGMESSAGES | AGateway.GatewayAttributes.WAPSI | AGateway.GatewayAttributes.PORTADDRESSING | AGateway.GatewayAttributes.FLASHSMS | AGateway.GatewayAttributes.DELIVERYREPORTS);
        if (myDriver != null) {
            setDriver(myDriver);
        } else {
            if (myType == ModemTypes.SERIAL) {
                setDriver(new SerialModemDriver(this, getModemDevice() + ":" + getModemParms()));
            } else {
                setDriver(new IPModemDriver(this, getModemDevice() + ":" + getModemParms()));
            }
        }
        setAtHandler(AATHandler.load(this, this.manufacturer, this.model));
        setSimPin("");
        setSimPin2("");
        setSmscNumber("");
        setCustomInitString("");
        this.outMpRefNo = new Random().nextInt();
        if (this.outMpRefNo < 0) {
            this.outMpRefNo *= -1;
        }
        this.outMpRefNo %= 65536;
        this.mpMsgList = new ArrayList<List<InboundMessage>>();
    }

    public void setIpProtocol(IPProtocols myIpProtocol) {
        this.ipProtocol = myIpProtocol;
    }

    public IPProtocols getIpProtocol() {
        return this.ipProtocol;
    }

    /**
     * Set the IP modem transmission encryption status
     *
     * @param ipEncryption the status of encryption
     */
    public void setIpEncryption(boolean ipEncryption) {
        this.ipEncryption = ipEncryption;
    }

    /**
     * Check if the encryption of IP modem transmission is enabled
     *
     * @return true if IP encryption is enabled
     */
    public boolean getIpEncryption() {
        return this.ipEncryption;
    }

    @Override
    public void startGateway() throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        Logger.getInstance().logInfo("Starting gateway, using " + getATHandler().getDescription() + " AT Handler.", null, getGatewayId());
        getDriver().connect();
        super.startGateway();
        Logger.getInstance().logInfo("Gateway started.", null, getGatewayId());
    }

    @Override
    public void stopGateway() throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        Logger.getInstance().logInfo("Stopping gateway...", null, getGatewayId());
        getATHandler().done();
        super.stopGateway();
        getDriver().disconnect();
        Logger.getInstance().logInfo("Gateway stopped.", null, getGatewayId());
    }

    @Override
    public void readMessages(Collection<InboundMessage> msgList, MessageClasses msgClass) throws TimeoutException, GatewayException, IOException, InterruptedException {
        if (getStatus() != GatewayStatuses.STARTED) {
            return;
        }
        synchronized (getDriver().getSYNCCommander()) {
            if (getProtocol() == Protocols.PDU) {
                readMessagesPDU(msgList, msgClass, 0);
            } else if (getProtocol() == Protocols.TEXT) {
                readMessagesTEXT(msgList, msgClass, 0);
            }
        }
    }

    @Override
    public InboundMessage readMessage(String memLoc, int memIndex) throws TimeoutException, GatewayException, IOException, InterruptedException {
        Collection<InboundMessage> msgList;
        if (getStatus() != GatewayStatuses.STARTED) {
            return null;
        }
        synchronized (getDriver().getSYNCCommander()) {
            msgList = new ArrayList<InboundMessage>();
            readMessages(msgList, MessageClasses.ALL);
            for (InboundMessage msg : msgList) {
                if ((msg.getMemIndex() == memIndex) && (msg.getMemLocation().equals(memLoc))) {
                    return msg;
                }
            }
            return null;
        }
    }

    @Override
    public boolean sendMessage(OutboundMessage msg) throws TimeoutException, GatewayException, IOException, InterruptedException {
        boolean sendKeepLinkOpen = false;
        if (getStatus() != GatewayStatuses.STARTED) {
            return false;
        }
        if (getLastKeepLinkOpen() == -1) {
            sendKeepLinkOpen = true;
        }
        if (!sendKeepLinkOpen) {
            if ((System.currentTimeMillis() - getLastKeepLinkOpen()) > 4000) {
                sendKeepLinkOpen = true;
            }
        }
        synchronized (getDriver().getSYNCCommander()) {
            if (sendKeepLinkOpen) {
                getAtHandler().keepLinkOpen();
            }
            setLastKeepLinkOpen(System.currentTimeMillis());
            if (getProtocol() == Protocols.PDU) {
                return sendMessagePDU(msg);
            } else if (getProtocol() == Protocols.TEXT) {
                return sendMessageTEXT(msg);
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean deleteMessage(InboundMessage msg) throws TimeoutException, GatewayException, IOException, InterruptedException {
        if (getStatus() != GatewayStatuses.STARTED) {
            return false;
        }
        synchronized (getDriver().getSYNCCommander()) {
            if (msg.getMemIndex() >= 0) {
                return deleteMessage(msg.getMemIndex(), msg.getMemLocation());
            } else if ((msg.getMemIndex() == -1) && (msg.getMpMemIndex().length() != 0)) {
                StringTokenizer tokens = new StringTokenizer(msg.getMpMemIndex(), ",");
                while (tokens.hasMoreTokens()) {
                    deleteMessage(Integer.parseInt(tokens.nextToken()), msg.getMemLocation());
                }
            }
            return true;
        }
    }

    @Override
    public int readPhonebook(Phonebook phonebook) throws TimeoutException, GatewayException, IOException, InterruptedException {
        int count = 0;
        String locations, location, entries, entry;
        locations = getATHandler().readPhonebookLocations();
        if (locations.length() > 0) {
            StringTokenizer tokens = new StringTokenizer(locations, ",");
            while (tokens.hasMoreTokens()) {
                location = tokens.nextToken().replaceAll("\"", "");
                entries = getATHandler().readPhonebook(location);
                if ((entries == null) || (entries.trim().length() == 0)) {
                    continue;
                }
                BufferedReader reader = new BufferedReader(new StringReader(entries));
                entry = reader.readLine().trim();
                while (!entry.equalsIgnoreCase("OK")) {
                    entry = entry.replaceAll("\\s*\\+CPBR:\\s*", "");
                    entry = entry.replaceAll("\"\"", "\" \"");
                    entry = entry.replaceAll("\"", "");
                    StringTokenizer tokens4 = new StringTokenizer(entry, ",");
                    String index = tokens4.nextToken();
                    index = (index == null ? "" : index.trim());
                    String phone = tokens4.nextToken();
                    phone = (phone == null ? "" : phone.trim());
                    // TODO: Parse number type.
                    String type = tokens4.nextToken();
                    type = (type == null ? "" : type.trim());
                    // TODO: *end*
                    String name = tokens4.nextToken();
                    name = (name == null ? "" : name.trim());
                    phonebook.getContacts().add(new Contact(name, phone, location, Integer.parseInt(index)));
                    count++;
                    entry = reader.readLine().trim();
                }
                reader.close();
            }
        }
        return count;
    }

    private boolean deleteMessage(int memIndex, String memLocation) throws TimeoutException, GatewayException, IOException, InterruptedException {
        return getATHandler().deleteMessage(memIndex, memLocation);
    }

    private boolean sendMessageTEXT(OutboundMessage msg) throws TimeoutException, GatewayException, IOException, InterruptedException {
        int refNo;
        boolean ok = false;
        refNo = getATHandler().sendMessage(0, "", msg.getRecipient(), msg.getText());
        if (refNo >= 0) {
            msg.setGatewayId(getGatewayId());
            msg.setRefNo("" + refNo);
            msg.setDispatchDate(new Date());
            msg.setMessageStatus(MessageStatuses.SENT);
            incOutboundMessageCount();
            ok = true;
        } else {
            msg.setRefNo(null);
            msg.setDispatchDate(null);
            msg.setMessageStatus(MessageStatuses.FAILED);
        }
        return ok;
    }

    private void readMessagesTEXT(Collection<InboundMessage> msgList, MessageClasses msgClass, int myLimit) throws TimeoutException, GatewayException, IOException, InterruptedException {
        int i, j, memIndex;
        int limit = (myLimit < 0 ? 0 : myLimit);
        String response, line, tmpLine, msgText, originator, dateStr, refNo;
        BufferedReader reader;
        StringTokenizer tokens;
        InboundMessage msg;
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        for (int ml = 0; ml < (getATHandler().getStorageLocations().length() / 2); ml++) {
            if (getATHandler().switchStorageLocation(getATHandler().getStorageLocations().substring((ml * 2), (ml * 2) + 2))) {
                response = getATHandler().listMessages(msgClass);
                response = response.replaceAll("\\s+OK\\s+", "\nOK");
                reader = new BufferedReader(new StringReader(response));
                for (;;) {
                    line = reader.readLine();
                    if (line == null) {
                        break;
                    }
                    line = line.trim();
                    if (line.length() > 0) {
                        break;
                    }
                }
                while (true) {
                    if (line == null) {
                        break;
                    }
                    if (line.length() <= 0 || line.equalsIgnoreCase("OK")) {
                        break;
                    }
                    i = line.indexOf(':');
                    j = line.indexOf(',');
                    memIndex = 0;
                    try {
                        memIndex = Integer.parseInt(line.substring(i + 1, j).trim());
                    } catch (NumberFormatException e) {
                        // TODO: What to do here?
                        Logger.getInstance().logWarn("Incorrect Memory Index number parsed!", e, getGatewayId());
                    }
                    tokens = new StringTokenizer(line, ",");
                    tokens.nextToken();
                    tokens.nextToken();
                    tmpLine = "";
                    if (Character.isDigit(tokens.nextToken().trim().charAt(0))) {
                        line = line.replaceAll(",,", ", ,");
                        tokens = new StringTokenizer(line, ",");
                        tokens.nextToken();
                        tokens.nextToken();
                        tokens.nextToken();
                        refNo = tokens.nextToken();
                        tokens.nextToken();
                        dateStr = tokens.nextToken().replaceAll("\"", "");
                        cal1.set(Calendar.YEAR, 2000 + Integer.parseInt(dateStr.substring(0, 2)));
                        cal1.set(Calendar.MONTH, Integer.parseInt(dateStr.substring(3, 5)) - 1);
                        cal1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(6, 8)));
                        dateStr = tokens.nextToken().replaceAll("\"", "");
                        cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateStr.substring(0, 2)));
                        cal1.set(Calendar.MINUTE, Integer.parseInt(dateStr.substring(3, 5)));
                        cal1.set(Calendar.SECOND, Integer.parseInt(dateStr.substring(6, 8)));
                        dateStr = tokens.nextToken().replaceAll("\"", "");
                        cal2.set(Calendar.YEAR, 2000 + Integer.parseInt(dateStr.substring(0, 2)));
                        cal2.set(Calendar.MONTH, Integer.parseInt(dateStr.substring(3, 5)) - 1);
                        cal2.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(6, 8)));
                        dateStr = tokens.nextToken().replaceAll("\"", "");
                        cal2.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateStr.substring(0, 2)));
                        cal2.set(Calendar.MINUTE, Integer.parseInt(dateStr.substring(3, 5)));
                        cal2.set(Calendar.SECOND, Integer.parseInt(dateStr.substring(6, 8)));
                        msg = new StatusReportMessage(refNo, memIndex, getATHandler().getStorageLocations().substring((ml * 2), (ml * 2) + 2), cal1.getTime(), cal2.getTime());
                        msg.setGatewayId(getGatewayId());
                        Logger.getInstance().logDebug("IN-DTLS: MI:" + msg.getMemIndex(), null, getGatewayId());
                        msgList.add(msg);
                        incInboundMessageCount();
                    } else {
                        line = line.replaceAll(",,", ", ,");
                        tokens = new StringTokenizer(line, ",");
                        tokens.nextToken();
                        tokens.nextToken();
                        originator = tokens.nextToken().replaceAll("\"", "");
                        tokens.nextToken();
                        dateStr = tokens.nextToken().replaceAll("\"", "");
                        cal1.set(Calendar.YEAR, 2000 + Integer.parseInt(dateStr.substring(0, 2)));
                        cal1.set(Calendar.MONTH, Integer.parseInt(dateStr.substring(3, 5)) - 1);
                        cal1.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(6, 8)));
                        dateStr = tokens.nextToken().replaceAll("\"", "");
                        cal1.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateStr.substring(0, 2)));
                        cal1.set(Calendar.MINUTE, Integer.parseInt(dateStr.substring(3, 5)));
                        cal1.set(Calendar.SECOND, Integer.parseInt(dateStr.substring(6, 8)));

                        msgText = "";
                        while (true) {
                            tmpLine = reader.readLine();
                            if (tmpLine == null) {
                                break;
                            }
                            if (tmpLine.startsWith("+CMGL")) {
                                break;
                            }
                            msgText += (msgText.length() == 0 ? "" : "\n") + tmpLine;
                        }
                        msgText = msgText.trim();

                        msg = new InboundMessage(cal1.getTime(), originator, msgText, memIndex, getATHandler().getStorageLocations().substring((ml * 2), (ml * 2) + 2));
                        msg.setGatewayId(getGatewayId());
                        Logger.getInstance().logDebug("IN-DTLS: MI:" + msg.getMemIndex(), null, getGatewayId());
                        msgList.add(msg);
                        incInboundMessageCount();
                    }
                    while (true) {
                        //line = reader.readLine();
                        line = ((tmpLine == null || tmpLine.length() == 0) ? reader.readLine() : tmpLine);
                        if (line == null) {
                            break;
                        }
                        line = line.trim();
                        if (line.length() > 0) {
                            break;
                        }
                    }
                    if ((limit > 0) && (msgList.size() == limit)) {
                        break;
                    }
                }
                reader.close();
            }
        }
    }

    private boolean sendMessagePDU(OutboundMessage msg) throws TimeoutException, GatewayException, IOException, InterruptedException {
        boolean ok = false;
        List<String> pdus = msg.getPdus(getSmscNumber(), this.outMpRefNo);
        for (String pdu : pdus) {
            Pdu newPdu = new PduParser().parsePdu(pdu);
            Logger.getInstance().logDebug(newPdu.toString(), null, getGatewayId());
            int j = pdu.length() / 2;
            if (getSmscNumber() == null) {
                // Do nothing on purpose!
            } else if (getSmscNumber().length() == 0) {
                j--;
            } else {
                int smscNumberLen = getSmscNumber().length();
                if (getSmscNumber().charAt(0) == '+') {
                    smscNumberLen--;
                }
                if (smscNumberLen % 2 != 0) {
                    smscNumberLen++;
                }
                int smscLen = (2 + smscNumberLen) / 2;
                j = j - smscLen - 1;
            }
            Logger.getInstance().logDebug("Sending Pdu: " + pdu, null, getGatewayId());
            int refNo = getATHandler().sendMessage(j, pdu, null, null);
            if (refNo >= 0) {
                msg.setGatewayId(getGatewayId());
                msg.setRefNo(String.valueOf(refNo));
                msg.setDispatchDate(new Date());
                msg.setMessageStatus(MessageStatuses.SENT);
                msg.setFailureCause(FailureCauses.NO_ERROR);
                incOutboundMessageCount();
                ok = true;
            } else {
                msg.setRefNo(null);
                msg.setDispatchDate(null);
                msg.setMessageStatus(MessageStatuses.FAILED);
                msg.setFailureCause(FailureCauses.UNKNOWN);
                ok = false;
            }
        }
        if (pdus.size() > 1) {
            this.outMpRefNo = (this.outMpRefNo + 1) % 65536;
        }
        return ok;
    }

     private void readMessagesPDU(Collection msgList, org.smslib.InboundMessage.MessageClasses messageClass, int myLimit)
        throws TimeoutException, GatewayException, IOException, InterruptedException
    {
        int limit = myLimit >= 0 ? myLimit : 0;
        for(int ml = 0; ml < getATHandler().getStorageLocations().length() / 2; ml++)
        {
            if(!getATHandler().switchStorageLocation(getATHandler().getStorageLocations().substring(ml * 2, ml * 2 + 2)))
                continue;
            String response = getATHandler().listMessages(messageClass);
            response = response.replaceAll("\\s+OK\\s+", "\nOK");
            BufferedReader reader = new BufferedReader(new StringReader(response));
            String line;
            do
            {
                line = reader.readLine();
                if(line == null)
                    break;
                line = line.trim();
            } while(line.length() <= 0);
            PduParser parser = new PduParser();
            do
            {
                if(line == null)
                    break;
                line = line.trim();
                if(line.length() <= 0 || line.equalsIgnoreCase("OK") || line.length() <= 0 || line.equalsIgnoreCase("ERROR"))
                    break;
                int i = line.indexOf(':');
                int j = line.indexOf(',');
                int memIndex = 0;
                try
                {
                    memIndex = Integer.parseInt(line.substring(i + 1, j).trim());
                }
                catch(NumberFormatException e)
                {
                    Logger.getInstance().logWarn("Incorrect Memory Index number parsed!", e, getGatewayId());
                }
                String pduString = reader.readLine().trim();
                try
                {
                    Logger.getInstance().logDebug((new StringBuilder()).append("READ PDU: ").append(pduString).toString(), null, getGatewayId());
                    Pdu pdu = parser.parsePdu(pduString);
                    if(pdu instanceof SmsDeliveryPdu)
                    {
                        Logger.getInstance().logDebug(pdu.toString(), null, getGatewayId());
                        String memLocation = getATHandler().getStorageLocations().substring(ml * 2, ml * 2 + 2);
                        InboundMessage msg;
                        if(pdu.isBinary())
                        {
                            msg = new InboundBinaryMessage((SmsDeliveryPdu)pdu, memIndex, memLocation);
                            if(Service.getInstance().getKeyManager().getKey(msg.getOriginator()) != null)
                                msg = new InboundEncryptedMessage((SmsDeliveryPdu)pdu, memIndex, memLocation);
                        } else
                        {
                            msg = new InboundMessage((SmsDeliveryPdu)pdu, memIndex, memLocation);
                        }
                        msg.setGatewayId(getGatewayId());
                        Logger.getInstance().logDebug((new StringBuilder()).append("IN-DTLS: MI:").append(msg.getMemIndex()).append(" REF:").append(msg.getMpRefNo()).append(" MAX:").append(msg.getMpMaxNo()).append(" SEQ:").append(msg.getMpSeqNo()).toString(), null, getGatewayId());
                        if(msg.getMpRefNo() == 0)
                        {
                            msgList.add(msg);
                            incInboundMessageCount();
                        } else
                        {
                            boolean found = false;
                            int k = 0;
                            do
                            {
                                if(k >= mpMsgList.size())
                                    break;
                                List tmpList = (List)mpMsgList.get(k);
                                InboundMessage listMsg = (InboundMessage)tmpList.get(0);
                                if(listMsg.getMpRefNo() == msg.getMpRefNo())
                                {
                                    boolean duplicate = false;
                                    int l = 0;
                                    do
                                    {
                                        if(l >= tmpList.size())
                                            break;
                                        listMsg = (InboundMessage)tmpList.get(l);
                                        if(listMsg.getMpSeqNo() == msg.getMpSeqNo())
                                        {
                                            duplicate = true;
                                            break;
                                        }
                                        l++;
                                    } while(true);
                                    if(!duplicate)
                                        tmpList.add(msg);
                                    found = true;
                                    break;
                                }
                                k++;
                            } while(true);
                            if(!found)
                            {
                                List tmpList = new ArrayList();
                                tmpList.add(msg);
                                mpMsgList.add(tmpList);
                            }
                        }
                    } else
                    if(pdu instanceof SmsStatusReportPdu)
                    {
                        StatusReportMessage msg = new StatusReportMessage((SmsStatusReportPdu)pdu, memIndex, getATHandler().getStorageLocations().substring(ml * 2, ml * 2 + 2));
                        msg.setGatewayId(getGatewayId());
                        msgList.add(msg);
                        incInboundMessageCount();
                    } else
                    if(displayIllegalReceivedMessages)
                    {
                        Logger.getInstance().logError((new StringBuilder()).append("Wrong type of PDU detected: ").append(pdu.getClass().getName()).toString(), null, getGatewayId());
                        Logger.getInstance().logError((new StringBuilder()).append("ERROR PDU: ").append(pduString).toString(), null, getGatewayId());
                    }
                }
                catch(Exception e)
                {
                    UnknownMessage msg = new UnknownMessage(pduString, memIndex, getATHandler().getStorageLocations().substring(ml * 2, ml * 2 + 2));
                    msg.setGatewayId(getGatewayId());
                    msgList.add(msg);
                    incInboundMessageCount();
                    Logger.getInstance().logError("Unhandled SMS in inbox, skipping...", e, getGatewayId());
                    Logger.getInstance().logError((new StringBuilder()).append("ERROR PDU: ").append(pduString).toString(), null, getGatewayId());
                }
                do
                {
                    line = reader.readLine();
                    if(line == null)
                        break;
                    line = line.trim();
                } while(line.length() <= 0);
            } while(limit <= 0 || msgList.size() != limit);
            reader.close();
        }

        checkMpMsgList(msgList);
    }

    private boolean displayIllegalReceivedMessages;

    public void setDisplayIllegalReceivedMessages(boolean b) {
        this.displayIllegalReceivedMessages = b;
    }

    public String getMessageByIndex(int msgIndex) throws TimeoutException, GatewayException, IOException, InterruptedException {
        synchronized (getDriver().getSYNCCommander()) {
            return getATHandler().getMessageByIndex(msgIndex);
        }
    }

    private void checkMpMsgList(Collection<InboundMessage> msgList) throws TimeoutException, GatewayException, IOException, InterruptedException {
        int k, l, m;
        List<InboundMessage> tmpList;
        InboundMessage listMsg, mpMsg;
        boolean found;
        mpMsg = null;
        Logger.getInstance().logDebug("CheckMpMsgList(): MAINLIST: " + this.mpMsgList.size(), null, getGatewayId());
        for (k = 0; k < this.mpMsgList.size(); k++) {
            tmpList = this.mpMsgList.get(k);
            Logger.getInstance().logDebug("CheckMpMsgList(): SUBLIST[" + k + "]: " + tmpList.size(), null, getGatewayId());
            listMsg = tmpList.get(0);
            found = false;
            if (listMsg.getMpMaxNo() == tmpList.size()) {
                found = true;
                for (l = 0; l < tmpList.size(); l++) {
                    for (m = 0; m < tmpList.size(); m++) {
                        listMsg = tmpList.get(m);
                        if (listMsg.getMpSeqNo() == (l + 1)) {
                            if (listMsg.getMpSeqNo() == 1) {
                                mpMsg = listMsg;
                                mpMsg.setMpMemIndex(mpMsg.getMemIndex());
                            } else {
                                if (mpMsg != null) {
                                    if (mpMsg instanceof InboundBinaryMessage) {
                                        InboundBinaryMessage mpMsgBinary = (InboundBinaryMessage) mpMsg;
                                        InboundBinaryMessage listMsgBinary = (InboundBinaryMessage) listMsg;
                                        mpMsgBinary.addDataBytes(listMsgBinary.getDataBytes());
                                    } else {
                                        // NEW
                                        String textToAdd = listMsg.getText();
                                        if (mpMsg.getEndsWithMultiChar()) {
                                            // adjust first char of textToAdd
                                            Logger.getInstance().logDebug("Adjusting dangling multi-char: " + textToAdd.charAt(0) + " --> " + PduUtils.getMultiCharFor(textToAdd.charAt(0)), null, getGatewayId());
                                            textToAdd = PduUtils.getMultiCharFor(textToAdd.charAt(0)) + textToAdd.substring(1);
                                        }
                                        mpMsg.setEndsWithMultiChar(listMsg.getEndsWithMultiChar());
                                        try {
                                            mpMsg.addText(textToAdd);
                                        } catch (UnsupportedEncodingException e) {
                                            // TODO: What to do with this?
                                        }
                                    }
                                    mpMsg.setMpSeqNo(listMsg.getMpSeqNo());
                                    mpMsg.setMpMemIndex(listMsg.getMemIndex());
                                    if (listMsg.getMpSeqNo() == listMsg.getMpMaxNo()) {
                                        mpMsg.setMemIndex(-1);
                                        msgList.add(mpMsg);
                                        incInboundMessageCount();
                                        mpMsg = null;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                tmpList.clear();
                tmpList = null;
            }
            if (found) {
                this.mpMsgList.remove(k);
                k--;
            }
        }
        // Check the remaining parts for "orphaned" status
        for (Iterator i$ = mpMsgList.iterator(); i$.hasNext();) {
            List remainingList = (List) i$.next();
            i$ = remainingList.iterator();
            while (i$.hasNext()) {
                InboundMessage msg = (InboundMessage) i$.next();
                if (getAgeInHours(msg.getDate()) > Service.getInstance().getSettings().HOURS_TO_ORPHAN && Service.getInstance().getOrphanedMessageNotification() != null && Service.getInstance().getOrphanedMessageNotification().process(Service.getInstance().getGateway(msg.getGatewayId()), msg)) {
                    deleteMessage(msg);
                }
            }
        }

    }

    private long getLastKeepLinkOpen() {
        return this.lastKeepLinkOpen;
    }

    private void setLastKeepLinkOpen(long lastKeepLinkOpen) {
        this.lastKeepLinkOpen = lastKeepLinkOpen;
    }

    /**
     * Sets the SIM PIN.
     *
     * @param mySimPin The SIM PIN.
     */
    public void setSimPin(String mySimPin) {
        this.simPin = mySimPin;
    }

    /**
     * Sets the SIM PIN 2.
     *
     * @param mySimPin2 The SIM PIN 2.
     */
    public void setSimPin2(String mySimPin2) {
        this.simPin2 = mySimPin2;
    }

    /**
     * Returns the SIM PIN.
     *
     * @return The SIM PIN.
     */
    public String getSimPin() {
        return this.simPin;
    }

    /**
     * Returns the SIM PIN 2.
     *
     * @return The SIM PIN 2.
     */
    public String getSimPin2() {
        return this.simPin2;
    }

    public AModemDriver getModemDriver() {
        return this.driver;
    }

    public AATHandler getATHandler() {
        return this.atHandler;
    }

    /**
     * Returns the Manufacturer string of the modem or phone.
     *
     * @return The Manufacturer string.
     * @throws TimeoutException The gateway did not respond in a timely manner.
     * @throws GatewayException A Gateway error occurred.
     * @throws IOException An IO error occurred.
     * @throws InterruptedException The call was interrupted.
     */
    public String getManufacturer() throws TimeoutException, GatewayException, IOException, InterruptedException {
        String response;
        synchronized (getDriver().getSYNCCommander()) {
            response = getATHandler().getManufacturer();
            if (response.indexOf("ERROR") >= 0) {
                return "N/A";
            }
            response = response.replaceAll("\\s+OK\\s+", "");
            return response;
        }
    }

    /**
     * Returns the Model string.
     *
     * @return The Model string.
     * @throws TimeoutException The gateway did not respond in a timely manner.
     * @throws GatewayException A Gateway error occurred.
     * @throws IOException An IO error occurred.
     * @throws InterruptedException The call was interrupted.
     */
    public String getModel() throws TimeoutException, GatewayException, IOException, InterruptedException {
        String response;
        synchronized (getDriver().getSYNCCommander()) {
            response = getATHandler().getModel();
            if (response.indexOf("ERROR") >= 0) {
                return "N/A";
            }
            response = response.replaceAll("\\s+OK\\s+", "");
            return response;
        }
    }

    /**
     * Returns the Serial Number of the modem.
     *
     * @return The Serial Number.
     * @throws TimeoutException The gateway did not respond in a timely manner.
     * @throws GatewayException A Gateway error occurred.
     * @throws IOException An IO error occurred.
     * @throws InterruptedException The call was interrupted.
     */
    public String getSerialNo() throws TimeoutException, GatewayException, IOException, InterruptedException {
        String response;
        synchronized (getDriver().getSYNCCommander()) {
            response = getATHandler().getSerialNo();
            if (response.indexOf("ERROR") >= 0) {
                return "N/A";
            }
            response = response.replaceAll("\\s+OK\\s+", "");
            return response;
        }
    }

    /**
     * Returns the IMSI (International Mobile Subscriber Identity) number.
     * <p>
     * This number is stored in the SIM. Since this number may be used for
     * several illegal activities, the method is remarked. If you wish to see
     * your IMSI, just uncomment the method.
     *
     * @return The IMSI.
     * @throws TimeoutException The gateway did not respond in a timely manner.
     * @throws GatewayException A Gateway error occurred.
     * @throws IOException An IO error occurred.
     * @throws InterruptedException The call was interrupted.
     */
    public String getImsi() throws TimeoutException, GatewayException, IOException, InterruptedException {
        if (Service.getInstance().getSettings().MASK_IMSI) {
            return "** MASKED **";
        }
        synchronized (getDriver().getSYNCCommander()) {
            String response;
            response = getATHandler().getImsi();
            if (response.indexOf("ERROR") >= 0) {
                return "N/A";
            }
            response = response.replaceAll("\\s+OK\\s+", "");
            return response;
        }
    }

    /**
     * Returns the modem's firmware version.
     *
     * @return The modem's firmware version.
     * @throws TimeoutException The gateway did not respond in a timely manner.
     * @throws GatewayException A Gateway error occurred.
     * @throws IOException An IO error occurred.
     * @throws InterruptedException The call was interrupted.
     */
    public String getSwVersion() throws TimeoutException, GatewayException, IOException, InterruptedException {
        String response;
        synchronized (getDriver().getSYNCCommander()) {
            response = getATHandler().getSwVersion();
            if (response.indexOf("ERROR") >= 0) {
                return "N/A";
            }
            response = response.replaceAll("\\s+OK\\s+", "");
            return response;
        }
    }

    boolean getGprsStatus() throws TimeoutException, GatewayException, IOException, InterruptedException {
        synchronized (getDriver().getSYNCCommander()) {
            return (getATHandler().getGprsStatus().matches("\\+CGATT[\\p{ASCII}]*1\\sOK\\s"));
        }
    }

    /**
     * Returns the battery level (0-100).
     *
     * @return The battery level.
     * @throws TimeoutException The gateway did not respond in a timely manner.
     * @throws GatewayException A Gateway error occurred.
     * @throws IOException An IO error occurred.
     * @throws InterruptedException The call was interrupted.
     */
    public int getBatteryLevel() throws TimeoutException, GatewayException, IOException, InterruptedException {
        String response;
        synchronized (getDriver().getSYNCCommander()) {
            response = getATHandler().getBatteryLevel();
            if (response.indexOf("ERROR") >= 0) {
                return 0;
            }
            Matcher m = Pattern.compile("\\+CBC: (\\d+),\\s*(\\d+)").matcher(response);
            if (m.find()) {
                return Integer.parseInt(m.group(2));
            }
            return 0;
        }
    }

    /**
     * Returns one of the following values: 0, [-113,-51], 99 to represent the
     * following signal levels:
     *
     * 0 = An error message from the modem -113 to -51 = Received Signal
     * Strength Indicator (RSSI) expressed in dBm, from -113 dB to -51 dBm in
     * steps of 2 dBm 99 = RSSI not known or not detectable
     *
     * Reference: 3GPP TS 07.07 (ETSI 100 916) v7.8.0, Sec. 8.5, p63
     *
     * @return The signal level.
     * @throws TimeoutException The gateway did not respond in a timely manner.
     * @throws GatewayException A Gateway error occurred.
     * @throws IOException An IO error occurred.
     * @throws InterruptedException The call was interrupted.
     */
    public int getSignalLevel() throws TimeoutException, GatewayException, IOException, InterruptedException {
        String response;
        StringTokenizer tokens;
        Integer rssi_code;
        synchronized (getDriver().getSYNCCommander()) {
            response = getATHandler().getSignalLevel();
            if (response.indexOf("ERROR") >= 0) {
                return 0;
            }
            response = response.replaceAll("\\s+OK\\s+", "");
            tokens = new StringTokenizer(response, ":,");
            tokens.nextToken();
            rssi_code = Integer.parseInt(tokens.nextToken().trim());
            if (rssi_code == 99) {
                return 99;
            } else {
                return (-113 + 2 * rssi_code);
            }
        }
    }

    public String put2GOnlyMode() throws TimeoutException, GatewayException, IOException, InterruptedException {
        String response = "";
        synchronized (getDriver().getSYNCCommander()) {
            response += getATHandler().put2GOnlyMode();
            return response;
        }
    }

    public String put3GOnlyMode() throws TimeoutException, GatewayException, IOException, InterruptedException {
        String response = "";
        synchronized (getDriver().getSYNCCommander()) {
            response += getATHandler().put3GOnlyMode();
            return response;
        }
    }

    public Boolean isIn3GOnlyMode(String response) throws TimeoutException, GatewayException, IOException, InterruptedException {
        char act;
        if (response.indexOf("ERROR") >= 0) {
            return false;
        } else {
            // For some reason there are two spaces left at the end of response
            // even though spaces should have been replaced by empty strings
            // in getCurrentOperatorSelection
            act = response.substring(response.length() - 3, response.length() - 2).charAt(0);
            switch (act) {
                case '0':
                    return false;
                case '1':
                    return false;
                case '2':
                    return true;
                case '3':
                    return false;
                case '4':
                    return true;
                case '5':
                    return true;
                case '6':
                    return true;
            }
        }
        return false;
    }

    public String putAnyMode() throws TimeoutException, GatewayException, IOException, InterruptedException {
        String response = "";
        synchronized (getDriver().getSYNCCommander()) {
            response += getATHandler().putAnyMode();
            return response;
        }
    }

    public String getCurrentOperatorSelection() throws TimeoutException, GatewayException, IOException, InterruptedException {
        String response;
        CharSequence cs1 = "+COPS:";
        CharSequence cs2 = "OK";
        CharSequence cs3 = " ";
        synchronized (getDriver().getSYNCCommander()) {
            response = getATHandler().getCurrentOperatorSelection();
            if (response.indexOf("ERROR") >= 0) {
                return response;
            } else {
                response = response.replace(cs1, "");
                response = response.replace(cs2, "");
                response = response.replace(cs3, "");
                return response;
            }
        }
    }

    public String getSignalLevelRange() throws TimeoutException, GatewayException, IOException, InterruptedException {
        // Response format: +CSQ: (0-31,99),(99)OK
        String response = "";
        CharSequence cs1 = "+CSQ: ";
        CharSequence cs2 = "OK";
        synchronized (getDriver().getSYNCCommander()) {
            response = getATHandler().getSignalLevelRange();
            response = response.replace(cs1, "");
            response = response.replace(cs2, "");
            return response;
        }
    }

    public String[] getRSSIandBER() throws TimeoutException, GatewayException, IOException, InterruptedException {
        // Response format: +CSQ: 6,99OK
        String res = "";
        String[] response = {"", ""};
        StringTokenizer tokens;
        Integer rssi_code, ber_code;
        CharSequence cs1 = "+CSQ: ";
        CharSequence cs2 = "OK";
        synchronized (getDriver().getSYNCCommander()) {
            res = getATHandler().getSignalLevel();
            if (res.indexOf("ERROR") >= 0) {
                response[0] = res;
                response[1] = res;
                return response;
            } else {
                res = res.replaceAll("\\s+OK\\s+", "");
                res = res.replace(cs1, "");
                res = res.replace(cs2, "");
                tokens = new StringTokenizer(res, ",");
                rssi_code = Integer.parseInt(tokens.nextToken().trim());
                if (rssi_code == 99) {
                    response[0] += "99";
                } else {
                    rssi_code = (-113 + 2 * rssi_code);
                    response[0] = rssi_code.toString();
                }
                ber_code = Integer.parseInt(tokens.nextToken().trim());
                response[1] = ber_code.toString();
                return response;
            }
        }
    }

    public String getSignalToNoiseRatioRange() throws TimeoutException, GatewayException, IOException, InterruptedException {
        // Response format: ^CSNR:(-145--60),(-32-0)OK
        String response = "";
        CharSequence cs1 = "^CSNR:";
        CharSequence cs2 = "OK";
        synchronized (getDriver().getSYNCCommander()) {
            response = getATHandler().getSignalToNoiseRatioRange();
            response = response.replace(cs1, "");
            response = response.replace(cs2, "");
            return response;
        }
    }

    public String[] getSignalToNoiseRatio() throws TimeoutException, GatewayException, IOException, InterruptedException {
        // Response format: ^CSNR:-101,-9OK
        String response;
        String[] SNR = new String[2];
        StringTokenizer tokens;
        CharSequence cs1 = "OK";
        CharSequence cs2 = "^CSNR:";
        synchronized (getDriver().getSYNCCommander()) {
            response = getATHandler().getSignalToNoiseRatio();
            if (response.indexOf("ERROR") >= 0) {
                SNR[0] = response;
                SNR[1] = response;
                return SNR;
            } else {
                response = response.replace(cs1, "");
                response = response.replace(cs2, "");
                tokens = new StringTokenizer(response, ",");
                SNR[0] = tokens.nextToken();
                SNR[1] = tokens.nextToken();
                return SNR;
            }
        }
    }

    /**
     * Returns the SMSC number used by SMSLib. If no SMSC number has been set
     * with setSmscNumber() call, this method returns nothing.
     *
     * @return The SMSC number.
     * @see #setSmscNumber(String)
     */
    public String getSmscNumber() {
        return this.smscNumber;
    }

    /**
     * Sets the SMSC number used by SMSLib.
     * <p>
     * Note that in most cases, you will <b>not</b> need to call this method, as
     * the modem knows the SMSC it should use by reading the SIM card. In rare
     * cases when the modem/phone cannot read the SMSC from the SIM card or you
     * would like to set a different SMSC than the default, you can use this
     * method.
     *
     * @param mySmscNumber The SMSC number used from now on.
     */
    public void setSmscNumber(String mySmscNumber) {
        this.smscNumber = mySmscNumber;
    }

    /**
     * Returns the custom modem init string (if any).
     *
     * @return the custom init string.
     * @see #setCustomInitString(String)
     */
    public String getCustomInitString() {
        return this.customInitString;
    }

    /**
     * Sets the custom modem init string. The init string (if defined) is sent
     * to the modem right before the SIM PIN check.
     *
     * @param myCustomInitString The custom init string.
     * @see #getCustomInitString()
     */
    public void setCustomInitString(String myCustomInitString) {
        this.customInitString = myCustomInitString;
    }

    /**
     * Send a custom AT command to the modem and returns the response received.
     *
     * @param atCommand The AT Command.
     * @return The modem's response.
     * @throws TimeoutException The gateway did not respond in a timely manner.
     * @throws GatewayException A Gateway error occurred.
     * @throws IOException An IO error occurred.
     * @throws InterruptedException The call was interrupted.
     */
    public String sendCustomATCommand(String atCommand) throws GatewayException, TimeoutException, IOException, InterruptedException {
        synchronized (getDriver().getSYNCCommander()) {
            return getATHandler().sendCustomATCommand(atCommand);
        }
    }

    /**
     * Send an Unstructured Supplementary Service Data command
     *
     * @param ussdCommand the USSD command to send
     * @return the network's response to the command
     * @throws GatewayException
     * @throws TimeoutException
     * @throws IOException
     * @throws InterruptedException
     */
    public String sendUSSDCommand(String ussdCommand) throws GatewayException, TimeoutException, IOException, InterruptedException {
        synchronized (getDriver().getSYNCCommander()) {
            return getATHandler().sendUSSDCommand(ussdCommand);
        }
    }

    protected AModemDriver getDriver() {
        return this.driver;
    }

    protected void setDriver(AModemDriver myDriver) {
        this.driver = myDriver;
    }

    protected AATHandler getAtHandler() {
        return this.atHandler;
    }

    protected void setAtHandler(AATHandler myAtHandler) {
        this.atHandler = myAtHandler;
    }

    protected String getModemDevice() {
        return this.modemDevice;
    }

    protected void setModemDevice(String myModemDevice) {
        this.modemDevice = myModemDevice;
    }

    protected int getModemParms() {
        return this.modemParms;
    }

    protected void setModemParms(int myModemParms) {
        this.modemParms = myModemParms;
    }

    protected int getAgeInHours(Date messageDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        long now = cal.getTimeInMillis();
        cal.setTime(messageDate);
        long past = cal.getTimeInMillis();
        return (int) ((now - past) / (60 * 60 * 1000));
    }

    @Override
    public int getQueueSchedulingInterval() {
        return 5000;
    }
}

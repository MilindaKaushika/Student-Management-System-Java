package org.smslib.modem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import org.smslib.AGateway;
import org.smslib.GatewayException;
import org.smslib.InboundMessage;
import org.smslib.InvalidMessageException;
import org.smslib.Message;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.USSDResponse;
import org.smslib.helper.Logger;
import static org.smslib.modem.AModemDriver._cls1.$SwitchMap$org$smslib$Message$MessageTypes;
import org.smslib.modem.athandler.ATHandler;
import org.smslib.notify.CallNotification;
import org.smslib.notify.InboundMessageNotification;
import org.smslib.threading.AServiceThread;

public abstract class AModemDriver {

    static class _cls1 {

        static final int $SwitchMap$org$smslib$Message$MessageTypes[];

        static {
            $SwitchMap$org$smslib$Message$MessageTypes = new int[org.smslib.Message.MessageTypes.values().length];
            try {
                $SwitchMap$org$smslib$Message$MessageTypes[org.smslib.Message.MessageTypes.INBOUND.ordinal()] = 1;
            } catch (NoSuchFieldError ex) {
            }
            try {
                $SwitchMap$org$smslib$Message$MessageTypes[org.smslib.Message.MessageTypes.STATUSREPORT.ordinal()] = 2;
            } catch (NoSuchFieldError ex) {
            }
        }
    }
    AModemDriver modemDriver;

    public AModemDriver getModemDriver() {
        return this.modemDriver;
    }

    private static final String rxErrorWithCode = "\\s*[\\p{ASCII}]*\\s*\\+(CM[ES])\\s+ERROR: (\\d+)\\s";
    private static final String rxPlainError = "\\s*[\\p{ASCII}]*\\s*(ERROR|NO CARRIER|NO DIALTONE)\\s";
    private Object SYNC_Reader;
    private Object SYNC_Commander;
    private Object SYNC_InboundReader;
    private ModemGateway gateway;
    private boolean dataReceived;
    private volatile boolean connected;
    private CharQueue charQueue;
    private ModemReader modemReader;
    private KeepAlive keepAlive;
    private AsyncNotifier asyncNotifier;
    private AsyncMessageProcessor asyncMessageProcessor;
    private CNMIEmulatorProcessor cnmiEmulationProcessor;
    private int lastError;
    static int OK = 0;

    protected AModemDriver(ModemGateway myGateway, String deviceParms) {
        setSYNCReader(new Object());
        setSYNCCommander(new Object());
        setSYNCInboundReader(new Object());
        setGateway(myGateway);
        setConnected(false);
        setDataReceived(false);
        setCharQueue(new CharQueue());
    }

    protected abstract void connectPort()
            throws GatewayException, IOException, InterruptedException;

    protected abstract void disconnectPort()
            throws IOException, InterruptedException;

    protected abstract void clear()
            throws IOException;

    protected void connect()
            throws TimeoutException, GatewayException, IOException, InterruptedException {
        synchronized (getSYNCCommander()) {
            try {
                connectPort();
                setConnected(true);
                setKeepAlive(new KeepAlive("KeepAlive [" + getGateway().getGatewayId() + "]", Service.getInstance().getSettings().SERIAL_KEEPALIVE_INTERVAL * 1000));
                setCnmiEmulationProcessor(new CNMIEmulatorProcessor("CNMIEmulatorProcessor [" + getGateway().getGatewayId() + "]", Service.getInstance().getSettings().CNMI_EMULATOR_INTERVAL * 1000));
                setModemReader(new ModemReader());
                setAsyncNotifier(new AsyncNotifier());
                setAsyncMessageProcessor(new AsyncMessageProcessor());
                clearBuffer();
                getGateway().getATHandler().reset();
                getGateway().getATHandler().sync();
                getGateway().getATHandler().echoOff();
                if ((getGateway().getCustomInitString() != null) && (getGateway().getCustomInitString().length() > 0)) {
                    write(getGateway().getCustomInitString() + "\r");
                    getGateway().getATHandler().echoOff();
                }
                for (;;) {
                    String response = getGateway().getATHandler().getSimStatus();
                    while (response.indexOf("BUSY") >= 0) {
                        Logger.getInstance().logDebug("SIM found busy, waiting...", null, getGateway().getGatewayId());
                        Thread.sleep(Service.getInstance().getSettings().AT_WAIT_SIMPIN);
                        response = getGateway().getATHandler().getSimStatus();
                    }
                    if (response.indexOf("SIM PIN2") >= 0) {
                        Logger.getInstance().logDebug("SIM requesting PIN2.", null, getGateway().getGatewayId());
                        if ((getGateway().getSimPin2() == null) || (getGateway().getSimPin2().length() == 0)) {
                            throw new GatewayException("The GSM modem requires SIM PIN2 to operate.");
                        }
                        if (!getGateway().getATHandler().enterPin(getGateway().getSimPin2())) {
                            throw new GatewayException("SIM PIN2 provided is not accepted by the GSM modem.");
                        }
                        Thread.sleep(Service.getInstance().getSettings().AT_WAIT_SIMPIN);
                    } else if (response.indexOf("SIM PIN") >= 0) {
                        Logger.getInstance().logDebug("SIM requesting PIN.", null, getGateway().getGatewayId());
                        if ((getGateway().getSimPin() == null) || (getGateway().getSimPin().length() == 0)) {
                            throw new GatewayException("The GSM modem requires SIM PIN to operate.");
                        }
                        if (!getGateway().getATHandler().enterPin(getGateway().getSimPin())) {
                            throw new GatewayException("SIM PIN provided is not accepted by the GSM modem.");
                        }
                        Thread.sleep(Service.getInstance().getSettings().AT_WAIT_SIMPIN);
                    } else {
                        if ((response.indexOf("READY") >= 0)
                                || (response.indexOf("OK") >= 0)) {
                            break;
                        }
                        if (response.indexOf("ERROR") >= 0) {
                            Logger.getInstance().logWarn("Erroneous CPIN response, proceeding with defaults.", null, getGateway().getGatewayId());
                            break;
                        }
                        Logger.getInstance().logWarn("Cannot understand SIMPIN response: " + response + ", will wait for a while...", null, getGateway().getGatewayId());
                        Thread.sleep(Service.getInstance().getSettings().AT_WAIT_SIMPIN);
                    }
                }
                getGateway().getATHandler().echoOff();
                getGateway().getATHandler().init();
                getGateway().getATHandler().echoOff();
                if (!waitForNetworkRegistration()) {
                    Logger.getInstance().logWarn("Network Registration failed, proceeding with defaults.", null, getGateway().getGatewayId());
                }
                getGateway().getATHandler().setVerboseErrors();
                if (getGateway().getATHandler().getStorageLocations().length() == 0) {
                    try {
                        getGateway().getATHandler().readStorageLocations();
                        Logger.getInstance().logInfo("MEM: Storage Locations Found: " + getGateway().getATHandler().getStorageLocations(), null, getGateway().getGatewayId());
                    } catch (Exception e) {
                        getGateway().getATHandler().setStorageLocations("--");
                        Logger.getInstance().logWarn("Storage locations could *not* be retrieved, will proceed with defaults.", e, getGateway().getGatewayId());
                    }
                }
                if (!getGateway().getATHandler().setIndications()) {
                    Logger.getInstance().logWarn("Callback indications were *not* set succesfully!", null, getGateway().getGatewayId());
                    getCnmiEmulationProcessor().enable();
                } else if (getGateway().getATHandler().getIndications().getMode().equals("0")) {
                    getCnmiEmulationProcessor().enable();
                }
                if (getGateway().getProtocol() == AGateway.Protocols.PDU) {
                    if (!getGateway().getATHandler().setPduProtocol()) {
                        throw new GatewayException("The GSM modem does not support the PDU protocol.");
                    }
                } else if (getGateway().getProtocol() == AGateway.Protocols.TEXT) {
                    if (!getGateway().getATHandler().setTextProtocol()) {
                        throw new GatewayException("The GSM modem does not support the TEXT protocol.");
                    }
                }
            } catch (TimeoutException t) {
                try {
                    disconnect();
                } catch (Exception e) {
                }
                throw t;
            } catch (GatewayException t) {
                try {
                    disconnect();
                } catch (Exception e) {
                }
                throw t;
            } catch (IOException t) {
                try {
                    disconnect();
                } catch (Exception e) {
                }
                throw t;
            } catch (InterruptedException t) {
                try {
                    disconnect();
                } catch (Exception e) {
                }
                throw t;
            }
        }
    }

    protected void disconnect()
            throws IOException, InterruptedException {
        setConnected(false);
        if (getKeepAlive() != null) {
            getKeepAlive().cancel();
            setKeepAlive(null);
        }
        if (getCnmiEmulationProcessor() != null) {
            getCnmiEmulationProcessor().cancel();
            setCnmiEmulationProcessor(null);
        }
        if (getAsyncNotifier() != null) {
            getAsyncNotifier().interrupt();
            getAsyncNotifier().join();
            setAsyncNotifier(null);
        }
        if (getAsyncMessageProcessor() != null) {
            getAsyncMessageProcessor().interrupt();
            getAsyncMessageProcessor().join();
            setAsyncMessageProcessor(null);
        }
        if (getModemReader() != null) {
            getModemReader().interrupt();
            getModemReader().join();
            setModemReader(null);
        }
        disconnectPort();
    }

    public abstract void write(char paramChar)
            throws IOException;

    public abstract void write(byte[] paramArrayOfByte)
            throws IOException;

    protected abstract int read()
            throws IOException;

    protected abstract boolean portHasData()
            throws IOException;

    public boolean dataAvailable()
            throws InterruptedException {
        return getCharQueue().peek() != -1;
    }

    public void write(String s)
            throws IOException {
        System.out.println("SEND :" + formatLog(s));
        write(s.getBytes());
    }

    public void addToQueue(String s) {
        for (int i = 0; i < s.length(); i++) {
            getCharQueue().put((byte) s.charAt(i));
        }
    }

    public String getResponse()
            throws GatewayException, TimeoutException, IOException, InterruptedException {
        return getResponse(AGateway.AsyncEvents.NOTHING);
    }

    public String getResponse(AGateway.AsyncEvents eventResponse)
            throws GatewayException, TimeoutException, IOException, InterruptedException {
        setLastError(-1);
        StringBuffer buffer = new StringBuffer(Service.getInstance().getSettings().SERIAL_BUFFER_SIZE);
        try {
            String response;
            for (;;) {
                if ((getCharQueue().peek() == 10) || (getCharQueue().peek() == 13)) {
                    getCharQueue().get();
                } else {
                    for (;;) {
                        byte c = getCharQueue().get();
                        if (System.getProperty("smslib.dumpqueues") != null) {
                            Logger.getInstance().logDebug("OUT READER QUEUE : " + c + " / " + (char) c, null, getGateway().getGatewayId());
                        }
                        if (c == 10) {
                            break;
                        }
                        buffer.append((char) c);
                    }
                    if (buffer.charAt(buffer.length() - 1) != '\r') {
                        buffer.append('\r');
                    }
                    response = buffer.toString();
                    if (getGateway().getATHandler().matchesTerminator(response)) {
                        break;
                    }
                }
            }
            System.out.println("BUFFER: " + buffer);
            if (getGateway().getATHandler().isUnsolicitedResponse(buffer.toString())) {
                AGateway.AsyncEvents event = getGateway().getATHandler().processUnsolicitedEvents(buffer.toString());
                if ((event == eventResponse) && (eventResponse != AGateway.AsyncEvents.NOTHING)) {
                    return buffer.toString();
                }
                if ((event == AGateway.AsyncEvents.INBOUNDMESSAGE) || (event == AGateway.AsyncEvents.INBOUNDSTATUSREPORTMESSAGE) || (event == AGateway.AsyncEvents.INBOUNDCALL) || (event == AGateway.AsyncEvents.USSDRESPONSE)) {
                    getAsyncNotifier().setEvent(event, buffer.toString());
                }
                return getResponse();
            }
            if (response.matches("\\s*[\\p{ASCII}]*\\s*\\+(CM[ES])\\s+ERROR: (\\d+)\\s")) {
                Pattern p = Pattern.compile("\\s*[\\p{ASCII}]*\\s*\\+(CM[ES])\\s+ERROR: (\\d+)\\s");
                Matcher m = p.matcher(response);
                if (m.find()) {
                    try {
                        if (m.group(1).equals("CME")) {
                            int code = Integer.parseInt(m.group(2));
                            setLastError(5000 + code);
                        } else if (m.group(1).equals("CMS")) {
                            int code = Integer.parseInt(m.group(2));
                            setLastError(6000 + code);
                        } else {
                            throw new GatewayException("Invalid error response: " + m.group(1));
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error on number conversion while interpreting response: ");
                        throw new GatewayException("Cannot convert error code number.");
                    }
                } else {
                    throw new GatewayException("Cannot match error code. Should never happen!");
                }
            } else if (response.matches("\\s*[\\p{ASCII}]*\\s*(ERROR|NO CARRIER|NO DIALTONE)\\s")) {
                setLastError(9000);
            } else if (response.indexOf("OK") >= 0) {
                setLastError(0);
            } else {
                setLastError(10000);
            }
            System.out.println("RECV :" + formatLog(buffer.toString()));
        } catch (InterruptedException e) {
            System.out.println("GetResponse() Interrupted.");
            e.printStackTrace();
            throw e;
        } catch (TimeoutException e) {
            System.out.println("Buffer contents on timeout: " + buffer);
            throw e;
        }
        return buffer.toString();
    }

    public void clearBuffer()
            throws IOException, InterruptedException {
        synchronized (getSYNCCommander()) {
            Logger.getInstance().logDebug("clearBuffer() called.", null, getGateway().getGatewayId());
            Thread.sleep(Service.getInstance().getSettings().SERIAL_CLEAR_WAIT);
            clear();
            getCharQueue().clear();
        }
    }

    protected boolean waitForNetworkRegistration()
            throws GatewayException, TimeoutException, IOException, InterruptedException {
        int retries = 0;
        for (;;) {
            String response = getGateway().getATHandler().getNetworkRegistration();
            if (response.indexOf("ERROR") >= 0) {
                return false;
            }
            response = response.replaceAll("\\s+OK\\s+", "");
            response = response.replaceAll("\\s+", "");
            response = response.replaceAll("\\+CREG:", "");
            StringTokenizer tokens = new StringTokenizer(response, ",");
            tokens.nextToken();
            int answer;
            try {
                answer = Integer.parseInt(tokens.nextToken());
            } catch (Exception e) {
                answer = -1;
            }
            switch (answer) {
                case 0:
                    Logger.getInstance().logError("GSM: Auto-registration disabled!", null, getGateway().getGatewayId());
                    throw new GatewayException("GSM Network Auto-Registration disabled!");
                case 1:
                    Logger.getInstance().logInfo("GSM: Registered to home network.", null, getGateway().getGatewayId());
                    return true;
                case 2:
                    Logger.getInstance().logWarn("GSM: Not registered, searching for network...", null, getGateway().getGatewayId());
                    retries++;
                    if (retries == 6) {
                        throw new GatewayException("GSM Network Registration failed, give up trying!");
                    }
                    break;
                case 3:
                    Logger.getInstance().logError("GSM: Network registration denied!", null, getGateway().getGatewayId());
                    throw new GatewayException("GSM Network Registration denied!");
                case 4:
                    Logger.getInstance().logError("GSM: Unknown registration error!", null, getGateway().getGatewayId());
                    throw new GatewayException("GSM Network Registration error!");
                case 5:
                    Logger.getInstance().logInfo("GSM: Registered to foreign network (roaming).", null, getGateway().getGatewayId());
                    return true;
                case -1:
                    Logger.getInstance().logInfo("GSM: Invalid CREG response.", null, getGateway().getGatewayId());
                    throw new GatewayException("GSM: Invalid CREG response.");
            }
            Thread.sleep(Service.getInstance().getSettings().AT_WAIT_NETWORK);
        }
    }

    private String formatLog(String s) {
        StringBuffer response = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\r':
                    response.append("(cr)");
                    break;
                case '\n':
                    response.append("(lf)");
                    break;
                case '\t':
                    response.append("(tab)");
                    break;
                case '\013':
                case '\f':
                default:
                    if ((c >= ' ') && (c < '')) {
                        response.append(c);
                    } else {
                        response.append("(" + c + ")");
                    }
                    break;
            }
        }
        return response.toString();
    }

    private class CharQueue {

        byte[] buffer;
        int bufferStart;
        int bufferEnd;

        public CharQueue() {
            this.buffer = null;
            this.bufferStart = 0;
            this.bufferEnd = 0;
        }

        public synchronized void put(byte c) {
            if (this.buffer == null) {
                this.buffer = new byte[Service.getInstance().getSettings().SERIAL_BUFFER_SIZE];
            }
            this.buffer[this.bufferEnd] = c;
            this.bufferEnd += 1;
            if (this.bufferEnd == Service.getInstance().getSettings().SERIAL_BUFFER_SIZE) {
                this.bufferEnd = 0;
            }
            if (System.getProperty("smslib.dumpqueues") != null) {
                Logger.getInstance().logDebug("IN READER QUEUE : " + c + " / " + (char) c, null, AModemDriver.this.getGateway().getGatewayId());
            }
            notifyAll();
        }

        public synchronized byte get()
                throws TimeoutException, InterruptedException {
            if (this.buffer == null) {
                this.buffer = new byte[Service.getInstance().getSettings().SERIAL_BUFFER_SIZE];
            }
            for (;;) {
                try {
                    if (this.bufferStart == this.bufferEnd) {
                        wait(Service.getInstance().getSettings().SERIAL_TIMEOUT);
                    }
                    if (this.bufferStart == this.bufferEnd) {
                        throw new TimeoutException("No response from device.");
                    }
                    byte c = this.buffer[this.bufferStart];
                    this.bufferStart += 1;
                    if (this.bufferStart == Service.getInstance().getSettings().SERIAL_BUFFER_SIZE) {
                        this.bufferStart = 0;
                    }
                    return c;
                } catch (InterruptedException e) {
                    if (AModemDriver.this.getGateway().getStatus() == AGateway.GatewayStatuses.STARTED) {
                        Logger.getInstance().logWarn("Ignoring InterruptedException in Queue.get().", null, AModemDriver.this.getGateway().getGatewayId());
                    } else {
                        Logger.getInstance().logWarn("Re-throwing InterruptedException in Queue.get() - should be during shutdown...", null, AModemDriver.this.getGateway().getGatewayId());
                        throw new InterruptedException();
                    }
                }
            }
        }

        public synchronized byte peek()
                throws InterruptedException {
            if (this.buffer == null) {
                this.buffer = new byte[Service.getInstance().getSettings().SERIAL_BUFFER_SIZE];
            }
            for (;;) {
                try {
                    if (this.bufferStart == this.bufferEnd) {
                        wait(Service.getInstance().getSettings().SERIAL_TIMEOUT);
                    }
                    if (this.bufferStart == this.bufferEnd) {
                        return -1;
                    }
                    return this.buffer[this.bufferStart];
                } catch (InterruptedException e) {
                    if (AModemDriver.this.getGateway().getStatus() == AGateway.GatewayStatuses.STARTED) {
                        Logger.getInstance().logWarn("Ignoring InterruptedException in Queue.peek().", e, AModemDriver.this.getGateway().getGatewayId());
                    } else {
                        Logger.getInstance().logWarn("Re-throwing InterruptedException in Queue.peek() - should be during shutdown...", e, AModemDriver.this.getGateway().getGatewayId());
                        throw new InterruptedException();
                    }
                }
            }
        }

        public synchronized String peek(int sizeToRead) {
            if (this.buffer == null) {
                this.buffer = new byte[Service.getInstance().getSettings().SERIAL_BUFFER_SIZE];
            }
            int size = sizeToRead;
            if (this.bufferStart == this.bufferEnd) {
                return "";
            }
            StringBuffer result = new StringBuffer(size);
            int i = this.bufferStart;
            while (size > 0) {
                if ((this.buffer[i] != 10) && (this.buffer[i] != 13)) {
                    result.append((char) this.buffer[i]);
                    size--;
                }
                i++;
                if (i == Service.getInstance().getSettings().SERIAL_BUFFER_SIZE) {
                    i = 0;
                }
                if (i == this.bufferEnd) {
                    break;
                }
            }
            return result.toString();
        }

        public synchronized void clear() {
            this.bufferStart = 0;
            this.bufferEnd = 0;
        }
    }

    private class ModemReader
            extends Thread {

        public ModemReader() {
            setName("SMSlib-ModemReader-" + AModemDriver.this.getGateway().getGatewayId());
            setDaemon(true);
            start();
            Logger.getInstance().logDebug("ModemReader thread started.", null, AModemDriver.this.getGateway().getGatewayId());
        }

        public void run() {
            while (AModemDriver.this.isConnected()) {
                try {
                    synchronized (AModemDriver.this.getSYNCReader()) {
                        if (!AModemDriver.this.isDataReceived()) {
                            AModemDriver.this.getSYNCReader().wait();
                        }
                        if (!AModemDriver.this.isConnected()) {
                            break;
                        }
                        int c = AModemDriver.this.read();
                        while (c != -1) {
                            AModemDriver.this.getCharQueue().put((byte) c);
                            if (!AModemDriver.this.portHasData()) {
                                break;
                            }
                            c = AModemDriver.this.read();
                        }
                        AModemDriver.this.setDataReceived(false);
                    }
                    String data = AModemDriver.this.getCharQueue().peek(6);
                    for (int i = 0; i < AModemDriver.this.getGateway().getATHandler().getUnsolicitedResponses().length; i++) {
                        if (data.indexOf(AModemDriver.this.getGateway().getATHandler().getUnsolicitedResponse(i)) >= 0) {
                            Thread.sleep(100L);
                            AModemDriver.this.getKeepAlive().interrupt();
                            break;
                        }
                    }
                } catch (InterruptedException e) {
                    if (!AModemDriver.this.isConnected()) {
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Logger.getInstance().logDebug("ModemReader thread ended.", null, AModemDriver.this.getGateway().getGatewayId());
        }
    }

    private class KeepAlive
            extends AServiceThread {

        public KeepAlive(String name, int delay) {
            super(delay, 0, true);
        }

        public void process()
                throws Exception {
            try {
                if (!AModemDriver.this.isConnected()) {
                    return;
                }
                if (AModemDriver.this.getGateway().getStatus() == AGateway.GatewayStatuses.STARTED) {
                    synchronized (AModemDriver.this.getSYNCCommander()) {
                        if (!AModemDriver.this.isConnected()) {
                            return;
                        }
                        try {
                            if (!AModemDriver.this.getGateway().getATHandler().isAlive()) {
                                AModemDriver.this.getGateway().setStatus(AGateway.GatewayStatuses.RESTART);
                            }
                        } catch (Exception e) {
                            AModemDriver.this.getGateway().setStatus(AGateway.GatewayStatuses.RESTART);
                        }
                    }
                }
            } catch (Exception e) {
                Logger.getInstance().logError("ModemDriver: KeepAlive Error.", e, AModemDriver.this.getGateway().getGatewayId());
                AModemDriver.this.getGateway().setStatus(AGateway.GatewayStatuses.RESTART);
            }
        }
    }

    private class AsyncNotifier
            extends Thread {

        private BlockingQueue<Event> eventQueue;
        private Object SYNC;

        class Event {

            AGateway.AsyncEvents event;
            String response;

            public Event(AGateway.AsyncEvents myEvent, String myResponse) {
                this.event = myEvent;
                this.response = myResponse;
            }

            public String toString() {
                return "Event: " + this.event + " / Response: " + this.response;
            }
        }

        public AsyncNotifier() {
            this.SYNC = new Object();
            this.eventQueue = new LinkedBlockingQueue();
            setPriority(1);
            setName("SMSLib-AsyncNotifier : " + AModemDriver.this.getGateway().getGatewayId());
            setDaemon(true);
            start();
            Logger.getInstance().logDebug("AsyncNotifier thread started.", null, AModemDriver.this.getGateway().getGatewayId());
        }

        protected void setEvent(AGateway.AsyncEvents event, String response) {
            synchronized (this.SYNC) {
                Event ev = new Event(event, response);
                Logger.getInstance().logDebug("Storing AsyncEvent: " + ev, null, AModemDriver.this.getGateway().getGatewayId());
                this.eventQueue.add(ev);
                this.SYNC.notify();
            }
        }

        protected String getOriginator(String indication) {
            Pattern p = Pattern.compile("\\+?\"\\S+\"");
            Matcher m = p.matcher(indication);
            if (m.find()) {
                return indication.substring(m.start(), m.end()).replaceAll("\"", "");
            }
            return "";
        }

        public void run() {
            while (AModemDriver.this.isConnected()) {
                try {
                    Event event = (Event) this.eventQueue.take();
                    Logger.getInstance().logDebug("Processing AsyncEvent: " + event, null, AModemDriver.this.getGateway().getGatewayId());
                    if (event.event == AGateway.AsyncEvents.INBOUNDMESSAGE) {
                        Logger.getInstance().logDebug("Inbound message detected!", null, AModemDriver.this.getGateway().getGatewayId());
                        event.event = AGateway.AsyncEvents.NOTHING;
                        String response = event.response;
                        AModemDriver.this.getAsyncMessageProcessor().setProcess();

//                        JOptionPane.showMessageDialog(null, ">>> New Inbound message detected from " + "+" + response);
//                        ATHandler aa = new ATHandler(gateway);
//                        String[] r = response.split(",");
//                        getModemDriver().write("at+cmgf=" + r[1] + "\r");
//                        /* 141:168 */ Thread.sleep(100);
//                        /* 142:169 */ System.out.println("new msg " + getModemDriver().getResponse());
//                        JOptionPane.showMessageDialog(null, ">>> New Inbound message detected from  " + getModemDriver().getResponse());

              //       new InboundMessage(Message.MessageTypes.WAPSI, lastError, response)
                    } else if (event.event == AGateway.AsyncEvents.INBOUNDSTATUSREPORTMESSAGE) {
                        Logger.getInstance().logDebug("Inbound status report message detected!", null, AModemDriver.this.getGateway().getGatewayId());
                        event.event = AGateway.AsyncEvents.NOTHING;
                        String response = event.response;
                        AModemDriver.this.getAsyncMessageProcessor().setProcess();

                    } else if (event.event == AGateway.AsyncEvents.INBOUNDCALL) {
                        Logger.getInstance().logDebug("Inbound call detected!", null, AModemDriver.this.getGateway().getGatewayId());
                        event.event = AGateway.AsyncEvents.NOTHING;
                        String response;
                        synchronized (AModemDriver.this.getSYNCCommander()) {
                            AModemDriver.this.getGateway().getATHandler().switchToCmdMode();
                            AModemDriver.this.getGateway().getModemDriver().write("ATH\r");
                            AModemDriver.this.getGateway().getModemDriver().getResponse();
                            response = event.response;
                        }
                        Service.getInstance().getNotifyQueueManager().getNotifyQueue().add(new CallNotification(AModemDriver.this.getGateway(), getOriginator(response)));
                    } else if (event.event == AGateway.AsyncEvents.USSDRESPONSE) {
                        Logger.getInstance().logDebug("Inbound USSD response detected!", null, AModemDriver.this.getGateway().getGatewayId());
                        event.event = AGateway.AsyncEvents.NOTHING;
                        String response = event.response;
                        Logger.getInstance().logDebug("USSD response : " + AModemDriver.this.formatLog(response), null, AModemDriver.this.getGateway().getGatewayId());
                        if (Service.getInstance().getUSSDNotification() != null) {
                            USSDResponse ussdResponse = new USSDResponse(response, AModemDriver.this.getGateway().getGatewayId());
                            ussdResponse.setContent(AModemDriver.this.getGateway().getATHandler().formatUSSDResponse(ussdResponse.getContent()));
                            Service.getInstance().getUSSDNotification().process(AModemDriver.this.getGateway(), ussdResponse);
                        }
                    }
                } catch (InterruptedException e) {
                    if (!AModemDriver.this.isConnected()) {
                        break;
                    }
                } catch (InvalidMessageException e) {
                    Logger.getInstance().logInfo("Invalid Message received! Ignoring. ", e, AModemDriver.this.getGateway().getGatewayId());
                } catch (GatewayException e) {
                    AModemDriver.this.getGateway().setStatus(AGateway.GatewayStatuses.RESTART);
                } catch (IOException e) {
                    AModemDriver.this.getGateway().setStatus(AGateway.GatewayStatuses.RESTART);
                } catch (TimeoutException e) {
                    AModemDriver.this.getGateway().setStatus(AGateway.GatewayStatuses.RESTART);
                }
            }
            Logger.getInstance().logDebug("AsyncNotifier thread ended.", null, AModemDriver.this.getGateway().getGatewayId());
        }
    }

    private class AsyncMessageProcessor
            extends Thread {

        private List<InboundMessage> msgList;
        private Object SYNC;
        private boolean process;

        public AsyncMessageProcessor() {
            this.msgList = new ArrayList();
            this.SYNC = new Object();
            this.process = false;
            setPriority(5);
            setName("SMSLib-AsyncMessageProcessor : " + AModemDriver.this.getGateway().getGatewayId());
            setDaemon(true);
            start();
            Logger.getInstance().logDebug("AsyncMessageProcessor thread started.", null, AModemDriver.this.getGateway().getGatewayId());
        }

        public void setProcess() {
            synchronized (this.SYNC) {
                if (this.process) {
                    return;
                }
                this.process = true;
                this.SYNC.notify();
            }
        }

        public void run() {
            while (AModemDriver.this.isConnected()) {
                try {
                    synchronized (this.SYNC) {
                        if (!this.process) {
                            this.SYNC.wait();
                            if (!AModemDriver.this.isConnected()) {
                                break;
                            }
                        }
                    }
                    synchronized (AModemDriver.this.getSYNCInboundReader()) {
                        AModemDriver.this.getGateway().readMessages(this.msgList, InboundMessage.MessageClasses.ALL);
                        for (InboundMessage msg : this.msgList) {
                            switch ($SwitchMap$org$smslib$Message$MessageTypes[msg.getType().ordinal()]) {
                                case 1:
                                case 2:
                                    Service.getInstance().getNotifyQueueManager().getNotifyQueue().add(new InboundMessageNotification(AModemDriver.this.getGateway(), msg.getType(), msg));
                            }
                        }
                    }
                    this.msgList.clear();
                    this.process = false;
                } catch (InterruptedException e) {
                    if (!AModemDriver.this.isConnected()) {
                        break;
                    }
                } catch (GatewayException e) {
                    AModemDriver.this.getGateway().setStatus(AGateway.GatewayStatuses.RESTART);
                } catch (IOException e) {
                    AModemDriver.this.getGateway().setStatus(AGateway.GatewayStatuses.RESTART);
                } catch (TimeoutException e) {
                    AModemDriver.this.getGateway().setStatus(AGateway.GatewayStatuses.RESTART);
                }
            }
            Logger.getInstance().logDebug("AsyncMessageProcessor thread ended.", null, AModemDriver.this.getGateway().getGatewayId());
        }
    }

    private class CNMIEmulatorProcessor extends AServiceThread {

        private List msgList;
        final AModemDriver this$0;

        public CNMIEmulatorProcessor(String name, int delay) {
            super(delay, 0, false);

            this$0 = AModemDriver.this;
        }

        @Override
        public void process() throws Exception {

            if (!isConnected() || getGateway().getStatus() != org.smslib.AGateway.GatewayStatuses.STARTED) {
                //   break ;
            }

            //  Object obj = getSYNCInboundReader();
            //  JVM INSTR monitorenter ;
            msgList = new ArrayList();
            getGateway().readMessages(msgList, org.smslib.InboundMessage.MessageClasses.ALL);
            Iterator i$ = msgList.iterator();
            do {
                if (i$.hasNext()) {

                    InboundMessage msg = (InboundMessage) i$.next();

                    switch ($SwitchMap$org$smslib$Message$MessageTypes[msg.getType().ordinal()]) {
                        case 1: // '\001'
                        case 2: // '\002'
                            Service.getInstance().getNotifyQueueManager().getNotifyQueue().add(new InboundMessageNotification(getGateway(), msg.getType(), msg));
                            break;
                    }
                    continue;

                }
                msgList.clear();
                //   break MISSING_BLOCK_LABEL_188;

            } while (true);

            //To change body 
            //of generated methods, choose Tools | Templates.
        }
    }

    void setLastError(int myLastError) {
        this.lastError = myLastError;
    }

    public int getLastError() {
        return this.lastError;
    }

    public String getLastErrorText() {
        if (getLastError() == 0) {
            return "OK";
        }
        if (getLastError() == -1) {
            return "Invalid or empty response";
        }
        if (getLastError() / 1000 == 5) {
            return "CME Error " + getLastError() % 1000;
        }
        if (getLastError() / 1000 == 6) {
            return "CMS Error " + getLastError() % 1000;
        }
        return "Error: unknown";
    }

    public boolean isOk() {
        return getLastError() == OK;
    }

    protected ModemGateway getGateway() {
        return this.gateway;
    }

    protected void setGateway(ModemGateway myGateway) {
        this.gateway = myGateway;
    }

    protected boolean isConnected() {
        return this.connected;
    }

    protected void setConnected(boolean myConnected) {
        this.connected = myConnected;
    }

    protected boolean isDataReceived() {
        return this.dataReceived;
    }

    protected void setDataReceived(boolean myDataReceived) {
        this.dataReceived = myDataReceived;
    }

    protected CharQueue getCharQueue() {
        return this.charQueue;
    }

    protected void setCharQueue(CharQueue myCharQueue) {
        this.charQueue = myCharQueue;
    }

    protected Object getSYNCReader() {
        return this.SYNC_Reader;
    }

    protected void setSYNCReader(Object reader) {
        this.SYNC_Reader = reader;
    }

    protected Object getSYNCCommander() {
        return this.SYNC_Commander;
    }

    protected void setSYNCCommander(Object commander) {
        this.SYNC_Commander = commander;
    }

    protected Object getSYNCInboundReader() {
        return this.SYNC_InboundReader;
    }

    protected void setSYNCInboundReader(Object inbMessage) {
        this.SYNC_InboundReader = inbMessage;
    }

    protected KeepAlive getKeepAlive() {
        return this.keepAlive;
    }

    protected void setKeepAlive(KeepAlive myKeepAlive) {
        this.keepAlive = myKeepAlive;
    }

    protected AsyncNotifier getAsyncNotifier() {
        return this.asyncNotifier;
    }

    protected void setAsyncNotifier(AsyncNotifier myAsyncNotifier) {
        this.asyncNotifier = myAsyncNotifier;
    }

    protected AsyncMessageProcessor getAsyncMessageProcessor() {
        return this.asyncMessageProcessor;
    }

    protected void setAsyncMessageProcessor(AsyncMessageProcessor myAsyncMessageProcessor) {
        this.asyncMessageProcessor = myAsyncMessageProcessor;
    }

    protected CNMIEmulatorProcessor getCnmiEmulationProcessor() {
        return this.cnmiEmulationProcessor;
    }

    protected void setCnmiEmulationProcessor(CNMIEmulatorProcessor myCnmiEmulationProcessor) {
        this.cnmiEmulationProcessor = myCnmiEmulationProcessor;
    }

    protected ModemReader getModemReader() {
        return this.modemReader;
    }

    protected void setModemReader(ModemReader myModemReader) {
        this.modemReader = myModemReader;
    }
}

package org.smslib;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.*;
import org.smslib.balancing.LoadBalancer;
import org.smslib.balancing.RoundRobinLoadBalancer;
import org.smslib.crypto.KeyManager;
import org.smslib.helper.Logger;
import org.smslib.notify.NotifyQueueManager;
import org.smslib.queues.AbstractQueueManager;
import org.smslib.queues.DefaultQueueManager;
import org.smslib.routing.Router;
import org.smslib.threading.AServiceThread;

public class Service {

    class ExceptionPair {

        public Throwable e;
        public AGateway src;
        final Service this$0;

        ExceptionPair() {
            super();

            this$0 = Service.this;
        }
    }
    private List<AGateway> gatewayList;
    private Router router;
    private LoadBalancer loadBalancer;
    private WatchDog watchDog;
    private IInboundMessageNotification inboundMessageNotification;
    private IOutboundMessageNotification outboundMessageNotification;
    private ICallNotification callNotification;
    private IGatewayStatusNotification gatewayStatusNotification;
    private IQueueSendingNotification queueSendingNotification;
    private IOrphanedMessageNotification orphanedMessageNotification;
    protected AbstractQueueManager queueManager;
    protected NotifyQueueManager notifyQueueManager;
    private long startMillis;
    private Collection<Group> groups;
    private volatile ServiceStatus serviceStatus;
    private KeyManager keyManager;
    private Settings S;
    private IUSSDNotification ussdNotification;

    public static enum ServiceStatus {

        STARTING, STARTED, STOPPING, STOPPED;

        private ServiceStatus() {
        }
    }

    private static final Service service = new Service();

    private Service() {
        setSettings(new Settings());
        initializeService();
    }

    protected void initializeService() {
        this.startMillis = System.currentTimeMillis();
        setServiceStatus(ServiceStatus.STOPPED);
        this.groups = new ArrayList();
        listSystemInformation();
        this.gatewayList = new ArrayList();
        this.keyManager = KeyManager.getInstance();
        setRouter(new Router());
        setLoadBalancer(new RoundRobinLoadBalancer());
        setNotifyQueueManager(new NotifyQueueManager());
    }

    private void listSystemInformation() {
        Logger.getInstance().logInfo(Library.getLibraryDescription(), null, null);
        Logger.getInstance().logInfo("Version: " + Library.getLibraryVersion(), null, null);
        Logger.getInstance().logInfo("JRE Version: " + System.getProperty("java.version"), null, null);
        Logger.getInstance().logInfo("JRE Impl Version: " + System.getProperty("java.vm.version"), null, null);
        Logger.getInstance().logInfo("O/S: " + System.getProperty("os.name") + " / " + System.getProperty("os.arch") + " / " + System.getProperty("os.version"), null, null);
    }

    public static Service getInstance() {
        return service;
    }

    public void addGateway(AGateway gateway)
            throws GatewayException {
        if (getServiceStatus() != ServiceStatus.STOPPED) {
            throw new GatewayException("Cannot add gateways while Service is running!");
        }
        getGateways().add(gateway);
    }

    public boolean removeGateway(AGateway gateway)
            throws GatewayException {
        if (getServiceStatus() != ServiceStatus.STOPPED) {
            throw new GatewayException("Cannot remove gateways while Service is running!");
        }
        return getGateways().remove(gateway);
    }

    public AGateway getGateway(String gatewayId) {
        for (AGateway gateway : getGateways()) {
            if (gateway.getGatewayId().equalsIgnoreCase(gatewayId)) {
                return gateway;
            }
        }
        return null;
    }

    public synchronized void startService()
            throws SMSLibException, TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        startService(true);
    }

    public synchronized void startService(boolean startAllGateways)
            throws SMSLibException, TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        this.queueManager = new DefaultQueueManager();
        try {
            getQueueManager().start();
            setServiceStatus(ServiceStatus.STARTING);
            setWatchDog(new WatchDog("WatchDog", getSettings().WATCHDOG_INTERVAL * 1000));
            startService_Internal(startAllGateways);
            setServiceStatus(ServiceStatus.STARTED);
        } catch (SMSLibException e) {
            stopService();
            throw e;
        } catch (IOException e) {
            stopService();
            throw e;
        } catch (InterruptedException e) {
            stopService();
            throw e;
        }
    }

    private void startService_Internal(boolean startAll)
            throws SMSLibException, TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        try {
            if (getSettings().CONCURRENT_GATEWAY_START) {
                List eList = new LinkedList();
                List gList = new LinkedList();
                if (getGateways().size() == 0) {
                    throw new SMSLibException("No gateways are defined.");
                }
                class Starter extends Thread {

                    public void run() {
                        try {
                            gateway.startGateway();
                            gList.add(gateway);
                        } catch (Exception e) {
                            ExceptionPair exc = new ExceptionPair();
                            exc.e = e;
                            exc.src = gateway;
                            eList.add(exc);
                        }
                    }

                    AGateway gateway;
                    List eList;
                    List gList;
                    final Service this$0;

                    public Starter(AGateway gateway, List eList, List gList) {
                        super();

                        this$0 = Service.this;
                        this.gateway = gateway;
                        this.eList = eList;
                        this.gList = gList;
                    }
                }

                AGateway gateway;
                for (Iterator i$ = getGateways().iterator(); i$.hasNext(); (new Starter(gateway, eList, gList)).start()) {
                    gateway = (AGateway) i$.next();
                }

                for (; gList.size() != getGateways().size() && eList.size() == 0; Thread.sleep(1000L));
                ExceptionPair e;
                for (Iterator i$ = eList.iterator(); i$.hasNext(); e.src.setStatus(AGateway.GatewayStatuses.RESTART)) {
                    e = (ExceptionPair) i$.next();
                }

                if (startAll) {
                    if (eList.size() != 0) {
                        e = (ExceptionPair) eList.get(0);
                        if (e.e instanceof TimeoutException) {
                            throw (TimeoutException) e.e;
                        }
                        if (e.e instanceof GatewayException) {
                            throw (GatewayException) e.e;
                        }
                        if (e.e instanceof SMSLibException) {
                            throw (SMSLibException) e.e;
                        }
                        if (e.e instanceof IOException) {
                            throw (IOException) e.e;
                        }
                        if (e.e instanceof InterruptedException) {
                            throw (InterruptedException) e.e;
                        }
                    } else {
                        eList.clear();
                        gList.clear();
                    }
                }
            } else {
                if (getGateways().size() == 0) {
                    throw new SMSLibException("No gateways are defined.");
                }
                AGateway gateway = null;

                for (Iterator i$ = getGateways().iterator(); i$.hasNext(); gateway.startGateway()) {
                    gateway = (AGateway) i$.next();

                }
            }

        }catch(Exception e){
            System.out.println(e);
        }
    }

    public synchronized void stopService()
            throws SMSLibException, TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        setServiceStatus(ServiceStatus.STOPPING);
        getQueueManager().stop();
        if (getWatchDog() != null) {
            getWatchDog().cancel();
            setWatchDog(null);
        }
        for (AGateway gateway : getGateways()) {
            gateway.stopGateway();
        }
        getNotifyQueueManager().cancel();
        setServiceStatus(ServiceStatus.STOPPED);
    }

    public int readMessages(Collection<InboundMessage> msgList, InboundMessage.MessageClasses msgClass)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return 0;
        }
        for (AGateway gateway : getGateways()) {
            if (gateway.isInbound()) {
                try {
                    readMessages(msgList, msgClass, gateway);
                } catch (TimeoutException e) {
                    Logger.getInstance().logWarn("readMessages(): Gateway " + gateway.getGatewayId() + " does not respond, marking for restart.", null, null);
                    gateway.setStatus(AGateway.GatewayStatuses.RESTART);
                } catch (IOException e) {
                    Logger.getInstance().logWarn("readMessages(): Gateway " + gateway.getGatewayId() + " throws IO errors, marking for restart.", null, null);
                    gateway.setStatus(AGateway.GatewayStatuses.RESTART);
                }
            }
        }
        return msgList.size();
    }

    public InboundMessage[] readMessages(InboundMessage.MessageClasses msgClass)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        ArrayList<InboundMessage> messageList = new ArrayList();
        readMessages(messageList, msgClass);
        return (InboundMessage[]) messageList.toArray(new InboundMessage[0]);
    }

    public int readMessages(Collection<InboundMessage> msgList, InboundMessage.MessageClasses msgClass, String gatewayId)
            throws TimeoutException, GatewayException, IOException,InterruptedException, Exception {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return 0;
        }
        AGateway gateway = findGateway(gatewayId);
        if ((gateway != null) && (gateway.isInbound())) {
            try {
                readMessages(msgList, msgClass, gateway);
            } catch (TimeoutException e) {
                Logger.getInstance().logWarn("readMessages(): Gateway " + gateway.getGatewayId() + " does not respond, marking for restart.", null, null);
                gateway.setStatus(AGateway.GatewayStatuses.RESTART);
            } catch (IOException e) {
                Logger.getInstance().logWarn("readMessages(): Gateway " + gateway.getGatewayId() + " throws IO errors, marking for restart.", null, null);
                gateway.setStatus(AGateway.GatewayStatuses.RESTART);
            }
        }
        return msgList.size();
    }

    public InboundMessage[] readMessages(InboundMessage.MessageClasses msgClass, String gatewayId)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        ArrayList<InboundMessage> messageList = new ArrayList();
        readMessages(messageList, msgClass, gatewayId);
        return (InboundMessage[]) messageList.toArray(new InboundMessage[0]);
    }

    public int readMessages(Collection<InboundMessage> msgList, InboundMessage.MessageClasses msgClass, AGateway gateway)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return 0;
        }
        try {
            gateway.readMessages(msgList, msgClass);
        } catch (TimeoutException e) {
            Logger.getInstance().logWarn("readMessages(): Gateway " + gateway.getGatewayId() + " does not respond, marking for restart.", null, null);
            gateway.setStatus(AGateway.GatewayStatuses.RESTART);
        } catch (IOException e) {
            Logger.getInstance().logWarn("readMessages(): Gateway " + gateway.getGatewayId() + " throws IO errors, marking for restart.", null, null);
            gateway.setStatus(AGateway.GatewayStatuses.RESTART);
        }
        return msgList.size();
    }

    public InboundMessage[] readMessages(InboundMessage.MessageClasses msgClass, AGateway gateway)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        ArrayList<InboundMessage> messageList = new ArrayList();
        readMessages(messageList, msgClass, gateway);
        return (InboundMessage[]) messageList.toArray(new InboundMessage[0]);
    }

    public InboundMessage readMessage(String gatewayId, String memLoc, int memIndex)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return null;
        }
        InboundMessage msg = null;
        AGateway gateway = findGateway(gatewayId);
        if ((gateway != null) && (gateway.isInbound())) {
            try {
                msg = gateway.readMessage(memLoc, memIndex);
            } catch (TimeoutException e) {
                Logger.getInstance().logWarn("readMessages(): Gateway " + gateway.getGatewayId() + " does not respond, marking for restart.", null, null);
                gateway.setStatus(AGateway.GatewayStatuses.RESTART);
            } catch (IOException e) {
                Logger.getInstance().logWarn("readMessages(): Gateway " + gateway.getGatewayId() + " throws IO errors, marking for restart.", null, null);
                gateway.setStatus(AGateway.GatewayStatuses.RESTART);
            }
        }
        return msg;
    }

    public boolean sendMessage(OutboundMessage msg)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return false;
        }
        AGateway gateway = routeMessage(msg);
        if (gateway != null) {
            try {
                List<String> recipients = expandGroup(msg.getRecipient());
                if (recipients.size() == 0) {
                    return gateway.sendMessage(msg);
                }
                List<OutboundMessage> groupMessages = new ArrayList();
                for (String to : recipients) {
                    OutboundMessage newMessage = new OutboundMessage();
                    msg.copyTo(newMessage);
                    newMessage.setRecipient(to);
                    groupMessages.add(newMessage);
                }
                sendMessages(groupMessages);
                return true;
            } catch (TimeoutException e) {
                Logger.getInstance().logWarn("sendMessage(): Gateway " + gateway.getGatewayId() + " does not respond, marking for restart.", null, null);
                gateway.setStatus(AGateway.GatewayStatuses.RESTART);
                msg.setMessageStatus(OutboundMessage.MessageStatuses.FAILED);
                msg.setFailureCause(OutboundMessage.FailureCauses.GATEWAY_FAILURE);
                return false;
            } catch (IOException e) {
                Logger.getInstance().logWarn("sendMessage(): Gateway " + gateway.getGatewayId() + " throws IO errors, marking for restart.", null, null);
                gateway.setStatus(AGateway.GatewayStatuses.RESTART);
                msg.setMessageStatus(OutboundMessage.MessageStatuses.FAILED);
                msg.setFailureCause(OutboundMessage.FailureCauses.GATEWAY_FAILURE);
                return false;
            }
        }
        return false;
    }

    public boolean sendMessage(OutboundMessage msg, String gatewayId)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return false;
        }
        msg.setGatewayId(gatewayId);
        return sendMessage(msg);
    }

    public int sendMessages(Collection<OutboundMessage> msgList)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return 0;
        }
        int counter = 0;
        for (OutboundMessage msg : msgList) {
            if (sendMessage(msg)) {
                counter++;
            }
        }
        return counter;
    }

    public int sendMessages(OutboundMessage[] msgArray)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        int counter = 0;
        for (int i = 0; i < msgArray.length; i++) {
            if (sendMessage(msgArray[i])) {
                counter++;
            }
        }
        return counter;
    }

    public int sendMessages(Collection<OutboundMessage> msgList, String gatewayId)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return 0;
        }
        int counter = 0;
        for (OutboundMessage msg : msgList) {
            msg.setGatewayId(gatewayId);
            if (sendMessage(msg)) {
                counter++;
            }
        }
        return counter;
    }

    public int sendMessages(OutboundMessage[] msgArray, String gatewayId)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        int counter = 0;
        for (int i = 0; i < msgArray.length; i++) {
            msgArray[i].setGatewayId(gatewayId);
            if (sendMessage(msgArray[i])) {
                counter++;
            }
        }
        return counter;
    }

    public boolean queueMessage(OutboundMessage msg) {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return false;
        }
        List<String> recipients = expandGroup(msg.getRecipient());
        if (recipients.size() == 0) {
            if ("*".equals(msg.getGatewayId())) {
                AGateway gateway = routeMessage(msg);
                if (gateway == null) {
                    return false;
                }
                msg.setGatewayId(gateway.getGatewayId());
            }
            return getQueueManager().queueMessage(msg);
        }
        for (String to : recipients) {
            OutboundMessage newMessage = new OutboundMessage();
            msg.copyTo(newMessage);
            newMessage.setRecipient(to);
            if ("*".equals(msg.getGatewayId())) {
                AGateway gateway = routeMessage(msg);
                if (gateway == null) {
                    return false;
                }
                msg.setGatewayId(gateway.getGatewayId());
            }
            getQueueManager().queueMessage(newMessage);
        }
        return true;
    }

    public boolean queueMessage(OutboundMessage msg, String gatewayId) {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return false;
        }
        msg.setGatewayId(gatewayId);
        return queueMessage(msg);
    }

    public int queueMessages(Collection<OutboundMessage> msgList) {
        int counter = 0;
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return 0;
        }
        for (OutboundMessage msg : msgList) {
            if (queueMessage(msg)) {
                counter++;
            }
        }
        return counter;
    }

    public int queueMessages(OutboundMessage[] msgArray) {
        int counter = 0;
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return 0;
        }
        for (int i = 0; i < msgArray.length; i++) {
            if (queueMessage(msgArray[i])) {
                counter++;
            }
        }
        return counter;
    }

    public int queueMessages(Collection<OutboundMessage> msgList, String gatewayId) {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return 0;
        }
        int counter = 0;
        for (OutboundMessage msg : msgList) {
            msg.setGatewayId(gatewayId);
            if (queueMessage(msg)) {
                counter++;
            }
        }
        return counter;
    }

    public int queueMessages(OutboundMessage[] msgArray, String gatewayId) {
        int counter = 0;
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return 0;
        }
        for (int i = 0; i < msgArray.length; i++) {
            msgArray[i].setGatewayId(gatewayId);
            if (queueMessage(msgArray[i])) {
                counter++;
            }
        }
        return counter;
    }

    public boolean queueMessageAt(OutboundMessage msg, Date at) {
        msg.setScheduledDeliveryDate(at);
        return queueMessage(msg);
    }

    public boolean queueMessageAt(OutboundMessage msg, long delayMillis) {
        msg.setDeliveryDelay(delayMillis);
        return queueMessage(msg);
    }

    public boolean removeMessage(OutboundMessage msg) {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return false;
        }
        return getQueueManager().removePendingMessage(msg);
    }

    public boolean removeMessage(String messageId) {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return false;
        }
        return getQueueManager().removePendingMessage(messageId);
    }

    public boolean deleteMessage(InboundMessage msg)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return false;
        }
        AGateway gateway = findGateway(msg.getGatewayId());
        if (gateway != null) {
            try {
                return gateway.deleteMessage(msg);
            } catch (TimeoutException e) {
                Logger.getInstance().logWarn("deleteMessage(): Gateway " + gateway.getGatewayId() + " does not respond, marking for restart.", null, null);
                gateway.setStatus(AGateway.GatewayStatuses.RESTART);
                return false;
            } catch (IOException e) {
                Logger.getInstance().logWarn("deleteMessage(): Gateway " + gateway.getGatewayId() + " throws IO errors, marking for restart.", null, null);
                gateway.setStatus(AGateway.GatewayStatuses.RESTART);
                return false;
            }
        }
        return false;
    }

    public boolean sendUSSDRequest(USSDRequest request, String gatewayId)
            throws GatewayException, TimeoutException, IOException, InterruptedException, Exception {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            throw new GatewayException("Service is not started");
        }
        AGateway gateway = findGateway(gatewayId);
        if (request == null) {
            throw new IllegalArgumentException("Cannot use a null request object");
        }
        if (gateway == null) {
            throw new GatewayException("Cannot use a null gateway");
        }
        return gateway.sendUSSDRequest(request);
    }

    public int readPhonebook(Phonebook phonebook, String gatewayId)
            throws TimeoutException, GatewayException, IOException, InterruptedException, Exception {
        if (getServiceStatus() != ServiceStatus.STARTED) {
            return 0;
        }
        AGateway gateway = findGateway(gatewayId);
        if (gateway != null) {
            return gateway.readPhonebook(phonebook);
        }
        return 0;
    }

    public int getInboundMessageCount(String gatewayId) {
        return getInboundMessageCount(findGateway(gatewayId));
    }

    public int getInboundMessageCount(AGateway gateway) {
        return gateway != null ? gateway.getInboundMessageCount() : -1;
    }

    public int getOutboundMessageCount(String gatewayId) {
        return getOutboundMessageCount(findGateway(gatewayId));
    }

    public int getOutboundMessageCount(AGateway gateway) {
        return gateway != null ? gateway.getOutboundMessageCount() : -1;
    }

    public int getInboundMessageCount() {
        int total = 0;
        for (AGateway gateway : getGateways()) {
            total += gateway.getInboundMessageCount();
        }
        return total;
    }

    public int getOutboundMessageCount() {
        int total = 0;
        for (AGateway gateway : getGateways()) {
            total += gateway.getOutboundMessageCount();
        }
        return total;
    }

    public AGateway findGateway(String gatewayId) {
        for (AGateway gateway : getGateways()) {
            if (gateway.getGatewayId().equals(gatewayId)) {
                return gateway;
            }
        }
        return null;
    }

    public Collection<AGateway> getGateways() {
        return this.gatewayList;
    }

    public AGateway[] getGatewaysNET() {
        return (AGateway[]) getGateways().toArray(new AGateway[0]);
    }

    public LoadBalancer getLoadBalancer() {
        return this.loadBalancer;
    }

    public void setLoadBalancer(LoadBalancer loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public Router getRouter() {
        return this.router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public synchronized AGateway routeMessage(OutboundMessage msg) {
        Collection<AGateway> gateways = getRouter().route(msg, getGateways());
        if (gateways.size() > 0) {
            return getLoadBalancer().balance(msg, gateways);
        }
        msg.setMessageStatus(OutboundMessage.MessageStatuses.FAILED);
        msg.setFailureCause(OutboundMessage.FailureCauses.NO_ROUTE);
        return null;
    }

    public IInboundMessageNotification getInboundMessageNotification() {
        return this.inboundMessageNotification;
    }

    public void setInboundMessageNotification(IInboundMessageNotification inboundNotification) {
        this.inboundMessageNotification = inboundNotification;
    }

    public IOutboundMessageNotification getOutboundMessageNotification() {
        return this.outboundMessageNotification;
    }

    public void setOutboundMessageNotification(IOutboundMessageNotification outboundNotification) {
        this.outboundMessageNotification = outboundNotification;
    }

    public ICallNotification getCallNotification() {
        return this.callNotification;
    }

    public void setCallNotification(ICallNotification callNotification) {
        this.callNotification = callNotification;
    }

    public IUSSDNotification getUSSDNotification() {
        return this.ussdNotification;
    }

    public void setUSSDNotification(IUSSDNotification ussdNotification) {
        this.ussdNotification = ussdNotification;
    }

    public IGatewayStatusNotification getGatewayStatusNotification() {
        return this.gatewayStatusNotification;
    }

    public void setGatewayStatusNotification(IGatewayStatusNotification gatewayStatusNotification) {
        this.gatewayStatusNotification = gatewayStatusNotification;
    }

    public IQueueSendingNotification getQueueSendingNotification() {
        return this.queueSendingNotification;
    }

    public void setQueueSendingNotification(IQueueSendingNotification queueSendingNotification) {
        this.queueSendingNotification = queueSendingNotification;
    }

    public IOrphanedMessageNotification getOrphanedMessageNotification() {
        return this.orphanedMessageNotification;
    }

    public void setOrphanedMessageNotification(IOrphanedMessageNotification orphanedMessageNotification) {
        this.orphanedMessageNotification = orphanedMessageNotification;
    }

    public long getStartMillis() {
        return this.startMillis;
    }

    void setSettings(Settings settings) {
        this.S = settings;
    }

    public ServiceStatus getServiceStatus() {
        return this.serviceStatus;
    }

    void setServiceStatus(ServiceStatus myServiceStatus) {
        this.serviceStatus = myServiceStatus;
    }

    public Settings getSettings() {
        return this.S;
    }

    public boolean createGroup(String groupName) {
        this.groups.add(new Group(groupName));
        return true;
    }

    public boolean removeGroup(String groupName) {
        for (Group a : this.groups) {
            if (a.getName().equalsIgnoreCase(groupName)) {
                a.clear();
                this.groups.remove(a);
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> expandGroup(String groupName) {
        for (Group a : this.groups) {
            if (a.getName().equalsIgnoreCase(groupName)) {
                return new ArrayList(a.getNumbers());
            }
        }
        return new ArrayList();
    }

    public boolean addToGroup(String groupName, String number) {
        for (Group a : this.groups) {
            if (a.getName().equalsIgnoreCase(groupName)) {
                a.addNumber(number);
                return true;
            }
        }
        return false;
    }

    public boolean removeFromGroup(String groupName, String number) {
        for (Group a : this.groups) {
            if (a.getName().equalsIgnoreCase(groupName)) {
                return a.removeNumber(number);
            }
        }
        return false;
    }

    public boolean setQueueManager(AbstractQueueManager myQueueManager) {
        if (getServiceStatus() != ServiceStatus.STOPPED) {
            return false;
        }
        this.queueManager = myQueueManager;
        return true;
    }

    public AbstractQueueManager getQueueManager() {
        return this.queueManager;
    }

    void setNotifyQueueManager(NotifyQueueManager myNotifyQueueManager) {
        this.notifyQueueManager = myNotifyQueueManager;
    }

    public NotifyQueueManager getNotifyQueueManager() {
        return this.notifyQueueManager;
    }

    public KeyManager getKeyManager() {
        return this.keyManager;
    }

    WatchDog getWatchDog() {
        return this.watchDog;
    }

    void setWatchDog(WatchDog myWatchDog) {
        this.watchDog = myWatchDog;
    }

    private class WatchDog
            extends AServiceThread {

        public WatchDog(String name, int delay) {
            super(delay, 0, true);
        }

        public void process()
                throws Exception {
            if (Service.this.getServiceStatus() != Service.ServiceStatus.STARTED) {
                return;
            }
            for (AGateway gateway : Service.this.getGateways()) {
                if (gateway.getStatus() == AGateway.GatewayStatuses.RESTART) {
                    Logger.getInstance().logWarn("Gateway: " + gateway.getGatewayId() + " restarting.", null, null);
                    try {
                        gateway.stopGateway();
                        gateway.setStatus(AGateway.GatewayStatuses.RESTART);
                    } catch (Exception e) {
                        gateway.setStatus(AGateway.GatewayStatuses.RESTART);
                        Logger.getInstance().logWarn("Error while shutting down Gateway: " + gateway.getGatewayId(), e, null);
                    }
                    try {
                        gateway.startGateway();
                    } catch (Exception e) {
                        gateway.setStatus(AGateway.GatewayStatuses.RESTART);
                        Logger.getInstance().logError("Error while starting Gateway: " + gateway.getGatewayId(), e, null);
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(Library.getLibraryDescription());
        System.out.println("\nSMSLib API Version: " + Library.getLibraryVersion());
    }
}

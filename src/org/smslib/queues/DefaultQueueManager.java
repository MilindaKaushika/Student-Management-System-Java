// Decompiled by DJ v3.12.12.100 Copyright 2015 Atanas Neshkov  Date: 12/20/2015 9:57:13 PM
// Home Page:  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   DefaultQueueManager.java

package org.smslib.queues;

import java.io.*;
import java.util.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.PriorityBlockingQueue;
import org.smslib.*;
import org.smslib.helper.Logger;

// Referenced classes of package org.smslib.queues:
//            AbstractQueueManager, ScheduledOutboundMessage

public class DefaultQueueManager extends AbstractQueueManager
{
    class PriorityComparator
        implements Comparator
    {

        public int compare(OutboundMessage x, OutboundMessage y)
        {
            int comp = y.getPriority() - x.getPriority();
            if(comp == 0)
                comp = x.getDate().compareTo(y.getDate());
            return comp;
        }

        @Override
        public int compare(Object x0, Object x1)
        {
            return compare((OutboundMessage)x0, (OutboundMessage)x1);
        }

        final DefaultQueueManager this$0;

        PriorityComparator()
        {
                        super();

            this$0 = DefaultQueueManager.this;
        }
    }


    public DefaultQueueManager()
    {
    }

    public DefaultQueueManager(String queueDirectory)
    {
        this.queueDirectory = queueDirectory;
    }

    public DefaultQueueManager(int delay)
    {
        super(delay);
    }

    public DefaultQueueManager(int delay, String queueDirectory)
    {
        super(delay);
        this.queueDirectory = queueDirectory;
    }

    protected void init()
    {
        super.init();
        queueMap = new HashMap();
        delayQueue = new DelayQueue();
        if(queueDirectory == null)
        {
            queueDirectory = Service.getInstance().getSettings().QUEUE_DIRECTORY;
            if(queueDirectory == null)
            {
                Logger.getInstance().logInfo("Queue directory not defined. Queued messages will not be saved to filesystem.", null, null);
                return;
            }
        }
        File baseDir = new File(queueDirectory, "queue");
        pendingMessageDir = new File(baseDir, "pending");
        if(!pendingMessageDir.exists())
        {
            if(!pendingMessageDir.mkdirs())
                Logger.getInstance().logError((new StringBuilder()).append("Could not create directory for pending messages queue at ").append(pendingMessageDir.getPath()).toString(), null, null);
        } else
        {
            Logger.getInstance().logDebug("loading pending messages..", null, null);
            loadPendingMessages();
        }
        delayedMessageDir = new File(baseDir, "delayed");
        if(!delayedMessageDir.exists())
        {
            if(!delayedMessageDir.mkdirs())
                Logger.getInstance().logError((new StringBuilder()).append("Could not create directory for delayed messages queue at ").append(delayedMessageDir.getPath()).toString(), null, null);
        } else
        {
            Logger.getInstance().logDebug("loading delayed messages..", null, null);
            loadDelayedMessages();
        }
    }

    public boolean queueMessage(OutboundMessage message)
    {
        if(message.getDeliveryDelay() > 0L)
            return addToDelayedQueue(message, true);
        else
            return addToGatewayQueue(message, true);
    }

    public boolean removePendingMessage(OutboundMessage message)
    {
        for(Iterator i$ = queueMap.values().iterator(); i$.hasNext();)
        {
            PriorityBlockingQueue q = (PriorityBlockingQueue)i$.next();
            if(q.remove(message))
            {
                deletePendingMessage(message.getGatewayId(), message.getUuid());
                return true;
            }
        }

        return false;
    }

    public boolean removePendingMessage(String messageUUID)
    {
        Iterator i$ = queueMap.values().iterator();
        OutboundMessage m;
label0:
        do
            if(i$.hasNext())
            {
                PriorityBlockingQueue q = (PriorityBlockingQueue)i$.next();
           i$ = q.iterator();
                do
                {
                    if(!i$.hasNext())
                        continue label0;
                    m = (OutboundMessage)i$.next();
                } while(!m.getId().equalsIgnoreCase(messageUUID) || !q.remove(m));
                break;
            } else
            {
                return false;
            }
        while(true);
        deletePendingMessage(m.getGatewayId(), messageUUID);
        return true;
    }

    private boolean addToGatewayQueue(OutboundMessage message, boolean store)
    {
        PriorityBlockingQueue queue = (PriorityBlockingQueue)queueMap.get(message.getGatewayId());
        if(queue == null)
        {
            queue = new PriorityBlockingQueue(50, new PriorityComparator());
            queueMap.put(message.getGatewayId(), queue);
        }
        boolean queued = queue.add(message);
        if(store && queued)
            storePendingMessage(message);
        return queued;
    }

    private boolean addToDelayedQueue(OutboundMessage message, boolean store)
    {
        boolean queued = delayQueue.add(new ScheduledOutboundMessage(message));
        if(store && queued)
            storeDelayedMessage(message);
        return queued;
    }

    private boolean storePendingMessage(OutboundMessage message)
    {
        if(queueDirectory == null)
            return true;
        File gatewayDir = new File(pendingMessageDir, message.getGatewayId());
        if(!gatewayDir.exists() && !gatewayDir.mkdir())
        {
            Logger.getInstance().logError((new StringBuilder()).append("Queue directory could be created for gateway ").append(message.getGatewayId()).append(". Could not create directory ..").append(gatewayDir.getPath()).toString(), null, null);
            return false;
        } else
        {
            return serializeMessage(message, new File(gatewayDir, (new StringBuilder()).append(message.getUuid()).append(".msg").toString()));
        }
    }

    private boolean deletePendingMessage(String gatewayId, String messageUUID)
    {
        if(queueDirectory == null)
            return true;
        else
            return (new File(new File(pendingMessageDir, gatewayId), (new StringBuilder()).append(messageUUID).append(".msg").toString())).delete();
    }

    private boolean deletePendingMessages(String gatewayId)
    {
        if(queueDirectory == null)
            return true;
        if(gatewayId == null)
            return emptyDirectory(pendingMessageDir, false);
        else
            return emptyDirectory(new File(pendingMessageDir, gatewayId), true);
    }

    private boolean storeDelayedMessage(OutboundMessage message)
    {
        if(queueDirectory == null)
            return true;
        else
            return serializeMessage(message, new File(delayedMessageDir, (new StringBuilder()).append(message.getUuid()).append(".msg").toString()));
    }

    private boolean deleteDelayedMessage(String messageUUID)
    {
        if(queueDirectory == null)
            return true;
        else
            return (new File(delayedMessageDir, (new StringBuilder()).append(messageUUID).append(".msg").toString())).delete();
    }

    public OutboundMessage pollDelayedMessage()
    {
        try
        {
            OutboundMessage message = ((ScheduledOutboundMessage)delayQueue.take()).getMessage();
            deleteDelayedMessage(message.getUuid());
            return message;
        }
        catch(InterruptedException e)
        {
            return null;
        }
    }

    public OutboundMessage pollPendingMessage(String gatewayId)
    {
        PriorityBlockingQueue queue = (PriorityBlockingQueue)queueMap.get(gatewayId);
        if(queue == null)
            return null;
        OutboundMessage message = (OutboundMessage)queue.poll();
        if(message != null)
            deletePendingMessage(gatewayId, message.getUuid());
        return message;
    }

    private boolean serializeMessage(OutboundMessage message, File toFile)
    {
        if(queueDirectory == null)
            return true;
        if(toFile.exists())
        {
            Logger.getInstance().logError((new StringBuilder()).append("Cannot save Message ").append(message.getUuid()).append(" File already exist.").toString(), null, null);
            return false;
        }
        ObjectOutputStream out = null;
        try
        {
            out = new ObjectOutputStream(new FileOutputStream(toFile));
            out.writeObject(message);
            out.close();
        }
        catch(IOException e)
        {
            Logger.getInstance().logError((new StringBuilder()).append("Cannot save Message ").append(message.getUuid()).toString(), e, null);
            return false;
        }
        return true;
    }

    private OutboundMessage deserializeMessage(File fromFile)
    {
        if(!fromFile.exists())
        {
            Logger.getInstance().logError((new StringBuilder()).append("File of queued message doesn't exist ").append(fromFile.getPath()).toString(), null, null);
            return null;
        }
        ObjectInputStream in = null;
        OutboundMessage message = null;
        try
        {
            in = new ObjectInputStream(new FileInputStream(fromFile));
            message = (OutboundMessage)in.readObject();
            in.close();
        }
        catch(IOException e)
        {
            Logger.getInstance().logError((new StringBuilder()).append("Could not read queued message from file ").append(fromFile.getPath()).toString(), e, null);
            return null;
        }
        catch(ClassNotFoundException e)
        {
            Logger.getInstance().logError((new StringBuilder()).append("Could not read queued message from file ").append(fromFile.getPath()).toString(), e, null);
            return null;
        }
        return message;
    }

    private void loadPendingMessages()
    {
        File pendingDirs[] = pendingMessageDir.listFiles();
        File arr$[] = pendingDirs;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            File pendingDir = arr$[i$];
            if(!pendingDir.isDirectory())
                continue;
            File pendingFiles[] = pendingDir.listFiles();
            File arr$1 [] = pendingFiles;
            int len$1 = arr$1.length;
            for(int i$1 = 0; i$1 < len$1; i$1++)
            {
                File pendingFile = arr$1[i$1];
                if(pendingFile.getName().endsWith(".msg"))
                    addToGatewayQueue(deserializeMessage(pendingFile), false);
            }

        }

    }

    private void loadDelayedMessages()
    {
        File delayedFiles[] = delayedMessageDir.listFiles();
        File arr$[] = delayedFiles;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            File delayedFile = arr$[i$];
            if(!delayedFile.getName().endsWith(".msg"))
                continue;
            OutboundMessage message = deserializeMessage(delayedFile);
            if(message.getDeliveryDelay() > 0L)
            {
                addToDelayedQueue(message, false);
            } else
            {
                addToGatewayQueue(message, true);
                deleteDelayedMessage(message.getUuid());
            }
        }

    }

    public int delayedQueueSize(String gatewayId)
    {
        return delayQueue.size();
    }

    public Collection getDelayedMessages()
    {
        List messages = new ArrayList();
        ScheduledOutboundMessage scheduledOutboundMessage;
        for(Iterator i$ = delayQueue.iterator(); i$.hasNext(); messages.add(scheduledOutboundMessage.getMessage()))
            scheduledOutboundMessage = (ScheduledOutboundMessage)i$.next();

        return messages;
    }

    public Collection getPendingMessages(String gatewayId)
    {
        PriorityBlockingQueue queue = (PriorityBlockingQueue)queueMap.get(gatewayId);
        if(queue == null)
            return new ArrayList();
        else
            return new ArrayList(queue);
    }

    public int pendingQueueSize(String gatewayId)
    {
        PriorityBlockingQueue queue = (PriorityBlockingQueue)queueMap.get(gatewayId);
        if(queue == null)
            return 0;
        else
            return queue.size();
    }

    public boolean removeDelayedMessage(OutboundMessage message)
    {
        for(Iterator i$ = delayQueue.iterator(); i$.hasNext();)
        {
            ScheduledOutboundMessage scheduledOutboundMessage = (ScheduledOutboundMessage)i$.next();
            if(message.equals(scheduledOutboundMessage.getMessage()) && delayQueue.remove(scheduledOutboundMessage))
            {
                deleteDelayedMessage(message.getUuid());
                return true;
            }
        }

        return false;
    }

    public boolean removeDelayedMessage(String messageUUID)
    {
        for(Iterator i$ = delayQueue.iterator(); i$.hasNext();)
        {
            ScheduledOutboundMessage scheduledOutboundMessage = (ScheduledOutboundMessage)i$.next();
            if(messageUUID.equals(scheduledOutboundMessage.getMessage().getUuid()) && delayQueue.remove(scheduledOutboundMessage))
            {
                deleteDelayedMessage(messageUUID);
                return true;
            }
        }

        return false;
    }

    public boolean removeAllDelayedMessages()
    {
        delayQueue.clear();
        if(queueDirectory == null)
            return true;
        else
            return emptyDirectory(delayedMessageDir, false);
    }

    public boolean removeAllPendingMessages(String gatewayId)
    {
        PriorityBlockingQueue queue = (PriorityBlockingQueue)queueMap.get(gatewayId);
        if(queue != null)
        {
            queue.clear();
            queueMap.remove(queue);
            deletePendingMessages(gatewayId);
            return true;
        } else
        {
            return false;
        }
    }

    public boolean removeAllPendingMessages()
    {
        queueMap.clear();
        deletePendingMessages(null);
        return false;
    }

    private boolean emptyDirectory(File dir, boolean removeDir)
    {
        File pendingDirs[] = dir.listFiles();
        File arr$[] = pendingDirs;
        int len$ = arr$.length;
        for(int i$ = 0; i$ < len$; i$++)
        {
            File file = arr$[i$];
            if(file.isDirectory())
            {
                emptyDirectory(file, true);
                continue;
            }
            if(!file.delete())
                return false;
        }

        if(removeDir)
            return dir.delete();
        else
            return true;
    }

    public static final String MESSAGE_FILE_EXT = ".msg";
    private Map queueMap;
    private DelayQueue delayQueue;
    private String queueDirectory;
    private File pendingMessageDir;
    private File delayedMessageDir;
}

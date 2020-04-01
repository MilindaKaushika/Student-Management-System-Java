package SMS;


import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.comm.*;
import java.util.Enumeration;
import java.util.concurrent.TimeoutException;
import javax.swing.JOptionPane;
import org.ajwcc.pduUtils.gsm3040.PduUtils;
import org.smslib.AGateway;
import org.smslib.AGateway.Protocols;
import org.smslib.GatewayException;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Library;
import org.smslib.Message;
import org.smslib.OutboundMessage;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.modem.AModemDriver;
import org.smslib.modem.SerialModemGateway;
import org.smslib.modem.athandler.ATHandler;


public class ListPorts {
    
    public static String smsc;
    public static String po;
        public static String ponew;

    public static AModemDriver modemDriver;

    public static AModemDriver getModemDriver() {
        return ListPorts.modemDriver;
    }
    
    public class InboundNotification implements IInboundMessageNotification {
    // @Override
    // Get triggered when a SMS is received
    public void process(AGateway gateway, Message.MessageTypes messageTypes, InboundMessage inboundMessage) {
        System.out.println(inboundMessage);
        try {
            gateway.deleteMessage(inboundMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        
    
    }

public class OutboundNotification implements IOutboundMessageNotification {

    // Get triggered when a SMS is sent
    public void process(AGateway gateway, OutboundMessage outboundMessage) {
       // System.out.println(outboundMessage);
    }
}
    
    public static String getConnectedport()
        throws TimeoutException, GatewayException, SMSLibException, IOException, InterruptedException, Exception {

    ArrayList<String> allports = new ArrayList();

    Enumeration ports = CommPortIdentifier.getPortIdentifiers();
    while (ports.hasMoreElements()) {
        CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
        String type;
        switch (port.getPortType()) {
        case CommPortIdentifier.PORT_PARALLEL:
            type = "Parallel";
            break;
        case CommPortIdentifier.PORT_SERIAL:
            allports.add(port.getName());
            type = "Serial";
            break;
        default: /// Shouldn't happen
            type = "Unknown";
            break;
        }
        System.out.println(port.getName() + ": " + type);
        allports.add(port.getName());
    }
    String connectedport = null;
    for (int a = 0; a < allports.size(); a++) {
        connectedport = getport(allports.get(a));
        
        if (connectedport != null) {
            a = allports.size();
         //   System.out.println(connectedport + " port in for loop");

        }

    }
//JOptionPane.showMessageDialog(null, connectedport);
    return connectedport;

}

private static String getport(String port)
        throws TimeoutException, GatewayException, SMSLibException, IOException, InterruptedException, Exception {
    String returnport = null;

  //  SerialModemGateway gateway = new SerialModemGateway("", port, 9600, "", "");
   // gateway.setInbound(true);
   // gateway.setOutbound(true);
   // OutboundNotification outboundNotification = new OutboundNotification();
   // InboundNotification inboundNotification = new InboundNotification();
   // Service service = Service.getInstance();
   // service.setOutboundMessageNotification(outboundNotification);
   // service.setInboundMessageNotification(inboundNotification);
   // service.addGateway(gateway);
   // service.startService();
   // OutboundMessage msg = new OutboundMessage(num, msge);
   // service.sendMessage(msg);
    returnport = port;
  //  System.out.println(returnport + " port in port finding method");
  //  Service.getInstance().stopService();
  //  Service.getInstance().removeGateway(gateway);
    return returnport;

}

public static String getp() {
        try {
           po = getConnectedport();
            // return po;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return po;

    }

    public static String getsmsc() {

        try {
            
            try {
              Statement s = DataBase.getcConnection().createStatement();
                //  s.executeUpdate("insert into cat_details (id,document) values (" + no + ",'" + all + "') ");

                //  System.out.println(all);
             ///   s.executeUpdate("update modem_details set portn ='" + jTextField2.getText() + "' ");
                
                String ser = "";
                ResultSet r = s.executeQuery("select * from modem_details");
                if (r.first()) {
                    
                    ponew = r.getString("portn");
                    
                }
               // JOptionPane.showMessageDialog(null, ponew);
                //jTextField2.setText(ser);
        } catch (Exception e) {
            e.printStackTrace();
        }
            
            SerialModemGateway gateway = new SerialModemGateway("modem.com8",
                    ponew, 115200, "ATHandler_Huawei", "E153");

            ListPorts.modemDriver = gateway.getModemDriver();

            Service.getInstance().addGateway(gateway);

            Service.getInstance().startService();

            getModemDriver().write("AT+CSCA?\r");
            smsc = getModemDriver().getResponse();

            smsc =smsc.substring(8, 18);

           Service.getInstance().stopService();
              Service.getInstance().removeGateway(gateway);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return smsc;
    }
public void doIt() throws Exception {
    
 
        SerialModemGateway gateway = new SerialModemGateway("modem.com8",
                getConnectedport(), 115200, "ATHandler_Huawei", "E153");

     
        this.modemDriver = gateway.getModemDriver();
      
        Service.getInstance().addGateway(gateway);
     

        Service.getInstance().startService();
   
       

       

   
      

      
       getModemDriver().write("AT+CSCA?\r");
		String smsc = getModemDriver().getResponse();
		
System.out.println(smsc.substring(8,18));
      
       Service.getInstance().stopService();
    
    }
public static void main(String args[]) throws SMSLibException {
    
   // try {
   //       new ListPorts().doIt();
   // } catch (Exception e) {
    //    e.printStackTrace();
   // }
       try {
         String connectedport = getConnectedport();
      //  JOptionPane.showMessageDialog(null, connectedport);
        
        
    } catch (Exception e) {
        e.printStackTrace();
        
    }
       
       
}
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SMS;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.BasicConfigurator;
import org.smslib.AGateway;
import org.smslib.IInboundMessageNotification;
import org.smslib.IOutboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Message;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;
import smsint.DataBase_Connection;

/**
 *
 * @author dell
 */
public class ModamSMS {

    ArrayList list;
    public static SerialModemGateway gateway;

    public static String portt;
    public static String unumber;
    public static boolean kk;

    public ModamSMS(String s) {

        portt = s;
    }

    public void sendMessage(String num, String massage) {

        try {
            SerialModemGateway gateway = new SerialModemGateway("", "COM18", 9600, "", "");
            //gateway.setInbound(true);
            gateway.setOutbound(true);
            OutboundNotification outboundNotification = new OutboundNotification();
            //  InboundNotification inboundNotification = new InboundNotification();
            Service service = Service.getInstance();
            service.setOutboundMessageNotification(outboundNotification);
            //  service.setInboundMessageNotification(inboundNotification);
            service.addGateway(gateway);
            service.startService();

            OutboundMessage msg = new OutboundMessage(num, massage);
            kk = service.sendMessage(msg);

            if (kk) {
                Service.getInstance().stopService();
                Service.getInstance().removeGateway(gateway);
                
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public ArrayList sendBulkMessage(ArrayList<String> numbers, String massage, ArrayList<String> numbersname) {
        try {

          
            list = new ArrayList();
            unumber = (String) numbers.get(0);
            smsint.Birth_wish.jProgressBar1.setString(" Please Wait..........Sending to " + unumber);
      //  JOptionPane.showMessageDialog(null, (String) numbers.get(2));
            // JOptionPane.showMessageDialog(null, (String) numbers.get(1));
            // JOptionPane.showMessageDialog(null, (String) numbers.get(0));

            //String connectedport = getConnectedport(numbers.get(0), massage);
            //  System.out.println(connectedport + " port in bulsksmssend method");
            if (true) {

                BasicConfigurator.configure();

                gateway = new SerialModemGateway("", portt, 9600, "", "");
                // gateway.setInbound(true);
                gateway.setOutbound(true);

                OutboundNotification outboundNotification = new OutboundNotification();

                // InboundNotification inboundNotification = new
                // InboundNotification();
                Service service = Service.getInstance();
                service.setOutboundMessageNotification(outboundNotification);
                // service.setInboundMessageNotification(inboundNotification);
                service.addGateway(gateway);
                service.startService();

                for (int i = 0; i < numbers.size(); i++) {
                    
                      String orgmsg=massage;
                   // massage = "";
                    String num = (String) numbers.get(i);
                    unumber = num;
                    smsint.Birth_wish.jProgressBar1.setString(" Please Wait..........Sending to " + unumber);

                    orgmsg += numbersname.get(i) + "\n -- Student Payment Details --";

                    OutboundMessage msg = new OutboundMessage(num, orgmsg);
                    System.out.println(i + " " + massage);
                    Thread.sleep(1000);

                    kk = service.sendMessage(msg);

                    if (kk) {
                        // JOptionPane.showMessageDialog(null, numbers.size() +" " + i);
                        // 3 2

                        if (i == numbers.size() - 1) {
                            // last work
                            smsint.Birth_wish.jProgressBar1.setVisible(false);
                            DefaultTableModel dtm = (DefaultTableModel) smsint.Birth_wish.jTable1.getModel();

                            dtm.setRowCount(0);
                        } else {
                            DefaultTableModel dtm = (DefaultTableModel) smsint.Birth_wish.jTable1.getModel();
                            dtm.removeRow(i);
                        }

                        ResultSet cs_rs1 = DataBase_Connection.getData("SELECT * FROM member where mobilen= '" + unumber + "' ");
                        // String cont = jTextField2.getText();
                        if (cs_rs1.next()) {

                            String mem_id = cs_rs1.getString("member_id");
                            String nam = cs_rs1.getString("name");
                            String[] s = orgmsg.split("\n");

                            //DataBase_Connection.putdata("INSERT INTO wish_out (mem_id,name,messagec,mobile,send_date,stime) VALUES ('" + mem_id + "','" + nam + "','" + s[0] + "','" + unumber + "','" + smsint.Birth_wish.date.getText() + "','" + smsint.Birth_wish.timel.getText() + "')");

                        }

                        smsint.Birth_wish.jProgressBar1.setString(" Send to " + unumber);
                        // JOptionPane.showMessageDialog(null, "Sent Success To " +unumber);
                    } else {
                        smsint.Birth_wish.jProgressBar1.setVisible(false);
                        JOptionPane.showMessageDialog(null, "Not Sent To " + unumber);
                    }
                    // 
                    list.add(num + " succes\n");

                }
                Service.getInstance().stopService();

                Service.getInstance().removeGateway(gateway);
            } else {
                JOptionPane.showMessageDialog(null, "Please make ! \n Modam is correctly inseted\nYou hava SMS package\n ");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " Error happen");
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));

            writfile(sw.toString());

        }
        return list;

    }

    public ArrayList sendBulkMessage(ArrayList<String> numbers, String massage) {
        try {

            list = new ArrayList();
            unumber = (String) numbers.get(0);
             UIDesigns.Studentpayment.jProgressBar1.setString(" Please Wait..........Sending to " + unumber);
      //  JOptionPane.showMessageDialog(null, (String) numbers.get(2));
            // JOptionPane.showMessageDialog(null, (String) numbers.get(1));
            // JOptionPane.showMessageDialog(null, (String) numbers.get(0));

            //String connectedport = getConnectedport(numbers.get(0), massage);
            //  System.out.println(connectedport + " port in bulsksmssend method");
            if (true) {

                BasicConfigurator.configure();

                gateway = new SerialModemGateway("", "COM18", 9600, "", "");
                // gateway.setInbound(true);
                gateway.setOutbound(true);

                OutboundNotification outboundNotification = new OutboundNotification();

                // InboundNotification inboundNotification = new
                // InboundNotification();
                Service service = Service.getInstance();
                service.setOutboundMessageNotification(outboundNotification);
                // service.setInboundMessageNotification(inboundNotification);
                service.addGateway(gateway);
                service.startService();

                for (int i = 0; i < numbers.size(); i++) {

                    String num = (String) numbers.get(i);
                    unumber = num;
                    UIDesigns.Studentpayment.jProgressBar1.setString(" Please Wait..........Sending to " + unumber);
                    OutboundMessage msg = new OutboundMessage(num, massage);
                    System.out.println(i + " " + massage);
                    Thread.sleep(1000);

                    kk = service.sendMessage(msg);

                    if (kk) {
                        // JOptionPane.showMessageDialog(null, numbers.size() +" " + i);
                        // 3 2

                        if (i == numbers.size() - 1) {
                            // last work
                            UIDesigns.Studentpayment.jProgressBar1.setVisible(false);
                            DefaultTableModel dtm = (DefaultTableModel) UIDesigns.Studentpayment.jTable1.getModel();

                            dtm.setRowCount(0);
                        } else {
                            DefaultTableModel dtm = (DefaultTableModel) UIDesigns.Studentpayment.jTable1.getModel();
                            dtm.removeRow(i);
                        }

                       

                        UIDesigns.Studentpayment.jProgressBar1.setString(" Send to " + unumber);
                        // JOptionPane.showMessageDialog(null, "Sent Success To " +unumber);
                    } else {
                        UIDesigns.Studentpayment.jProgressBar1.setVisible(false);
                        JOptionPane.showMessageDialog(null, "Not Sent To " + unumber);
                    }
                    // 
                    list.add(num + " Sms send succes\n");

                }
                Service.getInstance().stopService();

                Service.getInstance().removeGateway(gateway);
            } else {
                JOptionPane.showMessageDialog(null, "Please make ! \n Modam is correctly inseted\nYou hava SMS package\n ");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e + " Error happen");
            e.printStackTrace();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));

            writfile(sw.toString());

        }
        return list;

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
            System.out.println(outboundMessage);
        }
    }

//public String getConnectedport(String num, String msge)
//        throws TimeoutException, GatewayException, SMSLibException, IOException, InterruptedException, Exception {
//
//    ArrayList<String> allports = new ArrayList<>();
//
//    Enumeration ports = CommPortIdentifier.getPortIdentifiers();
//    while (ports.hasMoreElements()) {
//        CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
//        String type;
//        switch (port.getPortType()) {
//        case CommPortIdentifier.PORT_PARALLEL:
//            type = "Parallel";
//            break;
//        case CommPortIdentifier.PORT_SERIAL:
//            allports.add(port.getName());
//            type = "Serial";
//            break;
//        default: /// Shouldn't happen
//            type = "Unknown";
//            break;
//        }
//        System.out.println(port.getName() + ": " + type);
//        allports.add(port.getName());
//    }
//    String connectedport = null;
//    for (int a = 0; a < allports.size(); a++) {
//        connectedport = getport(allports.get(a), num, msge);
//        if (connectedport != null) {
//            a = allports.size();
//            System.out.println(connectedport + " port in for loop");
//
//        }
//
//    }
//
//    return connectedport;
//
//}
//private String getport(String port, String num, String msge)
//        throws TimeoutException, GatewayException, SMSLibException, IOException, InterruptedException, Exception {
//    String returnport = null;
//
//    SerialModemGateway gateway = new SerialModemGateway("", port, 9600, "", "");
//    gateway.setInbound(true);
//    gateway.setOutbound(true);
//    OutboundNotification outboundNotification = new OutboundNotification();
//    InboundNotification inboundNotification = new InboundNotification();
//    Service service = Service.getInstance();
//    service.setOutboundMessageNotification(outboundNotification);
//    service.setInboundMessageNotification(inboundNotification);
//    try {
//        service.addGateway(gateway);
//        service.startService();
//
//      //  OutboundMessage msg = new OutboundMessage(num, msge);
//      //  service.sendMessage(msg);
//        returnport = port;
//        System.out.println(returnport + " port in port finding method");
//
//    } catch (GatewayException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//    } catch (TimeoutException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//    } catch (SMSLibException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//    } catch (IOException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//    } catch (InterruptedException e) {
//        // TODO Auto-generated catch block
//        e.printStackTrace();
//    }
//    Service.getInstance().stopService();
//    Service.getInstance().removeGateway(gateway);
//    return returnport;
//
//}
    public static void main(String args[]) {

//        BasicConfigurator.configure();
//
//        ModamSMS app = new ModamSMS();
//        ArrayList<String> numbers = new ArrayList<>();
//
//        numbers.add("+94719648738");
//
//        numbers.add("+94788346116");
//
//        try {
//           //  JOptionPane.showMessageDialog(null, numbers.size());
//
//            // app.sendMessage("+923075142199","plz Allah it shoud work");
//            ArrayList sendBulkMessage = app.sendBulkMessage(numbers, "erorrr");
//
//            if (kk) {
//                JOptionPane.showMessageDialog(null, sendBulkMessage, "SMS INFO", JOptionPane.INFORMATION_MESSAGE);
//
//            } else {
//
//                JOptionPane.showMessageDialog(null, "Messege Not Sent Trying Faild, Please Resend", "SMS INFO", JOptionPane.ERROR_MESSAGE);
//            }
//
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e + " Error happen");
//            StringWriter sw = new StringWriter();
//            e.printStackTrace(new PrintWriter(sw));
//
//            writfile(sw.toString());
//        }
    }

    public static void writfile(String content) {

        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            fw = new FileWriter("Error.txt");
            bw = new BufferedWriter(fw);
            bw.write(content);

            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }
}

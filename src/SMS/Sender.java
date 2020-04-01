package SMS;

import java.util.Date;
import org.ajwcc.pduUtils.gsm3040.PduUtils;
import javax.swing.JOptionPane;
import org.smslib.Service;
import org.smslib.modem.AModemDriver;
import org.smslib.modem.SerialModemGateway;

public class Sender implements Runnable {

    private static final long STANDARD = 500;
    private static final long LONG = 2000;
    private static final long VERYLONG = 20000;

    public static SerialConnection mySerial = null;

    static final private char cntrlZ = (char) 26;
    String in, out;
    Thread aThread = null;
    private long delay = STANDARD;
    String recipient = null;
    String message = null;

    AModemDriver modemDriver;
    private String smsc;

    public AModemDriver getModemDriver() {
        return this.modemDriver;
    }

    String csca = ListPorts.smsc; // the message center
    private SerialParameters defaultParameters = new SerialParameters(ListPorts.getp(), 19200, 0, 0, 8, 1, 0);
    public int step;
    public int status = -1;
    public long messageNo = -1;
    private String po;

    public Sender(String recipient, String message) {

        this.recipient = recipient;
        this.message = message;

    }

    /**
     * connect to the port and start the dialogue thread
     */
    public int send() throws Exception {

        SerialParameters params = defaultParameters;

        mySerial = new SerialConnection(params);

        mySerial.openConnection();

        aThread = new Thread(this);

        aThread.start();
        //log("start");

        return 0;
    }

    

    /**
     * implement the dialogue thread, message / response via steps, handle time
     * out
     */
    @Override
    public void run() {

        boolean timeOut = false;
        long startTime = (new Date()).getTime();

        while ((step < 7) && (!timeOut)) {
//      log(""+((new Date()).getTime() - startTime);
            //check where we are in specified delay
            timeOut = ((new Date()).getTime() - startTime) > delay;

            //if atz does not work, type to send cntrlZ and retry, in case a message was stuck
            if (timeOut && (step == 1)) {
                step = -1;
                mySerial.send("" + cntrlZ);
            }

            //read incoming string
            String result = mySerial.getIncommingString();

//    log ("<- "+result+"\n--------");
            int expectedResult = -1;

            try {
                //log ("Step:"+step);

                switch (step) {
                    case 0:

                        mySerial.send("atz");
                        delay = LONG;
                        startTime = (new Date()).getTime();
                        break;

                    case 1:
                        delay = STANDARD;
                        mySerial.send("ath0");
                        startTime = (new Date()).getTime();
                        break;
                    case 2:
                        expectedResult = result.indexOf("OK");

                        //log ("received ok ="+expectedResult);
                        if (expectedResult > -1) {
                            mySerial.send("at+cmgf=1");
                            startTime = (new Date()).getTime();
                        } else {
                            step = step - 1;
                        }
                        break;
                    case 3:
                        expectedResult = result.indexOf("OK");

                        // log ("received ok ="+expectedResult);
                        if (expectedResult > -1) {
                            mySerial.send("at+csca=\"" + csca + "\"");
                            startTime = (new Date()).getTime();
                        } else {
                            step = step - 1;
                        }

                        break;
                    case 4:
                        expectedResult = result.indexOf("OK");

                        // log ("received ok ="+expectedResult);
                        if (expectedResult > -1) {
                            mySerial.send("at+cmgs=\"" + recipient + "\"");
                            startTime = (new Date()).getTime();
                        } else {
                            step = step - 1;
                        }

                        break;
                    case 5:
                        expectedResult = result.indexOf(">");

                        //log ("received ok ="+expectedResult);
                        if (expectedResult > -1) {
                            mySerial.send(message + cntrlZ);
                            startTime = (new Date()).getTime();
                        } else {
                            step = step - 1;
                        }
                        delay = VERYLONG;//waitning for message ack

                        break;

                    case 6:
                        expectedResult = result.indexOf("OK");
                        //read message number
                        if (expectedResult > -1) {
                            int n = result.indexOf("CMGS:");
                            result = result.substring(n + 5);
                            n = result.indexOf("\n");
                            status = 0;
                            messageNo = Long.parseLong(result.substring(0, n).trim());

                            log("sent message no:" + messageNo);

                        } else {
                            step = step - 1;
                        }

                        break;
                }
                step = step + 1;

                aThread.sleep(100);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        mySerial.closeConnection();

        //if timed out set status
        if (timeOut) {
            status = -2;
            log("*** time out at step " + step + "***");
        }
    }

    public String receiveMessage() throws Exception {
        int expectedResult = 0;

        SerialParameters params = defaultParameters;

        mySerial = new SerialConnection(params);

        mySerial.openConnection();

        // step 1
        //  mySerial.send("atz");
        delay = LONG;

        Thread.sleep(100);
        //aThread.sleep(100);

        String result = mySerial.getIncommingString();
        //to text format
        mySerial.send("AT+CMGF=1");

        // step 2
        delay = STANDARD;

        // mySerial.send("AT+CPMS=?");
        mySerial.send("at+cmgf=0");
        delay = STANDARD;

        //  for (int i = 1; i < 10; i++) {
        ///   mySerial.send("AT+CMGL=ALL");
        //  
        Thread.sleep(100);

        //   }
        // step 3
        result = mySerial.getIncommingString();
        expectedResult = result.indexOf("OK");

        //log ("received ok ="+expectedResult);
//        if (expectedResult < -1) {
//          //  mySerial.send("at+cmgf=1");
//            //startTime=(new Date()).getTime();
//        }
        // step 4
//        result = mySerial.getIncommingString();
//        expectedResult = result.indexOf("OK");
        //log ("received ok ="+expectedResult);
//        if (expectedResult > -1) {
//            //mySerial.send("at+cmgl=\"ALL\"");
//          //  mySerial.send("at+cmgr=1");
//            //startTime=(new Date()).getTime();
//        }
        Thread.sleep(100);
        result = mySerial.getIncommingString();
//        byte[] responded = PduUtils.pduToBytes(result);
//        String bite = PduUtils.bytesToBits(responded);
//        System.out.println(bite);
//
//        String resp = PduUtils.decode7bitEncoding(null, responded);
//        System.out.println(resp);

        // org.ajwcc.pduUtils.gsm3040.Pdu pdu = new org.ajwcc.pduUtils.gsm3040.PduParser().parsePdu(result);
        //  JOptionPane.showMessageDialog(null, result, "USSD", JOptionPane.INFORMATION_MESSAGE);
        //    System.out.println(result);
        mySerial.closeConnection();
        return result;
    }

    /**
     * logging function, includes date and class name
     */
    private void log(String s) {
        System.out.println(new java.util.Date() + ":" + this.getClass().getName() + ":" + s);
        Te.msg(s);
    }

}

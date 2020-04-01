package smsint;

import SMS.ModamSMS;
import static SMS.ModamSMS.kk;
import static SMS.ModamSMS.writfile;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.log4j.BasicConfigurator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hitan Bandara
 */
public class Birth_wish extends javax.swing.JFrame {

    int starrowcount = 0;
    int clickcount = 0;

    int clickcount1 = 0;
    public static int c = 0;
    String name = "";
    String owid = "";
    String vno = "";
    String type = "";
    public static String mobile = "";

    String exp = "";

    String documet = "";
    String all = "";

    int no = 0;

    /**
     * Creates new form sms
     */
    private static void createAndShowGUI() throws Exception {

    }

    public static String get_port() {
        String ser = "";
        try {
            //  Statement s = DataBase.getcConnection().createStatement();
            //  s.executeUpdate("insert into cat_details (id,document) values (" + no + ",'" + all + "') ");

            //  System.out.println(all);
            ///   s.executeUpdate("update modem_details set portn ='" + jTextField2.getText() + "' ");
            ResultSet r = DataBase_Connection.getData("select * from modem_details");
            if (r.first()) {

                ser = r.getString("portn");

            }

            // jTextField2.setText(ser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ser;
//To change body of generated methods, choose Tools | Templates.
    }

    public void removeSelectedRows(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int[] rows = table.getSelectedRows();
        for (int i = 0; i < rows.length; i++) {
            model.removeRow(rows[i] - i);
        }
    }

    public Birth_wish() {
        initComponents();
        Table_Search();
        jProgressBar1.setVisible(false);
        datatime();
        date.setVisible(false);
        timel.setVisible(false);

        jTextField2.setText("Happy Birthday To ");
        try {
            createAndShowGUI();
        } catch (Exception ex) {
            //Logger.getLogger(customers.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void datatime() {

        Thread time = new Thread() {
            public void run() {

                while (true) {

                    SimpleDateFormat ft = new SimpleDateFormat(" hh:mm:ss a  ");
                    SimpleDateFormat si = new SimpleDateFormat("YYYY.MM.dd");
                    timel.setText(ft.format(new Date()));
                    date.setText(si.format(new Date()));

                    try {
                        sleep(1000);
                    } catch (InterruptedException ex) {

                    }

                }

            }
        };
        time.start();

    }

    private void Table_Search() {

        SimpleDateFormat mon = new SimpleDateFormat("MMM");
        SimpleDateFormat da = new SimpleDateFormat("d");

        String month = mon.format(new Date());

        String dat = da.format(new Date());

        String ss = month + "-" + dat;
        // System.out.println(dat + "  " +month);

        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);
        try {

            ResultSet rs = DataBase_Connection.getData("SELECT*FROM member where birthd LIKE '_____" + ss + "'");
            int noo = 1;
            while (rs.next()) {
                Vector v = new Vector();
                // jLabel2.setText(rs.getString("id"));

                if (!rs.getString("mobilen").equals("-")) {

                    v.add(noo);
                    ++noo;
                    v.add(rs.getString("member_id"));
                    v.add(rs.getString("nic"));
                    v.add(rs.getString("name"));
                    v.add(rs.getString("mobilen"));
                    v.add(rs.getString("residence"));
                    v.add(rs.getString("birthd"));

                    //   v.add(rs2.getString("gamo"));
                    dtm.addRow(v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void all() {
        //  String oo = jComboBox1.getSelectedItem().toString();
        DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        dtm.setRowCount(0);
        try {

            ResultSet rs = DataBase_Connection.getData("SELECT*FROM member");

            while (rs.next()) {
                Vector v = new Vector();
                // jLabel2.setText(rs.getString("id"));

                v.add(rs.getString("id"));
                v.add(rs.getString("member_id"));
                v.add(rs.getString("nic"));
                v.add(rs.getString("name"));
                v.add(rs.getString("mobilen"));
                v.add(rs.getString("residence"));

                //   v.add(rs2.getString("gamo"));
                dtm.addRow(v);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel3 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextField2 = new javax.swing.JTextArea();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        jButton4 = new javax.swing.JButton();
        timel = new javax.swing.JLabel();
        date = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Birthday Wish");
        setMaximumSize(new java.awt.Dimension(982, 424));
        setMinimumSize(new java.awt.Dimension(982, 424));
        setPreferredSize(new java.awt.Dimension(982, 424));
        setResizable(false);
        getContentPane().setLayout(null);

        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 153));
        jLabel3.setText("Body");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(30, 80, 80, 130);

        jButton6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jButton6.setText("Remove Row");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton6);
        jButton6.setBounds(580, 290, 130, 40);

        jTextField2.setColumns(20);
        jTextField2.setRows(5);
        jScrollPane4.setViewportView(jTextField2);

        getContentPane().add(jScrollPane4);
        jScrollPane4.setBounds(120, 80, 320, 200);

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });
        getContentPane().add(jTextField1);
        jTextField1.setBounds(120, 30, 320, 30);

        jLabel4.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 153));
        jLabel4.setText("Number");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(30, 30, 90, 30);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Member ID", "Nic", "Name", "Mobile  No", "Residese No", "Birthday"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setMinWidth(50);
            jTable1.getColumnModel().getColumn(0).setMaxWidth(50);
            jTable1.getColumnModel().getColumn(6).setMinWidth(100);
            jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
        }

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(460, 30, 510, 250);

        jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 0, 0));
        jButton3.setText("Send Wish");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(840, 290, 130, 40);

        jProgressBar1.setBackground(new java.awt.Color(0, 0, 0));
        jProgressBar1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jProgressBar1.setForeground(new java.awt.Color(255, 0, 102));
        jProgressBar1.setPreferredSize(new java.awt.Dimension(146, 20));
        jProgressBar1.setString("Sending");
        jProgressBar1.setStringPainted(true);
        getContentPane().add(jProgressBar1);
        jProgressBar1.setBounds(0, 370, 980, 24);

        jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton4.setText("OutBox");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton4);
        jButton4.setBounds(720, 290, 110, 40);

        timel.setText("time");
        getContentPane().add(timel);
        timel.setBounds(40, 70, 20, 14);

        date.setText("date");
        getContentPane().add(date);
        date.setBounds(10, 70, 22, 14);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        if (clickcount == 0) {
            clickcount = 1;
            jButton3.setEnabled(false);

            starrowcount = jTable1.getRowCount();

            new Thread(new Runnable() {

                @Override
                public void run() {
                    try {

                        if (jTable1.getRowCount() == 0) {

                            JOptionPane.showMessageDialog(null, "Birthday Peoples Not Available Today", "Inane error",
                                    JOptionPane.ERROR_MESSAGE);
                            clickcount = 0;

                            jButton3.setEnabled(true);
                        } else if (jTable1.getRowCount() == 1) {
                            String body = jTextField2.getText();
                            if (body.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Please type massage body");
                                clickcount = 0;

                                jButton3.setEnabled(true);

                            } else {
                                BasicConfigurator.configure();
                                clickcount = 1;
                                jButton3.setEnabled(false);

                                Object oo = jTable1.getValueAt(0, 4);
                                mobile = (String) oo;

                                Object oo1 = jTable1.getValueAt(0, 3);
                                String bname = (String) oo1;

                                for (int i = 0; i <= jProgressBar1.getMaximum(); i++) {
                                    jProgressBar1.setVisible(true);
                                    jProgressBar1.setValue(i);

                                    if (i == 1) {
                                        jProgressBar1.setString("Starting Sending to " + mobile);
                                        // lbl_loading.setText("Welcome " + txt_un.getSelectedItem());
                                    }
                                    if (i == 20) {
                                        jProgressBar1.setString(" Please Wait. Sending to" + mobile);
                                        // lbl_loading.setText("Welcome " + txt_un.getSelectedItem() + ",Arranging Databases");
                                    }
                                    if (i == 40) {
                                        jProgressBar1.setString(" Please Wait...Sending to " + mobile);
                                        // lbl_loading.setText("Welcome " + txt_un.getSelectedItem() + ",Loading Images");
                                    }
                                    if (i == 60) {
                                        jProgressBar1.setString(" Please Wait......Sending to " + mobile);
                                        // i = 80;
                                    }
                                    if (i == 80) {
                                        jProgressBar1.setString(" Please Wait..........Sending to " + mobile);

                                        //  lbl_loading.setText("Welcome " + txt_un.getSelectedItem() + ",Loading Home Page");
                                    }
                                    if (i == 100) {
                                        ModamSMS app = new ModamSMS(get_port());
                                        body += bname + "\n -- CGTTI ALUMNI --";

                                        app.sendMessage(mobile, body);

                                        body += bname;
                                        //   ArrayList sendBulkMessage = app.sendBulkMessage(numbers, jTextField2.getText());

                                        if (kk) {
                                            jProgressBar1.setVisible(false);

                                            DataBase_Connection.putdata("INSERT INTO wish_out (mem_id,name,messagec,mobile,send_date,stime) VALUES ('" + jTable1.getValueAt(0, 1) + "','" + jTable1.getValueAt(0, 3) + "','" + body + "','" + mobile + "','" + date.getText() + "','" + timel.getText() + "')");

                                            jProgressBar1.setString(" Send Success To " + mobile);
                                            DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
                                            dtm.removeRow(0);
  jProgressBar1.setVisible(false);
                                            JOptionPane.showMessageDialog(null, jTextField1.getText() + " " + "Send Success", "SMS INFO", JOptionPane.INFORMATION_MESSAGE);
                                            jButton3.setEnabled(true);
                                            //      
                                            //   
                                            clickcount = 0;
                                        } else {
                                            jButton3.setEnabled(true);
                                            //

                                            clickcount = 0;
                                            JOptionPane.showMessageDialog(null, "Messege Not Sent Trying Faild, Please Resend", "SMS INFO", JOptionPane.ERROR_MESSAGE);
                                            jProgressBar1.setVisible(false);
                                        }

                                    }

                                    try {
                                        Thread.sleep(15);
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }

                            }

//JOptionPane.showMessageDialog(null, "one");
                        } else {
                            String body = jTextField2.getText();
                            if (body.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Please type massage body");
                                clickcount = 0;

                                jButton3.setEnabled(true);
                            } else {
                                BasicConfigurator.configure();
                                clickcount = 1;
                                jButton3.setEnabled(false);

                                ArrayList<String> numbersname = new ArrayList();

                                for (int ii = 0; ii < starrowcount; ii++) {

                                    Object oo1 = jTable1.getValueAt(ii, 3);
                                    String numm1 = (String) oo1;

                                    numbersname.add(ii, numm1);
                                    //  JOptionPane.showMessageDialog(null, ii);

                                }

                                ArrayList<String> numbers = new ArrayList();

                                for (int ii = 0; ii < starrowcount; ii++) {

                                    Object oo = jTable1.getValueAt(ii, 4);
                                    String numm = (String) oo;

                                    numbers.add(ii, numm);
                                    //  JOptionPane.showMessageDialog(null, ii);

                                }

                                for (int i = 0; i <= jProgressBar1.getMaximum(); i++) {
                                    jProgressBar1.setVisible(true);
                                    jProgressBar1.setValue(i);

                                    if (i == 1) {
                                        jProgressBar1.setString("Starting Sending");
                                        // lbl_loading.setText("Welcome " + txt_un.getSelectedItem());
                                    }
                                    if (i == 20) {
                                        jProgressBar1.setString(" Please Wait. Sending");
                                        // lbl_loading.setText("Welcome " + txt_un.getSelectedItem() + ",Arranging Databases");
                                    }
                                    if (i == 40) {
                                        jProgressBar1.setString(" Please Wait...Sending");
                                        // lbl_loading.setText("Welcome " + txt_un.getSelectedItem() + ",Loading Images");
                                    }
                                    if (i == 60) {
                                        jProgressBar1.setString(" Please Wait......Sending");
                                        // i = 80;
                                    }
                                    if (i == 80) {
                                        jProgressBar1.setString(" Please Wait..........Sending");

                                        //  lbl_loading.setText("Welcome " + txt_un.getSelectedItem() + ",Loading Home Page");
                                    }
                                    if (i == 100) {

                                        ModamSMS app = new ModamSMS(get_port());

                                        ArrayList sendBulkMessage = app.sendBulkMessage(numbers, body, numbersname);
                                        //jProgressBar1.setString(" Please Wait..........Sending to " + ModamSMS.unumber);

                                        if (kk) {
                                            JOptionPane.showMessageDialog(null, "All Wish Messages Sent success", "SMS INFO", JOptionPane.INFORMATION_MESSAGE);
                                            jButton3.setEnabled(true);
                                            jProgressBar1.setVisible(false);
                                            clickcount = 0;
                                        } else {

                                            jButton3.setEnabled(true);

                                            clickcount = 0;
                                            JOptionPane.showMessageDialog(null, "Messege Not Sent Trying Faild, Please Resend", "SMS INFO", JOptionPane.ERROR_MESSAGE);
                                            jProgressBar1.setVisible(false);
                                        }

                                    }

                                    try {
                                        Thread.sleep(15);
                                    } catch (Exception e) {
                                        System.out.println(e);
                                    }
                                }

                            }

                            // JOptionPane.showMessageDialog(null, ">one");  
                        }

                        // JOptionPane.showMessageDialog(null, senderName+password1);
                        // ss //  toatal_amount = toatal_amount + Double.parseDouble(jTable1.getValueAt(ii, 5).toString());
                        //   Total_due = Total_due + Double.parseDouble(jTable1.getValueAt(ii, 6).toString());
                    } catch (Exception ex) {
                        jButton3.setEnabled(true);

                        clickcount = 0;
                        //  JOptionPane.showMessageDialog(null, "Message Not Send to" + name + "(" + mobile + ")", "Warning", JOptionPane.ERROR_MESSAGE);
                        JOptionPane.showMessageDialog(null, ex + " Error happen");
                        StringWriter sw = new StringWriter();
                        ex.printStackTrace(new PrintWriter(sw));

                        writfile(sw.toString());
                        jProgressBar1.setVisible(false);

                        ex.printStackTrace();

                    }
                }

                //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }).start();

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //new Sms_Outbox_wish().setVisible(true);        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() == 2) {
            int r = jTable1.getSelectedRow();
            System.out.println(r);
            Object oo = jTable1.getValueAt(r, 4);
            String name = (String) oo;

            // int cidd = Integer.parseInt(s);
            jTextField1.setText(name);

            // jTextArea1.setText(addr);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // int con = count;
        //t connew = con - 1;
        //  count = connew;
        removeSelectedRows(jTable1);
        //  DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        // dtm.removeRow(jTable1.getSelectedRow());
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped
        if (Character.isDigit(evt.getKeyChar())) {

        } else {
            evt.consume();

        }

        if (jTextField1.getText().length() == 10) {
            evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1KeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Birth_wish.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Birth_wish.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Birth_wish.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Birth_wish.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Birth_wish().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel date;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    public static javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane4;
    public static javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextArea jTextField2;
    public static javax.swing.JLabel timel;
    // End of variables declaration//GEN-END:variables
}

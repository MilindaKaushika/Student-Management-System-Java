/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIDesigns;

import Method.Student;
import Method.StudentDetails;
import javax.swing.JOptionPane;
import Method.CourseDetails;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import java.util.Date;

/**
 * /**
 *
 * @author Home-PC
 */
public class StudentRagistration extends javax.swing.JPanel {

    private Vector<Vector<String>> data; //Used for data from database
    private Vector<String> header;
    private Vector<String> Student;
    private Vector<Vector<String>> Studentdata;
    private Component rootPane;

    String img, ss;
    static StringBuilder photo;

    private boolean isValidDate(String actualdate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class SnapMeAction extends AbstractAction {

        public SnapMeAction() {
            super("Snapshot");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                File file = new File(String.format("test-%d.jpg", System.currentTimeMillis()));
                ImageIO.write(webcam.getImage(), "JPG", file);
                System.out.println("Image saved in " + file.getAbsolutePath());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class StartAction extends AbstractAction implements Runnable {

        public StartAction() {
            super("Start");
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            btStart.setEnabled(false);
            btSnapMe.setEnabled(true);

            executor.execute(this);
        }

        @Override
        public void run() {
            panel.start();
        }
    }

    private Executor executor = Executors.newSingleThreadExecutor();

    private Dimension captureSize = WebcamResolution.VGA.getSize();
    private Dimension displaySize = WebcamResolution.QQVGA.getSize();

    private Webcam webcam = Webcam.getDefault();
    private WebcamPanel panel = new WebcamPanel(webcam, displaySize, false);

    private JButton btSnapMe = new JButton(new SnapMeAction());
    private JButton btStart = new JButton(new StartAction());

    /**
     * Creates new form Student
     */
    public StudentRagistration() {
        initComponents();

        fillCoursecode();
        fillCourseNames();
        header = new Vector<String>();
        header.add("sid");
        header.add("fname");
        header.add("mname");
        header.add("lname");
        header.add("DOB");
        header.add("phone_num");
        header.add("code");
        header.add("image");
        header.add("Course");
        header.add("username");
        header.add("password");
         header.add("usertype");
        loadStudentTable();

    }

    private void loadStudentTable() {
        header = new Vector<String>();
        header.add("sid");
        header.add("fname");
        header.add("mname");
        header.add("lname");
        header.add("DOB");
        header.add("phone_num");
        header.add("code");
        header.add("Course");
        header.add("Email");
         header.add("username");
        header.add("password");
         header.add("usertype");
        StudentDetails dao = new StudentDetails();
        data = dao.getStudentDetails();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                data, header));
        jScrollPane1.setViewportView(jTable1);

    }

    private void fillCoursecode() {
        CourseDetails dao = new CourseDetails();
        ArrayList codeList = dao.getCourse();

        Iterator i = codeList.iterator();

        while (i.hasNext()) {
            cscmb.addItem(i.next());
        }

        String cscode = cscmb.getSelectedItem().toString();

    }

    private void fillCourseNames() {
        CourseDetails dao = new CourseDetails();
        ArrayList NameList = dao.getCourseName();

        Iterator i = NameList.iterator();

        while (i.hasNext()) {
            jComboBox1.addItem(i.next());
        }

        String csName = jComboBox1.getSelectedItem().toString();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jFileChooser1 = new javax.swing.JFileChooser();
        jLabel92 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTextField8 = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMname = new javax.swing.JTextField();
        txtLname = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtdateofbirth = new javax.swing.JTextField();
        txtPhonenu = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        Image = new javax.swing.JPanel();
        Upload = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        cam_panel = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        passwordtxt1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        usernametxt = new javax.swing.JTextField();
        designationcmb = new javax.swing.JComboBox();
        jLabel16 = new javax.swing.JLabel();
        cscmb = new javax.swing.JComboBox();
        jLabel38 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel98 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        Submit = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();

        jLabel2.setBackground(new java.awt.Color(0, 153, 51));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Module Details");
        jLabel2.setOpaque(true);

        jLabel92.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel92.setForeground(new java.awt.Color(255, 0, 0));
        jLabel92.setText("*");

        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(255, 0, 0));
        jLabel96.setText("*");

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(255, 0, 0));
        jLabel97.setText("*");

        setPreferredSize(new java.awt.Dimension(780, 470));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(0, 153, 51));
        jLabel3.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Student Details");
        jLabel3.setOpaque(true);
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1090, 50));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Student ID "));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField8, javax.swing.GroupLayout.DEFAULT_SIZE, 578, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 610, 70));
        add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 200, -1));

        jLabel4.setText("First Name");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, 20));

        jLabel5.setText("Middle Name");
        add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, -1, 20));
        add(txtMname, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, 200, -1));
        add(txtLname, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 200, -1));

        jLabel6.setText("Last Name");
        add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, -1, 20));

        jLabel7.setText("Course No");
        add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, -1, 20));

        jLabel8.setText("Date of Birth");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 230, -1, 20));

        txtdateofbirth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtdateofbirthActionPerformed(evt);
            }
        });
        add(txtdateofbirth, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 230, 200, -1));

        txtPhonenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPhonenuActionPerformed(evt);
            }
        });
        txtPhonenu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhonenuKeyTyped(evt);
            }
        });
        add(txtPhonenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 230, 200, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 450, 1040, 190));

        jLabel9.setText("Phone Number ");
        add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, 20));

        Image.setBackground(new java.awt.Color(255, 255, 255));
        Image.setBorder(javax.swing.BorderFactory.createTitledBorder("Image"));
        Image.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Upload.setText("Upload");
        Upload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UploadActionPerformed(evt);
            }
        });
        Image.add(Upload, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 70, -1));

        jButton5.setText("Cam");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        Image.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 79, -1));

        cam_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setBackground(new java.awt.Color(204, 204, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/img.jpg"))); // NOI18N
        jLabel10.setToolTipText("");
        jLabel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jLabel10.setOpaque(true);
        jLabel10.setPreferredSize(new java.awt.Dimension(250, 136));
        cam_panel.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 170));

        Image.add(cam_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 260, -1));

        jButton6.setText("Save");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        Image.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 70, -1));

        add(Image, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 80, 300, 260));

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 200, -1));

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        add(jComboBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 260, 200, -1));

        jLabel11.setText("Course");
        add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, 20));

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, -1, 20));

        jLabel12.setText("Email");
        add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 200, -1, 20));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "login Details"));

        jLabel15.setText("Password");

        jLabel14.setText("Username");

        designationcmb.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        designationcmb.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Admin", "Student" }));
        designationcmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                designationcmbActionPerformed(evt);
            }
        });

        jLabel16.setText("Select Type");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16)
                            .addComponent(jLabel14))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernametxt)
                            .addComponent(passwordtxt1)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 80, Short.MAX_VALUE)
                        .addComponent(designationcmb, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(designationcmb)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(usernametxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordtxt1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 290, 290, 140));

        add(cscmb, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 260, 200, -1));

        jLabel38.setText("(yyyy/mm/dd)");
        add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 230, -1, 20));

        jLabel93.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(255, 0, 0));
        jLabel93.setText("*");
        add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, 10, 20));

        jLabel98.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(255, 0, 0));
        jLabel98.setText("*");
        add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 10, 20));

        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(255, 0, 0));
        jLabel95.setText("*");
        add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 170, -1, 20));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Details"));

        jButton4.setText("Clear");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Refresh-icon.png"))); // NOI18N
        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/Apps-system-software-update-icon.png"))); // NOI18N
        jButton1.setText("Update");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Submit.setIcon(new javax.swing.ImageIcon("C:\\Users\\Home-PC\\Documents\\StudentRegSystem\\src\\Image\\1460830381_Save.ico")); // NOI18N
        Submit.setText("Submit");
        Submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/delete-icon.png"))); // NOI18N
        jButton3.setText("Delete");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(Submit)
                        .addGap(5, 5, 5)
                        .addComponent(jButton1)
                        .addGap(3, 3, 3)
                        .addComponent(jButton2))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(7, 7, 7)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Submit)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(5, 5, 5)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 380, 140));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setOpaque(true);
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 660));

        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(255, 0, 0));
        jLabel94.setText("*");
        add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 170, 10, 20));
    }// </editor-fold>//GEN-END:initComponents

    private void SubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitActionPerformed
        // TODO add your handling code here:
         File f = jFileChooser1.getSelectedFile();
        String image = f.getAbsolutePath();
        image = image.replace("\\", "/");
        
        String sid = jTextField8.getText();
        int si = Integer.valueOf(sid);
        String fname = txtName.getText();
        String mname = txtMname.getText();
        String lname = txtLname.getText();
        String DOB = txtdateofbirth.getText();
         String phone_num = txtPhonenu.getText();
        String code = cscmb.getSelectedItem().toString();
     
        String Course = jComboBox1.getSelectedItem().toString();
        String Email = jTextField1.getText();
        boolean status = StudentDetails.email_Validation(jTextField1.getText());
        String username = usernametxt.getText();
        String password = passwordtxt1.getText();
        String usertype = designationcmb.getSelectedItem().toString();
        if (fname.isEmpty() || mname.isEmpty() || lname.isEmpty()) {

            JOptionPane.showMessageDialog(rootPane, "Please Fill Requierd Fields Before Submit");

        } else {
        
        int x = JOptionPane.showConfirmDialog(rootPane, "Are You Sure You want to Save This Recode?");
        if (x == 0) {
            Student cs = new Student(si, fname, mname, lname, DOB, phone_num, code, image, Course, Email, username, password,usertype);

            StudentDetails dao = new StudentDetails();
            dao.addStudent(cs);
            jTextField8.setText("");
            txtName.setText("");
            txtMname.setText("");
            txtLname.setText("");
            txtPhonenu.setText("");
            txtdateofbirth.setText("");
            cscmb.setSelectedItem("");
            jLabel10.setText("");
            jComboBox1.setSelectedItem("");
            jTextField1.setText("");
            jLabel13.setText("");
            usernametxt.setText("");
            passwordtxt1.setText("");
             designationcmb.setSelectedItem("");
            JOptionPane.showMessageDialog(rootPane, "Saved");

        } else if (x == 1) {
            jTextField8.setText("");
            txtName.setText("");
            txtMname.setText("");
            txtLname.setText("");
            txtPhonenu.setText("");
            txtdateofbirth.setText("");
            cscmb.setSelectedItem("");
            jLabel10.setText("");
            jComboBox1.setSelectedItem("");
            jTextField1.setText("");
            jLabel13.setText("");
            usernametxt.setText("");
            passwordtxt1.setText("");
             designationcmb.setSelectedItem("");
        } else {
            jTextField8.setText("");
            txtName.setText("");
            txtMname.setText("");
            txtLname.setText("");
            txtPhonenu.setText("");
            txtdateofbirth.setText("");
            jLabel10.setText("");
            jComboBox1.setSelectedItem("");
            jTextField1.setText("");
            jLabel13.setText("");
            usernametxt.setText("");
            passwordtxt1.setText("");
             designationcmb.setSelectedItem("");
        }
    }//GEN-LAST:event_SubmitActionPerformed
    }
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        File f = jFileChooser1.getSelectedFile();
        String image = f.getAbsolutePath();
        image = image.replace("\\", "/");
        
        String sid = jTextField8.getText();
        int si = Integer.valueOf(sid);
        String fname = txtName.getText();
        String mname = txtMname.getText();
        String lname = txtLname.getText();
        String DOB = txtdateofbirth.getText();
       String phone_num = txtPhonenu.getText();
        String code = cscmb.getSelectedItem().toString();
     
        String Course = jComboBox1.getSelectedItem().toString();
        String Email = jTextField1.getText();
           String username = usernametxt.getText();
        String password = passwordtxt1.getText();
        String usertype = designationcmb.getSelectedItem().toString();

        int x = JOptionPane.showConfirmDialog(rootPane, "Are You Sure You want to Change This Recode?");

        if (x == 0) {
            /* EmployeeDetails em;
             em = new EmployeeDetails(emppid, lname, con1, con2, address, des);

             dao.updateEmployee(em);
             *
             *
             *
             */
            Student cs = new Student(si, fname, mname, lname, DOB, phone_num, code, image, Course, Email, username, password,usertype);

            StudentDetails dao = new StudentDetails();
            dao.updateStudent(cs);
            jTextField8.setText("");
            txtName.setText("");
            txtMname.setText("");
            txtLname.setText("");
            txtdateofbirth.setText("");
            txtPhonenu.setText("");
            jLabel10.setText("");
            jTextField1.setText("");
            usernametxt.setText("");
            passwordtxt1.setText("");
             designationcmb.setSelectedItem("");
            //Save to the database
            JOptionPane.showMessageDialog(rootPane, "Saved");

        } else if (x == 1) {
            jTextField8.setText("");
            txtName.setText("");
            txtMname.setText("");
            txtLname.setText("");
            txtdateofbirth.setText("");
            txtPhonenu.setText("");
            cscmb.setSelectedItem("");
            jLabel10.setText("");
            jTextField1.setText("");
            usernametxt.setText("");
            passwordtxt1.setText("");
             designationcmb.setSelectedItem("");
        } else {
            jTextField8.setText("");
            txtName.setText("");
            txtMname.setText("");
            txtLname.setText("");
            txtdateofbirth.setText("");
            txtPhonenu.setText("");
            cscmb.setSelectedItem("");
            jLabel10.setText("");
            jTextField1.setText("");
            usernametxt.setText("");
            passwordtxt1.setText("");
             designationcmb.setSelectedItem("");
            ;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        loadStudentTable();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:

        String sid = jTextField8.getText();
        int si = Integer.valueOf(sid);
        String fname = txtName.getText();
        String mname = txtMname.getText();
        String lname = txtLname.getText();
        String DOB = txtdateofbirth.getText();
   String phone_num = txtPhonenu.getText();
        String code = cscmb.getSelectedItem().toString();
        String Course = jComboBox1.getSelectedItem().toString();
        String image = jLabel10.getText();
        String Email = jTextField1.getText();
            String username = usernametxt.getText();
        String password = passwordtxt1.getText();
        String usertype = designationcmb.getSelectedItem().toString();

        int x = JOptionPane.showConfirmDialog(rootPane, "You Want To Delete The Recode ?");

        if (x == 0) {
            Student cs = new Student(si, fname, mname, lname, DOB, phone_num, code, Course, image, Email, username, password,usertype);
            //Save to the database
            StudentDetails dao = new StudentDetails();
            dao.deleteBrand(cs);
            JOptionPane.showMessageDialog(rootPane, "Deleted !");
            jTextField8.setText("");
            txtName.setText("");
            txtMname.setText("");
            txtLname.setText("");
            txtdateofbirth.setText("");
            txtPhonenu.setText("");
            cscmb.setSelectedItem("");
            jTextField1.setText("");
            usernametxt.setText("");
            passwordtxt1.setText("");
               designationcmb.setSelectedItem("");

        } else if (x == 1) {

            jTextField8.setText("");
            txtName.setText("");
            txtMname.setText("");
            txtLname.setText("");
            txtdateofbirth.setText("");
            txtPhonenu.setText("");
            cscmb.setSelectedItem("");
            jTextField1.setText("");
            usernametxt.setText("");
            passwordtxt1.setText("");
               designationcmb.setSelectedItem("");
        } else {

            jTextField8.setText("");
            txtName.setText("");
            txtMname.setText("");
            txtLname.setText("");
            txtdateofbirth.setText("");
            txtPhonenu.setText("");
            cscmb.setSelectedItem("");
            jTextField1.setText("");
            usernametxt.setText("");
            passwordtxt1.setText("");
               designationcmb.setSelectedItem("");
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void UploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UploadActionPerformed
        // TODO add your handling code here:
        jFileChooser1.showOpenDialog(this);
        File f = jFileChooser1.getSelectedFile();
        String image = f.getAbsolutePath();
        image = image.replace("\\", "/");
        File f1 = new File(image);

        try {
            Image im = ImageIO.read(f1);
            im = im.getScaledInstance(jLabel10.getWidth(), jLabel10.getHeight(), Image.checkImage(im, rootPane));
            jLabel10.setIcon(new ImageIcon(im));

        } catch (Exception e) {

            e.printStackTrace();
            JOptionPane.showMessageDialog(rootPane, "No Picture Uploaded");
            // Logger.getLogger(uplo)


    }//GEN-LAST:event_UploadActionPerformed
    }
    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:

        DefaultTableModel Student = (DefaultTableModel) jTable1.getModel();
        jTextField8.setText(Student.getValueAt(jTable1.getSelectedRow(), 0).toString());
        txtName.setText(Student.getValueAt(jTable1.getSelectedRow(), 1).toString());
        txtMname.setText(Student.getValueAt(jTable1.getSelectedRow(), 2).toString());
        txtLname.setText(Student.getValueAt(jTable1.getSelectedRow(), 3).toString());
        txtdateofbirth.setText(Student.getValueAt(jTable1.getSelectedRow(), 4).toString());
        txtPhonenu.setText(Student.getValueAt(jTable1.getSelectedRow(), 5).toString());
        cscmb.setSelectedItem(Student.getValueAt(jTable1.getSelectedRow(), 6).toString());
        jComboBox1.setSelectedItem(Student.getValueAt(jTable1.getSelectedRow(), 7).toString());
        jTextField1.setText(Student.getValueAt(jTable1.getSelectedRow(), 8).toString());
        usernametxt.setText(Student.getValueAt(jTable1.getSelectedRow(), 9).toString());
        passwordtxt1.setText(Student.getValueAt(jTable1.getSelectedRow(), 10).toString());
         designationcmb.setSelectedItem(Student.getValueAt(jTable1.getSelectedRow(), 11).toString());
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        jTextField8.setText("");
        txtName.setText("");
        txtMname.setText("");
        txtLname.setText("");
        txtdateofbirth.setText("");
        txtPhonenu.setText("");
        cscmb.setSelectedItem("");
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        String email = jTextField1.getText();

        if (!email.contains(".") || !email.contains("@")) {
            JOptionPane.showMessageDialog(txtName, "Invalid Email Address");
            jTextField1.setText("");
            jTextField1.grabFocus();

        } else {

            jTextField1.grabFocus();
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Webcam webcam = (Webcam) Webcam.getWebcams().get(0);

        webcam.setViewSize(new Dimension(640, 480));

        webcam.open();
        try {
            String g = getClass().getResource("..//cam//").getPath();

            String path1 = g;
            try {
                path1 = URLDecoder.decode(path1, "utf-8");
            } catch (UnsupportedEncodingException ex) {
            }
            path1 = new File(path1).getPath();

            String name = String.format(System.currentTimeMillis() + " member.jpg", new Object[]{Long.valueOf(System.currentTimeMillis())});
            String n = path1 + "\\" + System.currentTimeMillis() + ".jpg";

            String pathlatest;

            pathlatest = path1.replace("build\\", "src");

            pathlatest = pathlatest.replace("classes", "");

            pathlatest = pathlatest.replace('\\', '/');

            File too = new File(pathlatest + "/" + name);

            ss = too.getAbsolutePath();

            BufferedImage image1 = webcam.getImage();

            Image resizedImage1 = image1.getScaledInstance(this.jLabel10.getWidth(), this.jLabel10.getHeight(), 0);

            ImageIcon im1 = new ImageIcon(resizedImage1);

            ImageIO.write(image1, "JPG", too);

            String ph = ss.replace('\\', '/');
            photo = new StringBuilder(ph);

            System.out.println("path new  " + photo);
            JOptionPane.showMessageDialog(null, photo);

            jLabel10.setIcon(im1);

            jLabel10.setText("Add success");

            webcam.close();
            System.out.format("File %s has been saved\n", new Object[]{name});
        } catch (IOException t) {
            t.printStackTrace();
        }   // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       webcam.setViewSize(captureSize);

		panel.setFPSDisplayed(true);
		panel.setFillArea(true);

		// start application with disable snapshot button - we enable it when
		// webcam is started

		btSnapMe.setEnabled(false);

		//setLayout(new FlowLayout());
		add(panel);
		add(btSnapMe);
		add(btStart);

		//pack();
		//setVisible(true);
		//setDefaultCloseOperat
    }//GEN-LAST:event_jButton6ActionPerformed

    private void txtdateofbirthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtdateofbirthActionPerformed
        // TODO add your handling code here:
        
       if (txtdateofbirth.getText().length()==0 || txtdateofbirth.getText().length()==0)
        JOptionPane.showMessageDialog(null, "One of the required field is empty!", "Error", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_txtdateofbirthActionPerformed

    private void txtPhonenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPhonenuActionPerformed
        // TODO add your handling code here:
         String mobileno = txtPhonenu.getText();

        if (mobileno.length() > 10) {

            JOptionPane.showMessageDialog(null, "Invalid Phone Number");
            txtPhonenu.setText("");

        } else if (mobileno.length() < 10) {
            JOptionPane.showMessageDialog(null, "Invalid Phone Number");
            txtPhonenu.setText("");
        } else {
            txtPhonenu.grabFocus();
        }
    }//GEN-LAST:event_txtPhonenuActionPerformed

    private void txtPhonenuKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhonenuKeyTyped
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtPhonenuKeyTyped

    private void designationcmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_designationcmbActionPerformed
        // TODO add your handling code here:
    
    }//GEN-LAST:event_designationcmbActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Image;
    private javax.swing.JButton Submit;
    private javax.swing.JButton Upload;
    private javax.swing.JPanel cam_panel;
    private javax.swing.JComboBox cscmb;
    private javax.swing.JComboBox designationcmb;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField passwordtxt1;
    private javax.swing.JTextField txtLname;
    private javax.swing.JTextField txtMname;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhonenu;
    private javax.swing.JTextField txtdateofbirth;
    private javax.swing.JTextField usernametxt;
    // End of variables declaration//GEN-END:variables

}

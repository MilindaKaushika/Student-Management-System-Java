/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIDesigns;

import Method.GradeDetails;
import Method.Grades;
import Method.Student;
import java.util.Vector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import Data.DBConnManager;
/**
 *
 * @author Home-PC
 */
public class View_Grades extends javax.swing.JPanel {
      private Vector<Vector<String>> data;
    private Vector<String> header;
    DefaultTableModel sp;
    /**
     * Creates new form View_Grades
     */


       private DBConnManager dbConnManager = null; 
 
    public View_Grades() {
        initComponents();
          dbConnManager = new DBConnManager();
 findUsers();
          header = new Vector<String>();
      
      header.add("Batch_No");
      header.add("sid");
      header.add("Grade");   
      header.add("Semester");   
      header.add("Student_Name");   
     header.add("Year");  
      header.add("Course"); 
       
//          creatcoloum();
           loadGradeTable();
      ;  
    }
 
  

    public ArrayList<Grades> ListUsers(String Id)
    {
        ArrayList<Grades> usersList = new ArrayList<Grades>();
        Connection dbConn = null;
      
        ResultSet rs;
        
        try{
              dbConn = dbConnManager.Connect();
            java.sql.Statement stmt = dbConn.createStatement();
            
         
            String searchQuery = "SELECT Batch_No,sid,code,Grade,Semester,Student_Name,year,Course FROM Grade WHERE Batch_No='"+Id+"'";
            rs = stmt.executeQuery(searchQuery);
            
            Grades p;
            
            while(rs.next())
            {
                p = new Grades(
                       rs.getString("Batch_No"),
                        rs.getInt("sid"),
                        rs.getString("code"),  
                         rs.getString("Grade"),
                         rs.getString("Semester"),
                        rs.getString("Student_Name"),
                        rs.getString("year"),
                          rs.getString("Course")

                          );
                
                                
                               
                usersList.add(p);
            }
            
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        return usersList;
    }
 public void findUsers()
    {

        ArrayList<Grades> Grades = ListUsers(jTextField1.getText());
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(new Object[]{"Batch_No","sid","Grade","Semester","Student_Name","Year","Course"});
        Object[] row = new Object[7];
        
        for(int i = 0; i < Grades.size(); i++)
        {
            row[0] = Grades.get(i).getBatch_No();
            row[1] = Grades.get(i).getsid();
            row[2] = Grades.get(i).getGrade();
            row[3] = Grades.get(i).getSemester();
            row[4] = Grades.get(i).getStudent_Name();
            row[5] = Grades.get(i).getyear();
            row[6] = Grades.get(i).getCourse();
         
            model.addRow(row);
        }
       jTable1.setModel(model);
       
    }

 
 private void loadGradeTable() {

        GradeDetails dao = new GradeDetails();
        data = dao.getGradeDetails();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                data, header));
        jScrollPane1.setViewportView(jTable1);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });
        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 30, 180, 30));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/search-icon.png"))); // NOI18N
        jButton1.setText("Find");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 30, -1, -1));

        jLabel2.setBackground(new java.awt.Color(0, 153, 51));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("View Resalt Details");
        jLabel2.setOpaque(true);
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1090, 50));

        jTable1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setGridColor(new java.awt.Color(255, 255, 255));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(5).setResizable(false);
        }

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, 990, 520));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setOpaque(true);
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 660));
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
  
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        // TODO add your handling code here:
//        String query=jTextField1.getText().toLowerCase();
//            filter(query);
DefaultTableModel dtm = (DefaultTableModel) jTable1.getModel();
        int i = jTable1.getSelectedRow();
        String Id = dtm.getValueAt(i, 0).toString();
        jTextField1.setText(Id);
        int sid = Integer.valueOf(Id);
        
           GradeDetails dao = new  GradeDetails();
       Student  sp = dao.searchDetails(sid);
     
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
    
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         findUsers();
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables

  
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UIDesigns;

import Method.ClassDetails;
import java.util.Vector;
import Method.ColoerTable;
import it.businesslogic.ireport.chart.gui.TagComboBoxRenderer;
import java.awt.Color;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Home-PC
 */
public class Class_view extends javax.swing.JPanel {
 private Vector<Vector<String>> data;
    private Vector<String> header;
    /**
     * Creates new form Class_view
     */
    public Class_view() {
        initComponents();
     
          header = new Vector<String>();
    
        header.add("Class Name");
          header.add("Schedule");
          header.add("Lecturer Name"); 
          header.add("Time In");
          header.add("Time out");
          
        loadCourseTable();
          
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1090, 660));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        jScrollPane1.setViewportView(jTable1);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, 1010, 530));

        jLabel2.setBackground(new java.awt.Color(204, 204, 204));
        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("View Class Details");
        jLabel2.setOpaque(true);
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1090, 50));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setOpaque(true);
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 660));
    }// </editor-fold>//GEN-END:initComponents
 private void loadCourseTable() {

         ClassDetails dao = new ClassDetails();
         data = dao.getClassDetailss();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                data, header));
        jScrollPane1.setViewportView(jTable1);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
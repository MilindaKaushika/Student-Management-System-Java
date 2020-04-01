/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PageLoad.java
 *
 * Created on Jan 31, 2016, 12:25:15 PM
 */

package UIDesigns;


import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.plaf.ProgressBarUI;

/**
 *
 * @author ESOFT
 */
public class PageLoad extends javax.swing.JFrame {

    /** Creates new form PageLoad */
    public PageLoad() {
   initComponents();
   
   

        new Thread(new Runnable() {

            @Override
            public void run() {
             
                for(int i=0;i<=100;i++){
                    jProgressBar1.setValue(i);

                    if(i<10){
                        try {
                            Thread.sleep(75);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PageLoad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jLabel1.setText(" WELCOME...10% Loading Exsisting Files...Please Wait...");

                    }
                    else if(i<20){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PageLoad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jLabel1.setText("20% loading Interfaces...");

                    }
                    else if(i<30){
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PageLoad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jLabel1.setText("30% Configuring Data...");
                    }
                    else if(i<40){
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PageLoad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jLabel1.setText("40% Loading Images...");

                    }
                    else if(i<50){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PageLoad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jLabel1.setText("50% loading...please wait..");

                    }
                    else if(i<60){
                        try {
                            Thread.sleep(75);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PageLoad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jLabel1.setText("60% Waiting.....");

                    }

                    else if(i<70){
                        try {
                            Thread.sleep(25);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PageLoad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jLabel1.setText("70% Loading database files...");
                    }
                    else if(i<80){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PageLoad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jLabel1.setText("80% Wait.....");

                    }
                    else if(i<100){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PageLoad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jLabel1.setText("90% Please wait...preparing..");
                    }
                    else if(i==100){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PageLoad.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        jLabel1.setText("100% Starting Software..");
                        s();


                    }


                }
            }
        }).start();


    }
           void s(){

        try {

              new Login().setVisible(true);
              this.dispose();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar2 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 0, 204));
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Loading...................");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 310, 290, -1));

        jProgressBar1.setBackground(new java.awt.Color(255, 255, 255));
        jProgressBar1.setForeground(new java.awt.Color(51, 51, 255));
        getContentPane().add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 270, 280, 25));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/TopBanner_2.png"))); // NOI18N
        jLabel2.setOpaque(true);
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 660, 360));

        setSize(new java.awt.Dimension(651, 341));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PageLoad().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    // End of variables declaration//GEN-END:variables

}

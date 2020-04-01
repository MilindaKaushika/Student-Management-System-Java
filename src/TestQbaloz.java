import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;


public class TestQbaloz {

    public static void main(String[] args) throws InterruptedException {

        final JPanel jPanelWebcam = new JPanel();
     //   final JPanel jPanelMap = new JPanel();

        JPanel jPanel5 = new JPanel();
         
         
       jPanel5.add("Webcam", jPanelWebcam);
        

        final JButton button = new JButton(new AbstractAction("dodaj") {

            private static final long serialVersionUID = 1L;

            @Override
            public void actionPerformed(ActionEvent e) {

                Webcam webcam = Webcam.getDefault();
                webcam.setViewSize(WebcamResolution.VGA.getSize());

                WebcamPanel webcamPanel = new WebcamPanel(webcam);
                webcamPanel.setFPSDisplayed(true);
                //webcamPanel.setDisplayDebugInfo(true);
              //  webcamPanel.setImageSizeDisplayed(true);
               // webcamPanel.setMirrored(true);

                jPanelWebcam.add(webcamPanel);
                jPanelWebcam.getParent().revalidate();

            }
        });

        JFrame window = new JFrame("Test webcam panel");
        window.setLayout(new FlowLayout());
        window.setPreferredSize(new Dimension(800, 600));
        window.add(jPanel5);
        window.add(button);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }
}
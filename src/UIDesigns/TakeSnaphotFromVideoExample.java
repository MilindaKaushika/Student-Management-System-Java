package UIDesigns;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;


@SuppressWarnings("serial")
public class TakeSnaphotFromVideoExample extends JFrame {

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

	public TakeSnaphotFromVideoExample() {

		//super("Test Snap Different Size");

		webcam.setViewSize(captureSize);

		panel.setFPSDisplayed(true);
		panel.setFillArea(true);

		// start application with disable snapshot button - we enable it when
		// webcam is started

		btSnapMe.setEnabled(false);

		setLayout(new FlowLayout());
		add(panel);
		add(btSnapMe);
		add(btStart);

		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                btStart.doClick();
                
	}

	public static void main(String[] args) {
		new TakeSnaphotFromVideoExample();
	}
}

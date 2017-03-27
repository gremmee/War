package nl.gremmee.war.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class SplashScreen extends JWindow {
    private static final long serialVersionUID = 6248071635450138926L;
    BorderLayout borderLayout1 = new BorderLayout();
    FlowLayout southPanelFlowLayout = new FlowLayout();
    ImageIcon imageIcon;
    JLabel imageLabel = new JLabel();
    JPanel southPanel = new JPanel();
    JProgressBar progressBar = new JProgressBar();

    public SplashScreen(ImageIcon aImageIcon) {
        this.imageIcon = aImageIcon;
        try {
            init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int getProgress() {
        return progressBar.getValue();
    }

    public void setProgress(int aProgress) {
        final int theProgress = aProgress;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                progressBar.setValue(theProgress);
            }
        });
    }

    public void setProgress(String aMessage, int aProgress) {
        final int theProgress = aProgress;
        final String theMessage = aMessage;
        setProgress(aProgress);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                progressBar.setValue(theProgress);
                setMessage(theMessage);
            }
        });
    }

    public void setProgressMax(int aMaxProgress) {
        progressBar.setMaximum(aMaxProgress);
    }

    public void setScreenVisible(boolean aVisible) {
        final boolean visible = aVisible;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setVisible(visible);
            }
        });
    }

    void init() {
        imageLabel.setIcon(imageIcon);
        this.getContentPane().setLayout(borderLayout1);
        southPanel.setLayout(southPanelFlowLayout);
        southPanel.setBackground(Color.BLACK);
        this.getContentPane().add(imageLabel, BorderLayout.CENTER);
        this.getContentPane().add(southPanel, BorderLayout.SOUTH);
        progressBar.setPreferredSize(new Dimension(500, 20));
        progressBar.setForeground(new Color(252, 225, 154));
        UIManager.put("ProgressBar.selectionForeground", new Color(76, 50, 37));
        southPanel.add(progressBar, null);
        this.pack();
    }

    private void setMessage(String aMessage) {
        if (aMessage == null) {
            aMessage = "";
            progressBar.setStringPainted(false);
        } else {
            progressBar.setStringPainted(true);
        }
        progressBar.setString(aMessage);
    }
}

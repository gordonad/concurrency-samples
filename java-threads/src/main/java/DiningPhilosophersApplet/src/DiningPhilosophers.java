import javax.swing.*;
import java.net.URL;
import java.awt.*;
import java.io.*;
import java.util.*;
import javax.swing.SwingUtilities;

//for dealing with the start stop button
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

//for dealing with the delay slider
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class DiningPhilosophers extends javax.swing.JApplet
        implements ActionListener, ChangeListener {
    
    //delays can go from 0 to 10,000 milliseconds, initial value is 500
    public int grabDelay = 500;
    private JButton stopStartButton = new JButton("start");
    private JSlider grabDelaySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 5);
    private JLabel label = new JLabel("  500 milliseconds");
    private JPanel philosopherArea;
    
    public Map<Dukes, ImageIcon> imgs;
    static final int NUMPHILS = 5;
    public Chopstick[] chopsticks = new Chopstick[NUMPHILS];
    private Philosopher[] philosophers = new Philosopher[NUMPHILS];
    String[] names = { "Arisduktle", "Dukrates", "Pythagoduke",
    "Duko", "Dukimedes" };
    private int width = 0;
    private int height = 0;
    private double spacing;
    private static final double MARGIN = 10.0f;
    
    public void init() {
        imgs = new EnumMap<Dukes, ImageIcon>(Dukes.class);
        imgs.put(Dukes.HUNGRYDUKE,
                createAppletImageIcon("images/hungryduke.gif",
                "hungry duke"));
        imgs.put(Dukes.RIGHTSPOONDUKE,
                createAppletImageIcon("images/rightspoonduke.gif",
                "right spoon duke"));
        imgs.put(Dukes.BOTHSPOONSDUKE,
                createAppletImageIcon("images/bothspoonsduke.gif",
                "both spoon duke"));
        width = imgs.get(Dukes.HUNGRYDUKE).getIconWidth() + (int)(MARGIN*2.0);
        height = imgs.get(Dukes.HUNGRYDUKE).getIconHeight() + (int)(MARGIN*2.0);
        spacing = width + MARGIN;
        
        //Execute a job on the event-dispatching thread:
        //creating this applet's GUI.
        try {
            javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    createGUI();
                }
            });
        } catch (Exception e) {
            System.err.format("createGUI didn't successfully complete%n");
        }
    }
    
    private void createGUI() {
        GridBagLayout gridBag = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
        
        JPanel contentPane = new JPanel();
        contentPane.setLayout(gridBag);
        
        philosopherArea = new JPanel(null);
        philosopherArea.setBackground(Color.white);
        Dimension preferredSize = createPhilosophersAndChopsticks();
        philosopherArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        philosopherArea.setPreferredSize(preferredSize);
        
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridBag.setConstraints(philosopherArea, c);
        contentPane.add(philosopherArea);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        c.weighty = 0.0;
        gridBag.setConstraints(stopStartButton, c);
        contentPane.add(stopStartButton);
        
        c.gridwidth = GridBagConstraints.RELATIVE; //don't end row
        c.weightx = 1.0;
        c.weighty = 0.0;
        gridBag.setConstraints(grabDelaySlider, c);
        contentPane.add(grabDelaySlider);
        
        c.weightx = 0.0;
        c.gridwidth = GridBagConstraints.REMAINDER; //end row
        gridBag.setConstraints(label, c);
        contentPane.add(label);
        contentPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setContentPane(contentPane);
        
        stopStartButton.addActionListener(this);
        grabDelaySlider.addChangeListener(this);
        
    }
    
    public void actionPerformed(ActionEvent e) {
        if ("stop/reset".equals(stopStartButton.getText())) {
            stopPhilosophers();
            stopStartButton.setText("start");
        } else if ("start".equals(stopStartButton.getText())) {
            startPhilosophers();
            stopStartButton.setText("stop/reset");
        }
    }
    
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        grabDelay = source.getValue()*100;
        label.setText(String.valueOf(grabDelay + " milliseconds"));
    }
    
    public void startPhilosophers() {
        for (int i = 0; i < NUMPHILS; i++)
            philosophers[i].philThread.start();
    }
    
    public void stopPhilosophers() {
        for (int i = 0; i < NUMPHILS; i++)
            philosophers[i].philThread.interrupt();
    }
    
    public Dimension createPhilosophersAndChopsticks() {
        double x, y;
        double radius = 80.0;
        double centerAdj = 85.0;
        double radians;
        
        Dimension preferredSize = new Dimension(0, 0);
        
        for (int i = 0; i < NUMPHILS; i++)
            chopsticks[i] = new Chopstick();
        
        for (int i = 0; i < NUMPHILS; i++) {
            radians = i*(2.0 * Math.PI /(double)NUMPHILS);
            x = Math.sin(radians) * radius + centerAdj;
            y = Math.cos(radians) * radius + centerAdj;
            philosophers[i] = new Philosopher(this, i);
            philosophers[i].setBounds((int)x, (int)y, width, height);
            philosopherArea.add(philosophers[i]);
            if ((int)x > preferredSize.width)
                preferredSize.width = (int)x;
            if ((int)y > preferredSize.height)
                preferredSize.height = (int)y;
        }
        preferredSize.width += width;
        preferredSize.height += height;
        return preferredSize;
    }
    
    protected static ImageIcon createAppletImageIcon(String path,
            String description) {
        int MAX_IMAGE_SIZE = 75000; //Change this to the size of
        //your biggest image, in bytes.
        int count = 0;
        BufferedInputStream imgStream = new BufferedInputStream(
                DiningPhilosophers.class.getResourceAsStream(path));
        if (imgStream != null) {
            byte buf[] = new byte[MAX_IMAGE_SIZE];
            try {
                count = imgStream.read(buf);
            } catch (IOException ieo) {
                System.err.format("Couldn't read stream from file: %s%n", path);
            }
            
            try {
                imgStream.close();
            } catch (IOException ieo) {
                System.err.format("Can't close file %s%n", path);
            }
            
            if (count <= 0) {
                System.err.format("Empty file: %s%n", path);
                return null;
            }
            return new ImageIcon(Toolkit.getDefaultToolkit().createImage(buf),
                    description);
        } else {
            System.err.format("Couldn't find file: %s%n", path);
            return null;
        }
    }
}

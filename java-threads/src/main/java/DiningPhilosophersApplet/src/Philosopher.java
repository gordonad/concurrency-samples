import java.util.Random;

import java.awt.*;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

public class Philosopher extends JLabel implements Runnable {
    private Chopstick leftStick, rightStick;
    private boolean sated;
    private DiningPhilosophers parent;
    private int position;
    private Dukes currentIcon;
    private String currentText;
    Thread philThread = null;

    public Philosopher(DiningPhilosophers parent, int position) {
        super(parent.names[position], parent.imgs.get(Dukes.HUNGRYDUKE),
              JLabel.CENTER);

        this.parent = parent;
        this.position = position;

        setVerticalTextPosition(JLabel.BOTTOM);
        setHorizontalTextPosition(JLabel.CENTER);

        //Identify the chopsticks to my right and left
        this.rightStick = parent.chopsticks[position];
        if (position == 0) {
            this.leftStick = parent.chopsticks[parent.NUMPHILS-1];
        } else {
            this.leftStick = parent.chopsticks[position-1];
        }

        //I'm hungry
        this.sated = false;
        philThread = new Thread(this);
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep((int)(Math.random() * parent.grabDelay));
                rightStick.grab();
                currentIcon = Dukes.RIGHTSPOONDUKE;
                currentText = "     ";
                try {
                    updatePhilosopher();
                } catch (Exception e) {
                    break;
                }
                Thread.sleep((int)(Math.random() * parent.grabDelay));
                leftStick.grab();
                currentIcon = Dukes.BOTHSPOONSDUKE;
                try {
                    updatePhilosopher();
                } catch (Exception e) {
                    break;
                }
                                                
                Thread.sleep((int)(Math.random() * parent.grabDelay));
                rightStick.release();
                leftStick.release();
                currentIcon = Dukes.HUNGRYDUKE;
                currentText = "Good!";
                try {
                    updatePhilosopher();
                } catch (Exception e) {
                    break;
                }
                sated = true;

                Thread.sleep((int)(Math.random() * parent.grabDelay * 4));
                sated = false;
            }
        } catch (java.lang.InterruptedException e) {}
        leftStick.releaseIfMine();
        rightStick.releaseIfMine();
                    
        currentIcon = Dukes.HUNGRYDUKE;
        currentText = parent.names[position];
        try {
            updatePhilosopher();
        } catch (Exception e) {
            System.err.format("Couldn't update icon for %d%n", position);
        }
        sated = false;
        philThread = new Thread(this);
    }
                
    void updatePhilosopher() throws Exception {
        Runnable changeThePhilosopher = new Runnable() {
            public void run() {
                setText(currentText);
                setIcon(parent.imgs.get(currentIcon));
            }
        };
        SwingUtilities.invokeAndWait(changeThePhilosopher);
    } 
}

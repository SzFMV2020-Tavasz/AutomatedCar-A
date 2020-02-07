package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.SamplePacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Gui extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger();
    private final int windowWidth = 1020;
    private final int windowHeight = 700;
    private ArrayList<Integer> keysPressed;
    private CourseDisplay courseDisplay;
    private Dashboard dashboard;
    private VirtualFunctionBus virtualFunctionBus;


    /**
     * Initialize the GUI class
     */
    public Gui() {
        setTitle("AutomatedCar");
        setLocation(0, 0); // default is 0,0 (top left corner)
        addWindowListener(new GuiAdapter());
        setPreferredSize(new Dimension(windowWidth, windowHeight)); // inner size
        setResizable(false);
        pack();

        // Icon downloaded from:
        // http://www.iconarchive.com/show/toolbar-2-icons-by-shlyapnikova/car-icon.html
        // and available under the licence of:
        // https://creativecommons.org/licenses/by/4.0/
        ImageIcon carIcon = new ImageIcon(ClassLoader.getSystemResource("car-icon.png"));
        setIconImage(carIcon.getImage());

        // Not using any layout manager, but fixed coordinates
        setLayout(null);

        courseDisplay = new CourseDisplay(this);
        add(courseDisplay);

        dashboard = new Dashboard(this);
        add(dashboard);

        setVisible(true);

        keysPressed = new ArrayList<>();

        KeyListener listen = new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {


            }

            @Override
            public void keyReleased(KeyEvent e) {
                int keyCode = e.getKeyCode();

                // Release turning and pedal pressing so the back positioning can run.
                if (keyCode == KeyEvent.VK_RIGHT) {
                    LOGGER.info(">");
                    SamplePacket p = new SamplePacket();
                    p.setKey(1);
                    virtualFunctionBus.samplePacket = p;
                }
                if (keyCode == KeyEvent.VK_LEFT) {
                    LOGGER.info("<");
                    SamplePacket p = new SamplePacket();
                    p.setKey(3);
                    virtualFunctionBus.samplePacket = p;
                }
                if (keyCode == KeyEvent.VK_UP) {
                    LOGGER.info("^");
                    SamplePacket p = new SamplePacket();
                    p.setKey(0);
                    virtualFunctionBus.samplePacket = p;
                }
                if (keyCode == KeyEvent.VK_DOWN) {
                    LOGGER.info("v");
                    SamplePacket p = new SamplePacket();
                    p.setKey(2);
                    virtualFunctionBus.samplePacket = p;
                }

                if (keysPressed.contains(keyCode)) {
                    keysPressed.remove(keysPressed.indexOf(keyCode));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

                if (!keysPressed.contains(e.getKeyCode())) {
                    keysPressed.add(e.getKeyCode());
                }

            }
        };

        this.addKeyListener(listen);

    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
    }

    public CourseDisplay getCourseDisplay() {
        return courseDisplay;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

}
package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.HMIKeyListener;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.SamplePacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

public class Gui extends JFrame {

    private static final Logger LOGGER = LogManager.getLogger(Gui.class);
    private final int windowWidth = 1020;
    private final int windowHeight = 700;
    private ArrayList<Integer> keysPressed;
    private CourseDisplay courseDisplay;
    private Dashboard dashboard;
    private VirtualFunctionBus virtualFunctionBus;
    private HMIKeyListener listener = new HMIKeyListener();


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

        KeyListener listen = listener.getHMIListener();
        this.addKeyListener(listen);

        dashboard = new Dashboard(this);
        add(dashboard);

        setVisible(true);

        keysPressed = new ArrayList<>();
       // loop();

    }
    public void loop()
    {
        Thread looping = new Thread(() ->{
            while(true){
                System.out.println("Gas pedal value: " +virtualFunctionBus.toPowerTrainPacket.getGasPedalValue());
                System.out.println("Break pedal value" +virtualFunctionBus.toPowerTrainPacket.getBreakPedalValue());
                System.out.println("Steering wheel value: " +virtualFunctionBus.toPowerTrainPacket.getSteeringWheelValue());
                System.out.println("Shifter value: " + virtualFunctionBus.toPowerTrainPacket.getShiftChangeRequest());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        looping.start();
    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
        listener.setVirtualFunctionBus(virtualFunctionBus);
        //dashboard.setVirtualFunctionBus(virtualFunctionBus);
    }

    public CourseDisplay getCourseDisplay() {
        return courseDisplay;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

}
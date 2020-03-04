package hu.oe.nik.szfmv.automatedcar.visualization;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger(Dashboard.class);

    private final int width = 250;
    private final int height = 700;
    private final int backgroundColor = 0x888888;


    private Gui parent;

    private Thread timer = new Thread() {
        int difference;

        public void run() {
            while (true) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    LOGGER.error(ex.getMessage());
                }

            }
        }
    };

    /**
     * Initialize the dashboard
     */
    public Dashboard(Gui pt) {


        FlowLayout dashboardLayout = new FlowLayout();
        dashboardLayout.setVgap(16);
        setLayout(dashboardLayout);

        setBackground(new Color(backgroundColor));
        setBounds(770, 0, width, height);

        // example


        GridLayout mainGridLayout = new GridLayout(5,1, 8, 8);
        GridLayout meterGridLayout = new GridLayout(1,2,8,8);
        GridLayout indexGridLayout = new GridLayout(1,3,8,8);
        GridLayout pedalGridLayout = new GridLayout(4,1,8,8);
        GridLayout accGridLayout = new GridLayout(4, 2, 4,4);
        //GridLayout optsGridLayout = new GridLayout(4, 2, 4, 4);
        //GridLayout signGridLayout = new GridLayout(3,1,0,0);
        GridLayout debugGridLayout = new GridLayout(4, 1, 8, 8);

        // meterPanel
        JPanel meterPanel = new JPanel();
        meterPanel.setLayout(meterGridLayout);
        meterPanel.setBounds(100,100, 250,100);
        meterPanel.setBackground(Color.MAGENTA);
        meterPanel.setVisible(true);



        // need vars from bus
        JLabel leftMeter = new JLabel( 2624 + " rpm");
        JLabel rightMeter = new JLabel(130 + " km/h");

        meterPanel.add(leftMeter);
        meterPanel.add(rightMeter);

        JPanel indexPanel = new JPanel();
        indexPanel.setVisible(true);
        indexPanel.setLayout(indexGridLayout);
        indexPanel.setBackground(Color.GREEN);

        JLabel leftIndex = new JLabel("left");

        JLabel rightIndex = new JLabel("right");
        JLabel gear = new JLabel("gear: D");


        indexPanel.add(leftIndex);
        indexPanel.add(gear);
        indexPanel.add(rightIndex);

        JPanel pedalPanel = new JPanel();
        pedalPanel.setVisible(true);
        pedalPanel.setLayout(pedalGridLayout);
        pedalPanel.setBackground(Color.ORANGE);

        //ACC
        JPanel accPanel = new JPanel();
        accPanel.setLayout(accGridLayout);

        //JPanel optsPanel = new JPanel();
        //optsPanel.setLayout(optsGridLayout);


        //JPanel signPanel = new JPanel();
        //signPanel.setLayout(signGridLayout);


        JLabel accSpeed = new JLabel("160");
        JLabel accDistance = new JLabel("1.2");
        JCheckBox acc = new JCheckBox("ACC");
        acc.setSelected(true);
        JCheckBox pp = new JCheckBox("PP");
        JCheckBox lka = new JCheckBox("LKA");
        JCheckBox lkaWarning = new JCheckBox("LKA WARNING");

        accPanel.add(accSpeed);
        accPanel.add(accDistance);
        accPanel.add(acc);
        accPanel.add(lka);
        accPanel.add(lkaWarning);

        JLabel lastSign = new JLabel("last road sign");
        JCheckBox aeb = new JCheckBox("AEB WARN");
        JCheckBox rrWarn = new JCheckBox("RR WARN");

        accPanel.add(lastSign);
        accPanel.add(aeb);
        accPanel.add(rrWarn);

        //accPanel.add(optsPanel);
        //accPanel.add(signPanel);


        // pedal
        JLabel gasLabel = new JLabel("gas pedal");
        JSlider gasSlider = new JSlider();
        gasSlider.setValue(80);

        JLabel breakLabel = new JLabel("break pedal");
        JSlider breakSlider = new JSlider();
        breakSlider.setValue(35);

        pedalPanel.add(gasLabel);
        pedalPanel.add(gasSlider);
        pedalPanel.add(breakLabel);
        pedalPanel.add(breakSlider);

        // debug
        JPanel debugPanel = new JPanel();
        debugPanel.setLayout(debugGridLayout);


        JLabel speedLimit = new JLabel("speed limit: 60");

        JLabel debug = new JLabel("debug:");
        JLabel steeringWheel = new JLabel("steering wheel: " + "+25");
        JLabel pos = new JLabel("x: " + 350 + "y: " + 500);

        debugPanel.add(speedLimit);
        debugPanel.add(debug);
        debugPanel.add(steeringWheel);
        debugPanel.add(pos);



        add(meterPanel);
        add(indexPanel);
        add(accPanel);
        add(pedalPanel);
        add(debugPanel);



        //example end

        parent = pt;

        timer.start();
    }

}

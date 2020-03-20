package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Index;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets.InputPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    private VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    private  JLabel gear = new JLabel("gear: P");
    private JLabel leftIndex = new JLabel("");
    private JLabel rightIndex = new JLabel("");
    private JProgressBar gasBar = new JProgressBar(0,100);
    private JProgressBar breakBar = new JProgressBar(0, 100);
    private JLabel steeringWheel = new JLabel("steering wheel: " + virtualFunctionBus.guiInputPacket.getSteeringWheelValue());
    private JLabel debug = new JLabel("debug:" + virtualFunctionBus.guiInputPacket.getDebugSwitch());
    private JLabel speedLimit = new JLabel("speed limit: " + virtualFunctionBus.guiInputPacket.getAccSpeedValue());
    JLabel accSpeed = new JLabel("speed limit: " + virtualFunctionBus.guiInputPacket.getAccSpeedValue());
    JLabel accDistance = new JLabel("Acc Distance: " + virtualFunctionBus.guiInputPacket.getAccFollowingDistanceValue());
    JCheckBox pp = new JCheckBox("PP");
    JCheckBox lka = new JCheckBox("LKA");
    JCheckBox acc = new JCheckBox("ACC");


    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
    }

    private static final Logger LOGGER = LogManager.getLogger(Dashboard.class);

    private final int width = 250;
    private final int height = 700;
    private final int backgroundColor = 0x888888;


    private Gui parent;
    /**
     * Initialize the dashboard
     */
    public Dashboard(Gui pt) {


        FlowLayout dashboardLayout = new FlowLayout();
        dashboardLayout.setVgap(16);
        setLayout(dashboardLayout);

        setBackground(new Color(backgroundColor));
        setBounds(770, 0, width, height);
        setDoubleBuffered(true);
        parent = pt;
        // example
        drawEverything();
    }

    private void drawMeterGridLayout(){
        GridLayout meterGridLayout = new GridLayout(1,2,8,8);

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

        add(meterPanel);
    }

    private  JPanel indexPanel = new JPanel();
    private void drawIndexGridLayout(){
        GridLayout indexGridLayout = new GridLayout(1,3,8,8);

        indexPanel.setVisible(true);
        indexPanel.setLayout(indexGridLayout);
        indexPanel.setBackground(Color.GREEN);




        indexPanel.add(leftIndex);
        indexPanel.add(gear);
        indexPanel.add(rightIndex);
        add(indexPanel);
    }

    private JPanel pedalPanel = new JPanel();
    private void drawPedalGridLayout(){
        GridLayout pedalGridLayout = new GridLayout(4,1,8,8);



        pedalPanel.setVisible(true);
        pedalPanel.setLayout(pedalGridLayout);
        pedalPanel.setBackground(Color.ORANGE);

        JLabel gasLabel = new JLabel("gas pedal");

        gasBar.setValue((int)virtualFunctionBus.guiInputPacket.getGasPedalValue());
        gasBar.setStringPainted(true);

        JLabel breakLabel = new JLabel("break pedal");

        breakBar.setStringPainted(true);
        breakBar.setValue((int)virtualFunctionBus.guiInputPacket.getBreakPedalValue());

        pedalPanel.add(gasLabel);
        pedalPanel.add(gasBar);
        pedalPanel.add(breakLabel);
        pedalPanel.add(breakBar);

        add(pedalPanel);
    }

    private  JPanel compactPanel  = new JPanel();
    public void drawAccGridLayout(){
        GridLayout compactLayout = new GridLayout(1,2,0,0);
        GridLayout accGridLayout = new GridLayout(4, 2, 4,4);
        GridLayout optsGridLayout = new GridLayout(4, 2, 4, 4);
        GridLayout signGridLayout = new GridLayout(3,1,0,0);


        compactPanel.setLayout(compactLayout);

        JPanel accPanel = new JPanel();
        accPanel.setLayout(accGridLayout);

        JPanel optsPanel = new JPanel();
        optsPanel.setLayout(optsGridLayout);

        JPanel signPanel = new JPanel();
        signPanel.setLayout(signGridLayout);

        compactPanel.add(accPanel);
        compactPanel.add(optsPanel);

        acc.setSelected(false);
        JCheckBox lkaWarning = new JCheckBox("LKA WARNING");

        acc.setEnabled(false);
        pp.setEnabled(false);
        lka.setEnabled(false);
        lkaWarning.setEnabled(false);

        accPanel.add(accSpeed);
        accPanel.add(accDistance);
        accPanel.add(acc);
        accPanel.add(lka);
        accPanel.add(pp);
       // accPanel.add(lkaWarning);

        JLabel lastSign = new JLabel("last road sign");
        JCheckBox aeb = new JCheckBox("AEB WARN");
        JCheckBox rrWarn = new JCheckBox("RR WARN");

        aeb .setEnabled(false);
        rrWarn.setEnabled(false);

        accPanel.add(lastSign);
        accPanel.add(aeb);
        //accPanel.add(rrWarn);

        add(accPanel);

    }

    JPanel debugPanel = new JPanel();

    private void drawDebugGridLayout(){
        GridLayout debugGridLayout = new GridLayout(4, 1, 8, 8);

        debugPanel.setLayout(debugGridLayout);
        JLabel pos = new JLabel("x: " + 350 + "y: " + 500);

        debugPanel.add(speedLimit);
        debugPanel.add(debug);
        debugPanel.add(steeringWheel);
        debugPanel.add(pos);



        add(debugPanel);
    }
    private void drawEverything() {
        drawMeterGridLayout();
        drawIndexGridLayout();
        drawPedalGridLayout();
        drawAccGridLayout();
        drawDebugGridLayout();
    }

    public void refreshDrawing(){
        gear.setText("gear: " + virtualFunctionBus.guiInputPacket.getShifterPos());
        indexStatus();
        indexPanel.revalidate();

        gasBar.setValue((int)virtualFunctionBus.guiInputPacket.getGasPedalValue());
        breakBar.setValue((int)virtualFunctionBus.guiInputPacket.getBreakPedalValue());
        pedalPanel.revalidate();

        steeringWheel.setText("steering wheel: " + virtualFunctionBus.guiInputPacket.getSteeringWheelValue());
        debug.setText("debug:" + virtualFunctionBus.guiInputPacket.getDebugSwitch());
        debugPanel.revalidate();

        accSpeed.setText("Acc speed: " + virtualFunctionBus.guiInputPacket.getAccSpeedValue());
        accDistance.setText("Acc distance: " + virtualFunctionBus.guiInputPacket.getAccFollowingDistanceValue());
        acc.setSelected(virtualFunctionBus.guiInputPacket.getACCStatus());
        pp.setSelected(virtualFunctionBus.guiInputPacket.getParkingPilotStatus());
        lka.setSelected(virtualFunctionBus.guiInputPacket.getLaneKeepingAssistant());

    }

    private void indexStatus(){
        if(virtualFunctionBus.guiInputPacket.getIndexStatus()== Index.IndexStatus.LEFT) {
            leftIndex.setText("LEFT");
            rightIndex.setText("");
        }
        else if(virtualFunctionBus.guiInputPacket.getIndexStatus()== Index.IndexStatus.RIGHT){
            leftIndex.setText("");
            rightIndex.setText("RIGHT");
        }
        else {
            leftIndex.setText("");
            rightIndex.setText("");
        }
    }
}

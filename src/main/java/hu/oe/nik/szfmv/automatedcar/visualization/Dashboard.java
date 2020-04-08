package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Index;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    private VirtualFunctionBus virtualFunctionBus;
    private final int width = 250;
    private final int height = 700;
    private final int backgroundColor = 0x888888;

    Gui parent;

    private TurnIndex leftTurn;
    private TurnIndex rightTurn;

    private JLabel currentSpeedText = new JLabel("0 KM/h");
    private JLabel currentRpmText = new JLabel("0");
    private JLabel gearShiftText = new JLabel("Gear:");
    private JLabel gasPedalText = new JLabel("Gas Pedal");
    private JLabel accMenuText = new JLabel("ACC opts:");
    private JLabel breakPedalText = new JLabel("Break Pedal");
    private JLabel xCoordText = new JLabel("X: ");
    private JLabel yCoordText = new JLabel("Y: ");
    private JLabel steeringWheelText = new JLabel("Steering Wheel:");
    private JLabel speedLimitText = new JLabel("Speed Limit:");

    private JLabel pedalExplainerText = new JLabel("W/S : Throttle/Break  ");
    private JLabel steeringExplainerText = new JLabel(" A/D :Turn Left/Right");
    private JLabel gearChangeExplainerText = new JLabel("K/L : Gear Up/Down");
    private JLabel parkingIndicatorExplainerText = new JLabel("P : Parking mode");
    private JLabel accIndicatorExplainerText = new JLabel("B : Automated Cruise Control");
    private JLabel laneKeepingIndicatorExplainerText = new JLabel("J : LaneKeeping");
    private JLabel timeGapExplainerText = new JLabel("U: Set time Gap");
    private JLabel referenceSpeedExplainer = new JLabel("I/O: Change ACC speed");
    private JLabel setIndex = new JLabel("Q/E : Left/Right");

    private JLabel lastRoadSign = new JLabel("No sign");
    private JLabel currentGearText = new JLabel("P");
    private JLabel speedLimitValueText = new JLabel("No limit");
    private JLabel steeringWheelValueText = new JLabel("0");
    private JLabel xCoordValueText = new JLabel("0");
    private JLabel yCoordValueText = new JLabel("0");

    private JProgressBar gasProgressBar = new JProgressBar(0, 100);
    private JProgressBar breakProgressBar = new JProgressBar(0, 100);

    private OMeter speedoMeter;
    private OMeter RPMometer;

    private StatusMarker accMarker;
    private StatusMarker ppmarker;
    private StatusMarker lkamarker;
    private StatusMarker lkwarnmarker;
    private StatusMarker aebwarnmarker;
    private StatusMarker rrwarnmarker;

    private StatusMarker timeGapMarker;
    private StatusMarker referenceSpeedMarker;


    private void CreateSpeedometer() {

        speedoMeter = new OMeter();
        speedoMeter.setPosition(new Point(0, 0));
        speedoMeter.setSize(new Point(100, 100));
        speedoMeter.setPerf_Percentage(0);
        speedoMeter.setBounds(10, 15, 110, 100);
    }

    private void CreateRPMmeter() {
        RPMometer = new OMeter();
        RPMometer.setPosition(new Point(0, 0));
        RPMometer.setSize(new Point(80, 80));
        RPMometer.setPerf_Percentage(0);
        RPMometer.setBounds(120, 25, 110, 100);
    }

    private void OMeterPlacing() {
        CreateRPMmeter();
        CreateSpeedometer();

        add(speedoMeter);
        add(RPMometer);
    }

    private void MarkerPlacing() {
        referenceSpeedMarker = new StatusMarker(10, 205, 50, 40, "0.0");
        timeGapMarker = new StatusMarker(60, 205, 50, 40, "0.8");
        accMarker = new StatusMarker(10, 250, 50, 40, "ACC");
        ppmarker = new StatusMarker(60, 250, 50, 40, "PP");
        lkamarker = new StatusMarker(10, 300, 50, 40, "LKA");
        lkwarnmarker = new StatusMarker(10, 350, 100, 40, "LKA WARN");
        aebwarnmarker = new StatusMarker(120, 310, 100, 40, "AEB WARN");
        rrwarnmarker = new StatusMarker(120, 350, 100, 40, "RR WARN");

        add(accMarker);
        add(ppmarker);
        add(lkamarker);
        add(lkwarnmarker);
        add(aebwarnmarker);
        add(rrwarnmarker);
        add(timeGapMarker);
        add(referenceSpeedMarker);
    }

    private void TextPlacing() {
        gearShiftText.setBounds(100, 150, 40, 15);
        currentGearText.setBounds(135, 150, 10, 15);
        accMenuText.setBounds(10, 190, 80, 15);
        gasPedalText.setBounds(10, 390, 100, 15);
        breakPedalText.setBounds(10, 420, 100, 15);
        speedLimitText.setBounds(10, 450, 120, 15);
        speedLimitValueText.setBounds(90, 450, 60, 15);
        steeringWheelText.setBounds(10, 620, 120, 15);
        steeringWheelValueText.setBounds(130, 620, 40, 15);
        xCoordText.setBounds(10, 635, 30, 15);
        xCoordValueText.setBounds(40, 635, 40, 15);
        yCoordText.setBounds(80, 635, 30, 15);
        yCoordValueText.setBounds(110, 635, 40, 15);
        currentSpeedText.setBounds(50, 125, 50, 15);
        currentRpmText.setBounds(150, 125, 50, 15);

        pedalExplainerText.setBounds(10, 485, 200, 15);
        steeringExplainerText.setBounds(10, 500, 200, 15);
        gearChangeExplainerText.setBounds(10, 515, 200, 15);
        setIndex.setBounds(10, 530, 200, 15);
        laneKeepingIndicatorExplainerText.setBounds(10, 560, 200, 15);
        parkingIndicatorExplainerText.setBounds(10, 545, 200, 15);
        accIndicatorExplainerText.setBounds(10, 575, 220, 15);
        timeGapExplainerText.setBounds(10, 590, 220, 15);
        referenceSpeedExplainer.setBounds(10, 605, 220, 15);

        lastRoadSign.setBounds(120, 205, 110, 110);

        add(lastRoadSign);
        add(gearShiftText);
        add(currentGearText);
        add(accMenuText);
        add(gasPedalText);
        add(breakPedalText);
        add(speedLimitText);
        add(speedLimitValueText);
        add(steeringWheelText);
        add(steeringWheelValueText);
        add(xCoordText);
        add(xCoordValueText);
        add(yCoordText);
        add(yCoordValueText);
        add(currentSpeedText);
        add(currentRpmText);

        add(pedalExplainerText);
        add(steeringExplainerText);
        add(gearChangeExplainerText);
        add(setIndex);
        add(accIndicatorExplainerText);
        add(parkingIndicatorExplainerText);
        add(laneKeepingIndicatorExplainerText);
        add(timeGapExplainerText);
        add(referenceSpeedExplainer);
    }

    public void turnIndexPlaceing() {
        leftTurn = new TurnIndex(10, 140, true);
        rightTurn = new TurnIndex(190, 140, false);

        add(leftTurn);
        add(rightTurn);
    }


    private void progressBarPlacing() {
        gasProgressBar.setBounds(10, 405, 200, 15);
        breakProgressBar.setBounds(10, 435, 200, 15);
        gasProgressBar.setStringPainted(true);
        breakProgressBar.setStringPainted(true);

        add(gasProgressBar);
        add(breakProgressBar);
    }

    private void placeElements() {
        turnIndexPlaceing();
        progressBarPlacing();
        TextPlacing();
        OMeterPlacing();
        MarkerPlacing();
    }

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
    }

    public void refresh(int yCoord, int xCoord) {
        gasProgressBar.setValue((int) virtualFunctionBus.guiInputPacket.getGasPedalValue());
        breakProgressBar.setValue((int) virtualFunctionBus.guiInputPacket.getBreakPedalValue());
        steeringWheelValueText.setText(Double.toString(virtualFunctionBus.guiInputPacket.getSteeringWheelValue()));
        currentGearText.setText(virtualFunctionBus.guiInputPacket.getShifterPos().toString());
        speedoMeter.setPerf_Percentage((int) virtualFunctionBus.guiInputPacket.getGasPedalValue());
        referenceSpeedMarker.setText(Double.toString(virtualFunctionBus.guiInputPacket.getAccSpeedValue()));
        timeGapMarker.setText(Double.toString(virtualFunctionBus.guiInputPacket.getAccFollowingDistanceValue()));
        accMarker.switchIt(virtualFunctionBus.guiInputPacket.getACCStatus());
        ppmarker.switchIt(virtualFunctionBus.guiInputPacket.getParkingPilotStatus());
        lkamarker.switchIt(virtualFunctionBus.guiInputPacket.getLaneKeepingAssistant());
        leftTurn.setOn(leftIndex(virtualFunctionBus.guiInputPacket.getIndexStatus()));
        rightTurn.setOn(rightIndex(virtualFunctionBus.guiInputPacket.getIndexStatus()));
        yCoordValueText.setText(Integer.toString(yCoord));
        xCoordValueText.setText(Integer.toString(xCoord));

    }

    private boolean rightIndex(Index.IndexStatus status) {
        if (status == Index.IndexStatus.RIGHT) {
            return true;
        } else {
            return false;
        }
    }

    private boolean leftIndex(Index.IndexStatus status) {
        if (status == Index.IndexStatus.LEFT) {
            return true;
        } else {
            return false;
        }
    }

    public Dashboard(Gui pt) {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBackground(new Color(backgroundColor));
        setBounds(770, 0, width, height);
        parent = pt;
        placeElements();
        //timer.start();
    }
}
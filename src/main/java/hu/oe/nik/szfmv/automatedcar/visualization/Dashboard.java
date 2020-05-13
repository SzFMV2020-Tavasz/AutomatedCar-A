package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.Main;
import hu.oe.nik.szfmv.automatedcar.powertrain.SimpleTransmission;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Index;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * Dashboard shows the state of the ego car, thus helps in debugging.
 */
public class Dashboard extends JPanel {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    private final int width = 235;  // this is the actual shown inner size
    private final int height = 700;
    private final int backgroundColor = 0x888888;

    private final int padding = 10;
    private final int separator = 10;
    private final int statusMarkerSmall = 45;
    private final int statusMarkerLarge = 65;
    private final int statusMarkerHeight = 40;
    private final int statusMarkerSmallFromRight = width - padding - statusMarkerSmall;
    private final int statusMarkerSmallSecond = statusMarkerSmall + separator + padding;
    private final int statusMarkerSmallThird = statusMarkerSmallFromRight - statusMarkerSmall - separator;
    private final int statusMarkerLargeFromRight = width - padding - statusMarkerLarge;
    private final int statusMarkersTop = 245;


    private Gui parent;
    private VirtualFunctionBus virtualFunctionBus;
    private TurnIndex leftTurn;
    private TurnIndex rightTurn;

    private ParkingRadarGui parkingRadarGui;

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
    private JLabel accIndicatorExplainerText = new JLabel("R : Automated Cruise Control");
    private JLabel laneKeepingIndicatorExplainerText = new JLabel("J : LaneKeeping");
    private JLabel timeGapExplainerText = new JLabel("T: Set time Gap");
    private JLabel referenceSpeedExplainer = new JLabel("I/O: Change ACC speed");
    private JLabel setIndex = new JLabel("Q/E : Index Left/Right");

    private JLabel lastRoadSign = new JLabel("No sign");
    private JLabel currentGearText = new JLabel("P");
    private JLabel speedLimitValueText = new JLabel("No limit");
    private JLabel steeringWheelValueText = new JLabel("0");
    private JLabel xCoordValueText = new JLabel("0");
    private JLabel yCoordValueText = new JLabel("0");

    private JProgressBar gasProgressBar = new JProgressBar(0, 100);
    private JProgressBar breakProgressBar = new JProgressBar(0, 100);

    private OMeter speedoMeter;
    private OMeter rpmometer;

    private StatusMarker accMarker;
    private StatusMarker ppmarker;
    private StatusMarker lkamarker;
    private StatusMarker lkwarnmarker;
    private StatusMarker aebwarnmarker;
    private StatusMarker rrwarnmarker;

    private StatusMarker timeGapMarker;
    private StatusMarker referenceSpeedMarker;


    public Dashboard(Gui pt) {
        // Not using any layout manager, but fixed coordinates
        setLayout(null);
        setBackground(new Color(backgroundColor));
        setBounds(770, 0, width, height);
        parent = pt;
        placeElements();
        //timer.start();
    }

    private void createSpeedometer() {

        speedoMeter = new OMeter();
        speedoMeter.setPosition(new Point(0, 0));
        speedoMeter.setSize(new Point(100, 100));
        speedoMeter.setPerfPercentage(0);
        speedoMeter.setBounds(10, 15, 110, 100);
    }

    private void createRPMmeter() {
        rpmometer = new OMeter();
        rpmometer.setPosition(new Point(0, 0));
        rpmometer.setSize(new Point(80, 80));
        rpmometer.setPerfPercentage(0);
        rpmometer.setBounds(120, 25, 110, 100);
    }

    private void oMeterPlacing() {
        createRPMmeter();
        createSpeedometer();

        add(speedoMeter);
        add(rpmometer);
    }

    private void markerPlacing() {

        int rowTop = statusMarkersTop;
        // row 1
        referenceSpeedMarker = new StatusMarker(
                padding, rowTop, statusMarkerSmall, statusMarkerHeight, "0.0");
        timeGapMarker = new StatusMarker(
                statusMarkerSmallSecond, rowTop, statusMarkerSmall, statusMarkerHeight, "0.8");
        accMarker = new StatusMarker(
                statusMarkerSmallThird, rowTop, statusMarkerSmall, statusMarkerHeight, "ACC");
        ppmarker = new StatusMarker(
                statusMarkerSmallFromRight, rowTop, statusMarkerSmall, statusMarkerHeight, "PP");
        rowTop += statusMarkerHeight + separator;
        // row 3
        lkamarker = new StatusMarker(
                padding, rowTop, statusMarkerLarge, statusMarkerHeight, "LKA");
        aebwarnmarker = new StatusMarker(
                statusMarkerLargeFromRight, rowTop, statusMarkerLarge, statusMarkerHeight, "AEB WARN");
        rowTop += statusMarkerHeight + separator;
        // row 4
        lkwarnmarker = new StatusMarker(
                padding, rowTop, statusMarkerLarge, statusMarkerHeight, "LKA WARN");
        rrwarnmarker = new StatusMarker(
                statusMarkerLargeFromRight, rowTop, statusMarkerLarge, statusMarkerHeight, "RR WARN");

        add(accMarker);
        add(ppmarker);
        add(lkamarker);
        add(lkwarnmarker);
        add(aebwarnmarker);
        add(rrwarnmarker);
        add(timeGapMarker);
        add(referenceSpeedMarker);
    }

    private void textPlacing() {
        gearShiftText.setBounds(100, 150, 40, 15);
        currentGearText.setBounds(135, 150, 10, 15);
        accMenuText.setBounds(10, 225, 80, 15);
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

        lastRoadSign.setBounds(padding + statusMarkerLarge + separator, 255,
                width - 2 * (padding + statusMarkerLarge + separator), 110);

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

    public void parkingRadarPlacing() {
        parkingRadarGui = new ParkingRadarGui(padding, 175, width - 2 * padding, backgroundColor);
        add(parkingRadarGui);
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
        textPlacing();
        parkingRadarPlacing();
        oMeterPlacing();
        markerPlacing();

    }

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
    }

    public void refresh(int xCoord, int yCoord) {
        gasProgressBar.setValue((int) virtualFunctionBus.guiInputPacket.getGasPedalValue());
        breakProgressBar.setValue((int) virtualFunctionBus.guiInputPacket.getBreakPedalValue());
        steeringWheelValueText.setText(Double.toString(virtualFunctionBus.guiInputPacket.getSteeringWheelValue()));
        currentGearText.setText(virtualFunctionBus.guiInputPacket.getShifterPos().toString());
        speedoMeter.setPerfPercentage((int) virtualFunctionBus.powerTrain.getMovement().getSpeed());
        rpmometer.setPerfPercentage((int) (virtualFunctionBus.powerTrain.getEngineStatus().getRPM() / SimpleTransmission.MAX_RPM * 100));
        referenceSpeedMarker.setText(Double.toString(virtualFunctionBus.cruiseControl.getState().getTargetSpeed()));
        timeGapMarker.setText(Double.toString(virtualFunctionBus.guiInputPacket.getAccFollowingDistanceValue()));
        accMarker.switchIt(virtualFunctionBus.guiInputPacket.getACCStatus());
        ppmarker.switchIt(virtualFunctionBus.guiInputPacket.getParkingPilotStatus());
        lkamarker.switchIt(virtualFunctionBus.guiInputPacket.getLaneKeepingAssistant());
        leftTurn.setOn(leftIndex(virtualFunctionBus.guiInputPacket.getIndexStatus()));
        rightTurn.setOn(rightIndex(virtualFunctionBus.guiInputPacket.getIndexStatus()));
        yCoordValueText.setText(Integer.toString(yCoord));
        xCoordValueText.setText(Integer.toString(xCoord));
        parkingRadarGui.setDistanceLeft(virtualFunctionBus.leftParkingDistance.getDistance());
        parkingRadarGui.setDistanceRight(virtualFunctionBus.rightParkingDistance.getDistance());
        parkingRadarGui.setState(virtualFunctionBus.parkingRadarGuiStatePacket.getParkingRadarGuiState());
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
}

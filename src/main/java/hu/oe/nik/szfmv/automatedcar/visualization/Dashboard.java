package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
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


    private final int width = 250;
    private final int height = 700;
    private final int backgroundColor = 0x888888;

    Gui parent;

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

    private JLabel pedalExplainerText=new JLabel("W/S : Throttle/Break  ");
    private JLabel steeringExplainerText=new JLabel(" A/D :Turn Left/Right");
    private JLabel gearChangeExplainerText=new JLabel("K/L : Gear Up/Down");
    private JLabel parkingIndicatorExplainerText=new JLabel("P : Parking mode");
    private JLabel accIndicatorExplainerText=new JLabel("B : Automated Cruise Control");
    private JLabel laneKeepingIndicatorExplainerText=new JLabel("J : LaneKeeping");
    private JLabel timeGapExplainerText=new JLabel("U: Set time Gap");
    private JLabel referenceSpeedExplainer=new JLabel("I/O: Change ACC speed");
    private JLabel setIndex=new JLabel("Q/E : Left/Right");

    private JLabel lastRoadSign=new JLabel("No sign");
    private JLabel currentGearText = new JLabel("P");
    private JLabel speedLimitValueText = new JLabel("No limit");
    private JLabel steeringWheelValueText = new JLabel("0");
    private JLabel xCoordValueText = new JLabel("0");
    private JLabel yCoordValueText = new JLabel("0");

    private JProgressBar gasProgressBar = new JProgressBar(0, 100);
    private JProgressBar breakProgressBar = new JProgressBar(0, 100);

    private OMeter speedoMeter;
    private OMeter RPMometer;

    private StatusMarker AccMarker;
    private StatusMarker PPMarker;
    private StatusMarker LKAMarker;
    private StatusMarker LKWARNMarker;
    private StatusMarker AEBWARNMarker;
    private StatusMarker RRWARNMArker;

    private StatusMarker TimeGapMarker;
    private StatusMarker ReferenceSpeedMarker;


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
        ReferenceSpeedMarker = new StatusMarker(10, 205, 50, 40, "0.0");
        TimeGapMarker = new StatusMarker(60, 205, 50, 40, "0.8");
        AccMarker = new StatusMarker(10, 250, 50, 40, "ACC");
        PPMarker = new StatusMarker(60, 250, 50, 40, "PP");
        LKAMarker = new StatusMarker(10, 300, 50, 40, "LKA");
        LKWARNMarker = new StatusMarker(10, 350, 100, 40, "LKA WARN");
        AEBWARNMarker = new StatusMarker(120, 310, 100, 40, "AEB WARN");
        RRWARNMArker = new StatusMarker(120, 350, 100, 40, "RR WARN");

        add(AccMarker);
        add(PPMarker);
        add(LKAMarker);
        add(LKWARNMarker);
        add(AEBWARNMarker);
        add(RRWARNMArker);
        add(TimeGapMarker);
        add(ReferenceSpeedMarker);
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
        currentRpmText.setBounds(150, 125, 50,15);

        pedalExplainerText.setBounds(10,485,200,15);
        steeringExplainerText.setBounds(10,500,200,15);
        gearChangeExplainerText.setBounds(10,515,200,15);
        setIndex.setBounds(10,530,200,15);
        laneKeepingIndicatorExplainerText.setBounds(10,560,200,15);
        parkingIndicatorExplainerText.setBounds(10,545,200,15);
        accIndicatorExplainerText.setBounds(10,575,220,15);
        timeGapExplainerText.setBounds(10,590,220,15);
        referenceSpeedExplainer.setBounds(10,605,220,15);

        lastRoadSign.setBounds(120,205,110,110);

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


    private void ProgressBarPlacing() {
        gasProgressBar.setBounds(10, 405, 200, 15);
        breakProgressBar.setBounds(10, 435, 200, 15);
        gasProgressBar.setStringPainted(true);
        breakProgressBar.setStringPainted(true);

        add(gasProgressBar);
        add(breakProgressBar);
    }

    private void placeElements() {
      // Turn_SignalPlacing();
        ProgressBarPlacing();
        TextPlacing();
        OMeterPlacing();
        MarkerPlacing();
    }

/*
    private void inputEventHandling(InputPacket inputPacket) {
        gasProgressBar.setValue(inputPacket.getGasPedalValue());
        breakProgressBar.setValue(inputPacket.getBreakPedalValue());
        steeringWheelValueText.setText(String.valueOf(inputPacket.getSteeringWheelValue()));
        //left_Turn_Signal.setOn(inputPacket.getLeftSignalValue());
        //right_Turn_Signal.setOn(inputPacket.getRightSignalValue());
        TimeGapMarker.setText(String.valueOf(inputPacket.getAccSpeed()));
        ReferenceSpeedMarker.setText(String.valueOf(inputPacket.getAccSpeed()));
        currentGearText.setText(String.valueOf(inputPacket.getShiftValue()));
        AccMarker.switchIt(inputPacket.getAccState());
        PPMarker.switchIt(inputPacket.getParkingPilotSwitch());
        LKAMarker.switchIt(inputPacket.getLaneKeepingAssistantSwitch());
*/
   /* private void OtherEventHandling(PowertrainPacket packet) {
        speedoMeter.setPerf_Percentage(packet.getVelocity());
        RPMmeter.setPerf_Percentage(packet.getRPM());
        AutomatedCar car = parent.getAutomatedCar();

        xCoordValueText.setText(String.valueOf(car.getPosX()));
        yCoordValueText.setText(String.valueOf(car.getPosY()));
        currentSpeedText.setText(String.valueOf(packet.getVelocity()) + " KM/H");
        currentRpmText.setText(String.valueOf(packet.getRPM()));
    }*/

    /*public void RoadSignEventHandling() {
        AutomatedCar car = parent.getAutomatedCar();
        WorldManager manager = parent.getManager();

        int width = parent.getCourseDisplay().getWidth();
        int height = parent.getCourseDisplay().getHeight();
        int offsetX = (width / 2) - (car.getPosX() - car.getReferenceX() + (car.getWidth() / 2));
        int offsetY = (height / 2) - (car.getPosY() - car.getReferenceY() + (car.getHeight() / 2));

        List<IObject> sensedObjects = car.getCamera().loop(manager, car, offsetX, offsetY, car.getRotation());

        if (sensedObjects.isEmpty()) {
            roadSign = null;
        } else {
            Point closestPoint = null;
            Map<Point, IObject> signPoints = new HashMap<>();
            for (IObject object : sensedObjects) {
                if (object instanceof Sign) {
                    signPoints.put(new Point(object.getPosX(), object.getPosY()), object);
                }
            }
            closestPoint = Collections.min(signPoints.keySet(),
                    (final Point p1, final Point p2) -> (int)p1.distanceSq(p2));
            roadSign = (Sign)signPoints.get(closestPoint);
        }

    private void drawDebugGridLayout() {
        GridLayout debugGridLayout = new GridLayout(4, 1, 8, 8);


        if (roadSign != null) {
            ImageIcon i=new ImageIcon(roadSign.getImage());


            lastRoadSign.setIcon(i);
            lastRoadSign.setText(null);
            speedLimitValueText.setText(roadSign.getSpeedLimit());
    }*/

   /* private void AEBEventHandling(AEBPacket packet){
        if(packet.getState()== AEBState.COLLISION_AVOIDABLE)
            AEBWARNIndicator.switchIt(true);
        else{
            AEBWARNIndicator.switchIt(false);
        }
        RRWARNIndicator.switchIt(packet.isAebNotOptimal());
    }
    private void EventHandling() {
        VirtualFunctionBus virtualFunctionBus = parent.getVirtualFunctionBus();
        if (virtualFunctionBus != null) {
            if(virtualFunctionBus.inputPacket != null)
                inputEventHandling(virtualFunctionBus.inputPacket);
            if(virtualFunctionBus.powertrainPacket != null)
                OtherEventHandling(virtualFunctionBus.powertrainPacket);
            if(virtualFunctionBus.samplePacket != null)
                RoadSignEventHandling();
            if(virtualFunctionBus.emergencyBrakePacket!=null)
                AEBEventHandling(virtualFunctionBus.emergencyBrakePacket);
        }
    }*/

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
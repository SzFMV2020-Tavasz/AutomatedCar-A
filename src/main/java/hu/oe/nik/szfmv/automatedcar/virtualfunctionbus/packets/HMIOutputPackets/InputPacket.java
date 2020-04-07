package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.shitfer.ShiftPos;

public class InputPacket implements BaseInputPacket {

    private int gasPedalValue;

    public void setGasPedalValue(int gasPedalValue) {
        this.gasPedalValue = gasPedalValue;
    }

    @Override
    public int getGasPedalValue() {
        return gasPedalValue;
    }


    private int breakPedalValue;

    public void setBreakPedalValue(int breakPedalValue) {
        this.breakPedalValue = breakPedalValue;
    }

    @Override
    public int getBreakPedalValue() {
        return breakPedalValue;
    }


    private int steeringWheelValue;

    public void setSteeringWheelValue(int steeringWheelValue) {
        this.steeringWheelValue = steeringWheelValue;
    }

    @Override
    public int getSteeringWheelValue() {
        return steeringWheelValue;
    }


    private int indexValue;

    public void setIndexValue(int indexValue) {
        this.indexValue = indexValue;
    }

    @Override
    public int getIndexValue() {
        return indexValue;
    }


    private ShiftPos shiftValue;

    public void setShiftValue(ShiftPos value) {
        shiftValue = shiftValue;
    }

    @Override
    public ShiftPos getShiftValue() {
        return shiftValue;
    }


    private int tempomatValue;

    public void setTempomatValue(int tempomatValue) {
        this.tempomatValue = tempomatValue;
    }

    @Override
    public int getTempomatValue() {
        return tempomatValue;
    }


    private boolean tempomatSwitch;

    public void setTempomatSwitch(boolean tempomatSwitch) {
        this.tempomatSwitch = tempomatSwitch;
    }

    @Override
    public boolean getTempomatSwitch() {
        return tempomatSwitch;
    }


    private boolean debugSwitch;

    public void setDebugSwitch(boolean debugSwitch) {
        this.debugSwitch = debugSwitch;
    }

    @Override
    public int getDebugSwitch() {
        return 0;
    }


    private boolean laneTrackingAssistantSwitch;

    public void setLaneTrackingAssistantSwitch(boolean laneTrackingAssistantSwitch) {
        this.laneTrackingAssistantSwitch = laneTrackingAssistantSwitch;
    }

    @Override
    public boolean getLaneKeepingAssistantSwitch() {
        return laneTrackingAssistantSwitch;
    }


    private boolean parkingPilotSwitch;

    public void setParkingPilotSwitch(boolean parkingPilotSwitch) {
        this.parkingPilotSwitch = parkingPilotSwitch;
    }

    @Override
    public boolean getParkingPilotSwitch() {
        return parkingPilotSwitch;
    }


    private int trackingDistanceValue;

    public void setTrackingDistanceValue(int trackingDistanceValue) {
        this.trackingDistanceValue = trackingDistanceValue;
    }

    @Override
    public double getTrackingDistanceValue() {
        return trackingDistanceValue;
    }


    private boolean trackingDistanceSwitch;

    public void setTrackingDistanceSwitch(boolean trackingDistanceSwitch) {
        this.trackingDistanceSwitch = trackingDistanceSwitch;
    }

    @Override
    public boolean getTrackingDistanceSwitch() {
        return trackingDistanceSwitch;
    }


    private boolean accState = false;

    public boolean getAccState() {
        return accState;
    }

    public void setAccState(boolean value) {
        accState = value;
    }


    private int accSpeed = 50;

    public void setAccSpeed(int value) {
        accSpeed = value;
    }

    public int getAccSpeed() {
        return accSpeed;
    }


    private double accFollowerGap = 0.8;

    public double getAccFollowerGap() {
        return accFollowerGap;
    }

    public void setAccFollowerGap(double value) {
        accFollowerGap = value;
    }


    private boolean signalLeftValue = false;

    public boolean getSignalLeftValue() {
        return signalLeftValue;
    }

    public void setSignalLeftValue(boolean value) {
        signalLeftValue = value;
    }


    private boolean signalRightValue = false;

    public boolean getSignalRightValue() {
        return signalRightValue;
    }

    public void setSignalRightValue(boolean value) {
        signalRightValue = value;
    }


}


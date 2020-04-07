package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmiOutputPackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.shitfer;

public class ToPowerTrainPacket implements ReadOnlyToPowerTrainPacket {

    private double gasPedalValue;
    private double breakPedalValue;
    private double steeringWheelValue;
    private shitfer.ShiftPos shiftChangeRequest;
    private int tempomatValue;
    private boolean tempomatSwitch;
    private double trackingDistanceValue;
    private boolean trackingDistanceSwitch;

    public void setGasPedalValue(double gasPedalValue) {
        this.gasPedalValue = gasPedalValue;
    }

    @Override
    public double getGasPedalValue() {
        return gasPedalValue;
    }


    public void setBreakPedalValue(double breakPedalValue) {
        this.breakPedalValue = breakPedalValue;
    }

    @Override
    public double getBreakPedalValue() {
        return breakPedalValue;
    }


    public void setSteeringWheelValue(double steeringWheelValue) {
        this.steeringWheelValue = steeringWheelValue;
    }

    @Override
    public double getSteeringWheelValue() {
        return steeringWheelValue;
    }


    public void setShiftChangeRequest(shitfer.ShiftPos shiftChangeRequest) {
        this.shiftChangeRequest = shiftChangeRequest;
    }

    @Override
    public shitfer.ShiftPos getShiftChangeRequest() {
        return shiftChangeRequest;
    }


    public void setTempomatValue(int tempomatValue) {
        this.tempomatValue = tempomatValue;
    }

    @Override
    public int getTempomatValue() {
        return tempomatValue;
    }


    public void setTempomatSwitch(boolean tempomatSwitch) {
        this.tempomatSwitch = tempomatSwitch;
    }

    @Override
    public boolean getTempomatSwitch() {
        return tempomatSwitch;
    }


    public void setTrackingDistanceValue(double trackingDistanceValue) {
        this.trackingDistanceValue = trackingDistanceValue;
    }

    @Override
    public double getTrackingDistanceValue() {
        return trackingDistanceValue;
    }


    public void setTrackingDistanceSwitch(boolean trackingDistanceSwitch) {
        this.trackingDistanceSwitch = trackingDistanceSwitch;
    }

    @Override
    public boolean getTrackingDistanceSwitch() {
        return trackingDistanceSwitch;
    }
}

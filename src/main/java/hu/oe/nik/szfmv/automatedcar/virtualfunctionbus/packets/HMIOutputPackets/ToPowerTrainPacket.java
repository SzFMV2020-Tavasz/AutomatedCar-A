package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

public class ToPowerTrainPacket implements ReadOnlyToPowerTrainPacket {

    private double gasPedalValue;//0-100
    public void setGasPedalValue(double gasPedalValue){
        this.gasPedalValue=gasPedalValue;
    }
    @Override
    public double getGasPedalValue() {
        return gasPedalValue;
    }


    private double breakPedalValue;//0-100
    public void setBreakPedalValue(double breakPedalValue){
        this.breakPedalValue=breakPedalValue;
    }
    @Override
    public double getBreakPedalValue() {
        return breakPedalValue;
    }


    private double steeringWheelValue;//not determined yet, probably -180-180
    public void setSteeringWheelValue(double steeringWheelValue){
        this.steeringWheelValue=steeringWheelValue;
    }
    @Override
    public double getSteeringWheelValue() {
        return steeringWheelValue;
    }


    private Shitfer.ShiftPos shiftChangeRequest;//P-R-N-D
    public void setShiftChangeRequest(Shitfer.ShiftPos shiftChangeRequest) {
        this.shiftChangeRequest = shiftChangeRequest;
    }
    @Override
    public Shitfer.ShiftPos getShiftChangeRequest() {
        return shiftChangeRequest;
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


    private double trackingDistanceValue;
    public void setTrackingDistanceValue(double trackingDistanceValue) {
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
}

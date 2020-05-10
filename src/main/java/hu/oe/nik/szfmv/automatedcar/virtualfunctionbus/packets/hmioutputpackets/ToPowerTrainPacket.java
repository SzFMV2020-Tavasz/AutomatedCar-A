package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

public class ToPowerTrainPacket implements ReadOnlyToPowerTrainPacket {

    /**The maximum absolute value returned by the {@link #getSteeringWheelValue()} method.*/
    public static final double MAX_STEERING_ROTATION = 180.0;

    private double gasPedalValue;
    private double breakPedalValue;
    private double steeringWheelValue;
    private Shitfer.ShiftPos shiftChangeRequest;
    private int tempomatValue;
    private boolean tempomatSwitch;
    private double trackingDistanceValue;
    private boolean trackingDistanceSwitch;

    @Override
    public double getGasPedalValue() {
        return gasPedalValue;
    }

    public void setGasPedalValue(double gasPedalValue) {
        this.gasPedalValue = gasPedalValue;
    }

    @Override
    public double getBreakPedalValue() {
        return breakPedalValue;
    }

    public void setBreakPedalValue(double breakPedalValue) {
        this.breakPedalValue = breakPedalValue;
    }

    @Override
    public double getSteeringWheelValue() {
        return steeringWheelValue;
    }

    public void setSteeringWheelValue(double steeringWheelValue) {
        this.steeringWheelValue = steeringWheelValue;
    }

    @Override
    public Shitfer.ShiftPos getShiftChangeRequest() {
        return shiftChangeRequest;
    }

    public void setShiftChangeRequest(Shitfer.ShiftPos shiftChangeRequest) {
        this.shiftChangeRequest = shiftChangeRequest;
    }

    @Override
    public int getTempomatValue() {
        return tempomatValue;
    }

    public void setTempomatValue(int tempomatValue) {
        this.tempomatValue = tempomatValue;
    }

    @Override
    public boolean getTempomatSwitch() {
        return tempomatSwitch;
    }

    public void setTempomatSwitch(boolean tempomatSwitch) {
        this.tempomatSwitch = tempomatSwitch;
    }

    @Override
    public double getTrackingDistanceValue() {
        return trackingDistanceValue;
    }

    public void setTrackingDistanceValue(double trackingDistanceValue) {
        this.trackingDistanceValue = trackingDistanceValue;
    }

    @Override
    public boolean getTrackingDistanceSwitch() {
        return trackingDistanceSwitch;
    }

    public void setTrackingDistanceSwitch(boolean trackingDistanceSwitch) {
        this.trackingDistanceSwitch = trackingDistanceSwitch;
    }
}

package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets;

import hu.oe.nik.szfmv.automatedcar.powertrain.CarTransmissionMode;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

import static java.lang.Math.toRadians;

public class ManualCarControlPacket implements IManualCarControlPacket {

    /**
     * The maximum absolute value given for setters of the {@link #gasPedalRatio} and {@link #breakPedalRatio} fields.
     */
    double MAX_PEDAL_VALUE = 100;

    private double gasPedalRatio;
    private double breakPedalRatio;
    private double steeringWheelRotation;
    private CarTransmissionMode shiftChangeRequest;
    private int tempomatValue;
    private boolean tempomatSwitch;
    private double trackingDistanceValue;
    private boolean trackingDistanceSwitch;

    @Override
    public double getGasPedalRatio() {
        return gasPedalRatio;
    }

    public void setGasPedalRatio(double ratio) {
        this.gasPedalRatio = ratio;
    }

    @Deprecated(forRemoval = true)
    public void setGasPedalValue(double gasPedalValue) {
        this.gasPedalRatio = gasPedalValue / MAX_PEDAL_VALUE;
    }

    @Override
    public double getBreakPedalRatio() {
        return breakPedalRatio;
    }

    public void setBreakPedalRatio(double ratio) {
        this.breakPedalRatio = ratio;
    }

    @Deprecated(forRemoval = true)
    public void setBreakPedalValue(double breakPedalValue) {
        this.breakPedalRatio = breakPedalValue / MAX_PEDAL_VALUE;
    }

    @Override
    public double getSteeringWheelRotation() {
        return steeringWheelRotation;
    }

    /**
     * @param rotationDegrees rotation of the steering wheel in degrees.
     */
    @Deprecated(forRemoval = true)
    public void setSteeringWheelValue(double rotationDegrees) {
        this.steeringWheelRotation = toRadians(rotationDegrees);
    }

    @Override
    public CarTransmissionMode getShiftChangeRequest() {
        return shiftChangeRequest;
    }

    public void setShiftChangeRequest(CarTransmissionMode requestedMode) {
        this.shiftChangeRequest = requestedMode;
    }

    @Deprecated(forRemoval = true)
    public void setShiftChangeRequest(Shitfer.ShiftPos shiftChangeRequest) {
        this.shiftChangeRequest = CarTransmissionMode.fromShiftPos(shiftChangeRequest);
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

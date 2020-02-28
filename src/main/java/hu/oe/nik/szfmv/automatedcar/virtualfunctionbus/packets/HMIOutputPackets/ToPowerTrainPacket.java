package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets;

public class ToPowerTrainPacket implements ReadOnlyToPowerTrainPacket {

    private int gasPedalValue;//0-100
    public void setGasPedalValue(int gasPedalValue){
        this.gasPedalValue=gasPedalValue;
    }
    @Override
    public int getGasPedalValue() {
        return gasPedalValue;
    }


    private int breakPedalValue;//0-100
    public void setBreakPedalValue(int breakPedalValue){
        this.breakPedalValue=breakPedalValue;
    }
    @Override
    public int getBreakPedalValue() {
        return breakPedalValue;
    }


    private int steeringWheelValue;//not determined yet, probably -180-180
    public void setSteeringWheelValue(int steeringWheelValue){
        this.steeringWheelValue=steeringWheelValue;
    }
    @Override
    public int getSteeringWheelValue() {
        return steeringWheelValue;
    }


    private char shiftChangeRequest;//P-R-N-D
    public void setShiftChangeRequest(char shiftChangeRequest) {
        this.shiftChangeRequest = shiftChangeRequest;
    }
    @Override
    public char getShiftChangeRequest() {
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

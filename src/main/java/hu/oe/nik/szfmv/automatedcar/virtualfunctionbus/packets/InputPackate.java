package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

public class InputPackate implements InputPacket {

    private int gasPedalValue;
    public void setGasPedalValue(int gasPedalValue){
        this.gasPedalValue=gasPedalValue;
    }
    @Override
    public int getGasPedalValue() {
        return gasPedalValue;
    }


    private int breakPedalValue;
    public void setbreakPedalValue(int breakPedalValue){
        this.breakPedalValue=breakPedalValue;
    }
    @Override
    public int getBreakPedalValue() {
        return breakPedalValue;
    }


    private int steeringWheelValue;
    public void setSteeringWheelValue(int steeringWheelValue){
        this.steeringWheelValue=steeringWheelValue;
    }
    @Override
    public int getSteeringWheelValue() {
        return steeringWheelValue;
    }


    private int indexValue;
    public void setIndexValue(int indexValue){
        this.indexValue=indexValue;
    }
    @Override
    public int getIndexValue() {
        return indexValue;
    }


    private char shiftValue;
    public void setShiftValue(char shiftValue){
        this.shiftValue=shiftValue;
    }
    @Override
    public char getShiftValue() {
        return shiftValue;
    }


    private int tempomatValue;
    public void setTempomatValue(int tempomatValue){
        this.tempomatValue=tempomatValue;
    }
    @Override
    public int getTempomatValue() {
        return tempomatValue;
    }


    private boolean tempomatSwitch;
    public void setTempomatSwitch(boolean tempomatSwitch){
        this.tempomatSwitch=tempomatSwitch;
    }
    @Override
    public boolean getTempomatSwitch() {
        return tempomatSwitch;
    }


    private boolean debugSwitch;
    public void setDebugSwitch(boolean debugSwitch){
        this.debugSwitch=debugSwitch;
    }
    @Override
    public int getDebugSwitch() {
        return 0;
    }


    private boolean laneTrackingAssistantSwitch;
    public void setLaneTrackingAssistantSwitch(boolean laneTrackingAssistantSwitch){
        this.laneTrackingAssistantSwitch=laneTrackingAssistantSwitch;
    }
    @Override
    public boolean getLaneKeepingAssistantSwitch() {
        return laneTrackingAssistantSwitch;
    }


    private boolean parkingPilotSwitch;
    public void setParkingPilotSwitch(boolean parkingPilotSwitch){
        this.parkingPilotSwitch=parkingPilotSwitch;
    }
    @Override
    public boolean getParkingPilotSwitch() {
        return parkingPilotSwitch;
    }


    private int trackingDistanceValue;
    public void setTrackingDistanceValue(int trackingDistanceValue){
        this.trackingDistanceValue=trackingDistanceValue;
    }
    @Override
    public double getTrackingDistanceValue() {
        return trackingDistanceValue;
    }


    private boolean trackingDistanceSwitch;
    public void setTrackingDistanceSwitch(boolean trackingDistanceSwitch){
        this.trackingDistanceSwitch=trackingDistanceSwitch;
    }
    @Override
    public boolean getTrackingDistanceSwitch() {
        return trackingDistanceSwitch;
    }
}

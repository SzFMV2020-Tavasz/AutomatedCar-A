package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Index;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

public class GuiInputPacket implements ReadOnlyGuiInputPacket {

    private Index.IndexStatus indexStatus;

    public void setIndexStatus(Index.IndexStatus indexStatus) {
        this.indexStatus = indexStatus;
    }

    @Override
    public Index.IndexStatus getIndexStatus() {
        return indexStatus;
    }

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


    private Shitfer.ShiftPos shiftChangeRequest = Shitfer.ShiftPos.P;//P-R-N-D
    public void setShifterPos(Shitfer.ShiftPos shiftChangeRequest) {
        this.shiftChangeRequest = shiftChangeRequest;
    }

    @Override
    public Shitfer.ShiftPos getShifterPos() {
        return shiftChangeRequest;
    }

    private boolean accSwitch;

    public void setAccSwitch(boolean accSwitch) {
        this.accSwitch = accSwitch;
    }

    @Override
    public boolean getACCStatus() {
        return accSwitch;
    }

    private int accSpeedValue=30;

    public void setAccSpeedValue(int accSpeedValue) {
        this.accSpeedValue = accSpeedValue;
    }

    @Override
    public int getAccSpeedValue() {
        return accSpeedValue;
    }

    private double accFollowingDistance;

    public void setAccFollowingDistance(double accFollowingDistance) {
        this.accFollowingDistance = accFollowingDistance;
    }

    @Override
    public double getAccFollowingDistanceValue() {
        return accFollowingDistance;
    }

    private boolean parkingPilotStatus;

    public void setParkingPilotStatus(boolean parkingPilotStatus) {
        this.parkingPilotStatus = parkingPilotStatus;
    }

    @Override
    public boolean getParkingPilotStatus() {
        return parkingPilotStatus;
    }

    private boolean laneKeepingAssistantStatus;

    public void setLaneKeepingAssistantStatus(boolean laneKeepingAssistantStatus) {
        this.laneKeepingAssistantStatus = laneKeepingAssistantStatus;
    }

    @Override
    public boolean getLaneKeepingAssistant() {
        return laneKeepingAssistantStatus;
    }

    private boolean debugSwitch;

    public void setDebugSwitch(boolean debugSwitch) {
        this.debugSwitch = debugSwitch;
    }

    @Override
    public boolean getDebugSwitch() {
        return debugSwitch;
    }
}

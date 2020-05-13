package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Index;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

public class GuiInputPacket implements ReadOnlyGuiInputPacket {

    private Index.IndexStatus indexStatus;
    private double gasPedalValue;
    private double breakPedalValue;
    private double steeringWheelValue;
    private Shitfer.ShiftPos shiftChangeRequest = Shitfer.ShiftPos.P;
    private boolean accSwitch;
    private int accSpeedValue = 30;
    private double accFollowingDistance = 0.8;
    private boolean parkingPilotStatus;
    private boolean laneKeepingAssistantStatus;
    private boolean debugSwitch;
    private boolean helpMenuSwitch;

    @Override
    public Index.IndexStatus getIndexStatus() {
        return indexStatus;
    }

    public void setIndexStatus(Index.IndexStatus indexStatus) {
        this.indexStatus = indexStatus;
    }

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
    public Shitfer.ShiftPos getShifterPos() {
        return shiftChangeRequest;
    }

    public void setShifterPos(Shitfer.ShiftPos shiftChangeRequest) {
        this.shiftChangeRequest = shiftChangeRequest;
    }

    public void setAccFollowingDistance(double accFollowingDistance) {
        this.accFollowingDistance = accFollowingDistance;
    }

    @Override
    public double getAccFollowingDistanceValue() {
        return accFollowingDistance;
    }

    @Override
    public boolean getParkingPilotStatus() {
        return parkingPilotStatus;
    }

    public void setParkingPilotStatus(boolean parkingPilotStatus) {
        this.parkingPilotStatus = parkingPilotStatus;
    }

    public void setLaneKeepingAssistantStatus(boolean laneKeepingAssistantStatus) {
        this.laneKeepingAssistantStatus = laneKeepingAssistantStatus;
    }

    @Override
    public boolean getLaneKeepingAssistant() {
        return laneKeepingAssistantStatus;
    }

    @Override
    public boolean getDebugSwitch() {
        return debugSwitch;
    }

    public void setDebugSwitch(boolean debugSwitch) {
        this.debugSwitch = debugSwitch;
    }

    @Override
    public boolean getHelpMenuSwitch() {
        return helpMenuSwitch;
    }

    public void setHelpMenuSwitch(boolean helpMenuSwitch) {
        this.helpMenuSwitch = helpMenuSwitch;
    }
}

package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmiOutputPackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Index;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.shitfer;

public class GuiInputPacket implements ReadOnlyGuiInputPacket {

    private Index.IndexStatus indexStatus;
    private double gasPedalValue;
    private double breakPedalValue;
    private double steeringWheelValue;
    private shitfer.ShiftPos shiftChangeRequest = shitfer.ShiftPos.P;
    private boolean accSwitch;
    private int accSpeedValue = 30;
    private double accFollowingDistance = 0.8;
    private boolean parkingPilotStatus;
    private boolean laneKeepingAssistantStatus;
    private boolean debugSwitch;
    private boolean helpMenuSwitch;


    public void setIndexStatus(Index.IndexStatus indexStatus) {
        this.indexStatus = indexStatus;
    }

    @Override
    public Index.IndexStatus getIndexStatus() {
        return indexStatus;
    }


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


    public void setShifterPos(shitfer.ShiftPos shiftChangeRequest) {
        this.shiftChangeRequest = shiftChangeRequest;
    }

    @Override
    public shitfer.ShiftPos getShifterPos() {
        return shiftChangeRequest;
    }


    public void setAccSwitch(boolean accSwitch) {
        this.accSwitch = accSwitch;
    }

    @Override
    public boolean getACCStatus() {
        return accSwitch;
    }


    public void setAccSpeedValue(int accSpeedValue) {
        this.accSpeedValue = accSpeedValue;
    }

    @Override
    public int getAccSpeedValue() {
        return accSpeedValue;
    }


    public void setAccFollowingDistance(double accFollowingDistance) {
        this.accFollowingDistance = accFollowingDistance;
    }

    @Override
    public double getAccFollowingDistanceValue() {
        return accFollowingDistance;
    }


    public void setParkingPilotStatus(boolean parkingPilotStatus) {
        this.parkingPilotStatus = parkingPilotStatus;
    }

    @Override
    public boolean getParkingPilotStatus() {
        return parkingPilotStatus;
    }


    public void setLaneKeepingAssistantStatus(boolean laneKeepingAssistantStatus) {
        this.laneKeepingAssistantStatus = laneKeepingAssistantStatus;
    }

    @Override
    public boolean getLaneKeepingAssistant() {
        return laneKeepingAssistantStatus;
    }


    public void setDebugSwitch(boolean debugSwitch) {
        this.debugSwitch = debugSwitch;
    }

    @Override
    public boolean getDebugSwitch() {
        return debugSwitch;
    }


    public void setHelpMenuSwitch(boolean helpMenuSwitch) {
        this.helpMenuSwitch = helpMenuSwitch;
    }

    @Override
    public boolean getHelpMenuSwitch() {
        return helpMenuSwitch;
    }
}

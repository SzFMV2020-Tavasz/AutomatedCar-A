package hu.oe.nik.szfmv.automatedcar.visualization;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets.InputPacket;

public class DashboardUpdater implements Updater {

    int rpmMeter;
    int speedMeter;
    int index;
    char gear;
    boolean tempomatSwitch;
    int tempomatValue;
    double trackingDistance;
    boolean parkingPilot;
    boolean laneKeepingAssistant;
    int gasPedal;

    public int getRpmMeter() {
        return rpmMeter;
    }

    public int getSpeedMeter() {
        return speedMeter;
    }

    public int getIndex() {
        return index;
    }

    public char getGear() {
        return gear;
    }

    public boolean isTempomatSwitch() {
        return tempomatSwitch;
    }

    public int getTempomatValue() {
        return tempomatValue;
    }

    public double getTrackingDistance() {
        return trackingDistance;
    }

    public boolean isParkingPilot() {
        return parkingPilot;
    }

    public boolean isLaneKeepingAssistant() {
        return laneKeepingAssistant;
    }

    public int getGasPedal() {
        return gasPedal;
    }

    public int getBreakPedal() {
        return breakPedal;
    }

    public int getSteeringWheelValue() {
        return steeringWheelValue;
    }

    int breakPedal;
    int steeringWheelValue;

    @Override
    public void setRpmMeter(int rpmMeter) {
        this.rpmMeter = rpmMeter;
    }

    @Override
    public void setSpeedMeter(int speedMeter) {
        this.speedMeter = speedMeter;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void setGear(char gear) {
        this.gear = gear;
    }

    @Override
    public void setTempomatSwitch(boolean tempomatSwitch) {
        this.tempomatSwitch = tempomatSwitch;
    }

    @Override
    public void setTempomatValue(int tempomatValue) {
        this.tempomatValue = tempomatValue;
    }

    @Override
    public void setTrackingDistance(double trackingDistance) {
        this.trackingDistance = trackingDistance;
    }

    @Override
    public void setParkingPilot(boolean parkingPilot) {
        this.parkingPilot = parkingPilot;
    }

    @Override
    public void setLaneKeepingAssistant(boolean laneKeepingAssistant) {
        this.laneKeepingAssistant = laneKeepingAssistant;
    }

    @Override
    public void setGasPedal(int gasPedal) {
        this.gasPedal = gasPedal;
    }

    @Override
    public void setBreakPedal(int breakPedal) {
        this.breakPedal = breakPedal;
    }

    @Override
    public void setSteeringWheelValue(int steeringWheelValue) {
        this.steeringWheelValue = steeringWheelValue;
    }
}


package hu.oe.nik.szfmv.automatedcar.visualization;

public interface Updater {

    void setRpmMeter(int speed);
    void setSpeedMeter(int rmp);
    void setIndex(int index);
    void setGear(char gear);
    void setTempomatValue(int tempomatValue);
    void setTempomatSwitch(boolean tempomatSwitch);
    void setTrackingDistance(double trackingDistance);
    void setParkingPilot(boolean parkinPilotSwitch);
    void setLaneKeepingAssistant(boolean laneKeepingAssistantSwitch);
    void setGasPedal(int gasPedalValue);
    void setBreakPedal(int breakPedalValue);
    void setSteeringWheelValue(int steeringWheelValue);

}

package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class ACC {

    private VirtualFunctionBus virtualFunctionBus;
    private boolean accSwitch;
    private boolean parkingPilotSwitch;
    private boolean laneKeepingAssistantSwitch;
    private int accSpeed = 30;
    private int accDistanceIndex;
    private double[] accDistance = new double[]{0.8, 1.0, 1.2, 1.4};

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
    }

    public void turnParkingPilotSwitchOff() {
        parkingPilotSwitch = false;
        virtualFunctionBus.guiInputPacket.setParkingPilotStatus(parkingPilotSwitch);
    }

    public void turnAccSwitch() {
        accSwitch = !accSwitch;
        virtualFunctionBus.guiInputPacket.setAccSwitch(accSwitch);
    }


    public void turnParkingPilotSwitch() {
        parkingPilotSwitch = !parkingPilotSwitch;
        virtualFunctionBus.guiInputPacket.setParkingPilotStatus(parkingPilotSwitch);
    }


    public void turnLaneKeepingAssistantSwitch() {
        laneKeepingAssistantSwitch = !laneKeepingAssistantSwitch;
        virtualFunctionBus.guiInputPacket.setLaneKeepingAssistantStatus(laneKeepingAssistantSwitch);
    }


    public void increaseAccSpeed() {
        if (accSpeed <= 150) {
            accSpeed += 10;
        }
        virtualFunctionBus.guiInputPacket.setAccSpeedValue(accSpeed);
    }

    public void decreaseAccSpeed() {
        if (accSpeed >= 40) {
            accSpeed -= 10;
        }
        virtualFunctionBus.guiInputPacket.setAccSpeedValue(accSpeed);
    }


    public void turnAccDistance() {
        accDistanceIndex = (accDistanceIndex + 1) % 4;
        virtualFunctionBus.guiInputPacket.setAccFollowingDistance(accDistance[accDistanceIndex]);
    }

}

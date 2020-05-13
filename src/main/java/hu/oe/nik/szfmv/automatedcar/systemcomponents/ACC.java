package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class ACC {

    private VirtualFunctionBus virtualFunctionBus;
    private boolean parkingPilotSwitch;
    private boolean laneKeepingAssistantSwitch;
    private int accDistanceIndex;
    private final double[] accDistance = { 0.8, 1.0, 1.2, 1.4 };

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
    }

    public void turnParkingPilotSwitchOff() {
        parkingPilotSwitch = false;
        virtualFunctionBus.guiInputPacket.setParkingPilotStatus(parkingPilotSwitch);
    }


    public void turnParkingPilotSwitch() {
        parkingPilotSwitch = !parkingPilotSwitch;
        virtualFunctionBus.guiInputPacket.setParkingPilotStatus(parkingPilotSwitch);
    }


    public void turnLaneKeepingAssistantSwitch() {
        laneKeepingAssistantSwitch = !laneKeepingAssistantSwitch;
        virtualFunctionBus.guiInputPacket.setLaneKeepingAssistantStatus(laneKeepingAssistantSwitch);
    }


    public void turnAccDistance() {
        accDistanceIndex = (accDistanceIndex + 1) % 4;
        virtualFunctionBus.guiInputPacket.setAccFollowingDistance(accDistance[accDistanceIndex]);
    }

}

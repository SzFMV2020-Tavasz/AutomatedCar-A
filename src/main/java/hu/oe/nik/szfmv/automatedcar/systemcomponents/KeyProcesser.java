package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class KeyProcesser {

    private VirtualFunctionBus virtualFunctionBus;
    private Index index = new Index();
    private boolean zeroIsPressed;
    private boolean controlIsPressed;
    private boolean helpMenuSwitch = false;
    private boolean debugMode;
    private PedalPosition pedalPos = new PedalPosition();
    private ACC accManager = new ACC();
    protected shitfer shiftManager = new shitfer();


    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
        pedalPos.setVirtualFunctionBus(virtualFunctionBus);
        accManager.setVirtualFunctionBus(virtualFunctionBus);
        shiftManager.setVirtualFunctionBus(virtualFunctionBus);
        index.setVirtualFunctionBus(virtualFunctionBus);
    }

    public void zeroPressed() {
        zeroIsPressed = true;
        setDebugMode();
    }

    public void zeroReleased() {
        zeroIsPressed = false;
    }

    public void controlPressed() {
        controlIsPressed = true;
        setDebugMode();
    }

    public void controlReleased() {
        controlIsPressed = false;
    }


    private void setDebugMode() {
        if (zeroIsPressed && controlIsPressed) {
            debugMode = !debugMode;
        }
        virtualFunctionBus.guiInputPacket.setDebugSwitch(debugMode);
        virtualFunctionBus.debugModePacket.setDebuggingState(debugMode);
    }


    public void helpButtonPressed() {
        helpMenuSwitch = !helpMenuSwitch;
        virtualFunctionBus.guiInputPacket.setHelpMenuSwitch(helpMenuSwitch);
    }


    public int KeyPressed(int keyCode) {

        return keyCode;
    }

    public void gasPedalPressed() {
        pedalPos.gasPedalDown();

    }

    public void gasPedalReleased() {
        pedalPos.gasPedalUp();
    }

    public void breakPedalPressed() {
        pedalPos.breakPedalDown();
    }

    public void breakPedalReleased() {
        pedalPos.breakPedalUp();
    }

    public void steeringLeftPressed() {
        pedalPos.startSteeringLeft();
    }

    public void steeringRightPressed() {
        pedalPos.startSteeringRight();
    }

    public void steeringReleased() {
        pedalPos.steeringWheelReleased();
    }


    public void turnAccSwitch() {
        accManager.turnAccSwitch();
    }

    public void turnLaneKeepingSwitch() {
        accManager.turnLaneKeepingAssistantSwitch();
    }

    public void turnParkingPilotSwitch() {
        accManager.turnParkingPilotSwitch();
    }

    public void increaseAccSpeed() {
        accManager.increaseAccSpeed();
    }

    public void decreaseAccSpeed() {
        accManager.decreaseAccSpeed();
    }

    public void turnAccDistance() {
        accManager.turnAccDistance();
    }


    public void GrowShift() {
        shiftManager.Increment();
    }

    public void LowerShift() {
        shiftManager.Decrement();
    }


    public void indexRight() {
        index.setStatusRight();
    }

    public void indexLeft() {
        index.setStatsLeft();
    }

}

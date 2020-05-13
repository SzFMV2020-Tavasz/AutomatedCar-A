package hu.oe.nik.szfmv.automatedcar.input;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.ACC;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Index;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.PedalPosition;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

import java.util.HashSet;

public class KeyProcesser {

    protected Shitfer shiftManager = new Shitfer();
    private VirtualFunctionBus virtualFunctionBus;
    private Index index = new Index();
    private boolean zeroIsPressed;
    private boolean controlIsPressed;
    private boolean helpMenuSwitch = false;
    private boolean debugMode;
    private PedalPosition pedalPos = new PedalPosition();
    private ACC accManager = new ACC();

    private final HashSet<Integer> pressedButtons = new HashSet<>();

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

    public void gasPedalPressed() {
        pedalPos.gasPedalDown();
        accManager.turnParkingPilotSwitchOff();
    }

    public void gasPedalReleased() {
        pedalPos.gasPedalUp();
    }

    public void breakPedalPressed() {
        pedalPos.breakPedalDown();
        accManager.turnParkingPilotSwitchOff();
    }

    public void breakPedalReleased() {
        pedalPos.breakPedalUp();
    }

    public void steeringLeftPressed() {
        pedalPos.startSteeringLeft();
        accManager.turnParkingPilotSwitchOff();
    }

    public void steeringRightPressed() {
        pedalPos.startSteeringRight();
        accManager.turnParkingPilotSwitchOff();
    }

    public void steeringReleased() {
        pedalPos.steeringWheelReleased();
    }

    public void turnLaneKeepingSwitch() {
        accManager.turnLaneKeepingAssistantSwitch();
    }

    public void turnParkingPilotSwitch() {
        accManager.turnParkingPilotSwitch();
    }

    public void turnAccDistance() {
        accManager.turnAccDistance();
    }


    public void growShift() {
        shiftManager.increment();
    }

    public void lowerShift() {
        shiftManager.decrement();
    }


    public void indexRight() {
        index.setStatusRight();
    }

    public void indexLeft() {
        index.setStatsLeft();
    }

    public void setAccButtonPressed(boolean pressed) {
        this.virtualFunctionBus.input.accInput.accButtonPressed = pressed;
    }

    public void setAccDecreaseSpeedButtonPressed(boolean pressed) {
        this.virtualFunctionBus.input.accInput.accDecreaseSpeedButtonPressed = pressed;
    }

    public void setAccIncreaseSpeedButtonPressed(boolean pressed) {
        this.virtualFunctionBus.input.accInput.accIncreaseSpeedButtonPressed = pressed;
    }
}

package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets.InputPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets.ToPowerTrainPacket;

import java.awt.event.KeyEvent;

public class KeyProcesser {

    private VirtualFunctionBus virtualFunctionBus;

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus){
        this.virtualFunctionBus=virtualFunctionBus;
        pedalPos.setVirtualFunctionBus(virtualFunctionBus);
        accManager.setVirtualFunctionBus(virtualFunctionBus);
        shiftManager.setVirtualFunctionBus(virtualFunctionBus);
        index.setVirtualFunctionBus(virtualFunctionBus);
    }

    private boolean zeroIsPressed;
    private boolean controlIsPressed;

    public void zeroPressed(){
        zeroIsPressed=true;
        setDebugMode();
    }

    public void zeroReleased(){
        zeroIsPressed=false;
    }

    public void controlPressed(){
        controlIsPressed=true;
        setDebugMode();
    }

    public void controlReleased(){
        controlIsPressed=false;
    }

    private boolean debugMode;

    private void setDebugMode(){
        if(zeroIsPressed && controlIsPressed){
            debugMode=!debugMode;
        }
        virtualFunctionBus.guiInputPacket.setDebugSwitch(debugMode);
    }

    private PedalPosition pedalPos = new PedalPosition();

    public int KeyPressed(int keyCode)
    {
        return keyCode;
    }

    public void gasPedalPressed(){
        pedalPos.gasPedalDown();

    }

    public void gasPedalReleased(){
        pedalPos.gasPedalUp();
    }

    public void breakPedalPressed(){pedalPos.breakPedalDown();}

    public void breakPedalReleased(){pedalPos.breakPedalUp();}

    public void steeringLeftPressed(){pedalPos.startSteeringLeft();}

    public void steeringRightPressed(){pedalPos.startSteeringRight();}

    public void steeringReleased(){pedalPos.steeringWheelReleased();}


    private ACC accManager = new ACC();

    public void turnAccSwitch(){accManager.turnAccSwitch();}

    public void turnLaneKeepingSwitch(){accManager.turnLaneKeepingAssistantSwitch();}

    public void turnParkingPilotSwitch(){accManager.turnParkingPilotSwitch();}

    public void increaseAccSpeed(){accManager.increaseAccSpeed();}

    public void decreaseAccSpeed(){accManager.decreaseAccSpeed();}

    public void turnAccDistance(){accManager.turnAccDistance();}


    protected Shitfer shiftManager = new Shitfer();

    public void GrowShift() {shiftManager.Increment();}

    public void LowerShift() {shiftManager.Decrement();}


    private Index index = new Index();

    public void indexRight(){index.setStatusRight();}

    public void indexLeft(){index.setStatsLeft();}

}

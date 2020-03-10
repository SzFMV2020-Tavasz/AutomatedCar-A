package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets.InputPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets.ToPowerTrainPacket;

import java.awt.event.KeyEvent;

public class KeyProcesser {

    private PedalPosition pedalPos = new PedalPosition();

    private Index index = new Index();

    ToPowerTrainPacket PTPacket = new ToPowerTrainPacket();

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


    private ACC accManager = new ACC();

    public void IsOnPressed() {
        accManager.IsOnPressedCheck();

    }

    public void MinusSpeedValue() {accManager.Minus();}

    public void PlusSpeedValue() {accManager.Plus();}

    public  void FollowerGapSetter() {accManager.FollowerGapSetter(); }

    protected InputPacket inputPacket = new InputPacket();

    public  double ChangeReturnFollowerGapSetter() {return accManager.ReturnFollowerGap();}


    protected Shitfer shiftManager = new Shitfer();

    public void GrowShift() {shiftManager.Increment();}

    public void LowerShift() {shiftManager.Decrement();}


    public void steeringLeftPressed(){pedalPos.startSteeringLeft();}

    public void steeringRightPressed(){pedalPos.startSteeringRight();}

    public void steeringReleased(){pedalPos.steeringWheelReleased();}

    public void indexRight(){index.setStatusRight();}

    public void indexLeft(){index.setStatsLeft();}

}

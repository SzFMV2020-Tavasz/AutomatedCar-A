package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.core.config.composite.DefaultMergeStrategy;

public class ACC extends SystemComponent {

    protected int avarageSpeed = 55;

    //why can i ref this in keylistenet??
    protected boolean isOn = false;

    private static final int minSpeed = 30;
    private static final int maxSpeed = 160;
    private static final int step = 10;

    public ACC(VirtualFunctionBus virtualFunctionBus)
    {
        super(virtualFunctionBus);
    }

    public void Set(int setSpeed) {
        avarageSpeed = setSpeed;
        isOn = true;
    }

    public void Plus() {
        if (avarageSpeed + step <= maxSpeed)
            avarageSpeed += step;
    }

    public void Minus() {
        if (avarageSpeed - step >= minSpeed)
            avarageSpeed -= step;
    }

    public int getReferenceSpeed()
    {
        return avarageSpeed;
    }




    private double[] followerGap = {0.8, 1.0, 1.2, 1.4};
    private int index = 0;

    public void FollowerGapSetter() {
        if (index < followerGap.length - 1) {
            ++index;
        } else {
            index = 0;
        }

    }

    public double ReturnTimeGap()
    {
        return followerGap[index];
    }

    public void turnOn() {
        virtualFunctionBus.inputPacket.setAccState(true);
        virtualFunctionBus.inputPacket.setAccSpeed(changeNewAccSpeed());
    }


    public void  turnOff()
    {
        virtualFunctionBus.inputPacket.setAccState(false);
    }

    private int changeNewAccSpeed() {
        var currentVelocity = virtualFunctionBus.toPowerTrainPacket.getGasPedalValue();
        if (currentVelocity >= 40 && currentVelocity <= 160) {
            return currentVelocity;
        } else {
            return virtualFunctionBus.inputPacket.getAccSpeed();
        }
    }


    @Override
    public void loop() {

        turnOff();
    }
}

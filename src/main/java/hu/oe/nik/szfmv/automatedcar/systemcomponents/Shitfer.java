package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class Shitfer {
    public enum ShiftPos {P, R, N, D}

    private int acttuallyValue = 0;

    private VirtualFunctionBus virtualFunctionBus;

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus){
        this.virtualFunctionBus=virtualFunctionBus;
    }

    public void Increment()
    {
        ShiftPos[] pos = ShiftPos.values();
        if (acttuallyValue < pos.length-1)
        {
            acttuallyValue++;
            virtualFunctionBus.toPowerTrainPacket.setShiftChangeRequest(pos[acttuallyValue]);
        }
    }

    public void Decrement()
    {
        if (acttuallyValue > 0)
        {
            acttuallyValue--;
            virtualFunctionBus.toPowerTrainPacket.setShiftChangeRequest(ShiftPos.values()[acttuallyValue]);
        }
    }

    public ShiftPos GetCurrentState()
    {
        ShiftPos[] curr = ShiftPos.values();
        return curr[acttuallyValue];
    }
}
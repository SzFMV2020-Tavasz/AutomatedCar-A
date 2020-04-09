package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;

public class Shitfer {
    private int acttuallyValue = 0;
    private VirtualFunctionBus virtualFunctionBus;

    public void setVirtualFunctionBus(VirtualFunctionBus virtualFunctionBus) {
        this.virtualFunctionBus = virtualFunctionBus;
    }

    public void increment() {
        ShiftPos[] pos = ShiftPos.values();
        if (acttuallyValue < pos.length - 1) {
            acttuallyValue++;
            virtualFunctionBus.toPowerTrainPacket.setShiftChangeRequest(pos[acttuallyValue]);
            virtualFunctionBus.guiInputPacket.setShifterPos(pos[acttuallyValue]);
        }
    }

    public void decrement() {
        if (acttuallyValue > 0) {
            acttuallyValue--;
            virtualFunctionBus.toPowerTrainPacket.setShiftChangeRequest(ShiftPos.values()[acttuallyValue]);
            virtualFunctionBus.guiInputPacket.setShifterPos(ShiftPos.values()[acttuallyValue]);
        }
    }

    public ShiftPos getCurrentState() {
        ShiftPos[] curr = ShiftPos.values();
        return curr[acttuallyValue];
    }

    public enum ShiftPos {
        P, R, N, D
    }
}

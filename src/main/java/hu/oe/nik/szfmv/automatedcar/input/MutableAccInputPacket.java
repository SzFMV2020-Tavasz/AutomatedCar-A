package hu.oe.nik.szfmv.automatedcar.input;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.cruisecontrol.IAccInputPacket;

public final class MutableAccInputPacket implements IAccInputPacket {

    boolean accButtonPressed = false;
    boolean accIncreaseSpeedButtonPressed = false;
    boolean accDecreaseSpeedButtonPressed = false;

    @Override
    public boolean isAccButtonPressed() {
        return accButtonPressed;
    }

    @Override
    public boolean isAccIncreaseSpeedButtonPressed() {
        return accIncreaseSpeedButtonPressed;
    }

    @Override
    public boolean isAccDecreaseSpeedButtonPressed() {
        return accDecreaseSpeedButtonPressed;
    }
}

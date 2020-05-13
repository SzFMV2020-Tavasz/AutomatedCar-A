package hu.oe.nik.szfmv.automatedcar.input;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.cruisecontrol.IAccInputPacket;

public class InputPackets {

    final MutableAccInputPacket accInput = new MutableAccInputPacket();

    public final IAccInputPacket getAccInput() {
        return accInput;
    }

}

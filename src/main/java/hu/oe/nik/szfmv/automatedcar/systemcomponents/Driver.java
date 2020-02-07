package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.SamplePacket;

public class Driver extends SystemComponent {

    private final SamplePacket samplePacket;

    public Driver(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        samplePacket = new SamplePacket();
        virtualFunctionBus.samplePacket = samplePacket;
    }


    @Override
    public void loop() {
    }

}

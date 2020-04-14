package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.ToPowerTrainPacket;

/**<p>The powertrain encompasses every component that converts the engine’s power into movement.</p>
<p>This includes the engine, transmission, the driveshaft, differentials, axles; basically anything from the engine through to the rotating wheels.</p>*/
public class PowerTrain extends SystemComponent {

    private ToPowerTrainPacket input;
    private CarPositionPacketData output;

    public PowerTrain(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        input = virtualFunctionBus.toPowerTrainPacket;
        output = new CarPositionPacketData(540,1450, null);
        virtualFunctionBus.carPositionPacket = output;
    }

    @Override public void loop() {
        int x = (int)output.getX();
        int y = (int)output.getY();
        output = new CarPositionPacketData(x,y, null);
        virtualFunctionBus.carPositionPacket = output;
    }

}

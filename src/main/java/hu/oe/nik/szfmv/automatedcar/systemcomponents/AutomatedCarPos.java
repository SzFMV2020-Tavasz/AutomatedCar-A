package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.CarPacket;

import java.awt.*;


/**
 * @author  attilanemeth
 *
 * Date: 2019-04-10
 */

public class AutomatedCarPos extends SystemComponent {
    private Point location;
    private float rotation;

    public AutomatedCarPos(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        location =  new Point(0, 0);
    }

    public void handleLocationChange(Point pos, float rotation) {
        this.location = pos;
        this.rotation = rotation;
    }
    void createPacketAndSend() {
        CarPacket packet = new CarPacket();
        packet.setPositon(this.location);
        packet.setRotation(rotation);
        virtualFunctionBus.carPacket = packet;
    }
    @Override
    public void loop() {
        createPacketAndSend();
    }
}

package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.ReadOnlyInputPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.SteeringPacket;
//import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

/**
 * @author  Robespierre19
 * @author  robert-megyesi-woz
 * @author  Kadi96
 * @author  attilanemeth
 *
 * Date: 2019-04-10
 */

public class Steering extends SystemComponent {
    private final float maxSteeringAngle = 60f;
    private final float steeringRateConst = 0.01f;

    private float steeringRate;
    private int steeringWheel;  // -100/+100
    private float steeringAngle;    // in degrees

    public Steering(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        steeringRate = maxSteeringAngle * steeringRateConst;
    }

    @Override
    public void loop() {
        getValuesFromInputPacket();
        calculateSteeringAngleFromWheelPosition();
        createAndSendPacket();
    }

    private void getValuesFromInputPacket() {
        ReadOnlyInputPacket packet = virtualFunctionBus.inputPacket;

        steeringWheel = packet.getSteeringWheel();
    }

    private void calculateSteeringAngleFromWheelPosition() {
        steeringAngle = steeringWheel * steeringRate;
    }

    private void createAndSendPacket() {
        SteeringPacket packet = new SteeringPacket();
        packet.setSteeringAngle(steeringAngle);
        virtualFunctionBus.steeringPacket = packet;
    }
}

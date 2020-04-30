package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.IVector;

public interface ICarMovePacket {

    /**Gets moving direction and amount of force produced by the {@link hu.oe.nik.szfmv.automatedcar.powertrain.PowerTrain powertrain}.
     * Magnitude is Newtons.*/
    IVector getAccelerationVector();

    default double getAcceleration() {
        return getAccelerationVector().getLength();
    }

    IVector getWheelFacingDirection();
}

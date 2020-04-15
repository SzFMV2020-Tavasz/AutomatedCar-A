package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.IVector;

public interface ICarMovePacket {

    /**Gets moving direction and speed of the car.
     * Magnitude is in km/s.*/
    IVector getMoveVector();
}

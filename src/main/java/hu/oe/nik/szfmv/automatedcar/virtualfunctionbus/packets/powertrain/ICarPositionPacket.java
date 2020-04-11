package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.IVector;

public interface ICarPositionPacket {
    /**Gets the X coordinate of the car.*/
    double getX();

    /**Gets the Y coordinate of the car.*/
    double getY();

    /**Gets moving direction and speed of the car.
     * Magnitude is in km/s.*/
    IVector getMoveVector();
}

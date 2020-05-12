package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

import static hu.oe.nik.szfmv.automatedcar.math.MathUtils.RADIAN_PERIOD;

/**@author Shared code.*/
public interface ICarControlPacket {

    /**From {@code 0.0} to {@code 1.0}.*/
    double getGasPedalRatio();

    /**From {@code 0.0} to {@code 1.0}.*/
    double getBreakPedalRatio();

    /**@return the rotation angle of the steering wheels in radians.*/
    double getSteeringWheelRotation();

    default double getMaxSteeringWheelRotation() {
        return RADIAN_PERIOD / 2;
    }

}

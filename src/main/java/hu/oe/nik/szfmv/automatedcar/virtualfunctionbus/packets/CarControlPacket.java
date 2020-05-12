package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

import hu.oe.nik.szfmv.automatedcar.CarIndexState;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.math.MathUtils;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorWithAngle;
import static java.lang.Math.toRadians;

/**@author Shared code.*/
public interface CarControlPacket {

    /**From 0 to 100.*/
    int getGasPedalValue();

    /**From 0 to 100.*/
    int getBreakPedalValue();

    /**Rotation of steering wheels in degrees ({@code -}{@link MathUtils#DEGREE_PERIOD 180} .. {@code +}{@link MathUtils#DEGREE_PERIOD 180}).*/
    int getSteeringWheelValue();

    default IVector getSteeringWheelRotation() {
        return vectorWithAngle(toRadians(getSteeringWheelValue()));
    }

    /**negatív -> balra, pozitív -> jobbra, 0 -> kikapcsolva*/
    @Deprecated(forRemoval = true /*please use getIndexState() instead in the future*/)
    int getIndexValue();

    default CarIndexState getIndexState() {
        return CarIndexState.fromValue(getIndexValue());
    }

    Shitfer.ShiftPos getShiftValue();

}

package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets;

import hu.oe.nik.szfmv.automatedcar.math.MathUtils;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Index.IndexStatus;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.ICarControlPacket;

import static java.lang.Math.toRadians;

/**@author Shared code.*/
public interface BaseInputPacket extends ICarControlPacket {

    /**From 0 to 100.*/
    int getGasPedalValue();

    @Override
    default double getGasPedalRatio() {
        return getGasPedalValue() / 100.0;
    }

    /**From 0 to 100.*/
    int getBreakPedalValue();

    @Override
    default double getBreakPedalRatio() {
        return getGasPedalValue() / 100.0;
    }

    /**Rotation of steering wheels in degrees ({@code -}{@link MathUtils#DEGREE_PERIOD 180} .. {@code +}{@link MathUtils#DEGREE_PERIOD 180}).*/
    int getSteeringWheelValue();

    default double getSteeringWheelRotation() {
        return toRadians(getSteeringWheelValue());
    }

    /**negatív -> balra, pozitív -> jobbra, 0 -> kikapcsolva*/
    @Deprecated(forRemoval = true /*please use getIndexState() instead in the future*/)
    int getIndexValue();

    default IndexStatus getIndexState() {
        return IndexStatus.fromValue(getIndexValue());
    }

    Shitfer.ShiftPos getShiftValue();

    int getTempomatValue();

    boolean getTempomatSwitch();

    int getDebugSwitch();

    boolean getLaneKeepingAssistantSwitch();

    boolean getParkingPilotSwitch();

    double getTrackingDistanceValue();

    boolean getTrackingDistanceSwitch();

}

package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain;

import hu.oe.nik.szfmv.automatedcar.powertrain.CarTransmissionMode;

public interface IEngineStatusPacket {
    /**Gets mode of the transmission of the car.*/
    CarTransmissionMode getTransmissionMode();

    /**Value from 1 to 6 inclusive, when car is in {@link CarTransmissionMode#D_DRIVE D},
     * {@code null} otherwise.*/
    Integer getForwardLevel();

    /**Gets rotate per minute value of the engine.*/
    double getRPM();
}

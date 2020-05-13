package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets;

import hu.oe.nik.szfmv.automatedcar.powertrain.CarTransmissionMode;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.ICarControlPacket;

public interface IManualCarControlPacket extends ICarControlPacket {

    CarTransmissionMode getShiftChangeRequest();

    int getTempomatValue();

    boolean getTempomatSwitch();

    double getTrackingDistanceValue();

    boolean getTrackingDistanceSwitch();

}

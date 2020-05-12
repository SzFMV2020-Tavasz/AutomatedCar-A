package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.CarControlPacket;

/**@author Shared code.*/
public interface BaseInputPacket extends CarControlPacket {

    int getTempomatValue();

    boolean getTempomatSwitch();

    int getDebugSwitch();

    boolean getLaneKeepingAssistantSwitch();

    boolean getParkingPilotSwitch();

    double getTrackingDistanceValue();

    boolean getTrackingDistanceSwitch();

}

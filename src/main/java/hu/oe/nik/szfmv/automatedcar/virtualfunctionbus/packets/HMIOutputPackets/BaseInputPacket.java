package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

public interface BaseInputPacket {

    int getGasPedalValue();
    int getBreakPedalValue();
    int getSteeringWheelValue();
    int getIndexValue(/*negatív -> balra, pozitív -> jobbra, 0 -> kikapcsolva */);
    Shitfer.ShiftPos getShiftValue();
    int getTempomatValue();
    boolean getTempomatSwitch();
    int getDebugSwitch();
    boolean getLaneKeepingAssistantSwitch();
    boolean getParkingPilotSwitch();
    double getTrackingDistanceValue();
    boolean getTrackingDistanceSwitch();
}

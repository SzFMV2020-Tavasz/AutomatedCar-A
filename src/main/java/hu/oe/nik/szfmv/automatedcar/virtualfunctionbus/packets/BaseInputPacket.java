package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

public interface BaseInputPacket {

    int getGasPedalValue();
    int getBreakPedalValue();
    int getSteeringWheelValue();
    int getIndexValue(/*negatív -> balra, pozitív -> jobbra, 0 -> kikapcsolva */);
    char getShiftValue();
    int getTempomatValue();
    boolean getTempomatSwitch();
    int getDebugSwitch();
    boolean getLaneKeepingAssistantSwitch();
    boolean getParkingPilotSwitch();
    double getTrackingDistanceValue();
    boolean getTrackingDistanceSwitch();
}

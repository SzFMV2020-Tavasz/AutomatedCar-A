package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets;

public interface ReadOnlyToPowerTrainPacket {

    int getGasPedalValue();
    int getBreakPedalValue();
    int getSteeringWheelValue();
    char getShiftChangeRequest();
    int getTempomatValue();
    boolean getTempomatSwitch();
    double getTrackingDistanceValue();
    boolean getTrackingDistanceSwitch();

}

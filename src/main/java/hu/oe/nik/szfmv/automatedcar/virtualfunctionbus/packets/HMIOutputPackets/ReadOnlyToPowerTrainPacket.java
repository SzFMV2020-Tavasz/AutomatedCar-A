package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

public interface ReadOnlyToPowerTrainPacket {

    int getGasPedalValue();
    int getBreakPedalValue();
    int getSteeringWheelValue();
    Shitfer.ShiftPos getShiftChangeRequest();
    int getTempomatValue();
    boolean getTempomatSwitch();
    double getTrackingDistanceValue();
    boolean getTrackingDistanceSwitch();

}

package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

public interface ReadOnlyToPowerTrainPacket {

    double getGasPedalValue();
    double getBreakPedalValue();
    double getSteeringWheelValue();
    Shitfer.ShiftPos getShiftChangeRequest();
    int getTempomatValue();
    boolean getTempomatSwitch();
    double getTrackingDistanceValue();
    boolean getTrackingDistanceSwitch();

}

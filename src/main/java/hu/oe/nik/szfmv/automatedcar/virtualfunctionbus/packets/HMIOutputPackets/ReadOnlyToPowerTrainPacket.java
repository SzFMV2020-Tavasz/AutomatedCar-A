package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.shitfer;

public interface ReadOnlyToPowerTrainPacket {

    double getGasPedalValue();
    double getBreakPedalValue();
    double getSteeringWheelValue();
    shitfer.ShiftPos getShiftChangeRequest();
    int getTempomatValue();
    boolean getTempomatSwitch();
    double getTrackingDistanceValue();
    boolean getTrackingDistanceSwitch();

}

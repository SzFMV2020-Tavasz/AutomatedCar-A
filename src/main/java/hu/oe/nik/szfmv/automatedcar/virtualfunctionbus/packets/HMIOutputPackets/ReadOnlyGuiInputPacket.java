package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Index;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

public interface GuiInputPacket {
    Shitfer.ShiftPos getShifterPos();
    Index.IndexStatus getIndexStatus();
    double getGasPedalStatus();
    double getBreakPedalStatus();
    double getSteeringWheelStatus();
    boolean getACCStatus();
    int getTempomatValue();
    double getFollowingDistancValue();
}

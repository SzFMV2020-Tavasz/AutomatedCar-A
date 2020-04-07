package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Index;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.shitfer;

public interface ReadOnlyGuiInputPacket {
    shitfer.ShiftPos getShifterPos();
    Index.IndexStatus getIndexStatus();
    double getGasPedalValue();
    double getBreakPedalValue();
    double getSteeringWheelValue();
    boolean getACCStatus();
    int getAccSpeedValue();
    double getAccFollowingDistanceValue();
    boolean getParkingPilotStatus();
    boolean getLaneKeepingAssistant();
    boolean getDebugSwitch();
    boolean getHelpMenuSwitch();
}

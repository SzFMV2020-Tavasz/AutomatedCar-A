package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.HMIOutputPackets;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Index;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

public class GuiInputPacket implements ReadOnlyGuiInputPacket {

    private Index.IndexStatus indexStatus;

    public void setIndexStatus(Index.IndexStatus indexStatus) {
        this.indexStatus = indexStatus;
    }

    @Override
    public Index.IndexStatus getIndexStatus() {
        return indexStatus;
    }

    private double gasPedalValue;//0-100
    public void setGasPedalValue(double gasPedalValue){
        this.gasPedalValue=gasPedalValue;
    }
    @Override
    public double getGasPedalValue() {
        return gasPedalValue;
    }


    private double breakPedalValue;//0-100
    public void setBreakPedalValue(double breakPedalValue){
        this.breakPedalValue=breakPedalValue;
    }
    @Override
    public double getBreakPedalValue() {
        return breakPedalValue;
    }


    private double steeringWheelValue;//not determined yet, probably -180-180
    public void setSteeringWheelValue(double steeringWheelValue){
        this.steeringWheelValue=steeringWheelValue;
    }
    @Override
    public double getSteeringWheelValue() {
        return steeringWheelValue;
    }


    private Shitfer.ShiftPos shiftChangeRequest;//P-R-N-D
    public void setShifterPos(Shitfer.ShiftPos shiftChangeRequest) {
        this.shiftChangeRequest = shiftChangeRequest;
    }

    @Override
    public Shitfer.ShiftPos getShifterPos() {
        return shiftChangeRequest;
    }

    @Override
    public boolean getACCStatus() {
        return false;
    }

    @Override
    public int getTempomatValue() {
        return 0;
    }

    @Override
    public double getFollowingDistanceValue() {
        return 0;
    }
}

package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.sample;

public class SamplePacket implements ReadOnlySamplePacket {
    private int gaspedalPosition = 0;
    private int breakpedalPosition = 0;
    private int wheelPosition = 0;
    private String gear = "";

    /**
     * Create a Sample Packet
     */
    public SamplePacket() {
    }

    public int getBreakpedalPosition() {
        return breakpedalPosition;
    }

    public void setBreakpedalPosition(int breakpedalPosition) {
        this.breakpedalPosition = breakpedalPosition;
    }

    public int getWheelPosition() {
        return wheelPosition;
    }

    public void setWheelPosition(int wheelPosition) {
        this.wheelPosition = wheelPosition;
    }

    public String getGear() {
        return gear;
    }

    public void setGear(String gear) {
        this.gear = gear;
    }

    @Override
    public int getGaspedalPosition() {
        return gaspedalPosition;
    }

    public void setGaspedalPosition(int gaspedalPosition) {
        this.gaspedalPosition = gaspedalPosition;
    }


}

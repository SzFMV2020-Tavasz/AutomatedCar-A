package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

public class SamplePacket implements ReadOnlySamplePacket {
    private int key = 0;

    /**
     * Create a Sample Packet
     */
    public SamplePacket() {
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

}

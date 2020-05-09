package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

public interface IParkingRadarDisplayStatePacket {
    /**
     * Gets the display state of the radar sensor triangles
     * @return true if camera is shown.
     */
    public boolean getRadarDisplayState();
}

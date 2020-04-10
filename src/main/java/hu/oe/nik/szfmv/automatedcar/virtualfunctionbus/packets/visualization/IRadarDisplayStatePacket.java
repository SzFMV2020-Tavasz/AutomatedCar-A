package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

public interface IRadarDisplayStatePacket {
    /**
     * Gets the display state of the radar sensor triangle
     * @return true if camera is shown.
     */
    public boolean getRadarDisplayState();
}

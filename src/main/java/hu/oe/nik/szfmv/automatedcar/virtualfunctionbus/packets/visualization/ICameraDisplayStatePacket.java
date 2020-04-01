package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

public interface ICameraDisplayStatePacket {

    /**
     * Gets the display state of the camera sensor triangle
     * @return true if camera is shown.
     */
    public boolean getCameraDisplayState();
}

package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

public interface IUltrasoundDisplayStatePacket {
    /**
     * Gets the display state of the ultrasound sensor triangle
     * @return true if camera is shown.
     */
    public boolean getUltrasoundDisplayState();
}

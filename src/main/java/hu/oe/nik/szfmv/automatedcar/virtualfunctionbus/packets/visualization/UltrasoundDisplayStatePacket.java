package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

public class UltrasoundDisplayStatePacket implements IUltrasoundDisplayStatePacket {
    boolean showSensor;

    public UltrasoundDisplayStatePacket() {
        showSensor = false;
    }

    /**
     * Sets the display state of the ultrasound sensor triangle
     * @param showSensor true if triangle should be shown
     */
    public void setUltrasoundDisplayState(boolean showSensor) {
        this.showSensor = showSensor;
    }

    /**
     * Gets the display state of the ultrasound sensor triangle
     * @return true if camera is shown.
     */
    public boolean getUltrasoundDisplayState() {
        return showSensor;
    }
}

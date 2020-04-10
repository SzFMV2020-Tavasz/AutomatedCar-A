package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

public class RadarDisplayStatePacket implements IRadarDisplayStatePacket {
    boolean showSensor;

    public RadarDisplayStatePacket() {
        showSensor = false;
    }

    /**
     * Sets the display state of the radar sensor triangle
     * @param showSensor true if triangle should be shown
     */
    public void setRadarDisplayState(boolean showSensor) {
        this.showSensor = showSensor;
    }

    /**
     * Gets the display state of the radar sensor triangle
     * @return true if camera is shown.
     */
    public boolean getRadarDisplayState() {
        return showSensor;
    }
}

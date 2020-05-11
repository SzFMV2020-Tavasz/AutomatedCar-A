package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

public class ParkingRadarDisplayStatePacket implements IParkingRadarDisplayStatePacket {
    boolean showSensor;

    public ParkingRadarDisplayStatePacket() {
        showSensor = false;
    }

    /**
     * Sets the display state of the radar sensor triangles
     * @param showSensor true if triangle should be shown
     */
    public void setRadarDisplayState(boolean showSensor) {
        this.showSensor = showSensor;
    }

    /**
     * Gets the display state of the radar sensor triangles
     * @return true if camera is shown.
     */
    public boolean getRadarDisplayState() {
        return showSensor;
    }
}

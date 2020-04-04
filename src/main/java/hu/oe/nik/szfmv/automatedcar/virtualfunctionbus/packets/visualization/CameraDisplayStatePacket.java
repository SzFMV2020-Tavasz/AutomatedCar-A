package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

public class CameraDisplayStatePacket implements ICameraDisplayStatePacket {
    boolean showSensor;

    public CameraDisplayStatePacket() {
        showSensor = false;
    }

    /**
     * Sets the display state of the camera sensor triangle
     * @param showSensor true if triangle should be shown
     */
    public void setCameraDisplayState(boolean showSensor) {
        this.showSensor = showSensor;
    }

    /**
     * Gets the display state of the camera sensor triangle
     * @return true if camera is shown.
     */
    public boolean getCameraDisplayState() {
        return showSensor;
    }
}

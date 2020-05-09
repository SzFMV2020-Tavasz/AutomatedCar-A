package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

public class ParkingRadarGuiStatePacket implements IParkingRadarGuiStatePacket {

    private boolean state;

    public ParkingRadarGuiStatePacket() {
        state = false;
    }

    /**
     * Gets the display state of of the parking radar gui
     *
     * @return true if gui is on.
     */
    public boolean getParkingRadarGuiState() {
        return state;
    }

    /**
     * Sets the display state of of the parking radar gui
     *
     * @param state true if gui is on.
     */
    public void setDebuggingState(boolean state) {
        this.state = state;
    }
}

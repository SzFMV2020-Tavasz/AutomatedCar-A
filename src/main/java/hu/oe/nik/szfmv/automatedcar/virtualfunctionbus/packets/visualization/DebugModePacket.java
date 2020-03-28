package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

public class DebugModePacket implements IDebugModePacket {
    private boolean debuggingModeState = false;

    /**
     * Debugging setting goes here
     */
    public DebugModePacket() {
    }

    /**
     * Gets the Debug state
     * @return true if debug polygons are on
     */
    public boolean getDebuggingState() {
        return debuggingModeState;
    }

    /**
     * Sets the Debug state
     * @param state true if the debug polygons are on
     */
    public void setDebuggingState(boolean state) {
        debuggingModeState = state;
    }
}

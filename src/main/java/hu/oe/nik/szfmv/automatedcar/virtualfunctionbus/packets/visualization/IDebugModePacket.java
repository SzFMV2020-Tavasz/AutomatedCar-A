package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.visualization;

public interface IDebugModePacket {
    /**
     * Gets the Debug state
     * @return true if debug polygons are on
     */
    boolean getDebuggingState();
}

package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.Debugging;

import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.ReadOnlySamplePacket;

public class DebugMode implements IDebugMode {
    private boolean debuggingModeStatus = false;

    /**
     * Debugging setting goes here
     */
    public DebugMode() {
    }


    @Override
    public boolean getDebuggingModeStatus() {
        return debuggingModeStatus;
    }

    @Override
    public void setDebuggingMode(boolean status) {
        debuggingModeStatus = status;
    }
}
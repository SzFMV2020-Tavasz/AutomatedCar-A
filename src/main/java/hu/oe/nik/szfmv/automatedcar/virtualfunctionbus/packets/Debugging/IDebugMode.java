package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.Debugging;

public interface IDebugMode {
    boolean getDebuggingModeStatus();
    void setDebuggingMode(boolean status);
}

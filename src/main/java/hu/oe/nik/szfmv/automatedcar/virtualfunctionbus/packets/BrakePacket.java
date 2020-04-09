package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

/**
 * @author  BazsoGabor
 *
 * Date: 2019-04-18
 */

public class BrakePacket implements ReadOnlyBrakePacket {

    boolean brake;
    boolean warning;

    public BrakePacket() {

    }

    public boolean isBrake() {
        return brake;
    }

    public void setBrake(boolean brake) {
        this.brake = brake;
    }

    public boolean isWarning() {
        return warning;
    }

    public void setWarning(boolean warning) {
        this.warning = warning;
    }

}

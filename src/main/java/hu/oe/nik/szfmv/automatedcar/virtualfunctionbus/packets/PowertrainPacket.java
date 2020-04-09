package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets;

/**
 * @author  Robespierre19
 * @author  dradequate
 *
 * Date: 2019-03-26
 */

public class PowertrainPacket implements ReadOnlyPowertrainPacket {

    private int rpm;
    private float speed;
    private int actualAutoGear;

    public PowertrainPacket() {
    }

    public PowertrainPacket(int rpm, float speed, int autoGear) {
        this.rpm = rpm;
        this.speed = speed;
        this.actualAutoGear = autoGear;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public int getRPM() {
        return this.rpm;
    }

    @Override
    public int getActualAutoGear() {
        return this.actualAutoGear;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setActualAutoGear(int actualAutoGear) {
        this.actualAutoGear = actualAutoGear;
    }
}

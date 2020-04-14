package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain;

public class PowertrainPacket implements ReadOnlyPowertrainPacket {

    public static final int MIN_SPEEDLIMIT = 10;
    private int rmp;
    private double speed;
    private double speedLimit;
    private Boolean isSpeedLimited;

    /**
     * PowertrainPacket consturctor
     */
    public PowertrainPacket() {
        this.rmp = 0;
        this.speed = 0d;
        this.speedLimit = 0d;
        this.isSpeedLimited = false;
    }

    @Override
    public int getRpm() {
        return this.rmp;
    }

    @Override
    public void setRpm(int rpm) {
        this.rmp = rpm;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    @Override
    public void unlockSpeedLimit() {
        System.out.println("Speed limitation unlocked");
        this.isSpeedLimited = false;
        this.speedLimit = 0;
    }

    @Override
    public Boolean isSpeedLimited() {
        return this.isSpeedLimited;
    }

    @Override
    public double getSpeedLimit() {
        return this.speedLimit;
    }

    @Override
    public void setSpeedLimit(double speedLimit) {
        System.out.println("Speed limited: " + speedLimit);
        this.isSpeedLimited = true;
        if (speedLimit < MIN_SPEEDLIMIT) {
            this.speedLimit = MIN_SPEEDLIMIT;
        } else {
            this.speedLimit = speedLimit;
        }
    }
}

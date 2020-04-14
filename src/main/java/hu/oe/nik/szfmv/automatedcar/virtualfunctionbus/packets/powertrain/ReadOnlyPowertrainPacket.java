package hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain;

public interface ReadOnlyPowertrainPacket {

    /**
     * Gets the engine revolution (revolution/minute)
     *
     * @return int (rpm)
     */
    int getRpm();

    /**
     * Sets the value of the rpm of the engine
     *
     * @param rpm of the engine
     */
    void setRpm(int rpm);

    /**
     * Gets the car actual speed
     * If it is a positive number, the car moves forward
     * If it is a negative number, the car moves backward
     *
     * @return dobule
     */
    double getSpeed();

    /**
     * Sets the value of the speed of the car
     *
     * @param speed the speed of the car
     */
    void setSpeed(double speed);

    /**
     * Removes speed limitation.
     */
    void unlockSpeedLimit();

    /**
     * Determines whether there is a speed limitation.
     *
     * @return True if there is a speed limitation.
     */
    Boolean isSpeedLimited();

    /**
     * Returns the speed limit.
     *
     * @return Speed limit.
     */
    double getSpeedLimit();

    /**
     * Sets a speed limit to the car. If the speed is lower than the current speed
     * then the car will brake until reaches the speed limit.
     */
    void setSpeedLimit(double speedLimit);
}

package hu.oe.nik.szfmv.automatedcar.powertrain;

/***
 * @author Team 3 (Magyar Dávid | aether-fox | davidson996@gmail.com)
 */
public interface ITransmission {

    /**
     * Gets the actual gear mode.
     * @return The actual value of the gearMode field.
     */
    CarTransmissionMode getCurrentTransmissionMode();

    /**Gets the current transmission level, 0 for non-leveled modes,
     * 1-2-3-4-5 for {@link CarTransmissionMode#D_DRIVE D}.*/
    int getCurrentTransmissionLevel();

    /**Should be called once per loop tick.
     * @param gasPedalPressRatio Expected value is between {@code 0.0} an {@code 1.0}.*/
    void update(double gasPedalPressRatio);

    void forceShift(CarTransmissionMode mode, int transmissionLevel);

    /**Gets the current rotate-per-minute value of the engine.*/
    long getCurrentRPM();

    /**Creates a packet of data containing the current information about the engine status.*/
    EngineStatusPacketData provideInfo();

    /**Converts RPM value to raw force (like Newton) in the given transmission mode and level.*/
    double rpmToForce(long rpm, CarTransmissionMode mode, int level);

    default double rpmToForce(long rpm) {
        return rpmToForce(rpm, getCurrentTransmissionMode(), getCurrentTransmissionLevel());
    }
}

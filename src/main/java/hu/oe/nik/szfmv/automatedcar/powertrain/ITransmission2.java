package hu.oe.nik.szfmv.automatedcar.powertrain;

/***
 * @author Team 3 (Magyar Dávid | aether-fox | davidson996@gmail.com)
 */
public interface ITransmission2 {

    /**
     * Gets the actual gear mode.
     * @return The actual value of the gearMode field.
     */
    CarTransmissionMode getCurrentTransmissionMode();

    default int getCurrentTransmissionLevel() {
        return getCurrentTransmissionMode() == CarTransmissionMode.D_DRIVE ? 1 : 0; //TODO
    }

    /**Expected value is between {@code 0.0} an {@code 1.0}.*/
    void update(double gasPedalPressRatio, CarTransmissionMode requestedMode, int requestedTransmissionLevel);

    long getCurrentRPM();

    EngineStatusPacketData provideInfo();

    double rpmToForce(long rpm, CarTransmissionMode mode, int level);

    default double rpmToForce(long rpm) {
        return rpmToForce(rpm, getCurrentTransmissionMode(), getCurrentTransmissionLevel());
    }
}

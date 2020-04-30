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

    /**Expected value is between {@code 0.0} an {@code 1.0}.*/
    void update(double gasPedalPressRatio, CarTransmissionMode requestedMode, int requestedTransmissionLevel);

    long getCurrentRPM();

    EngineStatusPacketData provideInfo();
}

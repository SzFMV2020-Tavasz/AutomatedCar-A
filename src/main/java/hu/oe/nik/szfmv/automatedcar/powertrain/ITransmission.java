package hu.oe.nik.szfmv.automatedcar.powertrain;

/**
 * ITransmission interface
 * Describes a transmission class.
 */
public interface ITransmission {

    /**
     * Getter method for gearMode field.
     * @return The actual value of the gearMode field.
     */
    CarTransmissionMode getGearMode();

    /**
     * Requests the transmission to shift to a desired gear.
     * Keep in mind, the transmission will not change speed, if you ask for impossible things.
     * (Like going reverse from drive, when the car has forward speed for example.)
     * @param gear The gear you want to switch into.
     * @return Returns true if the shifting was successful, false if it was unsuccessful.
     */
    boolean Shift(CarTransmissionMode gear);

    /**
     * Calculates the actual state of the transmission when it is called.
     * (I strongly recommend, to call it in the SystemComponent's loop method.)
     */
    void Loop(int RPM);
}

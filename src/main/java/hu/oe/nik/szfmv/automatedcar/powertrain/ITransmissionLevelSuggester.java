package hu.oe.nik.szfmv.automatedcar.powertrain;

/**A component, which receives RPM and transmission level information and suggests transmission level.
 * Designed to be used for transmission {@link CarTransmissionMode#D_DRIVE mode D}.*/
public interface ITransmissionLevelSuggester {

    void update(long rpm, int level);

    int getSuggestedLevel();

}

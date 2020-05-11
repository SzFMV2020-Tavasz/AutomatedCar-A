package hu.oe.nik.szfmv.automatedcar.visualization;

/**
 * enumeration with values for selecting a parking radar sensor
 */
public enum ParkingRadarPositions {
    RIGHT(0),
    LEFT(1);

    private int numval;

    ParkingRadarPositions(int numVal) {
        this.numval = numVal;
    }

    /**
     * Gets the corresponding number for the enum string
     * @return the value of the enum
     */
    public int getNumVal() {
        return numval;
    }
}

package hu.oe.nik.szfmv.automatedcar.visualization;

/**
 * enumeration with values for selecting an ultrasound sensor
 */
public enum UltrasoundPositions {
    FRONT_RIGHT(0),
    FRONT_LEFT(1),
    FRONT_LEFT_SIDE(2),
    REAR_LEFT_SIDE(3),
    REAR_LEFT(4),
    REAR_RIGHT(5),
    REAR_RIGHT_SIDE(6),
    FRONT_RIGHT_SIDE(7);

    private int numval;

    UltrasoundPositions(int numVal) {
        this.numval = numVal;
    }

    /**
     * Gets the corresponding number for the enum string
     *
     * @return the value of the enum
     */
    public int getNumVal() {
        return numval;
    }
}


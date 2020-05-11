package hu.oe.nik.szfmv.automatedcar;

/**Indicates whether the car is indexing and in what direction.
 * @author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public enum CarIndexState {

    /**Index is turned on towards left.*/
    LEFT,

    /**Index is turned on towards right.*/
    RIGHT,

    /**Index is turned off.*/
    OFF;

    @Deprecated(forRemoval = true)
    public int asValue() {
        switch (this) {
            case LEFT: return -1;
            case OFF: return 0;
            case RIGHT: return 1;
            default: throw new IllegalStateException();
        }
    }

    public static CarIndexState fromValue(int value) {
        if (value < 0) {
            return LEFT;
        } else if (value > 0) {
            return RIGHT;
        } else {
            return OFF;
        }
    }
}

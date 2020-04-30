package hu.oe.nik.szfmv.automatedcar;

/**Enumeration of car variants. Should be used instead of direct resource addressing of car variant resources too.
 * @author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public enum CarVariant {
    TYPE_1_WHITE,
    TYPE_2_WHITE,
    TYPE_2_RED;

    /**Returns the name of the image resource associated with the given car variant.*/
    public String getImageResourceName() {
        switch (this) {
            case TYPE_1_WHITE:
                return "car_1_white.png";
            case TYPE_2_WHITE:
                return "car_2_white.png";
            case TYPE_2_RED:
                return "car_2_red.png";
            default:
                throw new IllegalStateException("Unexpected variant of car: " + this);
        }
    }
}

package hu.oe.nik.szfmv.automatedcar;

/**Enumeration of car variants. Should be used instead of direct resource addressing of car variant resources too.
 * @author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public enum CarVariant {
    WHITE_1,
    WHITE_2,
    RED_2;

    /**Returns the name of the image resource associated with the given car variant.*/
    public String getImageResourceName() {
        switch (this) {
            case WHITE_1:
                return "car_1_white.png";
            case WHITE_2:
                return "car_2_white.png";
            case RED_2:
                return "car_2_red.png";
            default:
                throw new IllegalStateException("Unexpected variant of car: " + this);
        }
    }
}

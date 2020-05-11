package hu.oe.nik.szfmv.automatedcar;

/**Enumeration of car variants. Should be used instead of direct resource addressing of car variant resources too.
 * @author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)*/
public enum CarVariant {
    TYPE_1_BLUE,
    TYPE_1_RED,
    TYPE_1_WHITE,
    TYPE_2_BLUE,
    TYPE_2_RED,
    TYPE_2_WHITE,
    @Deprecated(/*no polygon defined for type 3 cars yet*/)TYPE_3_BLACK;

    CarVariant() {
        this.resourceFileName = "car_" + this.name().substring(5).toLowerCase()+ ".png";
    }

    CarVariant(String resourceFileName) {
        this.resourceFileName = resourceFileName;
    }

    private final String resourceFileName;

    /**Returns the name of the image resource associated with the given car variant.*/
    public String getImageResourceName() {
        return this.resourceFileName;
    }
}
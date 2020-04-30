package hu.oe.nik.szfmv.automatedcar;

public enum CarVariant {
    WHITE_1,
    WHITE_2,
    RED_2;

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

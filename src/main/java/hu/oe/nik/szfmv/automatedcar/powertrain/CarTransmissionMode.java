package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

/**@author Team 3*/
public enum CarTransmissionMode {

    /**Tolató állás.*/
    R_REVERSE,

    /**Üres állás.*/
    N_NEUTRAL,

    /**Parkoló mód.*/
    P_PARKING,

    /**Autómata állás.*/
    D_DRIVE(1,5);

    private final int minLevel;
    private final int maxLevel;

    CarTransmissionMode() {
        minLevel = 0;
        maxLevel = 0;
    }

    CarTransmissionMode(int minLevel, int maxLevel) {
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
    }

    public static CarTransmissionMode fromShiftPos(Shitfer.ShiftPos pos) {
        switch (pos) {
            case D:
                return CarTransmissionMode.D_DRIVE;
            case N:
                return CarTransmissionMode.N_NEUTRAL;
            case R:
                return CarTransmissionMode.R_REVERSE;
            default:
                return CarTransmissionMode.P_PARKING;
        }
    }

    public int getMinimumLevel() {
        return this.minLevel;
    }

    public int getMaximumLevel() {
        return this.maxLevel;
    }

    public boolean supportsLevel(int level) {
        return level >= getMinimumLevel() && level <= getMaximumLevel();
    }

    public char letter() {
        return name().charAt(0);
    }

}

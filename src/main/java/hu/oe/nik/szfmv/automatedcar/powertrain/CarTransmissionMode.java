package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;

public enum CarTransmissionMode {

    /**Tolató állás.*/
    R_REVERSE,

    /**Üres állás.*/
    N_NEUTRAL,

    /**Parkoló mód.*/
    P_PARKING,

    /**Autómata állás.*/
    D_DRIVE;

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

    public boolean supportsLevel(int level) {
        if (this == D_DRIVE) {
            return level > 0 && level <= 5;
        } else {
            return level == 0;
        }
    }

}

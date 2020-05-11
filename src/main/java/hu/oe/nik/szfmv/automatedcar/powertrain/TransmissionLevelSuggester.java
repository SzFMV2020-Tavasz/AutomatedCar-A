package hu.oe.nik.szfmv.automatedcar.powertrain;

import static hu.oe.nik.szfmv.automatedcar.math.MathUtils.mean;
import static hu.oe.nik.szfmv.automatedcar.powertrain.CarTransmissionMode.D_DRIVE;
import static java.lang.Float.NEGATIVE_INFINITY;
import static java.lang.Float.POSITIVE_INFINITY;

public class TransmissionLevelSuggester implements ITransmissionLevelSuggester {

    /**
     * <strong>IMPORTANT!!!</strong>
     *
     * <p>These constant values define the gear shifting thresholds in {@link CarTransmissionMode#D_DRIVE D mode}.
     * Changing these values can effect the workings of the car extremely.</p>
     * <p>Tweak carefully.</p>
     * TODO: calibrate !!!
     */
    private static final RPMThresholdInfo[] THRESHOLDS = {
            /*1*/ new RPMThresholdInfo(NEGATIVE_INFINITY, NEGATIVE_INFINITY, 1500, 4800),
            /*2*/ new RPMThresholdInfo(1000, 1400, 2000, 4800),
            /*3*/ new RPMThresholdInfo(1000, 1400, 2000, 4800),
            /*4*/ new RPMThresholdInfo(1000, 1400, 2000, 4800),
            /*5*/ new RPMThresholdInfo(1000, 1400, POSITIVE_INFINITY, POSITIVE_INFINITY)
    };

    private int suggestedLevel;

    @Override
    public void update(long rpm, int level) {
        RPMThresholdInfo levelInfo = getThresholds(level);

        if (rpm < levelInfo.allowedDownShift) {
            if (rpm < levelInfo.criticalDownShift) {
                suggestedLevel = level - 1;
                return;
            }

            if (rpm < mean(levelInfo.criticalDownShift, levelInfo.allowedDownShift)) {
                suggestedLevel = level - 1;
                return;
            }
        } else if (rpm > levelInfo.allowedUpShift) {
            if (rpm > levelInfo.criticalUpShift) {
                suggestedLevel = level + 1;
                return;
            }

            if (rpm > mean(levelInfo.criticalUpShift, levelInfo.allowedUpShift)) {
                suggestedLevel = level + 1;
                return;
            }
        }

        suggestedLevel = level;
    }

    @Override
    public int getSuggestedLevel() {
        return suggestedLevel;
    }

    private RPMThresholdInfo getThresholds(int level) {
        if (level < 1 || level > 5) {
            if (D_DRIVE.supportsLevel(level)) {
                throw new IllegalArgumentException("Missing implementation for transmission level: " + level);
            } else {
                throw new IllegalArgumentException("Invalid transmission level: " + level);
            }
        }

        return THRESHOLDS[level - 1];
    }

    private static class RPMThresholdInfo {
        private final float criticalUpShift;
        private final float allowedUpShift;
        private final float criticalDownShift;
        private final float allowedDownShift;

        public RPMThresholdInfo(float criticalDownShift, float allowedDownShift, float allowedUpShift, float criticalUpShift) {
            this.criticalUpShift = criticalUpShift;
            this.allowedUpShift = allowedUpShift;
            this.criticalDownShift = criticalDownShift;
            this.allowedDownShift = allowedDownShift;
        }
    }

}

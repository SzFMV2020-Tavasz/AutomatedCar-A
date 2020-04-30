package hu.oe.nik.szfmv.automatedcar.powertrain;

import static java.lang.System.currentTimeMillis;

/***
 * @author Team 3 (Magyar Dávid | aether-fox | davidson996@gmail.com)
 */
public class SimpleTransmission implements ITransmission2 {

    private static final long MAX_RPM = 5000;

    private static final double RATIO_2_TO_1 = 1.5;
    private static final double RATIO_3_TO_2 = 1.5;
    private static final double RATIO_4_TO_3 = 1.5;
    private static final double RATIO_5_TO_4 = 1.5;

    private static final double RATIO_3_TO_1 = RATIO_3_TO_2 * RATIO_2_TO_1;
    private static final double RATIO_4_TO_1 = RATIO_4_TO_3 * RATIO_3_TO_2 * RATIO_2_TO_1;
    private static final double RATIO_5_TO_1 = RATIO_5_TO_4 * RATIO_4_TO_3 * RATIO_3_TO_2 * RATIO_2_TO_1;

    private CarTransmissionMode currentTransmissionMode = CarTransmissionMode.N_NEUTRAL;
    private int currentTransmissionLevel = 0;

    private long currentRPM = 1000;

    private long lastUpdateTimeStamp;

    public SimpleTransmission() {
        lastUpdateTimeStamp = currentTimeMillis();
    }

    /**
     * Gets the current transmission mode.
     * @return The actual value of the gearMode field.
     */
    @Override
    public CarTransmissionMode getCurrentTransmissionMode() {
        return currentTransmissionMode;
    }

    @Override
    public long getCurrentRPM() {
        return currentRPM;
    }

    /**
     * @param gasPedalPressRatio Expected value is between {@code 0.0} an {@code 1.0}.
     * @param requestedMode To change transmission mode to.
     * @param requestedTransmissionLevel To change transmission level to.
     */
    @Override
    public void update(double gasPedalPressRatio, CarTransmissionMode requestedMode, int requestedTransmissionLevel) {
        long elapsedMillis = getMillisSinceLastUpdateAndReset();
        handleShifting(requestedMode, requestedTransmissionLevel);

        long currentRPMPerSec = getRPMPerSecond(currentTransmissionMode, currentTransmissionLevel);
        double elapsedSeconds = (elapsedMillis / 1000.0);
        double hasPedalEffect = (gasPedalPressRatio * 1.25 - 0.25);

        long newTargetRPM = (long)(elapsedSeconds * currentRPMPerSec * hasPedalEffect);
        this.currentRPM = Math.min(newTargetRPM, MAX_RPM);
    }

    private long getMillisSinceLastUpdateAndReset() {
        long now = currentTimeMillis();
        long elapsedTime = getTimeSinceLastUpdate(now);
        lastUpdateTimeStamp = now;
        return elapsedTime;
    }

    private long getTimeSinceLastUpdate(long givenTime) {
        return givenTime - lastUpdateTimeStamp;
    }

    private long getRPMPerSecond(CarTransmissionMode mode, int level) {
        switch (mode) {
            case P_PARKING:
            case N_NEUTRAL:
                return 0;
            case D_DRIVE:
                switch (level) {
                    case 1:
                        return 1000;
                    case 2:
                        return (long)(1000 * RATIO_2_TO_1);
                    case 3:
                        return (long)(1000 * RATIO_3_TO_1);
                    case 4:
                        return (long)(1000 * RATIO_4_TO_1);
                    case 5:
                        return (long)(1000 * RATIO_5_TO_1);
                    default:
                        if (mode.supportsLevel(level)) {
                            throw new IllegalStateException("Missing implementation of level "
                                    + level + " for transmission mode "
                                    + mode + "!");
                        } else {
                            throw new UnsupportedOperationException("Transmission mode "
                                    + mode + " does not support transmission level "
                                    + level + "!");
                        }
                }
            case R_REVERSE:
                return getRPMPerSecond(CarTransmissionMode.D_DRIVE, 1);
            default:
                throw new IllegalStateException("Missing implementation of transmission mode " + mode + "!");
        }
    }

    private void handleShifting(CarTransmissionMode requestedMode, int requestedTransmissionLevel) {
        if (requestedMode == this.currentTransmissionMode) {
            shiftToLevel(requestedTransmissionLevel);
        } else {
            if (!requestedMode.supportsLevel(requestedTransmissionLevel)) {
                throw new UnsupportedOperationException("Requested transmission mode does not support transmission level " + requestedTransmissionLevel + "!");
            }

            this.currentTransmissionMode = requestedMode;
            this.currentTransmissionLevel = requestedTransmissionLevel;
        }
    }

    private void shiftToLevel(int requestedTransmissionLevel) {
        int levelDiff = requestedTransmissionLevel - currentTransmissionLevel;

        if (levelDiff > 0) {
            if (requestedTransmissionLevel > 5) {
                throw new UnsupportedOperationException("Max transmission level is 5.");
            }

            for (;levelDiff > 0; --levelDiff) {
                currentRPM /= 1.5;
            }
        } else if (levelDiff < 0) {
            if (requestedTransmissionLevel < 0) {
                throw new UnsupportedOperationException("Min transmission level is 1.");
            }

            for (;levelDiff < 0; ++levelDiff) {
                currentRPM *= 1.5;
            }
        }
    }

    @Override
    public EngineStatusPacketData provideInfo() {
        return new EngineStatusPacketData(currentRPM, currentTransmissionLevel, currentTransmissionMode);
    }
}

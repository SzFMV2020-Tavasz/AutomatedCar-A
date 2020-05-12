package hu.oe.nik.szfmv.automatedcar.powertrain;

import static hu.oe.nik.szfmv.automatedcar.powertrain.CarTransmissionMode.D_DRIVE;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;

/***
 * @author Team 3 (Magyar Dávid | aether-fox | davidson996@gmail.com)
 */
public class SimpleTransmission implements ITransmission {

    private static final long MAX_RPM = 5000;
    private static final double RATIO_2_TO_1 = 1.5;
    private static final double RATIO_3_TO_2 = 1.5;
    private static final double RATIO_4_TO_3 = 1.5;
    private static final double RATIO_5_TO_4 = 1.5;
    private static final double RATIO_3_TO_1 = RATIO_3_TO_2 * RATIO_2_TO_1;
    private static final double RATIO_4_TO_1 = RATIO_4_TO_3 * RATIO_3_TO_2 * RATIO_2_TO_1;
    private static final double RATIO_5_TO_1 = RATIO_5_TO_4 * RATIO_4_TO_3 * RATIO_3_TO_2 * RATIO_2_TO_1;

    /**So the motor automatically slows down when the gas is not pressed.*/
    private static final double MOTOR_BREAK_RATIO = 0.7;
    private static final int BASE_RPM_PER_SEC = 1500;

    private final ITransmissionLevelSuggester levelHandler = new TransmissionLevelSuggester();

    private CarTransmissionMode currentTransmissionMode = CarTransmissionMode.P_PARKING;
    private int currentTransmissionLevel = 0;
    private long currentRPM = 0;
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

    /***
     * @param requestedMode To change transmission mode to.
     * @param requestedTransmissionLevel To change transmission level to.
     */
    @Override
    public void forceShift(CarTransmissionMode requestedMode, int requestedTransmissionLevel) {
        if (!requestedMode.supportsLevel(requestedTransmissionLevel)) {
            throw new IllegalArgumentException("Transmission mode "
                    + requestedMode + " does not support transmission level: "
                    + requestedTransmissionLevel);
        }

        handleTransmissionRequest(requestedMode, requestedTransmissionLevel);
    }

    /**
     * @param gasPedalPressRatio Expected value is between {@code 0.0} an {@code 1.0}.
     */
    @Override
    public void update(double gasPedalPressRatio) {
        long elapsedMillis = getMillisSinceLastUpdateAndReset();

        updateRPM(gasPedalPressRatio, elapsedMillis);

        if (this.currentTransmissionMode.supportsLevel(1)) {
            handleAutoLeveling();
        }
    }

    private void handleTransmissionRequest(CarTransmissionMode requestedMode, int requestedTransmissionLevel) {
        if (requestedMode != null) {
            if (!requestedMode.supportsLevel(requestedTransmissionLevel)) {
                throw new IllegalArgumentException("Transmission level "
                        + requestedMode + " is requested with invalid level: "
                        + requestedTransmissionLevel);
            }

            handleShifting(requestedMode, requestedTransmissionLevel);
        }
    }

    private void updateRPM(double gasPedalPressRatio, long elapsedMillis) {
        long currentRPMPerSec = getRPMPerSecond(currentTransmissionMode, currentTransmissionLevel); //power to increase RPM
        double elapsedSeconds = (elapsedMillis / 1000.0);
        double gasPedalEffect = gasPedalPressRatio == 0 ? -MOTOR_BREAK_RATIO : gasPedalPressRatio;

        long newTargetRPM = this.currentRPM + (long)(elapsedSeconds * currentRPMPerSec * gasPedalEffect);
        this.currentRPM = max(0, min(newTargetRPM, MAX_RPM));
    }

    private void handleAutoLeveling() {
        levelHandler.update(currentRPM, currentTransmissionLevel);
        int suggestedLevel = levelHandler.getSuggestedLevel();
        if (suggestedLevel < 1) {
            throw new IllegalStateException("Suggested transmission level is -1!");
        }

        if (suggestedLevel != this.currentTransmissionLevel) {
            handleShifting(this.currentTransmissionMode, levelHandler.getSuggestedLevel());
        }
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
                        return BASE_RPM_PER_SEC;
                    case 2:
                        return (long)(BASE_RPM_PER_SEC / RATIO_2_TO_1);
                    case 3:
                        return (long)(BASE_RPM_PER_SEC / RATIO_3_TO_1);
                    case 4:
                        return (long)(BASE_RPM_PER_SEC / RATIO_4_TO_1);
                    case 5:
                        return (long)(BASE_RPM_PER_SEC / RATIO_5_TO_1);
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
                return getRPMPerSecond(D_DRIVE, 1);
            default:
                throw new IllegalStateException("Missing implementation of transmission mode " + mode + "!");
        }
    }

    private void handleShifting(CarTransmissionMode requestedMode, int requestedTransmissionLevel) {
        if (!requestedMode.supportsLevel(requestedTransmissionLevel)) {
            throw new IllegalArgumentException("Transmission mode "
                    + requestedMode + " does not support level: "
                    + requestedTransmissionLevel);
        }

        if (requestedMode == this.currentTransmissionMode) {
            shiftToLevel(requestedTransmissionLevel);
        } else {
            if (!requestedMode.supportsLevel(requestedTransmissionLevel)) {
                String message = "Requested transmission mode ("
                        + requestedMode + ") does not support transmission level "
                        + requestedTransmissionLevel + "!";
                throw new UnsupportedOperationException(message);
            }

            out.println("Shifting [" + currentTransmissionMode.letter() + currentTransmissionLevel + "] ---> ["
                    + requestedMode.letter() + requestedTransmissionLevel + "] at RPM " + currentRPM);

            this.currentTransmissionMode = requestedMode;
            this.currentTransmissionLevel = requestedTransmissionLevel;
        }
    }

    private void shiftToLevel(int requestedTransmissionLevel) {
        int levelDiff = requestedTransmissionLevel - currentTransmissionLevel;

        if (levelDiff == 0) {
            return;
        }

        out.println("Shifting in ["
                + currentTransmissionMode + "] "
                + currentTransmissionLevel + " ---> "
                + requestedTransmissionLevel + "] at RPM "
                + currentRPM);

        if (levelDiff > 0) {
            shiftUp(requestedTransmissionLevel, levelDiff);
        } else {
            shiftDown(requestedTransmissionLevel, levelDiff);
        }

        this.currentTransmissionLevel = requestedTransmissionLevel;
    }

    private void shiftDown(int requestedTransmissionLevel, int levelDiff) {
        if (requestedTransmissionLevel < 0) {
            throw new UnsupportedOperationException("Min transmission level is 1.");
        }

        for (;levelDiff < 0; ++levelDiff) {
            currentRPM *= 1.5;
        }
    }

    private void shiftUp(int requestedTransmissionLevel, int levelDiff) {
        if (requestedTransmissionLevel > 5) {
            throw new UnsupportedOperationException("Max transmission level is 5.");
        }

        for (;levelDiff > 0; --levelDiff) {
            currentRPM /= 1.5;
        }
    }

    @Override
    public EngineStatusPacketData provideInfo() {
        return new EngineStatusPacketData(currentRPM, currentTransmissionLevel, currentTransmissionMode);
    }

    @Override
    public double rpmToForce(long rpm, CarTransmissionMode mode, int level) {
        switch (mode) {
            case P_PARKING:
            case N_NEUTRAL:
                return 0;
            case R_REVERSE:
                return -1 * rpmToForce(rpm, D_DRIVE, 1);
            case D_DRIVE:
                return rpmToForceInD(rpm, level);
            default:
                throw new IllegalStateException("Unexpected transmission mode: " + mode);
        }
    }

    private double rpmToForceInD(long rpm, int level) {
        switch (level) {
            case 1:
                return rpm / 500.0;
            case 2:
                return rpm / 500.0 * RATIO_2_TO_1;
            case 3:
                return rpm / 500.0 * RATIO_3_TO_1;
            case 4:
                return rpm / 500.0 * RATIO_4_TO_1;
            case 5:
                return rpm / 500.0 * RATIO_5_TO_1;
            default:
                if (D_DRIVE.supportsLevel(level)) {
                    throw new IllegalStateException("Missing implementation for D level: " + level);
                } else {
                    throw new IllegalArgumentException("Transmission mode D does not support level: " + level);
                }
        }
    }

    @Override
    public int getCurrentTransmissionLevel() {
        return this.currentTransmissionLevel;
    }

}

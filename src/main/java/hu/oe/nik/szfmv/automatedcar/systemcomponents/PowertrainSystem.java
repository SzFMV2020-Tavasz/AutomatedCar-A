package hu.oe.nik.szfmv.automatedcar.systemcomponents;


import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.PowertrainPacket;
import hu.oe.nik.szfmv.common.exceptions.NegativeNumberException;

/**
 * Powertrain system is responsible for the movement of the car.
 */
public class PowertrainSystem extends SystemComponent {
    public static final int MAX_RPM = 6000;
    public static final int MIN_RPM = 750;

    private static final double GEAR_RATIOS = 1.1;
    private static final int PERCENTAGE_DIVISOR = 100;
    private static final int SAMPLE_WEIGHT = 6000;
    private static final double SAMPLE_RESISTANCE = 2;
    private static final int ENGINE_BRAKE_TORQUE = 40;
    private static final double MAX_BRAKE_DECELERATION = 6;
    private static final double MAX_FORWARD_SPEED = 40;
    private static final double MIN_FORWARD_SPEED = 4.3888;
    private static final double MAX_REVERSE_SPEED = -10.278;
    private static final double MIN_REVERSE_SPEED = -3.3888;
    private static final int EMERGENCY_BRAKE_PEDAL = 80;
    private double speed;
    private int currentRPM;
    private int updatedRPM;
    private double speedDifference;
    private int gasPedal;
    private int brakePedal;
    private String gearState;
    private boolean isReverse;
    private boolean isInGear;

    /**
     * Creates a powertrain system that connects the Virtual Function Bus
     *
     * @param virtualFunctionBus {@link VirtualFunctionBus} used to connect {@link SystemComponent}s
     */
    public PowertrainSystem(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);

        this.virtualFunctionBus.powertrainPacket = new PowertrainPacket();

        this.currentRPM = MIN_RPM;
        this.updatedRPM = this.currentRPM;
    }

    @Override
    public void loop() {
        this.gearState = this.virtualFunctionBus.samplePacket.getGear();
        this.brakePedal = this.virtualFunctionBus.samplePacket.getBreakpedalPosition();
        this.gasPedal = this.virtualFunctionBus.samplePacket.getGaspedalPosition();

        doPowerTrain();

        this.currentRPM = this.updatedRPM;
        this.updatedRPM = MIN_RPM;

        this.virtualFunctionBus.powertrainPacket.setRpm(this.currentRPM);
        this.virtualFunctionBus.powertrainPacket.setSpeed(getSpeed());
    }

    /**
     * Returns the speed of the object.
     *
     * @return Speed of the object.
     */
    public double getSpeed() {
        return Math.abs(speed);
    }

    /**
     * Returns the speed of the object containing direction information (ie. negative means backwards).
     *
     * @return Speed of the object.
     */
    public double getSpeedWithDirection() {
        return speed;
    }

    /**
     * Stops the car immediately.
     */
    public void stopImmediately() {
        this.speedDifference = 0;
        this.speed = 0;
        this.updatedRPM = MIN_RPM;
    }

    /**
     * @param speedLimit Set the speed limit on the virtualfunctionbus
     */
    public void setSpeedLimit(double speedLimit) {
        this.virtualFunctionBus.powertrainPacket.setSpeedLimit(speedLimit);
    }

    /**
     * Unlock the Speedlimit on the virtualfunctionbus
     */
    public void unlockSpeedLimit() {
        this.virtualFunctionBus.powertrainPacket.unlockSpeedLimit();
    }

    /**
     * Do Power Train
     */
    private void doPowerTrain() {
        double speedThreshold = calculateSpeedThreshold();
        if (Math.abs(this.speed) > Math.abs(speedThreshold)) {
            this.brakePedal = EMERGENCY_BRAKE_PEDAL;
        }

        switch (gearState) {
            case "R":
                reverse();
                break;
            case "P":
                park();
                break;
            case "N":
                noGear();
                break;
            case "D":
                drive();
                break;
            default:
                break;
        }
    }

    /**
     * Set speed when gearstate is P - park
     */
    private void park() {
        this.updatedRPM = MIN_RPM;
        this.isInGear = false;
        calculateSpeedDifference();

        updateSpeed();
    }

    /**
     * Set speed when gearstate is D - drive
     */
    private void drive() {
        this.isReverse = false;
        this.isInGear = true;

        try {
            this.updatedRPM = calculateRpm(this.gasPedal);
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }

        calculateSpeedDifference();
        updateSpeed();

        if (this.gasPedal == 0 && this.speed < MIN_FORWARD_SPEED) {
            stopImmediately();
        }
    }


    /**
     * Set speed when gearstate is N - no gear
     */
    private void noGear() {
        this.updatedRPM = MIN_RPM;
        this.isInGear = false;
        calculateSpeedDifference();

        updateSpeed();
    }


    /**
     * Set speed when gearstate is R - reverse
     */
    private void reverse() {
        this.isReverse = true;
        this.isInGear = true;

        try {
            this.updatedRPM = calculateRpm(this.gasPedal);
        } catch (NegativeNumberException e) {
            e.printStackTrace();
        }

        calculateSpeedDifference();
        updateSpeed();

        if (this.gasPedal == 0 && this.speed < MIN_REVERSE_SPEED) {
            stopImmediately();
        }
    }

    // Calculates maximum or minimum (maximum in reverse) speed.
    private double calculateSpeedThreshold() {
        boolean isSpeedLimited = this.virtualFunctionBus.powertrainPacket.isSpeedLimited();
        double speedLimit = this.virtualFunctionBus.powertrainPacket.getSpeedLimit();

        return !this.isReverse ?
                   (isSpeedLimited ? Math.min(speedLimit, MAX_FORWARD_SPEED) : MAX_FORWARD_SPEED) :
                   (isSpeedLimited ? Math.max(speedLimit, MAX_REVERSE_SPEED) : MAX_REVERSE_SPEED);
    }

    /**
     * Calculate the difference between the previous and the increased speed.
     */
    private void calculateSpeedDifference() {
        double isReverseModifier = this.isReverse ? -1 : 1;

        if (virtualFunctionBus.powertrainPacket.isSpeedLimited()
                && this.speed <= virtualFunctionBus.powertrainPacket.getSpeedLimit()) {
            this.speedDifference = 0;
            return;
        }

        if (this.brakePedal > 0) {
            // Braking.
            this.speedDifference = -1 * isReverseModifier *
                                       ((MAX_BRAKE_DECELERATION / (double) PERCENTAGE_DIVISOR) * this.brakePedal);
        } else if (this.isInGear && this.gasPedal > 0) {
            // Acceleration.
            this.speedDifference = isReverseModifier * this.updatedRPM * GEAR_RATIOS /
                                       (SAMPLE_WEIGHT * SAMPLE_RESISTANCE);
        } else {
            // Slowing down.
            this.speedDifference = -1 * isReverseModifier * (double) ENGINE_BRAKE_TORQUE * SAMPLE_RESISTANCE /
                                       (double) PERCENTAGE_DIVISOR;
        }
    }

    /**
     * Change the current speed by the speed delta.
     */
    private void updateSpeed() {
        double updatedSpeed = this.speed + this.speedDifference;
        double speedThreshold = calculateSpeedThreshold();

        if (this.isReverse && (updatedSpeed >= speedThreshold || this.speedDifference > 0) ||
                !this.isReverse && (updatedSpeed <= speedThreshold || this.speedDifference < 0)) {
            this.speed += this.speedDifference;
        }

        if (!this.isReverse && updatedSpeed <= 0 || this.isReverse && updatedSpeed >= 0) {
            this.speed = 0;
        }
    }

    /**
     * Calculate the RPM of the engine.
     *
     * @param gasPedalPosition Position of the gaspedal
     * @return The calculated RPM
     * @throws NegativeNumberException the input value must be a non-negative number
     */
    public int calculateRpm(int gasPedalPosition) throws NegativeNumberException {
        if (gasPedalPosition < 0) {
            throw new NegativeNumberException("The position of the gas pedal must be a non-negative number");
        }

        int updatedRPM = MIN_RPM;
        if (gasPedalPosition > 0) {
            updatedRPM = (int) ((gasPedalPosition * ((double) (MAX_RPM - MIN_RPM) / PERCENTAGE_DIVISOR)) + MIN_RPM);
        }

        return updatedRPM;
    }
}


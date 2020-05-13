package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.cruisecontrol.CruiseControl;
import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.math.MathUtils;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.DependsOn;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.ICarControlPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.ManualCarControlPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.ICarMovePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.IEngineStatusPacket;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.*;
import static java.lang.Math.*;

/**
 * <p>The powertrain encompasses every component that converts the engine’s power into movement.</p>
 * <p>This includes the engine, transmission, the driveshaft, differentials, axles;
 * basically anything from the engine through to the rotating wheels.</p>
 *
 * @author Team 3
 */
@DependsOn(components = {/*MANUAL_INPUT_COMPONENT_PLACEHOLDER,*/ CruiseControl.class})
public class PowerTrain extends SystemComponent {

    /**
     * The maximum positive and negative rotation angle of the front wheels in {@link MathUtils#RADIAN_PERIOD radians}.
     */
    private static final double MAX_CAR_FRONT_WHEELS_ROTATION = toRadians(60);
    private static final double BREAK_POWER = 5.0;

    private final SimpleTransmission transmission = new SimpleTransmission();

    private IVector currentMovement = nullVector();
    private IVector currentWheelDirection = nullVector();

    /**
     * Received required information. Gets re-assigned in each loop.
     */
    private ManualCarControlPacket input;

    public PowerTrain(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        this.provideInitialOutput();
    }

    public ITransmission getTransmission() {
        return transmission;
    }

    private void provideInitialOutput() {
        CarMovePacketData initialPositionOutput = new CarMovePacketData(vectorFromXY(0, 0), Axis.Y.positiveDirection());
        IEngineStatusPacket initialEngineOutput = this.produceEngineInfoOutput();
        this.provideOutput(initialPositionOutput, initialEngineOutput);
    }

    @SuppressWarnings("removal" /*temporary support for compatibility*/)
    private void provideOutput(CarMovePacketData positionData, IEngineStatusPacket engineData) {
        this.virtualFunctionBus.carMovePacket = positionData;
        this.virtualFunctionBus.engineStatusPacket = engineData;

        this.virtualFunctionBus.powerTrain.carMovePacket = positionData;
        this.virtualFunctionBus.powerTrain.engineStatusPacket = engineData;
    }

    private CarMovePacketData produceMovementOutput() {
        IVector movement = calculateMovement();
        return new CarMovePacketData(movement, currentWheelDirection);
    }

    @Override
    public void loop() {
        updateInputs();
        updateTransmission();
        provideOutput(produceMovementOutput(), produceEngineInfoOutput());
    }

    private ICarControlPacket aggregateInputs() {
        boolean isTempomatOn = virtualFunctionBus.cruiseControl.getState().isEnabled();
        return new ICarControlPacket() {

            private ICarControlPacket getUsedInput() {
                return isTempomatOn
                        ? virtualFunctionBus.cruiseControl.getControl()
                        : virtualFunctionBus.manualCarControlPacket;
            }

            @Override
            public double getGasPedalRatio() {
                return getUsedInput().getGasPedalRatio();
            }

            @Override
            public double getBreakPedalRatio() {
                return getUsedInput().getGasPedalRatio();
            }

            @Override
            public double getSteeringWheelRotation() {
                return getUsedInput().getSteeringWheelRotation();
            }

        };
    }

    private void updateTransmission() {
        CarTransmissionMode targetMode = getRequestedCarTransmissionMode();
        ICarControlPacket input = aggregateInputs();

        if (targetMode != null && targetMode != transmission.getCurrentTransmissionMode()) {
            transmission.forceShift(targetMode, targetMode.getMinimumLevel());
        }

        transmission.update(input.getGasPedalRatio());
    }

    private void updateInputs() {
        this.input = virtualFunctionBus.manualCarControlPacket;
    }

    private IEngineStatusPacket produceEngineInfoOutput() {
        return transmission.provideInfo();
    }

    /**
     * @return {@link CarTransmissionMode} instance or {@code null}.
     */
    private CarTransmissionMode getRequestedCarTransmissionMode() {
        return input.getShiftChangeRequest();
    }

    private IVector calculateMovement() {
        this.currentWheelDirection = calculateWheelDirection();
        applyTrust();
        applySlowing();
        return this.currentMovement;
    }

    private void applyTrust() {
        double forceFromEngine = transmission.rpmToForce(this.transmission.getCurrentRPM());
        IVector trust = currentWheelDirection.multiplyBy(forceFromEngine);
        this.currentMovement = this.currentMovement.add(trust);

        if (abs(this.currentMovement.getDegreesRelativeTo(currentWheelDirection)) < 90) {
            this.currentMovement = this.currentMovement.withDirection(currentWheelDirection);
        } else {
            this.currentMovement = this.currentMovement.withDirection(currentWheelDirection).reverse();
        }
    }

    private void applySlowing() {
        double speed = this.currentMovement.getLength();
        double resistance = 0.005 + speed / 5;
        double breaking = input.getBreakPedalRatio() * BREAK_POWER;
        double decreasedSpeed = max(0, speed - resistance - breaking);
        this.currentMovement = this.currentMovement.withLength(decreasedSpeed);
    }

    private IVector calculateWheelDirection() {
        double maxSteeringWheelRotating = input.getMaxSteeringWheelRotation();
        double steeringWheelRatio = min(maxSteeringWheelRotating, input.getSteeringWheelRotation())
                / maxSteeringWheelRotating;
        double carWheelRadians = steeringWheelRatio * MAX_CAR_FRONT_WHEELS_ROTATION;

        return vectorWithAngle(carWheelRadians);
    }

    public static final class Packets {
        private ICarMovePacket carMovePacket;
        private IEngineStatusPacket engineStatusPacket;

        public ICarMovePacket getMovement() {
            return carMovePacket;
        }

        public IEngineStatusPacket getEngineStatus() {
            return engineStatusPacket;
        }
    }

}

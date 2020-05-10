package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.DependsOn;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.ToPowerTrainPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.IEngineStatusPacket;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.*;
import static hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.ToPowerTrainPacket.MAX_STEERING_ROTATION;
import static java.lang.Math.*;

/**<p>The powertrain encompasses every component that converts the engine’s power into movement.</p>
 * <p>This includes the engine, transmission, the driveshaft, differentials, axles;
 * basically anything from the engine through to the rotating wheels.</p>
 * @author Team 3*/
@DependsOn(components = Driver.class)
public class PowerTrain extends SystemComponent {

    static final double MAX_WHEEL_ROTATION = 60.0;
    private static final double MAX_GAS_PEDAL_VALUE = 100.0;
    private static final double MAX_BREAK_PEDAL_VALUE = 100.0;
    public static final double BREAK_POWER = 5.0;

    public ITransmission transmission = new SimpleTransmission();

    private IVector currentMovement = nullVector();
    private IVector currentWheelDirection = nullVector();

    /**Received required information. Gets re-assigned in each loop.*/
    private ToPowerTrainPacket input;

    public PowerTrain(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        this.provideInitialOutput();
    }

    private void provideInitialOutput() {
        CarMovePacketData initialPositionOutput = new CarMovePacketData(vectorFromXY(0, 0), Axis.Y.positiveDirection());
        IEngineStatusPacket initialEngineOutput = this.produceEngineInfoOutput();
        this.provideOutput(initialPositionOutput, initialEngineOutput);
    }

    private void provideOutput(CarMovePacketData positionData, IEngineStatusPacket engineData) {
        this.virtualFunctionBus.carMovePacket = positionData;
        this.virtualFunctionBus.engineStatusPacket = engineData;
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

    private void updateTransmission() {
        double gasPedalPressRatio = input.getGasPedalValue() / MAX_GAS_PEDAL_VALUE;
        CarTransmissionMode targetMode = getRequestedCarTransmissionMode();

        if (targetMode != null && targetMode != transmission.getCurrentTransmissionMode()) {
            transmission.forceShift(targetMode, targetMode.getMinimumLevel());
        }

        transmission.update(gasPedalPressRatio);
    }

    private void updateInputs() {
        this.input = virtualFunctionBus.toPowerTrainPacket;
    }

    private IEngineStatusPacket produceEngineInfoOutput() {
        return transmission.provideInfo();
    }

    /**@return {@link CarTransmissionMode} instance or {@code null}.*/
    private CarTransmissionMode getRequestedCarTransmissionMode() {
        Shitfer.ShiftPos requestedShiftPosition = input.getShiftChangeRequest();
        return requestedShiftPosition != null
                ? CarTransmissionMode.fromShiftPos(requestedShiftPosition)
                : null;
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
        double breaking = (input.getBreakPedalValue() / MAX_BREAK_PEDAL_VALUE) * BREAK_POWER;
        double decreasedSpeed = max(0, speed - resistance - breaking);
        this.currentMovement = this.currentMovement.withLength(decreasedSpeed);
    }

    private IVector calculateWheelDirection() {
        double steeringWheelDegree = input.getSteeringWheelValue();
        double carWheelDegree = steeringWheelDegree * (MAX_WHEEL_ROTATION / MAX_STEERING_ROTATION);
        double carWheelRadians = toRadians(carWheelDegree);

        return vectorWithAngle(carWheelRadians);
    }

}

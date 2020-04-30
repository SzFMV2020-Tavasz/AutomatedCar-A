package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.ToPowerTrainPacket;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.*;
import static java.lang.Math.toRadians;

/**<p>The powertrain encompasses every component that converts the engine’s power into movement.</p>
<p>This includes the engine, transmission, the driveshaft, differentials, axles; basically anything from the engine through to the rotating wheels.</p>*/
public class PowerTrain extends SystemComponent {

    static final double MAX_STEERING_ROTATION = 180.0;
    static final double MAX_WHEEL_ROTATION = 60.0;
    private static final double MAX_GAS_PEDAL_VALUE = 100.0;

    public ITransmission2 transmission = new SimpleTransmission();

    private ToPowerTrainPacket input;

    public PowerTrain(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        this.provideInitialOutput();
    }

    private void provideInitialOutput() {
        CarMovePacketData initialPositionOutput = new CarMovePacketData(vectorFromXY(0, 0), Axis.Y.positiveDirection());
        EngineStatusPacketData initialEngineOutput = this.produceEngineInfoOutput();
        this.provideOutput(initialPositionOutput, initialEngineOutput);
    }

    private void provideOutput(CarMovePacketData positionData, EngineStatusPacketData engineData) {
        this.virtualFunctionBus.carMovePacket = positionData;
        this.virtualFunctionBus.engineStatusPacket = engineData;
    }

    private CarMovePacketData produceMovementOutput() {
        IVector move = calculateMove();
        if (move.isDirectional()) {
            return new CarMovePacketData(move, move);
        } else {
            return new CarMovePacketData(nullVector(), calculateWheelRotation());
        }
    }

    @Override
    public void loop() {
        this.input = virtualFunctionBus.toPowerTrainPacket;
        double gasPedalPressRatio = virtualFunctionBus.toPowerTrainPacket.getGasPedalValue() / MAX_GAS_PEDAL_VALUE;
        CarTransmissionMode targetMode = getRequestedCarTransmissionMode();
        int targetLevel = targetMode == CarTransmissionMode.D_DRIVE ? 1 : 0; //TODO input.getTempomatValue(); ??

        transmission.update(gasPedalPressRatio, targetMode, targetLevel);

        provideOutput(produceMovementOutput(), produceEngineInfoOutput());
    }

    private EngineStatusPacketData produceEngineInfoOutput() {
        return transmission.provideInfo();
    }

    /**@return {@link CarTransmissionMode} instance or {@code null}.*/
    private CarTransmissionMode getRequestedCarTransmissionMode() {
        Shitfer.ShiftPos requestedShiftPosition = input.getShiftChangeRequest();
        return requestedShiftPosition != null
                ? CarTransmissionMode.fromShiftPos(requestedShiftPosition)
                : null;
    }

    private IVector calculateMove() {
        return calculateWheelRotation().multiplyBy(this.transmission.getCurrentRPM() / 100.0);
    }

    private IVector calculateWheelRotation() {
        double steeringWheelDegree = input.getSteeringWheelValue();
        double carWheelDegree = steeringWheelDegree * (MAX_WHEEL_ROTATION / MAX_STEERING_ROTATION);
        double carWheelRadians = toRadians(carWheelDegree);

        return vectorWithAngle(carWheelRadians);
    }

}

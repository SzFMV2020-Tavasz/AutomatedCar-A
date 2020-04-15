package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.ToPowerTrainPacket;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorFromXY;
import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorWithAngle;
import static java.lang.Math.toRadians;

/**<p>The powertrain encompasses every component that converts the engine’s power into movement.</p>
<p>This includes the engine, transmission, the driveshaft, differentials, axles; basically anything from the engine through to the rotating wheels.</p>*/
public class PowerTrain extends SystemComponent {

    static final double RPM_MULTIPLIER = 6000.0;
    static final double MAX_STEERING_ROTATION = 180.0;
    static final double MAX_WHEEL_ROTATION = 60.0;

    private ToPowerTrainPacket input;
    //private EngineStatusPacketData status;

    public PowerTrain(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        this.input = virtualFunctionBus.toPowerTrainPacket;
        this.provideInitialOutput();
    }

    private void provideInitialOutput() {
        CarMovePacketData initialOutput = new CarMovePacketData(vectorFromXY(0, 0));
        this.provideOutput(initialOutput);
    }

    private void provideOutput(CarMovePacketData data) {
        this.virtualFunctionBus.carPositionPacket = data;
    }

    private CarMovePacketData produceOutput() {
        IVector move = calculateMove();
        return new CarMovePacketData(move);
    }

    @Override
    public void loop() {
        provideOutput(produceOutput());
    }

    private IVector calculateMove() {
        return calculateWheelRotation().multiplyBy(input.getGasPedalValue());
    }

    private IVector calculateWheelRotation() {
        double steeringWheelDegree = input.getSteeringWheelValue();
        double carWheelDegree = steeringWheelDegree * (MAX_WHEEL_ROTATION / MAX_STEERING_ROTATION);
        double carWheelRadians = toRadians(carWheelDegree);

        return vectorWithAngle(carWheelRadians);
    }

}

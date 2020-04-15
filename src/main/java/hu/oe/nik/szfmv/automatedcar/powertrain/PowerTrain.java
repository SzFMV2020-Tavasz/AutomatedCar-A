package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.ToPowerTrainPacket;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorFromXY;
import static java.lang.Math.toRadians;

/**<p>The powertrain encompasses every component that converts the engine’s power into movement.</p>
<p>This includes the engine, transmission, the driveshaft, differentials, axles; basically anything from the engine through to the rotating wheels.</p>*/
public class PowerTrain extends SystemComponent {

    static final int RPM_MULTIPLIER = 6000;
    static final int MAX_STEERING_ROTATION = 180;
    static final int MAX_WHEEL_ROTATION = 60;

    private ToPowerTrainPacket input;
    //private EngineStatusPacketData status;

    private IVector currentCarRotation = Axis.Y.positiveDirection();

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

        return currentCarRotation.rotateByRadians(carWheelRadians);
    }

}

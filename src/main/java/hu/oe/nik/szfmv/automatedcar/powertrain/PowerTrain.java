package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.ToPowerTrainPacket;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorFromXY;

/**<p>The powertrain encompasses every component that converts the engine’s power into movement.</p>
<p>This includes the engine, transmission, the driveshaft, differentials, axles; basically anything from the engine through to the rotating wheels.</p>*/
public class PowerTrain extends SystemComponent {

    static final int RPM_MULTIPLIER = 6000;
    static final int MAX_STEERING_ROTATION = 180;
    static final int MAX_WHEEL_ROTATION = 60;

    private ToPowerTrainPacket input;
    private CarPositionPacketData lastOutput;
    //private EngineStatusPacketData status;

    private IVector currentCarRotation = Axis.Y.positiveDirection();

    public PowerTrain(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        this.input = virtualFunctionBus.toPowerTrainPacket;
        this.provideInitialOutput();
    }

    private void provideInitialOutput() {
        this.provideOutput(new CarPositionPacketData(540, 1450, vectorFromXY(0, 0)));
    }

    private void provideOutput(CarPositionPacketData data) {
        this.virtualFunctionBus.carPositionPacket = data;
        lastOutput = data;
    }

    private CarPositionPacketData produceOutput() {
        int previousCarPositionX = (int)lastOutput.getX();
        int previousCarPositionY = (int)lastOutput.getY();

        IVector move = calculateMove();
        return new CarPositionPacketData(
                previousCarPositionX + move.getXDiff(),
                previousCarPositionY + move.getYDiff(),
                move);
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
        double carWheelDegree = steeringWheelDegree * (MAX_WHEEL_ROTATION/MAX_STEERING_ROTATION);
        double carWheelRadians = Math.toRadians(carWheelDegree);

        return currentCarRotation.rotateByRadians(carWheelRadians);
    }

}

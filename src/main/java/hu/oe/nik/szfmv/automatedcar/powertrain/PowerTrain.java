package hu.oe.nik.szfmv.automatedcar.powertrain;

import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Shitfer;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.hmioutputpackets.ToPowerTrainPacket;

/**<p>The powertrain encompasses every component that converts the engine’s power into movement.</p>
<p>This includes the engine, transmission, the driveshaft, differentials, axles; basically anything from the engine through to the rotating wheels.</p>*/
public class PowerTrain extends SystemComponent {

    private ToPowerTrainPacket input;
    private CarPositionPacketData output;
    private EngineStatusPacketData status;

    final int RPM_MULTIPLIER = 6000;
    final int MAX_STEERING_ROTATION = 180;
    final int MAX_WHEEL_ROTATION = 60;

    public PowerTrain(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
        input = virtualFunctionBus.toPowerTrainPacket;
        output = new CarPositionPacketData(540, 1450, new IVector() {
            @Override
            public double getXDiff() {
                return 0;
            }

            @Override
            public double getYDiff() {
                return 0;
            }

            @Override
            public double getLength() {
                return 0;
            }

            @Override
            public double getRadiansRelativeTo(IVector direction) {
                return 0;
            }
        });
        virtualFunctionBus.carPositionPacket = output;
    }

    @Override public void loop() {
        int x = (int)output.getX();
        int y = (int)output.getY();
        int length = (int)output.getMoveVector().getLength();
        output = new CarPositionPacketData(x, y, new IVector() {
            @Override
            public double getXDiff() {
                double steeringWheelDegree = input.getSteeringWheelValue();
                double carWheelDegree = steeringWheelDegree * (MAX_WHEEL_ROTATION/MAX_STEERING_ROTATION);
                double carWheelRadians = Math.toRadians(carWheelDegree);
                double x = length * Math.sin(carWheelRadians);
                if(input.getShiftChangeRequest() == Shitfer.ShiftPos.D){
                    return x;
                }
                if(input.getShiftChangeRequest() == Shitfer.ShiftPos.R){
                    return -x;
                }
                return 0;
            }

            @Override
            public double getYDiff() {
                return 0;
            }

            @Override
            public double getLength() {
                if(input.getShiftChangeRequest() == Shitfer.ShiftPos.D) {
                    if (input.getGasPedalValue() == 0 && input.getBreakPedalValue() == 100) return 0;
                    else if(-(input.getGasPedalValue() - input.getBreakPedalValue()) < 0){
                        return -(input.getGasPedalValue() - (input.getBreakPedalValue() / 10));
                    }
                    else return 0;
                }
                if(input.getShiftChangeRequest() == Shitfer.ShiftPos.R){
                    if (input.getGasPedalValue() == 0 && input.getBreakPedalValue() == 100) return 0;
                    else if((input.getGasPedalValue() - input.getBreakPedalValue()) > 0){
                        return (input.getGasPedalValue() - (input.getBreakPedalValue() / 10));
                    }
                    else return 0;
                }
                return 0;
            }

            @Override
            public double getRadiansRelativeTo(IVector direction) {
                return 0;
            }
        });
        virtualFunctionBus.carPositionPacket = output;
    }

}

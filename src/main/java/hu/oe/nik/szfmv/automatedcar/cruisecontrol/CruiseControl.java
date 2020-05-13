package hu.oe.nik.szfmv.automatedcar.cruisecontrol;

import hu.oe.nik.szfmv.automatedcar.powertrain.CarTransmissionMode;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.DependsOn;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.cruisecontrol.ICruiseControlPacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.cruisecontrol.ICruiseControlStatePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.ICarMovePacket;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.IEngineStatusPacket;

import static java.lang.System.out;

/**
 * A system that automatically controls the speed of a motor vehicle. The system is a servomechanism
 * that takes over the throttle of the car to maintain a steady speed as set by the driver.
 * <p></p>
 * <p>Depends on {@link Driver}, because manual input from the human driver affects the cruise control,
 * like turning it on/off via switch or by pressing the break pedal manually.</p>
 *
 * @author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)
 */
@DependsOn(components = Driver.class)
public final class CruiseControl extends SystemComponent {

    private boolean enabled = false;
    private boolean enabledRequestIsInProgress = false;
    private double targetSpeed = 10;

    public CruiseControl(VirtualFunctionBus virtualFunctionBus) {
        super(virtualFunctionBus);
    }

    @Override
    public void loop() {
        this.update();
        this.provideOutput(this.produceControl(), this.provideStateInfo());
    }

    private void update() {
        updateWhetherEnabled();
    }

    private void updateWhetherEnabled() {
        IEngineStatusPacket engineStatus = virtualFunctionBus.powerTrain.getEngineStatus();
        if (engineStatus == null) {
            return;
        }

        if (virtualFunctionBus.input.getAccInput().isAccButtonPressed()) {
            if (!enabledRequestIsInProgress) {
                enabledRequestIsInProgress = true;
                enabled = !enabled;
                out.println("Toggle ACC to " + (enabled ? "ON" : "OFF"));
            }
        } else {
            enabledRequestIsInProgress = false;
        }

        if (enabled) {
            boolean isNotInD = engineStatus.getTransmissionMode() != CarTransmissionMode.D_DRIVE;
            boolean isBreakPedalPressed = virtualFunctionBus.manualCarControlPacket.getBreakPedalRatio() > 0.0001;
            boolean parkingTooClose = this.virtualFunctionBus.leftParkingDistance.getDistance() < 10
                    || this.virtualFunctionBus.rightParkingDistance.getDistance() < 10;
            if (isNotInD || isBreakPedalPressed || parkingTooClose) {
                enabled = false;
                out.println("Turn ACC OFF (because hazard)");
            }
        }
    }

    public void setEnabled(boolean state) {
        if (enabled != state) {
            this.enabled = state;
            out.println("Tempomat " + (enabled ? "ON" : "OFF") + " (code call)");
        }
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    private ICruiseControlPacket produceControl() {
        if (!isEnabled()) {
            return null;
        }

        ICarMovePacket movePacket = virtualFunctionBus.powerTrain.getMovement();
        if (movePacket == null) {
            return null;
        }

        return CruiseControlPacket.newCruiseControlPacket()
                .withGasPedalRatio(movePacket.getSpeed() > targetSpeed ? 0.0 : 0.5)
                .withBreakPedalRatio(0.0)
                .withSteeringWheelRotationRatio(0.0)
                .build();
    }

    private ICruiseControlStatePacket provideStateInfo() {
        return new ICruiseControlStatePacket() {
            private final boolean enabled = CruiseControl.this.enabled;
            private final double targetSpeed = enabled
                    ? CruiseControl.this.targetSpeed
                    : Double.NaN;

            @Override
            public boolean isEnabled() {
                return enabled;
            }

            @Override
            public double getTargetSpeed() {
                return targetSpeed;
            }
        };
    }

    private void provideOutput(ICruiseControlPacket controlPacket, ICruiseControlStatePacket statePacket) {
        virtualFunctionBus.cruiseControl.controlPacket = controlPacket;
        virtualFunctionBus.cruiseControl.statePacket = statePacket;
    }

    public static final class Packets {
        private ICruiseControlPacket controlPacket;
        private ICruiseControlStatePacket statePacket;

        public ICruiseControlPacket getControl() {
            return controlPacket;
        }

        public ICruiseControlStatePacket getState() {
            return statePacket;
        }
    }

}

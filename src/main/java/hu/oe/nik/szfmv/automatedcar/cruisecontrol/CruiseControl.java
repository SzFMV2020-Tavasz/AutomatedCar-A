package hu.oe.nik.szfmv.automatedcar.cruisecontrol;

import hu.oe.nik.szfmv.automatedcar.powertrain.CarTransmissionMode;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.SystemComponent;
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
 * <p>Depends on {@code MANUAL_INPUT_COMPONENT_PLACEHOLDER}, because manual input from the human driver affects the cruise control,
 * like turning it on/off via switch or by pressing the break pedal manually.</p>
 *
 * @author Team 3 (Dávid Magyar | aether-fox | davidson996@gmail.com)
 */
//@DependsOn(components = MANUAL_INPUT_COMPONENT_PLACEHOLDER.class)
public final class CruiseControl extends SystemComponent {

    private boolean enabled = false;
    private boolean enabledRequestIsInProgress = false;

    private static final double MIN_TARGET_SPEED = 10;
    private static final double MAX_TARGET_SPEED = 80;
    private boolean targetSpeedChangeInProgress = false;
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
        updateSpeed();
    }

    private void updateSpeed() {
        boolean increaseRequest = this.virtualFunctionBus.input.getAccInput().isAccIncreaseSpeedButtonPressed();
        boolean decreaseRequest = this.virtualFunctionBus.input.getAccInput().isAccDecreaseSpeedButtonPressed();

        if (increaseRequest) {
            if (!targetSpeedChangeInProgress) {
                targetSpeedChangeInProgress = true;
                targetSpeed += 10;
                if (targetSpeed > MAX_TARGET_SPEED) {
                    targetSpeed = MAX_TARGET_SPEED;
                }
            }
        }

        if (decreaseRequest) {
            if (!targetSpeedChangeInProgress) {
                targetSpeedChangeInProgress = true;
                targetSpeed -= 10;
                if (targetSpeed < MIN_TARGET_SPEED) {
                    targetSpeed = MIN_TARGET_SPEED;
                }
            }
        }

        if (!increaseRequest && !decreaseRequest) {
            targetSpeedChangeInProgress = false;
        }
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
            if (isBlocked()) {
                enabled = false;
                out.println("Turn ACC OFF (because hazard)");
            }
        }
    }

    private boolean isBlocked() {
        boolean isNotInD = virtualFunctionBus.powerTrain.getEngineStatus().getTransmissionMode() != CarTransmissionMode.D_DRIVE;
        boolean isBreakPedalPressed = virtualFunctionBus.manualCarControlPacket.getBreakPedalRatio() > 0.0001;
        boolean aebAlarm = this.virtualFunctionBus.ultrasonicAEB.isAlarming;
        return isNotInD || isBreakPedalPressed || aebAlarm;
    }

    /**@return {@code true} when enabled/disabled successfully, {@code false} if could not.*/
    public boolean setEnabled(boolean state) {
        if (enabled != state) {
            if (state && isBlocked()) {
                return false;
            }

            this.enabled = state;
            out.println("Tempomat " + (enabled ? "ON" : "OFF") + " (code call)");
        }
        return true;
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

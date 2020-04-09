package hu.oe.nik.szfmv.automatedcar.systemcomponents;

import hu.oe.nik.szfmv.automatedcar.AutomatedCar;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.PowertrainPacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * @author  Robespierre19
 * @author  dradequate
 * @author  beregvarizoltan
 * @author  BazsoGabor
 * @author  robert-megyesi-woz
 * @author  Kadi96
 *
 * Date: 2019-05-07
 */

public class Powertrain extends SystemComponent {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ArrayList<Double> GEAR_SHIFT_LEVEL_SPEED;

    static {
        GEAR_SHIFT_LEVEL_SPEED =
            new ArrayList<>(Arrays.asList(1.3888, 5.5555, 9.7222, 13.8888, 22.2222, 30.5555, Double.MAX_VALUE));
    }

    private final float deltaTime = 0.04f;

    private final float maxSpeed = 600f;
    private final float minSpeed = -150f;

    private final float accelConst = 20f;
    private final float reverseAccelConst = 25f;
    private final float slowConst = 20f;
    private final float brakePowerConst = 60f;

    private final float pedalRate = 10.0f;

    private AutomatedCar car;

    private int rpm = 700;
    private float speed = 0f;   // in pixel/s
    private int actualAutoGear = 0;
    private boolean isAccelerate;

    private PowertrainPacket packet;

    public Powertrain(VirtualFunctionBus virtualFunctionBus, AutomatedCar car) {
        super(virtualFunctionBus);
        this.car = car;
        packet = new PowertrainPacket();
        virtualFunctionBus.powertrainPacket = packet;
    }

    @Override
    public void loop() {
        speed = car.getSpeed();

//        if (virtualFunctionBus.brakePacket.isBrake()) {
//            handleEmergencyBrake();
//        } else if (virtualFunctionBus.tempomatPacket.isActive() && virtualFunctionBus.inputPacket.isAccOn()) {
//            speed = virtualFunctionBus.tempomatPacket.getAccSpeed();
//        } else if (virtualFunctionBus.inputPacket.isParkingPilotOn()) {
            //
//        } else {
            handleCarMovement();
//        }

        createAndSendPacket();
    }

    /**
     * Send speed and rpm to VFB
     */
    private void createAndSendPacket() {
        packet.setSpeed(speed);
        packet.setRpm(rpm);
        packet.setActualAutoGear(actualAutoGear);
    }

    /**
     * Set the car speed based on the pedals
     */
    private void handleCarMovement() {
        switch (virtualFunctionBus.inputPacket.getGearShift()) {
            case R:
                handleGearShiftR();
                this.actualAutoGear = 0;
                releasedPedals();
                break;
            case P:
                this.actualAutoGear = 0;
                releasedPedals();
                break;
            case N:
                this.actualAutoGear = 0;
                releasedPedals();
                break;
            case D:
                handleGearShiftD();
                releasedPedals();
                handleAutoGearShift();
                break;
            default:
                break;
        }
    }

    private void handleAutoGearShift() {
        double multiplier = ((double) (6500 - 700) / 100);
        rpm = (int) ((virtualFunctionBus.inputPacket.getGasPedal() * multiplier) + 700);

        int change = 0;

        if (isAccelerate && actualAutoGear < 6) {
            while (GEAR_SHIFT_LEVEL_SPEED.get(actualAutoGear + change) < Math.abs(speed)) {
                change++;
            }
            if ((change > 0)) {
                actualAutoGear += change;
            }
        }

        if (!isAccelerate && actualAutoGear > 1) {
            while (GEAR_SHIFT_LEVEL_SPEED.get(actualAutoGear + change) > Math.abs(speed)) {
                if (actualAutoGear > 1) {
                    change--;
                }
            }
            if ((change < 0)) {
                actualAutoGear += change;
            }
        }
    }

    private void handleEmergencyBrake() {
        if (speed > 0) {
            speed -= 100 / pedalRate * brakePowerConst * deltaTime;

            if (speed < 0) {
                speed = 0;
            }
        } else {
            speed += 100 / pedalRate * brakePowerConst * deltaTime;

            if (speed > 0) {
                speed = 0;
            }
        }
    }

    /**
     * Set speed for backwards movement
     */
    private void handleGearShiftR() {
        // Tolatás
        if (virtualFunctionBus.inputPacket.getGasPedal() > 0 && speed > minSpeed) {
            speed -= virtualFunctionBus.inputPacket.getGasPedal() / pedalRate * reverseAccelConst * deltaTime;
        }

        // Lassítás
        if (virtualFunctionBus.inputPacket.getBreakPedal() > 0 && speed < 0) {
            speed += virtualFunctionBus.inputPacket.getBreakPedal() / pedalRate * brakePowerConst * deltaTime;
        }

        // Még véletlenül se lehessen előre menni
        if (speed > 0) {
            speed = 0;
        }
    }

    /**
     * Set speed for forward movement
     */
    private void handleGearShiftD() {
        float deltaSpeed = 0;

        if (actualAutoGear == 0) {
            actualAutoGear++;
        }

        //Gyorsítás
        if (virtualFunctionBus.inputPacket.getGasPedal() > 0 && speed < maxSpeed) {
            deltaSpeed = virtualFunctionBus.inputPacket.getGasPedal() / pedalRate * accelConst * deltaTime;
            isAccelerate = true;
        }

        // Lassítás
        if (virtualFunctionBus.inputPacket.getBreakPedal() > 0 && speed > 0) {
            deltaSpeed = -virtualFunctionBus.inputPacket.getBreakPedal() / pedalRate * brakePowerConst * deltaTime;
            isAccelerate = false;
        }

        // Még véletlenül se lehessen hátrafelé menni
        if (speed < 0) {
            deltaSpeed = 0;
        }

        speed += deltaSpeed;
    }

    /**
     * Slowly adjust speed until 0 when none of the pedals are active
     */
    private void releasedPedals() {
        if (virtualFunctionBus.inputPacket.getGasPedal() == 0
            && virtualFunctionBus.inputPacket.getBreakPedal() == 0 && speed > 0) {
            speed -= slowConst * deltaTime;
            if (speed < 0) {
                speed = 0;
            }
            isAccelerate = false;
        }

        if (virtualFunctionBus.inputPacket.getGasPedal() == 0
            && virtualFunctionBus.inputPacket.getBreakPedal() == 0 && speed < 0) {
            speed += slowConst * deltaTime;
            if (speed > 0) {
                speed = 0;
            }
        }
    }
}

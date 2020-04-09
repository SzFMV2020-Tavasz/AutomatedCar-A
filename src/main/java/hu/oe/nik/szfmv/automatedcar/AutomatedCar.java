package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.powertrain.PowerTrain;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.AutomatedCarPos;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Powertrain;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Steering;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.ReadOnlyCarPacket;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class AutomatedCar extends WorldObject {

    private static final Logger LOGGER = LogManager.getLogger(AutomatedCar.class);

    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    private final PowerTrain powerTrain = new PowerTrain(virtualFunctionBus);

    private float speed;
    private float wheelBase;
    private float steeringAngle;
    private float carHeading;  // in radians
    private Vector2D carLocation;
    private final float deltaTime = 0.04f;
    private final float bumperAxleDistance = 12f;
    private AutomatedCarPos positionTracker;

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);
        new Powertrain(virtualFunctionBus, this);
        new Steering(virtualFunctionBus);
    }

//    public void drive() {
//        virtualFunctionBus.loop();
//
//        calculatePositionAndOrientation();
//    }

    public float getSpeed() {
        return speed;
    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    public PowerTrain getPowerTrain() {
        return powerTrain;
    }

//    private void calculatePositionAndOrientation() {
//        //TODO it is just a fake implementation
//
//        switch (virtualFunctionBus.samplePacket.getKey()) {
//            case 0:
//                y -= 5;
//                break;
//            case 1:
//                x += 5;
//                break;
//            case 2:
//                y += 5;
//                break;
//            case 3:
//                x -= 5;
//                break;
//            default:
//                break;
//        }
//    }

    public void drive() {
        virtualFunctionBus.loop();

        carLocation = new Vector2D(x, y);
        carHeading = rotation;
        positionTracker.handleLocationChange(new Point((int)carLocation.getX(),
            (int)carLocation.getY()), this.carHeading);

    }

    private void calculatePositionAndOrientation() {
        speed = virtualFunctionBus.powertrainPacket.getSpeed();
        steeringAngle = virtualFunctionBus.steeringPacket.getSteeringAngle();

        Vector2D frontWheelPosition = carLocation.add(new Vector2D(Math.cos(carHeading), Math.sin(carHeading))
                                                          .scalarMultiply(wheelBase / 2));
        Vector2D backWheelPosition = carLocation.subtract(new Vector2D(Math.cos(carHeading), Math.sin(carHeading))
                                                              .scalarMultiply(wheelBase / 2));

        backWheelPosition = backWheelPosition
                                .add(new Vector2D(Math.cos(carHeading), Math.sin(carHeading))
                                         .scalarMultiply(speed * deltaTime));
        frontWheelPosition = frontWheelPosition
                                 .add(new Vector2D(Math.cos(carHeading + Math.toRadians(steeringAngle)),
                                     Math.sin(carHeading + Math.toRadians(steeringAngle)))
                                          .scalarMultiply(speed * deltaTime));

        carLocation = frontWheelPosition.add(backWheelPosition).scalarMultiply(0.5);
        carHeading = (float) Math.atan2(frontWheelPosition.getY() - backWheelPosition.getY(),
            frontWheelPosition.getX() - backWheelPosition.getX());
    }

    private void updateCarPositionAndOrientation() {
        this.x = (int) carLocation.getX();
        this.y = (int) carLocation.getY();
        this.rotation = carHeading;
    }

    public ReadOnlyCarPacket getCarValues() {
        return virtualFunctionBus.carPacket;
    }
    private float calculateWheelBase() {
        return this.height - bumperAxleDistance;
    }
}

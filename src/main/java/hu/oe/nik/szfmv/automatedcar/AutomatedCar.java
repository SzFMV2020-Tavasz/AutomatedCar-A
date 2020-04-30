package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.powertrain.PowerTrain;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.ICarMovePacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Polygon;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.*;
import static java.lang.Double.isNaN;
import static java.lang.Math.max;

/**Represents a car object with all its inner components contained.*/
public class AutomatedCar extends WorldObject {

    private static final Logger LOGGER = LogManager.getLogger(AutomatedCar.class);

    /**Host for information interchange between components of the car via packets.*/
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    /**@see PowerTrain*/
    private final PowerTrain powerTrain = new PowerTrain(virtualFunctionBus);

    /**Not necessarily a unit vector, can have any length.*/
    private IVector facingDirection = Axis.Y.negativeDirection();

    private IVector currentMovement = nullVector();

    // may or may not be permanent: the egocar's debug polygon
    private final Polygon debugPoly = new Polygon(
        new int[]{50, 38, 27, 18, 12, 7, 6, 6, 3, 1, 0, 7, 7, 6, 6, 6, 9, 13, 17, 21, 26, 31, 37, 42, 50, 58, 63,
            69, 76, 79, 83, 87, 91, 94, 94, 94, 93, 93, 100, 99, 97, 94, 94, 93, 88, 82, 73, 62},
        new int[]{1, 2, 4, 8, 14, 23, 33, 63, 65, 67, 70, 69, 150, 152, 188, 191, 194, 198, 201, 203, 205, 206,
            207, 208, 208, 208, 207, 206, 205, 203, 201, 198, 194, 191, 188, 152, 150, 69, 70, 67, 65,
            63, 33, 23, 14, 8, 4, 2},
        48);

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);

        this.polygon = debugPoly;
    }

    public void drive() {
        virtualFunctionBus.loop();
        updatePositionAndOrientation();
    }

    public VirtualFunctionBus getVirtualFunctionBus() {
        return virtualFunctionBus;
    }

    public PowerTrain getPowerTrain() {
        return powerTrain;
    }

    /**Gets the current center position of the car.*/
    public IVector getPosition() {
        return vectorFromXY(this.x, this.y);
    }

    /**Sets the center position of the car.*/
    public void setPosition(IVector position) {
        this.setX((int)position.getXDiff());
        this.setY((int)position.getYDiff());
    }

    private void updatePositionAndOrientation() {
        applyTrust();
        applySlowing();

        double currentAcceleration = this.currentMovement.getLength();
        if (isNaN(currentAcceleration) || currentAcceleration == 0) {
            return;
        }
        this.moveCar(this.currentMovement);
    }

    private void applySlowing() {
        double currentAcceleration = this.currentMovement.getLength();
        double newAcceleration = max(0, currentAcceleration - currentAcceleration / 5);
        this.currentMovement = this.currentMovement.withLength(newAcceleration);
    }

    private void applyTrust() {
        ICarMovePacket propulsionData = virtualFunctionBus.carMovePacket;
        IVector trust = virtualFunctionBus.carMovePacket.getAccelerationVector();
        this.currentMovement = this.currentMovement.add(trust).withDirection(propulsionData.getWheelFacingDirection());
    }

    /**Applies movement to the car.
     * @param move The movement to apply.
     * <p>- its direction is interpreted as facing direction of the front wheels.</p>
     * <p>- its length is interpreted as the speed of movement.</p>*/
    private void moveCar(IVector move) {
        switch (powerTrain.transmission.getCurrentTransmissionMode()) {
            case D_DRIVE:
                this.moveForward(move);
                break;
            case R_REVERSE:
                this.moveBackward(move);
                break;
            case N_NEUTRAL:
                this.neturalGear();
                break;
            default:
                this.parkingGear();
                break;
        }
    }

    private void moveForward(IVector forwardMove) {
        IVector currentPosition = this.getPosition();
        IVector toCarFrontVector = this.facingDirection.withLength(getHeight() / 2.0);
        IVector carFrontPosition = currentPosition.add(toCarFrontVector);
        IVector carBackPosition = currentPosition.subtract(toCarFrontVector);

        IVector moveInDirection = forwardMove.rotateByRadians(this.facingDirection.getRadians());

        IVector newCarFrontPosition = carFrontPosition.add(moveInDirection);
        IVector newCarBackPosition = carBackPosition.add(forwardMove.withDirection(toCarFrontVector));

        this.setPosition(average(newCarFrontPosition, newCarBackPosition));

        this.facingDirection = newCarFrontPosition.subtract(newCarBackPosition);
        this.setRotation(facingDirection.getRadiansRelativeTo(Axis.Y.negativeDirection()));
    }

    private void moveBackward(IVector backwardMove) {
        IVector currentPosition = this.getPosition();
        IVector toCarBackVector = this.facingDirection.withLength(getHeight() / -2.0);
        IVector carFrontPosition = currentPosition.subtract(toCarBackVector);
        IVector carBackPosition = currentPosition.add(toCarBackVector);

        IVector moveInDirection = backwardMove.rotateByRadians(this.facingDirection.getRadians()).multiplyBy(-1);

        IVector newCarFrontPosition = carFrontPosition.add(moveInDirection);
        IVector newCarBackPosition = carBackPosition.add(backwardMove.withDirection(toCarBackVector));

        this.setPosition(average(newCarFrontPosition, newCarBackPosition));

        this.facingDirection = newCarFrontPosition.subtract(newCarBackPosition);
        this.setRotation(facingDirection.getRadiansRelativeTo(Axis.Y.negativeDirection()));
    }

    private void neturalGear() {
        //FAAAAAAAKE!!!
    }

    private void parkingGear() {
        //FAAAAAKE as FAF, but it will be probably enough for parking mode.
    }
}

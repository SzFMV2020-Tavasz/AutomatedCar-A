package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.powertrain.CarTransmissionMode;
import hu.oe.nik.szfmv.automatedcar.powertrain.PowerTrain;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.ICarMovePacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.average;
import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorFromXY;

/**Represents a car object with all its inner components contained.*/
public class AutomatedCar extends WorldObject {

    private static final Logger LOGGER = LogManager.getLogger(AutomatedCar.class);

    /**Host for information interchange between components of the car via packets.*/
    private final VirtualFunctionBus virtualFunctionBus = new VirtualFunctionBus();

    /**@see PowerTrain*/
    private final PowerTrain powerTrain = new PowerTrain(virtualFunctionBus);

    /**Not necessarily a unit vector, can have any length.*/
    private IVector facingDirection = Axis.Y.negativeDirection();

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
        ICarMovePacket moveInfo = this.virtualFunctionBus.carMovePacket;
        this.moveCar(moveInfo.getMoveVector());
    }

    /**Applies movement to the car.
     * @param movement The movement to apply.
     * <p>- its direction is interpreted as facing direction of the front wheels.</p>
     * <p>- its length is interpreted as the speed of movement.</p>*/
    private void moveCar(IVector movement) {
        IVector currentPosition = this.getPosition();
        IVector toCarFrontVector = this.facingDirection.withLength(getHeight() / 2.0);
        IVector carFrontPosition = currentPosition.add(toCarFrontVector);
        IVector carBackPosition = currentPosition.subtract(toCarFrontVector);

        IVector moveInDirection = movement.rotateByRadians(this.facingDirection.getRadians());

        IVector newCarFrontPosition = carFrontPosition.add(moveInDirection);

        boolean backwards = powerTrain.transmission.getCurrentTransmissionMode() == CarTransmissionMode.R_REVERSE;
        IVector newCarBackPosition = backwards
                ? carBackPosition.subtract(movement.withDirection(toCarFrontVector))
                : carBackPosition.add(movement.withDirection(toCarFrontVector));

        this.setPosition(average(newCarFrontPosition, newCarBackPosition));

        this.facingDirection = newCarFrontPosition.subtract(newCarBackPosition);
        this.setRotation(facingDirection.getRadiansRelativeTo(Axis.Y.negativeDirection()));
    }

}

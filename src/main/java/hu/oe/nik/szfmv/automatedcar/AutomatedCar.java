package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.model.deserializer.ReadPolygons;
import hu.oe.nik.szfmv.automatedcar.powertrain.PowerTrain;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.Polygon;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Arrays;

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

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);

        //ReadPolygons.readPolys();
        //this.polygons = new ArrayList<Path2D>(Arrays.asList(debugPoly));
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
        IVector carMove = virtualFunctionBus.carPositionPacket.getMoveVector();
        this.moveCar(carMove);
    }

    /**Applies movement to the car.
     * @param move The movement to apply.
     * <p>- its direction is interpreted as facing direction of the front wheels.</p>
     * <p>- its length is interpreted as the speed of movement.</p>*/
    private void moveCar(IVector move) {
        switch (powerTrain.transmission.getGearMode()) {
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

package hu.oe.nik.szfmv.automatedcar;

import hu.oe.nik.szfmv.automatedcar.math.Axis;
import hu.oe.nik.szfmv.automatedcar.math.IVector;
import hu.oe.nik.szfmv.automatedcar.model.WorldObject;
import hu.oe.nik.szfmv.automatedcar.powertrain.CarTransmissionMode;
import hu.oe.nik.szfmv.automatedcar.powertrain.PowerTrain;
import hu.oe.nik.szfmv.automatedcar.systemcomponents.Driver;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.DependentVirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.VirtualFunctionBus;
import hu.oe.nik.szfmv.automatedcar.virtualfunctionbus.packets.powertrain.ICarMovePacket;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static hu.oe.nik.szfmv.automatedcar.math.IVector.average;
import static hu.oe.nik.szfmv.automatedcar.math.IVector.vectorFromXY;
import static java.lang.Math.round;

/**Represents a car object with all its inner components contained.*/
public class AutomatedCar extends WorldObject {

    private static final Logger LOGGER = LogManager.getLogger(AutomatedCar.class);

    /**Host for information interchange between components of the car via packets.*/
    private final DependentVirtualFunctionBus virtualFunctionBus = new DependentVirtualFunctionBus();

    /**@see PowerTrain*/
    private final PowerTrain powerTrain = new PowerTrain(virtualFunctionBus);

    /**Not necessarily a unit vector, can have any length.*/
    private IVector facingDirection = Axis.Y.negativeDirection();

    public AutomatedCar(int x, int y, String imageFileName) {
        super(x, y, imageFileName);

        new Driver(virtualFunctionBus);
        this.virtualFunctionBus.validateAllDependenciesSatisfied();
    }

    public AutomatedCar(CarVariant variant) {
        this(0, 0, variant.getImageResourceName());
    }

    public void drive() {
        updatePositionAndOrientation();
        virtualFunctionBus.loop();
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
        this.setX((int)round(position.getXDiff()));
        this.setY((int)round(position.getYDiff()));
    }

    /**Sets the center position of the car.*/
    public void setPosition(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    /**@author Team 3*/
    private void updatePositionAndOrientation() {
        ICarMovePacket moveInfo = this.virtualFunctionBus.carMovePacket;
        this.moveCar(moveInfo.getMoveVector());
    }

    /**Applies movement to the car, although not the given vector directly.
     * Moves the car approximately in the direction of the given move also considering the rotation of the car.
     * @param movement The movement to apply.
     * <p>- its direction is interpreted as facing direction of the front wheels.</p>
     * <p>- its length is interpreted as the speed of movement.</p>
     * @author Team 3*/
    private void moveCar(IVector movement) {
        //TODO REMOVE DEBUG
        if (movement.hasLength() && movement.getLength() > 0) {
            System.out.printf("Relative to facing: %.4f°    (facing: (%.2f,%.2f) = %.4f° from -Y)",
                    movement.getDegrees(), facingDirection.getXDiff(),
                    facingDirection.getYDiff(),
                    facingDirection.getDegreesRelativeTo(Axis.Y.negativeDirection()));
            System.out.println();
        }

        IVector oldFacing = this.facingDirection;
        IVector oldPosition = this.getPosition();
        IVector toCarFrontVector = oldFacing.withLength(getHeight() / 2.0);
        IVector oldCarFrontPosition = oldPosition.add(toCarFrontVector);
        IVector oldCarBackPosition = oldPosition.subtract(toCarFrontVector);
        IVector moveInWheelFacingDirection = movement.rotateByRadians(oldFacing.getRadians());
        boolean reverseMode = powerTrain.transmission.getCurrentTransmissionMode() == CarTransmissionMode.R_REVERSE;

        IVector newCarFrontPosition = oldCarFrontPosition.add(moveInWheelFacingDirection);
        IVector newCarBackPosition = reverseMode
                ? oldCarBackPosition.subtract(movement.withDirection(oldFacing))
                : oldCarBackPosition.add(movement.withDirection(oldFacing));
        IVector newPosition = average(newCarFrontPosition, newCarBackPosition);
        IVector newFacing = newCarFrontPosition.subtract(newCarBackPosition);

        this.setPosition(newPosition);
        this.facingDirection = newFacing;
        this.setRotation(newFacing.getRadiansRelativeTo(Axis.Y.negativeDirection()));

        //TODO REMOVE DEBUG
        if (movement.hasLength() && movement.getLength() > 0) {
            System.out.printf("Move vector: %s",
                    newPosition.subtract(oldPosition).printXY("(%.3f,%.3f)"));
            System.out.println();
        }

        //TODO REMOVE DEBUG
        if (movement.hasLength() && movement.getLength() > 0) {
            System.out.printf("    Front Move vector: %s",
                    newCarFrontPosition.subtract(oldCarFrontPosition).printXY("(%.3f,%.3f)"));
            System.out.println();
        }

        //TODO REMOVE DEBUG
        if (movement.hasLength() && movement.getLength() > 0) {
            System.out.printf("    Back Move vector: %s",
                    newCarBackPosition.subtract(oldCarBackPosition).printXY("(%.3f,%.3f)"));
            System.out.println();
        }

        //TODO REMOVE DEBUG
        if (movement.hasLength() && movement.getLength() > 0) {
            System.out.printf("Move made: %.9f°,  oldPos:%s, newPos:%s",
                    newPosition.subtract(oldPosition).getDegreesRelativeTo(oldFacing),
                    oldPosition.printXY("(%.0f:%.0f)"),
                    newPosition.printXY("(%.0f:%.0f)"));
            System.out.println();
        }
    }

}
